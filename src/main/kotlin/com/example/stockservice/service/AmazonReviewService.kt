package com.example.stockservice.service

import com.example.stockservice.CustomDocument
import com.example.stockservice.DocumentFactory
import com.example.stockservice.Review
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

@Service
class AmazonReviewService {

    private val log = LoggerFactory.getLogger(this.javaClass)
    fun runReviewExtraction(url: String, productID: String): Flux<Review> {
        var pageNumber = 1
        return Flux.create { sink: FluxSink<Review> ->
            do {
                val doc =
                    DocumentFactory.factory {
                        CustomDocument(
                            url = url,
                            productId = productID,
                            pageNumber = pageNumber
                        )
                    }

                if (doc?.reviewElements?.isNotEmpty()!!) {
                    val task1 = Supplier<CustomDocument> {
                        doc

                    }
                    val future: CompletableFuture<Pair<Int, List<Review>>> =
                        CompletableFuture.supplyAsync(task1,).thenApply { doc -> extractLatestReviews(doc) }

                    future.whenComplete { reviewResult: Pair<Int, List<Review>>, exception: Throwable? ->
                        log.info("pageNumber= ${reviewResult.first} list= ${reviewResult.second.size}")
                        if (exception == null)
                            reviewResult.second.forEach(sink::next)
                        else
                            log.info("pageNumber= ${reviewResult.first} exception=${exception.message}")
                    }
                }

                pageNumber++
            } while (doc?.pageNumber != 0)
            sink.complete()
        }
    }

    fun extractLatestReviews(pageDocument: CustomDocument): Pair<Int, List<Review>> {
        var reviews: MutableList<Review> = arrayListOf();
        var allReviewsInPage: Elements
        var localReviewDate: LocalDate = LocalDate.now()
        try {
            if (pageDocument != null) {
                allReviewsInPage = pageDocument.reviewElements.select("div[data-hook=review]")
                allReviewsInPage.forEach { c ->
                    val ratingText = c.select("i[data-hook*=review-star-rating] span[class=a-icon-alt]").html()
                    val ratingPattern =
                        "[0-9].[0-9]".toRegex()
                    val rating =
                        if ("" != ratingText) BigDecimal(ratingPattern.find(ratingText)?.value) else BigDecimal.ZERO
                    if (rating.equals(BigDecimal.ZERO)) println("rating is empty at ${pageDocument.pageNumber} $ratingText")
                    val reviewBody: String =
                        c.select("div[id^=customer_review] span[class=cr-original-review-content]").html()
                    val reviewTitle: String =
                        c.select("a[data-hook=review-title] span[class=cr-original-review-content]").html()
                    val reviewAuthor: String =
                        c.select("span[class=a-profile-name]").html()
                    var reviewDateText: String =
                        c.select("span[data-hook=review-date]").html()
                    if (reviewDateText != "") {
                        val pattern =
                            "\\d{1,2}\\s(January|February|March|April|May|June|July|August|September|October|November|December)\\s\\d{4}".toRegex()
                        val match = pattern.find(reviewDateText)
                        val formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        val formatter2 = DateTimeFormatter.ofPattern("d MMMM yyyy")
                        localReviewDate = LocalDate.now()
                        localReviewDate = try {
                            LocalDate.parse(match?.value, formatter1)
                        } catch (ex: Exception) {
                            LocalDate.parse(match?.value, formatter2)
                        }
                    } else
                        println("review date is empty at ${pageDocument.pageNumber} $reviewDateText")
                    // if (localReviewDate.isAfter(startDate)) { TODO develop this logic later
                    // println("new review received")
                    reviews.add(
                        Review(
                            reviewId = c.id(),
                            rating = rating,
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
        } catch (ex: Exception) {
            log.error(ex.message)
        }
        return Pair(pageDocument.pageNumber, reviews)
    }
}