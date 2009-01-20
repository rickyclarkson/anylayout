package anylayout;

/**
	Information about the layout of a component.
	
	The reason both this and Constraint exist is that they are for different purposes.
	Constraint is called by AnyLayout only in normal use, and receives information from AnyLayout,
	about the component and container being laid out (LayoutContext), because that's very convenient in writing
	constraints.  However, from a SizeCalculator or from a Constraint, you might want to know the layout
	information about other components, so you can ask for this.
	
	From a Constraint, you can ask the context for this: context.getLayoutInfo(someComponent).
	From a SizeConstraint, you can ask AnyLayout for it directly.
	
	Instances of LayoutInfo are not intended to be cached, because they are not guaranteed to be updated.
*/
public interface LayoutInfo
{
	/**
		Gets the number of pixels between this component and the origin of its container, on the current axis.
	*/
	Integer getOffset();

	/**
		Gets the number of pixels between this component's far (bottom or right) border and the origin of its container, on the current axis.
	*/
	Integer getFarOffset();
}