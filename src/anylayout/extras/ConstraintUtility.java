package anylayout.extras;

import static anylayout.FunctionUtility.minus;
import static anylayout.extras.LayoutContextUtility.getParentSize;
import static anylayout.extras.ConstraintBuilder.preferredSize;
import static anylayout.extras.BorderLayoutEmulationUtility.parentSizeMinus;
import anylayout.Constraint;
import anylayout.LayoutContext;
import static anylayout.extras.ConstraintBuilder.buildConstraint;
import fj.F;
import static fj.Function.constant;

import java.awt.Component;

import fj.Function;
import fj.F;

/**
 * Some implementations of {@link Constraint} for common cases.
 */
public final class ConstraintUtility
{
	private static final F<Integer, F<LayoutContext, Integer>> intConstant=new F<Integer, F<LayoutContext, Integer>>()
	{
		public F<LayoutContext, Integer> f(final Integer integer)
		{
			return Function.constant(integer);
		}
	};

	/**
	 * Lays out a component <code>left</code> pixels from the left of the container and
	 * <code>top</code> pixels from the top of the container.
	 */
	public static Constraint topLeft(final F<LayoutContext,Integer> top, final F<LayoutContext,Integer> left)
	{
		return buildConstraint().setLeft(left).setTop(top).setWidth(preferredSize).setHeight(preferredSize);
	}

	/**
	 * Lays out a component <code>left</code> pixels from the left of the container and
	 * <code>farSide</code> pixels from the farSide of the container.
	 */
	public static Constraint bottomLeft(final F<LayoutContext,Integer> bottom, final F<LayoutContext,Integer> left)
	{
		return topLeft(parentSizeMinus(preferredSizePlus(bottom)),left);
	}

	private static F<LayoutContext, Integer> preferredSizePlus(final F<LayoutContext, Integer> function)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext layoutContext)
			{
				return layoutContext.getPreferredSize()+function.f(layoutContext);
			}
		};
	}

	/**
	 * Lays out a component in the centre of the container horizontally, and
	 * <code>farSide</code> pixels from the farSide of the container.
	 */
	public static Constraint bottomCentre(final F<LayoutContext, Integer> bottom)
	{
		return buildPartOfCentreConstraint().setTop(bottomOf(bottom)).setWidth(preferredSize).setHeight(preferredSize);
	}

	private static ConstraintBuilderStage2 buildPartOfCentreConstraint()
	{
		return buildConstraint().setLeft(getLeftCoordinateAsIfCentre());
	}

	private static F<LayoutContext, Integer> getLeftCoordinateAsIfCentre()
	{
		return new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return (context.getParentSize()-context.getPreferredSize())/2;
			}
		};

	}

	/**
	 * Lays out a component in the centre of the container horizontally, and
	 * <code>top</code> pixels from the top of the container.
	 */
	public static Constraint topCentre(final int top)
	{
		return buildPartOfCentreConstraint().setTop(intConstant.f(top)).setWidth(preferredSize).setHeight(preferredSize);
	}

	/**
	 * Lays out a component <code>right</code> pixels from the right-hand border of the container, and
	 * <code>farSide</code> pixels from the farSide of the container.
	 */
	public static Constraint bottomRight(final F<LayoutContext, Integer> bottom, final F<LayoutContext, Integer> right)
	{
		return buildConstraint().setLeft(farSide(right)).setTop(farSide(bottom)).setWidth(preferredSize).setHeight(preferredSize);
	}

	private static F<LayoutContext, Integer> bottomOf(final F<LayoutContext, Integer> bottom)
	{
		return ConstraintUtility.farSide(bottom);
	}

	/**
	 * Lays out a component <code>right</code> pixels from the right-hand border of the container, and
	 * <code>top</code> pixels from the top of the container.
	 */
	public static Constraint topRight(final F<LayoutContext,Integer> top, final F<LayoutContext,Integer> right)
	{
		return buildConstraint().setLeft(new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getParentSize()-context.getPreferredSize()-right.f(context);
			}
		}).setTop(top).setWidth(preferredSize).setHeight(preferredSize);
	}

	/**
	 * Gets the leftmost/topmost edge of something against the right or bottom border.
	 */
	private static F<LayoutContext, Integer> farSide(final F<LayoutContext,Integer> padding)
	{
		return minus(minus(getParentSize,padding),LayoutContextUtility.getPreferredSize);
	}

	public static F<Component,Constraint> typicalDefaultConstraint(final Runnable exceptionHandler)
	{
		return new F<Component, Constraint>()
		{
			public Constraint f(final Component component)
			{
				exceptionHandler.run();

				final F<LayoutContext, Integer> zero=constant(0);

				return buildConstraint().setLeft(zero).setTop(zero).setWidth(preferredSize).setHeight(preferredSize);
			}
		};
	}
}