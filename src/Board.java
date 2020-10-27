import java.util.Random; 

public class Board {
	
	int N;
	int S;
	int W;
	Tile[] tiles;
	Supply[] supplies;
	
	Board(){
		N=0;
		S=0;
		W=0;
	}
	
	Board(int N, int S, int W){		
		this.N = N;
		this.S = S;
		this.W = W;
		tiles = new Tile[this.N*this.N];
		supplies = new Supply[S];
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
	
    Tile getTile(int index) { return tiles[index]; }
   
    Supply getSupply(int index) { return supplies[index]; }
    
	void createTile() {
		
		Random rand = new Random();
				
		for(int j=0;j<N;j++)
		{
			for(int k=0;k<N;k++) {
				
				tiles[k+N*j].setDown(false);	//initializing boolean variables as false
				tiles[k+N*j].setUp(false);
				tiles[k+N*j].setLeft(false);
				tiles[k+N*j].setRight(false);
				tiles[k+N*j].setSupply(false);
			}
		}
		
		for(int j=0;j<N;j++)
		{
			for(int k=0;k<N;k++) {
				
				tiles[k+N*j].setX(j);
				tiles[k+N*j].setY(k);
				tiles[k+N*j].setTileId(k+N*j);
				
				if(tiles[k+N*j].x==0) { tiles[k+N*j].setDown(true); }		//checking if the tile is located on the
				if(tiles[k+N*j].x==N-1) { tiles[k+N*j].setUp(true); }		//boundaries of the board. If yes, considering
				if(tiles[k+N*j].y==0) { tiles[k+N*j].setLeft(true); }		//its position 1 or 2 booleans become true. 
				if(tiles[k+N*j].y==N-1) { tiles[k+N*j].setRight(true); }    //The rest remain false.
								
			}
		}
				
		tiles[0].down=false;  // The tile with 0 is considered to be the entrance
		int walls=W-(4*N-1);  //the number of walls left to be used after setting the perimetric walls
		
		do{
			int xi = rand.nextInt(N); //initialize a random tile's x coordinate
			int yi = rand.nextInt(N); //initialize a random tile's y coordinate
			int n = rand.nextInt(4);  //a random number between 0 and 3
			int side = 2*n+1; 	      //side is a variable in the set {1,3,5,7} 
					
			if(tiles[yi+N*xi].countTileWalls()<2) {  //checks if the given tile has more than 2 walls
							
				switch(side) //1->up, 5->down, 7->left, 3->right
				{
					case 1: 
						if(yi+N*xi+N<N*N) //checks if the upper neighboring tile of the given one exists
						{	
							if(tiles[yi+N*xi+N].countTileWalls()<2)	//checks if the upper neighboring tile has more than 2 walls
							{
								if(tiles[yi+N*xi].up!=true)	//checks if the given tile already has a wall upwards
								{
									tiles[yi+N*xi].up=rand.nextBoolean(); //if not then randomly decide if placing one on the upper side
									if(tiles[yi+N*xi].up==true) { //if a wall is placed
										tiles[yi+N*xi+N].down=true;	//the upper neighboring tile acquires an southern wall 
										walls--; //thus subtracts the wall counter by 1
									}
								}	
							}
					} break;
									
					//other cases follow the same logic
					case 3:
						if(yi<N-1) {
							if(tiles[yi+N*xi+1].countTileWalls()<2)
							{
								if(tiles[yi+N*xi].right!=true)
								{
									tiles[yi+N*xi].right=rand.nextBoolean();
									if(tiles[yi+N*xi].right==true) {
										tiles[yi+N*xi+1].left=true;
										walls--;
									}
								}	
							}
						} break;
								
					case 7:
						if(yi>0) {
							if(tiles[yi+N*xi-1].countTileWalls()<2)
							{
								if(tiles[yi+N*xi].left!=true)
								{
									tiles[yi+N*xi].left=rand.nextBoolean();
									if(tiles[yi+N*xi].left==true) {
										tiles[yi+N*xi-1].right=true;
										walls--;
									}
								}	
							}
					} break;
								
					case 5:
						if(yi+N*xi-N>0) {
						if(tiles[yi+N*xi-N].countTileWalls()<2)
						{
							if(tiles[yi+N*xi].down!=true)
							{
								tiles[yi+N*xi].down=rand.nextBoolean();
								if(tiles[yi+N*xi].down==true) {
									tiles[yi+N*xi-N].up=true;
									walls--;
								}
							}	
						}
					} break;							
				}												
			}																		
	}while(walls>0);						
	}
	
	void createSupply() {
		
		Random rand = new Random();
		
		int k = 0;
		int checkx, checky, checkId;
		
		while(k != S)
		{
			checkx = rand.nextInt(N); //x
			checky = rand.nextInt(N); //y
			checkId = checky + N*checkx; //Id
			
			if((checkId != 0) && checkId != ((N*1)*(N-1))/2 ) { // Supply must not be placed on the middle or on the first tile
									
				 if(!tiles[checkId].getSupply()) {		// if it doesn't exist
					supplies[k].x = checkx;	//the number in checkx variable is the next random x
					supplies[k].y = checky;	//the number in checky variable is the next random y
					supplies[k].supplyTileId = checkId;	//the number in check3 variable is the next random tile id
					supplies[k].setSupplyId(k+1); 
					tiles[supplies[k].getSupplyTileId()].setSupply(true);	///the tile with this specific Id now contains a supply
					k++;
				 }

			}
		}
				
	}
	

	/* 
	* TileIdToSupplyId function returns the supply's Id given 
	* the Id of the tile it is located on. If it is not found 
	* return -1.
	*/
	    
	int  TileIdToSupplyId(int tileId)
	{
	    for(int i = 0; i < S; i++)
	    	if(tileId == supplies[i].getSupplyTileId())
	    		return supplies[i].getSupplyId();
	    
	    return -1;
	    
	}	
}