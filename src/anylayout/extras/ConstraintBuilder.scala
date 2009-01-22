package anylayout.extras

import anylayout.{Constraint, LayoutContext}
import fj.F
import java.awt.Component

object ConstraintBuilder
{
 def buildWithSameLeftAsTop(leftAndTop: F[LayoutContext, Integer]): ConstraintBuilderStage3 = buildConstraint setLeft leftAndTop setTop leftAndTop
 def buildConstraint: ConstraintBuilderStage1 = new ConstraintBuilderStage1 { def setLeft(left: F[LayoutContext, Integer]): ConstraintBuilderStage2 =
  new ConstraintBuilderStage2 { def setTop(top: F[LayoutContext, Integer]): ConstraintBuilderStage3 =
   new ConstraintBuilderStage3 { def setWidth(width: F[LayoutContext, Integer]): ConstraintBuilderStage4 = 
    new ConstraintBuilderStage4 { def setHeight(height: F[LayoutContext, Integer]): Constraint = 
     new Constraint { def getLeft = left
                      def getWidth = width
                      def getHeight = height
                      def getTop = top }
                                }
                               }
                              }
                                                   }

 val preferredSize: F[LayoutContext, Integer] = new F[LayoutContext, Integer] { def f(layoutContext: LayoutContext) = layoutContext.getPreferredSize }
 val fill = new F[LayoutContext, Integer] { def f(key: LayoutContext) = key.getParentSize }
 def minimum[T](one: F[T, Integer], two: F[T, Integer]) = new F[T, Integer] {
  def f(input: T) = Math.min(one.f(input).intValue, two.f(input).intValue)
 }

 def after(component: Component) = new F[LayoutContext, Integer] {
  def f(layoutContext: LayoutContext) = layoutContext.getLayoutInfo(component).getFarOffset
 }
}

trait ConstraintBuilderStage1 { def setLeft(left: F[LayoutContext, Integer]): ConstraintBuilderStage2 }
trait ConstraintBuilderStage2 { def setTop(top: F[LayoutContext, Integer]): ConstraintBuilderStage3 }
trait ConstraintBuilderStage3 { def setWidth(width: F[LayoutContext, Integer]): ConstraintBuilderStage4 }
trait ConstraintBuilderStage4 { def setHeight(height: F[LayoutContext, Integer]): Constraint }
