package anylayout.examples

import anylayout._
import anylayout.extras._
import javax.swing._

object PercentLayoutExample { def main(args: Array[String]) {
 val frame = new JFrame
 AnyLayout.useAnyLayout(frame, 0.5f, 0.5f, SizeCalculatorUtility.absoluteSize(400, 400), component => throw null)
 val constraints = PercentConstraintsUtility.newInstance(frame)
 val dont = new JButton("Don't")
 constraints.add(new JButton("Click"), 5, 5, 20, 20, false, false)
 constraints.add(dont, 70, 70, 25, 28, true, true)
 frame.pack
 println(dont.getWidth + dont.getX)
 println(frame.getWidth)
 frame setVisible true
} }
