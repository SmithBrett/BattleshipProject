//Adam Dingess - v0.2 - 4/18/18
//Gamehandler using console for demo
//Known problems - ai check hit miss check not displaying true when hit occurs
import java.util.*;

public class gamehandler {
	
	static Scanner console = new Scanner(System.in);
	   
	boolean manualplace = false;
	boolean placementDone = false;
	static boolean gameOver = false;
	static boolean turnOver = false;
	
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
		turn = 1;
		 
			int xcoord = 0;
			int ycoord = 0;
			int pshipsRem = 5;
			int aishipsRem = 5;
			t.startTimer(30); 
			do {
				//Thread test = new Thread();
				if (turn == 1) { 
					//while (turnOver == false) {
					
					System.out.println(" ");
					System.out.println(p1.getName()+"'s turn");
					while (true) {
					try {
						System.out.println("Input x attack coordinate (0-9): ");
						 ycoord = console.nextInt();
						 System.out.println("Input y attack coordinate (0-9):");
						 xcoord = console.nextInt();
						 p1.attack(a, xcoord, ycoord);
						 break;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("invalid coordinate: "+e.getMessage());
						System.out.println("Please enter a valid x and y coordinate");
						System.out.println("");
					}catch (InputMismatchException e) {
						System.out.println("invalid coordinate: "+e.getMessage());
						System.out.println("Please enter a valid x and y coordinate");
						System.out.println("");
						}
					}
						Sound.shotSound();
						try {
						    Thread.sleep(1500);
							} 
						catch(InterruptedException ex) 
							{
						    Thread.currentThread().interrupt();
							}
			
					
					if (a.checkHitMiss(xcoord, ycoord) == true){
						Sound.hitSound();
						System.out.println("--------------------------------------");
						System.out.println("--------ENEMY ship has been hit!------");
						System.out.println("--------------------------------------");
					} else 
					{ 
				//		System.out.println(a.checkHitMiss(xcoord, ycoord));
						Sound.missSound(); 
					}
					aishipsRem = a.getShip_Remaining();
					System.out.println("----------------------------------------");
					System.out.println(aishipsRem+" enemy ships remaining");
					System.out.println("----------------------------------------");
					if (aishipsRem == 0)
					{
						gameOver = true;
					} 
					t.stopTimer();
					turn = 0; 
					System.out.println("Player Ship Grid ");
					p1.printGrid(0);
					System.out.println("Player attack Grid ");
					p1.printGrid(1);
					
				}
		if (turn == 0) {
			try {
			    Thread.sleep(2500);
			} 
			catch(InterruptedException ex) 
			{
			    Thread.currentThread().interrupt();
			}
			Sound.shotSound();
			try {
			    Thread.sleep(1500);
			} 
			catch(InterruptedException ex) 
			{
			    Thread.currentThread().interrupt();
			}
			System.out.println(" ");
			System.out.println("Opponent's turn...");
			if (a.attack(p1) == true){
				System.out.println("----------------------------------------");
				System.out.println("---------YOUR ship has been hit!--------");
				System.out.println("----------------------------------------");
				Sound.hitSound();
			} else 
			{ 
				Sound.missSound(); 
			}
			pshipsRem = p1.getShip_Remaining();
			System.out.println(pshipsRem+" player ships remaining");
			if (pshipsRem == 0)
			{
				gameOver = true;
			} 
			a.printGrid(0);
			a.printGrid(1);
			turn = 1;
			turnOver = false;
			System.out.println("Turn over");
			}		
		
	}  while(gameOver == false);
		
			if (aishipsRem == 0) {
				System.out.println("YOU WIN");
			}
			else {System.out.println("YOU LOSE");}
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

