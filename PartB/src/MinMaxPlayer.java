import java.util.*;
/**
 * Class that implements a heuristic player.
 */

public class MinMaxPlayer extends Player{
	
	// Structure (ArrayList that consists of ArrayLists of Integers) that gives information about players each movement in the current game.
	// path.get(0)->chosen dice
	// path.get(1)->shows if tile after the movement has or not supply
	// path.get(2)->distance from nearest supply
	// path.get(3)->distance from opponent
	// path.get(4)->times player moved up
	// path.get(5)->times player moved right
	// path.get(6)->times player moved down
	// path.get(7)->times player moved left
	private ArrayList <ArrayList<Integer>> path;
	// Variable that keeps the last dice of the player.
	private int LastMove;							
	// !Used only by Minotaur! 
	// Array that consists of counters for each supply, that show how many times Minotaur has been on a tile that has a specific supply on it. 
	private int[] timesBeenOnTheSupply;			
	// !Used only by Minotaur!
	// Array that implements if Minotaur has crossed a tile that contains supply enough time by now.
	private boolean[] enoughTimesBeenOnTheSupply;	
	
	private Node root;
	
	// Variable that implements if a close supply is the closest one to the player, in case there are more than 1 supplies he can see.
	boolean isTheClosestSupply = true;
		
	/**
	 * Initializes a clever/heuristic player. Variables are initialized to -1, arrays and structures are initialized without content.  
	 */
	public MinMaxPlayer() 
	{
		super();
		root = new Node();
		path = new ArrayList<ArrayList<Integer>>();
		LastMove = -1;
		timesBeenOnTheSupply = new int[board.getS()];
		enoughTimesBeenOnTheSupply = new boolean[board.getS()];
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
	public MinMaxPlayer(int playerId, String name, Board board, int score, int x, int y, int LastMove, Node root) 
	{
		super(playerId, name, board, score, x, y);
		
		this.LastMove = LastMove; //Initialized <1. Better as -1.
		
		path = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 

		timesBeenOnTheSupply = new int[board.getS()];

		for(int i = 0; i < board.getS(); i++)
			timesBeenOnTheSupply[i] = 0;

		enoughTimesBeenOnTheSupply = new boolean[board.getS()];

		for(int i = 0; i < board.getS(); i++)
			enoughTimesBeenOnTheSupply[i] = false;
		
		this.root = new Node(root);
	}
	
	/**
	 * Initializes HeuristicPlayer with the values of another HeuristicPlayer.
	 * @param player
	 */
	public MinMaxPlayer(MinMaxPlayer player) 
	{
		super(player);
		
		for(int i = 0; i < player.path.size(); i++) {

			this.path.set(i, new ArrayList<Integer>(player.path.get(i)));

			this.path.add(i, player.path.get(i));
		}
		
		this.LastMove = player.LastMove;
		
		this.timesBeenOnTheSupply = player.timesBeenOnTheSupply.clone();
		
		this.enoughTimesBeenOnTheSupply =player.enoughTimesBeenOnTheSupply.clone();

		this.root = new Node(player.root);
	}
	
	/**
	 * Returns last dice.
	 * @return LastMove
	 */
	public int getLastMove() { return LastMove; }
	
	/**
	 * Sets last dice.
	 * @param LastMove
	 */
	public void setLastMove(int LastMove) { this.LastMove = LastMove; }
	
	/**
	 * Sets path.
	 * @param path
	 */
	public void setPath(ArrayList<ArrayList<Integer>> path){ this.path = path; }
	
	/**
	 *Returns path. 
	 * @return path
	 */

	public ArrayList<ArrayList<Integer>> getPath(){ return this.path; } 
	
	public Node getRoot() {return root;}
	
	public void setRoot(Node root) {this.root = root;}
	
	
	
	/**
	 * This function evaluates the quality of player's single movements on the board in the directions that is given by dice, considering if he sees supplies or his opponent. 
	 * @param dice
	 * @param tileDistSupply
	 * @param tileDistOpponent
	 * @return the evaluation of the player's single movement in the direction the dice implements. 
	 */
	private double evaluate(int dice, Board board)	{
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
		double evaluation; 
		
		// Checks how many times Minotaur has been on the same supply (Minotaur is attracted by supplies because he may find Theseus near to them)
		if((playerId == 1) && board.getTile(currentTile).getSupply())
		{
			if(!enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1]) 
				timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] += 1;

			// Minotaur can be in the same tile as a supply only two times
			if(timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] == 2) 
			{
				// The supply which is been watched out by Minotaur is been deactivated
				timesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] = -1;
				enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile)-1] = true;	
				deactivatedSupplyId = board.TileIdToSupplyId(currentTile)-1;
			}	
		}

		// Road to supply reactivation for Minotaur

		for(int i = 0; i < board.getS(); i++)
		{
			// Just deactivated supply is not being considered into reactivation process.
			if(i == deactivatedSupplyId)
				continue;

			// The supply is reactivated after three rounds.
			if((timesBeenOnTheSupply[i] <= -1) && (timesBeenOnTheSupply[i] > -4))
				timesBeenOnTheSupply[i] -= 1;

			// Supply reactivation.
			if(timesBeenOnTheSupply[i] == -4 && board.getSupply(i).getSupplyTileId() != -1) 
			{
				timesBeenOnTheSupply[i] = 0;
				enoughTimesBeenOnTheSupply[i] = false;
			}
		}
		
		
		
		// Player is moving tile to tile in each direction(1->up, 3->right, 5->down, 7->left) for two times,
		// in order to test what is on the next tile (supply , opponent).
		// Thus, he sees in a distance of three tiles from his current tile and therefore he evaluates the move in every direction.
		

		double cumulativeEvaluation = 0;	// Sum of evaluations in one direction.
		
		for(int j=0; j<4; j++) {	 		// Loop for every movement
				
			tileDistSupply = -1;
			tileDistOpponent = -1;
			
			switch(dice) {
			
			case 1:	//up

				if(board.getTile(currentTile).getUp())						// Checks if there is a wall ahead.
					break;
				else {
					int iterationTimes = (x + 1) - initialX;				// Computing the distance between players initial position and his posotion after his testing move 
					
					if(board.getTile(currentTile + dimension).getSupply())	// Checks if the upper tile has supply.
					{	
						tileDistSupply = iterationTimes;	
						
						if(isTheClosestSupply) {	
							path.get(2).add(tileDistSupply);										// Store to path the distance between the closest supply and Theseus,  
							isTheClosestSupply = false;												// in case there are more than one supplies near to him.
						}
					
						if((playerId == 1)  && enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile + dimension) - 1])	// Checks if Minotaur is the player. 
							tileDistSupply = -1;													// If his has been enough times on the supply in front of him it's been deactivated.
					}
					
					
					if(playerId == 2 && board.getTile(currentTile + dimension).hasMinotaur()) {		//Checks if Minotaur is on Theseus' upper tile.
						tileDistOpponent = iterationTimes;	
						path.get(3).add(tileDistOpponent);											// Adds in Theseus' path his distance from Minotaur.
					}
					else if(playerId == 1 && board.getTile(currentTile + dimension).hasTheseus()) {	//Checks if Theseus is on Minotaur's upper tile.
						tileDistOpponent = iterationTimes;
						path.get(3).add(tileDistOpponent);											// Adds in Minotaur's path his distance from Theseus.
					}

					double NearSupplies = 0;
					double OpponentDist = 0;
					int sign = (playerId == 2) ? -1 : 1;
					
					if(tileDistSupply != -1)	
						NearSupplies = 1.0/tileDistSupply;	
				
					if(tileDistOpponent != -1)	
						OpponentDist = sign*(1.0/tileDistOpponent);								
					
					if(playerId == 2)
						cumulativeEvaluation += (NearSupplies * 0.4 + OpponentDist * 0.6)*10;		
					else 
						cumulativeEvaluation += (NearSupplies * 0.2 + OpponentDist * 0.8)*10;
												

					if(j != 3)
					{
						x = x + 1;																	// Player moves up.
						y = y;
						currentTile = y + x * dimension;
					}
				}
				break;
			
				// The same logic as described above is used in the rest cases.
				
			case 3:	//right
				
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

						if((playerId == 1)  && enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile+1)-1])
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

					double NearSupplies = 0;
					double OpponentDist = 0;
					int sign = (playerId == 2) ? -1 : 1;
					
					if(tileDistSupply != -1)	
						NearSupplies = 1.0/tileDistSupply;	
				
					if(tileDistOpponent != -1)	
						OpponentDist = sign*(1.0/tileDistOpponent);								
					
					if(playerId == 2)
						cumulativeEvaluation += (NearSupplies * 0.4 + OpponentDist * 0.6)*10;		
					else 
						cumulativeEvaluation += (NearSupplies * 0.2 + OpponentDist * 0.8)*10;
					if(j != 3)
					{
						x = x;
						y = y + 1;
						currentTile = y + x * dimension;
					}						
				}
				break;
			 
			case 5:	//down
				
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

						if((playerId == 1)  && enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile - dimension)-1])
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

					double NearSupplies = 0;
					double OpponentDist = 0;
					int sign = (playerId == 2) ? -1 : 1;
					
					if(tileDistSupply != -1)	
						NearSupplies = 1.0/tileDistSupply;	
				
					if(tileDistOpponent != -1)	
						OpponentDist = sign*(1.0/tileDistOpponent);								
					
					if(playerId == 2)
						cumulativeEvaluation += (NearSupplies * 0.4 + OpponentDist * 0.6)*10;		
					else 
						cumulativeEvaluation += (NearSupplies * 0.2 + OpponentDist * 0.8)*10;				
					if(j != 3)
					{
						x = x - 1;
						y = y;
						currentTile = y + x * dimension;
					}						
				}
				break;
			 
			case 7:	//left
				
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

						if((playerId == 1)  && enoughTimesBeenOnTheSupply[board.TileIdToSupplyId(currentTile-1) - 1])
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
					
					double NearSupplies = 0;
					double OpponentDist = 0;
					int sign = (playerId == 2) ? -1 : 1;
					
					if(tileDistSupply != -1)	
						NearSupplies = 1.0/tileDistSupply;	
				
					if(tileDistOpponent != -1)	
						OpponentDist = sign*(1.0/tileDistOpponent);								
					
					if(playerId == 2)
						cumulativeEvaluation += (NearSupplies * 0.4 + OpponentDist * 0.6)*10;		
					else 
						cumulativeEvaluation += (NearSupplies * 0.2 + OpponentDist * 0.8)*10;					
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
		evaluation = cumulativeEvaluation;	// Total evaluation of the movement is stored in the second column of evaluation array.

		x = initialX;								// Player returns in his initial position.
		y = initialY;
		currentTile = initialCurrentTile;

		return evaluation;
	}		
	
	
	
	
		int chooseMinMaxMove(Node node, int depth, boolean isMaximizing) {
		
		if(depth == 0) {
			return root.getNodeMove()[2]; //dice
		}
		
		double inf = Double.POSITIVE_INFINITY;
		if(isMaximizing) {
			double bestEval = -(1)*inf;
			for(int i=0; i<node.getChildren().size(); i++) 
				bestEval = Math.max(bestEval,chooseMinMaxMove(node.getChildren().get(i), depth-1, false));
			
			return (int)bestEval;
		}
		
		else {
			double bestEval = inf;
			for(int i=0; i<node.getChildren().size(); i++) 
				bestEval = Math.min(bestEval,chooseMinMaxMove(node.getChildren().get(i), depth-1, true));
			
			return (int)bestEval;
		}
	
		
	}
	
	
	
	
	
	boolean canMove(int direction) {
		
		switch(2*direction+1) 
		{
			case 1:	//up
				
				if(board.getTile(currentTile).getUp() == true)
					return false;				
				else 
					return true;
					
				
			case 3:	//right
				
				if(board.getTile(currentTile).getRight() == true)
					return false;				
				else
					return true;
				
			case 5:	//down
				
				if(board.getTile(currentTile).getDown() == true && currentTile==0)
					return false;					
				else
					return true;
				
			case 7:	//left
				
				if(board.getTile(currentTile).getLeft() == true)
					return false;				
				else 
					return true;
			default: 
					return false;
		}
	}
	
	
	
	
	
	
	void createMySubtree(int depth) {
		
		for(int i=0; i<4; i++) {
			if(canMove(i)) {
				
				Board childrenBoard = new Board(root.getNodeBoard());
				int[] childrenArray = new int[3];
				int tempX = this.x;
				int tempY = this.y;
				int tempCurrentTile = this.currentTile;
				childrenArray = move(2*i+1, childrenBoard);
				this.x = tempX;
				this.y = tempY;
				this.currentTile =  tempCurrentTile ;
				int[] newChildren = {childrenArray[1], childrenArray[2], 2*i+1};
				
				double evaluation = evaluate(2*i+1, childrenBoard);
				root.setChildren(new Node(root, new ArrayList<Node>(), depth+1, newChildren, childrenBoard, evaluation, root.getNodePlayer()));
				createOpponentSubtree(root.getChildren().get(root.getChildren().size()-1), depth+2, evaluation);
			}
		}
		
	}
	
	void createOpponentSubtree(Node parent, int depth, double parentEval) {
		
		for(int i=0; i<4; i++) {
			if(canMove(i)) {
				
				Board childrenBoard = new Board(parent.getNodeBoard());
				int[] childrenArray = new int[3];
				
				int tempX = parent.getNodePlayer().getX();
				int tempY = parent.getNodePlayer().getY();
				int tempCurrentTile = parent.getNodePlayer().getCurrentTile();
				childrenArray = parent.getNodePlayer().move(2*i+1, childrenBoard);
				parent.getNodePlayer().setX(tempX);
				parent.getNodePlayer().setY(tempY);
				parent.getNodePlayer().setCurrentTile(tempCurrentTile);
				int[] newChildren = {childrenArray[1], childrenArray[2], 2*i+1};
				
				double evaluation = parent.getNodePlayer().evaluate(2*i+1, childrenBoard);
				parent.setChildren(new Node(parent, new ArrayList<Node>(), depth+1, newChildren, childrenBoard, evaluation - parentEval, parent.getNodePlayer()));
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * The function that returns the best dice player can decide after evaluating his moves in each directions. 
	 * @return bestDice
	 */
	public int getNextMove()
	{	
		int dimension = board.getN();
		createMySubtree(2);
		int bestDice = chooseMinMaxMove(root, 2, true);
		
				
		// path (info about the dice (0), got supply or not (1), is near to a supply (2), is near to enemy (3), times moved up (4), times moved right (5), times moved down (6), times moved left (7))
		
		path.get(0).add(bestDice);	// Add bestDice in path's first ArrayList
		
		int gotSupply = 0;
		
		if(name == "Theseus") {  // If player is Theseus.
			// If he gets supply in his next move then add 1 in path's second ArrayList else add 0 (added through variable gotSupply)
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
		
		// If he has found a supply then isTheClosestSupply becomes false at the first code lines of the method.
		// If he hasn't found a supply isTheClosestSupply is true so -1 is added in third ArrayList of path.
		if(isTheClosestSupply)
			path.get(2).add(-1);
		
		// The third and fourth ArrayLists of path must have the same size (same as the round count up)
		// If they haven't it means no element has been added in the fourth array list so no opponent has been found near to the player.
		// So, in this case, add -1.
		if(path.get(2).size() != path.get(3).size())
			path.get(3).add(-1);

		// Adds dice in the suitable ArrayList for each move to add them up in statistics() in order to overlook how many times the player moved in each direction. 
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
	public void statistics() {
		
		for(int i = 0; i < path.get(1).size(); i++)
		{
			System.out.println("\nRound " + (i+1)+":");
			
			// Dice
			System.out.println(getName() + " had dice " + path.get(0).get(i) + ".");
			
			// Supplies
			if(playerId == 2) // If player is Theseus. 
			{
				System.out.println(getName() + " had collected " + getScore() + " supplies.");

				// Supply Distance
				System.out.print("Before moving ");
				if(path.get(2).get(i) != -1)
					System.out.println(getName() + " had " + path.get(2).get(i) + " tiles distance from the closest supply.");
				else
					System.out.println(getName() + " didn't see any supply.");
				
			}
			
			System.out.print("Before moving ");
			if(path.get(3).get(i) != -1)
				System.out.println(getName() + " had " + path.get(3).get(i) + " tiles distance from his opponent.\n");
			else
				System.out.println(getName() + " couldn't detect his opponent." + "\n");
				
		}

		// Moves
	
		System.out.println("\nTotal statistics for " + getName() + ":\n");
		System.out.println(getName() + " moved up " + path.get(4).size() + " times.");
		System.out.println(getName() + " moved right " + path.get(5).size() + " times.");
		System.out.println(getName() + " moved down " + path.get(6).size() + " times.");
		System.out.println(getName() + " moved left " + path.get(7).size() + " times.\n");

	}

	/**
	 * Returns a random dice excluding the one that got as input. 
	 * For instance if bestDice=3 the function may return 1, 5, 7.  
	 * @param bestDice
	 * @return a dice different from the given 
	 */
	private int DiceCalculation(int bestDice)
	{
		Random rand = new Random(System.currentTimeMillis());
		int n = rand.nextInt(3);
		n = (n + 1)*2;

		return ((bestDice + n) % 8);
	}
}
