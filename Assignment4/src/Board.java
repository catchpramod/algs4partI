import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * Created by pramod on 2/20/16.
 */
public class Board {

    private int[][] items;

    /*
    construct a board from an N-by-N array of blocks
    (where blocks[i][j] = block in row i, column j)
    */
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        items = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                items[i][j] = blocks[i][j];
            }
        }

    }

    public int dimension() {              // board dimension N
        return items.length;
    }

    public int hamming() {        // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (items[i][j] != 0 && items[i][j] != i * dimension() + j + 1) hamming++;
            }
        }
        return hamming;
    }

    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (items[i][j] != 0 && items[i][j] != i * dimension() + j + 1) {
                    int val = items[i][j];
                    int k = (val - 1) / dimension();
                    int l = (val - 1) % dimension();
                    manhattan += (Math.abs(i - k) + Math.abs(j - l));
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {           // is this board the goal board?
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (items[i][j] != 0 && items[i][j] != i * dimension() + j + 1) return false;
            }
        }
        return true;
    }

    public Board twin() {          // a board that is obtained by exchanging any pair of blocks
        int p, q, r, s;
        do {
            p = StdRandom.uniform(dimension());
            q = StdRandom.uniform(dimension());
            r = StdRandom.uniform(dimension());
            s = StdRandom.uniform(dimension());
        } while (items[p][q] == 0 || items[r][s] == 0 || items[p][q] == items[r][s]);
        int[][] itemsc = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                itemsc[i][j] = items[i][j];
            }
        }
        itemsc[p][q] = items[r][s];
        itemsc[r][s] = items[p][q];
        return new Board(itemsc);
    }

    public boolean equals(Object y) {        // does this board equal y?
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (items[i][j] != that.items[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        Queue<Board> neighbors = new Queue<Board>();

        int[][] items1 = new int[dimension()][dimension()];
        int[][] items2 = new int[dimension()][dimension()];
        int[][] items3 = new int[dimension()][dimension()];
        int[][] items4 = new int[dimension()][dimension()];
        int ei = 0, ej = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                items1[i][j] = items[i][j];
                items2[i][j] = items[i][j];
                items3[i][j] = items[i][j];
                items4[i][j] = items[i][j];
                if (items[i][j] == 0) {
                    ei = i;
                    ej = j;
                }
            }
        }

        if (ei - 1 >= 0) {
            items1[ei - 1][ej] = 0;
            items1[ei][ej] = items[ei - 1][ej];
            Board board1 = new Board(items1);
            neighbors.enqueue(board1);
        }
        if (ej + 1 < dimension()) {
            items2[ei][ej + 1] = 0;
            items2[ei][ej] = items[ei][ej + 1];
            Board board2 = new Board(items2);
            neighbors.enqueue(board2);
        }
        if (ei + 1 < dimension()) {
            items3[ei + 1][ej] = 0;
            items3[ei][ej] = items[ei + 1][ej];
            Board board3 = new Board(items3);
            neighbors.enqueue(board3);
        }
        if (ej - 1 >= 0) {
            items4[ei][ej - 1] = 0;
            items4[ei][ej] = items[ei][ej - 1];
            Board board4 = new Board(items4);
            neighbors.enqueue(board4);
        }

        return neighbors;

    }

    public String toString() {     // string representation of this board (in the output format specified below)
        StringBuffer sb = new StringBuffer();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(" ");
                sb.append(items[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) { // unit tests (not graded)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println("Initial:");
        System.out.println(initial.toString());
        System.out.println("Neighbours:");
        Iterator e = initial.neighbors().iterator();
        while (e.hasNext()) {
            System.out.println(e.next());
        }

    }

}
