package anylayout

import anylayout.util.MapUtility

import java.awt.{Component, Container, Dimension, LayoutManager2}
import java.util.{HashMap, Map}

object AnyLayout {
 def useAnyLayout(container: Container, alignmentX: Float, alignmentY: Float, calculator: SizeCalculator, ifMissingConstraint: Component => Constraint) {
  val constraints = new HashMap[Component, Constraint]
  val layout = new LayoutManager2 {
   def addLayoutComponent(comp: Component, constraint: Object) = constraints.put(comp, constraint.asInstanceOf[Constraint])
   def maximumLayoutSize(target: Container) = preferredLayoutSize(target)
   def getLayoutAlignmentX(target: Container) = alignmentX
   def getLayoutAlignmentY(target: Container) = alignmentY
   def invalidateLayout(target: Container) { }
   def addLayoutComponent(name: String, comp: Component) = throw new UnsupportedOperationException
   def removeLayoutComponent(comp: Component) = constraints remove comp
   def preferredLayoutSize(parent: Container) = new Dimension(calculator.getWidth,calculator.getHeight)
   def minimumLayoutSize(parent: Container) = preferredLayoutSize(parent)
   def layoutContainer(parent: Container) =
    for (comp <- parent.getComponents) {
     val xInfo = getInfo(comp, true)
     val yInfo = getInfo(comp, false)
     comp.setBounds(xInfo.getOffset.intValue, yInfo.getOffset.intValue, getSize(xInfo).intValue, getSize(yInfo).intValue)
    }
   def getSize(info: LayoutInfo) = info.getFarOffset.intValue - info.getOffset.intValue
   def getInfo(component: Component, isX: Boolean): LayoutInfo = {
    val context = new LayoutContext { def getParentSize = if (isX) container.getWidth else container.getHeight
                                      def getPreferredSize = { val preferredSize = component.getPreferredSize
                                                               if (isX) preferredSize.width else preferredSize.height }
                                      def getLayoutInfo(component1: Component): LayoutInfo = getInfo(component1, isX) }
    val getConstraint: Component => Constraint = component1 => MapUtility.get(constraints, component1, ifMissingConstraint)

    new LayoutInfo { def getOffset = { val constraint = getConstraint(component)
                                       if (isX) constraint.left(context) else constraint.top(context) }
                     def getFarOffset = {
                      val constraint = getConstraint(component)
                      if (isX) { val left = constraint.left
                                 val width = constraint.width
                                 left(context).intValue + width(context).intValue }
                      else constraint.top(context).intValue + constraint.height(context).intValue
		     }
                   }
   }
  }

  container setLayout layout
 }
}
