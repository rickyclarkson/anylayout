package anylayout.extras

import anylayout.SizeCalculator
import fj.F

import java.awt.Component

object SizeCalculatorUtility {
 case class absoluteSize(override val getWidth: Int, override val getHeight: Int) extends SizeCalculator
 val getPreferredWidth = new F[Component, Integer] { def f(component: Component) = component.getPreferredSize.width }
 val getPreferredHeight = new F[Component, Integer] { def f(component: Component) = component.getPreferredSize.height }
 def constrain(sizeCalculator: SizeCalculator, maxWidth: Int, maxHeight: Int) = new SizeCalculator {
  def getHeight = Math.min(maxHeight, sizeCalculator.getHeight)
  def getWidth = Math.min(maxWidth, sizeCalculator.getWidth)
 }
}
