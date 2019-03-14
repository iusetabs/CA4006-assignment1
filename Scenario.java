import java.util.*;

public class Scenario{

  public static void main(String [] args){
    ConcurrentLinkedQueue test_queue = new ConcurrentLinkedQueue(); //FIFO not allowed null elements
    //The size function and the any function with All are not guaranted to work. Unless you lock queue during these operations.
    //Don't rely on iterators with this queue. It can concurrently change.
    //https://docs.oracle.com/javase/10/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html
    Elevator elev1 = new Elevator("1");
    Person p1 = new Person("A", 1, 10);
  }
}
