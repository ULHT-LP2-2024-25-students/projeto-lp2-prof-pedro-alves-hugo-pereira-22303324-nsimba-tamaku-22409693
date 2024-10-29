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
        isDay = false;
    }

    public GameSession(int rows, int cols, boolean isDay, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, int shift) {
        this.isDay = isDay;
        this.shift = shift;
        this.turnCounter = 0;
        board = new Board(rows, cols, creatures, equipments);
    }


    public int getTurnCounter() {
        return turnCounter;
    }

    public boolean isDay() {
        return isDay;
    }

    public int getShift() {
        return shift;
    }

    public void changeTurn() {
        turnCounter++;
        shift = shift == 1 ? 0 : 1;
        if (turnCounter % 2 == 0) { //A cada duas jogas trocar o turno
            isDay = !isDay;
        }
    }

    public void assembleBoard() {
        board.assemblePieces();
    }

    public int[] getBoardSize() {
        return new int[]{board.getRows(), board.getCols()};
    }

    public String getSquareInfo(int row, int col) {
        return board.getSquareInfo(new Coord(row, col));
    }

    public String[] getCreatureInfo(int id) {
        String[] creatureInfo = board.getCreatureByID(id).getInfo();
        if (Objects.equals(creatureInfo[5], "null")) {
            creatureInfo[5] = null;
        }
        return creatureInfo;
    }

    public String getCreatureInfoAsString(int id) {
        return board.getCreatureByID(id).getInfoAsString();
    }

    public String[] getEquipmentInfo(int id) {
        String[] equipmentInfo = board.getEquipmentByID(id).getInfo();
        if (Objects.equals(equipmentInfo[4], "null")) {
            equipmentInfo[5] = null;
        }
        return equipmentInfo;
    }

    public String getEquipmentInfoAsString(int id) {
        return board.getEquipmentByID(id).getInfoAsString();
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        if (board.moveElement(xO, yO, xD, yD, shift)) {
            changeTurn();
            return true;
        }
        return false;
    }
}
