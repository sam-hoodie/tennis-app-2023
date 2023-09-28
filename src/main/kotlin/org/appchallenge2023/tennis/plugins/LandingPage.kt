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
        body(classes = "margin") {
            h1(classes = "centeraligntext") {
                +"Title (undecided)"
            }
            div(classes = "border1LP centeraligntext") {
                +"Last four majors:"
                br {}
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
                    br {}
                }
            }
            div(classes = "border2LP centeraligntext") {
                +"Last 10 tournaments:"
                br {}
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
                    br {}
                }
                a(href = "/tourneysForYear?year=2023") {
                    +"Show all tournaments from this year"
                }
            }
            div(classes = "border3LP centeraligntext") {
                val lastRankingDate = database.rankingQueries.selectLast10Rankings().executeAsList()[0]
                val top10 = database.rankingQueries.selectTop10(lastRankingDate).executeAsList()
                +"The most recent top 10 players are:"
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
                a(href = "/showLast10Top100") {
                    +"Show top 100 players"
                }
            }
            div(classes = "border4LP centeraligntext"){
                +"Search player by name:"
                form(action = "/playerlist", method = FormMethod.get) {
                    input(type = InputType.text, name = "searched")
                    button {
                        +"Search"
                    }
                }
            }
        }
    }
}