package pt.ulusofona.lp2.thenightofthelivingdeisi;

import java.util.ArrayList;

public class GameSession {
    private String[][] grid; // Decidi criar uma matrix de Strings porque não posso criar tabuleiro que seja ao mesmo tempo Creature e Equipment
    private ArrayList<Creature> creatures;
    private ArrayList<Equipment> equipments;
    int rows;
    int cols;
    int turnCounter;
    int turn;
    boolean isDay;

    public GameSession(int rows, int cols, boolean isDay, ArrayList<Creature> creatures, ArrayList<Equipment> equipments, int turn) {
        this.rows = rows;
        this.cols = cols;
        this.isDay = isDay;
        this.creatures = creatures;
        this.equipments = equipments;
        this.turn = turn;
        this.turnCounter = 0;
        grid = new String[rows][cols];
    }

    public String[][] getGrid() {
        return grid;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
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

}
