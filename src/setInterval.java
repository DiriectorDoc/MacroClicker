import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class setInterval
{
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private ScheduledFuture<?> endHandle;
	
	setInterval(Runnable callback, long milliseconds)
	{
		endHandle = scheduler.scheduleAtFixedRate(callback, milliseconds, milliseconds, TimeUnit.MILLISECONDS);
	}
	
	public void clearInterval()
	{
		endHandle.cancel(true);
	}
}
