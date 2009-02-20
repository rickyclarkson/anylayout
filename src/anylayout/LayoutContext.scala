package anylayout

import java.awt.Component

trait LayoutContext {
 def parentSize: Int
 def preferredSize: Int
 def layoutInfoFor(component: Component): LayoutInfo
}
