
import java.io.*;


class Dog implements Serializable{
		int i;
		final int j;
		C dff;

		public Dog() {
			this.i = 10;
			this.j = 20;
		}

}
 
class C {

}

class Serialize {
	public static void main (String[] args)throws IOException,ClassNotFoundException 
	{
		Dog d1=new Dog();
		//Serialization started
		System.out.println("serialization started");
        //Saving object in file abc.txt
		FileOutputStream fos= new FileOutputStream("abc.txt");
		ObjectOutputStream oos=new ObjectOutputStream(fos);
        //Serialize object d1
		oos.writeObject(d1);
        oos.close();
        fos.close();
		System.out.println("Serialization ended");
	
		//Deserialization started
		System.out.println("Deserialization started");
        //Reading object from file abc.txt
		FileInputStream fis=new FileInputStream("abc.txt");
		ObjectInputStream ois=new ObjectInputStream(fis);
        //Deserialize to create object d2
		Dog d2=(Dog) ois.readObject();
        ois.close();
        fis.close();
		System.out.println("Deserialization ended");
		System.out.println("Dog object data");
		//final result
		System.out.println(d2.i+"\t" +d2.j);
	}
}
