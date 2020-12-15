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
	// !Used only by Minotaur! 
	// Array that consists of counters for each supply, that show how many times Minotaur has been on a tile that has a specific supply on it. 
	private int[] timesBeenOnTheSupply;			
	// !Used only by Minotaur!
	// Array that implements if Minotaur has crossed a tile that contains supply enough time by now.
	private boolean[] enoughTimesBeenOnTheSupply;	
		
	/**
	 * Initializes a clever/heuristic player. Variables are initialized to -1, arrays and structures are initialized without content.  
	 */
	public MinMaxPlayer() 
	{
		super();
		path = new ArrayList<ArrayList<Integer>>();
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
	public MinMaxPlayer(int playerId, String name, Board board, int score, int x, int y, int LastMove/*, Node root*/) 
	{
		super(playerId, name, board, score, x, y);
		
		path = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 

		timesBeenOnTheSupply = new int[board.getS()];

		for(int i = 0; i < board.getS(); i++)
			timesBeenOnTheSupply[i] = 0;

		enoughTimesBeenOnTheSupply = new boolean[board.getS()];

		for(int i = 0; i < board.getS(); i++)
			enoughTimesBeenOnTheSupply[i] = false;
		
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
		
		this.timesBeenOnTheSupply = player.timesBeenOnTheSupply.clone();
		
		this.enoughTimesBeenOnTheSupply =player.enoughTimesBeenOnTheSupply.clone();

	}
	
	
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
	
	
	/**
	 * This function evaluates the quality of player's single movements on the board in the directions that is given by dice, considering if he sees supplies or his opponent. 
	 * @param dice
	 * @param tileDistSupply
	 * @param tileDistOpponent
	 * @return the evaluation of the player's single movement in the direction the dice implements. 
	 */
	private double evaluate(int dice, Board board)	{
		int dimension = board.getN();
		
		int initialX = x;
		int initialY = y;
		int initialCurrentTile = currentTile;
		
		int tileDistSupply = -1;
		int tileDistOpponent = -1;
		
		boolean isTheClosestSupply = true;
		double cumulativeEvaluation = 0;	
		
		for(int j=1; j<4; j++) {	 		
				
			tileDistSupply = -1;
			tileDistOpponent = -1;
			
			switch(dice) {
			
			case 1:	//up

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
					}
					
					
					if(playerId == 2 && board.getTile(currentTile + dimension).hasMinotaur()) {		
						tileDistOpponent = iterationTimes;	
						path.get(3).add(tileDistOpponent);											
					}
					else if(playerId == 1 && board.getTile(currentTile + dimension).hasTheseus()) {	
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
						x = x + 1;															
						y = y;
						currentTile = y + x * dimension;
					}
				}
				break;
				
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

		x = initialX;								
		y = initialY;
		currentTile = initialCurrentTile;

		return cumulativeEvaluation;	
	}	

	
	
	boolean canMove(int fakeX, int fakeY, int direction, Board cloneboard) {

		int fakeCurrentTile = fakeY + fakeX * cloneboard.getN();

		switch(direction) 
		{
			case 1:	//up
				
				if(cloneboard.getTile(fakeCurrentTile).getUp() == true)
					return false;				
				else 
					return true;
					
				
			case 3:	//right
				
				if(cloneboard.getTile(fakeCurrentTile).getRight() == true)
					return false;				
				else
					return true;
				
			case 5:	//down
				
				if(cloneboard.getTile(fakeCurrentTile).getDown() == true || fakeCurrentTile==0)
					return false;					
				else
					return true;
				
			case 7:	//left
				
				if(cloneboard.getTile(fakeCurrentTile).getLeft() == true)
					return false;				
				else 
					return true;
			default: 
					return true;
		}
	}

	public int[] fakemove(int fakeX, int fakeY, int direction, Board cloneboard)
	{				
		int supplyId = -1;			// When no supply is got, supplyId's value is -1.
		
		int[] array = new int[2];
		int fakeCurrentTile = fakeY + fakeX * cloneboard.getN();

		switch(direction) 
		{
			case 1:	//up
				
				if(cloneboard.getTile(fakeCurrentTile).getUp() == true)
					break;					
				else
				{ 
					// Player left from it's previous tile.
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(false); 
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(false);
						
					fakeX = fakeX + 1; 
					fakeCurrentTile = fakeY + fakeX * cloneboard.getN();

					// Player set on it's new current tile.
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(true);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(true);
					
					break;
				}
				
			case 3:	//right
				
				if(cloneboard.getTile(fakeCurrentTile).getRight() == true)
					break;					
				else
				{ 
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(false);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(false);
					
					fakeY = fakeY + 1;
					fakeCurrentTile = fakeY + fakeX * cloneboard.getN();

					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(true);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(true);
					
					break;
				}
				
			case 5:	//down
				
				if(cloneboard.getTile(fakeCurrentTile).getDown() == true || currentTile==0)
					break;					
				
				else
				{ 
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(false);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(false);
					
					fakeX = fakeX - 1;
					fakeCurrentTile = fakeY + fakeX * cloneboard.getN();
	
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(true);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(true);
					
					break;
				}
				
			case 7:	//left
				
				if(cloneboard.getTile(fakeCurrentTile).getLeft() == true)
					break;					
				else 
				{ 
					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(false);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(false);

					fakeY = fakeY - 1;
					fakeCurrentTile = fakeY + fakeX * cloneboard.getN();

					if(playerId ==2)
						cloneboard.getTile(fakeCurrentTile).setTheseus(true);
					else if(playerId == 1)
						cloneboard.getTile(fakeCurrentTile).setMinotaur(true);
					
					break;
				}
		}
				
		if(playerId == 2)										// Checks if Theseus is the player.
		{
			if(board.getTile(fakeCurrentTile).getSupply() == true)	// Checks if there is a supply in the current tile.
			{   
				supplyId = cloneboard.TileIdToSupplyId(fakeCurrentTile);
								
				// Theseus got the supply, so it should be deleted. Deletion is made by setting supply's
				// coordinates equal to -1 (outside board), and its id equal to -1 as well. 
				// Also, tile's variable supply is set false.
				
				cloneboard.getSupply(supplyId-1).setX(-1);
				cloneboard.getSupply(supplyId-1).setY(-1);
				cloneboard.getSupply(supplyId-1).setSupplyId(-1);
				cloneboard.getSupply(supplyId-1).setSupplyTileId(-1);
				cloneboard.getTile(fakeCurrentTile).setSupply(false);
				
			}
		} 
		
		array[0] = fakeX; 
		array[1] = fakeY; 
		
		return array;
	}


	int chooseMinMaxMove(Node node, int depth, boolean isMaximizing) {
		
		if(depth == 0) {
			return (int)node.getNodeEvaluation(); 
		}
		
		double inf = Double.POSITIVE_INFINITY;
		if(isMaximizing) {
			double bestEval = -(1)*inf;
			for(int i=0; i<node.getChildren().size(); i++) 
			{
				int minmax = chooseMinMaxMove(node.getChildren().get(i), depth-1, false);
				if(minmax > bestEval)
				{
					bestEval = minmax;
					node.getChildren().get(i).setNodeEvaluation(bestEval);			
					node.setNodeMove(node.getChildren().get(i).getNodeMove());
				}
			}			
			return (int)bestEval;
		}
		
		else {
			double bestEval = inf;
			for(int i=0; i<node.getChildren().size(); i++) 
			{
				int minmax = chooseMinMaxMove(node.getChildren().get(i), depth-1, true);
				if(minmax < bestEval)  
				{
					bestEval = minmax;
					node.getChildren().get(i).setNodeEvaluation(bestEval);		
					node.setNodeMove(node.getChildren().get(i).getNodeMove());
				}	
			}
			return (int)bestEval;
		}
	}
		
	void createMySubtree(Node root, int depth) {
	
		for(int i=0; i<4; i++) {

			int direction = 2*i+1;
			Board childrenBoard = new Board(root.getNodeBoard());

			if(canMove(this.x, this.y, direction, childrenBoard)) {
				
				int[] childrenArray = new int[3];
				int[] tempArray = new int[2];

				double evaluation = evaluate(direction, childrenBoard);
					
				tempArray = fakemove(this.x, this.y, 2*i+1, childrenBoard);

				childrenArray[0] = tempArray[0];
				childrenArray[1] = tempArray[1];
				childrenArray[2] = direction;

				root.setChildren(new Node(root, new ArrayList<Node>(), depth+1, childrenArray, childrenBoard, evaluation, root.getNodePlayer()));
				createOpponentSubtree(root.getChildren().get(root.getChildren().size()-1), depth+2, evaluation);
			}
		}
		
	}
	
	void createOpponentSubtree(Node parent, int depth, double parentEval) {

		for(int i=0; i<4; i++) {

			int direction = 2*i+1;
			Board childrenBoard = new Board(parent.getNodeBoard());

			if(canMove(parent.getNodePlayer().getX(), parent.getNodePlayer().getY(), direction, childrenBoard)) {
				
				int[] childrenArray = new int[3];
				int[] tempArray = new int[2];
				double evaluation = parent.getNodePlayer().evaluate(direction, childrenBoard);
				tempArray = parent.getNodePlayer().fakemove(parent.getNodePlayer().getX(), parent.getNodePlayer().getY(), 2*i+1, childrenBoard);
				
				childrenArray[0] = tempArray[0];
				childrenArray[1] = tempArray[1];
				childrenArray[2] = parent.getNodeMove()[2];

				parent.setChildren(new Node(parent, new ArrayList<Node>(), depth+1, childrenArray, childrenBoard, parentEval - evaluation, parent.getNodePlayer()));
			}
		}
	}
	
	
	/**
	 * The function that returns the best dice player can decide after evaluating his moves in each directions. 
	 * @return bestDice
	 */
	public int getNextMove(Node root)
	{	
		int dimension = board.getN();
		
		createMySubtree(root, 0);

		chooseMinMaxMove(root, 2, true);
		
		int bestDice = root.getNodeMove()[2];
				
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
		 if(path.get(2).size() != path.get(0).size())
		 	path.get(2).add(-1);
		
		// // The third and fourth ArrayLists of path must have the same size (same as the round count up)
		// // If they haven't it means no element has been added in the fourth array list so no opponent has been found near to the player.
		// // So, in this case, add -1.
		 if(path.get(3).size() != path.get(0).size())
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
}
