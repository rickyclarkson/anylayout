package anylayout

class Size(val width: () => Int, val height: () => Int)
object Size { def apply(width: => Int, height: => Int) = new Size(() => width, () => height) }
