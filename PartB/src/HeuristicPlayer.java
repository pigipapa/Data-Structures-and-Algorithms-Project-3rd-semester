import java.util.*;

public class HeuristicPlayer extends Player{
	ArrayList <int[]> path;
	int tileDistMinotaur;
	int tileDistSupply; 
	
	
	public HeuristicPlayer() 
	{
		super();
		
		path = new ArrayList<int[]>();
		tileDistMinotaur = -1;
		tileDistSupply = -1;
	}
	
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y, int[] dice, int[] allScores, int[] isNearToEnemy, int[] isNearToSupply, int tileDistMinotaur, int tileDistSupply) 
	{
		super(playerId, name, board, score, x, y);
		
		this.tileDistMinotaur = tileDistMinotaur; // These two variables should initialized in Game as -1.
		this.tileDistSupply = tileDistSupply;
		
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
		tileDistSupply = -1;
		tileDistMinotaur = -1;
		
		switch(dice)
		{
			case 1: // Up
				
				for(int i = 1; i < 4; i++)
					if((!board.getTile(currentTile + i*dimension).getDown()) && (currentTile + i*dimension) < dimension * dimension)
					{
						if(board.getTile(currentTile + i*dimension).getSupply() && (NearSupplies == 0)) {
							NearSupplies = 1 - 0.33*i;
							tileDistSupply = i;
						}

						if((board.getMinotaurTile() == currentTile + i*dimension) && (OpponentDist == 0)) {			
							OpponentDist = -1 + 0.33*i;
							tileDistMinotaur = i;
						}
					}
					
			break;
				
			case 3: // Right
				
				for(int i = 1; i < 4; i++)
					if((!board.getTile(currentTile + i*1).getLeft()) && (currentTile + i*1) < (getX()+1)*dimension-1)
					{
						if(board.getTile(currentTile + i*1).getSupply() && (NearSupplies == 0)) {
							NearSupplies = 1 - 0.33*i;
							tileDistSupply = i;
						}
						
						if((board.getMinotaurTile() == currentTile + i*1) && (OpponentDist == 0)) { 
							OpponentDist = -1 + 0.33*i;
							tileDistMinotaur = i;
						}
					}
			
			break;
		
			case 5: // Down
				
				for(int i = 1; i < 4; i++) 					
					if((!board.getTile(currentTile - i*dimension).getUp()) && (currentTile - i*dimension) > 0)
					{
						if(board.getTile(currentTile - i*dimension).getSupply() && (NearSupplies == 0)) {
							NearSupplies = 1 - 0.33*i;
							tileDistSupply = i;
						}
							
						if((board.getMinotaurTile() == currentTile - i*dimension) && (OpponentDist == 0)) { 
							OpponentDist = -1 + 0.33*i;
							tileDistMinotaur = i;
						}
					}
						
				
					
			break;
		
			case 7: // Left
				
				for(int i = 1; i < 4; i++) 
					if((!board.getTile(currentTile - i*1).getRight()) && (currentTile - i*1) > getX()*dimension)
					{
						if(board.getTile(currentTile - i*1).getSupply() && (NearSupplies == 0)) {
							NearSupplies = 1 - 0.33*i;
							tileDistSupply = i;
						}

						if((board.getMinotaurTile() == currentTile - i*1) && (OpponentDist == 0)) {
							OpponentDist = -1 + 0.33*i;	
							tileDistMinotaur = i;
						}
					}								
			break;
		}		
		
		return (NearSupplies * 0.4 + OpponentDist * 0.6);		
	}	
	
	int getNextMove(int round)
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
		
		// path (info about the dice (0), points of the movement (1), is near to a supply (2), is near to enemy(3))
		
			path.get(0)[round] = (int) bestDice;
			path.get(1)[round] = 0; //????
			path.get(3)[round] = tileDistSupply;
			path.get(3)[round] = tileDistMinotaur;
		
		return (int) bestDice;
	}
	
	
	void statistics() {
		
	}
}
