package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Human extends Creature{
    public Human(String name, int id, Team type, int row, int col, String image) {
        super(name, id, type, row, col, image);
    }

    @Override
    public String getCreatureTypeAsString() {
        return "";
    }



    public String getResumedInfo() {
        String team = this.getTeam() == Team.ALIVES? "H" : "Z";
        return String.format("%s:%d", team, this.getId());
    }
}
