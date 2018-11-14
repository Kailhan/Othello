package AI;
import Core.Board;

import java.util.*;

public class Node<T> implements GenericTree<T> {

    private T data = null;
    private int depth = 0;
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
        child.depth = this.depth++;
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

    public int getX() {
        int x = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = (Board)this.getParent().getData();
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = (Board)this.getData();
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) x = c;
            }
        }
        return x;
    }

    public int getY() {
        int y = -1; //makes sure we throw an error if we have not updated our coordinate
        Board parentBoard = (Board)this.getParent().getData();
        int[][] parentBoardGrid = parentBoard.getBoardGrid();
        Board currentBoard = (Board)this.getData();
        int[][] currentBoardGrid = currentBoard.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == 0 && currentBoardGrid[r][c] != 0) y = r;
            }
        }
        return y;
    }
}