import java.lang.*;
import java.util.*;

public class Elevator extends Thread{

  // 10 floor 0 - 9
  // Will make Evlevator starting random later
  // active is a way to make sure it's not in use, need? TBD.


  // Needed for Thread class
  // e.g elevator p = new elevator ();
  // p.start ();

  private int current_floor = 0;
  private boolean active = false;
  private String id;
  private int max_capacity = 10; //vanilla 10 people max
  private int next_floor = -1;
  private int cur_capacity = 0;
  private LinkedList<Integer> to_go_list = new LinkedList<>();


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
    this.active = true;
  }
  /*-----------END CONSTRUCTORS------------*/


  /*-------------START CLASS FUNCTIONS-------*/

  public void ArrivingGoingFromTo(int atFloor, int toFloor){
     this.current_floor = atFloor;
     this.letMeIn(toFloor);
  }

  public Boolean letMeIn(int toFloor){
      if (this.cur_capacity+1==this.max_capacity)
        return false; //no room for you bbz
      this.newDestination(toFloor);
      return true; //Yes you can come in bbz
  }

  public void newDestination(int floor){
    this.to_go_list.add(floor);
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

  public void run(){

	   System.out.println("Hello from the thread");
	}

	public int getCurrent_floor() {
		return current_floor;
	}

	public void setCurrent_floor(int current_floor) {
		this.current_floor = current_floor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
