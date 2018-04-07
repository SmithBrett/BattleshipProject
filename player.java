import java.util.*;
/*
 * Name: player.java
 * Author: Brett Smith
 * Ver: 1.1
 * Date: 4/7/2018
 * 
 * This is a simple class designed to handle all of the player specific entities in the battleship game
 * Also to allow access through the interface to other components in the program
 * 
 */
public class player 
{
	private String name;
	protected int ship_Remaining;
	//shipList contains the number of ship of the size
	//ex shipList ={1,1,2,1} contains 1 5x1, 1 4x1, 2 3x1, 1 2x1
	protected int[] shipList;
	protected int[] ship_length ={5,4,3,2};
	//Standard Grid sizes are 10x10 and 10x14
	//For target grid 0=empty 1=hit 2=miss
	protected int targetGrid[][];
	//For ship grid[x][y][0] 0=empty 1=ship hit 2=2x1 ship 3=3x1 ship 4=4x1 ship 5=5x1 ship
	protected int shipGrid[][][];
	
	//Constructors
	public player()
	{
		this("Player",5,10,10);
	}
	public player(String name)
	{
		this(name,5,10,10);
	}
	public player(String name,int ship_Remaining, int x,int y)
	{
		this.name=name;
		this.ship_Remaining=ship_Remaining;
		targetGrid = new int[x][y];
		shipGrid = new int[x][y][2];
	}
	
	//Getters/Setters
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}
	public void setShipRemaining(int ship_Remaining)
	{
		this.ship_Remaining=ship_Remaining;
	}
	public int getShip_Remaining()
	{
		return ship_Remaining;
	}
	public int[][] getTargetGrid()
	{
		return targetGrid;
	}
	public int[][][] getShipGrid()
	{
		return shipGrid;
	}
	public void setTargetGridCoord(int x,int y,int value)
	{
		targetGrid[x][y]=value;
	}
	public void setShipGridCoord(int x,int y,int value)
	{
		shipGrid[x][y][0]=value;
	}
	
	//Grid Clear
	public void clearTargetGrid()
	{	
		for(int i=0;i<targetGrid.length;i++)
		{
			Arrays.fill(targetGrid[i], 0);
		}
	}
	public void clearShipGrid()
	{	
		for(int i=0;i<shipGrid.length;i++)
		{
			Arrays.fill(shipGrid[i][0], 0);
		}
	}
	
	//this method updates the attacking player and the receiving players grids
	public void attack(player rPlayer,int x,int y)
	{
		if(rPlayer.checkHitMiss(x, y)==true)
		{
			targetGrid[x][y]=1;
			rPlayer.shipGrid[x][y][0]=2;
		}
		else
		{
			targetGrid[x][y]=2;
		}
	}
	//checks if ship is at the x y coordinate
	public boolean checkHitMiss(int x, int y)
	{
		if(shipGrid[x][y][0]>1 && shipGrid[x][y][0]<=5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public Vector<Integer> shipsDestroyed()
	{
		Vector<Integer> shipRemaining = new Vector<Integer>();
		Vector<Integer> shipDestroyed = new Vector<Integer>();
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			shipDestroyed.add(ship_length[i]+10*(j+1));
			//System.out.println("Ship Placed");
			//printGrid(0);
			}
		}
		for(int i=0;i<shipGrid.length;i++)
		{	
			
			for(int j=0;j<shipGrid[i].length;j++)
			{
				if(shipGrid[i][j][0]>1)
				{	
					if(!shipDestroyed.contains(shipGrid[i][j][0]+10*shipGrid[i][j][1]))
					{	
						shipRemaining.add(shipGrid[i][j][0]+10*shipGrid[i][j][1]);
						shipDestroyed.remove(shipGrid[i][j][0]+10*shipGrid[i][j][1]);
					}
				}
			}
		}
		ship_Remaining=shipRemaining.size();
		return shipDestroyed;
		
	}
	
}
