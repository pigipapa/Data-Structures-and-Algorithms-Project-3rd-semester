/**
 * Class that implements board's supplies.
 */
public class Supply {
	
	int supplyId; //id of the supply
	int x; //x coordinate of the board tile on which the supply is located
	int y; //y coordinate of the board tile on which the supply is located
	int supplyTileId; //id of the board tile on which the supply is located
	
	Supply(){
		supplyId = -1;
		x = -1;
		y = -1;
		supplyTileId = -1;
	}
	
	/**
     * Initializes Supply with the given values.
     */
	Supply(int supplyId, int x, int y, int supplyTileId){
		this.supplyId=supplyId;
		this.x=x;
		this.y=y;
		this.supplyTileId=supplyTileId;
	}
	
	/**
     * Initializes Supply with the values of another board.
     */
	Supply(Supply ob){
		supplyId=ob.supplyId;
		x=ob.x;
		y=ob.y;
		supplyTileId=ob.supplyTileId;		
	}
	
	void setSupplyId(int supId) { supplyId = supId; }
	
	int getSupplyId() { return supplyId; }
	
	void setX(int x) { this.x = x; }
	
	int getX() { return x; }
	
	void setY(int y) { this.y = y; }
	
	int getY() { return y; }
	
	void setSupplyTileId(int supTileId) { supplyTileId = supTileId; }
	
	int getSupplyTileId() { return supplyTileId; }

}


