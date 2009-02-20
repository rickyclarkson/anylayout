package anylayout.extras

import anylayout.Size

import java.awt.Component

trait PercentConstraints {
 def add(component: Component, left: Int, top: Int, width: Int, height: Int, stretchWidth: Boolean, stretchHeight: Boolean): Unit
 def size: Size
}
