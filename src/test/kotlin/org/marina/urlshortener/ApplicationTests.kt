package org.marina.urlshortener

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
class ApplicationTests {

    @Test
    fun contextLoads() {
    }

    companion object {
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:17.4")
    }

}
