package org.marina.urlshortener.managetinyurl

import org.marina.urlshortener.errors.PathResolveError
import org.marina.urlshortener.managetinyurl.dto.ShortUrl
import org.marina.urlshortener.util.UrlShortenerUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UrlShortenerService(
    private val urlRepository: UrlShortenerRepository,
    @Value("\${spring.application.url}") private val urlHead: String
) {

    private val util = UrlShortenerUtil()


    fun createShortUrl(longUrl: String): String {
        val shortUrlTail = generateAndEncode(longUrl)
        return buildReturn(shortUrlTail)
    }

    fun resolveShortUrl(url: String): String {
        return urlRepository
            .findById(url)
            .orElseThrow { PathResolveError("Path cannot be resolved") }
            .longUrl
    }

    private fun generateAndEncode(longUrl: String): String {
        val count = urlRepository.count()
        val encodedUrl = util.encode(count + 1)

        val saved = urlRepository.save(ShortUrl(shortUrl = encodedUrl, longUrl = longUrl))
        return saved.shortUrl
    }

    private fun buildReturn(urlTail: String) = urlHead + urlTail
}