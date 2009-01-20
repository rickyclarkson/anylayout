package anylayout.util;

import fj.F;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowListenerBuilder
{
    public static WindowListener closed(final F<WindowEvent, Void> callback)
	{
		return new WindowListener()
		{
			public void windowOpened(final WindowEvent e)
			{
			}

			public void windowClosing(final WindowEvent e)
			{
			}

			public void windowClosed(final WindowEvent e)
			{
				callback.f(e);
			}

			public void windowIconified(final WindowEvent e)
			{
			}

			public void windowDeiconified(final WindowEvent e)
			{
			}

			public void windowActivated(final WindowEvent e)
			{
			}

			public void windowDeactivated(final WindowEvent e)
			{
			}
		};
	}
}