import java.util.Random;

public class Game {
	int round;
	
	Game(){ this.round = 1; }
	
	int getRound() { return round; }
	void setRound(int round) { this.round = round; }
	
	
	public static void main(String[] args)
	{	    
		Random rand = new Random(System.currentTimeMillis());
		
		// Board variables
		int Dimensions = 5;  //egrafe 15
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		
		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		Player Minotaur = new Player(1, "Minotaur", board, 0, (Dimensions-1)/2, (Dimensions-1)/2); 
		Player Theseus = new Player(2, "Theseus", board, 0, 0, 0); 
		
		int direction = 0; // The direction the player will move to
		int n = 0;
		int times;
		for(times = 0; times < 200; times++)
		{
			System.out.println("======================================");
			System.out.println();			
			System.out.println("Current round: " + game.getRound());
			
			
			String newLine = System.getProperty("line.separator");
			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
				
			// Time for Theseus to move
			if(Theseus.getCurrentTile() == 0) 
			{
				n = rand.nextInt(2);
				direction = 2*n + 1;
				Theseus.move(direction);
			}
			else {
				n = rand.nextInt(4);
				direction = 2*n + 1;
				Theseus.move(direction);
			}

			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
			
			// Time for Minotaur to move
			if(Minotaur.getCurrentTile() == 0)
			{
				n = rand.nextInt(2);
				direction = 2*n + 1;
				Minotaur.move(direction);
			}				
			else 
			{
				n = rand.nextInt(4);
				direction = 2*n + 1;
				Minotaur.move(direction);
			}
			
			for (int i = 2*Dimensions; i >= 0; i--) {
				for (int j = 0; j < Dimensions; j++) {

					System.out.print(board.getStringRepresentation(Theseus.getCurrentTile(), Minotaur.getCurrentTile())[i][j]);
				}
				System.out.print(newLine);
			}
			
			System.out.print(newLine);
			System.out.println("Theseus' current score: " + Theseus.getScore());
			
			// Check if game should be finished
			if(Theseus.getScore() == Supplies) // Theseus got all supplies
			{
				System.out.println("Theseus gathered all supplies. Theseus is the winner.");
				break;
			}
			
			if((Theseus.getX() == Minotaur.getX()) && (Theseus.getY() == Minotaur.getY())) // Theseus went in the tile where Minotaur was
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");
				break;
			}		
			
			game.setRound(game.getRound()+1);
		}
		
		if(times == 200) System.out.println("Tie...");
	}
}