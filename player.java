import java.util.*;
/*
 * Name: player.java
 * Author: Brett Smith
 * Ver: 1.0
 * Date: 4/2/2018
 * 
 * This is a simple class designed to handle all of the player specific entities in the battleship game
 * Also to allow access through the interface to other components in the program
 * 
 */
public class player 
{
	private String name;
	private int ship_Remaining;
	
	//Standard Grid sizes are 10x10 and 10x14
	//For target grid 0=empty 1=hit 2=miss
	private int targetGrid[][];
	//For ship grid 0=empty 1=ship 2=ship hit
	private int shipGrid[][];
	
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
		shipGrid = new int[x][y];
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
	public int[][] getShipGrid()
	{
		return shipGrid;
	}
	public void setTargetGridCoord(int x,int y,int value)
	{
		targetGrid[x][y]=value;
	}
	public void setShipGridCoord(int x,int y,int value)
	{
		shipGrid[x][y]=value;
	}
	
	//Grid Clear
	public void clearTargetGrid()
	{
		Arrays.fill(targetGrid, 0);
	}
	public void clearShipGrid()
	{
		Arrays.fill(shipGrid, 0);
	}
	
	//this method updates the attacking player and the receiving players grids
	public void attack(player rPlayer,int x,int y)
	{
		if(rPlayer.checkHitMiss(x, y)==true)
		{
			targetGrid[x][y]=1;
			rPlayer.shipGrid[x][y]=2;
		}
		else
		{
			targetGrid[x][y]=2;
		}
	}
	//checks if ship is at the x y coordinate
	public boolean checkHitMiss(int x, int y)
	{
		if(shipGrid[x][y]==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
