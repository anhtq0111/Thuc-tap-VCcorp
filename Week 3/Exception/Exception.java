import java.io.*;

public class Exception {
    static void UnCheckedException(){
        int a = 1;
        int b = 0;
        try{
            int result = a/b;
            System.out.println(result);
        }
        catch(ArithmeticException e){
            //Handle exception
            e.printStackTrace();
        }
    }

    static void CheckedException(){
        try {
            FileReader read = new FileReader("not_exist_file.txt");
            int data;
            while ((data = read.read()) != -1) {
                System.out.print((char)data);
            }
            read.close();
        } catch (IOException e) {
            //Handle exception
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        UnCheckedException();
        CheckedException();
    }
}
