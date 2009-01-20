package anylayout.util;

import fj.F;

import java.util.Map;

public class MapUtility
{
	public static <K,V> V get(final Map<K,V> map,final K key,final F<K,V> defaultValue)
	{
		final V value=map.get(key);

		return null==value ? defaultValue.f(key) : value;
	}
}