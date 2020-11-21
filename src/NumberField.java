import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberField extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of <code>JTextField</code>.
     */
    public NumberField()
    {
        new JTextField(null, null, 0);
        addFilter();
        
    }

    /**
     * Creates a new instance of <code>JTextField</code>.
     *
     * @param text the initial text
     */
    public NumberField(String text)
    {
        new JTextField(null, text, 0);
        addFilter();
    }

    /**
     * Creates a new instance of <code>JTextField</code>.
     *
     * @param columns the number of columns
     *
     * @exception IllegalArgumentException if columns %lt; 0
     */
    public NumberField(int columns)
    {
        new JTextField(null, null, columns);
        addFilter();
    }

    /**
     * Creates a new instance of <code>JTextField</code>.
     *
     * @param text the initial text
     * @param columns the number of columns
     *
     * @exception IllegalArgumentException if columns %lt; 0
     */
    public NumberField(String text, int columns)
    {
        new JTextField(null, text, columns);
        addFilter();
    }
    
    public void setValue(Number num)
    {
    	super.setText(num+"");
    }
    
    public void clear()
    {
    	super.setText("");
    }
    
    public Number getValue()
    {
    	String num = getText();
    	try
    	{
        	return Long.parseLong(num);
    	}
    	catch(java.lang.NumberFormatException e)
    	{
    		return Double.parseDouble(num);
    	}
    }
    
    @Override
    public void setText(String arg0)
    {
    	return;
    }
    
    private void addFilter()
    {
    	((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter()
        {
        	@Override
        	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException
        	{
        		StringBuilder buffer = new StringBuilder(text.length());
        		for (int i = 0; i < text.length(); i++)
        		{
        			char keyCode = text.charAt(i);
            		if (keyCode >= 48 && keyCode <= 57)
        			{
        				buffer.append(keyCode);
        			}
        		}
        		super.insertString(fb, offset, buffer.toString(), attr);
        	}

        	@Override
        	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException
        	{
        		if(length > 0)
        		{
        			fb.remove(offset, length);
        		}
        		insertString(fb, offset, string, attr);
        	}
        });
    }
}