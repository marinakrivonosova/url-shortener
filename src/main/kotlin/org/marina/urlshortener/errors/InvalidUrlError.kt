package org.marina.urlshortener.errors

data class InvalidUrlError(override val message: String) : UrlShortenerException(message)