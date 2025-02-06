package org.marina.urlshortener.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

data class E(
    val message: String?
)

@ControllerAdvice
internal class GlobalControllerExceptionHandler {
    @ExceptionHandler(UrlShortenerException::class)
    fun handleUrlShortenerException(e: UrlShortenerException) =
        ResponseEntity(E(e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception) =
        ResponseEntity(E(e.message), HttpStatus.INTERNAL_SERVER_ERROR)
}