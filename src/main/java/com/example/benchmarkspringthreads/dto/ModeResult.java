package com.example.benchmarkspringthreads.dto;
public class ModeResult {
    private String mode;
    private long timeMs;
    private double speedup;
    private double efficiency;
    private int threadsUsed;

    public ModeResult(String mode, long timeMs, double speedup, double efficiency, int threadsUsed) {
        this.mode = mode;
        this.timeMs = timeMs;
        this.speedup = speedup;
        this.efficiency = efficiency;
        this.threadsUsed = threadsUsed;
    }

    public String getMode() { return mode; }
    public long getTimeMs() { return timeMs; }
    public double getSpeedup() { return speedup; }
    public double getEfficiency() { return efficiency; }
    public int getThreadsUsed() { return threadsUsed; }
}

