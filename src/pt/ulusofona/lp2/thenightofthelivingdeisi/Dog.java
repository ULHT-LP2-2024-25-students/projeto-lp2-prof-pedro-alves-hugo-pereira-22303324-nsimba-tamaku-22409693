package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Dog extends Creature {
    public Dog(String name, int id, int row, int col, String image) {
        super(name,  id,  Team.ALIVES,  row, col,  image);
    }

    @Override
    public String getCreatureTypeAsString() {
        return "Cão";
    }

    @Override
    public boolean movesRectilinear(int distance) {
        return distance >= 1 && distance <= 2;
    }

    @Override
    public boolean movesObliqual(int distance) {
        return false;
    }
}
