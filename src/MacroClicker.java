import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MacroClicker
{
	static Audio ping = new Audio(".\\src\\resources\\ping.wav"),
			     boohw = new Audio(".\\src\\resources\\boohw.wav");
	
	public static volatile boolean keyPressed = false;
	
	public static setInterval clicker;
	
	private static Robot bot;
	
	public static void main(String[] args)
	{
		try
		{
			bot = new Robot();
			GlobalScreen.registerNativeHook();
		}
		catch(AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NativeHookException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
		
		build();
		clicker = new setInterval(() -> {
			if(keyPressed)
			{
				click();
			}
			System.out.println(keyPressed);
		}, 1000);
	}

	public static void build()
	{
		JFrame window = new JFrame("Macro Clicker");
		{
			JMenuBar menuBar = new JMenuBar();
		    window.setJMenuBar(menuBar);
		    {
			    JMenu file = new JMenu("File");
			    file.setMnemonic(KeyEvent.VK_F);
			    menuBar.add(file);
			    {
				    JMenuItem save = new JMenuItem("Save Config");
				    file.add(save);
				    
				    JMenuItem open = new JMenuItem("Open Config");
				    file.add(open);
				    
				    JMenuItem exit = new JMenuItem("Exit");
				    file.add(exit);
				    
				    
				    exit.addActionListener(new ActionListener()
				    {
				    	public void actionPerformed (ActionEvent a)
						{
				    		System.exit(0);
						}
				    });
			    }
			    JMenu settings = new JMenu("Settings");
			    settings.setMnemonic(KeyEvent.VK_S);
			    menuBar.add(settings);
			    {
				    JMenuItem config = new JMenuItem("Cinfigure");
				    settings.add(config);
				    
				    
					config.addActionListener(new ActionListener()
					{
				    	public void actionPerformed (ActionEvent a)
						{
				    		AltWindow newWindow = new AltWindow("Settings", 200, 200);
						    
						    JButton test = new JButton("Tester");  
						    test.setBounds(5,5,95,30); 
						    newWindow.add(test);
						    
						    newWindow.buildAndShow();
						}
				    });
			    }
		    }
		    JButton activate = new JButton("Activate");  
		    activate.setBounds(5,5,95,30); 
		    window.add(activate);
		    
		    JButton deactivate = new JButton("Deactivate");  
		    deactivate.setBounds(5,40,95,30); 
		    window.add(deactivate);

			window.add(Box.createRigidArea(new Dimension(700, 500)));
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    //window.addKeyListener(new keys());
		    window.pack();
		    window.setVisible(true);
		    
		    
		    
			activate.addActionListener(new ActionListener()
			{
		    	public void actionPerformed(ActionEvent a)
				{
		    		ping.play();
		    		clicker.restart();
				}
		    });
			deactivate.addActionListener(new ActionListener()
			{
		    	public void actionPerformed(ActionEvent a)
				{
		    		boohw.play();
		    		clicker.clearInterval();
				}
		    });
		}
	}
	
	public static void click()
	{
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
}
