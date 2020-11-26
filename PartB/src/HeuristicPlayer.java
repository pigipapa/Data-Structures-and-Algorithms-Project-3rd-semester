import java.util.*;

public class HeuristicPlayer extends Player{
	ArrayList <ArrayList<Integer>> path;
	int LastMove;
	int tileDistOpponent;
	int tileDistSupply;
	//Board boardH;
		
	public HeuristicPlayer() 
	{
		super();
		
		path = new ArrayList<ArrayList<Integer>>();
		LastMove = -1;
	}
	

	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y, int LastMove) 
	{
		super(playerId, name, board, score, x, y);
		
		this.LastMove = LastMove; //Initialized <1. Better as -1.
		//boardH = board;
		
		path = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 
	}
	
	public HeuristicPlayer(Player player, ArrayList<ArrayList<Integer>> path) 
	{
		super(player);				//??????
		
		for(int i = 0; i < path.size(); i++) {

			this.path.set(i, new ArrayList<Integer>(path.get(i)));

			this.path.add(i, path.get(i));
		}

	}

	
	int getLastMove() { return LastMove; }
	
	void setLastMove(int LastMove) { this.LastMove = LastMove; }
	
	int getTileDistOpponent(){ return tileDistOpponent; }

	void setTileDistOpponent(int tileDistOpponent) {this.tileDistOpponent = tileDistOpponent;}
	
	int getTileDistSupply() { return tileDistSupply; } 
	
	void setTileDistSupply(int tileDistSupply) {this.tileDistSupply = tileDistSupply;}
	
	public int getOpponentTile() {
		int GetMinotaurTile = board.getMinotaurTile();
		int GetTheseusTile = board.getTheseusTile();
		int opp = 0;
		opp = (playerId == 1) ? GetTheseusTile : GetMinotaurTile;
		return opp;
	}
	
	double evaluate(int dice, int tileDistSupply, int tileDistOpponent)
	{
		double NearSupplies = 0;
		double OpponentDist = 0;
		int dimension = board.getN();
		//tileDistSupply = -1;
		//tileDistOpponent = -1;
		int sign = (playerId == 2) ? -1 : 1;
		
		switch(dice)
		{
			case 1: // Up
				
				if(tileDistSupply != -1) { 
					NearSupplies = 1.0/tileDistSupply;
				}
	
				if(tileDistOpponent != -1) {			
					OpponentDist = sign*(1.0/tileDistOpponent);
				}
					
			break;
				
			case 3: // Right
			
				if(tileDistSupply != -1) {
					NearSupplies = 1.0/tileDistSupply ;
				}
								
				if(tileDistOpponent != -1) { 
					OpponentDist = sign*(1.0/tileDistOpponent);
				}
			
			break;
		
			case 5: // Down
								
				if(tileDistSupply != -1) {
					NearSupplies = 1.0/tileDistSupply ;
										
				}
										
				if(tileDistOpponent != -1) { 
					OpponentDist = sign*(1.0/tileDistOpponent);
				}

			break;
		
			case 7: // Left
				
				if(tileDistSupply != -1) {
					NearSupplies = 1.0/tileDistSupply;
				}
		
				if(tileDistOpponent != -1) {
					OpponentDist = sign*(1.0/tileDistOpponent);
				}

			break;
		}
		
		
		if(playerId == 2)
			System.out.println(name + " evaluation: " + (NearSupplies * 0.4 + OpponentDist * 0.6));
		else
			System.out.println(name + " evaluation: " + (NearSupplies * 0.2 + OpponentDist * 0.8));
		
			
		if(playerId == 2)
			return (NearSupplies * 0.4 + OpponentDist * 0.6);		
		else 
			return (NearSupplies * 0.2 + OpponentDist * 0.8);
	}	
	
	public int getNextMove()
	{	
		int dimension = board.getN();
		
		int initialX = x;
		int initialY = y;
		int initialCurrentTile = currentTile;
		
		tileDistSupply = -1;
		tileDistOpponent = -1;
		
		double[][] evaluation = new double[4][2]; 
		
		for(int i=0; i<4; i++) {
			
			int dice = 2*i+1;
			evaluation[i][0] = dice;
			tileDistSupply = -1;
			tileDistOpponent = -1;
			
			for(int j=1; j<3; j++) {
				
				switch(dice) {
				
				case 1:
					
					if(board.getTile(currentTile).getUp()) break;
					else {
						int iterationTimes = (x + 1) - initialX;
						if(board.getTile(currentTile + dimension).getSupply() && tileDistSupply == -1) {
							tileDistSupply = iterationTimes;
						}
						if(currentTile + dimension == getOpponentTile()) {
							tileDistOpponent = iterationTimes;
						}
						x = x + 1; 
						y = y;
						currentTile = y + x * dimension;
					}
				 break;
				
				case 3:
					
					if(board.getTile(currentTile).getRight()) break;
					else {
						int iterationTimes = (x + 1) - initialX;
						if(board.getTile(currentTile + 1).getSupply() && tileDistSupply == -1) {
							tileDistSupply = iterationTimes;
						}
						if(currentTile + 1 == getOpponentTile()) {
							tileDistOpponent = iterationTimes;
						}
						x = x;
						y = y + 1;
						currentTile = y + x * dimension;
					}
				 break;
				 
				case 5:
					
					if(board.getTile(currentTile).getDown() || currentTile == 0) break;
					else {
						int iterationTimes = (x + 1) - initialX;
						if(board.getTile(currentTile - dimension).getSupply() && tileDistSupply == -1) {
							tileDistSupply = iterationTimes;
						}
						if(currentTile - dimension == getOpponentTile()) {
							tileDistOpponent = iterationTimes;
						}
						x = x - 1;
						y = y;
						currentTile = y + x * dimension;
					}
				 break;
				 
				case 7:
					
					if(board.getTile(currentTile).getLeft()) break;
					else {
						int iterationTimes = (x + 1) - initialX;
						if(board.getTile(currentTile - 1).getSupply() && tileDistSupply == -1) {
							tileDistSupply = iterationTimes;
						}
						if(currentTile - 1 == getOpponentTile()) {
							tileDistOpponent = iterationTimes;
						}
						x = x;
						y = y - 1;
						currentTile = y + x * dimension;
					}
				 break;
				 
				}
				
			}
			evaluation[i][1] = evaluate(dice, tileDistSupply, tileDistOpponent);

		}
		
		
		x = initialX;
		y = initialY;
		currentTile = initialCurrentTile;
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		boolean allEvaluationsAreZero = true;
		double maxEvaluation = -2;
		double belowZeroEvaluation = 10;
		int bestDice = 0;
		int worstDice = 0;
		
		for(int i = 0; i < 4; i++)
		{
			if(maxEvaluation < evaluation[i][1])
			{
				maxEvaluation = evaluation[i][1];
				bestDice = (int) evaluation[i][0];
			}
			if(evaluation[i][1] != 0)
				allEvaluationsAreZero = false;
			
			if(belowZeroEvaluation > evaluation[i][1]) {
				belowZeroEvaluation = evaluation[i][1];
				worstDice = (int) evaluation[i][0];
			}
		}
		
		if(allEvaluationsAreZero == false && maxEvaluation == 0) {
			boolean flag = true;

			while(flag)
			{
				switch(bestDice)
				{
					case 1:
						if(bestDice == worstDice) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getUp())					
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						break;
	
					case 3:
						if(bestDice == worstDice) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getRight())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
					
					case 5:
						if(bestDice == worstDice) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getDown() || (getCurrentTile() == 0)) 
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
	
						break;
	
					case 7:
						if(bestDice == worstDice) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getLeft())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
				}
			}
			
			
		}

		if(allEvaluationsAreZero)
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
						if(LastMove == 5 && currentTile != 0) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getUp())					
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						break;

					case 3:
						if(LastMove == 7 && currentTile != 0) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getRight())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
					
					case 5:
						if(LastMove == 1) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getDown() || (getCurrentTile() == 0)) 
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;

						break;

					case 7:
						if(LastMove == 3) {
							bestDice = DiceCalculation(bestDice);
							continue;
						}
						if(board.getTile(currentTile).getLeft())
							bestDice = DiceCalculation(bestDice);
						else
							flag = false;
						
						break;
				}
			}
			
			
		}
		
		LastMove = bestDice;
				
		// path (info about the dice (0), points of the movement (1), is near to a supply (2), is near to enemy(3))
		
		path.get(0).add(bestDice);
		
		int d = 0;
		if(name == "Theseus") {  // If player is Theseus.
			switch(bestDice) {
			
				case 1:
					d = board.getTile(currentTile + dimension).getSupply() ? 1 : 0;
					break;
				
				case 3:
					d = board.getTile(currentTile + 1).getSupply() ? 1 : 0;
					break;
				case 5:
					d = board.getTile(currentTile - dimension).getSupply() ? 1 : 0;
					break;
				case 7:
					d = board.getTile(currentTile -1).getSupply() ? 1 : 0;
					break;
				
			}
		}
		path.get(1).add(d); 
		path.get(2).add(tileDistSupply);
		path.get(3).add(tileDistOpponent);

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
	
	
	void statistics(String when) {

		if(when == "everyRound")
		{
			// Dice
			System.out.println(getName() + "'s' dice is " + path.get(0).get(path.get(0).size()-1) + ".");
			
			// Supplies
			if(playerId == 2)
			{
				System.out.println(getName() + " has collected " + getScore() + " supplies.");

				// Supply Distance
				if(getTileDistSupply() != -1)
					System.out.println(getName() + "'s distance from the closest supply is " + getTileDistSupply() + ".");
				else
					System.out.println(getName() + " can't see any supply.");
			}
		}

		// Moves
		if(when == "finalRound")
		{
			System.out.println(getName() + " moved up " + path.get(4).size() + " times.");
			System.out.println(getName() + " moved right " + path.get(5).size() + " times.");
			System.out.println(getName() + " moved down " + path.get(6).size() + " times.");
			System.out.println(getName() + " moved left " + path.get(7).size() + " times.");
		}

	}

	int DiceCalculation(int bestDice)
	{
		Random rand = new Random(System.currentTimeMillis());
		int n = rand.nextInt(3);
		n = (n + 1)*2;

		return ((bestDice + n) % 8);
	}
}
