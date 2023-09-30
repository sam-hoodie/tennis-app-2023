package org.appchallenge2023.tennis.sqldelight.data

import org.wikidata.wdtk.datamodel.interfaces.ItemDocument
import org.wikidata.wdtk.datamodel.interfaces.StringValue
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher
import java.security.MessageDigest

fun ItemDocument.getFromStatement(): String? {
    val imgStatement = this.findStatement("P18") ?: return null
    val imgClaim = imgStatement.claim
    val imgSnak = imgClaim.mainSnak
    val imgValue = imgSnak.value
    var imgAddress = (imgValue as StringValue).string
    imgAddress = imgAddress.replace(' ', '_')
//    println(imgAddress)
//    println(md5(imgAddress).toHex())
//    println("https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/$imgAddress")
    return "https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/$imgAddress"
}

fun ItemDocument.getFromStatements(): String? {
    val imgStatementGroup = this.findStatementGroup("P18") ?: return null
    val imgClaim = imgStatementGroup.statements.first().claim
    val imgSnak = imgClaim.mainSnak
    val imgValue = imgSnak.value
    var imgAddress = (imgValue as StringValue).string
    imgAddress = imgAddress.replace(' ', '_')
//    println(imgAddress)
//    println(md5(imgAddress).toHex())
//    println("https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/$imgAddress")
    return "https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/$imgAddress"
}

fun ItemDocument.get(): String? {
    return this.getFromStatement() ?: this.getFromStatements()
}

fun getPlayerImageUrl(playerWikidataId: String): String? {
    try {
        val wbdf = WikibaseDataFetcher.getWikidataDataFetcher()
        val entity = wbdf.getEntityDocument(playerWikidataId)
        if (entity is ItemDocument) {
            return entity.get()
        }
    } catch (t: Throwable) {
        return null
    }
    return null
}

fun main() {
    println(getPlayerImageUrl("Q4189526"))
}

fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(kotlin.text.Charsets.UTF_8))
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
