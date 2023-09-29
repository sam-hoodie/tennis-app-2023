package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2023.tennis.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.showTop100(
    database: Database
) {
    val date = call.parameters["date"]
    val top100 = database.rankingQueries.selectTop100(ranking_date = date.toString()).executeAsList()
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
        body {
            div(classes = "borderTitleTO centeraligntext") {
                h1(classes = "centeraligntext") {
                    if (date != null) {
                        +"Ranking for the week of "
                        +dateToReadableFormat(date.toInt())
                    }
                }
                br {}
                a(href = "/") {
                    +"Home Page"
                }
            }
            br {}
            div(classes = "border1RD") {
                for (i in 0..24) {
                    val player = top100[i]
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
            div(classes = "border2RD") {
                for (i in 25..49) {
                    val player = top100[i]
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
            div(classes = "border3RD") {
                for (i in 50..74) {
                    val player = top100[i]
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
            div(classes = "border4RD") {
                for (i in 75..99) {
                    val player = top100[i]
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
}

public suspend fun PipelineContext<Unit, ApplicationCall>.rankingDatesForYear(
    database: Database
) {
    val yearToFind = call.parameters["year"]!!
    val last10Dates = database.rankingQueries.selectLast10Rankings().executeAsList()
    val dates = database.rankingQueries.selectDatesForYear(year = yearToFind).executeAsList()
    val datesInQuarters = splitDatesIntoQuarters(dates)
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "styles.css")
        }
        body {
            div(classes = "borderTitle centeraligntext") {
                h1 {
                    +"Rank Weeks for $yearToFind"

                }
                if (yearToFind != "1968") {
                    a(href = "/rankingDatesForYear?year=${yearToFind.toInt() - 1}") {
                        +"Previous year  "
                    }
                }
                br{}
                if (yearToFind != "2023") {
                    a(href = "/rankingDatesForYear?year=${yearToFind.toInt() + 1}") {
                        +"  Next Year"
                    }
                }
                br {}
                a(href = "/") {
                    +"Home"
                }
            }
            div(classes = "border1RD") {
                h1 {
                    +"Jan, Feb, Mar"
                }
                for (date in datesInQuarters[0]) {
                    a(href = "/showTop100?date=${date}") {
                        +dateToReadableFormat(date.toInt())
                    }
                    br { }
                }
            }
            div(classes = "border2RD") {
                h1 {
                    +"Apr, May, Jun"
                }
                for (date in datesInQuarters[1]) {
                    a(href = "/showTop100?date=${date}") {
                        +dateToReadableFormat(date.toInt())
                    }
                    br { }
                }
            }
            div(classes = "border3RD") {
                h1 {
                    +"Jul, Aug, Sep"
                }
                for (date in datesInQuarters[2]) {
                    a(href = "/showTop100?date=${date}") {
                        +dateToReadableFormat(date.toInt())
                    }
                    br { }
                }
            }
            div(classes = "border4RD") {
                h1 {
                    +"Oct, Nov, Dec"
                }
                for (date in datesInQuarters[3]) {
                    a(href = "/showTop100?date=${date}") {
                        +dateToReadableFormat(date.toInt())
                    }
                    br { }
                }
            }
        }
    }
}

fun splitDatesIntoQuarters(dates: List<String>): List<List<String>> {
    val first = arrayListOf<String>()
    val second = arrayListOf<String>()
    val third = arrayListOf<String>()
    val fourth = arrayListOf<String>()
    for (date in dates) {
        val dateMonth = date.substring(4, 6)
        if (dateMonth == "01" || dateMonth == "02" || dateMonth == "03") {
            first.add(date)
        } else if (dateMonth == "04" || dateMonth == "05" || dateMonth == "06") {
            second.add(date)
        } else if (dateMonth == "07" || dateMonth == "08" || dateMonth == "09") {
            third.add(date)
        } else {
            fourth.add(date)
        }
    }
    return listOf(first, second, third, fourth)
}