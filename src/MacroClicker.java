import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;

import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MacroClicker
{
	@SuppressWarnings("unused")
	private static final short version = 1;
	public static volatile boolean keyPressed = false;

	public static setInterval clicker;

	private static Robot bot;

	private static int delay = 30,
					   clicksPerSecond = 33;
	
	private static Audio ping = new Audio(resource("/resources/sounds/ping.wav")),
						 boohw = new Audio(resource("/resources/sounds/boohw.wav"));
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			bot = new Robot();
			GlobalScreen.registerNativeHook();
		}
		catch(AWTException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
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
		}, delay);
	}

	public static void build()
	{
		/* ~ ~ ~ Start of Config Window creation ~ ~ ~ */
		AltWindow configWindow = new AltWindow("Settings", 600, 200);

			JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 33);

			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setPreferredSize(new Dimension(550, 50));
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
			configWindow.add(slider, BorderLayout.PAGE_END);

			JPanel radioButtonPanel = new JPanel();
			radioButtonPanel.setPreferredSize(new Dimension(170, 60));
			radioButtonPanel.add(Box.createRigidArea(new Dimension(100, 80)),  BorderLayout.PAGE_START);
			
			Box radioButtons = Box.createHorizontalBox();

				JRadioButton cps = new JRadioButton("Clicks/Second"),
							 dms = new JRadioButton("Delay (ms)");
				cps.setMnemonic(KeyEvent.VK_C);
				dms.setMnemonic(KeyEvent.VK_D);
				cps.setActionCommand("Clicks/Second");
				dms.setActionCommand("Delay (ms)");
				cps.setSelected(true);
				
				radioButtons.add(cps);
				radioButtons.add(dms);
				radioButtonPanel.add(radioButtons,  BorderLayout.LINE_START);

			configWindow.add(radioButtonPanel, BorderLayout.LINE_START);
			
			JPanel textFieldPanel = new JPanel();
			textFieldPanel.setPreferredSize(new Dimension(100, 60));
			textFieldPanel.add(Box.createRigidArea(new Dimension(1, 80)),  BorderLayout.PAGE_START);
			
				NumberField textField = new NumberField(10);
				textField.setValue(clicksPerSecond);
				textField.setPreferredSize(new Dimension(100, 20));
				textFieldPanel.add(textField,  BorderLayout.LINE_END);
				
			configWindow.add(textFieldPanel, BorderLayout.LINE_END);
			
		cps.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				cps.setSelected(true);
				dms.setSelected(false);
				textField.setValue(clicksPerSecond);
			}
		});
		dms.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				cps.setSelected(false);
				dms.setSelected(true);
				textField.setValue(delay);
			}
		});

		configWindow.build();
		/* ~ ~ ~ End of Config Window creation ~ ~ ~ */


		/* ~ ~ ~ Start of About Window creation ~ ~ ~ */
		JDialog aboutWindow = new JDialog();
		aboutWindow.setPreferredSize(new Dimension(400, 400));
		aboutWindow.setModal(true);
		aboutWindow.setAlwaysOnTop(true);
		aboutWindow.setModalityType(ModalityType.APPLICATION_MODAL);

			JTextPane textPane = new JTextPane();
			textPane.setContentType("text/html");
			textPane.setEditable(false);
			try
			{
				textPane.setPage(resource("/resources/about.html"));
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JScrollPane scrollPane = new JScrollPane(textPane);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			aboutWindow.add(scrollPane);

		aboutWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aboutWindow.pack();
		/* ~ ~ ~ End of About Window creation ~ ~ ~ */
		
		
		/* ~ ~ ~ Start of Main Window creation ~ ~ ~ */
		JFrame window = new JFrame("Macro Clicker");
		
			JMenuBar menuBar = new JMenuBar();
			window.setJMenuBar(menuBar);
			
				JMenu file = new JMenu("File");
				file.setMnemonic(KeyEvent.VK_F);
				menuBar.add(file);
				
					JMenuItem save = new JMenuItem("Save Config");
					save.setIcon(new ImageIcon(resource("/resources/icons/shell32_16761-7.png")));
					file.add(save);

					JMenuItem open = new JMenuItem("Open Config");
					open.setIcon(new ImageIcon(resource("/resources/icons/shell32_255-7.png")));
					file.add(open);

					JMenuItem exit = new JMenuItem("Exit");
					exit.setIcon(new ImageIcon(resource("/resources/icons/shell32_240-7.png")));
					exit.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent a)
						{
							System.exit(0);
						}
					});
					file.add(exit);
				
				JMenu settings = new JMenu("Settings");
				settings.setMnemonic(KeyEvent.VK_S);
				menuBar.add(settings);
				
					JMenuItem config = new JMenuItem("Cinfigure");
					config.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent a)
						{
							configWindow.setVisible(true);
						}
					});
					settings.add(config);
				
				JMenu about = new JMenu("About");
				about.setMnemonic(KeyEvent.VK_A);
				about.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent me)
					{
						aboutWindow.setVisible(true);
					}
				});
				menuBar.add(about);

			
			JButton activate = new JButton("Activate");
			activate.setBounds(5,5,95,30);
			activate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					ping.play();
					clicker.restart();
					activate.setEnabled(false);
					config.setEnabled(false);
				}
			});
			window.add(activate);

			JButton deactivate = new JButton("Deactivate");
			deactivate.setBounds(5,40,95,30);
			deactivate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					boohw.play();
					clicker.clearInterval();
					activate.setEnabled(true);
					config.setEnabled(true);
				}
			});
			window.add(deactivate);

		window.add(Box.createRigidArea(new Dimension(700, 500)));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<Image> icons = new ArrayList<Image>();
		for(int i = 0; i < 4; i++)
			icons.add(new ImageIcon(resource("/resources/icons/icon-" + i + ".png")).getImage());
		window.setIconImages(icons);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		/* ~ ~ ~ End of Main Window creation ~ ~ ~ */
	}

	public static void click()
	{
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	private static URL resource(String path)
	{
		return MacroClicker.class.getResource(path);
	}
}