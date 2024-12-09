package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class EquipmentFactory {
    private static EquipmentFactory instance;

    public static EquipmentFactory getInstance() {
        if (instance == null) {
            instance = new EquipmentFactory();
        }
        return instance;
    }
    private EquipmentFactory() {

    }

    public Equipment createEquipment(int equipmentType, int id, int row, int col, int currentLine) throws InvalidFileException {
        return switch (equipmentType) {
            case 0 -> new Shield(id, row, col, null);
            case 1 -> new Sword(id, row, col, null);
            case 2 -> new Pistol(id, row, col, null);
            case 3 -> new Bleach(id, row, col, null);
            default -> throw new InvalidFileException(currentLine);
        };
    }
}
