import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

public class ManagementSystem{

  private ConcurrentHashMap<Integer, ArrayList<Person>> request_Q;
  private ConcurrentHashMap<String, Elevator> elevators;

  ManagementSystem(){
     this.request_Q = new ConcurrentHashMap<Integer, ArrayList<Person>>();
     for (int i = 0; i < 10; i ++){
        Arraylist<Person> a = new ArrayList<Person>();
        this.request_Q.put(i, a);
     }
     this.elevators = new ConcurrentHashMap<String, Elevator>();
  }

  public void button_press(Person p){
    //System.out.println(p.getPersonName() + " has   pressed the button for the elevator.");
    this.request_Q.get(p.getCur_floor()).add(p);
    //if (this.requestElevator(p))
    //  return true;
    //System.out.println("A person has decided to get the stairs instead");
    //p = null;
    //return false;
    try {
      System.out.println(p.getPersonName() + " Has just pushed the button");
      this.elevators.get("test").arrivingGoingFromTo(p);
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
