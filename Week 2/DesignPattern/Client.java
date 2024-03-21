import java.util.ArrayList;
import java.util.HashSet;
 
// Car Class Implementing Iterable Interface so
// that we can implement the iterator method and
// add our own implementation

/**
 * Iterator
 */
interface Iterator {

  boolean hasNext();
  Car next();
}


class ListCarIterator implements Iterator{

  private ArrayList<Car> listCar;
  private int position;

  ListCarIterator(ArrayList<Car> listCar){
    this.listCar = listCar;
    this.position = 0;
  }

  public boolean hasNext(){
    return (position<listCar.size()) ? true : false;
  }

  public Car next(){
    if(!hasNext()) throw new IndexOutOfBoundsException("No more books available.");
    return listCar.get(position++);
  }
}

/**
 * CarCollection
 */
interface CarCollection {
  
  Iterator carIterator();
}

class CarStore implements CarCollection{

  private ArrayList<Car> listCar;

  public CarStore(){
    this.listCar = new ArrayList<>();
  }

  public void addCar(Car car){
    listCar.add(car);
  }
  public Iterator carIterator(){
    return new ListCarIterator(listCar);
  }
}


class Car{
  private String carName;
 
  // Default Constructor
  public Car(String name) {
    this.carName = name;
  }

  public String getName(){
    return carName;
  }
 
}

public class Client {

  public static void main(String[] args) {
 
    CarStore cars = new CarStore();
 
    // Adding cars to the Array
    cars.addCar(new Car("Porche"));
    cars.addCar(new Car("Ferrari"));
    cars.addCar(new Car("Sedan"));
 
    // Creating an Iterator and pointing the cursor
    // to the index just before the first element in cars
    Iterator carIterator = cars.carIterator();
 
    // Checking whether the next element is available or not
    while (carIterator.hasNext()) {
      System.out.println(carIterator.next().getName());
    }
  }
}
  

