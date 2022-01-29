package com.example.stockservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import javax.validation.Valid

@SpringBootApplication
class StockServiceApplication

fun main(args: Array<String>) {

	runApplication<StockServiceApplication>(*args)
}
