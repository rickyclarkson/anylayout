package anylayout.util

import java.util.Map

object MapUtility {
 def get[K, V](map: Map[K, V], key: K, defaultValue: K => V) = { val value = map.get(key)
                                                                 if (null==value) defaultValue(key) else value }
}
