package com.example.stockservice

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

data class CustomDocument(val url: String, val productId: String, var pageNumber: Int) {
    var reviewElements: Elements;
    var document: Document = Optional.of(
        Jsoup.connect("$url/$productId?pageNumber=$pageNumber").ignoreContentType(true)
            .userAgent("Mozilla/6.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
            .referrer("http://www.google.com")
            .timeout(20000)
            .followRedirects(true).get()
    ).orElse(null)

    init {
        reviewElements = document.select("div[data-hook=review]")
        if (document?.select("div[id~=cm_cr-pagination_bar]").isEmpty()) pageNumber=0
    }
}
