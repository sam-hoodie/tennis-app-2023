package org.appchallenge2023.tennis.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.html.*
import org.appchallenge2023.tennis.respondCss
import org.appchallenge2023.tennis.sqldelight.data.Database
import org.appchallenge2023.tennis.sqldelight.data.PlayerMatch

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
        get("/showLast10Top100") {
            showLast10Top100(database)
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

public suspend fun PipelineContext<Unit, ApplicationCall>.landingPage(
    database: Database
) {
    call.respondHtml {
        body {
            form(action = "/playerlist", method = FormMethod.get) {
                div {
                    input(type = InputType.text, name = "searched")
                }
                button {
                    +"Submit"
                }
            }
            a(href = "/showLast10Top100") {
                +"Show top 100 players"
            }
            br{}
            a(href = "/tourneysForYear?year=2023") {
                +"Show all tournaments from this year"
            }
            br{}
            br{}
            +"Last four majors:"
            br{}
            val allMajors = database.matchQueries.selectAllMajors().executeAsList()
            val last4Majors = allMajors.subList(allMajors.size - 4, allMajors.size)
            for (major in last4Majors) {
                val winner = database.matchQueries.selectWinner(id = major).executeAsOne()
                val winnerInfo = database.playerQueries.selectPlayerWithId(winner).executeAsOne()
                val tourneyInfo = database.matchQueries.selectAllMatchesFromTourney(major).executeAsList()[0]
                a(href = "/tourneyDetails?tourneyId=${major}") {
                    +"${tourneyInfo.tourney_name} ${tourneyInfo.tourney_date.substring(0, 4)}"
                }
                +": Winner was "
                a(href = "/playerpage?searched=${winnerInfo.player_id}") {
                    +"${winnerInfo.name_first} ${winnerInfo.name_last}"
                }
                br{}
            }
            br{}
            +"Last 10 tournaments:"
            br{}
            val allTourneys = database.matchQueries.selectAllTourneys().executeAsList()
            val last10Tourneys = allTourneys.subList(allTourneys.size - 10, allTourneys.size)
            for (tourney in last10Tourneys) {
                val winner = database.matchQueries.selectWinner(id = tourney).executeAsOne()
                val winnerInfo = database.playerQueries.selectPlayerWithId(winner).executeAsOne()
                val tourneyInfo = database.matchQueries.selectAllMatchesFromTourney(tourney).executeAsList()[0]
                a(href = "/tourneyDetails?tourneyId=${tourney}") {
                    +"${tourneyInfo.tourney_name} ${tourneyInfo.tourney_date.substring(0, 4)}"

                }
                +": Winner was "
                a(href = "/playerpage?searched=${winnerInfo.player_id}") {
                    +"${winnerInfo.name_first} ${winnerInfo.name_last}"
                }
                br{}
            }
            br{}
            val lastRankingDate = database.rankingQueries.selectLast10Rankings().executeAsList()[0]
            val top10 = database.rankingQueries.selectTop10(lastRankingDate).executeAsList()
            +"The most recent top 10 players are:"
            br{}
            for (player in top10) {
                val id = player.player
                val playerInfo = database.playerQueries.selectPlayerWithId(player_id = id).executeAsOne()
                +"${player.rank}. "
                a(href = "/playerpage?searched=${id}") {
                    +"${playerInfo.name_first} ${playerInfo.name_last}"
                }
                +" with ${player.points} points"
                br { }
            }
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

public suspend fun PipelineContext<Unit, ApplicationCall>.showTop100(
    database: Database
) {
    val date = call.parameters["date"]
    val top100 = database.rankingQueries.selectTop100(ranking_date = date.toString()).executeAsList()
    call.respondHtml {
        body {
            h1(classes = "centeraligntext") {
                if (date != null) {
                    +dateToReadableFormat(date.toInt())
                }
            }

            br {}
            for (player in top100) {
                val id = player.player
                val playerInfo = database.playerQueries.selectPlayerWithId(player_id = id).executeAsOne()
                +"${player.rank}. "
                a(href = "/playerpage?searched=${id}") {
                    +"${playerInfo.name_first} ${playerInfo.name_last}"
                }
                +" with ${player.points} points"
                br { }
            }
            a(href = "/") {
                +"Home Page"
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.showLast10Top100(
    database: Database
) {
    val last10Dates = database.rankingQueries.selectLast10Rankings().executeAsList()
    call.respondHtml {
        body {
            for (date in last10Dates) {
                a(href = "/showTop100?date=${date}") {
                    +dateToReadableFormat(date.toInt())
                }
                br { }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.showH2HHistory(
    database: Database
) {
    val p1Id = call.parameters["player1"]!!.toLong()
    val p2Id = call.parameters["player2"]!!.toLong()
    val allH2H = database.matchQueries.selectAllMatchupsWithTwoPlayers(p1Id, p2Id).executeAsList()
    val p1Info = database.playerQueries.selectPlayerWithId(player_id = p1Id).executeAsOne()
    val p2Info = database.playerQueries.selectPlayerWithId(player_id = p2Id).executeAsOne()
    if (allH2H.isEmpty()) {
        call.respondHtml {
            +"There are no previous matches between ${p1Info.name_first} ${p1Info.name_last} and ${p2Info.name_first} ${p2Info.name_last}"
        }
        return
    }
    val p1Wins = arrayListOf<PlayerMatch>()
    val p2Wins = arrayListOf<PlayerMatch>()
    val lastMeeting = allH2H[allH2H.size - 1]
    for (match in allH2H) {
        if (match.winner_id == p1Id) {
            p1Wins.add(match)
        } else {
            p2Wins.add(match)
        }
    }
    val majorMatchups = database.matchQueries.selectMajorMatchups(player1 = p1Id, player2 = p2Id).executeAsList()
    val majorMatchupRecord = returnH2HRecord(majorMatchups, p1Id, p2Id)
    val p1MajorWins = majorMatchupRecord[0]
    val p2MajorWins = majorMatchupRecord[1]
    call.respondHtml {
        body {
            h1 {
                +"${p1Info.name_first} ${p1Info.name_last} vs ${p2Info.name_first} ${p2Info.name_last}"
                br {}
            }
            if (p1Wins.size > p2Wins.size) {
                +"Head-to-Head: ${p1Info.name_last} leads ${p1Wins.size}-${p2Wins.size}"
            } else {
                +"Head-to-Head: ${p2Info.name_last} leads ${p2Wins.size}-${p1Wins.size}"
            }
            br {}
            br {}
            +"Last meeting: ${lastMeeting.tourney_name} ${lastMeeting.tourney_date.substring(0, 4)}"
            br {}
            when (lastMeeting.winner_id) {
                p1Id.toLong() -> +"${p1Info.name_last}: ${lastMeeting.score}"
                p2Id.toLong() -> +"${p2Info.name_last}: ${lastMeeting.score}"
            }
            br {}
            br {}
            if (majorMatchups.isNotEmpty()) {
                if (p1MajorWins > p2MajorWins) {
                    +"${p1Info.name_last} leads in majors ${p1MajorWins}-${p2MajorWins}"
                } else {
                    +"${p2Info.name_last} leads in majors ${p2MajorWins}-${p1MajorWins}"
                }
                br {}
                val finalsMatchups = database.matchQueries.selectMajorFinalsMatchups(p1Id, p2Id).executeAsList()
                val finalsRecord = returnH2HRecord(finalsMatchups, p1Id, p2Id)
                if (finalsMatchups.isEmpty()) {
                    +" (none in finals)"
                } else {
                    if (finalsRecord[0] > finalsRecord[1]) {
                        +"${p1Info.name_last} leads in major finals ${finalsRecord[0]}-${finalsRecord[1]}"
                    } else {
                        +"${p2Info.name_last} leads in major finals ${finalsRecord[1]}-${finalsRecord[0]}"
                    }
                }
            } else {
                +"These two players have not met at a major"
            }
            br {}
            br {}

            if (allH2H.size < 5) {
                +"Last matchups: "
                br {}
                for (match in allH2H) {
                    if (match.winner_id == p1Id) {
                        +"${p1Info.name_last} won ${match.score} at ${match.tourney_name} in ${
                            match.tourney_date.substring(
                                0,
                                4
                            )
                        }"
                    } else {
                        +"${p2Info.name_last} won ${match.score} at ${match.tourney_name} in ${
                            match.tourney_date.substring(
                                0,
                                4
                            )
                        }"
                    }
                    br{}
                }
            } else {
                +"Last 5 matchups:"
                val lastMatchups = allH2H.subList(allH2H.size - 5, allH2H.size)
                br {}
                for (match in lastMatchups) {
                    if (match.winner_id == p1Id) {
                        +"${p1Info.name_last} won ${match.score} at ${match.tourney_name} in ${
                            match.tourney_date.substring(
                                0,
                                4
                            )
                        }"
                    } else {
                        +"${p2Info.name_last} won ${match.score} at ${match.tourney_name} in ${
                            match.tourney_date.substring(
                                0,
                                4
                            )
                        }"
                    }
                    br {}
                }
            }
            br {}
            br {}
            a(href = "/playerpage?searched=${p1Id}") {
                +"Back to player page"
            }
        }

    }
}


// djokovic = 104925
// nadal = 104745

public suspend fun PipelineContext<Unit, ApplicationCall>.tourneyDetails(
    database: Database
) {
    val tournamentId = call.parameters["tourneyId"]!!
    val tourneyMatches = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tournamentId).executeAsList()
    val tourneysQFS = database.matchQueries.selectQF(tourneyId = tournamentId).executeAsList()
    val tourneysSFS = database.matchQueries.selectSF(tourneyId = tournamentId).executeAsList()
    val tourneysF = database.matchQueries.selectF(tourneyId = tournamentId).executeAsList()
    call.respondHtml {
        if (tourneyMatches.isEmpty()) {
            body {
                +"There is no data for this tournament/This tournament Doesn't Exist"
                br{}
                a(href = "/") {
                    +"Home"
                }
            }
        } else {
            val firstMatch = tourneyMatches[0]
            body {
                h1 {
                    +"${firstMatch.tourney_name} ${firstMatch.tourney_date.substring(0, 4)}"
                    br {}
                }
                +"Quarterfinals:"
                br {}
                for (qf in tourneysQFS) {
                    +"${idToName(qf.winner_id, database)} def ${idToName(qf.loser_id, database)} ${qf.score}"
                    br {}
                }
                br {}
                +"Semifinals"
                br {}
                for (sf in tourneysSFS) {
                    +"${idToName(sf.winner_id, database)} def ${idToName(sf.loser_id, database)} ${sf.score}"
                    br {}
                }
                br {}
                +"Finals"
                br {}
                +"${idToName(tourneysF[0].winner_id, database)} def ${
                    idToName(
                        tourneysF[0].loser_id,
                        database
                    )
                } ${tourneysF[0].score}"
                br {}
                br {}
                a(href = "/tourneyDetails?tourneyId=${(tournamentId.substring(0, 4).toInt() - 1).toString()}-${tournamentId.substring(5, tournamentId.length)}") {
                    +"Previous Year"
                }
                if (tournamentId.substring(0, 4) != "2023") {
                    a(href = "/tourneyDetails?tourneyId=${(tournamentId.substring(0, 4).toInt() + 1).toString()}-${tournamentId.substring(5, tournamentId.length)}") {
                        +" Next Year"
                    }
                }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.tourneysForYear(
    database: Database
) {
    val year = call.parameters["year"]!!
    val allTourneys = database.matchQueries.selectAllTourneysFromAYear(year = year).executeAsList()
    call.respondHtml {
        body {
            h1 {
                +"Tournaments in $year"
            }
            if (year != "1968") {
                a(href = "/tourneysForYear?year=${year.toInt() - 1}") {
                    +"Previous year "
                }
            }
            if (year != "2023") {
                a(href = "/tourneysForYear?year=${year.toInt() + 1}") {
                    +"Next Year"
                }
            }
            br {}
            br {  }
            for (tourney in allTourneys) {
                val match = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
                val name = match.tourney_name
                val date = match.tourney_date
                a(href = "/tourneyDetails?tourneyId=${tourney}") {
                    +"$name ${date.substring(0, 4)}"
                    br{}
                }
            }
            br{}
            a(href = "/") {
                +"Home"
            }
        }
    }
}