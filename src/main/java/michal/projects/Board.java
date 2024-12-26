package michal.projects;

import java.util.logging.Level;
import javafx.scene.layout.GridPane;

public class Board extends GridPane 
{
    private Tile tiles[][];

    private final int numOfColumns;
    private final int numOfRows;

    private final double probability;
    public final double getProbability(){return probability;}

    private final int speed;
    public final int getSpeed(){return speed;}

    private final Object paintLock = new Object();
    public final Object getLock(){return paintLock;}

    public Board(int numOfColumns, int numOfRows, double propability, int speed)
    {
        super();
        this.probability = propability;
        this.speed = speed;
        this.numOfColumns = numOfColumns;
        this.numOfRows = numOfRows;
        tiles = new Tile[numOfColumns][numOfRows];
    }

    /**
     * @brief Gets the tile at specified coordinates.
     * @param x X-coordinate of the tile.
     * @param y Y-coordinate of the tile.
     * @return The Tile object at the specified coordinates.
     */
    public Tile getTile(int x, int y)
    {
        if(x == -1)  
            x = numOfColumns - 1;
        if(x == numOfColumns)
            x = 0;
        if(y == -1)
            y = numOfRows - 1;
        if(y == numOfRows)
            y = 0;

        return tiles[x][y];
    }

     /**
     * @brief Sets a tile at specified coordinates.
     * @param x X-coordinate of the tile.
     * @param y Y-coordinate of the tile.
     * @param tile The Tile object to set at the specified coordinates.
     */
    public void setTile(int x, int y, Tile tile)
    {
        tiles[x][y] = tile;
    }

     /**
     * @brief Stops all tile threads in the board.
     */
    public void stopAllTiles()
    {
        for(int i = 0; i<numOfColumns; i++)
            for(int j = 0; j<numOfRows; j++)
                tiles[i][j].stopThread();
        MyLogger.logger.log(Level.INFO, "all threads stopped");
    }
}
