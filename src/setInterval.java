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

	private boolean isRunning = false;

	setInterval(Runnable call, long milliseconds)
	{
		callback = call;
		period = milliseconds;
	}

	public void clearInterval()
	{
		try
		{
			if(isRunning || !endHandle.isCancelled())
			{
				endHandle.cancel(true);
				isRunning = false;
			}
		}
		catch(java.lang.NullPointerException ignored) {}
	}

	public void restart()
	{
		if(!isRunning)
		{
			endHandle = scheduler.scheduleAtFixedRate(callback, period, period, TimeUnit.MILLISECONDS);
			isRunning = true;
		}
	}

	public void setPeriod(long delay)
	{
		if(!isRunning)
			period = delay;
	}
}