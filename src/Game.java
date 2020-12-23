import java.util.*;
/**
 * Class that contains the main and implements the game. 
 */
public class Game {
	private int round;	// Game's number of total rounds.
	private Board board;
	private MinMaxPlayer Minotaur;
	private MinMaxPlayer Theseus;
	private int maxRounds;

	public Game(Board gameBoard, MinMaxPlayer Minotaur, MinMaxPlayer Theseus, int maxRounds)
	{ 
		this.maxRounds = maxRounds;
		this.round = 0; 
		this.board = new Board(gameBoard);
		this.Minotaur = new MinMaxPlayer(Minotaur); 
		this.Theseus = new MinMaxPlayer(Theseus); 
	}

	Board getBoard() { return board; }
	
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

	void movePlayers()
	{
		double inf = Double.POSITIVE_INFINITY;
		Node TheseusNode = new Node(new Node(), new ArrayList<Node>(), 0, new int[3], board, (-1)*inf, Minotaur);
		Theseus.move(Theseus.getNextMove(TheseusNode), board);
		Theseus.setBoard(board);
		System.out.println(Theseus.getX()+" "+Theseus.getY());

		Node MinotaurNode = new Node(new Node(), new ArrayList<Node>(), 0, new int[3], board, (-1)*inf, Theseus);
		Minotaur.move(Minotaur.getNextMove(MinotaurNode), board);
		Minotaur.setBoard(board);
		System.out.println(Minotaur.getX()+" "+Minotaur.getY());
	}
	
}