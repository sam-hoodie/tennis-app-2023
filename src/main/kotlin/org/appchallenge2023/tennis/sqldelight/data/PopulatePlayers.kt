package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException

fun main() {
    val players: List<TennisPlayer> = readAllPlayers()
    println("Read ${players.size} players")

    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
    Database.Schema.create(driver)
    populatePlayersDatabase(driver, players)
}

fun readAllPlayers() : List<TennisPlayer> {
    val mapper = CsvMapper()
    mapper.registerModule(KotlinModule())
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    val input = TennisPlayer::class.java.getResourceAsStream("/atp_players.csv")

    return try {
        mapper.readerFor(TennisPlayer::class.java)
            .with(CsvSchema.emptySchema().withHeader())
            .readValues<TennisPlayer>(input)
            .readAll()
    } catch (exception: MissingKotlinParameterException) {
        println("Could not read CSV file!")
        println(exception.message)
        throw exception
    }
}

fun populatePlayersDatabase(driver: SqlDriver, players: List<TennisPlayer>) {
    val database = Database(driver)
    val playerQueries: PlayerQueries = database.playerQueries
    val rankingQueries: RankingQueries = database.rankingQueries

    val start = System.currentTimeMillis()
    for (player in players) {
        // Get ranking
        val ranking = rankingQueries.selectHighestRanking(player.player_id).executeAsOne().MIN ?: 10000
        val toInsert = player.copy(highest_ranking = ranking)
        println("Inserting ${toInsert.player_id} with ${toInsert.highest_ranking}")
        playerQueries.insertPlayerObject(toInsert)
    }
    val end = System.currentTimeMillis()
    println("Inserted players in " + (end - start) + " ms")

    println("" + playerQueries.selectAllPlayers().executeAsList().size + " players in database")
}

