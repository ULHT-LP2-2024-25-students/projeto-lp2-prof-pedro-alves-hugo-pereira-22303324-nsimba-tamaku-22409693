package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Board {
    int rows;
    int cols;
    String[][] grid;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new String[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
