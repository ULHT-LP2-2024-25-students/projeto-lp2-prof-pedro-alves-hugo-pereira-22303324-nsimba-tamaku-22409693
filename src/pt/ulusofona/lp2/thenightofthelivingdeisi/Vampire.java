package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Vampire extends Creature {
    public Vampire(String name, int id, int row, int col, String image) {
        super(name,  id,  Team.ZOMBIES,  row, col,  image);
    }




    @Override
    public String getCreatureTypeAsString() {
        return "Vampiro";
    }


    public String getResumedInfo() {
        return String.format("Z:%d", this.getId());
    }
}
