

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
	
	/**
	 * Function that prints board.
	 * @param res, a 2-Dimensional board  
	 * @param Dimensions, dimensions of board
	 */
	static public void printBoard(String[][] res, int Dimensions) {
	
		for (int i = 2*Dimensions; i >= 0; i--) {
			
			for (int j = 0; j < Dimensions; j++) {
	
				System.out.print(res[i][j]);
			}
			
			System.out.println();
		}
	}
	
	/**
	 * This function checks if Theseus or Minotaur won.
	 * @param Minotaur
	 * @param Theseus
	 * @param Supplies, number of supplies 
	 * @return true if some player is the winner, otherwise it returns false 
	 */
	static public boolean checkWin(HeuristicPlayer Minotaur, HeuristicPlayer Theseus, int Supplies) {
		
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
		
		if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())					// Theseus went in the tile where Minotaur was.
		{
			System.out.println("==========================================");
			System.out.println("\nMinotaur got Theseus. Minotaur is the winner.");	
																				
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
	
	public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 15;  
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		int maxRounds = 100;	// If max dices to tie up the game are 200, max rounds are 100.
		
		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		board.createBoard();
		HeuristicPlayer Minotaur = new HeuristicPlayer(1, "Minotaur", new Board(Dimensions, Supplies, 0), 0, (Dimensions-1)/2, (Dimensions-1)/2, -1); 
		HeuristicPlayer Theseus = new HeuristicPlayer(2, "Theseus", new Board(Dimensions, Supplies, 0), 0, 0, 0, -1); 
		
		int times;
		for(times = 0; times < maxRounds; times++)
		{	
			game.setRound(game.getRound()+1);
			
			System.out.println("==========================================");
			System.out.println("Current round: " + game.getRound());
			
			// Prints board before players take their turn to play.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
						
			// Time for Theseus to move
			Theseus.setBoard(board.getPlayerBoard(Theseus));
			Theseus.move(Theseus.getNextMove());

		    // Prints the board after Theseus moves.
			System.out.println("------------------------------------------");
			System.out.println("Theseus' turn to move\n");
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
			
			if(checkWin(Minotaur, Theseus, Supplies)) break;
						
			// Time for Minotaur to move
			Minotaur.setBoard(board.getPlayerBoard(Minotaur));
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