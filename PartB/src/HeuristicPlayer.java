import java.util.*;

public class HeuristicPlayer extends Player{
	ArrayList <int[]> path;
	
	public HeuristicPlayer() 
	{
		super();
		
		path = new ArrayList<int[]>();
	}
	
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y, int[] dice, int[] allScores, int[] isNearToEnemy, int[] isNearToSupply) 
	{
		super(playerId, name, board, score, x, y);
		
		path = new ArrayList<int[]>(4);
		path.add(0, dice);
		path.add(1, allScores);
		path.add(2, isNearToEnemy);
		path.add(3, isNearToSupply);
		
	}
	
	public HeuristicPlayer(Player player, ArrayList<int[]> path) 
	{
		super(player);
		
		for(int i = 0; i < path.size(); i++)
			this.path.add(path.get(i));
	}
	
	double evaluate(int dice)
	{
		double NearSupplies = 0;
		double OpponentDist = 0;
		int dimension = board.getN();
		
		switch(dice)
		{
			case 1: // Up
				
				for(int i = 1; i < 4; i++) {
					
					if((!board.getTile(currentTile + i*dimension).getDown()) && (currentTile + i*dimension) < dimension * dimension)
					{
						if(board.getTile(currentTile + i*dimension).getSupply())
						{
							NearSupplies = i;
							break;
						}	
					}
				}
				
				for(int i = 1; i < 4; i++) {
					
					if((!board.getTile(currentTile + i*dimension).getDown()) && (currentTile + i*dimension) < dimension * dimension)
					{
						if(board.getMinotaurTile() == currentTile + i*dimension) 
						{						
							OpponentDist = i;
							break;
						}
					}
				} break;
				
			case 3: // Right
				
				for(int i = 1; i < 4; i++) {
					
					if((!board.getTile(currentTile + i*1).getLeft()) && (currentTile + i*1) < (getX()+1)*dimension-1)
					{
						if(board.getTile(currentTile + i*1).getSupply())
						{
							NearSupplies = i;
							break;
						}
					}
				}
						
				for(int i = 1; i < 4; i++) {
							
					if((!board.getTile(currentTile + i*1).getLeft()) && (currentTile + i*1) < (getX()+1)*dimension-1)
					{
						if(board.getMinotaurTile() == currentTile + i*1) 
						{
							OpponentDist = i;
							break;
						}
					}	
				} break;
		
			case 5: // Down
				
				for(int i = 1; i < 4; i++) {
					
					if((!board.getTile(currentTile - i*dimension).getUp()) && (currentTile - i*dimension) > 0)
					{
						if(board.getTile(currentTile - i*dimension).getSupply())
						{
							NearSupplies = i;
							break;
						}
					}
				}
				
				for(int i = 1; i < 4; i++) {
					
					if((!board.getTile(currentTile - i*dimension).getUp()) && (currentTile - i*dimension) > 0)
					{
						if(board.getMinotaurTile() == currentTile - i*dimension) 
						{
							OpponentDist = i;
							break;
						}
					}
					
				} break;
		
			case 7: // Left
				
				for(int i = 1; i < 4; i++) {
					if((!board.getTile(currentTile - i*1).getRight()) && (currentTile - i*1) > getX()*dimension)
					{
						if(board.getTile(currentTile - i*1).getSupply())
						{
							NearSupplies = i;
							break;
						}
					}
				}
				
				for(int i = 1; i < 4; i++) {
					if((!board.getTile(currentTile - i*1).getRight()) && (currentTile - i*1) > getX()*dimension)
					{	
						if(board.getMinotaurTile() == currentTile - i*1) 
						{	
							OpponentDist = i;
							break;
						}
					}
					
				} break;
		}		
		
		return (NearSupplies * 0.46 + OpponentDist * 0.54);		
	}	
	
	int getNextMove()
	{
		double[][] evaluation = new double[4][2];
		
		for(int i = 0; i < 4; i++)
		{
			evaluation[i][1] = 2*i+1;
			evaluation[i][2] = evaluate(2*i+1);	
		}

		double maxEvaluation = -2;
		double bestDice  = 0;
		
		for(int i = 0; i < 4; i++)
			if(maxEvaluation < evaluation[i][2])
			{
				maxEvaluation = evaluation[i][2];
				bestDice = evaluation[i][1];
			}
		
		// path
		
		return (int) bestDice;
	}
	
	
	void statistics() {
		
	}
}
