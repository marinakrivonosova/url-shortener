package org.marina.urlshortener.managetinyurl

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.marina.urlshortener.errors.PathResolveError
import org.marina.urlshortener.managetinyurl.dto.CreateShortUrlRequest
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
internal class UrlShortenerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockitoBean
    private lateinit var urlShortenerService: UrlShortenerService

    @Test
    fun `when called should return shortened url`() {
        val longUrl = "https://www.baeldung.com/java-spring-mockito-mock-mockbean"

        Mockito.`when`(urlShortenerService.createShortUrl(longUrl)).thenReturn("super-short-url")

        mockMvc.post("/short-url") {
            content = mapper.writeValueAsString(CreateShortUrlRequest(longUrl))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isCreated()
            }
            content {
                contentType(MediaType.APPLICATION_JSON)
                string("""{"shortUrl":"super-short-url"}""")
            }
        }
    }

    @Test
    fun `when called should return bad request as url invalid`() {
        val invalidUrl = "jdjff./rff/"
        Mockito.`when`(urlShortenerService.createShortUrl(invalidUrl)).thenReturn("super-short-url")

        mockMvc.post("/short-url") {
            content = mapper.writeValueAsString(CreateShortUrlRequest(invalidUrl))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                isBadRequest()
            }
            content {
                contentType(MediaType.APPLICATION_JSON)
                string("""{"message":"Invalid URL: jdjff./rff/"}""")
            }
        }
    }

    @Test
    fun `when called should resolve and redirect`() {
        doAnswer { "super-long-url" }
            .whenever(urlShortenerService)
            .resolveShortUrl("abc")

        mockMvc.get("/abc").andExpect {
            status {
                isFound()
            }
        }
    }

    @Test
    fun `when called resolve found nothing`() {
        doAnswer { throw PathResolveError("Path cannot be resolved") }
            .whenever(urlShortenerService)
            .resolveShortUrl("abc")
        mockMvc.get("/abc").andExpect {
            status {
                isBadRequest()
            }
            content {
                contentType(MediaType.APPLICATION_JSON)
                string("""{"message":"Path cannot be resolved"}""")
            }
        }
    }
}