package anylayout.extras

import anylayout.{Constraint, LayoutContext}
import java.awt.Component

object ConstraintBuilder
{
 def buildWithSameLeftAsTop(leftAndTop: LayoutContext => Int): ConstraintBuilderStage3 = buildConstraint setLeft leftAndTop setTop leftAndTop
 def buildConstraint: ConstraintBuilderStage1 = new ConstraintBuilderStage1 { def setLeft(left: LayoutContext => Int): ConstraintBuilderStage2 = {
  if (left == null) throw null
  new ConstraintBuilderStage2 { def setTop(top: LayoutContext => Int): ConstraintBuilderStage3 =
   new ConstraintBuilderStage3 { def setWidth(width: LayoutContext => Int): ConstraintBuilderStage4 = 
    new ConstraintBuilderStage4 { def setHeight(height: LayoutContext => Int): Constraint = 
     new Constraint(left, top, width, height)
                                }
                               }
                              }
 } }
                                                                            

 val preferredSize: LayoutContext => Int = _.getPreferredSize
 val fill: LayoutContext => Int = _.getParentSize
 def minimum[T](one: T => Int, two: T => Int): T => Int = input => Math.min(one(input).intValue, two(input).intValue)

 def after(component: Component): LayoutContext => Int = _.getLayoutInfo(component).getFarOffset
}

trait ConstraintBuilderStage1 { def setLeft(left: LayoutContext => Int): ConstraintBuilderStage2 }
trait ConstraintBuilderStage2 { def setTop(top: LayoutContext => Int): ConstraintBuilderStage3 }
trait ConstraintBuilderStage3 { def setWidth(width: LayoutContext => Int): ConstraintBuilderStage4 }
trait ConstraintBuilderStage4 { def setHeight(height: LayoutContext => Int): Constraint }
