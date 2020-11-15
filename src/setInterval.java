import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class setInterval
{
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private ScheduledFuture<?> endHandle;
	
	private Runnable callback;
	
	private long period;
	
	setInterval(Runnable call, long milliseconds)
	{
		callback = call;
		period = milliseconds;
		restart();
	}
	
	public void clearInterval()
	{
		endHandle.cancel(true);
	}
	
	public void restart()
	{
		endHandle = scheduler.scheduleAtFixedRate(callback, period, period, TimeUnit.MILLISECONDS);
	}
}
