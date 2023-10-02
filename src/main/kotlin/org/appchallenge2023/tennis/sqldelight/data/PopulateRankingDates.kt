package org.appchallenge2023.tennis.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)

    val list = listOf("current", "20s", "10s", "00s", "90s", "80s", "70s")
    for (suffix in list) {
        val rankings: List<PlayerRanking> = readAllRankings("/atp_rankings_$suffix.csv")
        println("Read ${rankings.size} rankings from $suffix")
        val filtered = rankings.distinctBy { it.ranking_date }.map { PlayerRankingDate(it.ranking_date) }
        println("Filtered by date " + filtered.size)

        populateRankingDatesDatabase(driver, filtered)
    }
}

fun populateRankingDatesDatabase(driver: SqlDriver, rankingDates: List<PlayerRankingDate>) {
    val database = Database(driver)
    val rankingQueries: RankingQueries = database.rankingQueries

    val start = System.currentTimeMillis()
    for (rankingDate in rankingDates) {
        rankingQueries.insertRankingDateObject(rankingDate)
    }
    val end = System.currentTimeMillis()
    println("Inserted rankings in " + (end - start) + " ms")

    println("" + rankingQueries.selectAllRankingDates().executeAsList().size + " ranking dates in database")
}

