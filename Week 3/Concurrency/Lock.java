// package Week 3.Concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class Lock {
    static int reetrantLock = 0;
    static int readwriteLock = 0;
    static int stampedLock = 0;
    static int semaphoreLock = 0;

    static void testReetrantLock(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();
        executor.submit(() -> {
            lock.lock();
            try {       
                // reetrantLock++;
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });
        executor.shutdown();
    }
    static void testReadWriteLock(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReadWriteLock lock = new ReentrantReadWriteLock();

        
        executor.submit(() -> {
            lock.writeLock().lock();
            try{
                System.out.println("Write task"); 
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                lock.writeLock().unlock();
            }
        });
        Runnable task = () -> {
            lock.readLock().lock();
            try{
                TimeUnit.SECONDS.sleep(2);
               System.out.println("Read task");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                lock.readLock().unlock();
            }
        };
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
    }
    static void testStampedLock(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        
        executor.submit(() -> {
            long stamp = lock.writeLock();
            try{
                System.out.println("Write task"); 
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                lock.unlockWrite(stamp);;
            }
        });
        Runnable task = () -> {
            long stamp = lock.readLock();
            try{
                TimeUnit.SECONDS.sleep(2);
               System.out.println("Read task");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                lock.unlockRead(stamp);
            }
        };
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
    }
    static void testSemaphore(){
        Semaphore sem = new Semaphore(2);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Runnable task = () -> {
            boolean permit = false;
            try {
                permit = sem.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    TimeUnit.SECONDS.sleep(3);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    sem.release();
                }
            }
        };
        IntStream.range(0, 3)
        .forEach(i -> executor.submit(task));
        executor.shutdown();
    }
    public static void main(String[] args) {
        testReetrantLock();
        testReadWriteLock();
        testStampedLock();
        testSemaphore();
    }
}
