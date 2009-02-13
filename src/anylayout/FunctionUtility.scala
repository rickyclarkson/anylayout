package anylayout;

object FunctionUtility {
 def constIfNothing[T <: AnyRef, R <: AnyRef](o: Option[T], or: R, f: T => R): R = o match { case Some(s) => f(s)
                                                                                             case None => or }

 def minus[T](a: T => Int, b: T => Int): T => Int = t => a(t).intValue - b(t).intValue
}
