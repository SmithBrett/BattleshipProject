//Adam Dingess - Ver: 0.1 - test class for the sound
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
   
public class SoundTest extends JFrame {
	


	public SoundTest() {

	 
    Container cp = this.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JButton btnSound1 = new JButton("Sound 1");
    btnSound1.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
          Sound.shotSound();
       }
    });
    cp.add(btnSound1);
    JButton btnSound2 = new JButton("Sound 2");
    btnSound2.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
          Sound.menuSound();
          
       }
    });
    cp.add(btnSound2);
    JButton btnSound3 = new JButton("Sound 3");
    btnSound3.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.timerSound();
       }
    });
    cp.add(btnSound3);
    JButton btnSound4 = new JButton("Sound 4");
    btnSound4.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
    	  Sound.turnSwitchSound();
       }
    });
    cp.add(btnSound4);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Test Sound Effects");
    this.pack();
    this.setVisible(true);
 }
	
	
	
	public static void main(String[] args){
		new SoundTest();
		
	}
}
