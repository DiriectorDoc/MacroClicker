import java.awt.Dimension;

import javax.swing.JFrame;

class AltWindow extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int dimX,
				dimY;

	public AltWindow(String name, int x, int y)
	{
		dimX = x;
		dimY = y;
		setTitle(name);
	}

	public AltWindow(String name)
	{
		setTitle(name);
	}

	public void build()
	{
		if(dimX > 0 && dimY > 0)
			setPreferredSize(new Dimension(dimX, dimY));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
	}

	public void show()
	{
		setVisible(true);
	}
}