package anylayout.util

import java.awt.event._

object WindowListenerBuilder {
 def closed(callback: WindowEvent => Void) = new WindowAdapter { override def windowClosed(e: WindowEvent) = callback(e) }
}
