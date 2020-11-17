import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio
{
	private Clip clip;
	private File file;
	
	public Audio(String path)
	{
		file = new File(path);
	}
	
	public Audio(File sample)
	{
		file = sample;
	}
	
	public void play()
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.addLineListener(new LineListener()
				{
		    		public void update(LineEvent event)
		    		{
		    			if(event.getType().equals(LineEvent.Type.STOP))
		    			{
		    				Line soundClip = event.getLine();
		    				soundClip.close();
			    		}
			    	}
				}
			);
			clip.start();
		} 
		catch(LineUnavailableException e) 
		{
			e.printStackTrace();
		} 
		catch(IOException e) 
		{
			e.printStackTrace();
		} 
		catch(UnsupportedAudioFileException e) 
		{
			e.printStackTrace();
		}
	}
}
