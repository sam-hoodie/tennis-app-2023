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
        rule(".borderTitle") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("20%")
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("34%")
            backgroundColor = Color.lightGreen
            borderColor = Color.maroon
        }
        // fonts
        rule(".trirong") {
            fontFamily = "Trirong"
        }


        // player page
        rule(".imageResizePP") {
            width = LinearDimension("30%")
            float = Float.left
            marginLeft = LinearDimension("5%")
        }
        rule(".border1PP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("37%")
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("10%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border2PP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("37%")
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("10%")
            marginBottom = LinearDimension("2%")
            marginRight = LinearDimension("2%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border3PP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("37%")
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("45%")
            marginTop = LinearDimension("2%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".homeBorderPP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("10%")
            borderWidth = LinearDimension("10px")
            marginTop = LinearDimension("23%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }

        // landing page
        rule(".border1LP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("25%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("2%")
            paddingBottom = LinearDimension("6%")
            marginLeft = LinearDimension("2%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border2LP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.khaki
            borderWidth = LinearDimension("10px")
            float = Float.left
            marginLeft = LinearDimension("3%")
            marginRight = LinearDimension("3%")
            borderColor = Color.darkSlateBlue
        }
        rule(".border3LP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.khaki
            borderWidth = LinearDimension("10px")
            float = Float.left
            borderColor = Color.darkSlateBlue
        }
        rule(".border4LP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.khaki
            borderWidth = LinearDimension("10px")
            marginTop = LinearDimension("28%")
            marginLeft = LinearDimension("35%")
            borderColor = Color.darkSlateBlue
        }
        // all tournaments
        rule(".border1TP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("15%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            marginLeft = LinearDimension("6%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki

        }
        rule(".border2TP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("15%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border3TP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("15%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border4TP") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("15%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        //  tournament details
        rule(".qfs") {
            borderStyle = BorderStyle.solid
            borderColor = Color.black
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.saddleBrown
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            marginTop = LinearDimension("1%")
            marginLeft = LinearDimension("3%")
        }
        rule(".sfs") {
            borderStyle = BorderStyle.solid
            borderColor = Color.black
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.silver
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            marginRight = LinearDimension("1%")
            marginLeft = LinearDimension("1%")
        }
        rule(".fs") {
            borderStyle = BorderStyle.solid
            borderColor = Color.black
            padding = "2%"
            width = LinearDimension("25%")
            backgroundColor = Color.gold
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            marginTop = LinearDimension("-1%")
        }
        // h2h
        rule(".h2hInfo") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("30%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            marginLeft = LinearDimension("-8%")
            marginRight = LinearDimension("1%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".h2hImage1") {
            width = LinearDimension("30%")
            marginLeft = LinearDimension("9%")
            float = Float.left
        }
        rule(".h2hImage2") {
            width = LinearDimension("30%")
            float = Float.left

        }
        rule(".borderTitleH2H") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("30%")
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("31%")
            backgroundColor = Color.lightGreen
            borderColor = Color.maroon
        }
        // rankings
        rule(".border1RD") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("16%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            marginLeft = LinearDimension("4%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki

        }
        rule(".border2RD") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("16%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border3RD") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("16%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".border4RD") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("16%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".borderTitleTO") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("50%")
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("23%")
            backgroundColor = Color.lightGreen
            borderColor = Color.maroon
        }
        // player list
        rule(".borderTitlePL") {
            borderStyle = BorderStyle.solid
            padding = "2%"
            width = LinearDimension("35%")
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("28%")
            backgroundColor = Color.lightGreen
            borderColor = Color.maroon
        }
        rule(".borderPL") {
            borderStyle = BorderStyle.solid
            width = LinearDimension("16%")
            borderWidth = LinearDimension("10px")
            float = Float.left
            padding = "2%"
            textAlign = TextAlign.center
            marginTop = LinearDimension("2%")
            marginLeft = LinearDimension("37%")
            borderColor = Color.darkSlateBlue
            backgroundColor = Color.khaki
        }
        rule(".AliceBlue") {
            backgroundColor = Color.aliceBlue
        }
        rule(".LightCoral") {
            backgroundColor = Color.lightCoral
        }

    }
}