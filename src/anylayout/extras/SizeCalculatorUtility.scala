package anylayout.extras

import anylayout.SizeCalculator
import java.awt.Component

object SizeCalculatorUtility {
 case class absoluteSize(override val getWidth: Int, override val getHeight: Int) extends SizeCalculator
 val getPreferredWidth: Component => Int = _.getPreferredSize.width
 val getPreferredHeight: Component => Int = _.getPreferredSize.height
 def constrain(sizeCalculator: SizeCalculator, maxWidth: Int, maxHeight: Int) = new SizeCalculator {
  def getHeight = Math.min(maxHeight, sizeCalculator.getHeight)
  def getWidth = Math.min(maxWidth, sizeCalculator.getWidth)
 }
}
