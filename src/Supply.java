/**
 *	Class that implements the supplies Theseus is seeking.
 */
public class Supply {
	
	int supplyId; 		// Id of the supply.
	int x; 				// x coordinate of the board tile on which the supply is located.
	int y; 				// y coordinate of the board tile on which the supply is located.
	int supplyTileId;	// Id of the board tile on which the supply is located.
	
	/**
	 * Initializes supplies with -1 values.
	 */
	public Supply(){
		supplyId = -1;
		x = -1;
		y = -1;
		supplyTileId = -1;
	}
	
	/**
	 * Initializes supplies with the given values.
	 * @param supplyId
	 * @param x
	 * @param y
	 * @param supplyTileId
	 */
	public Supply(int supplyId, int x, int y, int supplyTileId){
		this.supplyId=supplyId;
		this.x=x;
		this.y=y;
		this.supplyTileId=supplyTileId;
	}
	
	/**
	 * Initializes supplies with the values of another supply.
	 * @param a Supply object.
	 */
	public Supply(Supply ob){
		supplyId=ob.supplyId;
		x=ob.x;
		y=ob.y;
		supplyTileId=ob.supplyTileId;
		
	}
	
	/**
	 * Sets the Id of the supply.
	 * @param supId, Id of the supply.
	 */
	public void setSupplyId(int supId) {
		supplyId = supId;
	}
	
	/**
	 * Returns the Id of the supply.
	 * @return supplyId
	 */
	public int getSupplyId() {
		return supplyId;
	}
	
	/**
	 * Sets x coordinate of the supply.
	 * @param x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * 
	 * Returns the x coordinate of the supply.
	 * @return x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets y coordinate of the supply.
	 * @param y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns the y coordinate of the supply.
	 * @return y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the tile Id on which the supply is on.
	 * @param supTileId, the Id of the tile that will contain the supply.
	 */
	public void setSupplyTileId(int supTileId) {
		supplyTileId = supTileId;
	}
	
	/**
	 * Returns the tile Id on which the supply is located on.  
	 * @return supplyTileId, the Id of the tile that contains the supply.
	 */
	public int getSupplyTileId() {
		return supplyTileId;
	}

}