package org.marina.urlshortener.managetinyurl.dto

import jakarta.validation.constraints.NotEmpty

data class CreateShortUrlResponse(@NotEmpty val shortUrl: String)