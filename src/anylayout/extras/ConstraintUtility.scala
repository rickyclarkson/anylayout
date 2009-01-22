package anylayout.extras

import FunctionUtility.minus
import LayoutContextUtility.getParentSize
import ConstraintBuilder.preferredSize
import anylayout.Constraint
import anylayout.LayoutContext
import ConstraintBuilder.buildConstraint
import fj.F
import fj.Function.constant

import java.awt.Component

import fj.Function
import fj.F

import java.lang.Integer

/**
 * Some implementations of {@link Constraint} for common cases.
 */
object ConstraintUtility
{
  private def parentSizeMinus(function: F[LayoutContext, Integer]) = new F[LayoutContext, Integer] {
   def f(context: LayoutContext) = context.getParentSize.intValue-function.f(context).intValue
  }

 private val intConstant = new F[Integer, F[LayoutContext, Integer]] { def f(integer: Integer) = Function.constant(integer) }

 def topLeft(top: F[LayoutContext, Integer], left: F[LayoutContext, Integer]) =
  buildConstraint setLeft left setTop top setWidth preferredSize setHeight preferredSize

 def bottomLeft(bottom: F[LayoutContext, Integer], left: F[LayoutContext, Integer]) =
  topLeft(parentSizeMinus(preferredSizePlus(bottom)), left)

 private def preferredSizePlus(function: F[LayoutContext, Integer]) =
  new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getPreferredSize + function.f(context).intValue }

 def bottomCentre(bottom: F[LayoutContext, Integer]) =
  buildPartOfCentreConstraint setTop bottomOf(bottom) setWidth preferredSize setHeight preferredSize

 private val buildPartOfCentreConstraint = buildConstraint setLeft getLeftCoordinateAsIfCentre

 private val getLeftCoordinateAsIfCentre =
  new F[LayoutContext, Integer] { def f(context: LayoutContext) = (context.getParentSize - context.getPreferredSize) / 2 }

 def topCentre(top: Int) = buildPartOfCentreConstraint setTop intConstant.f(top) setWidth preferredSize setHeight preferredSize

 def bottomRight(bottom: F[LayoutContext, Integer], right: F[LayoutContext, Integer]) = 
  buildConstraint setLeft farSide(right) setTop farSide(bottom) setWidth preferredSize setHeight preferredSize

 def bottomOf(bottom: F[LayoutContext, Integer]) = farSide(bottom)

 def topRight(top: F[LayoutContext, Integer], right: F[LayoutContext, Integer]) =
  buildConstraint setLeft new F[LayoutContext, Integer] {
   def f(context: LayoutContext) = context.getParentSize - context.getPreferredSize - right.f(context).intValue
  } setTop top setWidth preferredSize setHeight preferredSize

 def farSide(padding: F[LayoutContext, Integer]) = minus(minus(getParentSize, padding), LayoutContextUtility.getPreferredSize)
 def typicalDefaultConstraint(exceptionHandler: Runnable) = new F[Component, Constraint] { def f(component: Component) = {
  exceptionHandler.run
  val zero: F[LayoutContext, Integer] = constant(0)
  buildConstraint setLeft zero setTop zero setWidth preferredSize setHeight preferredSize
 }
                                                                                         }
}
