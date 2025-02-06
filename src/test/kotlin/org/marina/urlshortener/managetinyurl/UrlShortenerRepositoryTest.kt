package org.marina.urlshortener.managetinyurl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.marina.urlshortener.managetinyurl.dto.ShortUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
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

    val document1 = ShortUrl("short-url1", "very-long-url1")
    val document2 = ShortUrl("short-url2", "www.ecccveery-long-url")
    val document3 = ShortUrl("short-url3", "www.ecccveery-long-urdeddel")

    @BeforeEach
    fun clear() {
        repo.deleteAll()
        repo.save(document1)
        repo.save(document2)
        repo.save(document3)
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
    fun `when set up successful`() {
        assertEquals(3, repo.findAll().size)
    }

    @Test
    fun `when document is created and saved`() {
        val document = ShortUrl("short-url4", "another-very-long-url")
        val savedDocument = repo.save(document)

        assertEquals(savedDocument, document)
        assertEquals(4, repo.findAll().size)
        assertEquals(4, repo.count())
    }

    @Test
    fun `when find by short url is working`() {
        assertFalse(repo.existsById("short-url4"))
        assertTrue(repo.existsById("short-url3"))
        repo.count()
    }


}