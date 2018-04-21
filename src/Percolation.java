import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private int openSites;
    private WeightedQuickUnionUF unionFind;

    private boolean[][] grid;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        checkInBound(row, col);
        grid[row - 1][col - 1] = true;
        openSites++;
        connectToNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        checkInBound(row, col);
        return grid[row - 1][col - 1];
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return unionFind.connected(0, gridSize * (row - 1) + col);
        } else {
            return false;
        }
    }

    public boolean percolates() {
        return unionFind.connected(0, gridSize * gridSize + 1);
    }

    private void checkInBound(int row, int col) {
        if (row > gridSize || row < 1 || col > gridSize || col < 1) {
            throw new IllegalArgumentException();
        }
    }

    private void connectToNeighbors(int row, int col) {
        //top
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                unionFind.union(gridSize * (row - 2) + col, gridSize * (row - 1) + col);
            }
        } else {
            unionFind.union(0, gridSize * (row - 1) + col);
        }
        //bottom
        if (row < gridSize) {
            if (isOpen(row + 1, col)) {
                unionFind.union(gridSize * row + col, gridSize * (row - 1) + col);
            }
        } else {
            unionFind.union(gridSize * gridSize + 1, gridSize * (row - 1) + col);
        }
        //left
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                unionFind.union(gridSize * (row - 1) + col - 1, gridSize * (row - 1) + col);
            }
        }
        //right
        if (col < gridSize) {
            if (isOpen(row, col + 1)) {
                unionFind.union(gridSize * (row - 1) + col + 1, gridSize * (row - 1) + col);
            }
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(1, 4);
        percolation.open(1, 5);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        System.out.println(percolation.isFull(3, 3));


    }
}

