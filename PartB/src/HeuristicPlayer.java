import java.util.*;

public class HeuristicPlayer extends Player{
	ArrayList <ArrayList<Integer>> path;
	int tileDistMinotaur;
	int tileDistSupply; 
		
	public HeuristicPlayer() 
	{
		super();
		
		path = new ArrayList<ArrayList<Integer>>(0);
		tileDistMinotaur = -1;
		tileDistSupply = -1;
	}
	
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y) 
	{
		super(playerId, name, board, score, x, y);
		
		tileDistMinotaur = -1; // These two variables should be initialized in Game as -1.
		tileDistSupply = -1;
		
		path = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 
	}
	
	public HeuristicPlayer(Player player, ArrayList<ArrayList<Integer>> path) 
	{
		super(player);
		
		for(int i = 0; i < path.size(); i++)
			this.path.set(i, new ArrayList<Integer>(path.get(i)));
	}

	int getTileDistMinotaur(){ return tileDistMinotaur; }

	void setTileDistMinotaur(int tileDistMinotaur) {this.tileDistMinotaur = tileDistMinotaur;}
	
	int getTileDistSupply() { return tileDistSupply; } 
	
	void setTileDistSupply(int tileDistSupply) {this.tileDistSupply = tileDistSupply;} 
	
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
					if(((currentTile + i*dimension) < dimension * dimension) && !board.getTile(getCurrentTile()).getUp())
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
					if(((currentTile + i*1) < (getX()+1)*dimension-1) && !board.getTile(getCurrentTile()).getRight())
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
					if(((currentTile - i*dimension) > 0) && !board.getTile(getCurrentTile()).getDown())if((!board.getTile(currentTile - i*dimension).getUp()))
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
					if(((currentTile - i*1) > getX()*dimension) && !board.getTile(getCurrentTile()).getLeft())
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
		System.out.println("evaluation: " + (NearSupplies * 0.4 + OpponentDist * 0.6));
		return (NearSupplies * 0.4 + OpponentDist * 0.6);		
	}	
	
	int getNextMove()
	{
		double[][] evaluation = new double[4][2];
		
		for(int i = 0; i < 4; i++)
		{
			evaluation[i][0] = 2*i+1;
			evaluation[i][1] = evaluate(2*i+1);	
		}

		double maxEvaluation = -2;
		int bestDice  = 0;
		
		for(int i = 0; i < 4; i++)
			if(maxEvaluation < evaluation[i][1])
			{
				maxEvaluation = evaluation[i][0];
				bestDice = (int) evaluation[i][1];
			}
		
		if(bestDice == 0)
		{
			Random rand = new Random();
			int n = rand.nextInt(4);
			bestDice = 2*n + 1;
			boolean flag = true;

			while(flag)
			{
				switch(bestDice)
				{
					case 1:
						if(board.getTile(currentTile).getUp())					
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						break;

					case 3:
						if(board.getTile(currentTile).getRight())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
					
					case 5:
						if(board.getTile(currentTile).getDown() || (getCurrentTile() == 0))
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;

						break;

					case 7:
						if(board.getTile(currentTile).getLeft())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
				}
			}
		}
				
		// path (info about the dice (0), points of the movement (1), is near to a supply (2), is near to enemy(3))
		
		path.get(0).add(bestDice);
		path.get(1).add(0); //????
		path.get(2).add(tileDistSupply);
		path.get(3).add(tileDistMinotaur);

		switch(bestDice)
		{
			case 1:
				path.get(4).add(1);
				break;
			case 3:
				path.get(5).add(3);
				break;
			case 5:
				path.get(6).add(5);
				break;
			case 7:
				path.get(7).add(7);
				break;
		}
		return bestDice;
	}
	
	
	void statistics( ) {
		// Dice
		System.out.println(getName() + "'s' dice was " + path.get(0).get(path.get(0).size()-1) + ".");
		
		// Supplies
		System.out.println(getName() + " collected " + getScore() + " supplies.");

		// Supply Distance
		if(getTileDistSupply() != -1)
			System.out.println(getName() + "'s distance from the closest supply is " + getTileDistSupply() + ".");
		else
			System.out.println(getName() + " can't see any supply.");

		// Moves
		System.out.println(getName() + " moved up " + path.get(4).size() + " times.");
		System.out.println(getName() + " moved right " + path.get(5).size() + " times.");
		System.out.println(getName() + " moved down " + path.get(6).size() + " times.");
		System.out.println(getName() + " moved left " + path.get(7).size() + " times.");

	}

	int DiceCalculation(int bestDice)
	{
		Random rand = new Random(System.currentTimeMillis());
		int n = rand.nextInt(3);
		n = (n + 1)*2;

		return ((bestDice + n) % 8);
	}
}
