public class elevator extends Thread();
{

    // 10 floor 0 - 9
    // Will make Evlevator starting random later
    // active is a way to make sure it's not in use, need? TBD.
    private int current_floor = 0;
    private boolean active = false;

    // Needed for Thread class 
    // e.g elevator p = new elevator ();
    // p.start ();
    public void run ( )
	{
		System.out.println (“Hello from the thread”);
	}

    public void ArrivingGoingFromTo(int atFloor, int toFloor){

    }

}
