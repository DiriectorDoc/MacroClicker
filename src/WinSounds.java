import java.awt.Toolkit;

public class WinSounds
{
	/*
	 * win.sound.asterisk
	 * win.sound.close
	 * win.sound.default
	 * win.sound.exclamation
	 * win.sound.exit
	 * win.sound.hand
	 * win.sound.maximize
	 * win.sound.menuCommand
	 * win.sound.menuPopup
	 * win.sound.minimize
	 * win.sound.open
	 * win.sound.question
	 * win.sound.restoreDown
	 * win.sound.restoreUp
	 * win.sound.start
	 * */
	public static void play(String sound)
	{
		((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty(sound)).run();
	}
}
