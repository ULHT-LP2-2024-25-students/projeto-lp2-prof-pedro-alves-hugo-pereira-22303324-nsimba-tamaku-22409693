package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Vampire extends Creature {
    public Vampire(String name, int id, int row, int col, String image) {
        super(name,  id,  Team.ZOMBIES,  row, col,  image);
    }

    @Override
    public String getCreatureTypeAsString() {
        return "Vampiro";
    }

    @Override
    public boolean movesRectilinear(int distance) {
        return distance == 1;
    }

    @Override
    public boolean movesObliqual(int distance) {
        return distance == 1;
    }

    @Override
    public boolean onlyMovesAtNight() {
        return true;
    }
}
