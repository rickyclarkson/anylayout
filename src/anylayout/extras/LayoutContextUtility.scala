package anylayout.extras

import anylayout.LayoutContext

import java.awt.Component

object LayoutContextUtility {
 val getParentSize: LayoutContext => Int = _.getParentSize
 val getPreferredSize: LayoutContext => Int = _.getPreferredSize
 def getFarOffset(component: Component): LayoutContext => Int = _.getLayoutInfo(component).getFarOffset
}
