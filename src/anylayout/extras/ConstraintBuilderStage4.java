package anylayout.extras;

import anylayout.Constraint;
import anylayout.LayoutContext;
import fj.F;

public interface ConstraintBuilderStage4
{
	Constraint setHeight(F<LayoutContext,Integer> height);
}
