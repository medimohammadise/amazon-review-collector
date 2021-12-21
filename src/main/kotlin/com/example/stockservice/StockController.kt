package com.example.stockservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.WebApplicationType
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom


@RestController
class StockController {
    @Autowired
    lateinit var webClient: WebClient
    @Autowired
    lateinit var appConfiguration: AppConfiguration
    @GetMapping(value= ["/stock/{symbol}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun prices (@PathVariable symbol:String): Flux<ExchangeRateDTO> {
        return webClient.get().uri{ uriBuilder-> uriBuilder.path("&symbols=${symbol}").build()}
            .retrieve()
            .bodyToFlux(ExchangeRateDTO::class.java)

      // return  Flux.interval(Duration.ofMinutes(5)).log().map { StockPrice(symbol,randomPrice(), LocalDateTime.now()) }.log()
    }

    private fun randomPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }
}