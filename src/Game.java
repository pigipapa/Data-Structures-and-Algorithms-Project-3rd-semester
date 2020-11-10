import java.util.Random;

	//@authors Pigi Papanikolaou (10062), +0306978806503, pigipapa@ece.auth.gr
	//			Vasiliki Pappa (09981), +0306984119813, vasilikip@ece.auth.gr
 

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
	
	
	public static void main(String[] args)
	{
		Random rand = new Random(System.currentTimeMillis());
		
		// Board variables
		int Dimensions = 15;  
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		
		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		Player Minotaur = new Player(1, "Minotaur", board, 0, (Dimensions-1)/2, (Dimensions-1)/2); 
		Player Theseus = new Player(2, "Theseus", board, 0, 0, 0); 
		
		int direction = 0;	// The direction the player will move towards.
		int n = 0;
		int times;
		for(times = 0; times < 200; times++)
		{
			game.setRound(game.getRound()+1);
			
			System.out.println("Current round: " + game.getRound());
			
			String newLine = System.getProperty("line.separator");
			// Prints the board before players take their turn to play.
			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
				
			// Time for Theseus to move
			if(Theseus.getCurrentTile() == 0 ) {	// If Theseus is on the first tile he can't escape from the maze.
				n = rand.nextInt(2);
				direction = 2*n + 1;
				Theseus.move(direction);
			}
			else {
				n = rand.nextInt(4);
				direction = 2*n + 1;
				Theseus.move(direction);
			}
					    
		    // Prints the board after Theseus moves.
			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
			
			if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())	// Theseus went in the tile where Minotaur was.
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");	// There is a possibility because the moves are random, Theseus walks onto Minotaur,
				break;																	// so we check it here.
			}
			
			// Time for Minotaur to move
			if(Minotaur.getCurrentTile() == 0) {	// If Minotaur is on the first tile he can't escape from the maze.
				n = rand.nextInt(2);
				direction = 2*n + 1;
				Minotaur.move(direction);
			}
			else {
				n = rand.nextInt(4);
				direction = 2*n + 1;
				Minotaur.move(direction);
			}
			
			// Prints the board after Theseus moves.
			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
			
			System.out.println("Theseus' current score: " + Theseus.getScore());
			
			// Check if game should be finished.
			if(Theseus.getScore() == Supplies) 												// Theseus got all supplies.
			{
				System.out.println("Theseus gathered all supplies. Theseus is the winner.");
				break;
			}
			
			if(Theseus.getCurrentTile() == Minotaur.getCurrentTile())	// Theseus went in the tile where Minotaur was.
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");
				break;
			}
			
			if(times<199) {
			System.out.println("==========================================");
			}
		}
		
		if(times == 200) System.out.println("Tie...");	// Nobody won...
	}
}