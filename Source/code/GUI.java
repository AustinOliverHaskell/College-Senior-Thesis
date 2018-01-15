package austin.structures;

import javax.swing.*;

public class GUI extends JPanel
{
	private int total;
	private int current = 0;
	private JProgressBar bar;

	public GUI(int total)
	{
		this.total = total;

		bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(this.total);

		this.setSize(500, 500);

		add(bar);
	}

	public void increment()
	{
		current++;
		bar.setValue(current);
	}

	public void showGUI()
	{
		JFrame frame = new JFrame("Progress Bar");
		frame.setSize(500, 500);
    	frame.setContentPane(this);
    	frame.pack();
    	frame.setVisible(true);
	}

	public boolean finished()
	{
		boolean retVal = false;
		
		if (current >= total)
		{
			retVal = true;
		}

		return retVal;
	}
}