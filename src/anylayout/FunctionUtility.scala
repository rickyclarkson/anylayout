package anylayout;

import fj.F;
import fj.data.Option;

object FunctionUtility {
 def constIfNothing[T <: AnyRef, R <: AnyRef](o: Option[T], or: R, f: F[T, R]): R = o.option(or, f)

 def minus[T](a: F[T, Integer], b: F[T, Integer]): F[T, Integer] = new F[T, Integer] { def f(t: T) = a.f(t).intValue - b.f(t).intValue }
}
