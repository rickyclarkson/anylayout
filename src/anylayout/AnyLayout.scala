package anylayout

import anylayout.util.MapUtility
import fj.F

import java.awt.{Component, Container, Dimension, LayoutManager2}
import java.util.{HashMap, Map}

object AnyLayout {
 def useAnyLayout(container: Container, alignmentX: Float, alignmentY: Float, calculator: SizeCalculator, ifMissingConstraint: F[Component, Constraint]) {
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
    val getConstraint = new F[Component, Constraint] {
     def f(component1: Component) = MapUtility.get(constraints, component1, ifMissingConstraint)
    }

    new LayoutInfo { def getOffset = { val constraint = getConstraint.f(component)
                                       if (isX) constraint.getLeft.f(context) else constraint.getTop.f(context) }
                     def getFarOffset = {
                      val constraint = getConstraint.f(component)
                      if (isX) { val left = constraint.getLeft
                                 val width = constraint.getWidth
                                 left.f(context).intValue + width.f(context).intValue }
                      else constraint.getTop.f(context).intValue + constraint.getHeight.f(context).intValue
		     }
                   }
   }
  }

  container setLayout layout
 }
}
