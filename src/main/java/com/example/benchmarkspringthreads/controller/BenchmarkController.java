package com.example.benchmarkspringthreads.controller;

import com.example.benchmarkspringthreads.dto.BenchmarkResult;
import com.example.benchmarkspringthreads.service.BenchmarkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final BenchmarkService benchmarkService;

    public BenchmarkController(BenchmarkService benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @PostMapping("/start")
    public BenchmarkResult startBenchmark(@RequestParam int tasks, @RequestParam int threads) throws Exception {
        return benchmarkService.runBenchmark(tasks, threads);
    }

    @GetMapping("/result")
    public BenchmarkResult getLastResult() {
        return benchmarkService.getLastResult();
    }

    @GetMapping("/modes")
    public String[] getModes() {
        return new String[]{"SEQUENTIAL", "EXECUTOR_SERVICE", "SPRING_ASYNC"};
    }
}
