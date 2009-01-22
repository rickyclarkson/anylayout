package anylayout

import java.awt.Component

trait LayoutContext {
 def getParentSize: Int
 def getPreferredSize: Int
 def getLayoutInfo(component: Component): LayoutInfo
}
