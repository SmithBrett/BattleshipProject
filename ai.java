import java.util.*;

public class ai extends player 
{
	private int diff=1;
	private boolean ready=false;
	
	//Target Lists
	private ArrayList<Integer> targets = new ArrayList<Integer>(50);
	private Stack<Integer> priorityTargets=new Stack<Integer>();
	//Parity
	//private boolean parity = true;
	private int parity_size=2;
	//Enemy ship list
	private int e_shipList[];
	private Vector<Integer> e_shipDestroyed = new Vector<Integer>();
	
	//Standard Grid sizes are 10x10 and 10x14
	//For target grid 0=empty 1=hit 2=miss
	//private int targetGrid[][];
	//For ship grid 0=empty 1=ship 2=ship hit
	//private int shipGrid[][];
	
	//Attack history
	private ArrayList<Integer> attackEffectList=new ArrayList<Integer>(20);
	private ArrayList<Integer> attackCoordList=new ArrayList<Integer>(20);
	
	//Constructors
	public ai()
	{	
		this(1,5,10,10);
	}
	public ai(int diff,int ship_Remaining,int x,int y)
	{
		super("AI",ship_Remaining,x,y);
		this.diff=diff;
		initTargets();
	}
	
	//getters/setters
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
	public boolean attack(player rPlayer)
	{
		boolean hitMissFlag=false;
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
				hitMissFlag=true;
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
		else if(diff==2)
		{
			hitMissFlag=diff2_Attack(rPlayer);
		}
		return hitMissFlag;
	}
	//diff2 attack algorithm
	private boolean diff2_Attack(player rPlayer)
	{	
		Random rndm = new Random(System.nanoTime());
		boolean hitMissFlag=false;
		int x=0;
		int y=0;
		int coor=0;
		//hunts for targets
		if(priorityTargets.isEmpty())
		{
			coor=targets.get(rndm.nextInt(targets.size()));
			//System.out.println(coor);
			targets.remove(Integer.valueOf(coor));
			x=coor/100;
			y=coor%100;
			
		}
		//Finishes found targets
		else
		{
			coor=priorityTargets.pop();
			x=coor/100;
			y=coor%100;
		}
		//Updates grids
		if(rPlayer.checkHitMiss(x, y)==true)
		{ 
			hitMissFlag=true;
			targetGrid[x][y]=1;
			//adds priority targets
			//removes priority targets form targets list if present
			if(x-1>=0 && targetGrid[x-1][y]==0)
			{
				if(priorityTargets.search((x-1)*100+y)<0)
				{
					priorityTargets.push((x-1)*100+y);
					if(targets.contains((x+1)*100+y))
					{
						targets.remove(Integer.valueOf((x+1)*100+y));
					}
				}
			}
			if(x+1<shipGrid.length && targetGrid[x+1][y]==0)
			{
				if(priorityTargets.search((x+1)*100+y)<0)
				{
					priorityTargets.push((x+1)*100+y);
					if(targets.contains((x+1)*100+y))
					{
						targets.remove(Integer.valueOf((x+1)*100+y));
					}
				}
			}
			if(y-1>=0 && targetGrid[x][y-1]==0)
			{
				if(priorityTargets.search(x*100+(y-1))<0)
				{
					priorityTargets.push(x*100+(y-1));
					if(targets.contains(x*100+(y-1)))
					{
						targets.remove(Integer.valueOf(x*100+(y-1)));
					}
				}
			}
			if(y+1<shipGrid[0].length && targetGrid[x][y+1]==0)
			{
				if(priorityTargets.search(x*100+(y+1))<0)
				{
					priorityTargets.push(x*100+(y+1));
					if(targets.contains(x*100+(y+1)))
					{
						targets.remove(Integer.valueOf(x*100+(y+1)));
					}
			
				}
			}
			Vector<Integer> temp=rPlayer.shipsDestroyed();
			for(int i=0;i<temp.size();i++)
			{
				if(!e_shipDestroyed.contains(temp.elementAt(i)))
				{
					e_shipDestroyed.add(temp.elementAt(i));
					shipList[temp.elementAt(i)%10-2]--;
					updateTargets();
				}
			}
		}
		else
		{
			targetGrid[x][y]=2;
			attackCoordList.add(x*100+y);
			attackEffectList.add(2);
		}
		return hitMissFlag;
	}
	private void initTargets()
	{
		for(int i=0;i<shipGrid.length;i++)
		{
			for(int j=0;j<shipGrid[0].length;j++)
			{
				if((i+j)%parity_size==0)
				{
					targets.add(100*i+j);
				}
			}
		}
	}
	private void updateTargets()
	{	
		int temp;
		for(int i=e_shipList.length-1;i>=0;i--)
		{
			if(e_shipList[i]!=0)
			{
				parity_size=ship_length[i];
				break;
			}
		}
		for(int i=0;i<targets.size();i++)
		{
			temp=targets.get(i);
			if(((temp/100)+(temp%100))%parity_size!=0)
			{
				targets.remove(i);
			}
		}
		addTargets();
	}
	private void addTargets()
	{
		for(int i=0;i<shipGrid.length;i++)
		{
			for(int j=0;j<shipGrid[0].length;j++)
			{
				if(!targets.contains(i*100+j))
				{
					if((i+j)%parity_size==0 && targetGrid[i][j]==0 && priorityTargets.search(i*100+j)<0)
					{
					targets.add(100*i+j);
					}
				}
			}
		}
	}
	//Places standard ship configuration
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
	//Places custom ship configuration
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
}

