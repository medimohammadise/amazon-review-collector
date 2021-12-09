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
        //var reviewList= amazonReviewService.extractLatestReviews(URL="https://www.amazon.de/-/en/LS43AM704UUXEN-Samsung-S43AM704UU-SmartMonitor-43/product-reviews", productID = "B094DJTGFN", LocalDate.MIN);
        //return Flux.fromIterable(reviewList)




       return Flux.create { sink: FluxSink<Review> ->
           val test = amazonReviewService.runReviewExtraction(
               url = "https://www.amazon.de/-/en/product-reviews",
               productID = "B094DJTGFN"
           )
           test.forEach {
               it.whenComplete { list: List<Review>, exception: Throwable? ->
                   list.forEach(sink::next)
               }
           }
           sink.complete()
       }
    }
}