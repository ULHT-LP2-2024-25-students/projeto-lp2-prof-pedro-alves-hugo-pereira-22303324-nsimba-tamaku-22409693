package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class GameSession {
    private ArrayList<Creature> creatures;
    private ArrayList<Equipment> equipments;
    private Board board;
    int turnCounter;
    int turn;
    boolean isDay;

    public GameSession(int rows, int cols, boolean isDay, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, int turn) {
        this.isDay = isDay;
        this.creatures = creatures;
        this.equipments = equipments;
        this.turn = turn;
        this.turnCounter = 0;
        board = new Board(rows, cols);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public boolean isDay() {
        return isDay;
    }

    public int getTurn() {
        return turn;
    }

    public void changeTurn() {
        turnCounter++;
        if (turnCounter > 1) {
            turnCounter = 0;
            turn = turn == 1 ? 0 : turn;
        }
    }

    public int[] getBoardSize() {
        return new int[]{board.getRows(), board.getCols()};
    }

}
