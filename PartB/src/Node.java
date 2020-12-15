import java.util.*;

public class Node
{
    private Node parent;	            // The parent of the node.
    private ArrayList<Node> children;	// Every position of the ArrayList is a child of the node.
    private int nodeDepth;	            // The depth at witch the node is found.
    private int[] nodeMove;	            // An array that contains x [0], y[1] coordinate after the move that led to the node and the dice [2] which led to the node.
    private Board nodeBoard;	        // The node has it's own board, different from the game's one.
    private double nodeEvaluation;  	// The evaluation of the movement that led to the node.
    private MinMaxPlayer nodePlayer;	// A player that's usually the opponent of the player that has it's turn on the game.

    /**
     * Initializes the values of the Node to zero (0) and it calls void constructors of other objects Node class has as variables.
     */
    Node()
    {
    	nodeDepth = 0;
        nodeMove = new int[0];
        nodeBoard = new Board();
        nodeEvaluation = 0;
        children = new ArrayList<Node>();
        nodePlayer = new MinMaxPlayer();
    }

    /**
     * Initializes nodes with the given values. 
     * @param parent
     * @param children
     * @param nodeDepth
     * @param nodeMove
     * @param nodeBoard
     * @param nodeEvaluation
     * @param nodePlayer
     */
    Node(Node parent, ArrayList<Node> children, int nodeDepth, int[] nodeMove, Board nodeBoard, double nodeEvaluation, MinMaxPlayer nodePlayer)
    {
        this.parent = new Node(parent);
        this.children = new ArrayList<Node>(children);
        this.nodeDepth = nodeDepth;
        this.nodeMove = nodeMove;
        this.nodeBoard = new Board(nodeBoard); 
        this.nodeEvaluation = nodeEvaluation; 
        this.nodePlayer = nodePlayer;
    }

    /**
     * Initialized a node with the values of another node that is given.
     * @param node
     */
    Node(Node node)
    {
        this.parent = node.parent;
        this.children = new ArrayList<Node>(node.getChildren());
        this.nodeDepth = node.getNodeDepth();
        this.nodeMove = node.getNodeMove().clone();
        this.nodeBoard = new Board(node.getNodeBoard()); 
        this.nodeEvaluation = node.getNodeEvaluation(); 
        this.nodePlayer = node.nodePlayer;
    }

    /**
     * Returns the parent node of the node.
     * @return parent
     */
    Node getParent() {return parent;}

    /**
     * Sets the parent of the node.
     * @param parent
     */
    void setParent(Node parent) {this.parent = parent;}
    
	/**
	 * Returns the ArrayList that contains the children of the node.
	 * @return children
	 */
    ArrayList<Node> getChildren() {return children;}

    /**
     * Sets the whole ArrayList of children the node has.
     * @param children
     */
    void setChildren(ArrayList<Node> children) {this.children = new ArrayList<Node>(children);}
    
    /**
     * Adds the node that is given as parameter to the ArrayList of node's children. In a nut cell it adds a child.
     * @param node
     */
    void setChildren(Node node) {children.add(node);}

    /**
     * Return the depth of the node.
     * @return nodeDepth
     */
    int getNodeDepth() {return nodeDepth;}
    
    /**
     * Sest the depth of the node. 
     * @param nodeDepth
     */
    void setNodeDepth(int nodeDepth) {this.nodeDepth = nodeDepth;}
    
    /**
     * Returns nodeMove array.
     * @return nodeMove
     */
    int[] getNodeMove() {return nodeMove;}
    
    /**
     * Sets nodeMove array of the node.
     * @param nodeMove
     */
    void setNodeMove(int[] nodeMove) {this.nodeMove = nodeMove;}

    /**
     * Returns node's board.
     * @return nodeBoard
     */
    Board getNodeBoard() {return nodeBoard;}

    /**
     * Sets node's board.
     * @param nodeBoard
     */
    void setNodeBoard(Board nodeBoard) {this.nodeBoard = nodeBoard;}

    /**
     * Returns node's evaluation.
     * @return nodeEvaluation
     */
    double getNodeEvaluation() {return nodeEvaluation;}

    /**
     * Sets node's evaluation.
     * @param nodeEvaluation
     */
    void setNodeEvaluation(double nodeEvaluation) {this.nodeEvaluation = nodeEvaluation;}
    
    /**
     * Returns node's player.
     * @return nodePlayer
     */
    MinMaxPlayer getNodePlayer() {return nodePlayer;}
    
    /**
     * Sets node's player.
     * @param nodePlayer
     */
    void setNodePlayer(MinMaxPlayer nodePlayer) {this.nodePlayer = nodePlayer;}
}


