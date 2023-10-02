package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)

    val list = listOf("current", "20s", "10s", "00s", "90s", "80s", "70s")
    for (suffix in list) {
        val rankings: List<PlayerRanking> = readAllRankings("/atp_rankings_$suffix.csv")
        println("Read ${rankings.size} rankings from $suffix")
        val filtered = rankings.filter {
            it.rank <= 250
        }
        println("Filtered " + filtered.size)

        populateRankingsDatabase(driver, filtered)
    }
}

fun readAllRankings(filename: String): List<PlayerRanking> {
    val mapper = CsvMapper()
    mapper.registerModule(KotlinModule())
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    val input = PlayerRanking::class.java.getResourceAsStream(filename)

    return try {
        mapper.readerFor(PlayerRanking::class.java)
            .with(CsvSchema.emptySchema().withHeader())
            .readValues<PlayerRanking>(input)
            .readAll()
    } catch (exception: MissingKotlinParameterException) {
        println("Could not read CSV file!")
        println(exception.message)
        throw exception
    }
}

fun populateRankingsDatabase(driver: SqlDriver, rankings: List<PlayerRanking>) {
    val database = Database(driver)
    val rankingQueries: RankingQueries = database.rankingQueries

    val start = System.currentTimeMillis()
    for (ranking in rankings) {
        rankingQueries.insertRankingObject(ranking)
    }
    val end = System.currentTimeMillis()
    println("Inserted rankings in " + (end - start) + " ms")

    println("" + rankingQueries.selectAllRankings().executeAsList().size + " rankings in database")
}

