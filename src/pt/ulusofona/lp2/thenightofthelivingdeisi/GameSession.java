package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.Objects;

public class GameSession {
    private Board board;
    int turnCounter;
    int shift;
    boolean isDay;


    public GameSession() {
        turnCounter = 0;
        shift = 0;
        isDay = true;
    }


    public GameSession(int rows, int cols, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, ArrayList<SafeHeavenDoor> safeHeavenDoors, int shift) {
        this.shift = shift;
        this.turnCounter = 0;
        this.board = new Board(rows, cols, creatures, equipments, safeHeavenDoors);
        this.assembleBoard();
    }



    public int getTurnCounter() {
        return this.turnCounter;
    }

    public boolean isDay() {
        return this.isDay;
    }

    public int getShift() {
        return this.shift;
    }

    public void changeTurn() {
        this.turnCounter++;
        this.shift = this.shift == 10 ? 20 : 10;
        if (this.turnCounter % 2 == 0) { //A cada duas jogas trocar o turno
            this.isDay = !this.isDay;
        }
    }

    public void assembleBoard() {
        this.board.assemblePieces();
    }

    public int[] getBoardSize() {
        return new int[]{this.board.getRows(), this.board.getCols()};
    }

    public String getSquareInfo(int row, int col) {
        return this.board.getSquareInfo(new Coord(row, col));
    }

    public String[] getCreatureInfo(int id) {
        String[] creatureInfo = this.board.getCreatureByID(id).getInfo();
        if (Objects.equals(creatureInfo[6], "null")) {
            creatureInfo[6] = null;
        }
        return creatureInfo;
    }

    public String getCreatureInfoAsString(int id) {
        return this.board.getCreatureByID(id).getInfoAsString();
    }

    public String[] getEquipmentInfo(int id) {
        Equipment equipment = this.board.getEquipmentByID(id);
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
        Creature creature = this.board.getCreatureByID(id);
        if (!creature.hasEquipment()) {
            return false;
        }
        return creature.getEquipment().getAssocietedTypeNumber() == equipmentType;
    }

    public String getEquipmentInfoAsString(int id) {
        Equipment equipment = this.board.getEquipmentByID(id);

        return equipment.isCaptured() || equipment.isDestroyed() ? null : equipment.getInfoAsString();
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        Coord origin = new Coord(xO, yO);
        Coord destination = new Coord(xD, yD);
        if (this.board.moveElement(origin, destination, this.shift, this.isDay)) {
            changeTurn();
            return true;
        }
        return false;
    }

    public ArrayList<String> getSurvivors() {
        ArrayList<String> survivorsInfo = new ArrayList<>();
        survivorsInfo.add("Nr. turnos termindos:");
        survivorsInfo.add(this.turnCounter + "");
        survivorsInfo.add("");
        survivorsInfo.add("OS VIVOS");
        for (Creature human : this.board.getHumans()) {
            survivorsInfo.add(String.format("%d %s", human.getId(), human.getName()));
        }
        survivorsInfo.add("");
        survivorsInfo.add("OS OUTROS");
        for (Creature zombie : this.board.getZombies()) {
            survivorsInfo.add(String.format("%d %s", zombie.getId(), zombie.getName()));
        }
        survivorsInfo.add("-----");
        return survivorsInfo;
    }

    public boolean gameIsOver() {
        return this.turnCounter == 1200;
    }
}
