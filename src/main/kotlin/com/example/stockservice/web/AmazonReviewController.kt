package com.example.stockservice.web

import com.example.stockservice.Review
import com.example.stockservice.service.AmazonReviewService
import com.example.stockservice.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.function.Function

@Configuration
 class AmazonReviewController(){
    @Autowired
    private lateinit var amazonReviewService: AmazonReviewService
    @Autowired
    private lateinit var testService: TestService

    @Bean
     fun collectReviews(): Function<String, Flux<Review>>? {
        return Function {
            amazonReviewService.runReviewExtraction(
                url = "https://www.amazon.de/-/en/product-reviews", //TODO this one need to be changed to channel
                productID = it
            )
        }
    }

    @Bean
    fun reverseReactive(): Function<Flux<String?>, Flux<String>>? {
        return Function { flux: Flux<String?> ->
            flux
                .map { message: String? ->
                    StringBuilder(
                        message
                    ).reverse().toString()
                }
        }
    }
}
