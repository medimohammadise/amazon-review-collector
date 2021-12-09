package com.example.stockservice

import java.util.function.Supplier


class DocumentFactory {
    companion object {
        fun factory(s: Supplier<out CustomDocument?>): CustomDocument? {
            return s.get()
        }
    }

}