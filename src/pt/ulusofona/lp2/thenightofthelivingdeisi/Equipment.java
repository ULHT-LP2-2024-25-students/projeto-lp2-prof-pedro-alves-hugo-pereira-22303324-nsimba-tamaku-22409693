package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class Equipment {
    private final int id;
    private TypeEquipment type;
    private int row;
    private int col;
    private String image;

    public Equipment(int id, TypeEquipment type, int row, int col, String image) {
        this.id = id;
        this.type = type;
        this.row = row;
        this.col = col;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public TypeEquipment getType() {
        return type;
    }

    public String getTypeEquipmentAsString() {
        return type == TypeEquipment.ESPADA ? "Espada Samurai" : "Escudo de Madeira";
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void changePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String[] getEquipmentInfo() {
        String[] info = new String[5];
        info[0] = Integer.toString(id);
        info[1] = type.toString();
        info[2] = Integer.toString(row);
        info[3] = Integer.toString(col);
        info[4] = image;
        return info;
    }

    public String getEquipmentInfoAsString() {
        String typeEquipment = getTypeEquipmentAsString();
        return String.format("-%d | %s @(%d,%d)", id, typeEquipment, row, col);
    }
}
