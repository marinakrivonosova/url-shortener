package org.marina.urlshortener.managetinyurl.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class CreateShortUrlRequest(
    @field:NotEmpty @field:Size(
        max = 1000,
        message = "Maximum length must be 1000 characters"
    ) val url: String
)
