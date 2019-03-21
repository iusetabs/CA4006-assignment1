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
  public final Condition waiting_to_get_out = lock.newCondition();
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
      System.out.println("Hello from " + this.getPersonName());
      this.button_press();
      //lock.lock();
    //  try{
        this.before_elevator();
        System.out.println("I'm getting in " + this.getPersonName());
        //Getting into elevator
        this.in_elevator = true;
        this.elevating();
        //getting out of elevator
        this.in_elevator = false;
  //   }
    // finally{
       //lock.unlock();
  //   }
    }

  private void before_elevator(){
    try{
      this.waiting_for_elevator.await();
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
  }
  private void elevating(){
    try{
     this.waiting_to_get_out.await();
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
  }
  private void button_press(){
    try{
      this.airport_map.get(this.getCur_floor()).put(this);
      this.floor_waiting_queue.put(this.getCur_floor()); //will wake up elevator if nobody on it.
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

}
