package AI;
import java.util.*;

public class Node<T> implements GenericTree<T> {

    private T data = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    private boolean visited = false;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> getRoot() {
        Node<T> currentNode;
        currentNode = this;
        while(currentNode.parent != null)
        {
            currentNode = this.parent;
        }
        return currentNode;
    }

    public Node<T> addChild(Node<T> child) {
        this.children.add(child);
        child.setParent(this); //set parent when adding a child
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




}
