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

  public boolean button_press(Person p){
    this.request_queue.add(p);
    if (this.requestElevator(p))
      return true;
    System.out.println("A person has decided to get the stairs instead");
    p = null;
    return false;
  }

  public boolean requestElevator(Person p){
    return this.elevators.get("Test").arrivingGoingFromTo(p);
  }

  public void addElevator(String key, Elevator elev){
      this.elevators.put(key, elev);
      elev.start();
      Thread.sleep(1000); //1 second sleep
  }
}
