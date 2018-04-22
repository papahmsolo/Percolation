import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        double[] thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.printf("[%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}