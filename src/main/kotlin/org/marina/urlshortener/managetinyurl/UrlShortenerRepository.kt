package org.marina.urlshortener.managetinyurl

import org.marina.urlshortener.managetinyurl.dto.ShortUrl
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlShortenerRepository : MongoRepository<ShortUrl, String>