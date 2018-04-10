import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

/*Adam Dingess - Ver: 0.1 - 4/8/18
 * 
 * To play a sound, you need to invoke "menuSound();", "shotSound();", "timerSound();"... etc. 
 * You will also need to change the location of the audio files for each to test this. 
 * 
 * I'm still working on muting the sound 
 * 
 * If you want to try to find sounds: they must be .wav (they can be converted here: https://audio.online-convert.com/convert-to-wav)
 * Additionally, they must all be: 16-bit resolution, 44,100 Hz sampling rate, and mono audio channel
 * 
 */

public class Sound {

	   static Scanner console = new Scanner(System.in);
	   //C:/Users/Adam/workspace/SoundforBS/src/menu.wav
	//   String filePath = fileLoc();
	 
	   private static Clip clip;
	   
	  public static void menuSound() {
		      try {
		    	 File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/menu.wav");	     
		         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
		         clip = AudioSystem.getClip();
		         clip.open(audioInputStream);
		      } catch (UnsupportedAudioFileException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      } catch (LineUnavailableException e) {
		         e.printStackTrace();
		      } clip.start();
		   }
	  
	  public static void shotSound() {
	      try {
	    	 File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/shot.wav");	     
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } clip.start();
	   } 
	  
	  public static void timerSound() {
	      try {
	    	 File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/timer.wav");	     
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
	      clip.start();
	      
	   }
	  
	  public static void turnSwitchSound() {
	      try {
	    	 File audiofile= new File("C:/Users/Adam/workspace/SoundforBS/src/turnSwitch.wav");	     
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audiofile);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } clip.start();
	   }
	  
	   public static void mute() {
		   clip.stop();
	   }
	  
}	

