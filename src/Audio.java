import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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

	public Audio(URL url)
	{
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
		try
		{
			getFile(path.toString().split("!"));
		}
		catch(java.lang.IllegalArgumentException e)
		{
			file = Paths.get(path).toFile();
		}
	}
	
	private void getFile(String[] array) throws java.lang.IllegalArgumentException
	{
		try
		{
			final Map<String, String> env = new HashMap<>();
			final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
			final Path path = fs.getPath(array[1]);
			InputStream in = Files.newInputStream(path);
			final File tempFile = File.createTempFile("PREFIX", "SUFFIX");
			tempFile.deleteOnExit();
			try (FileOutputStream out = new FileOutputStream(tempFile))
			{
				IOUtils.copy(in, out);
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