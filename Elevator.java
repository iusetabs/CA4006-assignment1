import java.lang.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit.*;

public class Elevator extends Thread{

  private int current_floor = 0;
  private int pri_floor = 0;
  private boolean is_active = false;
  private String id;
  private int max_capacity = 10; //vanilla 10 people max
  private int next_floor = -1;
  private int cur_capacity = 0;
  ConcurrentHashMap<Integer, BlockingQueue<Person>> waiting_Q = new ConcurrentHashMap<>();
  ConcurrentHashMap<String, Person> to_go_map = new ConcurrentHashMap<>(); //FIFO not allowed null elements
  BlockingQueue<Integer> floor_waiting_queue;
  //The size function and the any function with All are not guaranted to work. Unless you lock queue during these operations.
  //Don't rely on iterators with this queue. It can concurrently change.
  //https://docs.oracle.com/javase/10/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html

/*------------CONSTRUCTORS----------------*/

  Elevator(){}
  Elevator(String i_d, ConcurrentHashMap<Integer, BlockingQueue<Person>> map, BlockingQueue<Integer> fq){
     this.id = i_d;
     this.current_floor = 0;
     this.next_floor = 1;
     this.waiting_Q = map;
     this.floor_waiting_queue = fq;
  }

  /*-----------END CONSTRUCTORS------------*/

  public void run(){
      try{
        Thread currentThread = Thread.currentThread();
        System.out.println("[" + this.getId() + "]: Hello from Elevator ID: " + this.id);
        System.out.println("[" + this.getId() + "]: id of the thread is " + currentThread.getId());
        while(true){
          int going_to = this.floor_waiting_queue.poll(1, TimeUnit.MINUTES);
          System.out.println("[" + this.getId() + "]: Going to " + Integer.toString(going_to));
          if (going_to == -1){
            break;
          }
          while (!(going_to == this.getCurrent_floor())){
            Thread.sleep(1000);
            if (ascend(going_to)){
              this.goUp();
            }
            else{
              this.goDown();
            }
            this.get_the_fuck_out();
            this.get_the_fuck_in(going_to);
          }
          System.out.println("[" + this.getId() + "]: Finished on floor " + Integer.toString(this.getCurrent_floor()));
        }
      }
      catch (InterruptedException e){
        System.out.println("[" + this.getId() + "]: FATAL: Elevator " + this.getElevId() + " has been interrupted.");
        e.printStackTrace();
      }
  }



  /*-------------START CLASS FUNCTIONS-------*/

 private void get_the_fuck_in(int going_to){
   while(cur_capacity < 11){
     try{
       Person p = this.waiting_Q.get(this.getCurrent_floor()).poll(3, TimeUnit.SECONDS);
       boolean multi_person = false;
       if (p == null || this.getCur_capacity() > 10)
          break;
       if(p != null){ //if the time has not elapsed
         if (multi_person || going_to != this.getCurrent_floor())
           this.floor_waiting_queue.remove(this.getCurrent_floor());
         else
           multi_person = true;
         p.getPersonLock().lock();
         try{
           p.getElevWaitingCond().signalAll();
           System.out.println("[" + this.getId() + "]: INFO: Elevator_" + this.getElevId() + " says get in " + p.getPersonName() + "!");
           System.out.println("[" + this.getId() + "]: DEBUG: Elev floor: " + Integer.toString(this.getCurrent_floor()) + " " +  p.getPersonName() + " is on floor " + Integer.toString(p.getCur_floor()) + " and wants to go to floor " + Integer.toString(p.getTar_floor()));
         }
         finally{
           p.getPersonLock().unlock();
         }
       }
       Thread.sleep(100);
       System.out.println("[" + this.getId() + "]: Finished letting people in on floor " + this.getCurrent_floor());
     }
     catch (InterruptedException e){
       e.printStackTrace();
     }
   }
}

 private void get_the_fuck_out(){
   assert true: "Nothing";
 }

  private boolean ascend(int dest){
    if (this.current_floor < dest)
      return true;
    else
      return false;
  }

  public void arrivingGoingFromTo(Person p){//merge
    try{
      this.waiting_Q.get(p.getCur_floor()).put(p);
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
    System.out.println(this.id + " --- DEBUG: Is the thread alive?: " + this.isAlive());
  }

  public void goUp(){
    this.pri_floor = this.current_floor;
    this.current_floor++;
    System.out.println("[" + this.getId() + "]: DEBUG: Elevator " + this.getElevId() + " going up " + this.getCurrent_floor());
    //TODO shit here about checking if somebody needs to get off
  }

  public void goDown(){
    this.pri_floor = this.current_floor;
    this.current_floor--;
    System.out.println("[" + this.getId() + "]: DEBUG: Elevator " + this.getElevId() + " going down " + this.getCurrent_floor());
    //TODO shit here about checking if somebody needs to get off
  }

  public synchronized void getIn(Person p){
    this.cur_capacity++;
    this.to_go_map.put(p.getPersonName(), p);
    System.out.println("[" + this.getId() + "]: " + p.getPersonName() + " has just got in elevator_" + this.getElevId() + ".\n[" + this.getId() + "]: " + "Current capacity = " + Integer.toString(this.getCur_capacity()));
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
