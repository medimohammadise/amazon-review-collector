package com.example.stockservice.web

import com.example.stockservice.service.AmazonReviewService
import com.example.stockservice.service.ReviewCollectionTask
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("review")
@CrossOrigin
class AmazonReviewController(val amazonReviewService: AmazonReviewService) {


    @GetMapping("/{productId}")

    fun collectReviews(@PathVariable productId:String): CompletableFuture<ReviewCollectionTask> {
        //TODO product url and product Id should be supplied by catalog microservice
        return amazonReviewService.runReviewExtraction(
            url = "https://www.amazon.de/-/en/product-reviews", //TODO this one need to be changed to channel
            productID = productId
        )
    }

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getReviews(): Flux<ServerSentEvent<Any>> {
        return amazonReviewService.getReviews()
    }
}