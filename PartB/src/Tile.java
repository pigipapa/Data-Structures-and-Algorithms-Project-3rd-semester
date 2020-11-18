
/**
 * Class that implements the tiles of the board. 
 */
public class Tile {
	private int tileId;		// Id number of the tile.
	private int x;			// x coordinate of the tile.
	private int y;			// y coordinate of the tile.	
	private boolean up;		// If true the tile has an upper wall.
	private boolean down;	// If true the tile has a bottom wall.
	private boolean left;	// If true the tile has a left wall.
	private boolean right;	// If true the tile has a right wall.	 
	private boolean supply;	// Id true the tile has a supply.

    /**
     * Initializes tile integer variables as zero (0) and boolean variables as false.
     */
    public Tile(){
    	tileId = -1;	
    	x = -1;
    	y = -1;
    	down = false;
    	up = false;
    	left = false;
    	right = false;
    	supply = false;
    }
    
    /**
     * Initializes tiles with the given values.
     * @param tile, a tile object.
     */
    public Tile(Tile tile)
    {
        tileId = tile.tileId;
        x = tile.x;
        y = tile.y;
        up = tile.up;
        down = tile.down;
        left = tile.left;
        right = tile.right;
        supply = tile.supply; 
        
    }

    /**
     * Initializes tiles with the values of another tile.
     * @param tileId
     * @param x
     * @param y
     * @param up
     * @param down
     * @param left
     * @param right
     * @param supply
     */
    public Tile(int tileId, int x, int y, boolean up, boolean down, boolean left, boolean right, boolean supply)
    {
        this.tileId = tileId;
        this.x = x;
        this.y = y;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.supply = supply;
    }

    /**
     * Returns Id of the tile.
     * @return tileId
     */
     public int getTileId() { return tileId; }

    /**
     * Sets the Id of the tile.
     * @param tileId
     */
    public void setTileId(int tileId){ this.tileId = tileId; }
    
    /**
     * Returns x coordinate of the tile.
     * @return x
     */
    public int getX() { return x; }
   
    /**
     * Sets x coordinate of the tile.
     * @param x
     */
    public void setX(int x) { this.x = x; }
    
    /**
     * Returns y coordinate of the tile.
     * @return y
     */
    public int getY() { return y; }
    
    /**
     * Sets y coordinate of the tile.
     * @param y
     */
    public void setY(int y) { this.y = y; }

    /**
     * Shows the absence or not of an upper wall.
     * @return up, true if it has, false if it doesn't.
     */
    public boolean getUp() { return up; }

    /**
     * Sets up variable true or false.
     * @param up
     */
    public void setUp(boolean up) { this.up = up; }

    /**
     * Shows the absence or not of a bottom wall.
     * @return down, true if it has, false if it doesn't.
     */
    public boolean getDown() { return down; }

    /**
     * Sets down variable true or false.
     * @param down
     */
    public void setDown(boolean down) { this.down = down; }

    /**
     * Shows the absence or not of a right wall.
     * @return right, true if it has, false if it doesn't.
     */
    public boolean getRight() { return right;    }

    /**
     * Sets right variable true or false.
     * @param right
     */
    public void setRight(boolean right) { this.right = right; }

    /**
     *  Shows the absence or not of a left wall.
     * @return left, true if it has, false if it doesn't.
     */
    public boolean getLeft() { return left; }

    /**
     * Sets left variable true or false.
     * @param left
     */
    public void setLeft(boolean left) { this.left = left; }
    
    /**
     * Shows the absence or not of a supply. 
     * @return supply, true if it has, false if it doesn't.
     */
    public boolean getSupply() { return supply; }
    /**
     * Sets supply variable true or false.
     * @param supply
     */
    public void setSupply(boolean supply) { this.supply = supply; }
    
    /**
     * It counts how many walls the tile has.
     * @return the number of the walls.
     */
    public int countTileWalls() {
    	int count=0;
    	if(down==true) {count++;}
    	if(up==true) {count++;}
    	if(left==true) {count++;}
    	if(right==true) {count++;}
    	
    	return count;
    }
}