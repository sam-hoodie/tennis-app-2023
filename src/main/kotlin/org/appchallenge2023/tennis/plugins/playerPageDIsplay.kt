package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database
import org.appchallenge2023.tennis.sqldelight.data.PlayerMatch
import org.appchallenge2023.tennis.sqldelight.data.getPlayerImageUrl

suspend fun PipelineContext<Unit, ApplicationCall>.showPlayer(
    database: Database
) {
    val parameter = call.parameters.get("searched")!!
    val id = parameter.toInt().toLong()
    try {
        val player = database.playerQueries.selectPlayerWithId(player_id = id).executeAsOne()
        val last10Rankings = database.rankingQueries.selectLast10RankingsForPlayer(player = id).executeAsList()
        val last10MatchResults = database.matchQueries.selectLast10MatchesForPlayer(player = id).executeAsList()
        call.respondHtml {
            head {
                link(rel = "stylesheet", href = "styles.css")
                link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Audiowide|Sofia|Trirong")
            }
            body {
                h1(classes = "centeraligntext") {
                    +"${player.name_first} ${player.name_last}"
                }
                br { }
                img(src = getPlayerImageUrl(player.wikidata_id.toString()), classes = "centeralignimage") {
                    this.width = "200"
                }
                br{}
                div(classes = "floatrightPP"){
                    +"Last 10 player rankings:"
                    br{}
                    for (ranking in last10Rankings) {
                        +"#${ranking.rank} on ${dateToReadableFormat(ranking.ranking_date.toInt())} with ${ranking.points} points"
                        br { }
                    }
                }
                div(classes = "floatleftPP"){
                    +"Last 10 Match results:"
                    br{}
                    for (result in last10MatchResults) {
                        var lineToPrint = ""
                        if (result.winner_id == id) {
                            val otherPlayer = database.playerQueries.selectPlayerWithId(result.loser_id).executeAsOne()
                            +"Win vs "
                            a(href = "/playerpage?searched=${otherPlayer.player_id}") {
                                +"${otherPlayer.name_first} ${otherPlayer.name_last}"
                            }
                            +" in ${result.tourney_name}"
                            +" on ${dateToReadableFormat(result.tourney_date.toInt())} (score:${result.score})"
                            a(href = "/showH2H?player1=${player.player_id}&player2=${otherPlayer.player_id}") {
                                +" [Show History]"
                            }

                        } else {
                            val otherPlayer = database.playerQueries.selectPlayerWithId(result.winner_id).executeAsOne()
                            +"Loss vs "
                            a(href = "/playerpage?searched=${otherPlayer.player_id}") {
                                +"${otherPlayer.name_first} ${otherPlayer.name_last}"
                            }
                            +" in ${result.tourney_name}"
                            +" on ${dateToReadableFormat(result.tourney_date.toInt())} (score:${result.score})"
                            a(href = "/showH2H?player1=${player.player_id}&player2=${otherPlayer.player_id}") {
                                +" [Show History]"
                            }
                        }
                        br { }
                    }
                    br { }
                }
                div(classes = "centeraligntext trirong") {
                    +"."
                    br{}
                    val majorWins = database.matchQueries.selectMajorWinsForPlayer(player = id).executeAsList()
                    val layout = getMajorsLayout(majorWins)
                    if (majorWins.isEmpty()) {
                        +"This player has not won a major tournament"
                        br{}
                    } else {
                        +"${majorWins.size} Major Champion"
                        if (layout[4] != 0) {
                            +", (${layout[4]} this year)"
                        }
                        br { }
                        if (layout[0] != 0) {
                            +"${layout[0]} at Australian Open"
                        }
                        if (layout[1] != 0) {
                            +", ${layout[1]} at French Open"
                        }
                        if (layout[2] != 0) {
                            +", ${layout[2]} at Wimbledon"
                        }
                        if (layout[3] != 0) {
                            +", ${layout[3]} at US Open"
                        }
                        br { }
                    }
                    br {}
                    val allTourneysWon = database.matchQueries.selectTourneyWinsForPlayer(player = id).executeAsList()
                    if (allTourneysWon.isEmpty()) {
                        +"No singles titles won"
                    } else {
                        +"${allTourneysWon.size} Singles Titles (${getTourneysWonThisYear(allTourneysWon)} this year)"
                    }
                    br {}
                    br {}
                    val topTenMatchesWon = database.matchQueries.selectWinsVsTop10(player = id).executeAsList()
                    val topTenWonThisYear  = getTop10MatchesCount(topTenMatchesWon)
                    if (topTenMatchesWon.isEmpty()) {
                        +"No matches won vs a top 10 player"
                    } else {
                        +"${topTenMatchesWon.size} wins vs Top 10 Opponents (${topTenWonThisYear} this year)"
                    }
                    br{}
                    br{}
                }
                br{}
                br{}
                a(href = "/", classes = "centeraligntext") {
                    +"Home page"
                }
            }
        }
    } catch (e: NullPointerException) {
        call.respondHtml {
            body {
                +"No matching player found"
                br { }
                a(href = "/", classes = "centeraligntext") {
                    +"Home page"
                }
            }

        }
    }
}

fun getMajorsLayout(majors: List<PlayerMatch>): List<Int> {
    // (Aus, French, Wimby, US, 2023)
    var aus = 0
    var french = 0
    var wimby = 0
    var us = 0
    var thisYear = 0
    for (match in majors) {
        if (match.tourney_date.substring(0, 4) == "2023") {
            thisYear++
        }
        when (match.tourney_name) {
            "Australian Open" -> aus++
            "Roland Garros" -> french++
            "Wimbledon" -> wimby++
            "Us Open" -> us++
            "US Open" -> us++
        }
    }
    return listOf(aus, french, wimby, us, thisYear)
}

fun getTourneysWonThisYear(allWon: List<PlayerMatch>): Int {
    var result = 0
    for (won in allWon) {
        if (won.tourney_date.substring(0, 4) == "2023") {
            result++
        }
    }
    return result
}

fun getTop10MatchesCount(matches: List<PlayerMatch>): Int {
    var result = 0
    for (match in matches) {
        if (match.tourney_date.substring(0, 4) == "2023") {
            result++
        }
    }
    return result
}