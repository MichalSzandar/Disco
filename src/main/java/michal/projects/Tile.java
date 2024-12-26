package michal.projects;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile implements Runnable
{
    private final Board board;
    private final Random random;

    private final int x;
    private final int y;

    private final Rectangle rectangle;
    @SuppressWarnings("exports")
    public Color getColor(){return (Color)rectangle.getFill();}

    private int delay;
    private double probability;

    private boolean paused = false;
    public boolean isPaused(){return paused;}
    private final Object pauseLock = new Object();
    public final Object getPauseLock(){return pauseLock;}

    private final Object paintLock;

    private boolean running = true;
    public void stopThread(){running = false;}

     /**
     * @brief Constructs a Tile object with specified parameters.
     * @param x X-coordinate of the tile.
     * @param y Y-coordinate of the tile.
     * @param rectangle Rectangle representing the visual component of the tile.
     * @param random Random object used for generating probabilities and delays.
     */
    public Tile(int x, int y, Rectangle rectangle, Random random, Object paintLock)
    {
        if(!(rectangle.getParent() instanceof Board))
            throw new IllegalArgumentException("error");

        this.random = random;

        this.x = x;
        this.y = y;

        this.rectangle = rectangle;
        rectangle.setOnMouseClicked(event -> togglePause());

        this.paintLock = paintLock;
            
        board = (Board)rectangle.getParent();  
    }

    /**
     * @brief pauses and resumes tile's thread when user clicks on it. Synchronized to puaseLockObject
     */
    private void togglePause() {
        synchronized (pauseLock) {
            paused = !paused;
            if (!paused) {
                pauseLock.notify();
                MyLogger.logger.log(Level.INFO, "Tile x: "+ x + ", y: " + y + " is now resumed");
            }else
                MyLogger.logger.log(Level.INFO, "Tile x: "+ x + ", y: " + y + " is now paused");
        }
    }

    /**
     * @brief Gets colors of tile's neighbours which are not paused
     * @return ArrayList of colors of tile's neighbours
     */
    public ArrayList<Color> getNeighboursColors()
    {
        ArrayList<Color> neighbours  = new ArrayList<>();
        int itX[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int itY[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        synchronized(paintLock)
        {
            for(int i = 0; i<8; i++)
            {
                synchronized(board.getTile(x + itX[i], y + itY[i]).getPauseLock())
                {
                    if(!board.getTile(x + itX[i], y+ itY[i]).isPaused())
                        neighbours.add(board.getTile(x + itX[i], y + itY[i]).getColor());
                }   
            }  
        }

        return neighbours;
    }

     /**
     * @brief Calculates the average color of the neighboring tiles.
     * @return The average color of the neighboring tiles.
     */
    public Color getAverageNeighboursColor()
    {  
        synchronized(paintLock){
            return Utils.calculateAverageColor(getNeighboursColors());
        }
    }

    /**
     * @brief Repaints the tile with the specified color.
     * @param color The color to repaint the tile with.
     */
    @SuppressWarnings("exports")
    public void repaint(Color color)
    {
        synchronized(paintLock){
            System.out.println("thread " +x + " " + y + ": START");
            Platform.runLater(()->rectangle.setFill(color));
            System.out.println("thread " +x + " " + y + ": END");
        }
    }

    /**
     * @brief The run method to be executed in a separate thread, controlling the tile's behavior.
     */
    @Override
    public void run() {
        while (running) 
        {
            try
            {   
                synchronized (pauseLock) {
                    while (paused) {
                        pauseLock.wait();
                    }
                }
                probability = random.nextDouble(0, 1.0);

                delay = random.nextInt(board.getSpeed(), 2*board.getSpeed());     
                if(probability <= board.getProbability()){
                    repaint(Utils.generateRandomColor(random));
                }else{
                    repaint(getAverageNeighboursColor());
                }
                Thread.sleep(delay); 
                   
            }catch(InterruptedException e){
                e.printStackTrace();
                MyLogger.logger.log(Level.SEVERE, "thread interrupted");
            }

        }
        MyLogger.logger.log(Level.INFO, "thread " + x + " " + y + " stopped running");
    }
}
