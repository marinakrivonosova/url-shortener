package org.marina.urlshortener.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


data class E(
    val message: String?
)

@ControllerAdvice
internal class GlobalControllerExceptionHandler {
    @ExceptionHandler(UrlShortenerException::class)
    fun handleUrlShortenerException(e: UrlShortenerException) =
        ResponseEntity(E(e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) =
        ResponseEntity(E(e.message), HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<E> {
        val errorMessages = e.bindingResult
            .fieldErrors
            .mapNotNull { it.defaultMessage }
            .joinToString(",")
        return ResponseEntity(E(errorMessages), HttpStatus.BAD_REQUEST)
    }
}