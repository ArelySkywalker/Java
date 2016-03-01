
/**
 * This class represents the coordinates on the board for a tile
 * 
 * @author Arely Miramontes
 */
public class TileCoordinates
{
    private int rowCoord;
    private int colCoord;

    /**
     * Constructor for objects of class TileCoordinates
     * 
     * @param row the row coordinate
     * @param col the col coordinate
     */
    public TileCoordinates(int row, int col)
    {
        this.rowCoord = row;
        this.colCoord = col;
    }

    /**
     * Accessor method for x-coordinate
     * 
     * @return the x-coordinate
     */
    public int getRowCoordinate()
    {
        return this.rowCoord;
    }
    
    /**
     * Accessor method for y-coordinate
     * 
     * @return the y-coordinate
     */
    public int getColumnCoordinate()
    {
        return this.colCoord;
    }
}