package pt.ulusofona.lp2.thenightofthelivingdeisi;

public abstract class Equipment extends BoardPiece {
    protected final int id;
    protected Coord coord;
    protected String image;
    protected EquipmentStatus status;
    protected EquipmentType type;

    public Equipment(int id, int row, int col, String image, EquipmentType type) {
        this.id = id;
        this.coord = new Coord(row, col);
        this.image = image;
        this.status = EquipmentStatus.UNCAPTURED;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return this.coord;
    }


    abstract public String getAssocietedTypeName();

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    abstract public int getAssocietedTypeNumber();

    public String getImage() {
        return image;
    }


    @Override
    public boolean moves() {
        return false;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void changePosition(Coord coord) {
        this.coord.update(coord);
    }

    public boolean hasAmo() {
        return true;
    }

    public void use() {

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

    public void setAsCaptured() {
        this.status = EquipmentStatus.CAPTURED;
    }

    public void setAsDestroyed() {
        this.status = EquipmentStatus.DESRTOYED;
    }

    public void setAsUncaptured() {
        this.status = EquipmentStatus.UNCAPTURED;
    }

    public boolean isCaptured() {
        return status == EquipmentStatus.CAPTURED;
    }

    public boolean isDestroyed() {
        return status == EquipmentStatus.DESRTOYED;
    }


    public String getInfoAsString() {
        String typeEquipment = getAssocietedTypeName();
        return String.format("%d | %s @ (%d, %d)", id, typeEquipment, coord.getY(), coord.getX());
    }

    @Override
    public String getResumedInfo() {
        return String.format("E:%d", this.getId());
    }

    public EquipmentType getType() {
        return type;
    }
}
