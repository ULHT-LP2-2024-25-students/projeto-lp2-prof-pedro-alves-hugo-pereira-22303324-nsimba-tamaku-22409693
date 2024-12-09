package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Dog extends Creature {
    public Dog(String name, int id, int row, int col, String image) {
        super(name,  id,  Team.ALIVES,  row, col,  image);
    }

    @Override
    public String getCreatureTypeAsString() {
        return "Cão";
    }

    public String getResumedInfo() {
        return String.format("H:%d", this.getId());
    }
}
