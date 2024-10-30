package pt.ulusofona.lp2.thenightofthelivingdeisi;

import javax.swing.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameManager {
    private GameSession gameSession;

    public GameManager() {
        this.gameSession = new GameSession();
    }

    private int[] parseDimensions(String line) {
        String[] parts = line.split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private Creature parseCreature(String line) {
        String[] parts = line.split(":");
        int id = Integer.parseInt(parts[0].trim());
        CreatureType type = parts[1].trim().equals("1") ? CreatureType.HUMANO : CreatureType.ZOMBIE;
        String name = parts[2].trim();
        int col = Integer.parseInt(parts[3].trim());
        int row = Integer.parseInt(parts[4].trim());
        return new Creature(name, id, type, row, col, null);
    }

    private Equipment parseEquipment(String line) {
        String[] parts = line.substring(1).split(":");
        int id = Integer.parseInt(parts[0].trim()) * -1;
        EquipmentType type = parts[1].trim().equals("1") ? EquipmentType.ESPADA : EquipmentType.ESCUDO;
        int col = Integer.parseInt(parts[2].trim());
        int row = Integer.parseInt(parts[3].trim());
        return new Equipment(id, type, row, col, null);
    }

    public boolean loadGame(File file) {
        ArrayList<Creature> creatures = new ArrayList<>();
        ArrayList<Equipment> equipments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int[] dimensions = parseDimensions(reader.readLine());
            int turn = Integer.parseInt(reader.readLine());
            int creatureCount = Integer.parseInt(reader.readLine());

            for (int i = 0; i < creatureCount; i++) {
                creatures.add(parseCreature(reader.readLine()));
            }

            int equipmentCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < equipmentCount; i++) {
                equipments.add(parseEquipment(reader.readLine()));
            }

            gameSession = new GameSession(dimensions[0], dimensions[1], true, creatures, equipments, turn);

            return true;

        } catch (Exception e) {
            System.err.println("Erro ao carregar o jogo: " + e.getMessage());
            return false;
        }
    }

    public int[] getWorldSize() {
        return gameSession.getBoardSize();
    }

    public int getInitialTeamId() {
        return gameSession.getShift();
    }

    public int getCurrentTeamId() {
        return gameSession.getShift();
    }

    public boolean isDay() {
        return gameSession.isDay();
    }

    public String getSquareInfo(int x, int y) {
        return gameSession.getSquareInfo(y, x);
    }

    public String[] getCreatureInfo(int id) {
        return gameSession.getCreatureInfo(id);
    }

    public String getCreatureInfoAsString(int id) {
        return gameSession.getCreatureInfoAsString(id);
    }

    public String[] getEquipmentInfo(int id) {
        return gameSession.getEquipmentInfo(id);
    }

    public String getEquipmentInfoAsString(int id) {
        return gameSession.getEquipmentInfoAsString(id);
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return false;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        return gameSession.move(yO, xO, yD, xD); //Os elementos estao guardados em coluna-linha
    }

    public boolean gameIsOver() {
        return gameSession.gameIsOver();
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
