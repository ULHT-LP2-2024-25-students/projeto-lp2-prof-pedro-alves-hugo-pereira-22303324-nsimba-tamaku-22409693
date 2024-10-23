package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;
import java.util.HashMap;

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
        shift = shift == 1 ? 0 : shift;
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
}
