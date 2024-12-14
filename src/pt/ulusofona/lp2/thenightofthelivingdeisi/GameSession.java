package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.Objects;

public class GameSession {
    private Board board;
    int turnCounter;
    int shift;
    boolean isDay;

    private static GameSession instance;


    private GameSession() {
        turnCounter = 0;
        shift = 0;
        isDay = true;
    }

    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
        instance = new GameSession();
    }

    public void setGame(int rows, int cols, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, ArrayList<SafeHeavenDoor> safeHeavenDoors, int shift) {
        if (instance != null) {
            instance.shift = shift;
            instance.turnCounter = 0;
            instance.board = new Board(rows, cols, creatures, equipments, safeHeavenDoors);
            instance.assembleBoard();
        }
    }


    public int getTurnCounter() {
        return instance.turnCounter;
    }

    public boolean isDay() {
        return instance.isDay;
    }

    public int getShift() {
        return instance.shift;
    }

    public void changeTurn() {
        instance.turnCounter++;
        instance.shift = instance.shift == 10 ? 20 : 10;
        if (instance.turnCounter % 2 == 0) { //A cada duas jogas trocar o turno
            instance.isDay = !instance.isDay;
        }
    }

    public void assembleBoard() {
        instance.board.assemblePieces();
    }

    public int[] getBoardSize() {
        return new int[]{instance.board.getRows(), instance.board.getCols()};
    }

    public String getSquareInfo(int row, int col) {
        return instance.board.getSquareInfo(new Coord(row, col));
    }

    public String[] getCreatureInfo(int id) {
        String[] creatureInfo = instance.board.getCreatureByID(id).getInfo();
        if (Objects.equals(creatureInfo[6], "null")) {
            creatureInfo[6] = null;
        }
        return creatureInfo;
    }

    public String getCreatureInfoAsString(int id) {
        Creature creature = instance.board.getCreatureByID(id);
        return creature != null ? creature.getInfoAsString() : null;
    }

    public String[] getEquipmentInfo(int id) {
        Equipment equipment = instance.board.getEquipmentByID(id);
        String[] equipmentInfo = equipment.getInfo();

        if (Objects.equals(equipmentInfo[4], "null")) {
            equipmentInfo[5] = null;
        }
        if (equipment.isDestroyed() || equipment.isCaptured()) {
            return null;
        }
        return equipmentInfo;
    }

    public boolean creatureHasEquipment(int id, int equipmentType) {
        Creature creature = instance.board.getCreatureByID(id);
        if (!creature.hasEquipment()) {
            return false;
        }
        return creature.getEquipment().getAssocietedTypeNumber() == equipmentType;
    }

    public String getEquipmentInfoAsString(int id) {
        Equipment equipment = instance.board.getEquipmentByID(id);

        return equipment.isCaptured() || equipment.isDestroyed() ? null : equipment.getInfoAsString();
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        Coord origin = new Coord(xO, yO);
        Coord destination = new Coord(xD, yD);
        if (instance.board.moveElement(origin, destination, instance.shift, instance.isDay)) {
            changeTurn();
            return true;
        }
        return false;
    }

    public ArrayList<Integer> getCreatureIDsInSaveHeaven() {
        return instance.board.getCreatureIDsInSaveHeaven();
    }

    public ArrayList<String> getSurvivors() {
        ArrayList<String> survivorsInfo = new ArrayList<>();
        survivorsInfo.add("Nr. de turnos terminados:");
        survivorsInfo.add(instance.turnCounter + "");
        survivorsInfo.add("");
        survivorsInfo.add("OS VIVOS");
        for (Creature human : instance.board.getHumans()) {
            survivorsInfo.add(String.format("%d %s", human.getId(), human.getName()));
        }
        survivorsInfo.add("");
        survivorsInfo.add("OS OUTROS");
        for (Creature zombie : instance.board.getZombies()) {
            survivorsInfo.add(String.format("%d (antigamente conhecido como %s)", zombie.getId(), zombie.getName()));
        }
        survivorsInfo.add("-----");
        return survivorsInfo;
    }

    public boolean gameIsOver() {
        if (turnCounter >= 8) {
            if (instance.board.getTransformations() == 0 && instance.board.getZombieKills() == 0) {
                return true;
            }
        }
        return instance.board.isOnlyOneTeamPlaying();
    }
}