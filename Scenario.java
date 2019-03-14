import java.util.Random;


// Obtain a number between [0 - 49].
// Add 1 to the result to get a number from the required range
// (i.e., [1 - 50]).
public class Scenario{
  public static void main(String [] args){
    Random rand = new Random();
    int n = rand.nextInt(10);
    n += 1;
    Elevator elev1 = new Elevator("1");
    Person p1 = new Person("A", 1, 10);
  }
}
