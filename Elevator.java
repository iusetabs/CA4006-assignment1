import java.lang.*;
import java.util.*;
import java.util.concurrent.*;


public class Elevator extends Thread{

  // 10 floor 0 - 9
  // Will make Evlevator starting random later
  // active is a way to make sure it's not in use, need? TBD.


  // Needed for Thread class
  // e.g elevator p = new elevator ();
  // p.start ();

  private int current_floor = 0;
  private boolean is_active = false;
  private String id;
  private int max_capacity = 10; //vanilla 10 people max
  private int next_floor = -1;
  private int cur_capacity = 0;
  ConcurrentLinkedQueue<Person> to_go_list = new ConcurrentLinkedQueue<Person>(); //FIFO not allowed null elements
  //The size function and the any function with All are not guaranted to work. Unless you lock queue during these operations.
  //Don't rely on iterators with this queue. It can concurrently change.
  //https://docs.oracle.com/javase/10/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

/*------------CONSTRUCTORS----------------*/
  Elevator(){}
  Elevator(String i_d){
     this.id = i_d;
  }
  Elevator(String i_d, int c_floor, int n_floor){
    this.id = i_d;
    this.cur_capacity = 1;
    this.current_floor = c_floor;
    this.next_floor = n_floor;
    this.is_active = true;
  }
  /*-----------END CONSTRUCTORS------------*/

  public void run(){
  //  try{
      Thread currentThread = Thread.currentThread();
      System.out.println("Hello from Elevator ID: " + this.id);
      System.out.println("id of the thread is " + currentThread.getId());
  //  }
//    catch (InterruptedException e){
  //    System.out.println("Elevator ID: " + this.id + " interrupted.");
  //  }
	}


  /*-------------START CLASS FUNCTIONS-------*/

  public boolean arrivingGoingFromTo(Person p){
     //this.current_floor = atFloor;
     return this.letMeIn(p);
  }

  public boolean letMeIn(Person p){
      if (this.cur_capacity+1==this.max_capacity)
        return false; //no room for you bbz
      this.newDestination(p);
      return true; //Yes you can come in bbz
  }

  public void newDestination(Person p){
    if (this.is_active == false)
      this.setIsActive(true);
    this.to_go_list.add(p);
  }

  public void goUp(){
    this.current_floor++;
    //TODO shit here about checking if somebody needs to get off
  }

  public void goDown(){
    this.current_floor--;
    //TODO shit here about checking if somebody needs to get off
  }

  /*-------------END CLASS FUNCTIONS---------*/


  /*------------START GETTERS & SETTERS------------*/
	public int getCurrent_floor() {
		return current_floor;
	}

	public void setCurrent_floor(int current_floor) {
		this.current_floor = current_floor;
	}

	public boolean getIsActive() {
		return this.is_active;
	}

	public void setIsActive(boolean a) {
		this.is_active = a;
	}

	public String getElevId() {
		return id;
	}

	public void setElevId(String id) {
		this.id = id;
	}

	public int getMax_capacity() {
		return max_capacity;
	}

	public void setMax_capacity(int max_capacity) {
		this.max_capacity = max_capacity;
	}

	public int getNext_floor() {
		return next_floor;
	}

	public void setNext_floor(int next_floor) {
		this.next_floor = next_floor;
	}

	public int getCur_capacity() {
		return cur_capacity;
	}

	public void setCur_capacity(int cur_capacity) {
		this.cur_capacity = cur_capacity;
	}
}
