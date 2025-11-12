package com.example.benchmarkspringthreads.task;

public class PrimeTask {

    private final int limit;

    public PrimeTask(int limit) {
        this.limit = limit;
    }

    public long run() {
        long count = 0;
        for (int i = 2; i < limit; i++) {
            if (isPrime(i)) count++;
        }
        return count;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
