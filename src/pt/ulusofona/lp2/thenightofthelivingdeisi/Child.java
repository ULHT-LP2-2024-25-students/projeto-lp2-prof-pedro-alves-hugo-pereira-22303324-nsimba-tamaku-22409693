package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Child extends Human {
    public Child(String name, int id, Team type, int row, int col, String image) {
        super(name, id, type, row, col, image);
    }


    public String getCreatureTypeAsString() {
        return "Criança";
    }
}
