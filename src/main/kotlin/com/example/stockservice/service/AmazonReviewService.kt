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
    fun runReviewExtraction(url: String, productID: String): List<CompletableFuture<List<Review>>> {
        var pageNumber = 1
        var futures: MutableList<CompletableFuture<List<Review>>> = mutableListOf()
        do {
            val doc =
                DocumentFactory.factory { CustomDocument(url = url, productId = productID, pageNumber = pageNumber) }
            val task1 = Supplier<Elements> {
                doc?.reviewElements

            }
            val future: CompletableFuture<List<Review>> =
                CompletableFuture.supplyAsync(task1).thenApply { doc -> extractLatestReviews(doc) }
            futures.add(future)
            pageNumber++
        } while (doc?.pageNumber != 0)
        return futures
    }

    fun extractLatestReviews(pageDocument: Elements?): List<Review> {
        var reviews: MutableList<Review> = arrayListOf();
        var allReviewsInPage: Elements
        // var pageNo = 1

        val pattern: Pattern = Pattern.compile("(\\d+)")
        if (pageDocument != null) {
            /*pageSize = Integer.valueOf(document.get().select("div[id~=cm_cr]")
                .select("li[class~=page-button]").last().
                            select("a").first().html());*/
            allReviewsInPage = pageDocument.select("div[data-hook=review]")
            allReviewsInPage.forEach { c ->
                val ratingText = c.select("i[data-hook=review-star-rating] span[class=a-icon-alt]").html()
                val ratingPattern =
                    "[0-9].[0-9]".toRegex()
                val rating= Integer.valueOf(ratingPattern.find(ratingText)?.value?.substringBefore("."))
                val reviewBody: String =
                    c.select("div[id^=customer_review] span[class=cr-original-review-content]").html()
                val reviewTitle: String =
                    c.select("a[data-hook=review-title] span[class=cr-original-review-content]").html()
                val reviewAuthor: String =
                    c.select("span[class=a-profile-name]").html()
                var reviewDateText: String =
                    c.select("span[data-hook=review-date]").html()
                val pattern =
                    "\\d{1,2}\\s(January|February|March|April|May|Jun|July|August|September|October|November|December)\\s\\d{4}".toRegex()
                val match = pattern.find(reviewDateText)
                val formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val formatter2 = DateTimeFormatter.ofPattern("d MMMM yyyy")
                var localReviewDate=LocalDate.now()
                try {
                     localReviewDate = LocalDate.parse(match?.value, formatter1)
                }
                catch(ex:Exception)
                {
                     localReviewDate = LocalDate.parse(match?.value, formatter2)
                }
                // if (localReviewDate.isAfter(startDate)) { TODO develop this logic later
                // println("new review received")
                reviews.add(
                    Review(
                        reviewId=c.id(),
                        rating=rating,
                        customerProfileId = c.select("div[id^=customer_review]").attr("id")
                            .substring("customer_review-".length),
                        title = reviewTitle,
                        author = reviewAuthor,
                        reviewText = reviewBody,
                        reviewDate = localReviewDate
                    )
                )
            }
        }
        return reviews
    }
}