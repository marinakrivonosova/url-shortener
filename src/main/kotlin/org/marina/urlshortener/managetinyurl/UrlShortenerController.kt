package org.marina.urlshortener.managetinyurl

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.apache.commons.validator.routines.UrlValidator
import org.marina.urlshortener.errors.InvalidUrlError
import org.marina.urlshortener.managetinyurl.dto.CreateShortUrlRequest
import org.marina.urlshortener.managetinyurl.dto.CreateShortUrlResponse
import org.marina.urlshortener.util.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class UrlShortenerController(val urlShortenerService: UrlShortenerService) {

    private val LOGGER = getLogger(UrlShortenerController::class.java)

    @PostMapping("/short-url", produces = ["application/json"])
    fun createShortUrl(@RequestBody @Valid request: CreateShortUrlRequest): ResponseEntity<out Any> {
        val isValid = UrlValidator.getInstance().isValid(request.url)
        if (!isValid) {
            LOGGER.error("URL {} is invalid", request.url)
            throw InvalidUrlError("Invalid URL: ${request.url}")
        }
        val shortURL = urlShortenerService.createShortUrl(request.url)
        val response = CreateShortUrlResponse(shortURL)
        return ResponseEntity<Any>(response, HttpStatus.CREATED)
    }

    @GetMapping("/{url}")
    fun resolveTinyUrl(@PathVariable url: String, request: HttpServletRequest): ResponseEntity<out Any> {
        val resolved = urlShortenerService.resolveShortUrl(url)
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(resolved))
            .build()
    }


}