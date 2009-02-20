package anylayout.extras

import anylayout.LayoutContext

import java.awt.Component

object LayoutContextUtility {
 val getParentSize: LayoutContext => Int = _.parentSize
 val getPreferredSize: LayoutContext => Int = _.preferredSize
 def getFarOffset(component: Component): LayoutContext => Int = _.layoutInfoFor(component).getFarOffset
}
