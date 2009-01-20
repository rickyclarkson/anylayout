package anylayout;

import java.awt.Component;

/**
	Describes the current component that we are laying out, in terms of its preferred size, and describes the parent
	container, in terms of its width and height.  This is done on a per-axis basis, so when calculating the width of a component,
 	the LayoutContext will supply the width of the parent and the preferred width of the component.
	
	It is possible to find out about the layout of other components on the same container via this interface.
	
	This is only intended as a convenience, constraints are free to use whatever information they like, but
	AnyLayout will supply at least the information given here.
	
	In normal use, LayoutContext implementations are supplied by AnyLayout; you do not need to write your
	own.  However, for testing purposes, you might choose to.
	
	Instances of this interface are not meant to be cached by users, and, more importantly, the return values are
	not meant to be cached.
*/
public interface LayoutContext
{
	/**
		@return the size of the parent container on the current axis.
	*/
	Integer getParentSize();

	/**
		@return the preferred size of the component to be laid out, on the current axis.
	*/
	Integer getPreferredSize();
	
	/**
		Note that this method may currently cause StackOverflowErrors, if two constraints request
		information about each others' layouts.  This may be resolved in a future release.
		
		@return a LayoutInfo describing the layout of the requested component.
	*/
	LayoutInfo getLayoutInfo(Component component);
}