/**
 * Class that implements board's tiles.
 */
public class Tile {
    int tileId; // Tile's id
    int x; // Tile's x coordinate
    int y; // Tile's y coordinate
    boolean up; // True if there is a wall northerly, false if not
    boolean down; // True if there is a wall southerly, false if not
    boolean left; // True if there is a wall westerly, false if not
    boolean right; // True if there is a wall easterly, false if not
    boolean supply; // True if there is a supply on the tile, false if not

    Tile(){
    	tileId = 0;
    	x = 0;
    	y = 0;
    	down = false;
    	up = false;
    	left = false;
    	right = false;
    	supply = false;
    }
    
	/**
     * Initializes Tile with the given values.
     */
    Tile(int tileId, int x, int y, boolean up, boolean down, boolean left, boolean right, boolean supply)
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
     * Initializes Tile with the values of another board.
     */    
    Tile(Tile tile)
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


    int getTileId() { return tileId; }

    void setTileId(int tileId){ this.tileId = tileId; }

    int getX() { return x; }

    void setX(int x) { this.x = x; }

    int getY() { return y; }

    void setY(int y) { this.y = y; }

    boolean getUp() { return up; }

    void setUp(boolean up) { this.up = up; }

    boolean getDown() { return down; }

    void setDown(boolean down) { this.down = down; }

    boolean getRight() { return right;    }

    void setRight(boolean right) { this.right = right; }

    boolean getLeft() { return left; }

    void setLeft(boolean left) { this.left = left; }
    
    boolean getSupply() { return supply; }
    
    void setSupply(boolean supply) { this.supply = supply; }
    
	/**
	 * Counts tile's total walls
	 */
    int countTileWalls() {
    	int count=0;
    	if(down==true) {count++;}
    	if(up==true) {count++;}
    	if(left==true) {count++;}
    	if(right==true) {count++;}
    	
    	return count;
    }
}


