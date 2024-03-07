package OOP;


// Class Main: Chứa logic xử lý chính của chương trình
public class Main {
    public static void main(String[] args) {
        
        IRepository<Student> studentRepository = new StudentRepository();

        // Thêm mới sinh viên
        studentRepository.add(new Student(1, "hehe", 20));
        studentRepository.add(new Student(2, "hoho", 21));

        // Hiển thị danh sách sinh viên
        for (Student student : studentRepository.getAll()) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", Age: " + student.getAge());
        }

        // Sửa thông tin sinh viên
        Student student = studentRepository.getAll().get(0);
        student.setName("hihi");
        studentRepository.update(student);

        // Xóa sinh viên
        studentRepository.delete(studentRepository.getAll().get(1));

        // Hiển thị danh sách sinh viên sau khi cập nhật
        System.out.println("List student after update:");
        for (Student student1 : studentRepository.getAll()) {
            System.out.println("ID: " + student1.getId() + ", Name: " + student1.getName() + ", Age: " + student1.getAge());
        }
        StudentRepository.test();
        studentRepository.add();
    }
}
