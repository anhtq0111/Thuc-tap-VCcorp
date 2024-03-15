package _file;

import java.io.*;


public class file {
    public void CreateTextFile(String fileName){
        try {
            File myObj = new File("../file/" + fileName + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("Text file created: " + myObj.getName());
              } else {
                System.out.println("File already exists.");
              }
        } catch (IOException e) {
            //TODO: handle exception
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void WriteTextFile(String fileName, String text){
        try {
            FileWriter write = new FileWriter("../file/" + fileName + ".txt");
            write.write(text);
            write.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void ReadTextFile(String fileName){
        try {
            FileReader read = new FileReader("../file/" + fileName + ".txt");
            int data;
            while ((data = read.read()) != -1) {
                System.out.print((char)data);
            }
            read.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void CreateBinaryFile(String fileName) {
        try {
            File myObj = new File("../file/" + fileName + ".bin");
            if (myObj.createNewFile()) {
                System.out.println("Binary file created: " + myObj.getName());
              } else {
                System.out.println("File already exists.");
              }
        } catch (IOException e) {
            //TODO: handle exception
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void ReadBinaryFile(String fileName){
        try {
            InputStream input = new FileInputStream("../file/" + fileName + ".bin");
            int data;
            while ((data = input.read()) != -1) {
                System.out.print((char) data);
            }
            input.close();

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void WriteBinaryFile(String fileName, byte[] byteArr){
        try {
            OutputStream op = new FileOutputStream("../file/" + fileName + ".bin");
            op.write(byteArr);
            op.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void ReadMultipleFile(String path) throws IOException {
        File dir = new File(path); 
        File[] files = dir.listFiles(); 

        for (File file : files) {
            if (file.isFile()) {
                BufferedReader inputStream = null;
                String line;
                try {
                    inputStream = new BufferedReader(new FileReader(file));
                    while ((line = inputStream.readLine()) != null) {
                        System.out.println(line); 
                    }
                } catch (IOException e) {
                    System.out.println(e);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        file test = new file();
        test.CreateBinaryFile("test1");
        test.CreateTextFile("test2");

        byte[] byteArr = {0x48, 0x65, 0x6C, 0x6C, 0x6F};
        test.WriteBinaryFile("test1", byteArr);
        test.ReadBinaryFile("test1");
        System.out.println();
        test.WriteTextFile("test2", "Hehe");
        test.ReadTextFile("test2");
    }
}
