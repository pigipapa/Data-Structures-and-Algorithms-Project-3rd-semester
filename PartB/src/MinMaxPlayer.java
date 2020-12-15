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

	/**
	 * Initializes a clever/heuristic player. Variables are initialized to -1, arrays and structures are initialized without content.  
	 */
	public MinMaxPlayer() 
	{
		super();
		path = new ArrayList<ArrayList<Integer>>();
	}
	
	/**
	 * Initializes a clever/heuristic player.  
	 * @param playerId
	 * @param name
	 * @param board
	 * @param score
	 * @param x
	 * @param y
	 */
	public MinMaxPlayer(int playerId, String name, Board board, int score, int x, int y) 
	{
		super(playerId, name, board, score, x, y);
		
		path = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++)
			path.add(new ArrayList<Integer>()); 
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
		
		// Save the initial values of the player variables that show his position on the board.
		int initialX = x;
		int initialY = y;
		int initialCurrentTile = currentTile;
		
		// Initialized to -1.
		int tileDistSupply = -1;
		int tileDistOpponent = -1;
		
		double cumulativeEvaluation = 0;	
		
		for(int j=1; j<4; j++) {	// Loop for every movement	
				
			tileDistSupply = -1;
			tileDistOpponent = -1;
			
			switch(dice) {
			
			case 1:	//up

				if(board.getTile(currentTile).getUp())				// Checks if there is a wall ahead.			
					break;
				else {
					int iterationTimes = (x + 1) - initialX;		// Computing the distance between players initial position and his posotion after his testing move.		 
					
					if(board.getTile(currentTile + dimension).getSupply())	
						tileDistSupply = iterationTimes;	
											
					if(playerId == 2 && board.getTile(currentTile + dimension).hasMinotaur()) 		//Checks if Minotaur is on Theseus' upper tile.
						tileDistOpponent = iterationTimes;	

					else if(playerId == 1 && board.getTile(currentTile + dimension).hasTheseus()) 	//Checks if Theseus is on Minotaur's upper tile.
						tileDistOpponent = iterationTimes;
					
					// This variable's values can be 0.3, 0.5, 1 depending on the distance between the player and a supply, which he can see.
					// If two tiles keep them apart then NearSupplies = 0.3, else if tile distance is 1 then NearSupplies = 0.5, else if they are neighboring NearSupplies = 1. 
					double NearSupplies = 0;
					// This variable's values can be 0.3, 0.5, 1 if player is Minotaur and is close to Theseus at distance 2,1 and 0 tiles.
					// If player is Theseus, OppponentDist values can be -0.3 when his distance from Minotaur is 2 tiles, -0.5 if it's 1 tile and -1 if Minotaur is next to him.  
					double OpponentDist = 0;
					// A sign roulette. If player is Minotaur then sign = -1. If palyer is Theseus sign = 1. 
					int sign = (playerId == 2) ? -1 : 1;
					
					
					// If player sees supply
					// tileDistSupply can be 1, 2 and 3 depending on the distance between the player and the supply.
					//1-> supply on the next tile in the direction player is heading 
					//2-> one tile between the player and the supply
					//3-> two tiles between the player and the supply
					// The function we use is 1/x because 1/1=1, 1/2=0.5 and 1/3=0.33, which are the values NearSupplies is expected to have.
					if(tileDistSupply != -1)	
						NearSupplies = 1.0/tileDistSupply;	
				
					
					// If player sees his opponent
					// The same comments as above, but this time player is looking for his opponent on the near tiles.
					// If player is Theseus sign=-1 so OpponentDist<0.
					// If palyer is Minotaur sign=1 so OpponentDist>0.
					if(tileDistOpponent != -1)	
						OpponentDist = sign*(1.0/tileDistOpponent);								
					
					
					if(playerId == 2)
						// Theseus' evaluation function 
						cumulativeEvaluation += (NearSupplies * 0.4 + OpponentDist * 0.6)*10;		
					else 
						// Minotaur's evaluation function
						cumulativeEvaluation += (NearSupplies * 0.2 + OpponentDist * 0.8)*10;
												

					if(j != 3)
					{
						x = x + 1;															
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
						tileDistSupply = iterationTimes;

					if(playerId == 2 && board.getTile(currentTile + 1).hasMinotaur()) 
						tileDistOpponent = iterationTimes;	

					else if(playerId == 1 && board.getTile(currentTile + 1).hasTheseus()) 
						tileDistOpponent = iterationTimes;

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
						tileDistSupply = iterationTimes;	
						

					if(playerId == 2 && board.getTile(currentTile - dimension).hasMinotaur()) 
						tileDistOpponent = iterationTimes;	
					else if(playerId == 1 && board.getTile(currentTile - dimension).hasTheseus()) 
						tileDistOpponent = iterationTimes;

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
						tileDistSupply = iterationTimes;	
						
					if(playerId == 2 && board.getTile(currentTile - 1).hasMinotaur()) 
						tileDistOpponent = iterationTimes;	
					else if(playerId == 1 && board.getTile(currentTile - 1).hasTheseus()) 
						tileDistOpponent = iterationTimes;
					
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

	/**
	 * This method checks if there is a wall at the given direction. If there is an obstacle the player can't move otherwise he can.
	 * @param fakeX
	 * @param fakeY
	 * @param direction
	 * @param cloneboard
	 * @return true if player can move or false if he cant't.
	 */
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

	/**
	 * This function simulates a false movement.
	 * @param fakeX
	 * @param fakeY
	 * @param direction
	 * @param cloneboard
	 * @return an integer array ([0]->fakeX, [1]->fakeY).
	 */
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

	/**
	 * This function creates the tree of the player that get's it's turn.
	 * @param root
	 * @param depth
	 */
	void createMySubtree(Node root, int depth) {
	
		for(int i=0; i<4; i++) {

			int direction = 2*i+1;											// Dice.
			Board childrenBoard = new Board(root.getNodeBoard());			// False board.

			if(canMove(this.x, this.y, direction, childrenBoard)) {			//Checks if he can move in this direction.
				
				int[] tempArray = new int[2];
				
				double evaluation = evaluate(direction, childrenBoard);		// Evaluation of the false movement.
					
				tempArray = fakemove(this.x, this.y, 2*i+1, childrenBoard);	// It contains false x and y.

				// Assignments
				int[] childrenMove = new int[3];  // [0]->false_x, [1]->false_y, [2]->dice 
				childrenMove[0] = tempArray[0];
				childrenMove[1] = tempArray[1];
				childrenMove[2] = direction;

				// Sets the new data of root's children.
				root.setChildren(new Node(root, new ArrayList<Node>(), depth+1, childrenMove, childrenBoard, evaluation, root.getNodePlayer()));
				// Call the function that creates opponent subtree. 
				createOpponentSubtree(root.getChildren().get(root.getChildren().size()-1), depth+2, evaluation);
			}
		}
	}
	
	/**
	 * Function that creates opponents subtree. !Called by createMySubtree(...) function!
	 * @param parent
	 * @param depth
	 * @param parentEval
	 */
	void createOpponentSubtree(Node parent, int depth, double parentEval) {

		for(int i=0; i<4; i++) {

			int direction = 2*i+1;									// Dice.
			Board childrenBoard = new Board(parent.getNodeBoard());	// False board.

			if(canMove(parent.getNodePlayer().getX(), parent.getNodePlayer().getY(), direction, childrenBoard)) {	// Checks if he can move in this direction.
				
				double evaluation = parent.getNodePlayer().evaluate(direction, childrenBoard);	// Evaluation of the false movement.

				// It contains false x and y.
				int[] tempArray = new int[2];
				tempArray = parent.getNodePlayer().fakemove(parent.getNodePlayer().getX(), parent.getNodePlayer().getY(), 2*i+1, childrenBoard);	
				
				int[] childrenMove = new int[3];	 
				childrenMove[0] = tempArray[0]; 		    // [0]->false_x,
				childrenMove[1] = tempArray[1]; 			// [1]->false_y
				childrenMove[2] = parent.getNodeMove()[2];  // [2]->dice 
				
				// Sets the new data of parent's children.
				parent.setChildren(new Node(parent, new ArrayList<Node>(), depth+1, childrenMove, childrenBoard, parentEval - evaluation, parent.getNodePlayer()));
			}
		}
	}
	
	/**
	 * This function implements the Min-Max algorithm.
	 * @param node
	 * @param depth
	 * @param isMaximizing
	 * @return evaluation of palyer's movement.
	 */
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
				double worstEval = inf;
				for(int i=0; i<node.getChildren().size(); i++) 
				{
					int minmax = chooseMinMaxMove(node.getChildren().get(i), depth-1, true);
					if(minmax < worstEval)  
					{
						worstEval = minmax;
						node.getChildren().get(i).setNodeEvaluation(worstEval);		
						node.setNodeMove(node.getChildren().get(i).getNodeMove());
					}	
				}
				return (int)worstEval;
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
		
		// Distance from nearest supply	and opponent

		int minDistance = 10;
		for(int i=0; i<4; i++) {	// Loop for every movement	
		
			int direction = 2*i + 1; 
			int tileDistSupply = -1;
			int tileDistOpponent = -1;

			switch(direction) {
			
			case 1:	//up
				for(int j = 0; j < 3; j++)
				{
					if(currentTile + j*dimension < dimension * dimension)
						break;
					else if(board.getTile(currentTile + dimension*j).getUp())				// Checks if there is a wall ahead.			
						break;
					else {
						if(board.getTile(currentTile + dimension*j).getSupply())	
						{	
							tileDistSupply = j+1;	

							if(minDistance > tileDistSupply){
								minDistance = tileDistSupply;	
								path.get(2).add(tileDistSupply);		// Store to path the distance between the closest supply and Theseus, 								
							}
						}
						
						if(playerId == 2 && board.getTile(currentTile + dimension*j).hasMinotaur()) {		//Checks if Minotaur is on Theseus' upper tile.
							tileDistOpponent = j+1;	
							path.get(3).add(tileDistOpponent);											// Adds in Theseus' path his distance from Minotaur.
						}
						else if(playerId == 1 && board.getTile(currentTile + dimension*j).hasTheseus()) {	//Checks if Theseus is on Minotaur's upper tile.
							tileDistOpponent = j+1;
							path.get(3).add(tileDistOpponent);											// Adds in Minotaur's path his distance from Theseus.
						}
					}
				}
				break;
				
			case 3:	//right
				for(int j = 0; j < 3; j++)
				{
					if(currentTile + j*dimension > 0)
						break;
					else if(board.getTile(currentTile + j).getRight()) 
						break;
					else {

						if(board.getTile(currentTile + j).getSupply()) 
						{
							tileDistSupply = j+1;	

							if(minDistance > tileDistSupply){
								minDistance = tileDistSupply;	
								path.get(2).add(tileDistSupply);		// Store to path the distance between the closest supply and Theseus, 								
							}
						}
						if(playerId == 2 && board.getTile(currentTile + j).hasMinotaur()) {
							tileDistOpponent = j+1;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile + j).hasTheseus()) {
							tileDistOpponent = j+1;
							path.get(3).add(tileDistOpponent);
						}
					}
				}
				break;
			 
			case 5:	//down
				for(int j = 0; j < 3; j++)
				{
					if(currentTile == 0 || (currentTile - j*dimension < 0))
						break;
					else if(board.getTile(currentTile - j*dimension).getDown()) 
						break;
					else {

						if(board.getTile(currentTile - j*dimension).getSupply()) 
						{
							tileDistSupply = j+1;	

							if(minDistance > tileDistSupply){
								minDistance = tileDistSupply;	
								path.get(2).add(tileDistSupply);		// Store to path the distance between the closest supply and Theseus, 								
							}
						}

						if(playerId == 2 && board.getTile(currentTile - j*dimension).hasMinotaur()) {
							tileDistOpponent = j;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile - j*dimension).hasTheseus()) {
							tileDistOpponent = j;
							path.get(3).add(tileDistOpponent);
						}

					}
				}
				break;
			 
			case 7:	//left
				for(int j = 0; j < 3; j++)
				{
					if(currentTile - j*dimension > 0)
						break;
					if(board.getTile(currentTile - j).getLeft()) 
						break;
					else {
						if(board.getTile(currentTile - j).getSupply()) 
						{
							tileDistSupply = j+1;	

							if(minDistance > tileDistSupply){
								minDistance = tileDistSupply;	
								path.get(2).add(tileDistSupply);		// Store to path the distance between the closest supply and Theseus, 								
							}
						}
						if(playerId == 2 && board.getTile(currentTile - j).hasMinotaur()) { 
							tileDistOpponent = j;	
							path.get(3).add(tileDistOpponent);
						}
						else if(playerId == 1 && board.getTile(currentTile - j).hasTheseus()) {
							tileDistOpponent = j;
							path.get(3).add(tileDistOpponent);
						}
					}
				}
				break;
			}
		}

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
				System.out.println(getName() + " has collected " + getScore() + " supplies totally.");

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
