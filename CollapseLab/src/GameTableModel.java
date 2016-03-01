import javax.swing.table.*;

public class GameTableModel extends AbstractTableModel
{

    private String[] columnNames = {"","","","","","","","","",""};   
    private CollapsePiece[][] gameBoard;

    public GameTableModel(CollapseGame game)
    {
        gameBoard = game.getTileBoard();
    }

    public int getColumnCount()
    {
        //return columnNames.length;
    	return gameBoard[0].length;
    }

    public int getRowCount()
    {
        return gameBoard.length;
    }

    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        return gameBoard[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * * * editor for each cell.  If we didn't implement this method,
     * * * * then the last column would contain text ("true"/"false"),
     * * * * * rather than a check box.
     * * * * * */
     public Class getColumnClass(int c)
     {
         return getValueAt(0, c).getClass();
     }
}