package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Coord {
    private int x;
    private int y;
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coord [x=" + x + ", y=" + y + "]";
    }
}
