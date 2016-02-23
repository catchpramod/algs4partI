import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by pramod on 2/20/16.
 */
public class Solver {
    private boolean solvable;
    private Node search;

    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        search = new Node(initial);
        Node tsearch = new Node(initial.twin());

        solvable = false;
        boolean solved = false;

        MinPQ<Node> iQ = new MinPQ<>();
        MinPQ<Node> tQ = new MinPQ<>();
        iQ.insert(search);
        tQ.insert(tsearch);

        do {
            //remove min from queue
            search = iQ.delMin();
            Board searchBoard = search.board;
            if (!searchBoard.isGoal()) {
                Iterator<Board> mit = searchBoard.neighbors().iterator();
                while (mit.hasNext()) {
                    Node n = new Node(mit.next(), search, search.nMoves + 1);
                    if (search.previous == null || !n.board.equals(search.previous.board)) iQ.insert(n);
                }
            } else {
                solved = true;
                solvable = true;
            }

            tsearch = tQ.delMin();
            searchBoard = tsearch.board;
            if (!searchBoard.isGoal()) {
                Iterator<Board> mit = searchBoard.neighbors().iterator();
                while (mit.hasNext()) {
                    Node n = new Node(mit.next(), tsearch, tsearch.nMoves + 1);
                    if (tsearch.previous == null || !n.board.equals(tsearch.previous.board)) tQ.insert(n);
                }
            } else {
                solved = true;
                solvable = false;
            }

        } while (!solved);

    }

    public boolean isSolvable() {         // is the initial board solvable?
        return solvable;
    }

    public int moves() {        // min number of moves to solve initial board; -1 if unsolvable
        if (!solvable) return -1;
        return search.nMoves;
    }

    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable

        if (!solvable) return null;
        Stack<Board> solution = new Stack<>();
        Node n = search;
        while (n != null) {
            solution.push(n.board);
            n = n.previous;
        }

        return solution;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node previous;
        private int nMoves;

        public Node(Board b) {
            board = b;
            nMoves = 0;
            previous = null;
        }

        public Node(Board b, Node p, int m) {
            board = b;
            previous = p;
            nMoves = m;
        }

        @Override
        public int compareTo(Node o) {
            return board.manhattan() + nMoves - o.board.manhattan() - o.nMoves;

        }
    }

    public static void main(String[] args) {    // solve a slider puzzle (given below)

        // create initial board from file
        In in = new In(args[0]);
//        if (args != null && args.length > 0 && !args[0].equals("")) in = new In(args[0]);
//        else in = new In("test/puzzle04.txt");

        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())

            StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());

        }
    }
}