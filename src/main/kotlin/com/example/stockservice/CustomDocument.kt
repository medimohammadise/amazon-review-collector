package com.example.stockservice

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

data class CustomDocument(val url: String, val productId: String, var pageNumber: Int) {
    var reviewElements: Elements;
    var document: Document = Optional.of(
        Jsoup.connect("$url/$productId?pageNumber=$pageNumber").ignoreContentType(true)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36")
            .referrer("http://www.google.com")
            .timeout(20000)
            .followRedirects(true).get()
    ).orElse(null)

    init {
        println("pageNumber$pageNumber")
        reviewElements = document.select("div[data-hook=review]")
        if (document?.select("div[id~=cm_cr-pagination_bar]").isEmpty()) pageNumber=0
    }
}
