package com.example.stockservice.web

import com.example.stockservice.Review
import com.example.stockservice.service.AmazonReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer
import java.util.function.Function
import javax.annotation.PostConstruct


@Configuration
 class AmazonReviewController(){
    @Autowired
    private lateinit var amazonReviewService: AmazonReviewService
    @Bean
     fun collectReviews(): Function<String, List<Review>>? {
        return Function {
            amazonReviewService.runReviewExtraction(
                url = "https://www.amazon.de/-/en/product-reviews", //TODO this one need to be changed to channel
                productID = it
            )
        }
    }

    @Bean
    fun getReviews(): Consumer<List<Review>> {
       return Consumer{
           println("Here I am!-------")
          println(it.forEach{it.rating})
       }
    }
}
