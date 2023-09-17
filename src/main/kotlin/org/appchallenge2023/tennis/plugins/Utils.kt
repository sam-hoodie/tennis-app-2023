package org.appchallenge2023.tennis.plugins

import org.appchallenge2023.tennis.sqldelight.data.PlayerMatch

fun dateToReadableFormat(date: Int): String {
    val dateString = date.toString()
    val year = dateString.substring(0, 4)
    val day = dateString.substring(6, 8)
    val month = monthToName(dateString.substring(4, 6))
    return "$month $day, $year"
}

fun monthToName(month: String): String {
    return when(month) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "Invalid month"
    }
}

fun returnH2HRecord(matches: List<PlayerMatch>, p1: Long, p2: Long): List<Int> {
    var p1wins = 0
    var p2wins = 0
    for (match in matches) {
        if (match.winner_id == p1) {
            p1wins ++
        } else {
            p2wins ++
        }
    }
    return listOf(p1wins, p2wins)
}
