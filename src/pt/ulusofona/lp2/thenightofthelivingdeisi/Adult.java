package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Adult extends Human{
    public Adult(String name, int id, Team type, int row, int col, String image) {
        super(name, id, type, row, col, image);
    }

    @Override
    public   String getCreatureTypeAsString() {
        return "Adulto";
    };
}
