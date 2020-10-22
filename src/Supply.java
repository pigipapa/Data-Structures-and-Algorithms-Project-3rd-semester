public class Supply {

    int supplyId; //id of the supply
    int x; //x coordinate of the board tile on which the supply is located
    int y; //y coordinate of the board tile on which the supply is located
    int supplyTileId; //id of the board tile on which the supply is located

    Supply(){
        //void constructor
    }

    Supply(Supply ob){
        supplyId=ob.supplyId;
        x=ob.x;
        y=ob.y;
        supplyTileId=ob.supplyTileId;

    }

    void setSupplyId(int supId) { supplyId = supId; }

    int getSupplyId() { return supplyId; }

    void setx(int x) { this.x = x; }

    int getx() { return x; }

    void sety(int y) { this.y = y; }

    int gety() { return y; }

    void setsupplyTileId(int supTileId) { supplyTileId = supTileId; }

    int getsupplyTileId() { return supplyTileId; }

}