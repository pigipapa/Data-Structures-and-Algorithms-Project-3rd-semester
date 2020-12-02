/**
 * Class that contains the main and implements the game. 
 */
public class Game {
	private int round;	// Game's number of total rounds.
	
	public Game(){ this.round = 0; }
	
	/**
     * Returns game's current round.
     *
     * @return game's current round.
     */	
	public int getRound() { return round; }
	
	/**
     * Sets game's current round.
     *
     * @param round, game's current round.
     */
	public void setRound(int round) { this.round = round; }
	
	static public void printBoard(String[][] res, int Dimensions) {
	
		for (int i = 2*Dimensions; i >= 0; i--) {
			
			for (int j = 0; j < Dimensions; j++) {
	
				System.out.print(res[i][j]);
			}
			
			System.out.println();
		}
	}
	
	static public boolean checkWin(HeuristicPlayer Minotaur, HeuristicPlayer Theseus, int Supplies) {
		if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())	// Theseus went in the tile where Minotaur was.
		{
			System.out.println("==========================================");
			System.out.println("\nMinotaur got Theseus. Minotaur is the winner.");	// There is a possibility because the moves are random, Theseus walks onto Minotaur,
																					// so we check it here.
			System.out.println("\n------------------------------------------");
			System.out.println("\nEach round's statistics for Theseus:");
			Theseus.statistics();
			System.out.println("------------------------------------------");
			System.out.println("\nEach round's statistics for Minotaur:");
			Minotaur.statistics();
			return true;
		}		
		
		if(Theseus.getScore() == Supplies) 											// Theseus got all supplies.
		{
			System.out.println("==========================================");
			System.out.println("\nTheseus gathered all supplies. Theseus is the winner.");
			System.out.println("\n------------------------------------------");
			System.out.println("\nEach round's statistics for Theseus:");
			Theseus.statistics();
			System.out.println("------------------------------------------");
			System.out.println("\nEach round's statistics for Minotaur:");
			Minotaur.statistics();
			return true;
		}
		
		return false;
	}
	
	static Board getPlayerBoard(Player player, Board board)
	{
		int Dimensions = board.getN();
		int Supplies = board.getS();

		Board playerBoard = new Board(Dimensions, Supplies, 0);

		playerBoard.createTile();
		playerBoard.setTile(player.getCurrentTile(), board.getTile(player.getCurrentTile()));

		if(board.getTile(player.getCurrentTile()).getSupply())
			playerBoard.setSupply((board.TileIdToSupplyId(player.getCurrentTile())-1), board.getSupply(board.TileIdToSupplyId(player.getCurrentTile())-1));
		
		for(int i=1; i<4; i++) { // Set up player board
			if(player.getCurrentTile() + i*Dimensions < Dimensions * Dimensions) {
				playerBoard.setTile(player.getCurrentTile() + i*Dimensions, board.getTile(player.getCurrentTile() + i*Dimensions));
				if(playerBoard.getTile(player.getCurrentTile() + i*Dimensions).getSupply())
					playerBoard.setSupply(board.TileIdToSupplyId(player.getCurrentTile() + i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(player.getCurrentTile() + i*Dimensions)-1));
			}
			if(player.getCurrentTile() - i*Dimensions > 0) {
				playerBoard.setTile(player.getCurrentTile() - i*Dimensions, board.getTile(player.getCurrentTile() - i*Dimensions));
				if(playerBoard.getTile(player.getCurrentTile() - i*Dimensions).getSupply())
					playerBoard.setSupply(board.TileIdToSupplyId(player.getCurrentTile() - i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(player.getCurrentTile() - i*Dimensions)-1));
			}
			if(player.getCurrentTile() + i <= (player.getX()+1)*Dimensions - 1) {
				playerBoard.setTile(player.getCurrentTile() + i, board.getTile(player.getCurrentTile() + i));
				if(playerBoard.getTile(player.getCurrentTile() + i).getSupply())
					playerBoard.setSupply(board.TileIdToSupplyId(player.getCurrentTile() + i)-1, board.getSupply(board.TileIdToSupplyId(player.getCurrentTile() + i)-1));
			}
			if(player.getCurrentTile() - i >= player.getX()*Dimensions) {
				playerBoard.setTile(player.getCurrentTile() - i, board.getTile(player.getCurrentTile() - i));
				if(playerBoard.getTile(player.getCurrentTile() - i).getSupply())
					playerBoard.setSupply(board.TileIdToSupplyId(player.getCurrentTile() - i)-1, board.getSupply(board.TileIdToSupplyId(player.getCurrentTile() - i)-1));
			}
		}

		return playerBoard;
	}
	
	public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 7;  
		int Supplies = 5;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		int maxRounds = 10;

		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		board.createBoard();
		HeuristicPlayer Minotaur = new HeuristicPlayer(1, "Minotaur", new Board(Dimensions, Supplies, 0), 0, (Dimensions-1)/2, (Dimensions-1)/2, -1); 
		HeuristicPlayer Theseus = new HeuristicPlayer(2, "Theseus", new Board(Dimensions, Supplies, 0), 0, 0, 0, -1); 
		
		int times;
		for(times = 0; times < maxRounds; times++)
		{	
			System.out.println("==========================================");
			
			game.setRound(game.getRound()+1);
			System.out.println("Current round: " + game.getRound());
			
			// Prints the board before players take their turn to play.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
						
			// Time for Theseus to move
			Theseus.setBoard(getPlayerBoard(Theseus, board));
			Theseus.move(Theseus.getNextMove());

		    // Prints the board after Theseus moves.
			System.out.println("------------------------------------------");
			System.out.println("Theseus' turn to move\n");
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
			
			if(checkWin(Minotaur, Theseus, Supplies)) break;
						
			// Time for Minotaur to move
			Minotaur.setBoard(getPlayerBoard(Minotaur, board));
			Minotaur.move(Minotaur.getNextMove());

			// Prints the board after Theseus moves.
			System.out.println("------------------------------------------");
			System.out.println("Minotaur's turn to move\n"); 	
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();

			// Check if game should be finished.
			if(checkWin(Minotaur, Theseus, Supplies)) break;
		
		}
		
		if(times == maxRounds) 
		{
			System.out.println("==========================================");
			System.out.println("\nTie...");	// Nobody won...
			System.out.println("\n------------------------------------------");
			System.out.println("\nEach round's statistics for Theseus:");
			Theseus.statistics();
			System.out.println("------------------------------------------");
			System.out.println("\nEach round's statistics for Minotaur:");
			Minotaur.statistics();
		}
	}
}