package org.marina.urlshortener.managetinyurl

import org.marina.urlshortener.errors.PathResolveError
import org.marina.urlshortener.util.UrlShortenerUtil
import org.marina.urlshortener.util.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UrlShortenerService(
    private val urlRepository: UrlShortenerRepository,
    @Value("\${spring.application.url}") private val urlHead: String
) {

    private val LOGGER = getLogger(UrlShortenerService::class.java)

    private val util = UrlShortenerUtil()

    @Transactional
    fun createShortUrl(longUrl: String): String {
        val shortUrlTail = generateAndEncode(longUrl)
        LOGGER.info("Generated id $shortUrlTail for $longUrl")
        return buildReturn(shortUrlTail)
    }

    fun resolveShortUrl(url: String): String {
        LOGGER.info("Resolving $urlHead$url...")
        val shortUrl = urlRepository
            .findByShortUrl(url)

        if (shortUrl == null) throw PathResolveError("Path cannot be resolved")
        return shortUrl.longUrl
    }

    private fun generateAndEncode(longUrl: String): String {
        val count = urlRepository.count()
        val encodedUrl = util.encode(count + 1)

        urlRepository.insert(shortUrl = encodedUrl, longUrl = longUrl)

        return encodedUrl
    }

    private fun buildReturn(urlTail: String) = urlHead + urlTail
}