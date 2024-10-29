package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    int rows;
    int cols;
    String[][] grid;
    private ArrayList<Creature> creatures;
    private ArrayList<Equipment> equipments;

    public Board(int rows, int cols, ArrayList<Creature> creatures, ArrayList<Equipment> equipments) {
        this.rows = rows;
        this.cols = cols;
        this.creatures = creatures;
        this.equipments = equipments;
        grid = new String[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void assemblePieces() {
        for (Creature creature : creatures) {
            if (positionIsEmpty(creature.getCoord())) { //Verifica se a coordenada esta vazia
                placePiece(creature.getCoord(), creature.getInfoAsString());
            }
        }

        for (Equipment equipment : equipments) {
            if (positionIsEmpty(equipment.getCoord())) {
                placePiece(equipment.getCoord(), equipment.getInfoAsString());
            }
        }
    }

    private void placePiece(Coord coord, String pieceInformation) {
        grid[coord.getX()][coord.getY()] = pieceInformation;
    }

    public HashMap<CreatureType, ArrayList<Creature>> getCreaturesByType() { //
        HashMap<CreatureType, ArrayList<Creature>> creaturesByType = new HashMap<>();
        for (Creature creature : creatures) {
            if (creaturesByType.containsKey(creature.getType())) {
                creaturesByType.get(creature.getType()).add(creature);
            } else {
                creaturesByType.put(creature.getType(), new ArrayList<>());
            }
        }
        return creaturesByType;
    }

    public ArrayList<Creature> getHumans() {
        return getCreaturesByType().get(CreatureType.HUMANO);
    }

    public ArrayList<Creature> getZombies() {
        return getCreaturesByType().get(CreatureType.ZOMBIE);
    }

    private boolean positionOcupiedByCreature(Coord coord) {
        if (positionIsEmpty(coord)) {
            return false;
        }
        return grid[coord.getX()][coord.getY()].split("\\|").length == 4; // Faco split com "|" e verifico se o numero de partes e' igual a 4
    }


    private boolean positionOcupiedByCreature(Coord coord, CreatureType creatureType) { //Verifica se a posicao esta ocupada pela criatura especificada
        if (!positionOcupiedByCreature(coord)) {
            return false;
        }
        String creatureInfo = grid[coord.getX()][coord.getY()];
        Creature creature = getCreatureByInfoString(creatureInfo);
        if (creature == null) {
            return false;
        }
        return creature.getType() == creatureType;
    }

    private boolean positionOcupiedByEquipment(Coord coord) {
        if (positionIsEmpty(coord)) {
            return false;
        }
        return grid[coord.getX()][coord.getY()].split("\\|").length == 2;
    }

    private boolean positionOcupiedByEquipment(Coord coord, EquipmentType equipmentType) {
        if (!positionOcupiedByEquipment(coord)) {
            return false;
        }
        String equipmentInfo = grid[coord.getX()][coord.getY()];
        Equipment equipment = getEquipmentByInfoString(equipmentInfo);
        if (equipment == null) {
            return false;
        }
        return equipment.getType() == equipmentType;
    }

    private boolean positionIsEmpty(Coord coord) {
        return grid[coord.getX()][coord.getY()] == null;
    }

    private Creature getCreatureByInfoString(String creatureInfo) {
        for (Creature creature : creatures) {
            if (creature.getInfoAsString().equals(creatureInfo)) {
                return creature;
            }
        }
        return null;
    }

    private Equipment getEquipmentByInfoString(String equipmentInfo) {
        for (Equipment equipment : equipments) {
            if (equipment.getInfoAsString().equals(equipmentInfo)) {
                return equipment;
            }
        }
        return null;
    }

    public String getSquareInfo(Coord coord) {
        if (positionIsEmpty(coord)) {
            return "";
        }
        if (positionOcupiedByCreature(coord, CreatureType.HUMANO)) {
            Creature creature = getCreatureByInfoString(grid[coord.getX()][coord.getY()]);
            if (creature == null) {
                return "";
            }
            return String.format("H:%d", creature.getId());
        }

        if (positionOcupiedByCreature(coord, CreatureType.ZOMBIE)) {
            Creature creature = getCreatureByInfoString(grid[coord.getX()][coord.getY()]);
            if (creature == null) {
                return null;
            }
            return String.format("Z:%d", creature.getId());
        }

        if (positionOcupiedByEquipment(coord)) {
            Equipment equipment = getEquipmentByInfoString(grid[coord.getX()][coord.getY()]);
            if (equipment == null) {
                return null;
            }
            return String.format("E:%d", equipment.getId());
        }
        return "";
    }

    public Creature getCreatureByID(int creatureID) {
        for (Creature creature : creatures) {
            if (creature.getId() == creatureID) {
                return creature;
            }
        }
        return null;
    }

    public Equipment getEquipmentByID(int equipmentID) {
        for (Equipment equipment : equipments) {
            if (equipment.getId() == equipmentID) {
                return equipment;
            }
        }
        return null;
    }


    /* Validacoes */

    public boolean coordIsOutOfBounds(Coord coord) {
        return coord.getX() >= rows || coord.getY() >= cols;
    }

    public boolean isLegalMove(Coord origin, Coord destination) {
        if (coordIsOutOfBounds(origin) || coordIsOutOfBounds(destination)) {
            return false;
        }
        int horizontalDistance = Math.abs(destination.getY() - origin.getY());
        int verticalDistance  = Math.abs(destination.getX() - origin.getX());

        return ((verticalDistance == 0 && horizontalDistance == 1) || // Pode andar na horizontal uma casa
                (horizontalDistance == 0 && verticalDistance == 1)); // Pode andar na vertical uma casa
    }
}
