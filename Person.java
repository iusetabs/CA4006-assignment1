import java.lang.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Person implements Runnable {

  private int cur_floor;
  private int tar_floor;
  private String name;
  private Boolean in_elevator;
  private ConcurrentHashMap<Integer, BlockingQueue<Person>> airport_map;
  final Lock lock = new ReentrantLock();
  public final Condition waiting_for_elevator = lock.newCondition();
  Elevator the_elevator;
  BlockingQueue<Integer> floor_waiting_queue = new LinkedBlockingQueue<Integer>();


  /*------------CONSTRUCTORS----------------*/

  Person(){}

  Person(int cur_floor, int tar_floor, int i, ConcurrentHashMap<Integer, BlockingQueue<Person>> map, Elevator e, BlockingQueue<Integer> fq){
    //this.pid = pid;
    this.cur_floor = cur_floor;
    this.tar_floor = tar_floor;
    this.in_elevator = false;
    this.airport_map = map;
    this.the_elevator = e;
    this.floor_waiting_queue = fq;
    this.name = "Person_" + Integer.toString(i);
  }
  /*-----------END CONSTRUCTORS------------*/

  public void run() {
      System.out.println("[" + this.getPersonName() +  "_:_ID: " + Thread.currentThread().getId() + "]: Hello! cur_floor: " + Integer.toString(this.getCur_floor()));
      this.button_press();
      this.before_elevator();
      //Getting into elevator
      this.in_elevator = true;
      this.elevating();
        //getting out of elevator
   }

  private void before_elevator(){
    this.person_lock.lock();
    try{
      this.waiting_for_elevator.await();
      this.the_elevator.getIn(this);
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
  }
  //signalall().
  //Current if we ask the perosn they will get out.
  //Person need to decided to get out.

  private void elevating(){
    this.the_elevator.lock.lock();
    try{
      while(this.in_elevator == true){
        this.the_elevator.waiting_to_get_out.await();
        //System.out.println("Reciving signal");
        if(this.tar_floor == this.the_elevator.getCurrent_floor()){
          this.in_elevator = false;
          System.out.println(this.name + " Leaving!!!");
          this.the_elevator.setCur_capacity(the_elevator.getCur_capacity()-1);
        }
      }
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    finally{
      this.the_elevator.lock.unlock();
    }
  }


  private void button_press(){
    try{
      this.airport_map.get(this.getCur_floor()).put(this);
      this.floor_waiting_queue.put(this.getCur_floor()); //will wake up elevator if nobody on it.
      System.out.println("[" + this.getPersonName() +  "_:_ID: " + Thread.currentThread().getId() + "]: Button pressed");
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
  }

	public int getCur_floor() {
		return cur_floor;
	}

	public void setCur_floor(int cur_floor) {
		this.cur_floor = cur_floor;
	}

	public int getTar_floor() {
		return tar_floor;
	}

	public void setTar_floor(int tar_floor) {
		this.tar_floor = tar_floor;
	}

  public void setPersonName(String name){
    this.name = name;
  }

  public String getPersonName(){
    return this.name;
  }

  public Lock getPersonLock(){
    return this.person_lock;
  }

  public Condition getElevWaitingCond(){
    return this.waiting_for_elevator;
  }

}
