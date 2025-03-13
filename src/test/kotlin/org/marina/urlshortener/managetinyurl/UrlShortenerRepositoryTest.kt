package org.marina.urlshortener.managetinyurl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.marina.urlshortener.managetinyurl.dto.ShortUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.AfterTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Testcontainers
@SpringBootTest
internal class UrlShortenerRepositoryTest {

    @Autowired
    private lateinit var repo: UrlShortenerRepository

    val shortUrl1 = ShortUrl("short-url1", "very-long-url1")
    val shortUrl2 = ShortUrl("short-url2", "www.ecccveery-long-url")
    val shortUrl3 = ShortUrl("short-url3", "www.ecccveery-long-urdeddel")

    @BeforeEach
    fun clear() {
        repo.deleteAll()
        repo.save(shortUrl1)
        repo.save(shortUrl2)
        repo.save(shortUrl3)
    }

    @AfterTest
    fun cleanDb() {
        repo.deleteAll()

    }

    companion object {
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:17.4")
    }

    @Test
    fun `when set up successful`() {
        assertEquals(3, repo.findAll().count())
    }

    @Test
    fun `when short url is created and saved`() {
        val document = ShortUrl("short-url4", "another-very-long-url")
        val savedDocument = repo.save(document)

        assertEquals(savedDocument, document)
        assertEquals(4, repo.findAll().count())
        assertEquals(4, repo.count())
    }

    @Test
    fun `when find by short url is working`() {
        assertFalse(repo.existsById("short-url4"))
        assertTrue(repo.existsById("short-url3"))
        repo.count()
    }

    @Test
    fun `when saving with not unique short url getting an error`() {
        assertThrows<InvalidDataAccessApiUsageException> {
            repo.insert(
                shortUrl = "short-url1",
                longUrl = "very-long-url-random"
            )
        }
    }

}