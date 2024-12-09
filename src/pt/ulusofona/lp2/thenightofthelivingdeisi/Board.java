package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    int rows;
    int cols;
    BoardPiece[][] grid;
    private final ArrayList<Creature> creatures;
    private final ArrayList<Equipment> equipments;
    private final ArrayList<SafeHeavenDoor> safeHeavenDoors;

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

    public ArrayList<BoardPiece> getPieces() {
        ArrayList<BoardPiece> pieces = new ArrayList<>();
        pieces.addAll(creatures);
        pieces.addAll(equipments);
        pieces.addAll(safeHeavenDoors);
        return pieces;
    }

    public void assemblePieces() {
        for (BoardPiece boardPiece : getPieces()) {
            if (positionIsEmpty(boardPiece.getCoord())) { //Verifica se a coordenada esta vazia
                placePiece(boardPiece.getCoord(), boardPiece);
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
        BoardPiece boardPiece = grid[coord.getX()][coord.getY()];
        return boardPiece.moves();
    }

    private boolean positionOcupiedByEquipment(Coord coord) {
        if (positionIsEmpty(coord)) {
            return false;
        }
        BoardPiece boardPiece = grid[coord.getX()][coord.getY()];
        return !boardPiece.moves();
    }


    private boolean positionOcupiedByCreature(Coord coord, Team team) { //Verifica se a posicao esta ocupada pela criatura especificada
        if (!positionOcupiedByCreature(coord)) {
            return false;
        }
        Creature creature = (Creature) grid[coord.getX()][coord.getY()];
        return creature.getTeam() == team;
    }

    private boolean positionOcupiedByEquipment(Coord coord, EquipmentType equipmentType) {
        if (!positionOcupiedByEquipment(coord)) {
            return false;
        }
        Equipment equipment = (Equipment) grid[coord.getX()][coord.getY()];
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

    public boolean canMoveRectilinear(Coord origin, Coord destination, int distance) {
        if (coordIsOutOfBounds(origin) || coordIsOutOfBounds(destination)) {
            return false;
        }
        int horizontalDistance = Math.abs(destination.getY() - origin.getY());
        int verticalDistance = Math.abs(destination.getX() - origin.getX());

        return ((verticalDistance == 0 && horizontalDistance == distance) || // Pode andar na horizontal distance casas
                (horizontalDistance == 0 && verticalDistance == distance)); // Pode andar na vertical distance casas
    }

    public boolean canMoveObliqual(Coord origin, Coord destination, int distance) {
        if (coordIsOutOfBounds(origin) || coordIsOutOfBounds(destination)) {
            return false;
        }
        int horizontalDistance = Math.abs(destination.getY() - origin.getY());
        int verticalDistance = Math.abs(destination.getX() - origin.getX());

        return ((verticalDistance == horizontalDistance && horizontalDistance == distance)); // Pode andar na vertical distance casas
    }

    public int getDistance(Coord origin, Coord destination) {
        int horizontalDistance = Math.abs(destination.getY() - origin.getY());
        int verticalDistance = Math.abs(destination.getX() - origin.getX());

        return Math.max(horizontalDistance, verticalDistance);
    }

    public boolean isLegalMove(Coord origin, Coord dest, Creature creature) {
        int distance = getDistance(origin, dest);
        boolean creatureMovesObliqual = creature.movesObliqual(distance);
        boolean creatureMovesRectilinear = creature.movesRectilinear(distance);

        boolean creatureCanMoveRectilinear = canMoveRectilinear(origin, dest, distance);
        boolean creatureCanMoveObliqual = canMoveObliqual(origin, dest, distance);

        if (creatureMovesRectilinear && creatureCanMoveRectilinear) {
            return true;
        }
        return creatureMovesObliqual && creatureCanMoveObliqual;
    }


    public boolean moveElement(int xO, int yO, int xD, int yD, int shift) {
        Coord origin = new Coord(xO, yO);
        Coord dest = new Coord(xD, yD);

        if (positionOcupiedByCreature(dest)) {
            return false;
        }

        if (shift == 20) { //Vez do humano
            if (!positionOcupiedByCreature(origin, Team.ALIVES)) {
                return false;
            }
            Creature human = (Creature) (grid[origin.getX()][origin.getY()]);
            assert human != null; //Temos a certeza que nao é null mas verificamos na mesma

            if (!isLegalMove(origin, dest, human)) {
                return false;
            }

            if ((human.hasEquipment() && positionOcupiedByEquipment(dest))) {
                return false;
            }
            if (!human.hasEquipment() && positionOcupiedByEquipment(dest)) {
                Equipment equipment = (Equipment) (grid[dest.getX()][dest.getY()]);
                assert equipment != null;
                human.equip(equipment);
            }
            grid[xO][yO] = null;
            human.changePosition(dest.getX(), dest.getY());
            placePiece(dest, human);
            return true;
        }
        if (!positionOcupiedByCreature(origin, Team.ZOMBIES)) {
            return false;
        }
        Creature zombie = (Creature) (grid[origin.getX()][origin.getY()]);
        assert zombie != null;
        if (!isLegalMove(origin, dest, zombie)) {
            return false;
        }
        if (positionOcupiedByEquipment(dest)) {
            Equipment equipment = (Equipment) (grid[dest.getX()][dest.getY()]);
            assert equipment != null;
            zombie.destroy(equipment);
        }
        grid[xO][yO] = null;
        zombie.changePosition(dest.getX(), dest.getY());
        placePiece(dest, zombie);
        return true;
    }
}
