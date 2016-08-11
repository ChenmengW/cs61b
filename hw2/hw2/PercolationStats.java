package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;

public class PercolationStats {

    private int N;
    private int T;
    private ArrayList<Double> threshold;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("arguments cannot be zero");
        }

        this.N = N;
        this.T = T;
        this.threshold = this.runSimulation();
    }

    private double runSinglePass() {
        Percolation p = new Percolation(this.N);
        int row = StdRandom.uniform(0, this.N);
        int col = StdRandom.uniform(0, this.N);

        while (!p.percolates()) {

            // get a blocked site
            while (!p.isOpen(row, col)) {
                row = StdRandom.uniform(0, this.N);
                col = StdRandom.uniform(0, this.N);
            }

            p.open(row, col);
        }

        double thresh = p.numberOfOpenSites() / this.N * this.N;
        return thresh;
    }

    private ArrayList<Double> runSimulation() {
        ArrayList<Double> thresh = new ArrayList<Double>();

        for (int i = 0; i < this.T; i++) {
            double n = runSinglePass();
            thresh.add(n);
        }

        return thresh;
    }

    public double mean() {
        double mu = 0;

        for (int i = 0; i < this.T; i++) {
            mu += this.threshold.get(i);
        }

        mu /= this.T;
        return mu;
    }

    public double stddev() {
        double mu = this.mean();
        double sigmasq = 0;

        for (int i = 0; i < this.T; i++) {
            double term =  this.threshold.get(i) - mu;
            term = Math.pow(term, 2);
            sigmasq += term;
        }

        sigmasq /= this.T - 1;
        return sigmasq;
    }

    public double confidenceLow() {
        double mu = this.mean();
        double sigma = Math.sqrt(this.stddev());

        double lowEndPoint = mu - ((1.96 * sigma) / Math.sqrt(this.T));
        return lowEndPoint;
    }

    public double confidenceHigh() {
        double mu = this.mean();
        double sigma = Math.sqrt(this.stddev());

        double highEndPoint = mu + ((1.96 * sigma) / Math.sqrt(this.T));
        return highEndPoint;
    }

}                       
