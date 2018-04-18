//Adam Dingess - Ver: 0.2 - test class for the sound
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;

		
public class gameTest extends JFrame{


		public gameTest() {

		Sound.menuSound();
	    Container cp = this.getContentPane();
	    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
	    JButton btnSound1 = new JButton("One Player Game");
	    btnSound1.addActionListener(new ActionListener() {
	       @Override
	       public void actionPerformed(ActionEvent e) {
	          gamehandler.onePlayerGame();
	          
	       }
	    });
	    cp.add(btnSound1);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Game Handler Test");
	    this.pack();
	    this.setVisible(true);
	 }
		
		
		
		public static void main(String[] args){
			new gameTest();
			
		}
	}

