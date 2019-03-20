import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

public class ManagementSystem{

  private ConcurrentLinkedQueue<Person> request_queue;
  private ConcurrentHashMap<String, Elevator> elevators;

  ManagementSystem(){
     this.request_queue = new ConcurrentLinkedQueue<Person>();
     this.elevators = new ConcurrentHashMap<String, Elevator>();
  }

  public void button_press(Person p){
    //System.out.println(p.getPersonName() + " has pressed the button for the elevator.");
    this.request_queue.add(p); //TODO this queue will need to be managed.
    //if (this.requestElevator(p))
    //  return true;
    //System.out.println("A person has decided to get the stairs instead");
    //p = null;
    //return false;
    try {
      this.elevators.get("test").arrivingGoingFromTo(p);
      System.out.println("Pressing all the buttons!!!");
    }
    catch(Exception e){
      System.out.println("A person has decided to get the stairs instead");
      e.printStackTrace();
      p = null;
    }
  }

//  public boolean requestElevator(Person p){
//    return this.elevators.get("test").arrivingGoingFromTo(p);
//  }

  public void addElevator(String key, Elevator elev){
      this.elevators.put(key, elev);
      elev.start();
  }
}
