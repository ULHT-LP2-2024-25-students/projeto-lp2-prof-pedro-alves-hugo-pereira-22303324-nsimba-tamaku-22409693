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
    private int transformations = 0;
    private int zombieKills = 0;


    public void reset() {
        this.rows = 0;
        this.cols = 0;
        this.grid = null;
        this.creatures.clear();
        this.equipments.clear();
        this.safeHeavenDoors.clear();
        this.creatureIDsInSaveHeaven.clear();
        this.transformations = 0;
        this.zombieKills = 0;
    }


    public ArrayList<Integer> getCreatureIDsInSaveHeaven() {
        return creatureIDsInSaveHeaven;
    }

    private final ArrayList<Integer> creatureIDsInSaveHeaven = new ArrayList<>();

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

    private boolean positionOcupiedBySafeHeavenDoor(Coord coord) { //Verifica se a posicao esta ocupada pela criatura especificada
        if (positionIsEmpty(coord)) {
            return false;
        }
        BoardPiece boardPiece = grid[coord.getX()][coord.getY()];
        return boardPiece.leadsToSaveHeaven();
    }

    private void putCreatureInSaveHeaven(Creature creature) {
        if (!creature.isHuman()) {
            throw new UnsupportedOperationException("Zombies nao entram no safe heaven");
        }
        creatureIDsInSaveHeaven.add(creature.getId());
    }

    private boolean positionOcupiedByEquipment(Coord coord, EquipmentType equipmentType) {
        if (!positionOcupiedByEquipment(coord)) {
            return false;
        }
        Equipment equipment = (Equipment) grid[coord.getX()][coord.getY()];
        return equipment.getType() == equipmentType;
    }

    private boolean positionIsEmpty(Coord coord) {
        if (coordIsOutOfBounds(coord)) {
            throw new IndexOutOfBoundsException("Coord is out of bounds");
        }
        return grid[coord.getX()][coord.getY()] == null;
    }

    private void emptyCell(Coord coord) {
        grid[coord.getX()][coord.getY()] = null;
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
        if (positionIsEmpty(coord) || coordIsOutOfBounds(coord)) {
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
        return coord.getX() >= rows || coord.getY() >= cols || coord.getX() < 0 || coord.getY() < 0;
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

    public boolean isLegalMove(Coord origin, Coord dest, Creature creature, int shift, boolean isDay) {
        int distance = getDistance(origin, dest);
        boolean creatureMovesObliqual = creature.movesObliqual(distance);
        boolean creatureMovesRectilinear = creature.movesRectilinear(distance);

        boolean creatureCanMoveRectilinear = canMoveRectilinear(origin, dest, distance);
        boolean creatureCanMoveObliqual = canMoveObliqual(origin, dest, distance);

        if (!isInTheCorrectShift(origin, shift)) {
            return false;
        }

        if ((creature.onlyMovesInTheMorning() && !isDay) || (creature.onlyMovesAtNight() && isDay)) {
            return false;
        }

        if (creatureMovesRectilinear && creatureCanMoveRectilinear) {
            return true;
        }
        return creatureMovesObliqual && creatureCanMoveObliqual;
    }

    private boolean isInTheCorrectShift(Coord origin, int shift) {
        if (shift == 20 && positionOcupiedByCreature(origin, Team.ALIVES)) {
            return true;
        }
        return shift == 10 && positionOcupiedByCreature(origin, Team.ZOMBIES);
    }


    public boolean moveElement(Coord origin, Coord dest, int shift, boolean isDay) throws UnsupportedOperationException {
        if (!positionOcupiedByCreature(origin)) {
            return false;
        }

        Creature creature = (Creature) grid[origin.getX()][origin.getY()];

        if (!isLegalMove(origin, dest, creature, shift, isDay)) {
            return false;
        }

        if (positionOcupiedBySafeHeavenDoor(dest)) {
            putCreatureInSaveHeaven(creature);
            creature.save();
            emptyCell(origin);
            return true;
        }

        if (positionOcupiedByCreature(dest)) {
            Creature creatureTobeAttacked = (Creature) grid[dest.getX()][dest.getY()];
            if (creature.getTeam() == creatureTobeAttacked.getTeam()) {
                return false;
            }

            if (creature.isHuman() && !creature.hasEquipment()) {
                return false;
            }

            if (creatureTobeAttacked.isHuman() && !creatureTobeAttacked.hasEquipment()) {
                creatureTobeAttacked.transform();
                transformations++;
                return true;
            }

            if (creatureTobeAttacked.isHuman() && creatureTobeAttacked.hasEquipment()) {
                Equipment equipment = creatureTobeAttacked.getEquipment();
                if (equipment.hasAmo()) {
                    creatureTobeAttacked.useEquipment();
                } else {
                    creatureTobeAttacked.transform();
                    transformations++;
                }
                return true;
            }

            if (creature.isHuman() && creature.hasEquipment()) {
                Equipment equipment = creature.getEquipment();
                if (equipment.isDefensive()) {
                    return false;
                }
                if (equipment.isOfensive() && !equipment.hasAmo()) {
                    return false;
                }
                if (equipment.hasAmo() && equipment.isOfensive()) {
                    placePiece(dest, creature);
                    creature.useEquipment();
                    creature.changePosition(dest);
                    emptyCell(origin);
                    creature.die();
                    zombieKills++;
                    return true;
                }
            }
        }

        if (positionOcupiedByEquipment(dest)) {
            handleEquipmentInteraction(creature, origin, dest);
            return true;
        } else if (!positionOcupiedByEquipment(dest)) {
            handleMovementWithoutEquipment(creature, origin, dest);
            return true;
        }


        return false;
    }

    private void handleEquipmentInteraction(Creature creature, Coord origin, Coord dest) {
        Equipment newEquipment = (Equipment) grid[dest.getX()][dest.getY()];

        if (creature.getTeam() == Team.ALIVES) {
            handleAliveTeamWithEquipment(creature, origin, dest, newEquipment);
        } else {
            creature.destroy(newEquipment);
            placePiece(dest, creature);
            creature.changePosition(dest);
            emptyCell(origin);
        }
    }

    private void handleAliveTeamWithEquipment(Creature creature, Coord origin, Coord dest, Equipment newEquipment) {
        if (creature.hasEquipment()) {
            Equipment oldEquipment = creature.getEquipment();
            oldEquipment.setAsUncaptured();
            creature.equip(newEquipment);
            placePiece(dest, creature);
            placePiece(origin, oldEquipment);
            creature.changePosition(dest);
            oldEquipment.changePosition(origin);
        } else {
            creature.equip(newEquipment);
            emptyCell(origin);
            placePiece(dest, creature);
            creature.changePosition(dest);
        }
    }

    private void handleMovementWithoutEquipment(Creature creature, Coord origin, Coord dest) {
        if (creature.hasEquipment()) {
            handleCreatureWithEquipment(creature, origin, dest);
        } else if (creature.getTeam() == Team.ZOMBIES) {
            placePiece(dest, creature);
            emptyCell(origin);
        } else {
            placePiece(dest, creature);
            emptyCell(origin);
        }
        creature.changePosition(dest);
    }

    private void handleCreatureWithEquipment(Creature creature, Coord origin, Coord dest) {
        if (creature.carriesEquipment()) {
            creature.changePosition(dest);
            placePiece(dest, creature);
            emptyCell(origin);
        } else {
            Equipment oldEquipment = creature.getEquipment();
            oldEquipment.setAsUncaptured();
            creature.unquip();
            placePiece(dest, creature);
            placePiece(origin, oldEquipment);
            oldEquipment.changePosition(origin);
        }
    }

    public ArrayList<Creature> creaturesPlaying() {
        ArrayList<Creature> activeCreatures = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                BoardPiece boardPiece = grid[i][j];
                if (positionIsEmpty(new Coord(i, j))) {
                    continue;
                }
                if (boardPiece.moves()) {
                    Creature creature = (Creature) boardPiece;
                    activeCreatures.add(creature);
                }
            }
        }
        return activeCreatures;
    }


    public boolean isOnlyOneTeamPlaying() {
        ArrayList<Creature> humans = new ArrayList<>();
        ArrayList<Creature> zombies = new ArrayList<>();
        ArrayList<Creature> creaturesPlaying = creaturesPlaying();
        for (Creature creature : creaturesPlaying) {
            if (creature.isHuman()) {
                humans.add(creature);
            } else {
                zombies.add(creature);
            }
        }
        return humans.size() == creaturesPlaying.size() || creaturesPlaying.size() == zombies.size();
    }

    public int getZombieKills() {
        return zombieKills;
    }

    public int getTransformations() {
        return transformations;
    }
}
