package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Equipment {
    private final int id;
    private final EquipmentType type;
    private Coord coord;
    private String image;

    public Equipment(int id, EquipmentType type, int row, int col, String image) {
        this.id = id;
        this.type = type;
        this.coord = new Coord(row, col);
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public EquipmentType getType() {
        return type;
    }

    public String getAssocietedTypeName() {
        return type == EquipmentType.ESPADA ? "Espada samurai" : "Escudo de madeira";
    }

    public int getAssocietedTypeNumber() {
        return type == EquipmentType.ESPADA ? 1 : 0;
    }

    public String getImage() {
        return image;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void changePosition(int row, int col) {
        coord = new Coord(row, col);
    }

    public String[] getInfo() {
        String[] info = new String[5];
        info[0] = Integer.toString(id);
        info[1] = Integer.toString(getAssocietedTypeNumber());
        info[2] = Integer.toString(coord.getY());
        info[3] = Integer.toString(coord.getX());
        info[4] = image;
        return info;
    }

    public String getInfoAsString() {
        String typeEquipment = getAssocietedTypeName();
        return String.format("%d | %s @ (%d,%d)", id, typeEquipment, coord.getY(), coord.getX());
    }
}
