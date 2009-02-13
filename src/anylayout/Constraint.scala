package anylayout;

case class Constraint(left: LayoutContext => Int, top: LayoutContext => Int,
                      width: LayoutContext => Int, height: LayoutContext => Int)

