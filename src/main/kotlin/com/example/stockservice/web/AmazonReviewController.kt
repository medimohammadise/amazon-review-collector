package com.example.stockservice.web

import com.example.stockservice.service.AmazonReviewService
import com.example.stockservice.Review
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@RestController
@RequestMapping("review")
class AmazonReviewController(val amazonReviewService: AmazonReviewService) {


    @GetMapping
    fun getReview(): Flux<Review> {
        //TODO product url and product Id should be supplied by catalog microservice
        return Flux.create { sink: FluxSink<Review> ->
            val test = amazonReviewService.runReviewExtraction(
                url = "https://www.amazon.de/-/en/product-reviews",
                productID = "B094DJTGFN"
            )
            test.forEach {
                it.whenComplete { list: List<Review>, exception: Throwable? ->
                    if (exception==null)
                    list.forEach(sink::next)
                    else exception?.let { it1 -> sink.error(it1).also { println(it) } }
                }
            }
            sink.complete()
        }
    }
}