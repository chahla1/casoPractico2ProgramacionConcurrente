package com.example.benchmarkspringthreads.dto;
import java.util.List;

public class BenchmarkResult {
    private int totalTasks;
    private List<ModeResult> results;

    public BenchmarkResult(int totalTasks, List<ModeResult> results) {
        this.totalTasks = totalTasks;
        this.results = results;
    }

    public int getTotalTasks() { return totalTasks; }
    public List<ModeResult> getResults() { return results; }
}
