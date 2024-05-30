public class AtomicAccessSynch {
	
	private volatile int c;
	private volatile int c2;
	private int c3;
	
	public AtomicAccessSynch()
	{
		c = 0;
	}
	
	public  void increment(int val){
		c += val;
	}
	
	public  void decrement(int val){
		c -= val;
	}
	
	public  int getCounter(){
		return c;
	}

}