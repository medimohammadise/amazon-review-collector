package com.example.stockservice

import java.time.LocalDate

data class Review( val productId:String, val customerProfileId:String, val title:String , val author:String,val reviewText:String ,
                   val reviewDate:LocalDate)
