package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class Creature {
    private String name;
    private int id;
    private CreatureType type;
    private Coord coord;
    private Equipment equipment;
    private String image;
    private int equipmentsDestroyed;

    public Creature(String name, int id, CreatureType type, int row, int col, String image) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.coord = new Coord(row, col);
        this.image = image;
        this.equipmentsDestroyed = 0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CreatureType getType() {
        return type;
    }

    public String getCreatureTypeAsString() {
        return type == CreatureType.HUMANO ? "Humano" : "Zombie";
    }

    public String getCreatureSign() {
        return type == CreatureType.HUMANO ? "+" : "-";
    }

    public boolean isHuman() {
        return type == CreatureType.HUMANO;
    }

    public String getImage() {
        return image;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void changePosition(int row, int col) {
        this.coord = new Coord(row, col);
        if (hasEquipment()) {
            equipment.changePosition(row, col);
        }
    }

    public String[] getInfo() {
        String[] info = new String[6];
        String typeAsString = getCreatureTypeAsString();
        info[0] = String.valueOf(getId());
        info[1] = typeAsString;
        info[2] = getName();
        info[3] = String.valueOf(coord.getY());
        info[4] = String.valueOf(coord.getX());
        info[5] = String.valueOf(getImage());
        return info;
    }


    public String getInfoAsString() {
        int equipmentCounter = equipment == null ? 0 : 1;
        return String.format("%s | %s | %s | %s%d @ (%d, %d)",
                getId(),
                getCreatureTypeAsString(),
                getName(),
                getCreatureSign(),
                isHuman() ? equipmentCounter : equipmentsDestroyed,
                coord.getY(),coord.getX());
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean equip(Equipment equipment) {
        if (type == CreatureType.HUMANO && this.equipment == null) {
            this.equipment = equipment;
            return true;
        }
        if (type == CreatureType.ZOMBIE) {
            equipmentsDestroyed++;
            return true;
        }
        return false;
    }

    public void incrementEquipmentsDestroyed() {
        equipmentsDestroyed++;
    }

    public boolean hasEquipment() {
        return equipment != null;
    }
}
