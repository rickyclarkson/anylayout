package anylayout.extras;

import static anylayout.AnyLayout.useAnyLayout;

import java.awt.Component;
import java.awt.Container;
import java.util.Iterator;
import java.util.NoSuchElementException;

import anylayout.Constraint;
import anylayout.LayoutContext;
import anylayout.SizeCalculator;
import fj.F;
import fj.data.Option;
import static fj.Function.constant;
import static anylayout.FunctionUtility.constIfNothing;
import anylayout.FunctionUtility;

import fj.Effect;

public final class BorderLayoutEmulationUtility
{
	public static BorderLayoutEmulation useBorderLayoutEmulation(final Container container)
	{
		final Option<Component> nothing=Option.none();
		return useBorderLayoutEmulation(container,nothing,nothing,nothing,nothing,nothing);
	}

	private static BorderLayoutEmulation useBorderLayoutEmulation(final Container container,final Option<Component> north,final Option<Component> south,final Option<Component> east,final Option<Component> west,final Option<Component> centre)
	{
		container.removeAll();

		final SizeCalculator sizeCalculator=new SizeCalculator()
		{
			public int getHeight()
			{
				return sum(zeroReserve(getPreferredHeight),northSouth())+max(zeroReserve(getPreferredHeight),westCentreEast());
			}

			private Iterable<Option<Component>> westCentreEast()
			{
				return cons(west,sequence(centre,east));
			}

			private Iterable<Option<Component>> northSouth()
			{
				return sequence(north,south);
			}

			public int getWidth()
			{
				final int width=max(zeroReserve(getPreferredWidth()),northSouth());
				final int centreWidth=sum(zeroReserve(getPreferredWidth()),westCentreEast());
				return Math.max(width,centreWidth);
			}
		};

		useAnyLayout(container,0.5f,0.5f,sizeCalculator,new F<Component,Constraint>()
		{
			public Constraint f(final Component component)
			{
				throw new UnsupportedOperationException();
			}
		});

		final F<LayoutContext,Integer> zero=constant(0);

		final F<LayoutContext,Integer> heightOfNorthOrSouth=new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				final int numOfComponents=(north==null ? 0 : 1)+(south==null ? 0 : 1)+(east!=null||centre!=null||west!=null ? 1 : 0);

				return Math.min(context.getPreferredSize(),context.getParentSize()/numOfComponents);
			}
		};

		final F<Component,F<LayoutContext,Integer>> getStart=new F<Component,F<LayoutContext,Integer>>()
		{
			public F<LayoutContext,Integer> f(final Component component)
			{
				return new F<LayoutContext,Integer>()
				{
					public Integer f(final LayoutContext context)
					{
						return context.getLayoutInfo(component).getFarOffset();
					}
				};
			}
		};

		final F<Option<Component>,F<LayoutContext,Integer>> getStartMaybe=new F<Option<Component>,F<LayoutContext,Integer>>()
		{
			public F<LayoutContext,Integer> f(final Option<Component> maybe)
			{
				return constIfNothing(maybe,zero,getStart);
			}
		};

		final F<LayoutContext,Integer> widthOfEastOrWest=new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				final int multiplier=sizeCalculator.getWidth()-context.getParentSize();
				final Integer preferredSize=context.getPreferredSize();
				return Math.min(preferredSize-multiplier*preferredSize/sizeCalculator.getWidth(),preferredSize);
			}
		};

		north.foreach(new Effect<Component>()
		{
			public void e(final Component component)
			{
				container.add(component,ConstraintBuilder.buildConstraint().setLeft(zero).setTop(zero).setWidth(parentSize).setHeight(heightOfNorthOrSouth));
			}
		});

		south.foreach(new Effect<Component>()
		{
			public void e(final Component component)
			{
				container.add(component,ConstraintBuilder.buildConstraint().setLeft(zero).setTop(parentSizeMinus(heightOfNorthOrSouth)).setWidth(parentSize).setHeight(heightOfNorthOrSouth));
			}
		});

		final F<LayoutContext,Integer> parentSize1=new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext layoutContext)
			{
				return layoutContext.getParentSize();
			}
		};

		final F<LayoutContext,Integer> heightOfCentral=new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				int height=FunctionUtility.minus(parentSize1,getFarOffset.f(north)).f(context);

				height-=constIfNothing(centre,0,new F<Component,Integer>()
				{
					public Integer f(final Component component)
					{
						return context.getParentSize()-context.getLayoutInfo(component).getOffset();
					}
				});

				return height;
			}
		};

		east.foreach(new Effect<Component>()
		{
			public void e(final Component component)
			{
				container.add(component,ConstraintBuilder.buildConstraint().setLeft(parentSizeMinus(widthOfEastOrWest)).setTop(getStartMaybe.f(north)).setWidth(widthOfEastOrWest).setHeight(heightOfCentral));
			}
		});

		west.foreach(new Effect<Component>()
		{
			public void e(final Component component)
			{
				container.add(component,ConstraintBuilder.buildConstraint().setLeft(zero).setTop(getStartMaybe.f(north)).setWidth(widthOfEastOrWest).setHeight(heightOfCentral));
			}
		});

		centre.foreach(new Effect<Component>()
		{
			public void e(final Component component)
			{
				final F<Integer,Integer> ifLessThanZero=new F<Integer,Integer>()
				{
					public Integer f(final Integer integer)
					{
						return -integer;
					}
				};

				container.add(component,ConstraintBuilder.buildConstraint().setLeft(getStartMaybe.f(west)).setTop(getStartMaybe.f(north)).setWidth(fillBetween(west,east,ifLessThanZero)).setHeight(heightOfCentral));
			}
		});

		return new BorderLayoutEmulation()
		{
			public BorderLayoutEmulation setNorth(final Option<Component> north1)
			{
				return useBorderLayoutEmulation(container,north1,south,east,west,centre);
			}

			public BorderLayoutEmulation setSouth(final Option<Component> south1)
			{
				return useBorderLayoutEmulation(container,north,south1,east,west,centre);
			}

			public BorderLayoutEmulation setEast(final Option<Component> east1)
			{
				return useBorderLayoutEmulation(container,north,south,east1,west,centre);
			}

			public BorderLayoutEmulation setWest(final Option<Component> west1)
			{
				return useBorderLayoutEmulation(container,north,south,east,west1,centre);
			}

			public BorderLayoutEmulation setCentre(final Option<Component> centre1)
			{
				return useBorderLayoutEmulation(container,north,south,east,west,centre1);
			}
		};
	}

	private static final F<Option<Component>,F<LayoutContext,Integer>> getFarOffset=new F<Option<Component>,F<LayoutContext,Integer>>()
	{
		public F<LayoutContext,Integer> f(final Option<Component> maybe)
		{
			final F<LayoutContext,Integer> constantZero=constant(0);
			return constIfNothing(maybe,constantZero,new F<Component,F<LayoutContext,Integer>>()
			{
				public F<LayoutContext,Integer> f(final Component component)
				{
					return new F<LayoutContext,Integer>()
					{
						public Integer f(final LayoutContext layoutContext)
						{
							return layoutContext.getLayoutInfo(component).getFarOffset();
						}
					};
				}
			});
		}
		/*	return new F<LayoutContext,Integer>()
		 {
		 public Integer f(final LayoutContext context)
		 {
		 return maybe.accept(new F<Component,Integer>()
		 {
		 public Integer f(final Component component)
		 {
		 return context.getLayoutInfo(component).getFarOffset();
		 }
		 },0);
		 }
		 };
		 }*/
	};

	public static F<LayoutContext,Integer> parentSizeMinus(final F<LayoutContext,Integer> function)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext layoutContext)
			{
				return layoutContext.getParentSize()-function.f(layoutContext);
			}
		};
	}

	private static <T> Iterable<T> sequence(final T first,final T second)
	{
		return new Iterable<T>()
		{
			public Iterator<T> iterator()
			{
				return new Iterator<T>()
				{
					int position;

					public boolean hasNext()
					{
						return 2>position;
					}

					public T next()
					{
						final T answer;

						if (0==position)
							answer=first;
						else
							if (1==position)
								answer=second;
							else
								throw new NoSuchElementException();

						position++;

						return answer;
					}

					public void remove()
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	private static <T> Iterable<T> cons(final T element,final Iterable<T> list)
	{
		return new Iterable<T>()
		{
			public Iterator<T> iterator()
			{
				return new Iterator<T>()
				{
					boolean readFirst;

					final Iterator<T> iterator=list.iterator();

					public boolean hasNext()
					{
						return readFirst ? iterator.hasNext() : true;
					}

					public T next()
					{
						if (!readFirst)
						{
							readFirst=true;
							return element;
						}

						return iterator.next();
					}

					public void remove()
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	private static F<Component,Integer> getPreferredWidth()
	{
		return new F<Component,Integer>()
		{
			public Integer f(final Component component)
			{
				return component.getPreferredSize().width;
			}
		};
	}

	private static <T> F<Option<T>,Integer> zeroReserve(final F<T,Integer> function)
	{
		return new F<Option<T>,Integer>()
		{
			public Integer f(final Option<T> maybe)
			{
                            return maybe.option(0, function);
			}
		};
	}

	public static final F<Component,Integer> getPreferredHeight=new F<Component,Integer>()
	{
		public Integer f(final Component component)
		{
			return component.getPreferredSize().height;
		}
	};

	private static <Key> int sum(final F<Key,Integer> get,final Iterable<Key> keys)
	{
		int total=0;

		for (final Key key: keys)
			total+=get.f(key);

		return total;
	}

	private static <Key> int max(final F<Key,Integer> get,final Iterable<? extends Key> keys)
	{
		int max=0;

		for (final Key key: keys)
			max=Math.max(max,get.f(key));

		return max;
	}

	private static F<LayoutContext,Integer> fillBetween(final Option<Component> from,final Option<Component> to,final F<Integer,Integer> ifLessThanZero)
	{
		return new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				final int farOffset=getFarOffset.f(from).f(context);

				int width=context.getParentSize()-farOffset;

				width-=to.option(0, new F<Component,Integer>()
				{
					public Integer f(final Component component)
					{
						return context.getLayoutInfo(component).getFarOffset()-context.getLayoutInfo(component).getOffset();
					}
				});

				if (0>width)
					return ifLessThanZero.f(width);

				return width;
			}
		};
	}

	public static final F<LayoutContext,Integer> parentSize=new F<LayoutContext,Integer>()
	{
		public Integer f(final LayoutContext context)
		{
			return context.getParentSize();
		}
	};
}