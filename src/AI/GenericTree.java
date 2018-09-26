package AI;
import java.util.*;


public interface GenericTree<T> {

    Node<T> getRoot();
    Node<T> addChild(Node<T> child);
    void addChildren(List<Node<T>> children);
    List<Node<T>> getChildren();
    T getData();
    void setData(T data);
    void setParent(Node<T> parent);
    Node<T> getParent();
    void setVisited();
    boolean getVisited();

}




