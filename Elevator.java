import java.lang.*;
import java.util.*;
import java.util.concurrent.*;


public class Elevator extends Thread{

  // 10 floor 1 - 10
  // Will make Evlevator starting random later
  // active is a way to make sure it's not in use, need? TBD.


  // Needed for Thread class
  // e.g elevator p = new elevator ();
  // p.start ();
  //jfslkhjs



//Remove privious floor and use next_floor
  private int current_floor = 0;
  private int pri_floor = 0;
  private boolean is_active = false;
  private String id;
  private int max_capacity = 10; //vanilla 10 people max
  private int next_floor = -1;
  private int cur_capacity = 0;
  ConcurrentLinkedQueue<Person> waiting_list = new ConcurrentLinkedQueue<Person>();
  ConcurrentHashMap<String, Person> to_go_map = new ConcurrentHashMap<String, Person>(); //FIFO not allowed null elements
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
    this.cur_capacity = 0;
    this.current_floor = c_floor;
    this.next_floor = n_floor;
    this.is_active = true;
  }
  /*-----------END CONSTRUCTORS------------*/

  public void run(){
      try{
        Thread currentThread = Thread.currentThread();
        System.out.println("Hello from Elevator ID: " + this.id);
        System.out.println("id of the thread is " + currentThread.getId());
        Iterator<Person> iter = this.waiting_list.iterator();
        while (this.waiting_list.isEmpty()){
          assert true: "Waiting for condition to not be met";
        }
        while(iter.hasNext()){
          //iterate over the contents of the list
            Person p = iter.next();
            String p_key = p.getPersonName();
            System.out.println("DEBUG: Name: " + p_key + " waiting on floor " + p.getCur_floor() + " to go to floor " + p.getTar_floor() + ".");
            int going_to = p.getCur_floor(); //Initally set to where the person is as we need to pick him up!
            int final_dest = p.getTar_floor();
            boolean finished = false;
            while (!finished){
              if (ascend(going_to)){
                this.goUp();
              }
              else{
                this.goDown();
              }
              Thread.sleep(1000);
              if (this.to_go_map.containsKey(p_key) && this.current_floor == p.getTar_floor()){
                finished = true;
                System.out.println("\nINFO: Complete. Elevator_" + this.getElevId() + " elevator current floor: " + this.getCurrent_floor() + "\nINFO: Complete. Person: " + p_key + " person cur_floor: " + this.to_go_map.get(p_key).getCur_floor() + " tar_floor: " + Integer.toString(p.getTar_floor()) + "\n");
                this.to_go_map.remove(p_key); //get out bitch
              }
              else if (!this.to_go_map.containsKey(p_key) && this.getCurrent_floor() == p.getCur_floor()){
                System.out.println("INFO: Elevator_" + this.getElevId() + " says get in " + p_key + "!");
                System.out.println("DEBUG: Elev floor: " + Integer.toString(this.current_floor) + " " + p_key + " is on floor " + Integer.toString(p.getCur_floor()) + " and wants to go to floor " + Integer.toString(p.getTar_floor()));
                going_to = final_dest;
                this.to_go_map.put(p_key, p); //Person has entered the elevator
              }
            }
          }
        }
        catch (InterruptedException e){
          System.out.println("FATAL: Elevator " + this.getElevId() + " has been interrupted.");
          e.printStackTrace();
        }
	}


  /*-------------START CLASS FUNCTIONS-------*/

  private boolean ascend(int dest){
    if (this.current_floor < dest)
      return true;
    else
      return false;
  }

  public void arrivingGoingFromTo(Person p){//merge
     //this.current_floor = atFloor;
//     return this.letMeIn(p);
     this.waiting_list.add(p);
     System.out.println("DEBUG: Is the thread alive?: " + this.isAlive());
  }

//  public boolean letMeIn(Person p){// merge
//      if (this.cur_capacity+1==this.max_capacity)
//        return false; //no room for you bbz
//      this.newDestination(p);
//      return true; //Yes you can come in bbz
//  }

//  public void newDestination(Person p){//merge
    //System.out.println("Adding new person " + p.getPersonName());
//    this.waiting_list.add(p);
//    System.out.println("DEBUG: Is the thread alive?: " + this.isAlive());
    /*if (!this.isAlive()){ // we need to fix this. We need to make sure the thread waits!
      this.start(); //restart the thread if it's dead
      System.out.println("Restaring thread " + this.getId());
    }*/
//  }

  public void goUp(){
    this.pri_floor = this.current_floor;
    this.current_floor++;
    System.out.println("DEBUG: Elevator " + this.getElevId() + " going up " + this.getCurrent_floor());
    //TODO shit here about checking if somebody needs to get off

    for(String s : this.to_go_map.keySet()){
      Person p = this.to_go_map.get(s);
      p.setCur_floor(p.getCur_floor()+1);
      this.to_go_map.put(p.getPersonName(), p);
    }
  }

  public void goDown(){
    this.pri_floor = this.current_floor;
    this.current_floor--;
    System.out.println("DEBUG: Elevator " + this.getElevId() + " going down " + this.getCurrent_floor());
    //TODO shit here about checking if somebody needs to get off
    for(String s : this.to_go_map.keySet()){
      Person p = this.to_go_map.get(s);
      p.setCur_floor(p.getCur_floor()-1);
      this.to_go_map.put(p.getPersonName(), p);
    }
  }

  public String getDirection(){
    if(this.current_floor == 0 || (this.pri_floor < this.current_floor && (this.current_floor != 9))){
      return "UP";
    }
    return "DOWN";
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
