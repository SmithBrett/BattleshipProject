import java.util.Random;

public class GUITest {
	
	
	//creating player and ai
	//will need to ask for player name
	static player p1 = new player("Player1Test");
	// will need to ask for ai difficulty
	static ai a = new ai(2,5,10,10);
	
	static GUI g = new GUI();
	static int turn = 1;
	static String shipHit = "An enemy ship has been hit!";
	static String pshipHit = "One of your ships has been hit!";	
	static String shipSank = "An enemy ship has been sunk!";
	static String pshipSank = "One of your ships has been sank!";
	
	
	public static void main(String[] args) 
	{
		timer t = new timer(); 
		t.initTimer();
		

		
		//initializing grids
		a.initGrids();
		p1.rndmShipGrid();
		
		//creating GUI

		g.disp();
		g.initPlayerInfo(p1, a);
		g.initTimerRefresh(t);
		g.colorAllFields(p1);
		
		//Turn taking process
		//Random rand = new Random();
	  //  turn = rand.nextInt(2) + 1;
		
	}
	public static void turn(int x, int y){
		int xcoord = (x*100+y)/100;
		int ycoord = (x*100+y)%100;
		
			//player turn
			g.setTurn(p1);
			p1.attack(a,xcoord,ycoord);
			System.out.println("player grid");
			p1.printGrid(0);
			p1.printGrid(1);
			g.colorAllFields(p1);
		//	Sound.shotSound();
		/*	try {
			    Thread.sleep(750);
				} 
			catch(InterruptedException ex) 
				{
			    Thread.currentThread().interrupt();
				}*/
			if (a.getShipGrid_Value(xcoord, ycoord) == 1)
			{
		//		Sound.hitSound();
				g.writeEvent(shipHit);
			} 
			else 
			{ 
			//	Sound.missSound(); 
			}
			System.out.println(a.getShip_Remaining());
			System.out.println(p1.getShip_Remaining());
		//	System.out.println("Ships remaining: "+a.getShip_Remaining());
			//if (a.shipsDestroyed() == <0>){
				
			//}
			
			//ai turn
			System.out.println("ai grid");
			g.setTurn(a);
			a.attack(p1);
			a.printGrid(0);
			a.printGrid(1);
			System.out.println("player ships remaining: "+p1.getShip_Remaining());
			/*if (a.attack(p1) == true)
			{
				Sound.hitSound();
			} 
			else 
			{ 
				Sound.missSound(); 
			}*/
		
	}
	
	
}