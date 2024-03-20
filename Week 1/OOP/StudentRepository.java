package OOP;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements IRepository<Student>{
    private List<Student> students = new ArrayList<>();
    
    static void test() {
        System.out.println("StudentRepository.test()");
    }
    
    public void add(){
        System.out.println("Polymorphism");
    }
    
    @Override
    public void add(Student student) {
        students.add(student);
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                break;
            }
        }
    }

    @Override
    public void delete(Student student) {
        students.remove(student);
    }
}
