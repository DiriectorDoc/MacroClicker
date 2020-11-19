import java.awt.AWTException;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

	private static int delay = 30;

	private static String info = SimpleFileReader.getContent(".\\info\\jnativehook.txt");

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
		catch(ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}, delay);
	}

	public static void build()
	{
		JFrame window = new JFrame("Macro Clicker");
		{
			AltWindow configWindow = new AltWindow("Settings", 600, 200);

			JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 33);

			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.addChangeListener(new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent arg0)
				{
					// TODO Auto-generated method stub
					int val = slider.getValue();
					if(val > 33)
					{
						bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						slider.setValue(33);
						WinSounds.play("win.sound.exclamation");
					}
					if(val < 1)
					{
						slider.setValue(1);
					}
				}
			});
			slider.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(MouseEvent me)
				{
					delay = 1000/slider.getValue();
				}
			});
			configWindow.add(slider);
			configWindow.build();
			
			
			
			JDialog aboutWindow = new JDialog();
			aboutWindow.setPreferredSize(new Dimension(400, 400));
			aboutWindow.setModal(true);
			aboutWindow.setAlwaysOnTop(true);
			aboutWindow.setModalityType(ModalityType.APPLICATION_MODAL);
			
			JTextArea textArea = new JTextArea(5, 20);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			textArea.setEditable(false);
			
			aboutWindow.add(scrollPane);
			
			aboutWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			aboutWindow.pack();
			
			info = info.replaceAll("\n", " ");
			textArea.append(info);



			JMenuBar menuBar = new JMenuBar();
			window.setJMenuBar(menuBar);
			{
				JMenu file = new JMenu("File");
				file.setMnemonic(KeyEvent.VK_F);
				menuBar.add(file);
				{
					JMenuItem save = new JMenuItem("Save Config");
					file.add(save);
					save.setIcon(new ImageIcon(".\\src\\resources\\shell32_16761-7.png"));

					JMenuItem open = new JMenuItem("Open Config");
					file.add(open);

					JMenuItem exit = new JMenuItem("Exit");
					file.add(exit);


					exit.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent a)
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
						public void actionPerformed(ActionEvent a)
						{
							configWindow.setVisible(true);
						}
					});
				}
				JMenu about = new JMenu("About");
				about.setMnemonic(KeyEvent.VK_A);
				menuBar.add(about);

				about.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent me)
					{
						aboutWindow.setVisible(true);
					}
				});

			}
			JButton activate = new JButton("Activate");
			activate.setBounds(5,5,95,30);
			window.add(activate);

			JButton deactivate = new JButton("Deactivate");
			deactivate.setBounds(5,40,95,30);
			window.add(deactivate);

			window.add(Box.createRigidArea(new Dimension(700, 500)));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.pack();
			window.setResizable(false);
			window.setVisible(true);

			activate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					ping.play();
					clicker.restart();
					activate.setEnabled(false);
				}
			});
			deactivate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					boohw.play();
					clicker.clearInterval();
					activate.setEnabled(true);
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