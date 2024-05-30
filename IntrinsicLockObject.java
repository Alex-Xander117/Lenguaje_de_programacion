public class IntrinsicLockObject {
	
	private int numLogins;
	private String loginName;
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	public IntrinsicLockObject()
	{
		numLogins = 0;
		loginName = "";
	}
	
	public void registerLogin(String loginName)
	{
		this.loginName = loginName;
		synchronized(this)
		{
			numLogins++;
		}
	}
	
	public void registerLogout()
	{
		loginName = "";
		synchronized(lock1) {
			numLogins --;
		}
	}
	
	public String getNumLogins()
	{
		String loginInfo = "num de logins:" + numLogins;
		synchronized(lock2)
		{
			return loginInfo;
		}
		
	}

}