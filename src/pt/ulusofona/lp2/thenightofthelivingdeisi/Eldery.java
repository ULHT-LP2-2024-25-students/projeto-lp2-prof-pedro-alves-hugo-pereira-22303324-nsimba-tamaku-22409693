package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Eldery extends Human{
    public Eldery(String name, int id, Team team, int row, int col, String image) {
        super(name, id, team, row, col, image);
    }

    @Override
    public  String getCreatureTypeAsString() {
        return "Idoso";
    }
}
