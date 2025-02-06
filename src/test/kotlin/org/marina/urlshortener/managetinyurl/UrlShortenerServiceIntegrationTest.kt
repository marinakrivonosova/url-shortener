package org.marina.urlshortener.managetinyurl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.marina.urlshortener.errors.PathResolveError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Testcontainers
@SpringBootTest
internal class UrlShortenerServiceIntegrationTest {

    @Autowired
    private lateinit var service: UrlShortenerService

    @Autowired
    private lateinit var repo: UrlShortenerRepository


    @BeforeEach
    fun clear() {
        repo.deleteAll()

    }

    @AfterTest
    fun cleanDb() {
        repo.deleteAll()

    }

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val mongo = MongoDBContainer("mongo:7.0").withExposedPorts(27017)
    }

    @Test
    fun `when short url created and saved`() {
        val created1 = service.createShortUrl("long-url")
        assertEquals("shorturl.com/1", created1)
        assertEquals("long-url", repo.findById("1").get().longUrl)


        val created2 = service.createShortUrl("another-long-url")
        assertEquals("shorturl.com/2", created2)

        assertEquals("another-long-url", repo.findById("2").get().longUrl)
    }

    @Test
    fun `when short url is found`() {
        val created1 = service.createShortUrl("long-url")
        assertEquals("shorturl.com/1", created1)
        assertEquals("long-url", repo.findById("1").get().longUrl)
        val longUrl = service.resolveShortUrl("1")
        assertEquals("long-url", longUrl)

    }

    @Test
    fun `when short url is not found`() {
        assertThrows<PathResolveError> { service.resolveShortUrl("4") }
    }

}