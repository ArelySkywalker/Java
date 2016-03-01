import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Renderer class for a cell in the Collapse Game.
 * 
 * Arely Miramontes
 *
 */
public class GameCellRenderer extends DefaultTableCellRenderer
{
	private static final int kGreen = 0, kPurple = 1, kRed = 2;
	private ImageIcon [] images;
	
	public GameCellRenderer(ImageIcon [] images)
	{
		this.images = images;
	}
	
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void setValue(Object value)
    {
    	CollapsePiece piece = (CollapsePiece) value;
    	
        setIcon(null);
        setText(null);
        
    	if(piece == CollapsePiece.green)
    	{
    		setIcon(images[kGreen]);
    	}
    	else if(piece == CollapsePiece.purple)
    	{
    		setIcon(images[kPurple]);
    	}
    	else if(piece == CollapsePiece.red)
    	{
    		setIcon(images[kRed]);
    	}

    }
}