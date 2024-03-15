import java.util.Iterator;
 
// Car Class Implementing Iterable Interface so
// that we can implement the iterator method and
// add our own implementation
 
public class Car implements Iterable<String> {
  private String[] cars;
  private int index;
 
  // Default Constructor
  public Car() {
    cars = new String[10];
    index = 0;
  }
 
  // Method 1
  // Adding method to add Cars
  public void addCar(String car) {
    if (index == cars.length) {
      String[] largerCars = new String[cars.length + 5];
      System.arraycopy(cars, 0, largerCars, 0, cars.length);
      cars = largerCars;
      largerCars = null;
    }
    cars[index] = car;
    index++;
  }
 
  // Method 2
  // Implementing the iterator method and
  // adding implementation
  @Override
  public Iterator<String> iterator() {
    Iterator<String> it = new Iterator<String>() {
 
      private int currentIndex = 0;
 
      // Method 3
      // Finding whether during iteration if
      // there is next element or not
      @Override
      public boolean hasNext() {
        return currentIndex < cars.length && cars[currentIndex] != null;
      }
 
      // Method 4
      // Going to grab each car element by one by one
      // according to the index
      @Override
      public String next() {
        return cars[currentIndex++];
      }
 
      // Method 5
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
 
    return it;
  }
 
  // Method 6
  // Main driver method
  public static void main(String[] args) {
 
    // Instantiating Car object
    Car cars = new Car();
 
    // Adding cars to the Array
    cars.addCar("Dodge");
    cars.addCar("Ferrari");
    cars.addCar("Sedan");
 
    // Creating an Iterator and pointing the cursor
    // to the index just before the first element in cars
    Iterator<String> carIterator = cars.iterator();
 
    // Checking whether the next element is available or not
    while (carIterator.hasNext()) {
      System.out.println(carIterator.next());
    }
  }
}
