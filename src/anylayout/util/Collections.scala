package anylayout.util

import fj.F

import java.util._

object Collections {
 def hashMap[K, V] = new HashMap[K, V]
 def max[T](function: F[T, Integer], operands: T*) = operands.foldLeft(0)((x, y) => Math.max(x, function.f(y).intValue))
 def arrayList[T] = new ArrayList[T]
 def map[T, R](function: F[T, R], original: Iterable[T]) = original.map(t => function.f(t))
}
