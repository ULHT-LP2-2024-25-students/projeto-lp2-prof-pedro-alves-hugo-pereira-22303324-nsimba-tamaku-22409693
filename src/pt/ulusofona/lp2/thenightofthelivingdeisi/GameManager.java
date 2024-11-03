package pt.ulusofona.lp2.thenightofthelivingdeisi;

import javax.swing.*;
import java.awt.*;
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
        return gameSession.creatureHasEquipment(creatureId, equipmentTypeId);
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        return gameSession.move(yO, xO, yD, xD); //Os elementos estao guardados em coluna-linha
    }

    public boolean gameIsOver() {
        return gameSession.gameIsOver();
    }

    public ArrayList<String> getSurvivors() {
        return gameSession.getSurvivors();
    }

    public JPanel getCreditsPanel() {
        // Criação do painel principal
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new BorderLayout());

        // Fundo e estilo
        creditsPanel.setBackground(new Color(30, 144, 255)); // Azul vibrante, estilo céu

        // Criação do título
        JLabel title = new JLabel("Créditos");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        creditsPanel.add(title, BorderLayout.NORTH);

        // Painel central com os créditos
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(34, 139, 34)); // Verde, estilo "grama"
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Adicionando mensagens estilo Minecraft
        JLabel line1 = new JLabel("Este jogo foi criado por:");
        line1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        line1.setForeground(Color.WHITE);
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel line2 = new JLabel("Hugo Pereira - Mestre das mecânicas!");
        line2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        line2.setForeground(Color.WHITE);
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel line3 = new JLabel("Nsimba Tamaku - Arquitetura dos blocos!");
        line3.setFont(new Font("SansSerif", Font.PLAIN, 18));
        line3.setForeground(Color.WHITE);
        line3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel line4 = new JLabel("Inspirado por uma noite pixelada sob o céu estrelado.");
        line4.setFont(new Font("SansSerif", Font.PLAIN, 16));
        line4.setForeground(Color.LIGHT_GRAY);
        line4.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel line5 = new JLabel("Dedicado aos que constroem mundos... um bloco de cada vez.");
        line5.setFont(new Font("SansSerif", Font.PLAIN, 16));
        line5.setForeground(Color.LIGHT_GRAY);
        line5.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adicionando elementos ao painel de texto
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(line1);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(line2);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(line3);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(line4);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(line5);

        creditsPanel.add(textPanel, BorderLayout.CENTER);

        return creditsPanel;
    }

    public HashMap<String, String> customizeBoard() {
        return null;
    }
}
