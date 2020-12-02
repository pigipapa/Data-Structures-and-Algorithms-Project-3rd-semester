import java.util.*;

public class HeuristicPlayer extends Player{
	
	// Structure (ArrayList that consists of ArrayLists of Integers) that gives information about players each movement in the current game.
	// path.get(0)->chosen dice
	// path.get(1)->shows if tile after the movement has or not supply
	// path.get(2)->distance from nearest supply
	// path.get(3)->distance from opponent
	// path.get(4)->times player moved up
	// path.get(5)->times player moved right
	// path.get(6)->times player moved down
	// path.get(7)->times player moved left
	ArrayList <ArrayList<Integer>> path;
	// Variable that keeps the last dice of the player.
	int LastMove;							
	// !Used only by Minotaur! 
	// Array that consists of counters for each supply, that show how many times Minotaur has been on a tile that has a specific supply on it. 
	int[] timesBeenOnTheSupply;			
	// !Used only by Minotaur!
	// Array that implements if Minotaur has crossed a tile that contains supply enough time by now.
	boolean[] enoughTimesBeenOnTheSupply;	
		
	/**
	 * Initializes a clever/heuristic player. Variables are initialized to -1, arrays and structures are initialized without content.  
	 */
	public HeuristicPlayer() 
	{
		super();
		
		path = new ArrayList<ArrayList<Integer>>();
		LastMove = -1;
		timesBeenOnTheSupply = new int[board.getS()];
		enoughTimesBeenOnTheSupply = new boolean[board.getN()*board.getN()];
	}
	
	/**
	 * Initializes a clever/heuristic player.  
	 * @param playerId
	 * @param name
	 * @param board
	 * @param score
	 * @param x
	 * @param y
	 * @param LastMove
	 */
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y, int LastMove) 
	{
		super(playerId, name, board, score, x, y);
		
		this.LastMove = LastMove; //Initialized <1. Better as -1.
		
		path = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 

		timesBeenOnTheSupply = new int[board.getS()];

		for(int i = 0; i < board.getS(); i++)
			timesBeenOnTheSupply[i] = 0;

		enoughTimesBeenOnTheSupply = new boolean[board.getN()*board.getN()];

		for(int i = 0; i < (board.getN()*board.getN()); i++)
			enoughTimesBeenOnTheSupply[i] = false;
	}
	
	/**
	 * Initializes HeuristicPlayer with the values of another HeuristicPlayer.
	 * @param player
	 */
	public HeuristicPlayer(HeuristicPlayer player) 
	{
		super(player);
		
		for(int i = 0; i < player.path.size(); i++) {

			this.path.set(i, new ArrayList<Integer>(player.path.get(i)));

			this.path.add(i, player.path.get(i));
		}
		
		this.LastMove = player.LastMove;
		
		this.timesBeenOnTheSupply = player.timesBeenOnTheSupply.clone();
		
		this.enoughTimesBeenOnTheSupply =player.enoughTimesBeenOnTheSupply.clone();

	}
	
	/**
	 * Returns last dice.
	 * @return LastMove
	 */
	int getLastMove() { return LastMove; }
	
	/**
	 * Sets last dice.
	 * @param LastMove
	 */
	void setLastMove(int LastMove) { this.LastMove = LastMove; }
	
	/**
	 * Sets path.
	 * @param path
	 */
	void setPath(ArrayList<ArrayList<Integer>> path){ this.path = path; }
	
	/**
	 *Returns path. 
	 * @return path
	 */

	ArrayList<ArrayList<Integer>> getPath(){ return this.path; } 
	
	
	/**
	 * This function evaluates the quality of player's single movements on the board in the directions that is given by dice, considering if he sees supplies or his opponent. 
	 * @param dice
	 * @param tileDistSupply
	 * @param tileDistOpponent
	 * @return the evaluation of the player's single movement in the direction the dice implements. 
	 */
	double evaluate(int dice, int tileDistSupply, int tileDistOpponent)
	{
		// This variable's values can be 0.3, 0.5, 1 depending on the distance between the player and a supply, which he can see.
		// If two tiles keep them apart then NearSupplies = 0.3, else if tile distance is 1 then NearSupplies = 0.5, else if they are neighboring NearSupplies = 1. 
		double NearSupplies = 0;
		// This variable's values can be 0.3, 0.5, 1 if player is Minotaur and is close to Theseus at distance 2,1 and 0 tiles.
		// If player is Theseus, OppponentDist values can be -0.3 when his distance from Minotaur is 2 tiles, -0.5 if it's 1 tile and -1 if Minotaur is next to him.  
		double OpponentDist = 0;
		// A sign roulette. If player is Minotaur then sign = -1. If palyer is Theseus sign = 1. 
		int sign = (playerId == 2) ? -1 : 1;
		
		switch(dice)
		{
			case 1: // Up
				
				if(tileDistSupply != -1)	// If player sees supply
					// tileDistSupply can be 1, 2 and 3 depending on the distance between the player and the supply.
					//1-> supply on the next tile in the direction player is heading 
					//2-> one tile between the player and the supply
					//3-> two tiles between the player and the supply
					// The function we use is 1/x because 1/1=1, 1/2=0.5 and 1/3=0.33, which are the values NearSupplies is expected to have.
					NearSupplies = 1.0/tileDistSupply;	
	
				if(tileDistOpponent != -1)	// If player sees his opponent
					// The same comments as above, but this time player is looking for his opponent on the near tiles.
					// If player is Theseus sign=-1 so OpponentDist<0.
					// If palyer is Minotaur sign=1 so OpponentDist>0.
					OpponentDist = sign*(1.0/tileDistOpponent);
					
			break;
			
			// Same logic as above...
			
			case 3: // Right
			
				if(tileDistSupply != -1) 
					NearSupplies = 1.0/tileDistSupply ;
								
				if(tileDistOpponent != -1) 
					OpponentDist = sign*(1.0/tileDistOpponent);
			
			break;
		
			case 5: // Down
								
				if(tileDistSupply != -1) 
					NearSupplies = 1.0/tileDistSupply ;										
										
				if(tileDistOpponent != -1)  
					OpponentDist = sign*(1.0/tileDistOpponent);
				
			break;
		
			case 7: // Left
				
				if(tileDistSupply != -1) 
					NearSupplies = 1.0/tileDistSupply;
		
				if(tileDistOpponent != -1) 
					OpponentDist = sign*(1.0/tileDistOpponent);

			break;
		}
			
		
		if(playerId == 2)
			// Theseus' evaluation function 
			return (NearSupplies * 0.4 + OpponentDist * 0.6);		
		else 
			// Minotaurs's evaluation function
			return (NearSupplies * 0.2 + OpponentDist * 0.8);
	}	
	
	/**
	 * The function that returns the dice player is going to use in Game to move. 
	 * @return bestDice
	 */
	public int getNextMove()
	{	
		int dimension = board.getN();
		
		// Save the initial values of the player variables that show his position on the board. 
		int initialX = x;
		int initialY = y;
		int initialCurrentTile = currentTile;
		
		// If this variable is -1 then no supply is deactivated for Minotaur. 
		// Else the variable has the value of the supply id that has been deactivated.
		int deactivatedSupplyId = -1;
		
		// Initialized to -1.
		int tileDistSupply = -1;
		int tileDistOpponent = -1;

		// In the first column we store dice [1, 3, 5, 7] and in the second column we save the evaluations for each of these moves. 
		double[][] evaluation = new double[4][2]; 
		
		// Checks how many times Minotaur has been on the same supply (Minotaur is attracted by supplies because he may find Theseus near to them)
		if((playerId == 1) && board.getTile(currentTile).getSupply())
		{
			if(!enoughTimesBeenOnTheSupply[currentTile]) 
				timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] += 1;

			// Minotaur can be in the same tile as a supply only two times
			if(timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] == 2) 
			{
				// The supply which is been watched out by Minotaur is been deactivated
				timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] = -1;
				enoughTimesBeenOnTheSupply[currentTile] = true;	
				deactivatedSupplyId = board.TileIdToSupplyId(currentTile)-1;
			}	
		}

		// Road to supply reactivation for Minotaur

		for(int i = 0; i < board.getS(); i++)
		{
			if(i == deactivatedSupplyId)
				continue;

			if((timesBeenOnTheSupply[i] <= -1) && (timesBeenOnTheSupply[i] > -4))
				timesBeenOnTheSupply[i] -= 1;

			if(timesBeenOnTheSupply[i] == -4 && board.getSupply(i).getSupplyTileId() != -1) // Supply reactivation
			{
				timesBeenOnTheSupply[i] = 0;
				enoughTimesBeenOnTheSupply[board.getSupply(i).getSupplyTileId()] = false;
			}
		}
		
		boolean isTheClosestSupply = true;
		
		for(int i=0; i<4; i++) {
			
			int dice = 2*i+1;
			double cumulativeEvaluation = 0;
			evaluation[i][0] = dice;
			
			for(int j=0; j<4; j++) {
					
				tileDistSupply = -1;
				tileDistOpponent = -1;
				double tempEvaluation = 0;

				switch(dice) {
				
				case 1:	

					if(board.getTile(currentTile).getUp()) 
						break;
					else {
						int iterationTimes = (x + 1) - initialX;
						
						if(board.getTile(currentTile + dimension).getSupply())
						{	
							tileDistSupply = iterationTimes;
							
							if(isTheClosestSupply) {
								path.get(2).add(tileDistSupply);
								isTheClosestSupply = false;
							}
						
							if((playerId == 1)  && enoughTimesBeenOnTheSupply[currentTile + dimension])
								tileDistSupply = -1;
						}
						
						
						if(playerId == 2 && board.getTile(currentTile + dimension).hasMinotaur()) {
							tileDistOpponent = iterationTimes;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile + dimension).hasTheseus()) {
							tileDistOpponent = iterationTimes;
							path.get(3).add(tileDistOpponent);
						}
													
						tempEvaluation = evaluate(dice, tileDistSupply, tileDistOpponent);
						cumulativeEvaluation += tempEvaluation;

						if(j != 3)
						{
							x = x + 1; 
							y = y;
							currentTile = y + x * dimension;
						}
					}
					break;
				
				case 3:
					
					if(board.getTile(currentTile).getRight()) 
						break;
					else {
						int iterationTimes = (y + 1) - initialY;

						if(board.getTile(currentTile + 1).getSupply()) 
						{
							tileDistSupply = iterationTimes;	
							
							if(isTheClosestSupply) {
								path.get(2).add(tileDistSupply);
								isTheClosestSupply = false;
							}

							if((playerId == 1)  && enoughTimesBeenOnTheSupply[currentTile+1])
								tileDistSupply = -1;
						}
						if(playerId == 2 && board.getTile(currentTile + 1).hasMinotaur()) {
							tileDistOpponent = iterationTimes;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile + 1).hasTheseus()) {
							tileDistOpponent = iterationTimes;
							path.get(3).add(tileDistOpponent);
						}

						tempEvaluation = evaluate(dice, tileDistSupply, tileDistOpponent);
						cumulativeEvaluation += tempEvaluation;

						if(j != 3)
						{
							x = x;
							y = y + 1;
							currentTile = y + x * dimension;
						}						
					}
					break;
				 
				case 5:
					
					if(board.getTile(currentTile).getDown() || currentTile == 0) 
						break;
					else {
						int iterationTimes = initialX - (x - 1);

						if(board.getTile(currentTile - dimension).getSupply()) 
						{
							tileDistSupply = iterationTimes;	
							
							if(isTheClosestSupply) {
								path.get(2).add(tileDistSupply);
								isTheClosestSupply = false;
							}

							if((playerId == 1)  && enoughTimesBeenOnTheSupply[currentTile - dimension])
								tileDistSupply = -1;
						}

						if(playerId == 2 && board.getTile(currentTile - dimension).hasMinotaur()) {
							tileDistOpponent = iterationTimes;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile - dimension).hasTheseus()) {
							tileDistOpponent = iterationTimes;
							path.get(3).add(tileDistOpponent);
						}

						tempEvaluation = evaluate(dice, tileDistSupply, tileDistOpponent);
						cumulativeEvaluation += tempEvaluation;
					
						if(j != 3)
						{
							x = x - 1;
							y = y;
							currentTile = y + x * dimension;
						}						
					}
					break;
				 
				case 7:
					
					if(board.getTile(currentTile).getLeft()) 
						break;
					else {
						int iterationTimes = initialY - (y - 1);

						if(board.getTile(currentTile - 1).getSupply()) 
						{
							tileDistSupply = iterationTimes;	
							
							if(isTheClosestSupply) {
								path.get(2).add(tileDistSupply);
								isTheClosestSupply = false;
							}

							if((playerId == 1)  && enoughTimesBeenOnTheSupply[currentTile-1])
								tileDistSupply = -1;
						}
						if(playerId == 2 && board.getTile(currentTile - 1).hasMinotaur()) { 
							tileDistOpponent = iterationTimes;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile - 1).hasTheseus()) {
							tileDistOpponent = iterationTimes;
							path.get(3).add(tileDistOpponent);
						}
						
						tempEvaluation = evaluate(dice, tileDistSupply, tileDistOpponent);
						cumulativeEvaluation += tempEvaluation;
						
						if(j != 3)
						{
							x = x;
							y = y - 1;
							currentTile = y + x * dimension;
						}

					}
					break;
				}
				
			}
			evaluation[i][1] = cumulativeEvaluation;

			x = initialX;
			y = initialY;
			currentTile = initialCurrentTile;
		}
		
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
		
		int gotSupply = 0;
		if(name == "Theseus") {  // If player is Theseus.
			switch(bestDice) {
			
				case 1:
					gotSupply = board.getTile(currentTile + dimension).getSupply() ? 1 : 0;
					break;
				
				case 3:
					gotSupply = board.getTile(currentTile + 1).getSupply() ? 1 : 0;
					break;
				case 5:
					gotSupply = board.getTile(currentTile - dimension).getSupply() ? 1 : 0;
					break;
				case 7:
					gotSupply = board.getTile(currentTile -1).getSupply() ? 1 : 0;
					break;
				
			}
		}

		path.get(1).add(gotSupply); 
		
		if(isTheClosestSupply)
			path.get(2).add(-1);
		
		if(path.get(2).size() != path.get(3).size())
			path.get(3).add(-1);

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
	
	/**
	 * This function prints the data of the player.
	 * @param when
	 */
	void statistics() {
		
		for(int i = 0; i < path.get(1).size(); i++)
		{
			System.out.println("\nRound " + (i+1)+":");
			
			// Dice
			System.out.println(getName() + " had dice " + path.get(0).get(i) + ".");
			
			// Supplies
			if(playerId == 2) // If player is Theseus. 
			{
				System.out.println(getName() + " has collected " + getScore() + " supplies.");

				// Supply Distance
				System.out.print("Before moving ");
				if(path.get(2).get(i) != -1)
					System.out.println(getName() + " had " + path.get(2).get(i) + " tiles distance from the closest supply.\n");
				else
					System.out.println(getName() + " didn't see any supply." + "\n");
				
			}
		}

		// Moves
	
		System.out.println("\nTotal statistics for " + getName() + ":\n");
		System.out.println(getName() + " moved up " + path.get(4).size() + " times.");
		System.out.println(getName() + " moved right " + path.get(5).size() + " times.");
		System.out.println(getName() + " moved down " + path.get(6).size() + " times.");
		System.out.println(getName() + " moved left " + path.get(7).size() + " times.\n");

	}

	/**
	 * Takes as parameter a dice and returns a random new value different from the initial that can be dice. 
	 * For instance if bestDice=3 the function may return 1, 5, 7.  
	 * @param bestDice
	 * @return a dice different from the given 
	 */
	int DiceCalculation(int bestDice)
	{
		Random rand = new Random(System.currentTimeMillis());
		int n = rand.nextInt(3);
		n = (n + 1)*2;

		return ((bestDice + n) % 8);
	}
}
