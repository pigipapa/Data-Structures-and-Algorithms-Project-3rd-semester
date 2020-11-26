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
		String newLine = System.getProperty("line.separator");
		for (int i = 2*Dimensions; i >= 0; i--) {
			for (int j = 0; j < Dimensions; j++) {
	
				System.out.print(res[i][j]);
			}
				System.out.print(newLine);
		}
	}
	
	
	public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 7;  
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		
		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		HeuristicPlayer Minotaur = new HeuristicPlayer(1, "Minotaur", board, 0, (Dimensions-1)/2, (Dimensions-1)/2, -1, -1, -1); 
		HeuristicPlayer Theseus = new HeuristicPlayer(2, "Theseus", board, 0, 0, 0, -1, -1, -1); 
		
		int times;
		for(times = 0; times < 200; times++)
		{	
			game.setRound(game.getRound()+1);
			
			System.out.println("Current round: " + game.getRound());
			
			String newLine = System.getProperty("line.separator");
			// Prints the board before players take their turn to play.
			
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			System.out.print(newLine);
			
			// Time for Theseus to move
			Theseus.move(Theseus.getNextMove());
			Minotaur.board.setTheseusTile(Theseus.getCurrentTile());
			Theseus.statistics("everyRound");

		    // Prints the board after Theseus moves.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			/*for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}*/
			
			System.out.print(newLine);
			
			if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())	// Theseus went in the tile where Minotaur was.
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");	// There is a possibility because the moves are random, Theseus walks onto Minotaur,
				break;																	// so we check it here.
			}		
			
			if(Theseus.getScore() == Supplies) 												// Theseus got all supplies.
			{
				System.out.println("Theseus gathered all supplies. Theseus is the winner.");
				Theseus.statistics("finalRound");
				Minotaur.statistics("finalRound");
				break;
			}
			
			// Time for Minotaur to move
			Minotaur.move(Minotaur.getNextMove());
			Theseus.board.setMinotaurTile(Minotaur.getCurrentTile());
			Minotaur.statistics("everyRound");

			// Prints the board after Theseus moves.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			System.out.print(newLine);
			
			System.out.println("Theseus' current score: " + Theseus.getScore());
			
			// Check if game should be finished.
			if(Theseus.getScore() == Supplies) 												// Theseus got all supplies.
			{
				System.out.println("Theseus gathered all supplies. Theseus is the winner.");
				Theseus.statistics("finalRound");
				Minotaur.statistics("finalRound");
				break;
			}
		
			if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())	// Theseus went in the tile where Minotaur was.
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");
				Theseus.statistics("finalRound");
				System.out.println();
				Minotaur.statistics("finalRound");
				break;
			}
			
			if(times<199)
			System.out.println("==========================================");
		
		}
		
		if(times == 200) 
		{
			System.out.println("Tie...");	// Nobody won...
			Theseus.statistics("finalRound");
			System.out.println();
			Minotaur.statistics("finalRound");
		}
	}
}