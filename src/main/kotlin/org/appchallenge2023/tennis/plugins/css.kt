package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.Float
import org.appchallenge2023.tennis.respondCss
import org.appchallenge2023.tennis.sqldelight.data.Database
import javax.sound.sampled.Line

public suspend fun PipelineContext<Unit, ApplicationCall>.css() {
    call.respondCss {
        body {
            margin(0.px)
        }

        rule(".centeralignimage") {
            marginLeft = LinearDimension.auto
            marginRight = LinearDimension.auto
            width = LinearDimension("50%")
            display = Display.block
        }
        rule(".centeraligntext") {
            textAlign = TextAlign.center
        }
        rule(".rightaligntext") {
            textAlign = TextAlign.right
        }
        rule(".margin") {
            margin = "3%"
        }
        // fonts
        rule(".trirong") {
            fontFamily = "Trirong"
        }
        // player page
        rule(".floatrightPP") {
            float = Float.right
            width = LinearDimension("40%")

        }
        rule(".floatleftPP") {
            float = Float.left
            width = LinearDimension("60%")
        }
        // landing page
        rule(".border1LP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("6%")
            paddingBottom = LinearDimension("6%")
        }
        rule(".border2LP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            marginLeft = LinearDimension("3%")
            marginRight = LinearDimension("3%")
        }
        rule(".border3LP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
        }
        rule(".border4LP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            marginTop = LinearDimension("23%")
        }
    }
}