package anylayout.examples;

import static anylayout.util.WindowListenerBuilder.closed;
import fj.F;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import static java.util.logging.Level.FINEST;
import java.util.logging.Logger;
import static java.util.logging.Logger.getAnonymousLogger;

public class Examples
{
	public static void main(final String[] args)
	{
		final Logger logger=getAnonymousLogger();
		final Handler consoleHandler=new ConsoleHandler();
		consoleHandler.setLevel(FINEST);

		logger.addHandler(consoleHandler);
		logger.setLevel(FINEST);
		
		final WindowListener listener=closed(new F<WindowEvent, Void>()
		{
			public Void f(final WindowEvent event)
			{
				final Runnable exceptionHandler=new Runnable()
				{
					public void run()
					{
						logger.severe("Something went wrong");
					}
				};

				TextExample.f(exceptionHandler);
                                return null;
			}
		});

		BorderLayoutExample.run(listener);
	}
}