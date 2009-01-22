package anylayout.extras

import anylayout.LayoutContext
import fj.F

import java.awt.Component

object LayoutContextUtility {
 val getParentSize = new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getParentSize }
 val getPreferredSize = new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getPreferredSize }
 def getFarOffset(component: Component) =
  new F[LayoutContext, Integer] { def f(context: LayoutContext) = context.getLayoutInfo(component).getFarOffset }
}
