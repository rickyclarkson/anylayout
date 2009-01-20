package anylayout;

/**
	Calculates the preferred size of the container.
	
	The constraints are not available to help here, as many constraints depend on the container size themselves.
	An API to help in this will be made available.
*/
public interface SizeCalculator
{
	/**
		Calculates the preferred width of the container.
	*/
	int getWidth();
	
	/**
		Calculates the preferred height of the container.
	*/
	int getHeight();
}