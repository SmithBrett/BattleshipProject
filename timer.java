
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;

/*
 * Name: timer.java
 * Author: Brett Smith
 * Ver: 1.0
 * Date: 4/7/2018
 * 
 * This is a simple program that handle the timer functionality.
 */

public class timer
{
	private ScheduledThreadPoolExecutor timerThread;
	private ScheduledFuture<?> timerDecHandle;
	
	private volatile int count;
	private volatile boolean flag;
	
	//getters/setters
	public void setCount(int count)
	{
		this.count=count;
	}
	public int getCount()
	{
		return count;
	}
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
	public boolean getFlag()
	{
		return flag;
	}
	
	//initializes timer
	public void initTimer()
	{
		timerThread = new ScheduledThreadPoolExecutor(1);
		timerThread.setRemoveOnCancelPolicy(true);
		timerThread.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
	}
	//starts count down timer
	//timer has a 5 sec delay on start
	public void startTimer(int sec)
	{
		count=sec;
		Runnable timeDec = new Runnable(){public void run(){count--; /*System.out.println(count);*/}};
		timerDecHandle = timerThread.scheduleAtFixedRate(timeDec,5,1,SECONDS);
		timerThread.schedule(new Runnable(){public void run() {timerDecHandle.cancel(true);flag=true;}},sec+5-1,SECONDS);
		
	}
	//stops timer
	public void stopTimer()
	{
		timerDecHandle.cancel(true);
		timerThread.shutdownNow();
	}
}
