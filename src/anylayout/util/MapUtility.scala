package anylayout.util

import fj.F

import java.util.Map

object MapUtility {
 def get[K, V](map: Map[K, V], key: K, defaultValue: F[K, V]) = { val value = map.get(key)
                                                                  if (null==value) defaultValue.f(key) else value }
}
