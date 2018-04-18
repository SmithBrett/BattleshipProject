//Adam Dingess - v0.1 - 4/17/18
//Gamehandler using console for demo
import java.util.*;

public class gamehandler {
	
	static Scanner console = new Scanner(System.in);
	   
	boolean manualplace = false;
	boolean placementDone = false;
	static boolean gameOver = false;
	
	public static void onePlayerGame(){
		Sound.stop();
		timer t = new timer();
		t.initTimer();		
		
		System.out.println("input player name");
		String input1 = console.nextLine();
	    player p1 = new player(input1);
			
		int input2 = 0;
		do {
			System.out.println("input difficulty: 1 or 2");
			input2 = console.nextInt();
			} while(input2 != 1 && input2 != 2);
		ai a = new ai(input2,5,10,10);
		a.initGrids();
		p1.rndmShipGrid();
//		System.out.println("player ship grid"); p1.printGrid(0);
//   	System.out.println("player target grid"); p1.printGrid(1);
//		System.out.println("ai ship grid"); a.printGrid(0);
//		System.out.println("ai target gird"); a.printGrid(1);;
		Random rand = new Random();
		int turn = rand.nextInt(2) + 1;

/*		do {
			t.startTimer(5); */
			do {
				if (turn ==1) { 
					System.out.println(" ");
					System.out.println(p1.getName()+"'s turn");
					System.out.println("Input x attack coordinate (0-9): ");
					int xcoord = console.nextInt();
					System.out.println("Input y attack coordinate (0-9):");
					int ycoord = console.nextInt();
					p1.attack(a, xcoord, ycoord);
					//	Sound.shotSound();
			
			
					if (a.checkHitMiss(xcoord, ycoord) == true){
						Sound.hitSound();
					} else 
					{ 
						Sound.missSound(); 
					}
					if (a.getShip_Remaining() == 0)
					{
						gameOver = true;
					} 
					p1.printGrid(0);
					p1.printGrid(1);
					turn = 0;
					System.out.println("Turn over");
					System.out.println(t.getCount());
				}
		//	} while (t.getCount() > 0);
			int timesup = t.getCount();
			if (timesup == 0 ){
				System.out.println("You took too long! Turn skipped");
				turn =0;
			}
		if (turn == 0) {
			System.out.println(" ");
			System.out.println("Opponent's turn...");
			a.attack(p1);
			
			{ 
				Sound.missSound(); 
			}
			if (a.getShip_Remaining() == 0)
			{
				gameOver = true;
			} 
			a.printGrid(0);
			a.printGrid(1);
			turn = 1;
			System.out.println("Turn over");
			}		
		
	}  while(gameOver == false);
		
		
		
	/*	if(manualplace == true) {
	  
	 
			/* code will probably need deleted (retarded way of doing things)
			  int k = p1.ship_Remaining;
				 for (int i = 0; i < p1.shipList.length; i++){
				       int temp1 = p1.shipList[i];
				       int j = 0;
				       int temp2 = 0;
				       do {
				       for (j=j; j < p1.ship_length.length; j++){
					        temp2 = p1.ship_length[i]; break;}
				            System.out.println(temp2+"  + "+temp1); 
				            temp1= temp1-1;
				            }
				       while(temp1>0);
				 p1.placeShip(temp2, temp1);}//
				 
			p1.rndmShipGrid();
			do{
			//p1.moveShip(oldx, oldy, x, y);
			} while(placementDone != true);}*/
		
		
	}
	
	public void twoPlayerGame(){
		
	}
	
	public void manualPlacement() {
		manualplace = true;
	}
	
	public void finishedPlacement(){
		placementDone = true;
	}
}

