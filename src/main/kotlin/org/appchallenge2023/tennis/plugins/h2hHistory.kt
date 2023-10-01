package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database
import org.appchallenge2023.tennis.sqldelight.data.PlayerMatch
import org.appchallenge2023.tennis.sqldelight.data.getPlayerImageUrl

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
            body {
                div(classes = "LightCoral") {
                    +"There are no previous matches between ${p1Info.name_first} ${p1Info.name_last} and ${p2Info.name_first} ${p2Info.name_last}"
                }
            }
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
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
        body(classes = "AliceBlue") {
            div(classes = "borderTitleH2H centeraligntext") {
                h1 {
                    +"${p1Info.name_first} ${p1Info.name_last} vs ${p2Info.name_first} ${p2Info.name_last}"
                    br {}
                }
                a(href = "/playerpage?searched=${p1Id}") {
                    +"Back to player page"
                }
                br {}
                br {}
                a(href = "/") {
                    +"Home"
                }
            }
            div(classes = "h2hImage1") {
                img(src = getPlayerImageUrl(p1Info.wikidata_id.toString())) {
                    this.width = "400"
                }
            }

            div(classes = "h2hInfo centeraligntext") {
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
                        br {}
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

            }
            div(classes = "h2hImage2") {
                img(src = getPlayerImageUrl(p2Info.wikidata_id.toString())) {
                    this.width = "400"
                }
            }
        }
    }
}