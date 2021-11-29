package com.example.stockservice

import org.springframework.http.HttpHeaders
import org.springframework.util.CollectionUtils
import java.util.stream.Collectors

class LoggingUtil {
    companion object {
        fun getHeaderString(headers: HttpHeaders): String? {
            return if (CollectionUtils.isEmpty(headers)) {
                ""
            } else headers.entries.stream()
                .map { maskSensitiveHeaders(it) }.collect(Collectors.joining(","))
        }

        private fun maskSensitiveHeaders(entry: Map.Entry<String, List<String>>): String{
            val key = entry.key
            var value = entry.value
            if (HttpHeaders.AUTHORIZATION.equals(key, ignoreCase = true)) {
                value = listOf("***")
            }
            return StringBuilder(key).append(": '").append(value).append("'").toString()
        }
    }

}