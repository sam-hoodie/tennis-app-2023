package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.tourneyDetails(
    database: Database
) {
    val tournamentId = call.parameters["tourneyId"]!!
    val tourneyMatches = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tournamentId).executeAsList()
    val tourneysQFS = database.matchQueries.selectQF(tourneyId = tournamentId).executeAsList()
    val tourneysSFS = database.matchQueries.selectSF(tourneyId = tournamentId).executeAsList()
    val tourneysF = database.matchQueries.selectF(tourneyId = tournamentId).executeAsList()
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
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
                div(classes = "borderTitle centeraligntext") {
                    h1 {
                        +"${firstMatch.tourney_name} ${firstMatch.tourney_date.substring(0, 4)}"
                        br {}
                    }

                    a(href = "/tourneyDetails?tourneyId=${(tournamentId.substring(0, 4).toInt() - 1).toString()}-${tournamentId.substring(5, tournamentId.length)}") {
                        +"Previous Year"
                    }
                    if (tournamentId.substring(0, 4) != "2023") {
                        a(href = "/tourneyDetails?tourneyId=${(tournamentId.substring(0, 4).toInt() + 1).toString()}-${tournamentId.substring(5, tournamentId.length)}") {
                            +" Next Year"
                        }
                    }
                    br{}
                    br{}
                    a(href = "/") {
                        +"Home"
                    }

                }
                div(classes = "qfs") {
                    h1(classes = "centeraligntext") {
                        +"Quarterfinals:"
                    }
                    br {}
                    for (qf in tourneysQFS) {
                        +"${idToName(qf.winner_id, database)} def ${idToName(qf.loser_id, database)} ${qf.score}"
                        br {}
                    }
                }

                br {}
                div(classes = "sfs") {
                    h1(classes = "centeraligntext") {
                        +"Semifinals:"
                    }
                    br {}
                    for (sf in tourneysSFS) {
                        +"${idToName(sf.winner_id, database)} def ${idToName(sf.loser_id, database)} ${sf.score}"
                        br {}
                    }
                }

                br {}
                div(classes = "fs") {
                    h1(classes = "centeraligntext") {
                        +"Finals:"
                    }
                    br {}
                    +"${idToName(tourneysF[0].winner_id, database)} def ${
                        idToName(
                            tourneysF[0].loser_id,
                            database
                        )
                    } ${tourneysF[0].score}"
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
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
        body {
            div(classes = "borderTitle centeraligntext") {
                h1 {
                    +"Tournaments in $year"
                }
                if (year != "1968") {
                    a(href = "/tourneysForYear?year=${year.toInt() - 1}") {
                        +"Previous year  "
                    }
                }
                br{}
                if (year != "2023") {
                    a(href = "/tourneysForYear?year=${year.toInt() + 1}") {
                        +"  Next Year"
                    }
                }
                br{}
                a(href = "/") {
                    +"Home"
                }
            }

            br {}
            br {  }
            val tourneyQuaters = splitTourneysByQuarters(allTourneys, database)
            div(classes = "border1TP") {
                h1 {
                    +"Jan, Feb, Mar"
                }
                for (tourney in tourneyQuaters[0]) {
                    val match = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
                    val name = match.tourney_name
                    val date = match.tourney_date
                    a(href = "/tourneyDetails?tourneyId=${tourney}") {
                        +"$name ${date.substring(0, 4)}"
                        br{}
                    }
                }
            }
            div(classes = "border2TP") {
                h1 {
                    +"Apr, May, Jun"
                }
                for (tourney in tourneyQuaters[1]) {
                    val match = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
                    val name = match.tourney_name
                    val date = match.tourney_date
                    a(href = "/tourneyDetails?tourneyId=${tourney}") {
                        +"$name ${date.substring(0, 4)}"
                        br{}
                    }
                }
            }
            div(classes = "border3TP") {
                h1 {
                    +"Jul, Aug, Sep"
                }
                for (tourney in tourneyQuaters[2]) {
                    val match = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
                    val name = match.tourney_name
                    val date = match.tourney_date
                    a(href = "/tourneyDetails?tourneyId=${tourney}") {
                        +"$name ${date.substring(0, 4)}"
                        br{}
                    }
                }
            }
            div(classes = "border4TP") {
                h1 {
                    +"Oct, Nov, Dec"
                }
                for (tourney in tourneyQuaters[3]) {
                    val match = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
                    val name = match.tourney_name
                    val date = match.tourney_date
                    a(href = "/tourneyDetails?tourneyId=${tourney}") {
                        +"$name ${date.substring(0, 4)}"
                        br{}
                    }
                }
            }
            br{}

        }
    }
}

fun splitTourneysByQuarters(tourneys: List<String>, database: Database): List<List<String>> {
    val first = arrayListOf<String>()
    val second = arrayListOf<String>()
    val third = arrayListOf<String>()
    val fourth = arrayListOf<String>()
    for (tourney in tourneys) {
        val firstMatch = database.matchQueries.selectAllMatchesFromTourney(tourneyId = tourney).executeAsList()[0]
        val dateMonth = firstMatch.tourney_date.substring(4, 6)
        if (dateMonth == "01" || dateMonth == "02" || dateMonth == "03") {
            first.add(tourney)
        } else if (dateMonth == "04" || dateMonth == "05" || dateMonth == "06") {
            second.add(tourney)
        } else if (dateMonth == "07" || dateMonth == "08" || dateMonth == "09") {
            third.add(tourney)
        } else {
            fourth.add(tourney)
        }
    }
    return listOf(first, second, third, fourth)
}