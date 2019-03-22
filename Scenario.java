import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

public class Scenario{


// Want to try make it work with Person.wait(n);

  //@param ms: the management system reference
  //@param num: the amount of people to make
  /*  static ScheduledExecutorService PeepsFactory(ManagementSystem ms, int num, ScheduledExecutorService executor){
      for (int i = 0; i < num; i++){
        Random rand = new Random();
        int cur_floor = -1;
        int tar_floor = -1;
        while (cur_floor == tar_floor){ // can't have person going to the same floor
          cur_floor = rand.nextInt(10); //will generate 0. The max floor is 9. The min is 0.
          tar_floor  = rand.nextInt(10);
        }
        Person person = new Person(Math.abs(cur_floor), Math.abs(tar_floor), i, ms);
        executor.schedule(person, Math.abs(rand.nextInt(15)) , TimeUnit.SECONDS);
      }
      return executor;
  }*/

  public static void main(String [] args){
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    ConcurrentHashMap<Integer, BlockingQueue<Person>> waiting_Q = new ConcurrentHashMap<>();
    BlockingQueue<Integer> floor_requests = new LinkedBlockingQueue<Integer>();
    BlockingQueue<Integer> floor_getoff_queue = new LinkedBlockingQueue<Integer>();

    for (int i = 0; i < 10; i++){
      BlockingQueue<Person> bq = new LinkedBlockingQueue<>();
      waiting_Q.put(i, bq);
    }
    Elevator the_elev = new Elevator("elev_1", waiting_Q, floor_requests, floor_getoff_queue);
    new Thread(the_elev).start(); //Start elevator thread.
    //TODO ManagementSystem ms = new ManagementSystem();
    //TODO ms.addElevator("test", new Elevator("test", 0, 10));
    //TODO ms.addElevator("test1", new Elevator("test1", 1, 8)); // This elevator is never call in requestElevator();
  /*  for (int i = 0; i < 2; i++){
      Random rand = new Random();
      int cur_floor = -1;
      int tar_floor = -1;
      while (cur_floor == tar_floor){ // can't have person going to the same floor
        cur_floor = rand.nextInt(10); //will generate 0. The max floor is 9. The min is 0.
        tar_floor  = rand.nextInt(10);
      }
      Person person = new Person(Math.abs(cur_floor), Math.abs(tar_floor), i, waiting_Q, the_elev, floor_requests);*/
      Person [] peeps = {
          new Person(Math.abs(9), Math.abs(2), 0, waiting_Q, the_elev, floor_requests, floor_getoff_queue),
          new Person(Math.abs(7), Math.abs(2), 1, waiting_Q, the_elev, floor_requests, floor_getoff_queue),
          new Person(Math.abs(5), Math.abs(2), 2, waiting_Q, the_elev, floor_requests, floor_getoff_queue),
          new Person(Math.abs(1), Math.abs(2), 3, waiting_Q, the_elev, floor_requests, floor_getoff_queue),
          new Person(Math.abs(2), Math.abs(2), 4, waiting_Q, the_elev, floor_requests, floor_getoff_queue),

      };
      Random rand = new Random();
      for (Person person : peeps){
        executor.schedule(person, Math.abs(rand.nextInt(5)+1) , TimeUnit.SECONDS);
      }
  //  }
    try {
          executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
          e.printStackTrace();
    }
   executor.shutdown();
  }
}
