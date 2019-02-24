public class Person {

  private Thread t;
  private String pid;
  private int cur_floor;
  private int tar_floor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

  public void run() {

  }

   public void start () {
      System.out.println("Starting " +  pid );
      if (t == null) {
         t = new Thread (this, pid);
         t.start ();
      }
   }
}
