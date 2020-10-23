import java.util.Random; 

public class Board {
	
	int N;
	int S;
	int W;
	Tile[] tiles;
	Supply[] supplies;
	
	Board(){
		//void constructor
		tiles = new Tile[this.N*this.N];
		supplies = new Supply[S];
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
				
		for(int j=0;j<N;j++)
		{
			for(int k=0;k<N;k++) {
				
				tiles[k+N*j].down=false;	//initializing boolean variables as false
				tiles[k+N*j].up=false;
				tiles[k+N*j].left=false;
				tiles[k+N*j].right=false;
				tiles[k+N*j].supply = false;
			}
		}
		
		for(int j=0;j<N;j++)
		{
			for(int k=0;k<N;k++) {
				
				tiles[k+N*j].x=j;
				tiles[k+N*j].y=k;
				tiles[k+N*j].tileId = k+N*j;
				
				if(tiles[k+N*j].x==0) {tiles[k+N*j].down=true;}		//checking if the tile is located on the
				if(tiles[k+N*j].x==N-1) {tiles[k+N*j].up=true;}	//boundaries of the board. If yes, considering
				if(tiles[k+N*j].y==0) {tiles[k+N*j].left=true;}	//its position 1 or 2 booleans become true. 
				if(tiles[k+N*j].y==N-1) {tiles[k+N*j].right=true;} //The rest remain false.
				
				
			}
		}
				
		tiles[0].down=false;  //?
		int walls=W-(4*N-1);  //the number of walls left to be used after setting the perimetric walls
		
			do{
				int xi = rand.nextInt(N); //initialize a random x
				int yi = rand.nextInt(N); //initialize a random y
				int n = rand.nextInt(4);  //a random number between 0 and 3
				int side=2*n+1; 	      //side is a variable in the set {1,3,5,7} 
				
					if(tiles[yi+N*xi].countTileWalls()<2) {  //checks if the given tile has more than 2 walls
						
						switch(side) //1->up, 5->down, 7->left, 3->right
						{
							case 1: 
								if(yi+N*xi+N<N*N) {	//checks if the upper neighboring tile of the given one exists
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
		int i=0;
		int supp=S;
		int[] arr=new int[S];
		int c=0;
		
		supplies[i].supplyId = rand.nextInt(S)+1; //supply Id is between 1-S, randomly choose one number of the set
		arr[i]=supplies[i].supplyId; //store the previous result in a assisting array
		supp--; //reverse supply counter
		i++; //supply array index
		int check;
		
		while(supp>0) {
			
				check = rand.nextInt(S)+1; //find a random number and store it in the check variable
				for(int j=0;j<i;j++)
				{
					if(check==arr[j])
					{
						c++;				//check if this random number which is the supply id already exists in the assisting array
					}
				}
			
				
						
				if(c==0){		// if it doesn't exist
					supplies[i].supplyId = check; //the number in check variable is the next random id
					arr[i]=supplies[i].supplyId;  //add it in the assisting array
					 supp--;				
					 i++;					 
					}					
			c=0;
		} 
		
			//same logic as the above
		int k=0;
		supplies[k].x=rand.nextInt(N*N);
		supplies[k].y=rand.nextInt(N*N);
		supplies[k].supplyTileId=supplies[k].y+N*supplies[k].x;
		tiles[supplies[k].getSupplyTileId()].setSupply(true);
		arr[k]=supplies[k].supplyTileId;
		k++;
		int checkx, checky, check3, c1=0;
		
		while(k!=S)
		{
			checkx = rand.nextInt(N*N); //x
			checky = rand.nextInt(N*N); //y
			check3 = checky+N*checkx; //Id
			for(int j=0;j<k;j++)
			{
				if(check3==arr[j])
				{
					c1++;
				}
			}
			 if(c1==0) {
				supplies[k].x=checkx;
				supplies[k].y=checky;
				supplies[k].supplyTileId=check3;
				tiles[supplies[k].getSupplyTileId()].setSupply(true);
				 k++;
			 }
			 
			c1=0;
		}
			
	}
}    