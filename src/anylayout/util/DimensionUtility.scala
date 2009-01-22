package anylayout.util;

import java.awt.Dimension;

object DimensionUtility {
 def multiply(first: Dimension, second: Int) = new Dimension(first.width * second, first.height * second)
 def divide(first: Dimension, second: Dimension) = new Dimension(first.width/second.width, first.height/second.height)
}
