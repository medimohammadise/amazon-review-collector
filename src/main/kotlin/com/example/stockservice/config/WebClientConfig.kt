package com.example.stockservice.config

import com.example.stockservice.AppConfiguration
import com.example.stockservice.LoggingUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class WebClientConfig {
    @Autowired
    lateinit var appConfiguration: AppConfiguration

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun webClient( builder: WebClient.Builder): WebClient {
        /*val loggingEncoder = LoggingEncoder { bytes ->
            if (log.isTraceEnabled) log.trace(
                "WebClient request body: {}",
                String(bytes)
            )
        }
        val loggingDecoder = LoggingDecoder { bytes ->
            if (log.isTraceEnabled()) log.trace(
                "WebClient response body: \n{}",
                String(bytes)
            )
        }*/
        return builder.baseUrl(appConfiguration.fixerApi.latestEndpoint)
            .filter(logRequest())
            .filter(logResponseStatus())
            /*.codecs { codecConfigurer ->
            codecConfigurer.defaultCodecs().jackson2JsonEncoder(loggingEncoder)
            codecConfigurer.defaultCodecs().jackson2JsonDecoder(loggingDecoder)
            }*/
            .build()
    }

    private fun logRequest(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofRequestProcessor {
            log.debug("WebClient request: {}: {}", it.method(), it.url().toString())
            if (log.isTraceEnabled)
                log.trace("WebClient request headers: {}", LoggingUtil.getHeaderString(it.headers()))
            Mono.just(it)
        }
    }
    private fun logResponseStatus(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor {
            log.debug(
                "WebClient response status: [{}: {}]",
                it.rawStatusCode(),
                it.statusCode().reasonPhrase
            )
            if (log.isTraceEnabled) {
                log.trace("WebClient response headers: {}", it.headers().asHttpHeaders().toString())
            }
            Mono.just(it)
        }
    }






}