package anylayout.util

import fj.F
import java.awt.event._

object WindowListenerBuilder {
 def closed(callback: F[WindowEvent, Void]) = new WindowAdapter { override def windowClosed(e: WindowEvent) = callback.f(e) }
}
