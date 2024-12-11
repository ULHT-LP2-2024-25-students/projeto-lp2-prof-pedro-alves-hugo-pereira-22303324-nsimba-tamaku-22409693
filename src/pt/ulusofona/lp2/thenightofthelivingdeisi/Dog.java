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

    @Override
    public void equip(Equipment equipment) {
        throw new UnsupportedOperationException("Caes não apanham equipaentos");
    }

    @Override
    public String getInfoAsString() {
        int equipmentCounter = equipment == null ? 0 : 1;
        return String.format("%s | %s | %s @ (%d, %d)",
                getId(),
                getCreatureTypeAsString(),
                getName(),
                coord.getY(), coord.getX());
    }
}
