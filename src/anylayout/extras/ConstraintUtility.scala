package anylayout.extras

import FunctionUtility.minus
import LayoutContextUtility.getParentSize
import ConstraintBuilder.preferredSize
import anylayout.Constraint
import anylayout.LayoutContext
import ConstraintBuilder.buildConstraint

import java.awt.Component

/**
 * Some implementations of {@link Constraint} for common cases.
 */
object ConstraintUtility
{
  private def parentSizeMinus(function: LayoutContext => Int): LayoutContext => Int =
   context => context.getParentSize.intValue-function(context).intValue

 private val intConstant: Int => (LayoutContext => Int) = x => (y => x)

 def topLeft(top: LayoutContext => Int, left: LayoutContext => Int) =
  buildConstraint setLeft left setTop top setWidth preferredSize setHeight preferredSize

 def bottomLeft(bottom: LayoutContext => Int, left: LayoutContext => Int) =
  topLeft(parentSizeMinus(preferredSizePlus(bottom)), left)

 private def preferredSizePlus(function: LayoutContext => Int): LayoutContext => Int =
  context => context.getPreferredSize + function(context).intValue

 def bottomCentre(bottom: LayoutContext => Int) =
  buildPartOfCentreConstraint setTop bottomOf(bottom) setWidth preferredSize setHeight preferredSize

 private lazy val buildPartOfCentreConstraint = buildConstraint setLeft getLeftCoordinateAsIfCentre

 private lazy val getLeftCoordinateAsIfCentre: LayoutContext => Int = context => (context.getParentSize - context.getPreferredSize) / 2

 def topCentre(top: Int) = buildPartOfCentreConstraint setTop intConstant(top) setWidth preferredSize setHeight preferredSize

 def bottomRight(bottom: LayoutContext => Int, right: LayoutContext => Int) = 
  buildConstraint setLeft farSide(right) setTop farSide(bottom) setWidth preferredSize setHeight preferredSize

 def bottomOf(bottom: LayoutContext => Int) = farSide(bottom)

 def topRight(top: LayoutContext => Int, right: LayoutContext => Int) =
  buildConstraint setLeft (context => context.getParentSize - context.getPreferredSize - right(context).intValue) setTop top setWidth preferredSize setHeight preferredSize

 def farSide(padding: LayoutContext => Int) = minus(minus(getParentSize, padding), LayoutContextUtility.getPreferredSize)
 def typicalDefaultConstraint(exceptionHandler: Runnable): Component => Constraint = component => {
  exceptionHandler.run
  val zero: LayoutContext => Int = x => 0
  buildConstraint setLeft zero setTop zero setWidth preferredSize setHeight preferredSize
 }
}
