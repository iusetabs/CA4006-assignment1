import java.util.*;
import java.util.concurrent.*;

public class Scenario{

  private static void PeepsFactory(){
    while (true){
      Random rand = new Random();
      for (int i = 0; i < peopleNum; i++){
        int cur_floor = rand.nextInt(10);
        int tar_floor  = rand.nextInt(10);
        Person person = new Person(cur_floor, tar_floor);
         //Add the new person to the elevator class!!!!!!
      }
    }
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
