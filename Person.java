import java.lang.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Person implements Runnable {

  private int cur_floor;
  private int tar_floor;
  private String name;
  private Boolean in_elevator;
  private ConcurrentHashMap<Integer, BlockingQueue<Person>> airport_map;
  final Lock person_lock = new ReentrantLock();
  public final Condition waiting_for_elevator = person_lock.newCondition();
  public final Condition waiting_to_get_out = person_lock.newCondition();
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
      this.in_elevator = false;
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
    finally{
      this.person_lock.unlock();

    }
  }
  private void elevating(){
    this.person_lock.lock();
    try{
     this.waiting_to_get_out.await();
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
    finally{
      this.person_lock.unlock();
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
