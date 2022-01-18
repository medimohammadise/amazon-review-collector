package com.example.stockservice.web

import com.example.stockservice.Review
import com.example.stockservice.service.AmazonReviewService
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
 class AmazonReviewController(private val amazonReviewService: AmazonReviewService){
    @Bean
     fun collectReviews(): (Flux<String>) -> (Flux<ServerSentEvent<Any>>) {
        return {
            amazonReviewService.runReviewExtraction(
                url = "https://www.amazon.de/-/en/product-reviews", //TODO this one need to be changed to channel
                productID = it.blockFirst()!!
            )
        }
    }
}
