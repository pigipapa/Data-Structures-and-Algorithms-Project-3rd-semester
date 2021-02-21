public class Main
{
    public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 5;  
		int Supplies = 2;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		int maxRounds = 100;	// If max dices to tie up the game are 200, max rounds are 100.
       
        Board board = new Board(Dimensions, Supplies, Walls);
		IntroFrame introFrame = new IntroFrame(board, maxRounds);
	}

}