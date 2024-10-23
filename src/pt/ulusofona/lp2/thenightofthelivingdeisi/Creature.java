package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class Creature {
    private String name;
    private int id;
    private CreatureType type;
    private Coord coord;
    private ArrayList<Equipment> equipments;
    private String image;

    public Creature(String name, int id, CreatureType type, int row, int col, String image) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.coord = new Coord(row, col);
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void changePosition(int row, int col) {
        this.coord = new Coord(row, col);
    }

    public String[] getCreatureInfo() {
        String[] info = new String[6];
        info[0] = String.valueOf(getId());
        String typeAsString = getCreatureTypeAsString().charAt(0) + ""; // (+ "") para converter char para String
        info[1] = typeAsString;
        info[2] = getName();
        info[3] = String.valueOf(coord.getX());
        info[4] = String.valueOf(coord.getY());
        info[5] = String.valueOf(getImage());
        return info;
    }

    public String getCreatureInfoAsString() {
        return String.format("%s | %s | %s | %s%d@(%d,%d)",
                getId(),
                getCreatureTypeAsString(),
                getName(),
                getCreatureSign(),
                equipments.size(),
                coord.getX(), coord.getY());
    }

    public Coord getCoord() {
        return coord;
    }
}
