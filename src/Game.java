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
		int Dimensions = 15;
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		
		Game game = new Game();
		Board board = new Board(Dimensions, Supplies, Walls);
		Player Minotaur = new Player(1, "Minotaur", board, 0, (Dimensions-1)/2, (Dimensions-1)/2); 
		Player Theseus = new Player(2, "Theseus", board, 0, 0, 0); 
		
		int direction = 0; // The direction the player will move to
		int n = 0;
		
		for(int times = 0; times < 200; times++)
		{
			System.out.println("Current round: " + game.getRound());
//			Board.getStringRepresentation();
			
			// Time for Theseus to move
			n = rand.nextInt(4);
			direction = 2*n + 1;
			Theseus.move(direction);
//			Board.getStringRepresentation();
			
			// Time for Minotaur to move
			n = rand.nextInt(4);
			direction = 2*n + 1;
			Minotaur.move(direction);
//			Board.getStringRepresentation();
			
			// Check if game should be finished
			if(Theseus.getScore() == Supplies) // Theseus got all supplies
			{
				System.out.println("Theseus gathered all supplies. Theseus is the winner.");
				break;
			}
			else if((Theseus.getX() == Minotaur.getX()) && (Theseus.getY() == Minotaur.getY())) // Theseus went in the tile where Minotaur was
			{
				System.out.println("Minotaur got Theseus. Minotaur is the winner.");
				break;
			}
			
			game.setRound(game.getRound()+1);
		}
		
		System.out.println("Tie!");
	}
}
