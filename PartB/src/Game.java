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
	
	
	public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 15;  
		int Supplies = 5;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		int maxRounds = 100;

		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		board.createBoard();
		HeuristicPlayer Minotaur = new HeuristicPlayer(1, "Minotaur", new Board(Dimensions, Supplies, 0), 0, (Dimensions-1)/2, (Dimensions-1)/2, -1); 
		HeuristicPlayer Theseus = new HeuristicPlayer(2, "Theseus", new Board(Dimensions, Supplies, 0), 0, 0, 0, -1); 
		
		int times;
		for(times = 0; times < maxRounds; times++)
		{	
			System.out.println("==========================================");
			Board TheseusBoard = new Board(Dimensions, Supplies, 0);
			TheseusBoard.createTile();
			Board MinotaurBoard = new Board(Dimensions, Supplies, 0);
			MinotaurBoard.createTile();
			
			game.setRound(game.getRound()+1);
			System.out.println("Current round: " + game.getRound());
			
			// Prints the board before players take their turn to play.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
			
			//printBoard(playerBoard.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			TheseusBoard.setTile(Theseus.getCurrentTile(), board.getTile(Theseus.getCurrentTile()));
			if(board.getTile(Theseus.getCurrentTile()).getSupply())
				TheseusBoard.setSupply((board.TileIdToSupplyId(Theseus.getCurrentTile())-1), board.getSupply(board.TileIdToSupplyId(Theseus.getCurrentTile())-1));
			
			for(int i=1; i<4; i++) { // Set up player board
				if(Theseus.getCurrentTile() + i*Dimensions < Dimensions * Dimensions) {
					TheseusBoard.setTile(Theseus.getCurrentTile() + i*Dimensions, board.getTile(Theseus.getCurrentTile() + i*Dimensions));
					if(TheseusBoard.getTile(Theseus.getCurrentTile() + i*Dimensions).getSupply())
						TheseusBoard.setSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() + i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() + i*Dimensions)-1));
				}
				if(Theseus.getCurrentTile() - i*Dimensions > 0) {
					TheseusBoard.setTile(Theseus.getCurrentTile() - i*Dimensions, board.getTile(Theseus.getCurrentTile() - i*Dimensions));
					if(TheseusBoard.getTile(Theseus.getCurrentTile() - i*Dimensions).getSupply())
						TheseusBoard.setSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() - i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() - i*Dimensions)-1));
				}
				if(Theseus.getCurrentTile() + i <= (Theseus.getX()+1)*Dimensions - 1) {
					TheseusBoard.setTile(Theseus.getCurrentTile() + i, board.getTile(Theseus.getCurrentTile() + i));
					if(TheseusBoard.getTile(Theseus.getCurrentTile() + i).getSupply())
						TheseusBoard.setSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() + i)-1, board.getSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() + i)-1));
				}
				if(Theseus.getCurrentTile() - i >= Theseus.getX()*Dimensions) {
					TheseusBoard.setTile(Theseus.getCurrentTile() - i, board.getTile(Theseus.getCurrentTile() - i));
					if(TheseusBoard.getTile(Theseus.getCurrentTile() - i).getSupply())
						TheseusBoard.setSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() - i)-1, board.getSupply(board.TileIdToSupplyId(Theseus.getCurrentTile() - i)-1));
				}
			}
			
			Theseus.setBoard(TheseusBoard);
			
			//printBoard(TheseusBoard.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			// Time for Theseus to move
			System.out.println("------------------------------------------");
			System.out.println("Theseus' turn to move\n");
			Theseus.move(Theseus.getNextMove());

		    // Prints the board after Theseus moves.
			printBoard(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			System.out.println();
			
			if(checkWin(Minotaur, Theseus, Supplies)) break;
			
			MinotaurBoard.setTile(Minotaur.getCurrentTile(), board.getTile(Minotaur.getCurrentTile()));
			if(board.getTile(Minotaur.getCurrentTile()).getSupply())
				MinotaurBoard.setSupply((board.TileIdToSupplyId(Minotaur.getCurrentTile())-1), board.getSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile())-1));
			
			for(int i=1; i<4; i++) { // Set up player board
				if(Minotaur.getCurrentTile() + i*Dimensions < Dimensions * Dimensions) {
					MinotaurBoard.setTile(Minotaur.getCurrentTile() + i*Dimensions, board.getTile(Minotaur.getCurrentTile() + i*Dimensions));
					if(MinotaurBoard.getTile(Minotaur.getCurrentTile() + i*Dimensions).getSupply())
					MinotaurBoard.setSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() + i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() + i*Dimensions)-1));
				}
				if(Minotaur.getCurrentTile() - i*Dimensions > 0) {
					MinotaurBoard.setTile(Minotaur.getCurrentTile() - i*Dimensions, board.getTile(Minotaur.getCurrentTile() - i*Dimensions));
					if(MinotaurBoard.getTile(Minotaur.getCurrentTile() - i*Dimensions).getSupply())
						MinotaurBoard.setSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() - i*Dimensions)-1, board.getSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() - i*Dimensions)-1));
				}
				if(Minotaur.getCurrentTile() + i <= (Minotaur.getX()+1)*Dimensions - 1) {
					MinotaurBoard.setTile(Minotaur.getCurrentTile() + i, board.getTile(Minotaur.getCurrentTile() + i));
					if(MinotaurBoard.getTile(Minotaur.getCurrentTile() + i).getSupply())
						MinotaurBoard.setSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() + i)-1, board.getSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() + i)-1));
				}
				if(Minotaur.getCurrentTile() - i >= Minotaur.getX()*Dimensions) {
					MinotaurBoard.setTile(Minotaur.getCurrentTile() - i, board.getTile(Minotaur.getCurrentTile() - i));
					if(MinotaurBoard.getTile(Minotaur.getCurrentTile() - i).getSupply())
						MinotaurBoard.setSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() - i)-1, board.getSupply(board.TileIdToSupplyId(Minotaur.getCurrentTile() - i)-1));
				}
			}
			
			Minotaur.setBoard(MinotaurBoard);
			
			//printBoard(MinotaurBoard.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile()), Dimensions);
			
			// Time for Minotaur to move
			System.out.println("------------------------------------------");
			System.out.println("Minotaur's turn to move\n"); 	
			Minotaur.move(Minotaur.getNextMove());

			// Prints the board after Theseus moves.
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