package org.marina.urlshortener.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UrlShortenerUtil {
    private val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val base = alphabet.length

    fun encode(input: Long): String {
        val res = StringBuilder()
        var t = input
        while (t > 0) {
            res.append(alphabet[(t % base).toInt()])
            t /= base
        }

        return res.reverse().toString()
    }

    fun decode(shortUrl: String): Int {
        var res = 0
        for (i in shortUrl) {
            res = res * 62 + alphabet.indexOf(i)
        }
        return res
    }
}

fun getLogger(forClass: Class<*>): Logger =
    LoggerFactory.getLogger(forClass)