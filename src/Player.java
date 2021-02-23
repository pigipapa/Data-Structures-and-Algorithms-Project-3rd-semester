
/**
 * Class that implements game's players.
 */
public class Player {

	protected int playerId;		// 1 for Minotaur, 2 for Theseus
	protected String name;		// Player's name
	protected Board board;		// The board on which the game is played
	protected int score;			// Player's score, depending on the number of supplies that the player has got. Always equal to 0 for Minotaur.
	protected int x;				// Player's x coordinate on the board
	protected int y;				// Player's y coordinate on the board
	protected int currentTile;	// Id of the tile, player is located on. 
	
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
		
		if(playerId == 2)
			this.board.getTile(this.currentTile).setTheseus(true);
		else if(playerId == 1)
			this.board.getTile(this.currentTile).setMinotaur(true);
			
	}
	
	/**
     * Initializes Player with the values of another player.
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
		
		for(int i=0; i<board.getN()*board.getN(); i++) {
			this.board.setTile(i, board.getTile(i));
		}
		
		for(int i=0; i<board.getS(); i++) {
			this.board.setSupply(i, board.getSupply(i));
		}
		
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
		
		switch(direction) 
		{
			case 1:	//up
				
				if(board.getTile(currentTile).getUp() == true)
				{
					System.out.println(getName() + " didn't move. Wall ahead!" + "\n");
					break;					
				}
				else
				{ 
					System.out.println(getName() + " moved up.");
					
					// Player left from it's previous tile.
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(false); 
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(false);
						
					this.x = this.x + 1; 
					this.currentTile = this.y + this.x * board.getN();
					
					// Player set on it's new current tile.
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(true);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(true);
					
					break;
				}
				
			case 3:	//right
				
				if(board.getTile(currentTile).getRight() == true)
				{
					System.out.println(getName() + " didn't move. Wall at the right side!" + "\n");
					break;					
				}
				else
				{ 
					System.out.println(getName() + " moved right.");
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(false);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(false);
					
					this.y = this.y + 1; 
					this.currentTile = this.y + this.x * board.getN();
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(true);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(true);
					
					break;
				}
				
			case 5:	//down
				
				if(board.getTile(currentTile).getDown() == true && currentTile==0)
				{
					System.out.println(getName() + " didn't move. Wall down!" + "\n");
					break;					
				}
				else
				{ 
					System.out.println(getName() + " moved down.");
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(false);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(false);
					
					this.x = this.x - 1; 
					this.currentTile = this.y + this.x * board.getN();
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(true);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(true);
					
					break;
				}
				
			case 7:	//left
				
				if(board.getTile(currentTile).getLeft() == true)
				{
					System.out.println(getName() + " didn't move. Wall at the left side!" + "\n");
					break;					
				}
				else 
				{ 
					System.out.println(getName() + " moved left.");
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(false);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(false);
					
					this.y = this.y - 1; 
					this.currentTile = this.y + this.x * board.getN();
					
					if(playerId ==2)
						board.getTile(currentTile).setTheseus(true);
					else if(playerId == 1)
						board.getTile(currentTile).setMinotaur(true);
					
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
