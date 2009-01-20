package anylayout;

import anylayout.util.MapUtility;
import fj.F;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * A layout manager that delegates to user-defined {@link Constraint}s for all
 * its layout.
 */
public final class AnyLayout
{
	/**
	 * Makes the specified container use AnyLayout, with the specified
	 * alignments and size calculator.
	 * 
	 * @param container
	 *                the container that will be laid out using AnyLayout.
	 */
	public static void useAnyLayout(final Container container,final float alignmentX,final float alignmentY,final SizeCalculator calculator,final F<Component,Constraint> ifMissingConstraint)
	{
		final Map<Component,Constraint> constraints=new HashMap<Component,Constraint>();

		final LayoutManager2 layout=new LayoutManager2()
                {
                    public void addLayoutComponent(final Component component,final Object constraint)
                    {
				constraints.put(component,(Constraint)constraint);
			}

			public Dimension maximumLayoutSize(final Container target)
			{
				return preferredLayoutSize(target);
			}

			public float getLayoutAlignmentX(final Container target)
			{
				return alignmentX;
			}

			public float getLayoutAlignmentY(final Container target)
			{
				return alignmentY;
			}

			public void invalidateLayout(final Container target)
			{
			}

			public void addLayoutComponent(final String name,final Component comp)
			{
				throw new UnsupportedOperationException();
			}

			public void removeLayoutComponent(final Component component)
			{
				constraints.remove(component);
			}

			public Dimension preferredLayoutSize(final Container parent)
			{
				return new Dimension(calculator.getWidth(),calculator.getHeight());
			}

			public Dimension minimumLayoutSize(final Container parent)
			{
				return preferredLayoutSize(parent);
			}

			public void layoutContainer(final Container parent)
			{
				for (final Component component: parent.getComponents())
				{
					final LayoutInfo xInfo=getInfo(component,true);
					final LayoutInfo yInfo=getInfo(component,false);

					component.setBounds(xInfo.getOffset(),yInfo.getOffset(),getSize(xInfo),getSize(yInfo));
				}
			}

			private int getSize(final LayoutInfo info)
			{
				return info.getFarOffset()-info.getOffset();
			}

			public LayoutInfo getInfo(final Component component,final boolean isX)
			{
				final LayoutContext context=new LayoutContext()
				{
					public Integer getParentSize()
					{
						return isX ? container.getWidth() : container.getHeight();
					}

					public Integer getPreferredSize()
					{
						final Dimension preferredSize=component.getPreferredSize();

						return isX ? preferredSize.width : preferredSize.height;
					}

					public LayoutInfo getLayoutInfo(final Component component1)
					{
						return getInfo(component1,isX);
					}
				};

				final F<Component,Constraint> getConstraint=new F<Component,Constraint>()
				{
					public Constraint f(final Component component1)
					{
						return MapUtility.get(constraints,component1,ifMissingConstraint);
					}
				};

				return new LayoutInfo()
				{
					public Integer getOffset()
					{
						final Constraint constraint=getConstraint.f(component);

						return isX ? constraint.getLeft().f(context) : constraint.getTop().f(context);
					}

					public Integer getFarOffset()
					{
						final Constraint constraint=getConstraint.f(component);

						if (isX)
						{
                                                    // public static <T, U> F<T, U> foo(F<T, U> f, F<T, U> g, F2<T, T, T> h) { return new F<T, U>() { public U f(T t) { return h.f(f.f(t), g.f(t)); } }; }
							final F<LayoutContext,Integer> left=constraint.getLeft();
							final F<LayoutContext,Integer> width=constraint.getWidth();

							return left.f(context)+width.f(context);
						}

						return constraint.getTop().f(context)+constraint.getHeight().f(context);
					}
				};
			}
		};

		container.setLayout(layout);
	}
}