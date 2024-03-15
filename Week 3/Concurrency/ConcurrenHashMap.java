// package Week 3.Concurrency;

import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ForkJoinPool;

public class ConcurrenHashMap {
    public static void testConcurrenHashMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");
        map.put("d4", "p1");
        map.put("e5", "p2");
        map.put("f6", "p3");
        map.put("g7", "p4");


        map.forEach(1, (key, value) ->
        System.out.printf("key: %s; value: %s; thread: %s\n", key, value, Thread.currentThread().getName()));

        String result1 = map.search(1, (key, value) -> {
            System.out.println(Thread.currentThread().getName());
            if ("foo".equals(key)) {
                return value;
            }
            return null;
        });
        System.out.println("Result: " + result1);

        String result2 = map.reduce(1,
        (key, value) -> {
            System.out.println("Transform: " + Thread.currentThread().getName());
            return key + "=" + value;
        },
        (s1, s2) -> {
            System.out.println("Reduce: " + Thread.currentThread().getName());
            return s1 + ", " + s2;
        });
    
        System.out.println("Result: " + result2);
        // System.out.println(ForkJoinPool.getCommonPoolParallelism());
    }

    public static void main(String[] args) {
        testConcurrenHashMap();
    }
}
