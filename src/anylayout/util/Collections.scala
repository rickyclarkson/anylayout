package anylayout.util

import java.util._

object Collections {
 def hashMap[K, V] = new HashMap[K, V]
 def max[T](function: T => Int, operands: T*) = operands.foldLeft(0)((x, y) => math.max(x, function(y).intValue))
 def arrayList[T] = new ArrayList[T]
 def map[T, R](function: T => R, original: Iterable[T]) = original.map(t => function(t))
}
