package org.appchallenge2023.tennis.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.appchallenge2023.tennis.sqldelight.data.Database

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello Sam!")
        }
    }
}

fun Application.mainRouting() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)
    val database = Database(driver)

    routing {
        get("/") {
            landingPage(database)
        }
        get("/playerpage") {
            showPlayer(database)
        }
        get("/playerlist") {
            showPlayerList(database)
        }
        get("/showTop100") {
            showTop100(database)
        }
        get("/rankingDatesForYear") {
            rankingDatesForYear(database)
        }
        get("/showH2H") {
            showH2HHistory(database)
        }
        get("/tourneyDetails") {
            tourneyDetails(database)
        }
        get("/tourneysForYear") {
            tourneysForYear(database)
        }
        get("/styles.css") {
            css()
        }
    }
}








// djokovic = 104925
// nadal = 104745

