package anylayout.extras

import anylayout._
import ConstraintBuilder.buildConstraint

import java.awt.Component

object RelativeConstraints {
 def above(below: Component, padding: Int) = buildConstraintSameLeftAs(below) setTop (context => context.getLayoutInfo(below).getOffset.intValue-padding-context.getPreferredSize) setWidth ConstraintBuilder.preferredSize setHeight ConstraintBuilder.preferredSize

 def buildConstraintSameLeftAs(component: Component) = buildConstraint setLeft equalTo(component)
 def below(above: Component, padding: Int) = buildConstraintSameLeftAs(above) setTop after(above, padding) setWidth ConstraintBuilder.preferredSize setHeight ConstraintBuilder.preferredSize
 def after(above: Component, padding: Int): LayoutContext => Int = _.getLayoutInfo(above).getFarOffset.intValue+padding
 def rightOf(left: Component, padding: Int) = buildConstraint setLeft after(left, padding) setTop equalTo(left) setWidth ConstraintBuilder.preferredSize setHeight ConstraintBuilder.preferredSize
 def halfwayBetween(one: Component, two: Component) = buildConstraint setLeft (c => {
  val oneInfo = c.getLayoutInfo(one)
  val twoInfo = c.getLayoutInfo(two)
  val tmp = (oneInfo.getFarOffset.intValue + twoInfo.getOffset.intValue - c.getPreferredSize) / 2
  Math.max(0, tmp / 2) }
                                                                              ) setTop (context => (after(one, context.getLayoutInfo(two).getOffset.intValue)(context).intValue-context.getPreferredSize.intValue)/2) setWidth ConstraintBuilder.preferredSize setHeight ConstraintBuilder.preferredSize

 def levelWith(horizontal: Component, vertical: Component) = buildConstraintSameLeftAs(vertical).setTop(equalTo(horizontal)).setWidth(ConstraintBuilder.preferredSize).setHeight(ConstraintBuilder.preferredSize)
 private def equalTo(horizontal: Component): LayoutContext => Int = _.getLayoutInfo(horizontal).getOffset
}
