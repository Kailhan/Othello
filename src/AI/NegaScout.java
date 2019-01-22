package AI;

import Core.*;

import java.util.List;
/**
 * This class contains the NegaScout algorithm along with the best move for it
 * @author Maaike & Yvar
 */
public class NegaScout extends AI {
    int depth;
    private GameTree gameTree;
    private EvaluationFunction evaluator;
    private Node<Board> root;
    private Board board;

//  public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
// return -1;
// }

    public NegaScout(int depth, Board board, GameTree gameTree) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
        this.gameTree = gameTree;
    }

    public NegaScout(int depth, Board board) {
        this.depth = depth;
        this.evaluator = new EvaluationFunction(board);
    }

    public double evaluateFitness(int gamesToBeSimmed, int boardSize) {
        return -1;
    }

    /**
     * Finds best move based for board on NegaScout algorithm
     * @param board board for which best move needs to be found
     * @return best move
     */
    public int[] getBestMove(Board board){
        Node<Board> root = getRootMinimaxed(board);

        int[] bestMove = new int[2];
       try {
            bestMove[0] = selectMove(root).getRow();
            bestMove[1] = selectMove(root).getColumn();
        }
        catch(NullPointerException e) {
            System.out.println("no more moves");
        }
        return bestMove;
    }

    /**
     * Chooses the move according to the best move
     * @param root root of the tree that is created
     * @return move
     */
    public Node<Board> selectMove(Node<Board> root) {
        for (Node<Board> currentChild : root.getChildren()) {
            if (-1 * currentChild.getValue() == root.getValue()) return currentChild;
        }
        return null;
    }

    /**
     * NegaScout algorithm using an alpha-beta window
     * @param currentNode the node that is currently viewed
     * @param alpha alpha being the cutoff point of the tree
     * @param beta
     * @param player player being the black or white player
     * @return the minimax evaluation value of all the nodes
     */
    public double NegaSAlg(Node<Board> currentNode, double alpha, double beta, int player) {
        if (currentNode.getChildren().size() == 0) {
            //System.out.println(player * evaluator.evaluate(currentNode.getData()));
            double value = player * evaluator.evaluate(currentNode.getData());
            currentNode.setValue(value);
            return value;
        } else {
            double value;
            for (Node<Board> child : currentNode.getChildren()) {
                if(child != currentNode.getChildren().get(0)) {
                    value = -NegaSAlg(child, -alpha-1, -alpha, -player);
                    if (alpha < value && value < beta) {
                        value = -NegaSAlg(child, -beta, -value, -player);
                    }
                } else {
                    value = -NegaSAlg(child, -beta, -alpha, -player);
                }
                alpha = Math.max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            }
            currentNode.setValue(alpha);
            return alpha;
        }
    }

    public Node<Board> getRootMinimaxed(Board board){
        NegaScout ns = new NegaScout(this.depth, board);
        gameTree = new GameTree(this.depth, board);
        Node<Board> root = gameTree.createTree();

        ns.NegaSAlg(root, -10000, 10000, board.getCurrentPlayer()); //integer minvalue and max

        return root;
    }

    public int[] getBestMoveFromNode(Node<Board> root){
        int[] bestMove = new int[2];
        try {
            bestMove[0] = selectMove(root).getRow();
            bestMove[1] = selectMove(root).getColumn();
        }
        catch(NullPointerException e) {
            System.out.println("no more moves");
        }
        return bestMove;
    }

    public GameTree getGameTree() {
        return gameTree;
    }

    public Node<Board> selectPV_node(Node<Board> root, int depth){
        Node<Board> currentRoot = root;
        for(int i = 0; i < depth; i++){
            Node<Board> newRoot = selectMove(currentRoot);
            if(newRoot != null) currentRoot = newRoot;
        }
        return currentRoot;
    }

    public void PV_orderTreeLayer(Node<Board> PV_node){
        Node<Board> currentPV_node = PV_node;
        Node<Board> currentParent = PV_node.getParent();
        Node<Board> foundPV_node = null;
        //List<Node<Board>> currentChildren = currentParent.getChildren();

        while(currentParent != null) {
            List<Node<Board>> currentChildren = currentParent.getChildren();
            for (Node<Board> child : currentChildren) {
                if (child == currentPV_node) {
                    foundPV_node = child;
                    //currentChildren.add(0, child);
                }
            }
            currentChildren.remove(foundPV_node);
            currentChildren.add(0, foundPV_node);

            currentPV_node = currentParent;
            currentParent = currentParent.getParent();
        }
    }

    public void MinimaxIterative(Board board, Node<Board> root){
        NegaScout ns = new NegaScout(this.depth, board);
        gameTree.addTreeLayer();

        ns.NegaSAlg(root, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer());
    }

}
