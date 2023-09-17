package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
    Database.Schema.create(driver)

    for (suffix in 1968..2023) {
        val matches: List<PlayerMatch> = readAllMatches("/atp_matches_$suffix.csv")
        println("Read ${matches.size} matches from $suffix")

        populateMatchesDatabase(driver, matches)
    }
}

fun readAllMatches(filename: String): List<PlayerMatch> {
    val wrapped = BufferedReader(
        InputStreamReader(
            PlayerMatch::class.java.getResourceAsStream(filename)
        )
    )
    val result = mutableListOf<PlayerMatch>()
    while (true) {
        val line = wrapped.readLine() ?: break
        val parts = line.split(",")
        if (parts[0] == "tourney_id") {
            continue
        }
        result.add(
            PlayerMatch(
                tourney_id = parts[0],
                tourney_name = parts[1],
                surface = parts[2],
                tourney_date = parts[5],
                match_num = parts[6].toLong(),
                winner_id = parts[7].toLong(),
                winner_rank = if (parts[45].isNotEmpty()) parts[45].toLong() else -1L,
                loser_id = parts[15].toLong(),
                loser_rank = if (parts[47].isNotEmpty()) parts[47].toLong() else -1L,
                score = parts[23],
                round = parts[25]
            )
        )
    }
    wrapped.close()
    return result
}

fun populateMatchesDatabase(driver: SqlDriver, matches: List<PlayerMatch>) {
    val database = Database(driver)
    val matchQueries: MatchQueries = database.matchQueries

    val start = System.currentTimeMillis()
    for (match in matches) {
        matchQueries.insertMatchObject(match)
    }
    val end = System.currentTimeMillis()
    println("Inserted matches in " + (end - start) + " ms")

    println("" + matchQueries.selectAllMatches().executeAsList().size + " matches in database")
}

