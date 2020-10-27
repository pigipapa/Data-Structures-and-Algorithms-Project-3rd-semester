public class Supply {

    int supplyId; //id of the supply
    int x; //x coordinate of the board tile on which the supply is located
    int y; //y coordinate of the board tile on which the supply is located
    int supplyTileId; //id of the board tile on which the supply is located

    Supply(){
    	x = -1;
    	y = -1;
    	supplyId = -1;
    	supplyTileId = -1;
    }
    
	Supply(int supplyId, int x, int y, int supplyTileId){
		this.supplyId = supplyId;
		this.x = x;
		this.y = y;
		this.supplyTileId = supplyTileId;
	}

    Supply(Supply ob){
        supplyId=ob.getSupplyId();
        x=ob.getX();
        y=ob.getY();
        supplyTileId=ob.getSupplyTileId();
    }

    void setSupplyId(int supplyId) { this.supplyId = supplyId; }

    int getSupplyId() { return supplyId; }

    void setX(int x) { this.x = x; }

    int getX() { return x; }

    void setY(int y) { this.y = y; }

    int getY() { return y; }

    void setSupplyTileId(int supplyTileId) { this.supplyTileId = supplyTileId; }

    int getSupplyTileId() { return supplyTileId; }


}