package anylayout.extras;

import anylayout.LayoutContext;
import fj.F;

public interface ConstraintBuilderStage1
{
	ConstraintBuilderStage2 setLeft(F<LayoutContext,Integer> left);
}