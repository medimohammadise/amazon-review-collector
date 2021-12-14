package com.example.stockservice

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun main() {
    val pattern =
        "\\d{1,2}\\s(January|February|March|April|May|Jun|July|August|September|October|November|December)\\s\\d{4}".toRegex()
    val match = pattern.find("2 May 2021")
    println(match?.value)
    val formatter = DateTimeFormatter.ofPattern("[d]d MMMM yyyy")
    val localReviewDate = LocalDate.parse(match?.value, formatter)
}