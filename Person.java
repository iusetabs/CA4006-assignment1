import java.lang.*;
import java.util.java.util.concurrent.*;

public class Person implements Runnable {

  private int cur_floor;
  private int tar_floor;
  private String name;
  private Boolean in_elevator;
  private ConcurrentHashMap<Integer, BlockingQueue<Person>> airport_map;
  final Lock lock = new ReentrantLock();
  public final Condition waiting_for_elevator = new lock.newCondition();
  public final Condition waiting_to_get_out = new lock.newCondition();
  Elevator the_elevator; 


  /*------------CONSTRUCTORS----------------*/

  Person(){}

  Person(int cur_floor, int tar_floor, int i, ConcurrentHashMap<Integer, BlockingQueue<Person>> map, Elevator e){
    //this.pid = pid;
    this.cur_floor = cur_floor;
    this.tar_floor = tar_floor;
    this.in_elevator = false;
    this.airport_map = map;
    this.the_elevator = e;
    this.name = "Person_" + Integer.toString(i);
  }
  /*-----------END CONSTRUCTORS------------*/

  public void run() {
      this.button_press();
      //lock.lock();
    //  try{
        this.before_elevator();
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

  private before_elevator(){
      this.waiting_for_elevator.await();
  }
  private elevating(){
     this.waiting_to_get_out.await();
  }
  private button_press(){
    this.waiting_Q.get(this.getCur_floor()).put(this);
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

//   public void start () {
//      System.out.println("Starting ");
//      if (t == null) {
//         t = new Thread (this);
//         t.start ();
//      }
//   }

}
