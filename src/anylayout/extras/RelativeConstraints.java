package anylayout.extras;

import anylayout.Constraint;
import anylayout.LayoutContext;
import anylayout.LayoutInfo;
import static anylayout.extras.ConstraintBuilder.buildConstraint;
import fj.F;

import java.awt.Component;

public final class RelativeConstraints
{
	public static Constraint above(final Component below,final int padding)
	{
		return buildConstraintSameLeftAs(below).setTop(new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getLayoutInfo(below).getOffset()-padding-context.getPreferredSize();
			}
		}).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize);
	}

	private static ConstraintBuilderStage2 buildConstraintSameLeftAs(final Component component)
	{
		return buildConstraint().setLeft(equalTo(component));
	}

	public static Constraint below(final Component above,final int padding)
	{
		return buildConstraintSameLeftAs(above).setTop(after(above, padding)).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize);
	}

	private static F<LayoutContext,Integer> after(final Component above,final int padding)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getLayoutInfo(above).getFarOffset()+padding;
			}
		};
	}

	public static Constraint rightOf(final Component left,final int padding)
	{
		return buildConstraint().setLeft(after(left, padding)).setTop(equalTo(left)).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize);
	}

	public static Constraint halfwayBetween(final Component one,final Component two)
	{
		return buildConstraint().setLeft(new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				final LayoutInfo oneInfo=context.getLayoutInfo(one);
				final LayoutInfo twoInfo=context.getLayoutInfo(two);

				return Math.max(0, (oneInfo.getFarOffset()+twoInfo.getOffset()-context.getPreferredSize())/2);
			}
		}).setTop(new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return (after(one, context.getLayoutInfo(two).getOffset()).f(context)-context.getPreferredSize())/2;
			}
		}).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize);
	}

	public static Constraint levelWith(final Component horizontal,final Component vertical)
	{
		return buildConstraintSameLeftAs(vertical).setTop(equalTo(horizontal)).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize);
	}

	private static F<LayoutContext,Integer> equalTo(final Component horizontal)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getLayoutInfo(horizontal).getOffset();
			}
		};
	}
}