/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author dan
 */
public class Percolation {
    
    boolean[] grid;
    WeightedQuickUnionUF tree;

    int pseudoNodeTop;
    int pseudoNodeBottom;
    
    public Percolation(int n) {
        int gridSize = n * n;
        pseudoNodeTop = gridSize;
        pseudoNodeBottom = gridSize + 1;
        
        if (n <= 0) throw new java.lang.IllegalArgumentException("n must be > 0");
        
        grid = new boolean[gridSize];
        tree = new WeightedQuickUnionUF(gridSize + 2); // +2 pseudo nodes
        
        for(int i=0;i<grid.length;i++){
                grid[i] = false;
        }
        
        // connect pseudo nodes to prevent n^2 percolation checks
        for (int i=0; i < n; i++) {
            tree.union(pseudoNodeTop, i);
            tree.union(pseudoNodeBottom, gridSize-1-i);
        }
    }
    
    private int getGridPos (int row, int col) {
        Double gridWidth = Math.sqrt(grid.length);
        
        return (row -1 ) * gridWidth.intValue() + col - 1;
    }
    
    private boolean validGridPos (int row, int col) {
        Double gridWidth = Math.sqrt(grid.length);
        return row >= 1 
                && row <= gridWidth.intValue() 
                && col >= 1 
                && col <= gridWidth.intValue();
    }
    
    private int[][] getAdjacents (int row, int col) {
        int[][] adjacents = new int[4][2];
        int[][] directions = {{0,-1},{-1,0},{0,1},{1,0}};
        
        for (int i=0; i<4; i++) {
            if (validGridPos(
                    row + directions[i][0], 
                    col + directions[i][1])) {
                adjacents[i] = new int[]{row + directions[i][0], 
                                         col + directions[i][1]};
            } else {
                adjacents[i] = new int[]{row, col};
            }
        }
        
        return adjacents;
    }
    
    public void open (int row, int col) {
        
        if (validGridPos(row, col)) {
            // open grid position
            grid[getGridPos(row, col)] = true;
        
            // check adjacents for open sites         
            for (int[] adjacent : getAdjacents(row, col)) {
                if (row != adjacent[0] || col != adjacent[1]) {
                    if (isOpen(adjacent[0], adjacent[1])) {
                        tree.union(
                            getGridPos(row,col),
                            getGridPos(adjacent[0],adjacent[1]));
                    }
                }
            }
        }
        
    }
    
    public boolean isOpen (int row, int col) {

        return grid[getGridPos(row, col)];
    }
    
    public boolean isFull (int row, int col) {
        
        return tree.connected(getGridPos(row, col), pseudoNodeTop);
    }
    
    public int numberOfOpenSites() {
        int openCount = 0;
        
        for (int i=0; i<grid.length; i++) {
            if (grid[i]) openCount++;
        }
        
        return openCount;
    }
    
    public boolean percolates() {
        return tree.connected(pseudoNodeTop, pseudoNodeBottom);
    }
    
 
}

