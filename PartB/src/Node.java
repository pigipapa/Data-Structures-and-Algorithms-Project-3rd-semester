import java.util.*;

public class Node
{
    private Node parent;
    private ArrayList<Node> children;
    private int nodeDepth;
    private int[] nodeMove;
    private Board nodeBoard;
    private double nodeEvaluation;
    private MinMaxPlayer nodePlayer;

    Node()
    {
    	nodeDepth = 0;
        nodeMove = new int[0];
        nodeBoard = new Board();
        nodeEvaluation = 0;
        children = new ArrayList<Node>();
        //nodePlayer = new MinMaxPlayer();
    }

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

    Node getParent() {return parent;}

    void setParent(Node parent) {this.parent = parent;}

    ArrayList<Node> getChildren() {return children;}

    void setChildren(ArrayList<Node> children) {this.children = new ArrayList<Node>(children);}
    
    void setChildren(Node node) {children.add(node);}

    int getNodeDepth() {return nodeDepth;}

    void setNodeDepth(int nodeDepth) {this.nodeDepth = nodeDepth;}
    
    int[] getNodeMove() {return nodeMove;}
    
    void setNodeMove(int[] nodeMove) {this.nodeMove = nodeMove;}

    Board getNodeBoard() {return nodeBoard;}

    void setNodeBoard(Board nodeBoard) {this.nodeBoard = nodeBoard;}

    double getNodeEvaluation() {return nodeEvaluation;}

    void setNodeEvaluation(double nodeEvaluation) {this.nodeEvaluation = nodeEvaluation;}
    
    MinMaxPlayer getNodePlayer() {return nodePlayer;}
    
    void setNodePlayer(MinMaxPlayer nodePlayer) {this.nodePlayer = nodePlayer;}
}


