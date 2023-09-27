package org.appchallenge2023.tennis.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.Float
import org.appchallenge2023.tennis.respondCss
import org.appchallenge2023.tennis.sqldelight.data.Database

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
        rule(".floatrightLP") {
            float = Float.right
            width = LinearDimension("40%")

        }
        rule(".floatleftLP") {
            float = Float.left
            width = LinearDimension("60%")
        }
        // landing page
        rule(".trirong") {
            fontFamily = "Trirong"
        }
    }
}