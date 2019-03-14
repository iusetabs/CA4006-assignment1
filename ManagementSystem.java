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
    if (this.requestElevator(e.getCur_floor(), e.getTar_floor()))
      return true;
    System.out.println("A person has decided to get the stairs instead");
    e = null;
  }

  public boolean requestElevator(int at_floor, int to_floor){
    return this.elevators.get("Test").arrivingGoingFromTo(at_floor, to_floor);
  }
}
