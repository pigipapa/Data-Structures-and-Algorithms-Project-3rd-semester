/**
 * Class that implements game's players.
 */
public class Player {

	int playerId;		// 1 for Minotaur, 2 for Theseus
	String name;		// Player's name
	Board board;		// The board on which the game is played
	int score;			// Player's score, depending on the number of supplies that the player has got. Always equal to 0 for Minotaur.
	int x;				// Player's x coordinate on the board
	int y;				// Player's y coordinate on the board
	int currentTile;	// Id of the tile, player is located on. 
	
	/**
	 * Initializes the objects and variables of the player as zero (0), minus 1 (-1) and null.
	 */
	public Player()
	{
		this.playerId = 0;
		this.score = 0;
		this.x = -1;
		this.y = -1;
		this.currentTile = -1;
		this.name = ""; 
		Board board = new Board();
	}
	
	/**
     * Initializes Player with the given values.
     */
	public Player(int playerId, String name, Board board, int score, int x, int y)
	{
		this.playerId = playerId; 
		this.name = name;
		this.board = new Board(board); 
		this.score = score;
		this.x = x;
		this.y = y;
		this.currentTile = y + x * board.getN();
	}
	
	/**
     * Initializes Player with the values of another board.
     */
	public Player(Player player)
	{
		playerId = player.getPlayerId(); 
		name = player.getName() ;
		board = new Board(player.board); 
		score = player.getScore();
		x = player.getX();
		y = player.getY();
		currentTile = player.getY() + player.getX() * player.getBoard().getN();
	}	
	
	/**
     * Returns player's Id.
     *
     * @return player's Id.
     */
	public int getPlayerId() { return playerId; }
	
	/**
     * Sets player's Id.
     *
     * @param playerId, player's Id.
     */
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	
	/**
     * Returns player's name.
     *
     * @return player's name.
     */	
	public String getName() { return name; }
	
	/**
     * Sets player's name.
     *
     * @param name, player's name.
     */
	public void setName(String name) {this.name = name;}
	
	/**
     * Returns player's score.
     *
     * @return player's score.
     */	
	public int getScore() { return score; }
	
	/**
     * Sets player's score, which for Minotaur is equal to 0 and for Theseus is equal to the number of supplies he has got.
     *
     * @param name, player's name.
     */
	public void setScore(int score) { this.score  = score; }
	
	/**
     * Returns player's x coordinate on the board.
     *
     * @return player's x coordinate on the board.
     */	
	public int getX() { return x; }
	
	/**
     * Sets player's x coordinate on the board.
     *
     * @param x, player's x coordinate on the board.
     */
	public void setX(int x) { this.x = x; }
	
	/**
     * Returns player's y coordinate on the board.
     *
     * @return player's y coordinate on the board.
     */	
	public int getY() { return y; }
	
	/**
     * Sets player's y coordinate on the board.
     *
     * @param y, player's y coordinate on the board.
     */
	public void setY(int y) { this.y = y;}
	
	/**
     * Returns Board object.
     *
     * @return Board object.
     */	
	public Board getBoard() { return board; }
	
	/**
     * Sets Board's variables, given an another Board object.
     *
     * @param board, board on which the player is.
     */
	public void setBoard(Board board) {
		this.board.setN(board.getN());
		this.board.setS(board.getS());
		this.board.setW(board.getW());
		this.board.tiles = board.tiles.clone();
		this.board.supplies = board.supplies.clone();
	}
	
	/**
     * Returns player's current tile.
     *
     * @return player's current tile.
     */	
	public int getCurrentTile() { return currentTile; }
	
	/**
     * Sets player's current tile.
     *
     * @param currentTile, player's current tile.
     */
	public void setCurrentTile(int currentTile) { this.currentTile = currentTile; }
	
	/**
     * Moves player towards the chosen direction. If player is Theseus, checks whether there is any supply in the tile he moved to. If there is, he gets it.
     *
     * @param direction, the direction (1 -> Up, 5 -> Down, 7 -> Left, 3 -> Right) chosen for the player to be moved to.
     */
	public int[] move(int direction)
	{				
		int supplyId = -1;			// When no supply is got, supplyId's value is -1.
		
		int[] array = {currentTile, x, y, supplyId};
		
		String printplayer = null;
		
		if(getPlayerId() == 1)			printplayer = "Minotaur";
		else if (getPlayerId() == 2)	printplayer = "Theseus";
		
		switch(direction) 
		{
			case 1:	//up
				
				if(board.tiles[currentTile].getUp() == true)
				{
					System.out.println(printplayer + " didn't move. Wall ahead!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved up.");
					this.x = this.x + 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 3:	//right
				
				if(board.tiles[currentTile].getRight() == true)
				{
					System.out.println(printplayer + " didn't move. Wall at the right side!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved right.");
					this.y = this.y + 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 5:	//down
				
				if(board.tiles[currentTile].getDown() == true)
				{
					System.out.println(printplayer + " didn't move. Wall down!");
					break;					
				}
				else
				{ 
					System.out.println(printplayer + " moved down.");
					this.x = this.x - 1; 
					this.currentTile = this.y + this.x * board.getN();
					break;
				}
				
			case 7:	//left
				
				if(board.tiles[currentTile].getLeft() == true)
				{
					System.out.println(printplayer + " didn't move. Wall at the left side!");
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
				
		if(playerId == 2)										// Checks if Theseus is the player.
		{
			if(board.getTile(currentTile).getSupply() == true)	// Checks if there is a supply in the current tile.
			{   
				supplyId = board.TileIdToSupplyId(currentTile);
				
				array[3] = supplyId;
				
				// Theseus got the supply, so it should be deleted. Deletion is made by setting supply's
				// coordinates equal to -1 (outside board), and its id equal to -1 as well. 
				// Also, tile's variable supply is set false.
				
				System.out.println("Theseus just got supply S" + supplyId + ".");
				
				board.getSupply(supplyId-1).setX(-1);
				board.getSupply(supplyId-1).setY(-1);
				board.getSupply(supplyId-1).setSupplyId(-1);
				board.getSupply(supplyId-1).setSupplyTileId(-1);
				board.getTile(currentTile).setSupply(false);
				
				score++; 
				
			}
		} 
		
		array[0] = this.currentTile;
		array[1] = this.x;
		array[2] = this.y;
		
		return array;
	}
}