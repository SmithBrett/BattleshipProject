import java.util.*;

public class ai extends player 
{
	private int diff=1;
	private boolean ready=false;
	
	
	private int e_shipList[];
	private Vector<Integer> e_shipDestroyed = new Vector<Integer>();
	//Standard Grid sizes are 10x10 and 10x14
	//For target grid 0=empty 1=hit 2=miss
	//private int targetGrid[][];
	//For ship grid 0=empty 1=ship 2=ship hit
	//private int shipGrid[][];
	private ArrayList<Integer> attackEffectList=new ArrayList<Integer>(20);
	private ArrayList<Integer> attackCoordList=new ArrayList<Integer>(20);
	
	public ai()
	{	
		super("AI");
		this.diff=1;	
	}
	public ai(int diff,int ship_Remaining,int x,int y)
	{
		super("AI",ship_Remaining,x,y);
		this.diff=diff;
	}
	
	public int getDiff()
	{
		return diff;
	}
	public void setDiff(int diff)
	{
		this.diff=diff;
	}
	public boolean getReady()
	{
		return ready;
	}
	//attack algorithm
	public void attack(player rPlayer)
	{
		if(diff==1)
		{	
			Random rndm = new Random(System.nanoTime());
			int x=rndm.nextInt(shipGrid.length);
			int y=rndm.nextInt(shipGrid[0].length);
			while (targetGrid[x][y]!=0)
			{
				x=rndm.nextInt(shipGrid.length);
				y=rndm.nextInt(shipGrid[0].length);
			}
			if(rPlayer.checkHitMiss(x, y)==true)
			{
				targetGrid[x][y]=1;
				attackCoordList.add(x*100+y);
				attackEffectList.add(1);
				Vector<Integer> temp=rPlayer.shipsDestroyed();
				for(int i=0;i<temp.size();i++)
				{
					if(!e_shipDestroyed.contains(temp.elementAt(i)))
					{
						e_shipDestroyed.add(temp.elementAt(i));
						shipList[temp.elementAt(i)%10-2]--;
					}
				}
			}
			else
			{
				targetGrid[x][y]=2;
				attackCoordList.add(x*100+y);
				attackEffectList.add(2);
			}
			
		}
	}
	//Places standard ship config
	public void initGrids()
	{	
		ready=false;
		clearShipGrid();
		clearTargetGrid();
		shipList = new int[] {1,1,2,1};
		e_shipList = new int[] {1,1,2,1};
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			placeShip(ship_length[i],j+1);
			//System.out.println("Ship Placed");
			//printGrid(0);
			}
		}
		ready=true;
	}
	//Places custom ship config
	public void initShipGrid(int [] shipList)
	{	
		this.shipList=shipList;
		this.e_shipList=shipList;
		for(int i=0;i<shipList.length;i++)
		{
			for(int j=0;j<shipList[i];j++)
			{
			placeShip(ship_length[i],shipList[i]+1);
			//System.out.println("Ship Placed");
			//printGrid(0);
			}
		}
		ready=true;
	}
	//Algorithm for randomly placing ships
	private void placeShip(int ship_len,int ship_num)
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
						shipGrid[x-i][y][1]=ship_num;
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
						shipGrid[x][y-i][1]=ship_num;
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
						shipGrid[x+i][y][1]=ship_num;
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
						shipGrid[x][y+i][1]=ship_num;
					}
					placed=true;
				}
				break;
			default:
				break;
			}
		}
		
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
					System.out.print(shipGrid[j][i][0]);
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
					System.out.print(targetGrid[j][i]);
				}
				System.out.print('\n');
			}
			System.out.print('\n');
		}
	}
}
