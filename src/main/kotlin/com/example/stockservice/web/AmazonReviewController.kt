package com.example.stockservice.web

import com.example.stockservice.Review
import com.example.stockservice.service.AmazonReviewService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("review")
class AmazonReviewController(val amazonReviewService: AmazonReviewService) {


    @GetMapping( produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getReview(): Flux<Review> {
        //TODO product url and product Id should be supplied by catalog microservice
        return amazonReviewService.runReviewExtraction(
            url = "https://www.amazon.de/-/en/product-reviews",
            productID = "B094DJTGFN"
        )
    }
}