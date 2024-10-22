package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class Creature {
    private String name;
    private int id;
    private TypeCreature typeCreature;
    private int row;
    private int col;
    private ArrayList<Equipment> equipments;
    private String image;

    public Creature(String name, int id, TypeCreature typeCreature, int row, int col, String image) {
        this.name = name;
        this.id = id;
        this.typeCreature = typeCreature;
        this.row = row;
        this.col = col;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public TypeCreature getTypeCreature() {
        return typeCreature;
    }

    public String getCreatureTypeAsString() {
        return typeCreature == TypeCreature.HUMANO ? "Humano" : "Zombie";
    }

    public String getCreatureSign() {
        return typeCreature == TypeCreature.HUMANO ? "+" : "-";
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

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void changePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String[] getCreatureInfo() {
        String[] info = new String[6];
        info[0] = String.valueOf(getId());
        String typeAsString = getCreatureTypeAsString().charAt(0) + ""; // (+ "") para converter char para String
        info[1] = typeAsString;
        info[2] = getName();
        info[3] = String.valueOf(getRow());
        info[4] = String.valueOf(getCol());
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
                row, col);
    }
}
