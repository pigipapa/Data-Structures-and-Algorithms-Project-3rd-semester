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
		this.board = new Board();
		this.score = 0;
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
	
	int[] move(int direction)
	{				
		int supply = -1; //when no supply is got, supplies value is -1
		
		int[] array = {currentTile, x, y, supply};
		
		switch(direction)
		{
			case 1:
				
				if(board.tiles[currentTile].getUp())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ 
					this.y = this.y + 1; 
					this.currentTile = this.y + this.x * board.getN();
				}
				
			case 3:
				
				if(board.tiles[currentTile].getRight())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ 
					this.x = this.x + 1; 
					this.currentTile = this.y + this.x * board.getN();
				}
				
			case 5:
				
				if(board.tiles[currentTile].getDown())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ 
					this.y = this.y - 1; 
					this.currentTile = this.y + this.x * board.getN();
				}
				
			case 7:
				
				if(board.tiles[currentTile].getLeft())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else 
				{ 
					this.x = this.x - 1; 
					this.currentTile = this.y + this.x * board.getN();
				}
		}
				
		if(playerId == 2) //Checks if Theseus is the player
		{
//			if(board.getTile(currentTile).getSupply()) //Checks if there is a supply in the current tile
//			{
//				array[3] = supplies[currentTile].getSupplyId();
//				supplies[currentTile].setX(-1);
//				supplies[currentTile].setY(-1)'
//				supplies[currentTile].setSupplyId(-1);
//				supplies[currentTile].setSupplyTileId(-1);
//				board.tiles[currentTile].setSupply(false);
//				setScore(getScore() + 1); 
//			}
		} 
		array[0]  = this.currentTile;
		array[1] = this.x;
		array[2] = this.y;
		
		return array;
	}
}
