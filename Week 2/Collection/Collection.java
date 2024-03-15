// package Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Collection {
    public static void main(String[] args) {
        ArrayList<String> name = new ArrayList<>();
        HashSet<Integer> age = new HashSet<>();
        HashMap<String, Integer> test = new HashMap<>();

        //Test ArrayList 
        name.add("test1");
        name.add("test2");
        name.add("test3");
        name.add("test3");
        System.out.println(name);
        name.set(3, "test4");
        System.out.println(name);

        //Test HashSet
        age.add(10);
        age.add(20);
        age.add(21);
        age.add(21);
        System.out.println(age);

        //Test HashMap
        test.put("test1", 10);
        test.put("test2", 20);
        test.put("test3", 21);
        test.put("test3", 22);
        System.out.println(test);

        

        //hashCode and equals

        Set<TestClass> personSet = new HashSet<>();

        personSet.add(new TestClass("test1", 25));
        personSet.add(new TestClass("test2", 30));
        personSet.add(new TestClass("test3", 35));

        boolean testHashCode = personSet.add(new TestClass("test1", 26));
        System.out.println("Duplicate element added? " + testHashCode);

        boolean testEquals = personSet.add(new TestClass("test1", 25));
        System.out.println("Duplicate element added? " + testEquals);

        for (TestClass person : personSet) {
            System.out.println(person.toString());
        }
    }
}
