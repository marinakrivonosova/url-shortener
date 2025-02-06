package org.marina.urlshortener.errors

data class PathResolveError(override val message: String) : UrlShortenerException(message)