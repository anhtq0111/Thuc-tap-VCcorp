// package Week 3.Concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Atomic {
    public static void test1AtomicInteger() {
        AtomicInteger atomicInt = new AtomicInteger();
        ExecutorService executor = Executors.newWorkStealingPool();
        IntStream.range(0, 1000)
        .forEach(i -> executor.submit(atomicInt::incrementAndGet));
        executor.shutdown();
        System.out.println(atomicInt.get());
    }

    public static void test2AtomicInteger() {
        AtomicInteger atomicInt = new AtomicInteger(1);
        ExecutorService executor = Executors.newWorkStealingPool();
        IntStream.range(0, 1000)
        .forEach(i -> executor.submit(
            () -> {
                System.out.println(Thread.currentThread().getName());
                Runnable task = () -> atomicInt.updateAndGet(n -> n + 2);
                executor.submit(task);
            }
        ));
        System.out.println(atomicInt.get());
    }

    public static void test3AtomicInteger() {
        AtomicInteger atomicInt = new AtomicInteger();
        ExecutorService executor = Executors.newWorkStealingPool();
        IntStream.range(0, 1000)
        .forEach(i -> executor.submit(() -> {
            atomicInt.accumulateAndGet(i, (n, m) -> n+m);
        }));
        System.out.println(atomicInt.get());
    }

    public static void main(String[] args) {
        test1AtomicInteger();
        // test2AtomicInteger();
        // test3AtomicInteger();
    }
}
