package anylayout;

import fj.F;

trait Constraint {
 def getLeft: F[LayoutContext,Integer]
 def getTop: F[LayoutContext, Integer]
 def getWidth: F[LayoutContext, Integer]
 def getHeight: F[LayoutContext, Integer]
}
