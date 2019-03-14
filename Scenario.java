import java.util.*;
import java.util.concurrent.*;
import ManagementSystem;


public class Scenario{

  private final ManagementSystem ms = new ManagementSystem();

  private static void PeepsFactory(){
    //TODO FUCK YEA CUNT
  }

  public static void main(String [] args){
    ConcurrentLinkedQueue<Person> test_queue = new ConcurrentLinkedQueue<Person>(); //FIFO not allowed null elements
    //The size function and the any function with All are not guaranted to work. Unless you lock queue during these operations.
    //Don't rely on iterators with this queue. It can concurrently change.
    //https://docs.oracle.com/javase/10/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html
    Elevator elev1 = new Elevator("1");
    Person p1 = new Person(1, 10);

    //TODO some loop which adds people
    Person [] people_array = new Person[] {new Person(1, 10), new Person(3, 4), new Person(9, 3), new Person(7,8)};
    int i = 0;
    for (Person elem : people_array){
      test_queue.add(elem);
      if (elev1.get
    }
  }
}
