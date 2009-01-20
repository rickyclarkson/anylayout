package anylayout.extras;

import anylayout.LayoutContext;
import anylayout.SizeCalculator;
import static anylayout.util.Collections.max;
import anylayout.util.DimensionUtility;
import fj.F;
import fj.P2;
import static fj.P.p;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class PercentConstraintsUtility
{
	private static final Component[] emptyComponents=new Component[0];

	public static PercentConstraints newInstance(final Container container)
	{
		final F<P2<Component,Dimension>,Dimension> getPreferredParentSize=new F<P2<Component,Dimension>,Dimension>()
		{
			public Dimension f(final P2<Component, Dimension> pair)
			{
                            final Dimension preferredSize=DimensionUtility.multiply(pair._1().getPreferredSize(),100);
				return DimensionUtility.divide(preferredSize,pair._2());
			}
		};

		final Map<Component,P2<Component,Dimension>> constraints=new HashMap<Component,P2<Component,Dimension>>();

		return new PercentConstraints()
		{
			public void add(final Component component,final int left,final int top,final int width,final int height,final boolean stretchWidth,final boolean stretchHeight)
			{
				final P2<Component,Dimension> pair=p(component,new Dimension(width,height));
				constraints.put(component,pair);

				container.add(component, ConstraintBuilder.buildConstraint().setLeft(new F<LayoutContext, Integer>()
				{
					public Integer f(final LayoutContext context)
					{
						return context.getParentSize()*left/100;
					}
				}).setTop(new F<LayoutContext, Integer>()
				{
					public Integer f(final LayoutContext context)
					{
						return context.getParentSize()*top/100;
					}
				}).setWidth(percentSize(stretchWidth, width)).setHeight(percentSize(stretchHeight, height)));
			}

			public SizeCalculator getSizeCalculator()
			{
				return new SizeCalculator()
				{
					public int getHeight()
					{
						return getASize(new F<Dimension,Integer>()
						{
							public Integer f(final Dimension dimension)
							{
								return dimension.height;
							}
						});
					}

					private int getASize(final F<Dimension,Integer> dimension)
					{
						return max(new F<Component,Integer>()
						{
							public Integer f(final Component component)
                                                        {
								return dimension.f(getPreferredParentSize.f(constraints.get(component)));
                                                        }							
						},constraints.keySet().toArray(emptyComponents));
					}

					public int getWidth()
					{
						return getASize(new F<Dimension,Integer>()
						{
							public Integer f(final Dimension dimension)
							{
								return dimension.width;
							}
						});
					}
				};
			}                        	
		};
	}

	private static F<LayoutContext,Integer> percentSize(final boolean stretch, final int size)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				if (stretch)
					return context.getParentSize()*size/100;

				return Math.min(context.getParentSize()*size/100,context.getPreferredSize());
			}
		};
	}
}