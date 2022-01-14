package com.example.stockservice.service

import java.util.concurrent.atomic.AtomicInteger

data class ReviewCollectionTask( val numberOfPages:Int,
                                 val numberOfSuccessfulEmits:AtomicInteger
)
