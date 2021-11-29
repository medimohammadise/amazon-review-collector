package com.example.stockservice

import java.io.Serializable
import java.math.BigDecimal
import java.util.Date

data class ExchangeRateDTO(
        val success: Boolean? = false,
        val historical:Boolean?= false,
        val timestamp: Date? = null,
        var base: String? = null,

        val date: Date? = null,

        val rates: Map<String, BigDecimal>? = null,
        var error: ExchangeRateErrorDTO? = ExchangeRateErrorDTO()
) :Serializable{

    override fun toString(): String {
        return "ExchangeRate [base=$base, date=$date, rates=$rates]"
    }
}

data class ExchangeRateErrorDTO(
        val code: String?=null,
        val type: String?=null,
        val info: String?=null
):Serializable