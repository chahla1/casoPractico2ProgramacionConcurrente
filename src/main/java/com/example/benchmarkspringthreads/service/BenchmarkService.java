package com.example.benchmarkspringthreads.service;
import com.example.benchmarkspringthreads.dto.BenchmarkResult;
import com.example.benchmarkspringthreads.dto.ModeResult;
import com.example.benchmarkspringthreads.task.PrimeTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class BenchmarkService {

    private final Executor benchmarkExecutor;
    private BenchmarkResult lastResult;

    public BenchmarkService(@Qualifier("benchmarkExecutor") Executor benchmarkExecutor) {
        this.benchmarkExecutor = benchmarkExecutor;
    }

    public BenchmarkResult runBenchmark(int tasks, int threads) throws Exception {
        int perTaskWork = 20000; // cantidad de trabajo por tarea
        List<ModeResult> results = new ArrayList<>();

        //ejecucion secuencial
        long startSeq = System.nanoTime();
        for (int i = 0; i < tasks; i++) new PrimeTask(perTaskWork).run();
        long timeSeq = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startSeq);
        results.add(new ModeResult("SEQUENTIAL", timeSeq, 1.0, 1.0, 1));

        //ejecucion concurrente
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<Long>> futures = new ArrayList<>();
        long startExec = System.nanoTime();
        for (int i = 0; i < tasks; i++) {
            futures.add(executor.submit(() -> new PrimeTask(perTaskWork).run()));
        }
        for (Future<Long> f : futures) f.get();
        long timeExec = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startExec);
        executor.shutdown();

        double speedupExec = (double) timeSeq / timeExec;
        double efficiencyExec = speedupExec / threads;
        results.add(new ModeResult("EXECUTOR_SERVICE", timeExec, speedupExec, efficiencyExec, threads));

        //ejecucion asincrona
        long startAsync = System.nanoTime();
        List<CompletableFuture<Long>> asyncFutures = new ArrayList<>();
        for (int i = 0; i < tasks; i++) {
            asyncFutures.add(runAsyncTask(perTaskWork));
        }
        for (CompletableFuture<Long> f : asyncFutures) f.get();
        long timeAsync = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startAsync);
        double speedupAsync = (double) timeSeq / timeAsync;
        double efficiencyAsync = speedupAsync / threads;
        results.add(new ModeResult("SPRING_ASYNC", timeAsync, speedupAsync, efficiencyAsync, threads));

        lastResult = new BenchmarkResult(tasks, results);
        return lastResult;
    }

    @Async("benchmarkExecutor")
    public CompletableFuture<Long> runAsyncTask(int work) {
        long result = new PrimeTask(work).run();
        return CompletableFuture.completedFuture(result);
    }

    public BenchmarkResult getLastResult() {
        return lastResult;
    }
}
