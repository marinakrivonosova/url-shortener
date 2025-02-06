package org.marina.urlshortener.managetinyurl.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "shortUrl")
data class ShortUrl(@Id val shortUrl: String, val longUrl: String)