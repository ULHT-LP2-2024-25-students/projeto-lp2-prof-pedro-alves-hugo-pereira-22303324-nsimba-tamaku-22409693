package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Pistol extends Equipment {

    private int bullets;

    public Pistol(int id, int row, int col, String image) {
        super(id, row, col, image, EquipmentType.OFESIVE);
        this.bullets = 3;
    }

    @Override
    public String getAssocietedTypeName() {
        return "Pistola Walther PPK";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 2;
    }

    @Override
    public String getInfoAsString() {
        String typeEquipment = getAssocietedTypeName();
        return String.format("%d | %s @ (%d, %d) | %d balas", id, typeEquipment, coord.getY(), coord.getX(), bullets);
    }

    @Override
    public boolean hasAmo() {
        return bullets > 0;
    }

    @Override
    public void use() {
        if (bullets > 0) {
            bullets--;
        }
    }
}
