//Adam Dingess - Ver: 0.3 - test class for the sound 4/17/18
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
   
public class SoundTest extends JFrame {
	


	public SoundTest() {

	 
    Container cp = this.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JButton btnSound1 = new JButton("Shot");
    btnSound1.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
          Sound.shotSound();
       }
    });
    cp.add(btnSound1);
    
    JButton btnSound2 = new JButton("Menu music");
    btnSound2.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
          Sound.menuSound();
          
       }
    });
    cp.add(btnSound2);
    
    JButton btnSound3 = new JButton("Time expiring");
    btnSound3.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.timerSound();
       }
    });
    cp.add(btnSound3);
    
    JButton btnSound4 = new JButton("Turn switch");
    btnSound4.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.turnSwitchSound();
       }
    });
    cp.add(btnSound4);
    
    JButton btnSound5 = new JButton("Hit sound");
    btnSound5.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.hitSound();
       }
    });
    cp.add(btnSound5);
    
    JButton btnSound6 = new JButton("Miss sound");
    btnSound6.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.missSound();
       }
    })
    ;cp.add(btnSound6);
    
    JButton btnSound7 = new JButton("Mute");
    btnSound7.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.mute();
       }
    });
    cp.add(btnSound7);
    
  /*  JButton btnSound8 = new JButton("Volume Up");
    btnSound8.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.volumeChange();
       }
    });
    cp.add(btnSound8);
    */
    
    JSlider soundLevel = new JSlider(JSlider.HORIZONTAL, -50, 6, -10);
    soundLevel.addChangeListener(new ChangeListener() {
    	public void stateChanged(ChangeEvent event) {
    		//boolean running = Sound.checkRunning();
    		// if(running = true){
    			 int value = soundLevel.getValue();
    			 Sound.volumeChange(value);
    	//	 }
    		
    	}
       });
    soundLevel.setMajorTickSpacing(10);
   // soundLevel.setMinorTickSpacing(5);
    soundLevel.setPaintTicks(true);
   // soundLevel.setPaintLabels(true);
    cp.add(soundLevel);
    

    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Test Sound Effects");
    this.pack();
    this.setVisible(true);
 }
	
	
	
	public static void main(String[] args){
		new SoundTest();
		
	}
}
