
import java.util.*;
/*
 * Name: player.java
 * Author: Brett Smith
 * Ver: 1.2
 * Date: 4/13/2018
 * 
 * This is a simple class designed to handle all of the player specific entities in the battleship game
 * Also to allow access through the interface to other components in the program
 * 
 */
public class player 
{
	protected boolean ready;
	private String name;
	protected int ship_Remaining;
	//shipList contains the number of ship of the size
	//ex shipList ={1,1,2,1} contains 1 5x1, 1 4x1, 2 3x1, 1 2x1
	protected int[] shipList = {1,1,2,1};
	protected int[] shipListT = {1,1,2,1};
	protected int[] ship_length ={5,4,3,2};
	protected int e_shipList[];
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
	public int getTargetGrid_Value(int x, int y)
	{
		return targetGrid[x][y];
	}
	public int getShipGrid_Value(int x, int y)
	{
		return shipGrid[x][y][0];
	}
	public void setTargetGridCoord(int x,int y,int value)
	{
		targetGrid[x][y]=value;
	}
	public void setShipGridCoord(int x,int y,int value,int id)
	{
		shipGrid[x][y][0]=value;
		shipGrid[x][y][1]=id;
	}
	public boolean getReady()
	{
		return ready;
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
			for(int j=0;j<shipGrid[0].length;j++)
			{
				Arrays.fill(shipGrid[i][j], 0);
			}
		}
	}
	
	//this method updates the attacking player and the receiving players grids
	public boolean attack(player rPlayer,int x,int y)
	{
		if(rPlayer.checkHitMiss(x, y)==true)
		{
			targetGrid[x][y]=1;
			rPlayer.shipsDestroyed();
			return true;
		}
		else
		{
			targetGrid[x][y]=2;
			return false;
		}
	}
	//checks if ship is at the x y coordinate
	public boolean checkHitMiss(int x, int y)
	{
		if(shipGrid[x][y][0]>1 && shipGrid[x][y][0]<=5)
		{
			shipGrid[x][y][0]=1;
			return true;
		}
		else
		{
			return false;
		}
	}
	//get ships destroyed
	public Vector<Integer> shipsDestroyed()
	{
		Vector<Integer> shipRemaining = new Vector<Integer>();
		Vector<Integer> shipDestroyed = new Vector<Integer>();
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			shipDestroyed.add(ship_length[i]+10*(j+1));
			//System.out.println("Ship Added " +(ship_length[i]+10*(j+1)));
			//printGrid(0);
			}
		}
		for(int i=0;i<shipGrid.length;i++)
		{	
			
			for(int j=0;j<shipGrid[0].length;j++)
			{
				if(shipGrid[i][j][0]>1)
				{	
					if(shipDestroyed.contains(Integer.valueOf(shipGrid[i][j][1])))
					{	
						//System.out.println("+1 to shipRemaining "+shipGrid[i][j][1]);
						shipRemaining.add(shipGrid[i][j][1]);
						shipDestroyed.remove(Integer.valueOf(shipGrid[i][j][1]));
					}
					
				}
			}
		}
		Arrays.fill(shipListT,0);
		for(int i=0; i<shipRemaining.size();i++)
		{
			shipListT[5-(shipRemaining.get(i))%10]++;
		}
		//System.out.println("Ships"+ship_Remaining);
		ship_Remaining=shipRemaining.size();
		//System.out.println("Ships" +ship_Remaining);
		return shipDestroyed;
		
	}
	
	//Random shipGrid
	public void rndmShipGrid()
	{
		clearShipGrid();
		clearTargetGrid();
		shipList = new int[] {1,1,2,1};
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			placeShip(ship_length[i],j+1);
			//System.out.println("Ship Placed");
			//printGrid(0);
			}
		}
	}
	public void rndmShipGrid(int [] shipList)
	{
		clearShipGrid();
		clearTargetGrid();
		this.shipList = shipList;
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			placeShip(ship_length[i],j+1);
			//System.out.println("Ship Placed");
			//printGrid(0);
			}
		}
	}
	//Algorithm for randomly placing ships
	protected void placeShip(int ship_len,int ship_num)
	{
		Random rndm = new Random(System.nanoTime());
		int orient;
		int N,S,E,W;
		int x=0;
		int y=0;
		boolean placed =false;
		ArrayList<Integer> orientList= new ArrayList<Integer>(4);
		while(orientList.isEmpty()==true)
		{
			N=S=E=W=0;
			x=rndm.nextInt(shipGrid.length);
			y=rndm.nextInt(shipGrid[0].length);
			//System.out.println(x);
			//System.out.println(y);
			for(int i=0;i<ship_len;i++)
			{
				if(x-ship_len>=0)
				{
					if(shipGrid[x-i][y][0]!=0)
					{
					}
					else
					{
						W++;
						if(W==ship_len)
						{
							orientList.add(0);
						}	
					}
				}
				if(y-ship_len>=0)
					{
					if(shipGrid[x][y-i][0]!=0)
					{
					}
					else
					{
						N++;
						if(N==ship_len)
						{
							orientList.add(1);
						}
					}
				}
				if(x+ship_len<=shipGrid.length)
					{
					if(shipGrid[x+i][y][0]!=0)
					{
					}
					else
					{
						E++;
						if(E==ship_len)
						{
							orientList.add(2);
						}
					}
				}
				if(y+ship_len<=shipGrid[0].length)
				{
					if(shipGrid[x][y+i][0]!=0)
					{
					}
					else
					{
						S++;
						if(S==ship_len)
						{
							orientList.add(3);
						}
					}
				}
			}
			
		}
		while(placed==false)
		{
			orient=orientList.get(rndm.nextInt(orientList.size()));
			switch(orient)
			{
			case 0:
				if(x-ship_len>=0)
				{	
					for(int i=0;i<ship_len;i++)
					{	
						shipGrid[x-i][y][0]=ship_len;
						shipGrid[x-i][y][1]=ship_num*10+ship_len;
					}
					placed=true;
				}
				break;
			case 1:
				if(y-ship_len>=0)
				{
					for(int i=0;i<ship_len;i++)
					{	
						shipGrid[x][y-i][0]=ship_len;
						shipGrid[x][y-i][1]=ship_num*10+ship_len;
					}
					placed=true;
				}
				break;
			case 2:
				if(x+ship_len<=shipGrid.length)
				{
					for(int i=0;i<ship_len;i++)
					{
						shipGrid[x+i][y][0]=ship_len;
						shipGrid[x+i][y][1]=ship_num*10+ship_len;
					}
					placed=true;
				}
				break;
			case 3:
				if(y+ship_len<=shipGrid[0].length)
				{
					for(int i=0;i<ship_len;i++)
					{	
						shipGrid[x][y+i][0]=ship_len;
						shipGrid[x][y+i][1]=ship_num*10+ship_len;
					}
					placed=true;
				}
				break;
			default:
				break;
			}
		}
		
	}
	//Moves ship on grid
	public void moveShip(int oldx,int oldy,int x,int y)
	{	
		ready=false;
		//shipId = ship_num *10 + ship_len
		int shipId = shipGrid[oldx][oldy][1];
		
		//printGrid(0);
		int ship_len=shipId%10;
		int orient=-1;
		int N,S,E,W;
		boolean elx,ely,ehx,ehy;
		elx=ely=ehx=ehy=false;
		ArrayList<Integer> orientList= new ArrayList<Integer>(4);
		if(shipId==0)
		{
			ready=true;
			return;
		}
			N=S=E=W=0;
			for(int i=0;i<ship_len;i++)
			{
				if(x-ship_len>=0)
				{
					if(shipGrid[x-i][y][0]!=0 && shipGrid[x-i][y][1]!=shipId)
					{
						
					}
					else
					{
						W++;
						if(W==ship_len)
						{
							orientList.add(0);
						}	
					}
				}
				if(y-ship_len>=0)
					{
					if(shipGrid[x][y-i][0]!=0 && shipGrid[x][y-i][1]!=shipId)
					{
					}
					else
					{
						N++;
						if(N==ship_len)
						{
							orientList.add(1);
						}
					}
				}
				if(x+ship_len<=shipGrid.length)
					{
					if(shipGrid[x+i][y][0]!=0 && shipGrid[x+i][y][1]!=shipId)
					{
					}
					else
					{
						E++;
						if(E==ship_len)
						{
							orientList.add(2);
						}
					}
				}
				if(y+ship_len<=shipGrid[0].length)
				{
					if(shipGrid[x][y+i][0]!=0 && shipGrid[x][y+i][1]!=shipId)
					{
					}
					else
					{
						S++;
						if(S==ship_len)
						{
							orientList.add(3);
						}
					}
				}
			}
		if(oldx==x && oldy==y)
		{
			if(orientList.isEmpty())
			{
				//System.out.println("Ship does not fit");
				ready=true;
				return;
			}
			
			if(oldx-1<0)
			{
				elx=true;
			}
			if(oldy-1<0)
			{
				ely=true;
			}
			if(oldx+1>shipGrid.length-1)
			{
				ehx=true;
			}
			if(oldy+1>shipGrid[0].length-1)
			{
				ehy=true;
			}
			if(!elx )
			{
					if(shipGrid[x-1][y][1]==shipId)
					{
						if(orientList.contains(Integer.valueOf(1)))
						{
							//System.out.println("orient");
							orient=1;
						}
						else if(orientList.contains(Integer.valueOf(2)))
						{
							//System.out.println("orient");
							orient=2;
						}
						else if(orientList.contains(Integer.valueOf(3)))
						{
							//System.out.println("orient");
							orient=3;
						}
					}
			}
				if(!ely)
				{
					if(shipGrid[x][y-1][1]==shipId)
					{
						if(orientList.contains(Integer.valueOf(2)))
						{
							//System.out.println("orient");
							orient=2;
						}
						else if(orientList.contains(Integer.valueOf(3)))
						{
							//System.out.println("orient");
							orient=3;
						}
						else if(orientList.contains(Integer.valueOf(0)))
						{
							//System.out.println("orient");
							orient=0;
						}
					}
				}
				if(!ehx)
				{
					
					if(shipGrid[x+1][y][1]==shipId)
					{
						if(orientList.contains(Integer.valueOf(3)))
						{
							//System.out.println("orient");
							orient=3;
						}
						else if(orientList.contains(Integer.valueOf(0)))
						{
							//System.out.println("orient");
							orient=0;
						}
						else if(orientList.contains(Integer.valueOf(1)))
						{
							//System.out.println("orient");
							orient=1;
						}
					}
				}
				if(!ehy)
				{
					
					if(shipGrid[x][y+1][1]==shipId)
					{
						if(orientList.contains(Integer.valueOf(0)))
						{
							//System.out.println("orient");
							orient=0;
						}
						else if(orientList.contains(Integer.valueOf(1)))
						{
							//System.out.println("orient");
							orient=1;
						}
						else if(orientList.contains(Integer.valueOf(2)))
						{
							//System.out.println("orient");
							orient=2;
						}
					}
				}
				
				
				
			if(orient>-1)
			{
			//Removes ship from grid
			for(int i=0;i<shipGrid.length;i++)
			{
				if(shipGrid[oldx][i][1]==shipId)
				{
					shipGrid[oldx][i][1]=0;
					shipGrid[oldx][i][0]=0;
				}
			}
			for(int i=0;i<shipGrid[0].length;i++)
			{
				if(shipGrid[i][oldy][1]==shipId)
				{
					shipGrid[i][oldy][1]=0;
					shipGrid[i][oldy][0]=0;
				}
			}
			}
		}
		else
		{
			if(orientList.isEmpty())
			{
				//System.out.println("ship does not fit");
				ready=true;
				return;
			}
			//Removes ship from grid
			for(int i=0;i<shipGrid.length;i++)
			{
				if(shipGrid[oldx][i][1]==shipId)
				{
					shipGrid[oldx][i][1]=0;
					shipGrid[oldx][i][0]=0;
				}
			}
			for(int i=0;i<shipGrid[0].length;i++)
			{
				if(shipGrid[i][oldy][1]==shipId)
				{
					shipGrid[i][oldy][1]=0;
					shipGrid[i][oldy][0]=0;
				}
			}
			orient=orientList.get(0);
			
		}
		//places ship on grid
		//System.out.println(orient);
		switch(orient)
		{
		case 0:
			if(x-ship_len>=0)
			{	
				for(int i=0;i<ship_len;i++)
				{	
					shipGrid[x-i][y][0]=ship_len;
					shipGrid[x-i][y][1]=shipId;
				}
			}
			break;
		case 1:
			if(y-ship_len>=0)
			{
				for(int i=0;i<ship_len;i++)
				{	
					shipGrid[x][y-i][0]=ship_len;
					shipGrid[x][y-i][1]=shipId;
				}
			}
			break;
		case 2:
			if(x+ship_len<=shipGrid.length)
			{
				for(int i=0;i<ship_len;i++)
				{
					shipGrid[x+i][y][0]=ship_len;
					shipGrid[x+i][y][1]=shipId;
				}
			}
			break;
		case 3:
			if(y+ship_len<=shipGrid[0].length)
			{
				for(int i=0;i<ship_len;i++)
				{	
					shipGrid[x][y+i][0]=ship_len;
					shipGrid[x][y+i][1]=shipId;
				}
			}
			break;
		default:
			break;
		}
		ready=true;
	}
	//0-print shipGrid 1-print targetGrid
	public void printGrid(int num)
	{	
		if(num==0)
		{
			for(int j=0;j<shipGrid.length;j++)
			{
				for(int i=0;i<shipGrid[j].length;i++)
				{
					System.out.print(shipGrid[i][j][0]);
				}
				System.out.print('\n');
			}
			System.out.print('\n');
		}
		if(num==1)
		{
			for(int j=0;j<targetGrid.length;j++)
			{
				for(int i=0;i<targetGrid[j].length;i++)
				{
					System.out.print(targetGrid[i][j]);
				}
				System.out.print('\n');
			}
			System.out.print('\n');
		}
	
	}
}
