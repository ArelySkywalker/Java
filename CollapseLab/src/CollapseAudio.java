import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.swing.JApplet;


public class CollapseAudio extends JApplet {

	public class Sound {
		 AudioClip song;
		 URL songPath;
		 String sng =  "EverythingIsAwesome.mp3";

		Sound(String sng) {
			try {
				songPath = new URL(sng); 
				song = JApplet.newAudioClip(songPath);
			} catch(Exception e) {e.printStackTrace();}
		}
		public void playSound() {
			song.play();
		}
	}

}
