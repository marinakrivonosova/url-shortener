package org.marina.urlshortener.managetinyurl.dto

import jakarta.validation.constraints.NotEmpty


data class CreateShortUrlRequest(@NotEmpty val url: String)