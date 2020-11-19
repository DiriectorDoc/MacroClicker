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
	}

	public AltWindow(String name)
	{

	}

	public void build()
	{
		if(dimX > 0 && dimY > 0)
			setPreferredSize(new Dimension(dimX, dimY));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	    setVisible(true);
	}
}