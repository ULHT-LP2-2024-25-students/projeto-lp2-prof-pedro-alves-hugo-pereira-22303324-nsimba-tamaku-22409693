package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Child extends Creature {
    public Child(String name, int id, Team type, int row, int col, String image) {
        super(name, id, type, row, col, image);
    }


    public String getCreatureTypeAsString() {
        return "Criança";
    }

    @Override
    public boolean movesRectilinear(int distance) {
        return distance == 1;
    }

    @Override
    public boolean movesObliqual(int distance) {
        return false;
    }

    @Override
    public void equip(Equipment equipment) {
        if (equipment.isDefensive()) {
            super.equip(equipment);
        } else {
            throw new UnsupportedOperationException(" As crianças vivas não podem apanhar equipamentos ofensivos");
        }

    }
}
