package anylayout.util;

import fj.F;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Collections
{
	public static <K,V> Map<K,V> hashMap()
	{
		return new HashMap<K,V>();
	}

	public static <T> Integer max(final F<T,Integer> function,final T... operands)
        {
		int result=0;

		for (final T item: operands)
			result=Math.max(result,function.f(item));

		return result;
        }

	public static <T> List<T> arrayList()
	{
		return new ArrayList<T>();
	}

	public static <T,R> Iterable<R> map(final F<T,R> function, final Iterable<T> original)
	{
		return new Iterable<R>()
		{
			public Iterator<R> iterator()
			{
				return new Iterator<R>()
				{
					Iterator<T> wrapped=original.iterator();

					public boolean hasNext()
					{
						return wrapped.hasNext();
					}

					public R next()
					{
						return function.f(wrapped.next());
					}

					public void remove()
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}