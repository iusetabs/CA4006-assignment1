import java.lang.*;

public class Person extends Thread {

  private Thread t;
  private int cur_floor;
  private int tar_floor;
  private String name;

  Person(){}

  Person(int cur_floor, int tar_floor){
    //this.pid = pid;
    this.cur_floor = cur_floor;
    this.tar_floor = tar_floor;
  }

  Person(int cur_floor, int tar_floor, int n){
    //this.pid = pid;
    this.cur_floor = cur_floor;
    this.tar_floor = tar_floor;
    this.name = "bitch_number_" + Integer.toString(n);
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

  public void run() {
    System.out.println("Hi");
  }

   public void start () {
      System.out.println("Starting ");
      if (t == null) {
         t = new Thread (this);
         t.start ();
      }
   }

}
