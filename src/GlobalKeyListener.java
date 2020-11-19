import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener
{
	private static int lastKeyPressed;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{
		MacroClicker.keyPressed = (lastKeyPressed = e.getKeyCode()) == NativeKeyEvent.VC_C;
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		MacroClicker.keyPressed = false;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e)
	{
		return;
	}

	public static int getLastKeyCode()
	{
		return lastKeyPressed;
	}

	public static String getLastKeyText()
	{
		return NativeKeyEvent.getKeyText(lastKeyPressed);
	}
}