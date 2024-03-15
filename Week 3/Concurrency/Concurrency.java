// package Week 3.Concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Concurrency {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //Runnable thread
        Runnable task1  = () -> {
            String threadName = Thread.currentThread().getName();
            int c = 0;
            while ( c < 100) {
                c++;
                System.out.println(c);
            }
            
            System.out.println(threadName);
        } ;
        ExecutorService pool1 = Executors.newFixedThreadPool(1);
        pool1.submit(task1);
        pool1.shutdown();

        //Pool callable thread
        Callable<Integer> task2 = () -> {
            String threadName = Thread.currentThread().getName();
            int c = 0;
            while (c < 100) {
                c++;
                System.out.println(c);
            }
            System.out.println(threadName);
            return c;
        };

        Callable<Integer> task3 = () -> {
            String threadName = Thread.currentThread().getName();
            int c = 0;
            while (c < 50) {
                c++;
                System.out.println(c);
            }
            System.out.println(threadName);
            return c;
        };
        ExecutorService pool2 = Executors.newFixedThreadPool(2);
        // List<Callable<Integer>> callables = Arrays.asList(task2, task3);
        Future<Integer> future1 = pool2.submit(task2);
        Future<Integer> future2 = pool2.submit(task3);
        pool2.shutdown();
        System.out.println(future1.get());
        System.out.println(future2.get());
        
    }
}
