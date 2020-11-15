import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;

class AltWindow extends JFrame
{
	private static final long serialVersionUID = 3595832825444530846L;
	
	private int dimX,
				dimY;

	public AltWindow(String name, int x, int y)
	{
		dimX = x;
		dimY = y;
	}
	
	public void buildAndShow()
	{
		add(Box.createRigidArea(new Dimension(dimX, dimY)));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	    setVisible(true);
	}
}
