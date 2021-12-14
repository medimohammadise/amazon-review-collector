package com.example.stockservice.service

import com.example.stockservice.CustomDocument
import com.example.stockservice.DocumentFactory
import com.example.stockservice.Review
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import java.util.regex.Pattern

@Service
class AmazonReviewService {
    fun runReviewExtraction(url: String, productID: String): List<CompletableFuture<List<Review>>>{
        var pageNumber= 1
        var futures: MutableList<CompletableFuture<List<Review>>> = mutableListOf()
        do {
            val doc =
                DocumentFactory.factory { CustomDocument(url = url, productId = productID, pageNumber = pageNumber) }
            val task1= Supplier<Elements> {
                doc?.reviewElements

            }
            val future: CompletableFuture<List<Review>> =
                CompletableFuture.supplyAsync(task1).thenApply { doc-> extractLatestReviews(doc) }
            futures.add(future)
            pageNumber++
        }
        while (doc?.pageNumber!=0)
        return futures
    }
    fun extractLatestReviews(pageDocument: Elements?): List<Review> {
        var reviews: MutableList<Review> = arrayListOf();
        var allReviewsInPage: Elements
       // var pageNo = 1

        val pattern: Pattern = Pattern.compile("(\\d+)")
        if (pageDocument!=null) {
            /*pageSize = Integer.valueOf(document.get().select("div[id~=cm_cr]")
                .select("li[class~=page-button]").last().
                            select("a").first().html());*/
                allReviewsInPage = pageDocument.select("div[data-hook=review]")
                allReviewsInPage.forEach { c ->
                    val rating = ""
                    /*val macther: Matcher = pattern.matcher(
                        c.select("div[id^=customer_review]")
                            .select("div[data-hook^=review-star-rating]").first().getElementsByClass("a-icon-alt").html()
                    )
                    macther.find()*/
                    val reviewBody: String =
                        c.select("div[id^=customer_review] span[class=cr-original-review-content]").html()
                    val reviewTitle: String =
                        c.select("a[data-hook=review-title] span[class=cr-original-review-content]").html()
                    val reviewAuthor: String =
                        c.select("span[class=a-profile-name]").html()
                    var reviewDate: String =
                        c.select("span[data-hook=review-date]").html()
                    val dateRegExpr="^[0-9][0-9]\\s(January|February|March|April|May|Jun|July|August|September|October|November|December)\\s\\d{4}\$"
                    val pattern=Pattern.compile(dateRegExpr)
                    val matcher=pattern.matcher(reviewDate)
                    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                    //val localReviewDate = LocalDate.parse( matcher.results().findFirst().orElse(null).toString(), formatter)
                   // if (localReviewDate.isAfter(startDate)) { TODO develop this logic later
                       // println("new review received")
                        reviews.add(
                            Review(
                                productId = "1",
                                customerProfileId = c.select("div[id^=customer_review]").attr("id").substring("customer_review-".length),
                                title = reviewTitle,
                                author = reviewAuthor,
                                reviewText = reviewBody,
                                reviewDate = LocalDate.now()
                            )
                        )
                    }
               // }
                //pageNo++
               // println("page = $pageNo")
            //} while (allReviewsInPage.size !== 0 &&
           // )
        }
        return reviews
    }
}