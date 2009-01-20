package anylayout.extras;

import anylayout.Constraint;
import anylayout.LayoutContext;
import fj.F;

import java.awt.Component;

public final class ConstraintBuilder
{
	public static ConstraintBuilderStage3 buildWithSameLeftAsTop(final F<LayoutContext, Integer> leftAndTop)
	{
		return buildConstraint().setLeft(leftAndTop).setTop(leftAndTop);
	}

	public static ConstraintBuilderStage1 buildConstraint()
	{
		return new ConstraintBuilderStage1()
		{
			public ConstraintBuilderStage2 setLeft(final F<LayoutContext, Integer> left)
			{
				return new ConstraintBuilderStage2()
				{
					public ConstraintBuilderStage3 setTop(final F<LayoutContext, Integer> top)
					{
						return new ConstraintBuilderStage3()
						{
							public ConstraintBuilderStage4 setWidth(final F<LayoutContext, Integer> width)
							{
								return new ConstraintBuilderStage4()
								{
									public Constraint setHeight(final F<LayoutContext, Integer> height)
									{
										return new Constraint()
										{
											public F<LayoutContext, Integer> getLeft()
											{
												return left;
											}

											public F<LayoutContext, Integer> getTop()
											{
												return top;
											}

											public F<LayoutContext, Integer> getWidth()
											{
												return width;
											}

											public F<LayoutContext, Integer> getHeight()
											{
												return height;
											}
										};
									}
								};
							}
						};
					}
				};
			}
		};
	}

	public static final F<LayoutContext, Integer> preferredSize=new F<LayoutContext, Integer>()
	{
		public Integer f(final LayoutContext layoutContext)
		{
			return layoutContext.getPreferredSize();
		}
	};

	public static F<LayoutContext, Integer> fill()
	{
		return new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext key)
			{
				return key.getParentSize();
			}
		};
	}

	public static <T> F<T, Integer> minimum(final F<T,Integer> one, final F<T,Integer> two)
	{
		return new F<T, Integer>()
		{
			public Integer f(final T input)
			{
				return Math.min(one.f(input),two.f(input));
			}
		};
	}

	public static F<LayoutContext, Integer> after(final Component component)
	{
		return new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext layoutContext)
			{
				return layoutContext.getLayoutInfo(component).getFarOffset();
			}
		};
	}
}