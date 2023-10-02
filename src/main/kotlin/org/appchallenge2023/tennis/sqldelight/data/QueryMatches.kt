package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)

//    queryMatches(driver, 101414)
    val database = Database(driver)
}

fun queryMatches(driver: SqlDriver, playerId: Long) {
    val database = Database(driver)
    val rows = database.matchQueries.selectMatchesForPlayer(player = playerId).executeAsList()
    println("Has ${rows.size} results")
    for (row in rows) {
        println("#${row.winner_id} vs ${row.loser_id} on the week of ${row.tourney_date}: ${row.score}")
    }

    val wimbledonWins = database.matchQueries.selectWinsInTournamentForPlayer(playerId, "Wimbledon").executeAsList().size
    val wimbledonLosses = database.matchQueries.selectLossesInTournamentForPlayer(playerId, "Wimbledon").executeAsList().size

    println("Wimbledon record $wimbledonWins:$wimbledonLosses")
}
