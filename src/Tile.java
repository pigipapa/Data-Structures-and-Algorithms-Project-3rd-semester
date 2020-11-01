public class Tile {
    int tileId;
    int x;
    int y;
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    boolean supply;

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
    
    
    int countTileWalls() {
    	int count=0;
    	if(down==true) {count++;}
    	if(up==true) {count++;}
    	if(left==true) {count++;}
    	if(right==true) {count++;}
    	
    	return count;
    }
}


