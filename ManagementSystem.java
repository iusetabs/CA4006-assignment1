import java.lang.*;
import java.util.*;
import java.util.concurrent.*;
import Person;
import Elevator;

public class MangementSystem{

  private ConcurrentLinkedQueue<Person> request_queue;
  private ConcurrentHashMap<Integer, Elevator> elevators;

  public MangementSystem(){
     this.request_queue = new ConcurrentLinkedQueue<Person>();
     this.elevators = new ConcurrentHashMap<Integer>();
  }

  public boolean button_press(Person e){
    this.request_queue.add(e);
    this.requestElevator(e.getTar_floor());
    return true;
  }

  public boolean requestElevator(int to_floor){
    return this.elevators.get("Test").letMeIn(to_floor);
  }
}
