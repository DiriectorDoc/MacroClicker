import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MacroClicker
{
	/*static Audio ping = new Audio("C:\\Users\\User\\AppData\\Roaming\\Macro Clicker\\Ping.wav"),
			     boohw = new Audio("C:\\Users\\User\\AppData\\Roaming\\Macro Clicker\\Boohw.wav");*/
	
	private static volatile boolean keyPressed = false;
	
	public static void main (String[] args)
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke)
            {
                synchronized(MacroClicker.class)
                {
                    switch (ke.getID())
                    {
                    case KeyEvent.KEY_PRESSED:
                        keyPressed = ke.getKeyCode() == KeyEvent.VK_C;
                        break;
                    case KeyEvent.KEY_RELEASED:
                        keyPressed = false;
                        break;
                    }
                    return false;
                }
            }
        });
		build();
		@SuppressWarnings("unused")
		setInterval clicker = new setInterval(() -> {
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
				    
				    
				    exit.addActionListener(new ActionListener() {
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
				    
				    
					config.addActionListener(new ActionListener() {
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
		    
		    
		    
			activate.addActionListener(new ActionListener() {
		    	public void actionPerformed (ActionEvent a)
				{
		    		//ping.play();
				}
		    });
			deactivate.addActionListener(new ActionListener() {
		    	public void actionPerformed (ActionEvent a)
				{
		    		//boohw.play();
				}
		    });
		}
	}
}
