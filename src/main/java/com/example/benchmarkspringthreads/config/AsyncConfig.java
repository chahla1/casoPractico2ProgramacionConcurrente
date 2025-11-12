package com.example.benchmarkspringthreads.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("benchmarkExecutor")
    public Executor benchmarkExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);   // num minimo de hilos
        executor.setMaxPoolSize(16);   // maximo de hilos
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("bench-");
        executor.initialize();
        return executor;
    }
}
