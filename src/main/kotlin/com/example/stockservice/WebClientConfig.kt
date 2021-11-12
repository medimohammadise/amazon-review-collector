package com.example.stockservice


import io.netty.handler.logging.LogLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat

@Configuration
class WebClientConfig {
    @Autowired
    lateinit var appConfiguration: AppConfiguration

   // private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun webClient( builder: WebClient.Builder ): WebClient {
       /* if (logger.isDebugEnabled()) {
            builder.clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                )
            )
        }*/
        return builder.baseUrl("${appConfiguration.fixerApi.latestEndpoint}").build()
    }


}