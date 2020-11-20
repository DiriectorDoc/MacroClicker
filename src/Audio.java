import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.io.IOUtils;

public class Audio
{
	private Clip clip;
	private File file;
	private String extension;

	public Audio(URL url)
	{
		String urlStr = url.toString();
		extension = urlStr.substring(urlStr.length() - 4).replaceAll("\\w?[.]", "");
		URI path = null;
		try
		{
			path = url.toURI();
		}
		catch(URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(extension)
		{
		case "wav":
			try
			{
				getFile(path.toString().split("!"));
			}
			catch(java.lang.IllegalArgumentException e)
			{
				file = Paths.get(path).toFile();
			}
			break;
		case "ogg":
			file = new File(path);
			break;
		}
	}
	
	private void getFile(String[] array) throws java.lang.IllegalArgumentException
	{
		try
		{
			final Map<String, String> env = new HashMap<>();
			final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
			final File tempFile = File.createTempFile("PREFIX", "SUFFIX");
			tempFile.deleteOnExit();
			try (FileOutputStream out = new FileOutputStream(tempFile))
			{
				IOUtils.copy(Files.newInputStream(fs.getPath(array[1])), out);
			}
			file = tempFile;
			fs.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		switch(extension)
		{
		case "wav":
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
			catch(LineUnavailableException | IOException | UnsupportedAudioFileException e)
			{
				e.printStackTrace();
			}
			break;
		case "ogg":
			/*
			 * For a later date
			 * */
			break;
		}
	}
}