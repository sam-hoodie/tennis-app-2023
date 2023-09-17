
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "org.appchallenge2023.tennis"
version = "0.0.1"

application {
    mainClass.set("org.appchallenge2023.tennis.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}


sqldelight {
    databases {
        create("Database") {
            packageName.set("org.appchallenge2023.tennis.sqldelight.data")
        }
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.473")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.15.2")
    implementation("org.wikidata.wdtk:wdtk-datamodel:0.8.0")
    implementation("org.wikidata.wdtk:wdtk-dumpfiles:0.8.0")
    implementation("org.wikidata.wdtk:wdtk-rdf:0.8.0")
    implementation("org.wikidata.wdtk:wdtk-wikibaseapi:0.8.0")

}
