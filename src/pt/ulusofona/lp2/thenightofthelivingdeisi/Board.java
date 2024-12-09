package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    int rows;
    int cols;
    BoardPiece[][] grid;
    private ArrayList<Creature> creatures;
    private ArrayList<Equipment> equipments;
    private ArrayList<SafeHeavenDoor> safeHeavenDoors;

    public Board(int rows, int cols, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, ArrayList<SafeHeavenDoor> safeHeavenDoors) {
        this.rows = rows;
        this.cols = cols;
        this.creatures = creatures;
        this.equipments = equipments;
        this.safeHeavenDoors = safeHeavenDoors;
        grid = new BoardPiece[rows][cols];
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
                placePiece(creature.getCoord(), creature);
            }
        }

        for (Equipment equipment : equipments) {
            if (positionIsEmpty(equipment.getCoord())) {
                placePiece(equipment.getCoord(), equipment);
            }
        }

        for (SafeHeavenDoor safeHeavenDoor : safeHeavenDoors) {
            if (positionIsEmpty(safeHeavenDoor.getCoord())) {
                placePiece(safeHeavenDoor.getCoord(), safeHeavenDoor);
            }
        }
    }

    private void placePiece(Coord coord, BoardPiece piece) {
        grid[coord.getX()][coord.getY()] = piece;
    }

    public HashMap<Team, ArrayList<Creature>> getCreaturesByTeam() {
        HashMap<Team, ArrayList<Creature>> creaturesByType = new HashMap<>();
        for (Creature creature : creatures) {
            if (creaturesByType.containsKey(creature.getTeam())) {
                creaturesByType.get(creature.getTeam()).add(creature);
            } else {
                creaturesByType.put(creature.getTeam(), new ArrayList<>());
                creaturesByType.get(creature.getTeam()).add(creature);
            }
        }
        return creaturesByType;
    }

    public ArrayList<Creature> getHumans() {
        return getCreaturesByTeam().get(Team.ALIVES);
    }

    public ArrayList<Creature> getZombies() {
        return getCreaturesByTeam().get(Team.ZOMBIES);
    }

    private boolean positionOcupiedByCreature(Coord coord) {
        if (positionIsEmpty(coord)) {
            return false;
        }
//        return grid[coord.getX()][coord.getY()].split("\\|").length == 4; // Faco split com "|" e verifico se o numero de partes e' igual a 4
        return true;
    }


    private boolean positionOcupiedByCreature(Coord coord, Team team) { //Verifica se a posicao esta ocupada pela criatura especificada
//        if (!positionOcupiedByCreature(coord)) {
//            return false;
//        }
//        String creatureInfo = grid[coord.getX()][coord.getY()];
//        Creature creature = getCreatureByInfoString(creatureInfo);
//        if (creature == null) {
//            return false;
//        }
//        return creature.getTeam() == team;
        return true;
    }

    private boolean positionOcupiedByEquipment(Coord coord) {
//        if (positionIsEmpty(coord)) {
//            return false;
//        }
//        return grid[coord.getX()][coord.getY()].split("\\|").length == 2;
        return true;
    }

    private boolean positionOcupiedByEquipment(Coord coord, EquipmentType equipmentType) {
//        if (!positionOcupiedByEquipment(coord)) {
//            return false;
//        }
//        String equipmentInfo = grid[coord.getX()][coord.getY()];
//        Equipment equipment = getEquipmentByInfoString(equipmentInfo);
//        if (equipment == null) {
//            return false;
//        }
//        return equipment.getType() == equipmentType;
        return false;
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
        BoardPiece boardPiece = grid[coord.getX()][coord.getY()];
        return boardPiece.getResumedInfo();
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
        int verticalDistance = Math.abs(destination.getX() - origin.getX());

        return ((verticalDistance == 0 && horizontalDistance == 1) || // Pode andar na horizontal uma casa
                (horizontalDistance == 0 && verticalDistance == 1)); // Pode andar na vertical uma casa
    }

    public boolean moveElement(int xO, int yO, int xD, int yD, int shift) {
//        Coord origin = new Coord(xO, yO);
//        Coord dest = new Coord(xD, yD);
//
//        if (!isLegalMove(origin, dest)) {
//            return false;
//        }
//
//        if (positionOcupiedByCreature(dest)) {
//            return false;
//        }
//
//        if (shift == 1) { //Vez do humano
//            if (!positionOcupiedByCreature(origin, Team.ALIVES)) {
//                return false;
//            }
//            Creature human = getCreatureByInfoString(grid[origin.getX()][origin.getY()]);
//            assert human != null; //Temos a certeza que nao é null mas verificamos na mesma
//            if ((human.hasEquipment() && positionOcupiedByEquipment(dest))) {
//                return false;
//            }
//            if (!human.hasEquipment() && positionOcupiedByEquipment(dest)) {
//                Equipment equipment = getEquipmentByInfoString(grid[dest.getX()][dest.getY()]);
//                assert equipment != null;
//                human.equip(equipment);
//            }
//            grid[xO][yO] = null;
//            human.changePosition(dest.getX(), dest.getY());
//            placePiece(dest, human.getInfoAsString());
//            return true;
//        }
//        if (!positionOcupiedByCreature(origin, Team.ZOMBIES)) {
//            return false;
//        }
//        Creature zombie = getCreatureByInfoString(grid[origin.getX()][origin.getY()]);
//        assert zombie != null;
//        if (positionOcupiedByEquipment(dest)) {
//            Equipment equipment = getEquipmentByInfoString(grid[dest.getX()][dest.getY()]);
//            assert equipment != null;
//            zombie.destroy(equipment);
//        }
//        grid[xO][yO] = null;
//        zombie.changePosition(dest.getX(), dest.getY());
//        placePiece(dest, zombie.getInfoAsString());
        return true;
    }
}
