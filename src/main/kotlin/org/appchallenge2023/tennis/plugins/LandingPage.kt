package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.landingPage(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
        body(classes = "margin AliceBlue") {
            h1(classes = "centeraligntext") {
                +"Tennis Explorer"
            }
            div(classes = "border1LP centeraligntext") {
                h1 {
                    +"Last four majors:"
                }
                br {}
                val allMajors = database.matchQueries.selectAllMajors().executeAsList()
                val last4Majors = allMajors.subList(allMajors.size - 4, allMajors.size)
                for (major in last4Majors) {
                    val winner = database.matchQueries.selectWinner(id = major).executeAsOne()
                    val winnerInfo = database.playerQueries.selectPlayerWithId(winner).executeAsOne()
                    val tourneyInfo = database.matchQueries.selectF(major).executeAsOne()
                    a(href = "/tourneyDetails?tourneyId=${major}") {
                        +"${tourneyInfo.tourney_name} ${tourneyInfo.tourney_date.substring(0, 4)}"
                    }
                    +": Winner was "
                    a(href = "/playerpage?searched=${winnerInfo.player_id}") {
                        +"${winnerInfo.name_first} ${winnerInfo.name_last}"
                    }
                    br {}
                }
            }
            div(classes = "border2LP centeraligntext") {
                h1 {
                    +"Last 10 tournaments:"
                }
                br {}
                val last10Tourneys = database.matchQueries.selectLast10Tourneys().executeAsList()
                for (tourney in last10Tourneys) {
                    val winner = database.matchQueries.selectWinner(id = tourney).executeAsOne()
                    val winnerInfo = database.playerQueries.selectPlayerWithId(winner).executeAsOne()
                    val tourneyInfo = database.matchQueries.selectF(tourney).executeAsOne()
                    a(href = "/tourneyDetails?tourneyId=${tourney}") {
                        +"${tourneyInfo.tourney_name} ${tourneyInfo.tourney_date.substring(0, 4)}"

                    }
                    +": Winner was "
                    a(href = "/playerpage?searched=${winnerInfo.player_id}") {
                        +"${winnerInfo.name_first} ${winnerInfo.name_last}"
                    }
                    br {}
                }
                a(href = "/tourneysForYear?year=2023") {
                    +"Show all tournaments from this year"
                }
            }

            div(classes = "border3LP centeraligntext") {
                val lastRankingDate = database.rankingQueries.selectLastRankingDate().executeAsOne().MAX!!
                val top10 = database.rankingQueries.selectTop10(lastRankingDate).executeAsList()
                h1 {
                    +"The most recent top 10 players are:"
                }
                br {}
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
                a(href = "/rankingDatesForYear?year=2023") {
                    +"Show All Rankings"
                }
            }

            div(classes = "border4LP centeraligntext"){
                +"Search player by name:"
                form(action = "/playerlist", method = FormMethod.get) {
                    input(type = InputType.text, name = "searched")
                    +" "
                    button {
                        +"Search"
                    }
                }
            }
        }
    }
}