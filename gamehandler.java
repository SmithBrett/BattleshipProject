import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class gameHandler {
	
	private static ScheduledThreadPoolExecutor playerSoundThread;
	private static ScheduledFuture<?> playerSoundHandle;
	private static ScheduledThreadPoolExecutor aiSoundThread;
	private static ScheduledFuture<?> aiSoundHandle;
	private static ScheduledThreadPoolExecutor timerThread;
	private static ScheduledFuture<?> timerHandle;
	
	static boolean hit = false;
	
	//creating player and ai
	//will need to ask for player name
	static player p1 = new player("Player1Test");
	// will need to ask for ai difficulty
	static ai a = new ai(2,5,10,10);
	
	static GUI g = new GUI();

	static String shipHit = "An enemy ship has been hit!";
	static String pshipHit = "One of your ships has been hit!";	
	static String shipSank = "An enemy ship has been sunk!";
	static String pshipSank = "One of your ships has been sank!";
	static String p1win = "All enemy ships destroyed. YOU WIN";
	static String p2win = "All your ships are destroyed. YOU LOSE";
	static String over = "GAME OVER";
	static String skippedturn = "You took too long, turn skipped";
	static String wait = "Wait for enemy to take turn...";
	
	static timer t = new timer(); 
	
	public static void main(String[] args) 
	{
	
		
		t.initTimer();
		t.startTimer(10);
		//initializing grids
		a.initGrids();
		p1.rndmShipGrid();
		
		//creating GUI

		g.disp();
		g.initPlayerInfo(p1, a);
		g.initTimerRefresh(t);
		g.colorAllFields(p1);
		g.updateScore();
		
		timerDetect();
		
	}
	public static void turn(int x, int y){
		int xcoord = (x*100+y)/100;
		int ycoord = (x*100+y)%100;
		t.stopTimer();
       
			//player turn
		
			p1.attack(a,xcoord,ycoord);
			System.out.println("player grid");
			p1.printGrid(0);
			p1.printGrid(1);
		//	g.colorAllFields(p1);
	
			Sound.shotSound();
			System.out.println(Sound.checkRunning());
			playerSound(xcoord,ycoord);
			
			System.out.println("ai ships remaining: "+a.getShip_Remaining());
			System.out.println("player ships remaining: "+p1.getShip_Remaining());
			
			//ai turn
			System.out.println("ai grid");
			g.setTurn(a);
			hit = a.attack(p1);
			a.printGrid(0);
			a.printGrid(1);
			System.out.println("player ships remaining: "+p1.getShip_Remaining());
         	aiSound();
			g.setTurn(p1);
			g.updateScore();
			
			//checking win conditions
			if (a.getShip_Remaining()==0){
				g.writeEvent(p1win);
				g.writeEvent(over);
				try {
				    Thread.sleep(1000);
					} 
				catch(InterruptedException ex) 
					{
				    Thread.currentThread().interrupt();
					}
				timerHandle.cancel(true);
				timerThread.shutdownNow();
				aiSoundThread.shutdownNow();
				playerSoundThread.shutdownNow();
				g.stopTimerRefresh();
				g.frameInVis();
			}
			if (p1.getShip_Remaining()==0){
				g.writeEvent(p2win);
				g.writeEvent(over);
				g.stopTimerRefresh();
				timerHandle.cancel(true);
				timerThread.shutdownNow();
				aiSoundThread.shutdownNow();
				playerSoundThread.shutdownNow();
				try {
				    Thread.sleep(1000);
					} 
				catch(InterruptedException ex) 
					{
				    Thread.currentThread().interrupt();
					}
				g.frameInVis();
				}
			
	}
	
	public static void mute(){
		Sound.mute();
	}
	
	public static void playerSound(int xcoord, int ycoord)
	{
		playerSoundThread = new ScheduledThreadPoolExecutor(1);
		playerSoundThread.setRemoveOnCancelPolicy(true);
		playerSoundThread.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		Runnable timeUpdate = new Runnable(){
			public void run()
			{
				if (a.getShipGrid_Value(xcoord, ycoord) == 1)
				{
					
					while(true){
						if (Sound.checkMute()==true){break;}
						if (Sound.checkRunning()==true) {}
						else {Sound.hitSound(); break;}
					}
					g.writeEvent(shipHit);
				} 
				else 
				{ 
					
					while(true) {
						if (Sound.checkMute()==true){break;}
						if (Sound.checkRunning()==true) {}
						else {Sound.missSound();  break;}
						}
					
				}
				g.colorTargetField(xcoord, ycoord, p1);
			}
		};
		playerSoundHandle = playerSoundThread.scheduleAtFixedRate(timeUpdate,2,999999999,SECONDS);
		
	}
	
	public static void aiSound()
	{
		aiSoundThread = new ScheduledThreadPoolExecutor(1);
		aiSoundThread.setRemoveOnCancelPolicy(true);
		aiSoundThread.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		Runnable timeUpdate = new Runnable(){
			public void run()
			{
				if (hit == true)
				{
					Sound.shotSound();
					while(true){
						if (Sound.checkMute()==true){break;}
						if (Sound.checkRunning()==true) {}
						else {Sound.hitSound(); break;}
					}
					g.writeEvent(pshipHit);
				} 
				else 
				{ 
				    Sound.shotSound();
					while(true) {
						if (Sound.checkMute()==true){break;}
						if (Sound.checkRunning()==true) {}
						else {Sound.missSound();  break;}
						}
					
				}
				g.colorAllFields(p1);
				t.stopTimer();
				t.initTimer();
				t.startTimer(30);
				timerDetect();
			}
		};
		aiSoundHandle = aiSoundThread.scheduleAtFixedRate(timeUpdate,5,999999999,SECONDS);
	}
	
	public static void timerDetect()
	{
		timerThread = new ScheduledThreadPoolExecutor(1);
		timerThread.setRemoveOnCancelPolicy(true);
		timerThread.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		Runnable timeUpdate = new Runnable(){
			public void run()
			{
				if (t.getCount()==0){
			
					g.setTurn(a);
					a.attack(p1);
					aiSound();
					g.setTurn(p1);
					g.updateScore();
				//	timerThread.shutdown();
					g.writeEvent(skippedturn);
					g.writeEvent(wait);
					System.out.println("Turn skipped");
					
				}
			}
		};
			timerHandle = timerThread.schedule(timeUpdate,15,SECONDS);
			System.out.println("timer detector started");
		
	}
	
	public static void surrender() {
		g.frameInVis();
		timerHandle.cancel(true);
		timerThread.shutdownNow();
		aiSoundThread.shutdownNow();
		playerSoundThread.shutdownNow();
	}
	

}