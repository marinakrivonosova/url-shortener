package org.marina.urlshortener.managetinyurl.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "short_urls")
class ShortUrl(
    @Column(name = "short_url")
    @Id
    val shortUrl: String = "",

    @Column(name = "long_url")
    val longUrl: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShortUrl

        if (shortUrl != other.shortUrl) return false
        if (longUrl != other.longUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shortUrl.hashCode()
        result = 31 * result + longUrl.hashCode()
        return result
    }
}

