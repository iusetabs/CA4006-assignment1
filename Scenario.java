import java.util.*;
import java.util.concurrent.*;

public class Scenario{

  private static void PeepsFactory(){
    //TODO FUCK YEA CUNT
  }

  public static void main(String [] args){
    ManagementSystem ms = new ManagementSystem();
    ms.addElevator("test", new Elevator("test", 0, 10));
    ms.addElevator("test1", new Elevator("test1", 1, 8));

    Person [] people_array = new Person[] {new Person(1, 10), new Person(3, 4), new Person(9, 3), new Person(7,8)};
    //int i = 0;
    //for (Person elem : people_array){
    //}
  }
}
