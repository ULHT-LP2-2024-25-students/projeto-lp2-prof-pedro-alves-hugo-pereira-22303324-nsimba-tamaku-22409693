package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Eldery extends Creature{
    public Eldery(String name, int id, Team team, int row, int col, String image) {
        super(name, id, team, row, col, image);
    }

    @Override
    public  String getCreatureTypeAsString() {
        return "Idoso";
    }

    @Override
    public boolean movesRectilinear(int distance) {
        return false;
    }

    @Override
    public boolean movesObliqual(int distance) {
        return distance == 1;
    }
}
