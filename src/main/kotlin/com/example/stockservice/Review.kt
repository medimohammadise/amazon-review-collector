package com.example.stockservice

import java.time.LocalDate

data class Review( val reviewId:String,  val rating:Int,val customerProfileId:String, val title:String , val author:String,val reviewText:String ,
                   val reviewDate:LocalDate)
