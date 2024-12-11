package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Sword extends Equipment{
    public Sword(int id, int row, int col, String image) {
        super(id, row, col, image, EquipmentType.OFESIVE);
    }

    @Override
    public String getAssocietedTypeName() {
        return "Espada samurai";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 1;
    }

    @Override
    public boolean hasAmo() {
        return true;
    }
}
