package org.appchallenge2023.tennis.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello Sam!")
        }
    }
}

fun Application.mainRouting() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
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

public suspend fun PipelineContext<Unit, ApplicationCall>.showPlayerList(
    database: Database
) {
    val search = call.parameters.get("searched")
    val players = database.playerQueries.selectPlayersWithName(name = search.toString()).executeAsList()
    if (players.isEmpty()) {
        call.respondHtml {
            body {
                +"There are no players that match this name!"
                br { }
                a(href = "/", classes = "centeraligntext") {
                    +"Home page"
                }
            }
        }
    } else if (players.size == 1) {
        val playerId = players[0].player_id
        call.respondRedirect(url = "/playerpage?searched=${playerId}")
    } else {
        call.respondHtml {
            for (player in players) {
                body {
                    a(href = "/playerpage?searched=${player.player_id}") {
                        +"${player.name_first} ${player.name_last}"
                    }
                    br { }
                }
            }
        }
    }
}






// djokovic = 104925
// nadal = 104745

