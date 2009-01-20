package anylayout;

import fj.F;

/**
	Layout constraints for {@link AnyLayout}.
	
	{@link AnyLayout} users use or create implementations of this interface, which, for a given component and container, will report where it should be placed, and how wide
	and high it should be.
	
	Each function receives a {@link LayoutContext}, which reports the size of the container, the preferred
	size of the component being laid out, and allows a function to get at the {@link LayoutInfo} for other components.
*/
public interface Constraint
{
	/**
		Reports where the left border of the component should be, in pixels from the left border of the container. 
	*/
	F<LayoutContext,Integer> getLeft();
	
	/**
		Reports where the top border of the component should be, in pixels from the top border of the container.
	*/
	F<LayoutContext,Integer> getTop();
	
	/**
		Reports how wide the component should be, in pixels.
	*/
	F<LayoutContext,Integer> getWidth();
	
	/**
		Reports how tall the component should be, in pixels.
	*/
	F<LayoutContext,Integer> getHeight();
}