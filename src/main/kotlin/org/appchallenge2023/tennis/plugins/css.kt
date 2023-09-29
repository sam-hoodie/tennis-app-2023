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
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("20%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("34%")
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
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("37%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("10%")
        }
        rule(".border2PP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("37%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("10%")
            marginBottom = LinearDimension("2%")
            marginRight = LinearDimension("2%")
        }
        rule(".border3PP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("37%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float  =  Float.left
            marginLeft = LinearDimension("45%")
            marginTop = LinearDimension("2%")
        }
        rule(".homeBorderPP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("10%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            marginTop = LinearDimension("23%")
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
            marginTop = LinearDimension("28%")
            marginLeft = LinearDimension("33%")
        }
        // all tournaments
        rule(".border1TP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("15%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            marginLeft = LinearDimension("6%")

        }
        rule(".border2TP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("15%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
        }
        rule(".border3TP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("15%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
        }
        rule(".border4TP") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("15%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
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
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("30%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            marginLeft = LinearDimension("-8%")
            marginRight = LinearDimension("1%")
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
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("30%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("31%")
        }
        // rankings
        rule(".border1RD") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("16%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
            marginLeft = LinearDimension("4%")

        }
        rule(".border2RD") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("16%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
        }
        rule(".border3RD") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("16%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
        }
        rule(".border4RD") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("16%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            float = Float.left
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("2%")
            margin = "1%"
        }
        rule(".borderTitleTO") {
            borderStyle = BorderStyle.solid
            borderColor = Color.green
            padding = "2%"
            width = LinearDimension("50%")
            backgroundColor = Color.lightGray
            borderWidth = LinearDimension("10px")
            paddingTop = LinearDimension("0.3%")
            paddingBottom = LinearDimension("0.3%")
            margin = "1%"
            marginLeft = LinearDimension("23%")
        }
    }

}