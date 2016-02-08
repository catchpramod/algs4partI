import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by pramod on 1/24/16.
 */
public class PercolationStats {
    private int  T;
    private double[] stats;

    public PercolationStats(int N, int T) {     // perform T independent experiments on an N-by-N grid
        if (N < 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        this.T = T;
        stats = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int opencnt = 0;
            while (!p.percolates()) {
                int ri = StdRandom.uniform(N) + 1;
                int rj = StdRandom.uniform(N) + 1;
                if (!p.isOpen(ri, rj)) {
                    p.open(ri, rj);
                    opencnt++;
                }
            }
            stats[i] = (double) opencnt / (N * N);

        }

    }

    public double mean() {                      // sample mean of percolation threshold
        return StdStats.mean(stats);

    }

    public double stddev() {                   // sample standard deviation of percolation threshold
        return StdStats.stddev(stats);
    }

    public double confidenceLo() {            // low  endpoint of 95% confidence interval
        return (mean() - stddev() * 1.96 / Math.sqrt(T));
    }

    public double confidenceHi() {             // high endpoint of 95% confidence interval
        return (mean() + stddev() * 1.96 / Math.sqrt(T));

    }

    public static void main(String[] args) {    // test client (described below) pass CLI arguments as N T
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//        PercolationStats ps = new PercolationStats(100,10);
        System.out.println("mean                     = " + ps.mean());
        System.out.println("stddev                   = " + ps.stddev());
        System.out.println("95% confidence interval  = " + ps.confidenceLo()+", "+ps.confidenceHi());
    }
}
