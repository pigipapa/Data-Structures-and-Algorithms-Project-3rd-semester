public class Main
{
    public static void main(String[] args)
	{		
		// Board variables
		int Dimensions = 13;  
		int Supplies = 4;
		int Walls = (Dimensions*Dimensions*3+1)/2;
		int maxRounds = 5;	// If max dices to tie up the game are 200, max rounds are 100.
       
        Board board = new Board(Dimensions, Supplies, Walls);
		IntroFrame introFrame = new IntroFrame(board, maxRounds);
	}

}