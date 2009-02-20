package anylayout;

case class Constraint(left: LayoutContext => Int, top: LayoutContext => Int,
                      width: LayoutContext => Int, height: LayoutContext => Int) {
                       if (left == null) throw null
                       if (top == null) throw null
                       if (width == null) throw null
                       if (height == null) throw null
                      }

