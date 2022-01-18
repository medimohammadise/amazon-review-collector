package com.example.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class StockServiceApplication

fun main(args: Array<String>) {


	runApplication<StockServiceApplication>(*args)
}
