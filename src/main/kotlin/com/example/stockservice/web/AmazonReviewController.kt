package com.example.stockservice.web

import com.example.stockservice.Review
import com.example.stockservice.service.AmazonReviewService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("review")
@CrossOrigin
class AmazonReviewController(val amazonReviewService: AmazonReviewService) {


    @GetMapping("/{productId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getReview(@PathVariable productId:String): Flux<Review> {
        //TODO product url and product Id should be supplied by catalog microservice
        return amazonReviewService.runReviewExtraction(
            url = "https://www.amazon.de/-/en/product-reviews", //TODO this one need to be changed to channel
            productID = productId
        )
    }
}