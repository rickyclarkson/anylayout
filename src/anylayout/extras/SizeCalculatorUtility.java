package anylayout.extras;

import anylayout.SizeCalculator;
import fj.F;

import java.awt.Component;

public final class SizeCalculatorUtility
{
	public static SizeCalculator absoluteSize(final int width,final int height)
	{
		return new SizeCalculator()
		{
			public int getWidth()
			{
				return width;
			}

			public int getHeight()
			{
				return height;
			}
		};
	}

	public static F<Component,Integer> getPreferredWidth()
	{
		return new F<Component,Integer>()
		{
			public Integer f(final Component component)
                        {
				return component.getPreferredSize().width;
		        }
		};
	}

	public static F<Component,Integer> getPreferredHeight()
	{
		return new F<Component,Integer>()
		{
			public Integer f(final Component component)
			{
				return component.getPreferredSize().height;
			}
		};
	}

	public static SizeCalculator constrain(final SizeCalculator sizeCalculator,final int maxWidth,final int maxHeight)
        {
		return new SizeCalculator()
		{
			public int getHeight()
                        {
				return Math.min(maxHeight,sizeCalculator.getHeight());
                        }

			public int getWidth()
                        {
				return Math.min(maxWidth,sizeCalculator.getWidth());
                        }			
		};
        }
}