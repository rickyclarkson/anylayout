package anylayout.examples;

import static anylayout.extras.BorderLayoutEmulationUtility.useBorderLayoutEmulation;
import static fj.data.Option.some;
import static javax.swing.BorderFactory.createLoweredBevelBorder;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;

import anylayout.extras.BorderLayoutEmulation;
import fj.data.Option;

final class BorderLayoutExample
{
	public static void run(final WindowListener windowListener)
	{
		Toolkit.getDefaultToolkit().setDynamicLayout(true);

		final JFrame frame=new JFrame();
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.addWindowListener(windowListener);
		final Container container=frame.getContentPane();

		final JLabel southComponent=new JLabel("A bit low");
		southComponent.setOpaque(true);
		southComponent.setBackground(Color.pink);

		final JList eastComponent=new JList(new String[]{"A Little Haiku","","I like lists","But they have Vector in their API","which makes me cry"});
		eastComponent.setBorder(createLoweredBevelBorder());

		final Option<Component> none=Option.none();

		BorderLayoutEmulation emulation=useBorderLayoutEmulation(container).setNorth(some((Component)new JButton("Rather high")));
		emulation=emulation.setSouth(some((Component)southComponent)).setEast(some((Component)eastComponent));
		emulation=emulation.setWest(some((Component)new JTree())).setCentre(some((Component)new JButton("Centre")));
		emulation.setSouth(some((Component)new JComboBox())).setCentre(none);

		frame.pack();
		frame.setVisible(true);
	}
}