import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private int openSites;
    private WeightedQuickUnionUF unionFind;

    private boolean[][] grid;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++;
            connectToNeighbors(row, col);
        }
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
            return unionFind.connected(0, getGridId(row, col));
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
                unionFind.union(getGridId(row - 1, col), getGridId(row, col));
            }
        } else {
            unionFind.union(0, getGridId(row, col));
        }
        //bottom
        if (row < gridSize) {
            if (isOpen(row + 1, col)) {
                unionFind.union(getGridId(row + 1, col), getGridId(row, col));
            }
        } else {
            unionFind.union(gridSize * gridSize + 1, getGridId(row, col));
        }
        //left
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(getGridId(row, col - 1), getGridId(row, col));
        }
        //right
        if (col < gridSize && isOpen(row, col + 1)) {
            unionFind.union(getGridId(row, col + 1), getGridId(row, col));
        }
    }

    private int getGridId(int row, int col) {
        return (row - 1) * gridSize + col;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 5);
        percolation.open(2, 5);
        percolation.open(3, 5);
        percolation.open(4, 5);
        percolation.open(5, 5);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
        System.out.println(percolation.isFull(3, 5));
    }
}

