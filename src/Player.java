import java.util.Random;

public class Player {

	int playerId; // 1 for Minotaur, 2 for Theseus
	String name;
	Board board;
	int score;
	int x;
	int y;
	
	Player()
	{
		this.board = new Board();
	}
	
	Player(int playerId, String name, Board board, int score, int x, int y)
	{
		this.playerId = playerId; 
		this.name = name;
		this.board = new Board(board); 
		this.score = score;
		this.x = x;
		this.y = y;
	}
	
	Player(Player player)
	{
		this.playerId = player.getPlayerId(); 
		this.name = player.getName() ;
		this.board = new Board(player.board); 
		this.score = player.getScore() ;
		this.x = player.getX() ;
		this.y = player.getY() ;
	}	
	
	int getPlayerId() { return playerId; }
	
	void setPlayerId(int playerId) { this.playerId = playerId; }
	
	String getName() { return name; }
	
	void setName(String name) {this.name = name;}
	
	int getScore() { return score; }
	
	void setScore(int score) { this.score  = score; }
	
	int getX() { return x; }
	
	void setX(int x) { this.x = x; }

	int getY() { return y; }
	
	void setY(int y) { this.y = y;}
	
	int[] move(int id)
	{
		// TODO: 1) check whether player is Minotaur or Theseus
		//       2) id?
		//       3) supplies
		
		Random rand  = new Random();
		int n = rand.nextInt(4);
		int move = 2*n + 1;
		
		int currentTile = this.y + this.x * board.getN();
		
		int supplies = -1; //when no supply is got, supplies value is -1
		
		int[] array = {id, this.x, this.y, supplies};
		
		switch(move)
		{
			case 1:
				
				if(board.tiles[currentTile].getUp())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ this.y = this.y + 1; }
				
			case 3:
				
				if(board.tiles[currentTile].getRight())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ this.x = this.x + 1; }
				
			case 5:
				
				if(board.tiles[currentTile].getDown())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else
				{ this.y = this.y - 1; }
				
			case 7:
				
				if(board.tiles[currentTile].getLeft())
				{
					System.out.println("Player didn't move. Wall ahead!");
					break;					
				}
				else { this.x = this.x - 1; }
		}
		
		
		if(id == 2)
		{
			
		}
		
		return array;
	}
}
