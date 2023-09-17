package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver


fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
    Database.Schema.create(driver)

    queryPlayer(driver, 104925)
}

fun queryPlayer(driver: SqlDriver, playerId: Long) {
    val database = Database(driver)
    val result = database.playerQueries.selectPlayerWithId(player_id = playerId).executeAsOne()
    println(result)
}


