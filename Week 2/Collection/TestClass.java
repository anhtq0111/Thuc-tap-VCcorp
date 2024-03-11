// package Collection;

import java.util.Objects;

public class TestClass {
    private String name;
    private int age;

    public TestClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TestClass other = (TestClass) obj;
        return Objects.equals(name, other.name) && age == other.age;
    }

    public String toString(){
        return "test - " + name + ":" + age;
    }
}
