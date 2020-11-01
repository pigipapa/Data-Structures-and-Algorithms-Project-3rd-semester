
public class Player {

	int playerId; // 1 for Minotaur, 2 for Theseus
	String name;
	Board board;
	int score;
	int x;
	int y;
	int currentTile;
	
	Player()
	{
		this.playerId = 0;
		this.score = 0;
		this.x = 0;
		this.y = 0;
		this.currentTile = 0;
		this.name = ""; 
		Board board = new Board();
	}
	
	Player(int playerId, String name, Board board, int score, int x, int y)
	{
		this.playerId = playerId; 
		this.name = name;
		this.board = new Board(board); 
		this.score = score;
		this.x = x;
		this.y = y;
		this.currentTile = y + x * board.getN();
	}
	
	Player(Player player)
	{
		playerId = player.getPlayerId(); 
		name = player.getName() ;
		board = new Board(player.board); 
		score = player.getScore();
		x = player.getX();
		y = player.getY();
		currentTile = player.getY() + player.getX() * player.getBoard().getN();
	}	
	
	int getPlayerId() { return playerId; }
	
	void setPlayerId(int playerId) { this.playerId = playerId; }
	
	String getName() { return name; }
	
	void setName(String name) {this.name = name;}
	
	int getScore() { return score; }
	
	void setScore(int score) { this.score  = score; }
	
	int getX() { return x; }
	
	void setX(int x) { this.x = x; }

	int getY() { return y; }
	
	void setY(int y) { this.y = y;}
	
	Board getBoard() { return board; }
	
	void setBoard(Board board) {
		this.board.setN(board.getN());
		this.board.setS(board.getS());
		this.board.setW(board.getW());
		this.board.tiles = board.tiles.clone();
		this.board.supplies = board.supplies.clone();
	}
	
	public int getCurrentTile() { return currentTile; }
	
	public void setCurrentTile(int currentTile) { this.currentTile = currentTile; }
	
	int[] move(int direction)
	{				
		int supplyId = -1; //when no supply is got, supplyId's value is -1
		
		int[] array = {currentTile, x, y, supplyId};
		
		String printplayer = null;
		
		if(getPlayerId() == 1)			printplayer = "Minotaur";
		else if (getPlayerId() == 2)	printplayer = "Theseus";
		
		switch(direction)
		{
			case 1:
				
				if(board.tiles[currentTile].getUp() == true)
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved up.");
					this.x = this.x + 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 3:
				
				if(board.tiles[currentTile].getRight() == true)
				{
					System.out.println("Player didn't move. Wall at the right side!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved right.");
					this.y = this.y + 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 5:
				
				if(board.tiles[currentTile].getDown() == true)
				{
					System.out.println("Player didn't move. Wall down!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved down.");
					this.x = this.x - 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 7:
				
				if(board.tiles[currentTile].getLeft() == true)
				{
					System.out.println("Player didn't move. Wall at the left side!");
					break;					
				}
				else 
				{ 
					System.out.println(printplayer + " moved left.");
					this.y = this.y - 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
		}
				
		if(playerId == 2) //Checks if Theseus is the player
		{
			if(board.getTile(currentTile).getSupply() == true) //Checks if there is a supply in the current tile
			{   
				supplyId = board.TileIdToSupplyId(currentTile);
				if(supplyId > 0) {	
					array[3] = supplyId;
					System.out.println("Theseus just got supply S" + supplyId + ".");
					board.getSupply(supplyId-1).setX(-1);
					board.getSupply(supplyId-1).setY(-1);
					board.getSupply(supplyId-1).setSupplyId(-1);
					board.getSupply(supplyId-1).setSupplyTileId(-1);
					board.getTile(supplyId-1).setSupply(false);
					score++; 
				}
			}
		} 
		
		array[0] = this.currentTile;
		array[1] = this.x;
		array[2] = this.y;
		
		return array;
	}
}