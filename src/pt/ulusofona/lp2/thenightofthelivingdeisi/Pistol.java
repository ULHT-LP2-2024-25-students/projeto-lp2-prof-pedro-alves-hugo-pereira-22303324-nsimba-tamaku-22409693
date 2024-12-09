package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Pistol extends Equipment{
    public Pistol(int id, int row, int col, String image) {
        super(id, row, col, image, EquipmentType.ONFESIVE);
    }

    @Override
    public String getAssocietedTypeName() {
        return "Pistola";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 2;
    }


}
