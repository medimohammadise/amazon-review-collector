package com.example.stockservice.config

//import brave.sampler.Sampler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.Executors


@Configuration
class ExecuterService {

    @Bean
    fun getExecuterService() : Executor {
        val executor = ThreadPoolTaskExecutor()
        // CUSTOMIZE HERE
        // CUSTOMIZE HERE
        executor.corePoolSize = 7
        executor.maxPoolSize = 42
        executor.setQueueCapacity(11)
        executor.setThreadNamePrefix("MyExecutor-")
        // DON'T FORGET TO INITIALIZE
        // DON'T FORGET TO INITIALIZE
        executor.initialize()
        return executor
    }

    /*@Bean
    fun defaultSampler(): Sampler? {
        return Sampler.ALWAYS_SAMPLE
    }*/


}