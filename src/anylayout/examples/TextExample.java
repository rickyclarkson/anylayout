package anylayout.examples;

import static anylayout.AnyLayout.useAnyLayout;
import anylayout.Constraint;
import anylayout.LayoutContext;
import anylayout.SizeCalculator;
import static anylayout.extras.ConstraintBuilder.buildWithSameLeftAsTop;
import anylayout.extras.ConstraintUtility;
import static anylayout.extras.ConstraintUtility.bottomLeft;
import static anylayout.extras.ConstraintUtility.topLeft;
import static anylayout.extras.ConstraintUtility.topRight;
import static anylayout.extras.RelativeConstraints.above;
import static anylayout.extras.RelativeConstraints.below;
import static anylayout.extras.SizeCalculatorUtility.absoluteSize;
import fj.F;

import static fj.Function.constant;
import static anylayout.FunctionUtility.minus;

import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.awt.Container;
import static java.lang.Math.max;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Copying the interface shown here: http://i4.tinypic.com/2464cxl.png
 */
final class TextExample
{
	public static void f(final Runnable exceptionHandler)
	{
		setSystemLookAndFeel(exceptionHandler);

		final JFrame frame=new JFrame();
		frame.setLocationByPlatform(true);

		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		final Container contentPane=frame.getContentPane();

		useAnyLayout(contentPane, 0.5f, 0.5f, absoluteSize(300, 350), ConstraintUtility.typicalDefaultConstraint(exceptionHandler));

		final JLabel formulaSyntax=new JLabel("<html><h1>Formula syntax</h1><p>Molle supports <i>parenthesised</i> formula expressions with propositional<br> and modal operators.<br>A formula is a composition of subformulas with propositional or modal operators.<br>Such operators are recognized as follows:<br><br></html>");
		formulaSyntax.setVerticalAlignment(SwingConstants.TOP);

		final Map<String, String> map=new LinkedHashMap<String, String>();
		map.put("~", "NOT");
		map.put("&", "AND");
		map.put("|", "OR");
		final JPanel propPanel=TextExample.createTitledPanel("Propositional syntax", map);

		map.clear();
		map.put("[]   ", "NECESSARY <> POSSIBLY");

		final JPanel modalPanel=TextExample.createTitledPanel("Modal syntax", map);

		final F<LayoutContext, Integer> get3=constant(3);

		final F<LayoutContext, Integer> parentSize=new F<LayoutContext,Integer>()
		{
			public Integer f(final LayoutContext layoutContext)
			{
				return layoutContext.getParentSize();
			}
		};

		final F<LayoutContext,Integer> parentMinus3=minus(parentSize,get3);

		frame.add(formulaSyntax, buildWithSameLeftAsTop(get3).setWidth(parentMinus3).setHeight(new F<LayoutContext, Integer>()
		{
			public Integer f(final LayoutContext context)
			{
				return context.getLayoutInfo(propPanel).getOffset()-10;
			}
		}));

                F<LayoutContext, Integer> three = constant(3);
		frame.add(modalPanel, bottomLeft(three, three));

		frame.add(propPanel, above(modalPanel, 10));

		frame.pack();
		frame.setVisible(true);
	}

	private static void setSystemLookAndFeel(final Runnable exceptionHandler)
	{
		final String lookAndFeelName=getSystemLookAndFeelClassName();

		if (!lookAndFeelName.toLowerCase().contains("gtk"))
			try
			{
				UIManager.setLookAndFeel(lookAndFeelName);
			}
			catch (ClassNotFoundException e)
			{
				exceptionHandler.run();
			}
			catch (InstantiationException e)
			{
				exceptionHandler.run();
			}
			catch (IllegalAccessException e)
			{
				exceptionHandler.run();
			}
			catch (UnsupportedLookAndFeelException e)
			{
				exceptionHandler.run();
			}
	}

	private static JPanel createTitledPanel(final String title, final Map<String, String> contents)
	{
		final JPanel panel=new JPanel();
		final TitledBorder titledBorder=createTitledBorder(title);

		panel.setBorder(titledBorder);

		final Map<JLabel, JLabel> map=new LinkedHashMap<JLabel, JLabel>();

		final int padding=5;

		useAnyLayout(panel, 0.5f, 0.5f, new SizeCalculator()
		{
			public int getWidth()
			{
				int max=titledBorder.getMinimumSize(panel).width;

				for (final Map.Entry<JLabel, JLabel> entry : map.entrySet())
					max=max(max, entry.getKey().getPreferredSize().width+padding*3+entry.getValue().getPreferredSize().width);

				return max;
			}

			public int getHeight()
			{
				return panel.getInsets().top+panel.getInsets().bottom+(padding+map.entrySet().iterator().next().getKey().getPreferredSize().height)*map.size()+padding;
			}
		}, new F<Component, Constraint>()
		{
			public Constraint f(final Component component)
			{
				throw new UnsupportedOperationException();
			}
		});

		JLabel aboveLeft=null;
		JLabel aboveRight=null;

		for (final Map.Entry<String, String> entry : contents.entrySet())
		{
			final JLabel left=new JLabel(entry.getKey());
			final JLabel right=new JLabel(entry.getValue());

			final int top=padding+panel.getInsets().top;

			if (aboveLeft==null)
			{
                            F<LayoutContext, Integer> ctop = constant(top);
                            F<LayoutContext, Integer> cpadding = constant(padding);

				panel.add(left, topLeft(ctop, cpadding));
				panel.add(right, topRight(ctop, cpadding));
			}
			else
			{
				panel.add(left, below(aboveRight, padding));
				panel.add(right, below(aboveLeft, padding));
			}

			aboveLeft=left;
			aboveRight=right;

			map.put(left, right);
		}

		return panel;
	}
}