import java.util.Random; 

/**
 *	Class that implements the board of the game.
 */
public class Board {
	
	private int N;				// The dimensions of NxN board. N is odd.
	private int S;				// The number of the supplies on the board.
	private int W;				// The number of the walls that can be added in the maze. Walls are determined by N as (N*N*3+1)/2.
	private Tile[] tiles;		// An array with Tile objects.
	private Supply[] supplies;	// An array with Supply objects.
	
	/**
     * Initializes board with zero values.
     */
	public Board(){
		N=0;
		S=0;
		W=0;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
	}
	
	/**
	 * Initializes board with the given values.
	 * @param N
	 * @param S
	 * @param W
	 */
     
	public Board(int N, int S, int W){
		
		this.N = N;
		this.S = S;
		this.W = W;
		tiles = new Tile[this.N*this.N];
		
		for(int i=0; i<N*N; i++) {
        tiles[i] = new Tile();
		}
		
		supplies = new Supply[S];
		
		for(int i=0; i<S; i++) {
			supplies[i] = new Supply();
		}
		
		createBoard();
	}
	
	/**
	 * Initializes board with the values of another board.
	 * @param a Board object. 
	 */
	public Board(Board ob){
		N = ob.N;
		S = ob.S;
		W = ob.W;
		tiles = ob.tiles.clone();
		supplies = ob.supplies.clone();
		
	}

	/**
     * Sets the size of the board.
     *
     * @param N the size of the square matrix.
     */
	public void setN(int N) {
		this.N = N;
	}
	
	/**
     * Returns the size of the board.
     *
     * @return the size of the board.
     */
	public int getN() {
		return N;
	}
	
	/**
     * Sets the number of the supplies on the board.
     *
     * @param S the host of the supplies.
     */
	public void setS(int S) {
		this.S = S;
	}
	
	/**
     * Returns the number of the supplies.
     *
     * @return the number of the supplies.
     */
	public int getS() {
		return S;
	}
	
	/**
     * Sets the number of the walls of the maze.
     *
     * @param W the host of the supplies.
     */
	public void setW(int W) {
		this.W = W;
	}
	
	/**
     * Returns the number of the maze walls.
     *
     * @return the number of the walls.
     */
	public int getW() {
		return W;
	}
	
	/**
	 * Sets the values of a tile of given index equal to the values of another tile. 
	 * 
	 * @param index, the index of tile that is given to be set.
	 * @param tile, the tile that is going to be copied.
	 */
	public void setTile(int index, Tile tile) { tiles[index] = tile; }
	
	/**
     * Returns a tile of the board given its index.
     *
     * @param index, the index of the tile to be returned.
     * @return a tile of the board given its index.
     */
	public Tile getTile(int index) { return tiles[index]; }
	
	/**
	 * Sets the values of a supply of given index equal to the values of another supply.
	 * 
	 * @param index, the index of supply that is given to be set.
	 * @param supply, he supply that is going to be copied.
	 */
	public void setSupply(int index, Supply supply) { supplies[index] = supply; }
	
	/**
     * Returns a supply given its index.
     *
     * @param index, the index of the supply to be returned.
     * @return a supply given its index.
     */   
	public Supply getSupply(int index) { return supplies[index]; }
	
	/**
	 * Initializes the tiles in a random way.
	 * The x, y and tileId variables of the tile objects in the array tile[] are set
	 * and it is randomly decided if a wall will be built in any direction of the tile
	 * if it's possible.
	 */
	private void createTile() {
		
		Random rand = new Random(System.currentTimeMillis());
		
		
		for(int j=0;j<N;j++)
		{
			for(int k=0;k<N;k++) {
				
				tiles[k+N*j].setX(j);										// Initializing x, y and tileId.
				tiles[k+N*j].setY(k);
				tiles[k+N*j].setTileId(k+N*j);
				
				if(tiles[k+N*j].getX()==0) { tiles[k+N*j].setDown(true); }		// Checking if the tile is located on the
				if(tiles[k+N*j].getX()==N-1) { tiles[k+N*j].setUp(true); }		// boundaries of the board. If yes, considering
				if(tiles[k+N*j].getY()==0) { tiles[k+N*j].setLeft(true); }		// its position 1 or 2 booleans become true. 
				if(tiles[k+N*j].getY()==N-1) { tiles[k+N*j].setRight(true); }   // The rest remain false.
				
				
			}
		}
				
		tiles[0].setDown(false); 			// The tile with 0 is considered to be the entrance. No wall is added.
		int walls=getW()-(4*getN()-1); 		// The number of walls left to be used after setting the perimetric walls.
		
			do{
				int xi = rand.nextInt(N); 	// Initialize a random x.
				int yi = rand.nextInt(N); 	// Initialize a random y.
				int n = rand.nextInt(4);  	// A random number between 0 and 3.
				int side=2*n+1; 	      	// Side is a variable in the set {1,3,5,7}. 
				
					if(tiles[yi+N*xi].countTileWalls()<2) {  							// Checks if the given tile has more than 2 walls.
						
						switch(side) // 1->up, 5->down, 7->left, 3->right
						{
							case 1:	//up
								if(yi+N*xi+N<N*N) {										// Checks if the upper neighboring tile of the given one exists.
									if(tiles[yi+N*xi+N].countTileWalls()<2)				// Checks if the upper neighboring tile has more than 2 walls.
									{
										if(tiles[yi+N*xi].getUp()!=true)				// Checks if the given tile already has a wall upwards.
										{
											tiles[yi+N*xi].setUp(rand.nextBoolean());	// If not then randomly decide if placing one on the upper side.
											if(tiles[yi+N*xi].getUp()==true) { 			// If a wall is placed
												tiles[yi+N*xi+N].setDown(true);			// the upper neighboring tile acquires an southern wall. 
												walls=walls-2; 							// Thus subtracts the wall counter by 2.
											}
										}	
									}
							} break;
								
							// The rest cases follow the same logic.
							case 3:	//right
								if(yi<N-1) {
									if(tiles[yi+N*xi+1].countTileWalls()<2)
									{
										if(tiles[yi+N*xi].getRight()!=true)
										{
											tiles[yi+N*xi].setRight(rand.nextBoolean());
											if(tiles[yi+N*xi].getRight()==true) {
												tiles[yi+N*xi+1].setLeft(true);
												walls=walls-2;
											}
										}	
									}
							} break;
							
							case 7:	//left
								if(yi>0) {
									if(tiles[yi+N*xi-1].countTileWalls()<2)
									{
										if(tiles[yi+N*xi].getLeft()!=true)
										{
											tiles[yi+N*xi].setLeft(rand.nextBoolean());
											if(tiles[yi+N*xi].getLeft()==true) {
												tiles[yi+N*xi-1].setRight(true);
												walls=walls-2;
											}
										}	
									}
							} break;
							
							case 5:	//down
								if(yi+N*xi-N>0) {
									if(tiles[yi+N*xi-N].countTileWalls()<2)
									{
										if(tiles[yi+N*xi].getDown()!=true)
										{
											tiles[yi+N*xi].setDown(rand.nextBoolean());
											if(tiles[yi+N*xi].getDown()==true) {
												tiles[yi+N*xi-N].setUp(true);
												walls=walls-2;
											}
										}	
									}
							} break;
						
						}
						
						
					}		
					
					
					
			}while(walls>0);
				
	}
	
	/**
	 * Initializes the supplies in a random way.
	 * supplyId variable, of the supply objects in the array supplies[], is set
	 * and x, y and supplyTileId are randomly decided. 
	 */
	private void createSupply() {
		
		Random rand = new Random(System.currentTimeMillis());
		
		int k = 0;
		int checkx, checky, checkId;
		
		while(k != S)
		{
			checkx = rand.nextInt(N); // x
			checky = rand.nextInt(N); // y
			checkId = checky + N*checkx; // Id
			
			if((checkId != 0) && (checkId != (N*N-1)/2) ) { 				// Supply must not be placed on the middle or on the first tile.
									
				 if(!tiles[checkId].getSupply()) {							// If it doesn't exist:
					supplies[k].setX(checkx);								// the number in checkx variable is the next random x
					supplies[k].setY(checky);								// the number in checky variable is the next random y
					supplies[k].setSupplyTileId(checkId);					// the number in check3 variable is the next random tile id.
					supplies[k].setSupplyId(k+1); 
					tiles[supplies[k].getSupplyTileId()].setSupply(true);	// The tile with this specific Id now contains a supply.
					k++;
				 }

			}
		}
				
	}
	
	/**
	 * Creates the board using createTile() and createSupply() functions which are initializing the board in a random way. 
	 */
	private void createBoard() {
		
		createTile();
		createSupply();
				
	}
	
	/**
	 * This function creates and returns a (2*N + 1)xN string matrix.
	 * It prints the graphic representation of the board.
	 * 
	 *  @param minotaurTile the tileId on which the Minotaur is on.
	 *  @param theseusTile the tileId on which Theseus is on.
	 *  @return a string matrix with size (2*N + 1)xN.
	 */
	String[][] getStringRepresentation(int theseusTile, int minotaurTile){
		
		String[][] b = new String[2*N+1][N];
		
	
		for (int i = 2; i < 2 * N - 1; i=i+2) {
			for (int j = 0; j < N; j++) {

				if (j == N - 1)
					b[i][j] = "+   +";										// In this loop the "+" are placed at the limits of the tile.
				else 
					b[i][j] = "+   ";
			}		
		}
  

		for (int i = 1; i < 2 * N; i = i + 2) {
			for (int j = 1; j < N-1; j = j + 1) {
				b[i][j] = "    ";											// Spaces are added in between.
			}
		}

		
		for(int i=0; i<N*N; i++) {
			if(tiles[i].getUp()==true) {
				if(tiles[i].getY() == N-1)
					b[2*tiles[i].getX()+2][tiles[i].getY()]="+---+";
				else
					b[2*tiles[i].getX()+2][tiles[i].getY()]="+---";
			}
			if(tiles[i].getDown()==true) {
				if(tiles[i].getY() == N-1)
					b[2*tiles[i].getX()][tiles[i].getY()]="+---+";			// The walls are placed inside the maze.
				else
					b[2*tiles[i].getX()][tiles[i].getY()]="+---";
			}								
			if(tiles[i].getLeft()==true) {
				if(tiles[i].getY() == N-1)
					b[2*tiles[i].getX()+1][tiles[i].getY()]="|   |";
				else
					b[2*tiles[i].getX()+1][tiles[i].getY()]="|   ";
			}
			
		}
		
		for (int i = 1; i < 2 * N; i = i + 2) {
			
			if(tiles[(N-1)+N*(i/2)].getLeft() == false)						
			b[i] [N - 1] = "    |";
		}
		
		b[2*N][N-1] ="+---+";
		b[0][N-1] = "+---+";
		b[0][0] = "+   ";
		
		// Minotaur and Theseus are placed on the board.
		b[2*(minotaurTile / N)+1][minotaurTile % N] = b[2*(minotaurTile / N)+1][minotaurTile % N].replace("   "," M ");
		b[2*(theseusTile / N)+1][theseusTile % N] = b[2*(theseusTile / N)+1][theseusTile % N].replace("   "," T ");		
		
		// The supplies are placed.
		for(int i=0; i<S; i++) {											
			if(supplies[i].getSupplyId() > 0) {
				if(tiles[supplies[i].getSupplyTileId()].getLeft() == true)
					b[2*supplies[i].getX()+1][supplies[i].getY()] = b[2*supplies[i].getX()+1][supplies[i].getY()].replace("   ", "S"+ (i+1) + " ");
				else
					b[2*supplies[i].getX()+1][supplies[i].getY()] = b[2*supplies[i].getX()+1][supplies[i].getY()].replace("   ", " S"+ (i+1)); 
			}
		}
			
		return b;
		
	}
	
	/**
	 * If the tile which Id is given, contains a supply then the function returns the Id of this supply.
	 * Else if the tile which Id is given doesn't contain a supply, it returns -1.  
	 * @param a tile Id.
	 * @return supplyId of the supply on the tile given or -1 if it doesn't exist. 
	 */
	 int  TileIdToSupplyId(int tileId)
	{
	    for(int i = 0; i < S; i++)
	    	if(tileId == supplies[i].getSupplyTileId())
	    		return supplies[i].getSupplyId();
	    
	    return -1;
	    
	}

}
    
