package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver


fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
    Database.Schema.create(driver)

    queryRankings(driver, 101414)
}

fun queryRankings(driver: SqlDriver, playerId: Long) {
    val database = Database(driver)
    val rows = database.rankingQueries.selectRankingsForPlayer(player = playerId).executeAsList()
    println("Has ${rows.size} results")
    for (row in rows) {
        println("#${row.rank} on ${row.ranking_date}")
    }
}
