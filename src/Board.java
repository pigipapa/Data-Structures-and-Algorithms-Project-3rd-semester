import java.util.Random; 

public class Board {

    int N;
    int S;
    int W;
    Tile[] tiles;
    Supply[] supplies;

    Board(){
        //void constructor
    }

    Board(int N, int S, int W){
        this.N = N;
        this.S = S;
        this.W = W;
    }

    Board(Board ob){
        N = ob.N;
        S = ob.S;
        W = ob.W;
        tiles = ob.tiles.clone();
        supplies = ob.supplies.clone();
    }

    void setN(int N) { this.N = N; }

    int getN() { return N; }

    void setS(int S) { this.S = S; }

    int getS() { return S; }

    void setW(int W) { this.W = W; }

    int getW() { return W; }

    void createTile() {

        Random rand = new Random();

        for(int i=0;i<tiles.length;i++)
        {
            tiles[i].x= rand.nextInt();
        }
    }

}