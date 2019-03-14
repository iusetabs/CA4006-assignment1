import java.lang.*;
import java.util.Random;
import Person.*;


public class PeopleFactory {


  public static Person ranBuilder(){

    while (true){
      Random rand = new Random();
      for (int i = 0; i < peopleNum; i++){
        int cur_floor = rand.nextInt(10);
        int tar_floor  = rand.nextInt(10);
        Person person = new Person(cur_floor, tar_floor);
         //Add the new person to the elevator class!!!!!!
      }
    }
  }


  public static void main(String [] args){

  }
}
