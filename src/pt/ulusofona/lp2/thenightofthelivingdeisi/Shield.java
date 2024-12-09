package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Shield extends Equipment{
    public Shield(int id, int row, int col, String image) {
        super(id, row, col, image, EquipmentType.DEFENSIVE);
    }

    @Override
    public String getAssocietedTypeName() {
        return "Escudo";
    }

    @Override
    public int getAssocietedTypeNumber() {
        return 0;
    }
}
