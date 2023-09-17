package org.appchallenge2023.tennis.sqldelight.data

import org.wikidata.wdtk.datamodel.interfaces.ItemDocument
import org.wikidata.wdtk.datamodel.interfaces.StringValue
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher
import java.security.MessageDigest

fun getPlayerImageUrl(playerWikidataId: String): String? {
    try {
        val wbdf = WikibaseDataFetcher.getWikidataDataFetcher()
        val entity = wbdf.getEntityDocument(playerWikidataId)
        if (entity is ItemDocument) {
            val imgStatement = entity.findStatement("P18")
            val imgClaim = imgStatement.claim
            val imgSnak = imgClaim.mainSnak
            val imgValue = imgSnak.value
            var imgAddress = (imgValue as StringValue).string
            imgAddress = imgAddress.replace(' ', '_')
            return "https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/$imgAddress"
        }
    } catch (t: Throwable) {
        return null
    }
    return null
}

fun main() {
    println(getPlayerImageUrl("Q5812"))
}

fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(kotlin.text.Charsets.UTF_8))
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
