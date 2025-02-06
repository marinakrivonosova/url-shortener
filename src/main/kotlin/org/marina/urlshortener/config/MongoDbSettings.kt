package org.marina.urlshortener.config

import com.mongodb.MongoClientSettings
import com.mongodb.connection.ClusterSettings
import com.mongodb.connection.ConnectionPoolSettings
import com.mongodb.connection.SocketSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class MongoDbSettings {
    @Value("\${spring.application.name}")
    private val applicationName: String? = null

    @Bean
    fun mongoClientSettingsBuilderCustomizer(): MongoClientSettingsBuilderCustomizer {
        return MongoClientSettingsBuilderCustomizer { custom: MongoClientSettings.Builder ->
            custom.applyToSocketSettings { builder: SocketSettings.Builder ->
                builder.connectTimeout(
                    CONNECTION_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                )
                    .readTimeout(
                        CONNECTION_TIMEOUT.toLong(),
                        TimeUnit.SECONDS
                    )
            }
                .applyToConnectionPoolSettings { builder: ConnectionPoolSettings.Builder ->
                    builder.maxSize(CONNECTION_POOL_SIZE)
                        .minSize(CONNECTION_POOL_MIN_SIZE)
                        .maxConnectionIdleTime(
                            CONNECTION_POOL_MAX_IDLE_TIME.toLong(),
                            TimeUnit.SECONDS
                        )
                        .maxWaitTime(
                            CONNECTION_POOL_MAX_WAIT_TIME.toLong(),
                            TimeUnit.SECONDS
                        )
                }
                .applyToClusterSettings { builder: ClusterSettings.Builder ->
                    builder.serverSelectionTimeout(
                        CONNECTION_TIMEOUT.toLong(),
                        TimeUnit.SECONDS
                    )
                }
                .applicationName(applicationName)
        }
    }

    companion object {
        private const val CONNECTION_POOL_SIZE = 10
        private const val CONNECTION_POOL_MIN_SIZE = 1
        private const val CONNECTION_POOL_MAX_IDLE_TIME = 3
        private const val CONNECTION_POOL_MAX_WAIT_TIME = 8
        private const val CONNECTION_TIMEOUT = 3
    }
}