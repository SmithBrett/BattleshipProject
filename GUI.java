import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Stack;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GUI extends JFrame implements ActionListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Integer> coords=new Stack<Integer>();
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
	private JButton preGameGrid[][];
	private JButton p1_turn;
	private JButton p2_turn;
	private JButton surr_Btn;
	private JButton mute_Btn;
	private JButton p1Game_Btn;
	private JButton btnExit;
	private JButton setting_Btn;
	
	//Panels
	private JPanel preShip;
	private JPanel ship;
	private JPanel target;
	private JPanel grid_Panel;
	private JPanel score_panel;
	private JPanel msc_Panel;
	private JPanel contentPane;
	private JPanel preGamePanel;
	private JPanel gamePanel;
	
	//Containers
	private Container content;
	
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
	
	private CardLayout cards;
	
	//TextArea
	private JTextArea activity_Log;
	
	
	private static JFrame frame;

	//constructor
	public GUI(){}
	
//gets frame
public JFrame getFrame()
{
	return frame;
}
public JPanel getGamePanel()
{
	return gamePanel;
}
//displays frame
public void disp()
{
	frame=new JFrame("Battleship");
	
	ship=new JPanel();
	target=new JPanel();
	grid_Panel=new JPanel();
	gamePanel=new JPanel();
	JPanel btmPanel = new JPanel();
	
	content=frame.getContentPane();
	content.setLayout(new CardLayout());
	//content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
	
	
	initGridPanel();
	initScorePanel();
	initMscPanel();
	initMenu();
	initPreGame();
	
	btmPanel.setLayout(new BoxLayout(btmPanel,BoxLayout.X_AXIS));
	gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));
	
	btmPanel.add(grid_Panel);
	btmPanel.add(msc_Panel);
	gamePanel.add(score_panel);
	gamePanel.add(btmPanel);
	
	content.add(contentPane,"Menu");
	content.add(preGamePanel, "PreMatch");
	content.add(gamePanel,"Match");
	cards = (CardLayout) content.getLayout();
	
	frame.setSize(800,800);
	File temp = new File("battleshipIcon.png");	
	String absolutePath = temp.getAbsolutePath();
	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));		 
	 //String file = filePath.replaceAll("\\\\" , "/");
	frame.setIconImage(Toolkit.getDefaultToolkit().getImage(filePath+"/src/"+temp));
	frame.setBackground(Color.DARK_GRAY);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    Sound.menuSound();
	
}
private void initPreGame()
{
	preGamePanel=new JPanel();
	preShip=new JPanel();
	JPanel btns = new JPanel();
	preGameGrid=new JButton[10][10];
	
	btns.setLayout(new FlowLayout(FlowLayout.TRAILING));
	preShip.setLayout(new GridBagLayout());
	preGamePanel.setLayout(new BoxLayout(preGamePanel,BoxLayout.Y_AXIS));
	
	preShip.setPreferredSize(new Dimension(600,600));
	preShip.setMaximumSize(new Dimension(2000,2000));

	preShip.setBackground(Color.BLACK);

	buildGrid(preShip);
	
	JButton btnConfirm = new JButton("Confirm");
	// actionPerformed on the confirm button
	btnConfirm.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			colorAllFields(player1);
			cards.next(content);
			gameHandler.startGame();	
		}
		
	});
	
	JButton btnRearrangeRandomly = new JButton("Random Placement");
	btnRearrangeRandomly.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			player1.clearShipGrid();
			player1.rndmShipGrid();
			colorPreGameFields(player1);
		}
		
	});
	
	btns.add(btnConfirm);
	btns.add(btnRearrangeRandomly);
	
	preGamePanel.add(preShip);
	preGamePanel.add(btns);
	
	/*// Group Layout for the content Pane
	GroupLayout groupLayout = new GroupLayout(preGamePanel);
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(preShip, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(30)
				.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
				.addGap(101)
				.addComponent(btnRearrangeRandomly, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(125, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(preShip, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnRearrangeRandomly, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
				.addGap(25))
	);
	
	// layout of the contentPane
	preGamePanel.setLayout(groupLayout);
	*/
}
private void initMenu() {
	// TODO Auto-generated method stub
	
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//setBounds(500, 200, 450, 350);
	//setIconImage(Toolkit.getDefaultToolkit().getImage(MenuGui.class.getResource("/BattleShip/resources/battleshipIcon.png")));
	
	contentPane = new JPanel();
	contentPane.setBackground(new Color(240, 255, 240));
	contentPane.setForeground(Color.RED);
	contentPane.setBorder(null);
	setContentPane(contentPane);
	
	
	JLabel lblMenu = new JLabel("Menu");
	lblMenu.setBackground(Color.WHITE);
	lblMenu.setFont(new Font("Vivaldi", Font.BOLD, 40));
	lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
	
	p1Game_Btn = new JButton("1 Player");
	
	p1Game_Btn.setForeground(Color.BLACK);
	p1Game_Btn.setBackground(Color.WHITE);
	p1Game_Btn.setFont(new Font("Viner Hand ITC", Font.BOLD | Font.ITALIC, 30));
	p1Game_Btn.setVerticalAlignment(SwingConstants.TOP);
	p1Game_Btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			cards.next(content);
			Sound.stop();
			gameHandler.initGame();
			Sound.bgmSound();
			colorPreGameFields(player1);
		}});
	
	setting_Btn = new JButton("Game Setting");
	setting_Btn.setForeground(Color.BLACK);
	setting_Btn.setBackground(Color.WHITE);
	setting_Btn.setFont(new Font("Viner Hand ITC", Font.BOLD | Font.ITALIC, 30));
	setting_Btn.setVerticalAlignment(SwingConstants.TOP);
	setting_Btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			  JTextField pName = new JTextField(5);
		      JTextField aiDiff = new JTextField(5);
		      JTextField sLevel = new JTextField(5);
		      JTextField tDur = new JTextField(5);
		      
		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Player Name:"));
		      myPanel.add(pName);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("AI Difficulty (1 or 2):"));
		      myPanel.add(aiDiff);
		      myPanel.add(new JLabel("Sound (0-100):"));
		      myPanel.add(sLevel);
		      myPanel.add(new JLabel("Timer Duration:"));
		      myPanel.add(tDur);
		      
		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Settings Menu", JOptionPane.OK_CANCEL_OPTION);
		      if (result == JOptionPane.OK_OPTION) {
		    	  String temp_p=null;
		    	  String temp_a=null;
		    	  String temp_s=null;
		    	  String temp_t=null;
		    	  temp_p=pName.getText().trim();
		    	  temp_a=aiDiff.getText().trim();
		    	  temp_s=sLevel.getText().trim();
		    	  temp_t=tDur.getText().trim();
		    	  if(temp_p != null)
		    	  {
		    			  if(temp_p.equals(""))
		    			  {
		    				  player1.setName("Player 1");
		    			  }
		    			  else
		    			  {
		    				  	player1.setName(temp_p);
		    			  }
		    	  }
		    	  else
		    	  {
		    		  //player1.setName("Player 1");
		    	  }
		    	  if( temp_a != null)
		    	  {
		    		  try
		    		  {
		    		  int temp = Integer.parseInt(temp_a);
		    		  
		    		  if(temp<=1)
		    		  {
		    			  gameHandler.setDiff(1);
		    		  }
		    		  else
		    		  {
		    			  gameHandler.setDiff(2);
		    		  }
		    		  }catch(Exception e)
		    		  {
		    		  
		    		  }
		    	  }
		    	  if( temp_a != null )
		    	  	{
		    		  
		    		  try
		    	  	{
		    		  int temp = Integer.parseInt(temp_s);
		    		  //System.out.println(temp+"\n");
		    		  if(temp>86)
		    		  {
		    			  Sound.volumeChange(6);
		    		  }
		    		  else if (temp<0)
		    		  {
		    			  Sound.volumeChange(-80);
		    		  }
		    		  else
		    		  {
		    			  Sound.volumeChange(temp-80);
		    		  }
		    		  //Sound.stop();
		    		  //Sound.menuSound();
		    	  	}catch(Exception e)
		    	  	{
		    		  
		    	  	}
		    		  
		    		  
		    	  }
		    	  if(temp_t !=null)
		    	  {
		    		  try
		    		  {
		    			  int temp = Integer.parseInt(temp_t);
		    			  if(temp<5)
		    			  {
		    				  gameHandler.setTimeDuration(5);
		    			  }
		    			  else
		    			  {
		    				  gameHandler.setTimeDuration(temp);
		    			  }
		    		  }catch(Exception e)
		    		  {
		    			  
		    		  }
		    	  }
		      }
		}});
	
	btnExit = new JButton("EXIT");
	
	btnExit.setBackground(Color.WHITE);
	btnExit.setForeground(Color.BLACK);
	btnExit.setFont(new Font("Viner Hand ITC", Font.BOLD | Font.ITALIC, 30));
	btnExit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
		}});
	
	
	
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(85)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addComponent(btnExit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
					.addComponent(setting_Btn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
					.addComponent(p1Game_Btn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(78))
			.addGroup(gl_contentPane.createSequentialGroup()
				.addGap(16)
				.addComponent(lblMenu, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
				.addContainerGap())
	);
	gl_contentPane.setVerticalGroup(
		gl_contentPane.createParallelGroup(Alignment.LEADING)
			.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
				.addGap(4)
				.addComponent(lblMenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(19)
				.addComponent(p1Game_Btn)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(setting_Btn)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(btnExit)
				.addGap(100))
	);
	contentPane.setLayout(gl_contentPane);
	
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
	surr_Btn.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent e) {
	         //System.out.println("surrender");
	         gameHandler.surrender();
	       }
	    });
	mute_Btn = new JButton("Mute");
	mute_Btn.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent e) {
	    	 // System.out.println("muted");
	    	  gameHandler.mute();
	       }
	    });
	
	surr_Btn.setPreferredSize(new Dimension(100,100));
	mute_Btn.setPreferredSize(new Dimension(100,100));
	
	timerLabel = new JLabel("Timer: 30");
	
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
	
	p1_turn = new JButton("turn");
	p2_turn = new JButton("turn");
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
	
	ship.setPreferredSize(new Dimension(600,600));
	ship.setMaximumSize(new Dimension(2000,2000));
	target.setPreferredSize(new Dimension(600,600));
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
				else if(panel.equals(preShip))
				{
					offset=preGameGrid[0][0].getLocation();
				}
				int x=(pt.x-offset.x)/rect.width; //-grid_Panel.getWidth()/(2*rect.width)+5;
				int y=(pt.y-offset.y)/rect.height;//-grid_Panel.getHeight()/(4*rect.height)+5;
				
				
				if(panel.equals(preShip))
				{
					coords.push(x*100+y);
					if(coords.size()>1)
					{
						
						int temp=coords.pop();
						int old=coords.pop();
						player1.moveShip(old/100, old%100, temp/100, temp%100);
						while(player1.ready==false)
						{
						}
						colorPreGameFields(player1);
						//player1.printGrid(0);
					}
				}
				else
				{
				sendCoor(x,y);
				}
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
	gameHandler.turn(x,y);
	x = (x*100+y)/100;
	y = (x*100+y)%100;
//	System.out.println("xcoord: "+x+"  ycoord: "+y);
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
	if(player.getName().equals(player1.getName()))
	{
		
		p2_turn.setText("");
		p1_turn.setText("Turn");
		//p2_turn.repaint();
		//p1_turn.repaint();
	}
	else
	{
		p1_turn.setText("");;
		p2_turn.setText("Turn");
		//p2_turn.repaint();
		//p1_turn.repaint();
	}
}
//updates remaining ships on scoreboard
public void updateScore()
{	
	int[] temp=player1.shipListT;
	player1_Label.setText(player1.getName());
	player1_2x1.setText("2x1: "+temp[3]);
	player1_3x1.setText("3x1: "+temp[2]);
	player1_4x1.setText("4x1: "+temp[1]);
	player1_5x1.setText("5x1: "+temp[0]);
	
	temp=player2.shipListT;
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

//colors preGame Fields
public void colorPreGameFields(player player)
{
	for(int i=0;i<shipGrid.length;i++)
	{
		for(int j=0;j<shipGrid[0].length;j++)
		{
		switch (player.getShipGrid_Value(i, j))
		{
		case 0: preGameGrid[i][j].setBackground(Color.GRAY);
			break;
		case 1: preGameGrid[i][j].setBackground(Color.ORANGE);
			break;
		default: preGameGrid[i][j].setBackground(Color.BLUE);
			break;
		}
		}
	}
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
	else if(p.equals(preShip))
	{
		preGameGrid[x][y]=btn;
	}
}
//frame visibility
public void toMenu()
{
	cards.show(content, "Menu");
	Sound.stop();
	try {
		
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Sound.menuSound();
	activity_Log.setText("");
}

public Component getVisible()
{
	for(Component comp : content.getComponents()) {
	    if (comp.isVisible()) {
	        return (JPanel)comp;
	    }
	}
	return content;
}
//Button pressed Logic
public void actionPerformed(ActionEvent e) 
{
	if(e.getSource()==surr_Btn)
	{
		//refer to line 119
		//Add Game handler Method
	}
	else if(e.getSource()==mute_Btn)
	{
		//refer to line 127
	
	}
	else if(e.getSource()==setting_Btn)
	{
		
	
	}
	else if(e.getSource()==p1Game_Btn)
	{
	
	}
	else if(e.getSource()==btnExit)
	{
	
	}
	
}
}