package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Bleach extends  Equipment{
    public Bleach(int id,  int row, int col, String image) {
        super(id, row, col, image, EquipmentType.DEFENSIVE);
    }

    @Override
    public String getAssocietedTypeName() {
        return "Lixívia";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 3;
    }
}
