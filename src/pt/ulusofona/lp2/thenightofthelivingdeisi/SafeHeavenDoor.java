package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class SafeHeavenDoor extends BoardPiece{
    private Coord coord;
    public SafeHeavenDoor(Coord coord) {
        this.coord = coord;
    }

    @Override
    String getResumedInfo() {
        return "SH";
    }

    @Override
    public boolean moves() {
        return false;
    }

    public Coord getCoord() {
        return this.coord;
    }

    @Override
    public boolean leadsToSaveHeaven() {
        return true;
    }
}
