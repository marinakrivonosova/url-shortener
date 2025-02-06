package org.marina.urlshortener.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class E(
    val message: String?
)

@ControllerAdvice
internal class GlobalControllerExceptionHandler {
    @ExceptionHandler(UrlShortenerException::class)
    fun handlePathResolverException(e: UrlShortenerException) =
        ResponseEntity(E(e.message), HttpStatus.BAD_REQUEST)
}