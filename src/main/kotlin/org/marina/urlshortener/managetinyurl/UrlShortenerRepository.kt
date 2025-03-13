package org.marina.urlshortener.managetinyurl


import org.marina.urlshortener.managetinyurl.dto.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UrlShortenerRepository : JpaRepository<ShortUrl, String> {

    fun findByShortUrl(shortUrl: String): ShortUrl?

    @Modifying
    @Query(
        value = "insert into short_urls (short_url, long_url) "
                + "values (:shortUrl, :longUrl)", nativeQuery = true
    )
    fun insert(@Param("shortUrl") shortUrl: String, @Param("longUrl") longUrl: String)

}