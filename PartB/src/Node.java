import java.util.*;

public class Node
{
    private Node parent;
    private ArrayList<Node> children;
    private int[] nodeDepth;
    private Board nodeBoard;
    private double nodeEvaluation;

    Node()
    {
        nodeDepth = new int[0];
        nodeBoard = new Board();
        nodeEvaluation = 0;
        children = new ArrayList<Node>();
    }

    Node(Node parent, ArrayList<Node> children, int[] nodeDepth, Board nodeBoard, double nodeEvaluation)
    {
        this.parent = new Node(parent);
        this.children = new ArrayList<Node>(children);
        this.nodeDepth = nodeDepth;
        this.nodeBoard = new Board(nodeBoard); 
        this.nodeEvaluation = nodeEvaluation; 
    }

    Node(Node node)
    {
        this.parent = new Node(node.getParent());
        this.children = new ArrayList<Node>(node.getChildren());
        this.nodeDepth = node.getNodeDepth();
        this.nodeBoard = new Board(node.getNodeBoard()); 
        this.nodeEvaluation = node.getNodeEvaluation(); 
    }

    Node getParent() {return parent;}

    void setParent(Node parent) {this.parent = parent;}

    ArrayList<Node> getChildren() {return children;}

    void setChildren(ArrayList<Node> children) {this.children = new ArrayList<Node>(children);}

    int[] getNodeDepth() {return nodeDepth;}

    void setNodeDepth(int[] nodeDepth) {this.nodeDepth = nodeDepth;}

    Board getNodeBoard() {return nodeBoard;}

    void setNodeBoard(Board nodeBoard) {this.nodeBoard = nodeBoard;}

    double getNodeEvaluation() {return nodeEvaluation;}

    void setNodeEvaluation(double nodeEvaluation) {this.nodeEvaluation = nodeEvaluation;}
}