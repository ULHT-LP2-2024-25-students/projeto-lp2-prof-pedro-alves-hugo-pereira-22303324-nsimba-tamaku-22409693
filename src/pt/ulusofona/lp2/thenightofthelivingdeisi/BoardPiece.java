package pt.ulusofona.lp2.thenightofthelivingdeisi;

public abstract class BoardPiece {
    private Coord coord;


    public Coord getCoord() {
        return this.coord;
    }

    abstract String getResumedInfo();


}
