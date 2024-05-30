public class SynchronizedCounter {
    private int c;	
	
	public SynchronizedCounter(){ 
		c = 0; 
	}
	
	public synchronized void increment(int val){
		c += val;
	}
	
	public synchronized void decrement(int val){
		c -= val;
	}
	
	public synchronized int getCounter(){
		return c;
	}

}
