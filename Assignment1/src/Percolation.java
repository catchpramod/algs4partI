/**
 * Created by pramod on 1/24/16.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufFull;

    public Percolation(int N) {               // create N-by-N grid, with all sites blocked
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufFull = new WeightedQuickUnionUF(N * N + 1);
        grid = new int[N][N];

        for (int i = 0; i < N; i++) {
            uf.union(i + 1, 0);
            ufFull.union(i + 1, 0);
            uf.union(N * N - i, N * N + 1);
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }
    }


    private void connectIfOpen(int a, int b, int p, int q) {
        if (a < 1 || b < 1 || a > grid.length || b > grid.length) return;
        if (isOpen(a, b)) {
            uf.union(grid.length * (a - 1) + b, grid.length * (p - 1) + q);
            ufFull.union(grid.length * (a - 1) + b, grid.length * (p - 1) + q);
        }
    }

    public void open(int i, int j) {          // open site (row i, column j) if it is not open already
        if (i < 1 || j < 1 || i > grid.length || j > grid.length)
            throw new java.lang.IndexOutOfBoundsException();
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = 1;
            connectIfOpen(i - 1, j, i, j);
            connectIfOpen(i + 1, j, i, j);
            connectIfOpen(i, j - 1, i, j);
            connectIfOpen(i, j + 1, i, j);
        }

    }


    public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
        if (i < 1 || j < 1 || i > grid.length || j > grid.length)
            throw new java.lang.IndexOutOfBoundsException();
        return grid[i - 1][j - 1] == 1;
    }

    public boolean isFull(int i, int j) {     // is site (row i, column j) full?
        if (i < 1 || j < 1 || i > grid.length || j > grid.length)
            throw new java.lang.IndexOutOfBoundsException();
        return isOpen(i, j) && ufFull.connected(0, grid.length * (i - 1) + j);
    }

    public boolean percolates() {             // does the system percolate?
        return (grid.length > 1 || isOpen(1, 1)) && uf.connected(0, (grid.length * grid.length + 1));
    }


}
