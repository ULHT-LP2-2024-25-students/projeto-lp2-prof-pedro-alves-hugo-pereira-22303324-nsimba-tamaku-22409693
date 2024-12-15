package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Adult extends Creature{
    public Adult(String name, int id, Team type, int row, int col, String image) {
        super(name, id, type, row, col, image);
    }

    @Override
    public   String getCreatureTypeAsString() {
        return "Adulto";
    }

    @Override
    public boolean movesRectilinear(int distance) {
        return distance >= 1 && distance <= 2;
    }

    @Override
    public boolean movesObliqual(int distance) {
        return distance >= 1 && distance <= 2;
    }
}
