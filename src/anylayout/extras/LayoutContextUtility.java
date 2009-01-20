package anylayout.extras;

import anylayout.LayoutContext;
import fj.F;

import java.awt.Component;

public class LayoutContextUtility
{
	public static final F<LayoutContext, Integer> getParentSize=new F<LayoutContext,Integer>()
	{
		public Integer f(final LayoutContext layoutContext)
		{
			return layoutContext.getParentSize();
		}
	};
	
	public static final F<LayoutContext, Integer> getPreferredSize=new F<LayoutContext,Integer>()
	{
		public Integer f(final LayoutContext layoutContext)
		{
			return layoutContext.getPreferredSize();
		}
	};

	public static F<LayoutContext,Integer> getFarOffset(final Component component)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getLayoutInfo(component).getFarOffset();
			}
		};
	}
}