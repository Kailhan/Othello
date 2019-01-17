package AI;
import Core.Board;

import java.util.*;

public class Node<T> implements GenericTree<T> {

    private T data = null;
    private int depth = 0;
    private double value;
    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent = null;
    private boolean visited = false;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> getRoot() {
        Node<T> currentNode;
        currentNode = this;
        while(currentNode.parent != null) {
            currentNode = this.parent;
        }
        return currentNode;
    }

    public Node<T> addChild(Node<T> child) {
        this.children.add(child);
        child.setParent(this); //set parent when adding a child
        child.depth = depth + 1;
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        this.children.addAll(children);
        for(Node<T> child : children)
        {
            child.setParent(this);
        }
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setVisited(){
        visited = true;
    }

    public boolean getVisited(){
        return visited;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public int getRow() {
        int row = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = (Board)getParent().getData();
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = (Board)getData();
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
//        System.out.println("getX parentBoard");
//        parentBoard.displayBoardGrid();
//        System.out.println("getX currentBoard");
//        currentBoard.displayBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) row = r;
            }
        }
        return row;
    }

    public int getColumn() {
        int column = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = (Board)getParent().getData();
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = (Board)getData();
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
        //System.out.println("getY parentBoard");
        //parentBoard.displayBoardGrid();
        //System.out.println("getY currentBoard");
        //currentBoard.displayBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) column = c;
            }
        }
        return column;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}