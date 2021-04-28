import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PathFinder {

    public static class Cell {

        private final int rowNum;
        private final int colNum;

        public Cell(int rowNum, int colNum) {
            this.rowNum = rowNum;
            this.colNum = colNum;
        }

        public int getRowNum() {
            return rowNum;
        }

        public int getColNum() {
            return colNum;
        }
    }

    private boolean[][] walls;
    private int[][] dist;
    private Cell[][] pred;
    private boolean[][] visited;
    private int numMoves;

    public PathFinder(boolean[][] walls) {
        this.walls = walls;
        this.dist = new int[walls.length][walls[0].length];
        this.pred = new Cell[walls.length][walls[0].length];
        this.visited = new boolean[walls.length][walls[0].length];
    }

    private boolean BFS() {

        LinkedList<Cell> queue = new LinkedList<>();

        // 4 directions that we can take
        int[][] squares = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        // initiate the arrays
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[0].length; j++) {
                visited[i][j] = false;
                dist[i][j] = Integer.MAX_VALUE;
                pred[i][j] = null;
            }
        }

        // make walls visited tiles
        for (int i = 0; i < walls.length; i++) {
            System.arraycopy(walls[i], 0, visited[i], 0, walls[0].length);
        }

        // starting from the top left
        Cell startingCell = new Cell(0,0);
        visited[0][0] = true;
        queue.add(startingCell);


        while (!queue.isEmpty()) {

            // pop the first element from the list
            Cell currCell = queue.poll();

            // check adj tiles
            for (int[] square : squares) {

                int row = currCell.getRowNum() + square[0];
                int col = currCell.getColNum() + square[1];

                // check whether we are inside the grid
                if (row >= 0 && col >= 0 && row < walls.length && col < walls[0].length) {

                    if (!visited[row][col]) {

                        visited[row][col] = true;
                        dist[row][col] = dist[currCell.getRowNum()][currCell.getColNum()] + 1;
                        pred[row][col] = currCell;
                        queue.add(new Cell(row, col));

                        if (row == walls.length - 1 && col == walls[0].length - 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public LinkedList<Cell> getPath() {

        LinkedList<Cell> path = new LinkedList<Cell>();

        if (BFS()) {

            Cell currCell = new Cell(walls.length-1, walls[0].length-1);
            path.addFirst(currCell);

            // backtrack each cell and add it to the path
            while (pred[currCell.getRowNum()][currCell.getColNum()] != null) {
                path.addFirst(pred[currCell.getRowNum()][currCell.getColNum()]);
                currCell = pred[currCell.getRowNum()][currCell.getColNum()];
            }
        }
        numMoves = path.size();
        return path;
    }

    public int getNumMoves() {
        return numMoves;
    }
}
