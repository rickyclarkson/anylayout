package anylayout.extras

import anylayout._
import anylayout.util.Collections.max
import anylayout.util.DimensionUtility
import fj.F
import fj.P2
import fj.P.p

import java.awt._
import java.util._
import java.lang.Integer

object PercentConstraintsUtility {
 val emptyComponents=new Array[Component](0);

 def newInstance(container: Container) = {
  val getPreferredParentSize = new F[P2[Component, Dimension], Dimension] { def f(pair: P2[Component, Dimension]) = {
   val preferredSize = DimensionUtility.multiply(pair._1.getPreferredSize, 100)
   DimensionUtility.divide(preferredSize, pair._2) } }

  val constraints = new HashMap[Component, P2[Component, Dimension]]
  new PercentConstraints {
   def add(component: Component, left: Int, top: Int, width: Int, height: Int, stretchWidth: Boolean, stretchHeight: Boolean) = {
    val pair = p(component, new Dimension(width, height))
    constraints.put(component, pair)
    container.add(component, ConstraintBuilder.buildConstraint.setLeft(
     new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getParentSize * left / 100 }).setTop(
      new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getParentSize * top / 100 }).setWidth(
       percentSize(stretchWidth, width)).setHeight(percentSize(stretchHeight, height)))
   }

   def getSizeCalculator = new SizeCalculator {
    def getHeight = getASize(new F[Dimension, Integer] { def f(d: Dimension) = d.height }).intValue
    def getASize(dimension: F[Dimension, Integer]) = max(new F[Component, Integer] { def f(c: Component) =
     dimension.f(getPreferredParentSize.f(constraints.get(c))) })
    def getWidth = getASize(new F[Dimension, Integer] { def f(dimension: Dimension) = dimension.width }).intValue
   }
  }
 }

 def percentSize(stretch: Boolean, size: Int): F[LayoutContext, Integer] = new F[LayoutContext, Integer] {
  def f(context: LayoutContext) = if (stretch) context.getParentSize * size / 100 else
   Math.min(context.getParentSize * size / 100, context.getPreferredSize)
 }
}
