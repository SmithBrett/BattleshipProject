import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

/*Adam Dingess - Ver: 0.3 - 4/17/18
 * 
 * To play a sound, you need to invoke "menuSound();", "shotSound();", "timerSound();"... etc. 
 * You will also need to change the location of the audio files for each to test this. 
 * 
 * If you want to try to find sounds: they must be .wav (they can be converted here: https://audio.online-convert.com/convert-to-wav)
 * Additionally, they must all be: 16-bit resolution, 44,100 Hz sampling rate, and mono audio channel
 * 
 */

public class Sound {

	   
	 
	   private static boolean mute = false;
	   private static Clip clip;
	   
	  public static void menuSound() {
		      try {
		    	// File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/menu.wav");	
		         File temp = new File("menu.wav");	
		    	 String absolutePath = temp.getAbsolutePath();
		    	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		    	 String file = filePath.replaceAll("\\\\" , "/");
		    	 File audiofile = new File(filePath+"/src/"+temp);
		    	 
		         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
		         clip = AudioSystem.getClip();
		         clip.open(audioInputStream);
		      } catch (UnsupportedAudioFileException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      } catch (LineUnavailableException e) {
		         e.printStackTrace();
		      } while (mute != true) 
		     	{ clip.setLoopPoints(0,-1);
			      clip.loop(50);  break; }
		      
		   }
	  
	  public static void shotSound() {
	      try {
	    	// File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/shot.wav");	
	    	  File temp = new File("shot.wav");	
		   	  String absolutePath = temp.getAbsolutePath();
		      String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		   	  String file = filePath.replaceAll("\\\\" , "/");
		   	 File audiofile = new File(filePath+"/src/"+temp);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } 
	      while (mute != true) 
	     	{clip.start(); break;}
	   } 
	  
	  public static void timerSound() {
	      try {
	    	     
	    	  File temp = new File("timer.wav");	
		     String absolutePath = temp.getAbsolutePath();
		   	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		   	 String file = filePath.replaceAll("\\\\" , "/");
		   	 File audiofile = new File(filePath+"/src/"+temp);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } 
	      while (mute != true) 
	     	{clip.start(); break;}
	   }
	  
	  public static void turnSwitchSound() {
	      try {
	    	     
	    	 File temp = new File("turnSwitch.wav");	
		   	 String absolutePath = temp.getAbsolutePath();
		   	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		   	 String file = filePath.replaceAll("\\\\" , "/");
	    	 File audiofile = new File(filePath+"/src/"+temp);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } while (mute != true) 
	     	{clip.start(); break;}
	   }
	  
	  public static void hitSound() {
	      try {
	     
	         File temp = new File("hitSound.wav");	
	    	 String absolutePath = temp.getAbsolutePath();
	    	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));		 
        	 String file = filePath.replaceAll("\\\\" , "/");
		   	 File audiofile = new File(file+"/src/"+temp);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } 
	      while (mute != true) 
	     	{clip.start(); break;}
	   } 
	  
	  public static void missSound() {
	      try {
	    		     
	         File temp = new File("missSound.wav");	
	    	 String absolutePath = temp.getAbsolutePath();
	    	 String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));		 
	    	 String file = filePath.replaceAll("\\\\" , "/");
		   	 File audiofile = new File(filePath+"/src/"+temp);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } 
	      while (mute != true) 
	     	{clip.start(); break;}
	   } 
	  
	   public static void mute() {
		   if(mute == false)
		   {mute = true;
		   if (
			   clip.isRunning()){clip.stop();}
		   
		   }
		   
		   else mute = false;
		   
	   }
	   public static void stop(){
		   clip.stop();
	   }
	   
	   public static void volumeChange(int num) {
	   FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(num);
	   } 
	   
	   public static boolean checkRunning(){
		  return clip.isActive();
	   }
	  
	  
}	

