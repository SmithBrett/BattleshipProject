import javax.swing.*;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GUI extends JFrame implements ActionListener
{
	//players
	private player player1;
	private player player2;
	
	//Threading
	private ScheduledThreadPoolExecutor timerThread;
	private ScheduledFuture<?> timerHandle;
	
	//grid tile size
	private int SQUARE_SIZE=50;
	
	//Buttons
	private JButton shipGrid[][];
	private JButton targetGrid[][];
	private JButton p1_turn;
	private JButton p2_turn;
	private JButton surr_Btn;
	private JButton mute_Btn;
	
	//Panels
	private JPanel ship;
	private JPanel target;
	private JPanel grid_Panel;
	private JPanel score_panel;
	private JPanel msc_Panel;
	
	//Labels
	private JLabel score_Label;
	private JLabel player1_Label;
	private JLabel player1_2x1;
	private JLabel player1_3x1;
	private JLabel player1_4x1;
	private JLabel player1_5x1;
	
	private JLabel player2_Label;
	private JLabel player2_2x1;
	private JLabel player2_3x1;
	private JLabel player2_4x1;
	private JLabel player2_5x1;
	
	private JLabel timerLabel;
	
	//TextArea
	private JTextArea activity_Log;
	
	static JFrame frame;

	//constructor
	public GUI(){}
	
//gets frame
public JFrame getFrame()
{
	return frame;
}
//displays frame
public void disp()
{
	frame=new JFrame("Battleship");
	
	ship=new JPanel();
	target=new JPanel();
	grid_Panel=new JPanel();
	
	JPanel btmPanel = new JPanel();
	
	Container content=frame.getContentPane();
	content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

	initGridPanel();
	initScorePanel();
	initMscPanel();
	
	btmPanel.setLayout(new BoxLayout(btmPanel,BoxLayout.X_AXIS));
	
	btmPanel.add(grid_Panel);
	btmPanel.add(msc_Panel);
	content.add(score_panel);
	content.add(btmPanel);
	
	frame.setSize(800,800);
	frame.setBackground(Color.DARK_GRAY);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
	
}

//sets player 1 and player 2
public void initPlayerInfo(player player1,player player2)
{
	this.player1=player1;
	this.player2=player2;
}
//inits misc. panel
private void initMscPanel()
{
	JPanel activityPanel=new JPanel();
	JPanel btnPanel=new JPanel();
	
	msc_Panel = new JPanel();
	msc_Panel.setLayout(new BoxLayout(msc_Panel,BoxLayout.Y_AXIS));
	
	
	btnPanel.setLayout(new GridLayout(1,2));
	activityPanel.setLayout(new BoxLayout(activityPanel,BoxLayout.Y_AXIS));
	surr_Btn = new JButton("Surrender");
	mute_Btn = new JButton("Mute");
	
	surr_Btn.setPreferredSize(new Dimension(100,100));
	mute_Btn.setPreferredSize(new Dimension(100,100));
	
	timerLabel = new JLabel("Timer: 0");
	
	timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
	timerLabel.setVerticalAlignment(SwingConstants.CENTER);
	
	timerLabel.setFont(new Font("AvantGarde", Font.BOLD, 32));
	
	JLabel act_Label=new JLabel("Event Log");
	activity_Log = new JTextArea(15,15);
	activity_Log.setEditable(false);
	JScrollPane activityLogScroll = new JScrollPane(activity_Log,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	btnPanel.add(surr_Btn);
	btnPanel.add(mute_Btn);
	activityPanel.add(act_Label);
	activityPanel.add(activity_Log);
	activityPanel.add(activityLogScroll);
	
	msc_Panel.add(btnPanel);
	msc_Panel.add(timerLabel);
	msc_Panel.add(activityPanel);
	
}
//inits timer refresh on GUI
public void initTimerRefresh(timer t)
{
	timerThread = new ScheduledThreadPoolExecutor(1);
	timerThread.setRemoveOnCancelPolicy(true);
	timerThread.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
	Runnable timeUpdate = new Runnable(){public void run(){timerLabel.setText("Timer: "+t.getCount()); /*System.out.println(count);*/}};
	timerHandle = timerThread.scheduleAtFixedRate(timeUpdate,5,1,SECONDS);
}
//Stops timer refresh thread
//only call when game ends
public void stopTimerRefresh()
{
	timerHandle.cancel(true);
	timerThread.shutdownNow();
}
//Sets up the Score board
private void initScorePanel()
{
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p1_ships = new JPanel();
	JPanel p2_ships = new JPanel();
	JPanel p1_p2 = new JPanel();
	
	p1.setBackground(Color.DARK_GRAY);
	p2.setBackground(Color.DARK_GRAY);
	p1_ships.setBackground(Color.DARK_GRAY);
	p2_ships.setBackground(Color.DARK_GRAY);
	p1_p2.setBackground(Color.DARK_GRAY);
	
	p1_turn = new JButton("↓");
	p2_turn = new JButton("↓");
	score_panel = new JPanel();
	score_panel.setLayout(new BoxLayout(score_panel,BoxLayout.Y_AXIS));
	
	p1_p2.setLayout(new BoxLayout(p1_p2,BoxLayout.X_AXIS));
	p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
	p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
	p1_ships.setLayout(new GridLayout(2,2));
	p2_ships.setLayout(new GridLayout(2,2));
	
	score_Label=new JLabel("Score Board");
	score_Label.setFont(new Font("AvantGarde", Font.BOLD, 42));
	score_Label.setForeground(Color.WHITE);
	score_Label.setHorizontalAlignment(SwingConstants.CENTER);
	score_Label.setVerticalAlignment(SwingConstants.CENTER);
	
	player1_Label=new JLabel("Player1");
	player1_2x1=new JLabel("2x1: 0");
	player1_3x1=new JLabel("3x1: 0");
	player1_4x1=new JLabel("4x1: 0");
	player1_5x1=new JLabel("5x1: 0");
	
	player1_Label.setForeground(Color.WHITE);
	player1_2x1.setForeground(Color.WHITE);
	player1_3x1.setForeground(Color.WHITE);
	player1_4x1.setForeground(Color.WHITE);
	player1_5x1.setForeground(Color.WHITE);
	
	player1_Label.setFont(new Font("AvantGarde", Font.BOLD, 32));
	player1_2x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player1_3x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player1_4x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player1_5x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	
	player1_Label.setHorizontalAlignment(SwingConstants.CENTER);
	player1_Label.setVerticalAlignment(SwingConstants.CENTER);
	player1_2x1.setHorizontalAlignment(SwingConstants.CENTER);
	player1_2x1.setVerticalAlignment(SwingConstants.CENTER);
	player1_3x1.setHorizontalAlignment(SwingConstants.CENTER);
	player1_3x1.setVerticalAlignment(SwingConstants.CENTER);
	player1_4x1.setHorizontalAlignment(SwingConstants.CENTER);
	player1_4x1.setVerticalAlignment(SwingConstants.CENTER);
	player1_5x1.setHorizontalAlignment(SwingConstants.CENTER);
	player1_5x1.setVerticalAlignment(SwingConstants.CENTER);
	
	p1_ships.add(player1_2x1);
	p1_ships.add(player1_3x1);
	p1_ships.add(player1_4x1);
	p1_ships.add(player1_5x1);
	
	p1.add(p1_turn);
	p1.add(player1_Label);
	p1.add(p1_ships);
	
	player2_Label=new JLabel("Player2");
	player2_2x1=new JLabel("2x1: 0");
	player2_3x1=new JLabel("3x1: 0");
	player2_4x1=new JLabel("4x1: 0");
	player2_5x1=new JLabel("5x1: 0");
	
	player2_Label.setForeground(Color.WHITE);
	player2_2x1.setForeground(Color.WHITE);
	player2_3x1.setForeground(Color.WHITE);
	player2_4x1.setForeground(Color.WHITE);
	player2_5x1.setForeground(Color.WHITE);
	
	player2_Label.setFont(new Font("AvantGarde", Font.BOLD, 32));
	player2_2x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player2_3x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player2_4x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	player2_5x1.setFont(new Font("AvantGarde", Font.BOLD, 28));
	
	player2_Label.setHorizontalAlignment(SwingConstants.CENTER);
	player2_Label.setVerticalAlignment(SwingConstants.CENTER);
	player2_2x1.setHorizontalAlignment(SwingConstants.CENTER);
	player2_2x1.setVerticalAlignment(SwingConstants.CENTER);
	player2_3x1.setHorizontalAlignment(SwingConstants.CENTER);
	player2_3x1.setVerticalAlignment(SwingConstants.CENTER);
	player2_4x1.setHorizontalAlignment(SwingConstants.CENTER);
	player2_4x1.setVerticalAlignment(SwingConstants.CENTER);
	player2_5x1.setHorizontalAlignment(SwingConstants.CENTER);
	player2_5x1.setVerticalAlignment(SwingConstants.CENTER);
	
	p2_ships.add(player2_2x1);
	p2_ships.add(player2_3x1);
	p2_ships.add(player2_4x1);
	p2_ships.add(player2_5x1);
	
	p2.add(p2_turn);
	p2.add(player2_Label);
	p2.add(p2_ships);
	
	p1_p2.add(p1);
	p1_p2.add(p2);
	
	score_panel.add(score_Label);
	score_panel.add(p1_p2);
	
	score_panel.setBackground(Color.DARK_GRAY);
	
	
	
}
//Initiates grids
private void initGridPanel()
{
	shipGrid=new JButton[10][10];
	targetGrid=new JButton[10][10];
	ship.setLayout(new GridBagLayout());
	target.setLayout(new GridBagLayout());
	
	ship.setPreferredSize(new Dimension(600,500));
	ship.setMaximumSize(new Dimension(2000,2000));
	target.setPreferredSize(new Dimension(600,500));
	target.setMaximumSize(new Dimension(2000,2000));
	
	target.setBackground(Color.BLACK);
	ship.setBackground(Color.BLACK);
	
	//JLabel ship_Label=new JLabel("Ship Grid");
	//JLabel target_Label=new JLabel("Target Grid");
	grid_Panel.setLayout(new GridLayout(1,2,5,0));
	grid_Panel.setBackground(Color.WHITE);
	
	buildGrid(ship);
	buildGrid(target);
	
	
	grid_Panel.add(target);
	grid_Panel.add(ship);
	
}
//Builds button Matrix for grids
private void buildGrid(JPanel p)
{
	GridBagConstraints constraints = new GridBagConstraints();
	constraints.fill = GridBagConstraints.BOTH;
	
	for(int i=0; i<10; i++)
	{
		for(int j=0;j<10;j++)
		{
			JButton btn = new JButton();
			btn.setBackground(Color.GRAY);
			btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			btn.setMinimumSize(new Dimension(20,20));
			btn.setMaximumSize(new Dimension(200,200));
			btn.setPreferredSize(new Dimension(SQUARE_SIZE,SQUARE_SIZE));
			btn.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
				JButton btn = (JButton) e.getSource();
				JPanel panel = (JPanel) btn.getParent();
				Rectangle rect = btn.getBounds();
				Point pt = btn.getLocation();
				Point offset = panel.getLocation();
				
				//System.out.println(grid_Panel.getWidth()/(2*rect.width));
				if(panel.equals(ship))
				{
					offset=shipGrid[0][0].getLocation();
					
				}
				else if(panel.equals(target))
				{
					offset=targetGrid[0][0].getLocation();
				}
				int x=(pt.x-offset.x)/rect.width; //-grid_Panel.getWidth()/(2*rect.width)+5;
				int y=(pt.y-offset.y)/rect.height;//-grid_Panel.getHeight()/(4*rect.height)+5;
				
				
				
				sendCoor(x,y);
				}
				
			});
			
			addField(i,j,btn,p);
			constraints.gridx=i;
			constraints.gridy=j;
			
			p.add(btn,constraints);
		}
	}
	
}
//input handling method
public void sendCoor(int x, int y)
{
	System.out.println(x*100+y);
	//add gamehandler functions
}
//Write String to activity log
public void writeEvent(String str)
{
	activity_Log.append(str+"\n");
}
//set Turn indicator
public void setTurn(player player)
{
	if(player.equals(player1))
	{
		p2_turn.setVisible(false);
		p1_turn.setVisible(true);
	}
	else
	{
		p1_turn.setVisible(false);
		p2_turn.setVisible(true);
	}
}
//updates remaining ships on scoreboard
public void updateScore()
{	
	int[] temp=player1.shipList;
	player1_Label.setText(player1.getName());
	player1_2x1.setText("2x1: "+temp[3]);
	player1_3x1.setText("3x1: "+temp[2]);
	player1_4x1.setText("4x1: "+temp[1]);
	player1_5x1.setText("5x1: "+temp[0]);
	
	temp=player2.shipList;
	player2_Label.setText(player2.getName());
	player2_2x1.setText("2x1: "+temp[3]);
	player2_3x1.setText("3x1: "+temp[2]);
	player2_4x1.setText("4x1: "+temp[1]);
	player2_5x1.setText("5x1: "+temp[0]);
}
//colors single button
public void colorTargetField(int x, int y, player player)
{
	switch (player.getTargetGrid_Value(x, y))
	{
	case 0: targetGrid[x][y].setBackground(Color.GRAY);
		break;
	case 1: targetGrid[x][y].setBackground(Color.RED);
		break;
	case 2: targetGrid[x][y].setBackground(Color.WHITE);
	default:
		break;
	}
	target.repaint();
}
public void colorShipField(int x, int y,player player)
{
	switch (player.getShipGrid_Value(x, y))
	{
	case 0: shipGrid[x][y].setBackground(Color.GRAY);
		break;
	case 1: shipGrid[x][y].setBackground(Color.ORANGE);
		break;
	default: shipGrid[x][y].setBackground(Color.BLUE);
		break;
	}
	ship.repaint();
}
//colors all buttons
public void colorAllFields(player player)
{
	for(int i=0;i<shipGrid.length;i++)
	{
		for(int j=0;j<shipGrid[0].length;j++)
		{
			switch (player.getShipGrid_Value(i, j))
			{
			case 0: shipGrid[i][j].setBackground(Color.GRAY);
				break;
			case 1: shipGrid[i][j].setBackground(Color.ORANGE);
				break;
			default: shipGrid[i][j].setBackground(Color.BLUE);
				break;
			}
			switch (player.getTargetGrid_Value(i, j))
			{
			case 0: targetGrid[i][j].setBackground(Color.GRAY);
				break;
			case 1: targetGrid[i][j].setBackground(Color.RED);
				break;
			case 2: targetGrid[i][j].setBackground(Color.WHITE);
			default:
				break;	
			}
		
		}
	}
	ship.repaint();
	target.repaint();
}
//Adds button to 2d button array
public void addField(int x,int y, JButton btn,JPanel p)
{
	if(p.equals(ship))
	{
		shipGrid[x][y]=btn;
	}
	else if(p.equals(target))
	{
		targetGrid[x][y]=btn;
	}
}
//frame visibility
public void frameVis()
{
	frame.setVisible(true);
}
public void frameInVis()
{
	frame.setVisible(false);
}
public void refresh()
{
	frame.revalidate();
}
//Button pressed Logic
public void actionPerformed(ActionEvent e) 
{
	if(e.getSource()==surr_Btn)
	{
		//Add Game handler Method
	}
	else if(e.getSource()==mute_Btn)
	{
		// add game handle Method
	}
	
}
}
