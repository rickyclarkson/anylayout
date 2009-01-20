package anylayout.extras;

import anylayout.LayoutContext;
import fj.F;

public interface ConstraintBuilderStage2
{
	ConstraintBuilderStage3 setTop(F<LayoutContext,Integer> top);
}