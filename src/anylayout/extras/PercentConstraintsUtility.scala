package anylayout.extras

import anylayout._
import anylayout.util.Collections.max
import anylayout.util.DimensionUtility

import java.awt._
import java.util._
import java.lang.Integer


object PercentConstraintsUtility {
 val emptyComponents=new Array[Component](0);

 def newInstance(container: Container) = {
  val getPreferredParentSize: ((Component, Dimension)) => Dimension = { case (component, dimension) => {
   val preferredSize = DimensionUtility.multiply(component.getPreferredSize, 100)
   DimensionUtility.divide(preferredSize, dimension)
  } }

  val constraints = new HashMap[Component, (Component, Dimension)]
  new PercentConstraints {
   def add(component: Component, left: Int, top: Int, width: Int, height: Int, stretchWidth: Boolean, stretchHeight: Boolean) = {
    constraints.put(component, component -> new Dimension(width, height))
    container.add(component, ConstraintBuilder.buildConstraint.setLeft(_.parentSize * left / 100).setTop(_.parentSize * top / 100).setWidth(percentSize(stretchWidth, width)).setHeight(percentSize(stretchHeight, height)))
   }
   def getASize(dimension: Dimension => Int) = max((c: Component) => dimension(getPreferredParentSize(constraints.get(c))))
   def size = Size(getASize(_.width), getASize(_.height))
  }
 }

 def percentSize(stretch: Boolean, size: Int): LayoutContext => Int = context => if (stretch) context.parentSize * size / 100 else math.min(context.parentSize * size / 100, context.preferredSize)
}
