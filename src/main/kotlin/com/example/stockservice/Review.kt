package com.example.stockservice

import java.math.BigDecimal
import java.time.LocalDate

data class Review( val reviewId:String,  val rating:BigDecimal,val customerProfileId:String, val title:String , val author:String,val reviewText:String ,
                   val reviewDate:LocalDate)
