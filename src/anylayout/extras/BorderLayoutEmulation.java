package anylayout.extras;

import java.awt.Component;
import fj.data.Option;

public interface BorderLayoutEmulation
{
	BorderLayoutEmulation setNorth(Option<Component> north);

	BorderLayoutEmulation setSouth(Option<Component> south);

	BorderLayoutEmulation setEast(Option<Component> east);

	BorderLayoutEmulation setWest(Option<Component> west);

	BorderLayoutEmulation setCentre(Option<Component> centre);
}