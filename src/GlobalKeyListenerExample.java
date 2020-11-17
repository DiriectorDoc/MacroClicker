import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerExample implements NativeKeyListener
{
	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{
		MacroClicker.keyPressed = e.getKeyCode() == NativeKeyEvent.VC_C;
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		MacroClicker.keyPressed = false;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e)
	{
		
	}
}