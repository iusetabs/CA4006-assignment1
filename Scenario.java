import java.util.*;
import java.util.concurrent.*;


public class Scenario{


// Want to try make it work with Person.wait(n);

  //@param ms: the management system reference
  //@param num: the amount of people to make
    static ScheduledExecutorService PeepsFactory(ManagementSystem ms, int num, ScheduledExecutorService executor){
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
  }

  public static void main(String [] args){
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    ManagementSystem ms = new ManagementSystem();
    ms.addElevator("test", new Elevator("test", 0, 10));
    //ms.addElevator("test1", new Elevator("test1", 1, 8)); // This elevator is never call in requestElevator();
    int num = 3;
    for (int i = 0; i < num; i++){
      Random rand = new Random();
      int cur_floor = -1;
      int tar_floor = -1;
      while (cur_floor == tar_floor){ // can't have person going to the same floor
        cur_floor = rand.nextInt(10); //will generate 0. The max floor is 9. The min is 0.
        tar_floor  = rand.nextInt(10);
      }
      Person person = new Person(Math.abs(cur_floor), Math.abs(tar_floor), i, ms);
      executor.schedule(person, Math.abs(rand.nextInt(5)) , TimeUnit.SECONDS);
    }
    try {
          executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
          e.printStackTrace();
    }

   executor.shutdown();
  }
}
