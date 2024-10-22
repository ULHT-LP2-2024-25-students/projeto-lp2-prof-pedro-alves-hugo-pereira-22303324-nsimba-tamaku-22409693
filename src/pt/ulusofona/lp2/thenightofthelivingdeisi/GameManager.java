package pt.ulusofona.lp2.thenightofthelivingdeisi;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class GameManager {
    GameSession gameSession;
    public GameManager() {
    }

    private int[] getDimentionFromFileLine(String line) {
        String[] parts = line.split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private Creature getCreatureFromFileLine(String line) {
        String[] parts = line.split(":");
        int id = Integer.parseInt(parts[0].trim());
        TypeCreature typeCreature = Objects.equals(parts[1].trim(), "1") ? TypeCreature.HUMANO : TypeCreature.ZOMBIE;
        String name = parts[2].trim();
        int row = Integer.parseInt(parts[3].trim());
        int col = Integer.parseInt(parts[4].trim());
        return new Creature(name, id, typeCreature, row, col, null);
    }

    private Equipment getEquipmentFromFileLine(String line) {
        String newLine = line.substring(1); //Saltar o primeiro caractere que e' um "-"
        String[] parts = newLine.split(":");
        int id = Integer.parseInt(parts[0].trim());
        TypeEquipment typeEquipment = Objects.equals(parts[1].trim(), "1") ? TypeEquipment.ESPADA : TypeEquipment.ESCUDO;
        int row = Integer.parseInt(parts[2].trim());
        int col = Integer.parseInt(parts[3].trim());
        return new Equipment(id, typeEquipment, row, col, null);
    }

    public boolean loadGame(File file) {
        /* Informacoes que espero obter do arquivo */
        int row, col;
        int turn;
        int nCreatures, nEquipments;
        ArrayList<Creature> creatures = new ArrayList<>();
        ArrayList<Equipment> equipments = new ArrayList<>();
        /* leitura do ficheiro */
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            /* obter dimensao do tabuleiro */
            line = reader.readLine();
            row = getDimentionFromFileLine(line)[0];
            col = getDimentionFromFileLine(line)[1];

            /* obter turno */
            line = reader.readLine();
            turn = Integer.parseInt(line);

            /* obter numero de criaturas */
            line = reader.readLine();
            nCreatures = Integer.parseInt(line);

            /* obter as proximas n Criaturas */
            for (int i = 0; i < nCreatures; i++) {
                line = reader.readLine();
                creatures.add(getCreatureFromFileLine(line));
            }

            /* obter o numero de equipamentos */
            line = reader.readLine();
            nEquipments = Integer.parseInt(line);

            /* obter os proximos n Equipamentos */
            for (int i = 0; i < nEquipments; i++) {
                line = reader.readLine();
                equipments.add(getEquipmentFromFileLine(line));
            }
            gameSession = new GameSession(row, col, true, creatures, equipments, turn);
            return true; // Retorna true se a leitura for bem-sucedida
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Retorna false se ocorrer algum erro
        }
    }

    public int[] getWorldSize() {
        return new int[2];
    }

    public int getInitialTeamId() {
        return 0;
    }

    public int getCurrentTeamId() {
        return 0;
    }

    public boolean isDay() {
        return false;
    }

    public String getSquareInfo(int x, int y) {
        return "";
    }

    public String[] getCreatureInfo(int id) {
        return new String[0];
    }

    public String getCreatureInfoAsString(int id) {
        return "";
    }

    public String[] getEquipmentInfo(int id) {
        return new String[0];
    }

    public String getEquipmentInfoAsString(int id) {
        return "";
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return false;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        return false;
    }

    public boolean gameIsOver() {
        return false;
    }

    public ArrayList<String> getSurvivors() {
        return null;
    }

    public JPanel getCreditsPanel() {
        return null;
    }

    public HashMap<String, String> customizeBoard() {
        return null;
    }
}
