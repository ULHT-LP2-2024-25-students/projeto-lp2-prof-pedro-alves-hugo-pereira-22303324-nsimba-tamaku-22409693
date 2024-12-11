package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Bleach extends Equipment{
    private double liters;

    public Bleach(int id,  int row, int col, String image) {
        super(id, row, col, image, EquipmentType.DEFENSIVE);
        this.liters = 1.0;
    }

    @Override
    public String getAssocietedTypeName() {
        return "Lixívia";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 3;
    }

    @Override
    public String getInfoAsString() {
        String typeEquipment = getAssocietedTypeName();
        return String.format("%d | %s @ (%d, %d) | %.1f litros", id, typeEquipment, coord.getY(), coord.getX(), liters);
    }

    @Override
    public void use() {
        if ((liters - 0.3) > 0) {
            liters -= 0.3;
        } else {
            liters = 0;
        }
    }

    @Override
    public boolean hasAmo() {
        return liters > 0;
    }
}
