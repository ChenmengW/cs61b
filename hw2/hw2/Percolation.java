package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int numOpenSites;
    private boolean[][] openSites;


    private WeightedQuickUnionUF disjointSets;
    private WeightedQuickUnionUF connectedToTop;

    private int virtualTop;
    private int virtualBottom;

    private int indexXYTo1D(int x, int y) {
        return (x * N) + y;
    }

    public Percolation(int N) {                 // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N must be positive");
        }

        this.N = N;
        this.numOpenSites = 0;
        this.openSites = new boolean[N][N];     // all are initialized to false by default

        int numSets = (N * N) + 2;              // + 2 for virtual top & bottom
        this.disjointSets = new WeightedQuickUnionUF(numSets);   // each element in its own set
        this.connectedToTop = new WeightedQuickUnionUF(numSets - 1);    // only virtual top, no bottom

        // Make the last 2 elements of the WQUUF the virtual
        // top and bottom
        this.virtualTop = numSets - 2;
        this.virtualBottom = numSets - 1;

    }

    public void open(int row, int col) {       // open the site (row, col) if it is not open already
        if (row < 0 || row >= this.N ||
                col < 0 || col >= this.N) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        openSites[row][col] = true;
        int index1D = this.indexXYTo1D(row, col);

        // Connect top row to virtual top and bottom row to
        // virtual bottom

        if (row == 0) {
            this.disjointSets.union(index1D, this.virtualTop);
            this.connectedToTop.union(index1D, this.virtualTop);
        }

        if (row == this.N - 1) {
            this.disjointSets.union(index1D, this.virtualBottom);
        }


        // Check if neighbors are open. If yes, connect them.
        // Omit the checks for boundary cells obviously.

        if (row > 0 && isOpen(row - 1, col)) {                     // up
            int upIndex1D = this.indexXYTo1D(row - 1, col);
            this.disjointSets.union(index1D, upIndex1D);
            this.connectedToTop.union(index1D, upIndex1D);
        }

        if (row < this.N - 1 && isOpen(row + 1, col)) {            // down
            int downIndex1D = this.indexXYTo1D(row + 1, col);
            this.disjointSets.union(index1D, downIndex1D);
            this.connectedToTop.union(index1D, downIndex1D);
        }

        if (col > 0 && isOpen(row, col - 1)) {                     // left
            int leftIndex1D = this.indexXYTo1D(row, col - 1);
            this.disjointSets.union(index1D, leftIndex1D);
            this.connectedToTop.union(index1D, leftIndex1D);
        }

        if (col < this.N - 1 && isOpen(row, col + 1)) {            // right
            int rightIndex1D = this.indexXYTo1D(row, col + 1);
            this.disjointSets.union(index1D, rightIndex1D);
            this.connectedToTop.union(index1D, rightIndex1D);
        }

        this.numOpenSites += 1;
    }

    public boolean isOpen(int row, int col) {  // is the site (row, col) open?
        if (row < 0 || row >= this.N ||
                col < 0 || col >= this.N) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        return openSites[row][col];
    }

    public boolean isFull(int row, int col) {  // is the site (row, col) full?
        if (row < 0 || row >= this.N ||
                col < 0 || col >= this.N) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int index1D = this.indexXYTo1D(row, col);
        return this.disjointSets.connected(index1D, this.virtualTop)
                && this.connectedToTop.connected(index1D, this.virtualTop);
    }

    public int numberOfOpenSites() {           // number of open sites
        return this.numOpenSites;
    }

    public boolean percolates() {              // does the system percolate?
        return this.disjointSets.connected(this.virtualBottom, this.virtualTop);
    }
    public static void main(String[] args){    // unit testing (not required)
//        Percolation p = new Percolation(5);
//        p.open(0, 3);
//        p.open(1, 3);
    }
}                       
