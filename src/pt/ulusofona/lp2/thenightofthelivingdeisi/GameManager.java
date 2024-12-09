package pt.ulusofona.lp2.thenightofthelivingdeisi;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    private GameSession gameSession;

    public GameManager() {
        this.gameSession = new GameSession();
    }

    private int[] parseDimensions(String line, int currentLine) {
        String[] parts = line.split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private SafeHeavenDoor parseSafeHeavenDoor(String line, int currentLine) {
        String[] parts = line.split(":");
        return new SafeHeavenDoor(new Coord(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[0].trim())));
    }

    private Creature parseCreature(String line, int currentLine) throws InvalidFileException {
        int id = 0;
        Team team = null;
        int creatureType = 0;
        String name = null;
        int col = 0;
        int row = 0;
        try {
            String[] parts = line.split(":");
            id = Integer.parseInt(parts[0].trim());
            team = parts[1].trim().equals("20") ? Team.ALIVES : Team.ZOMBIES;
            creatureType = Integer.parseInt(parts[2].trim());
            name = parts[3].trim();
            col = Integer.parseInt(parts[4].trim());
            row = Integer.parseInt(parts[5].trim());
        } catch (Exception e) {
            throw new InvalidFileException(currentLine);
        }
        return switch (creatureType) {
            case 0 -> new Child(name, id, team, row, col, null);
            case 1 -> new Adult(name, id, team, row, col, null);
            case 2 -> new Eldery(name, id, team, row, col, null);
            case 3 -> new Dog(name, id, row, col, null);
            case 4 -> new Vampire(name, id, row, col, null);
            default -> throw new InvalidFileException(currentLine);
        };
    }

    private Equipment parseEquipment(String line, int currentLine) throws InvalidFileException {
        int id = 0;
        int equipmentType = 0;
        int col = 0;
        int row = 0;
        try {
            String[] parts = line.substring(1).split(":");
            id = Integer.parseInt(parts[0].trim()) * -1;
            equipmentType = Integer.parseInt(parts[1].trim());
            col = Integer.parseInt(parts[2].trim());
            row = Integer.parseInt(parts[3].trim());
        } catch (Exception e) {
            throw new InvalidFileException(currentLine);
        }
        return switch (equipmentType) {
            case 0 -> new Shield(id, row, col, null);
            case 1 -> new Sword(id, row, col, null);
            case 2 -> new Pistol(id, row, col, null);
            case 3 -> new Bleach(id, row, col, null);
            default -> throw new InvalidFileException(currentLine);
        };
    }

    public void loadGame(File file) throws FileNotFoundException, IOException, InvalidFileException {
        ArrayList<Creature> creatures = new ArrayList<>();
        ArrayList<Equipment> equipments = new ArrayList<>();
        ArrayList<SafeHeavenDoor> safeHeavenDoors = new ArrayList<>();
        int currentLine = 1;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int[] dimensions = parseDimensions(reader.readLine(), currentLine);
        int team = Integer.parseInt(reader.readLine());
        int creatureCount = Integer.parseInt(reader.readLine());

        currentLine += 2;
        for (int i = 0; i < creatureCount; i++) {
            currentLine += i;
            creatures.add(parseCreature(reader.readLine(), currentLine));
        }

        int equipmentCount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < equipmentCount; i++) {
            currentLine += i;
            equipments.add(parseEquipment(reader.readLine(), currentLine));
        }

        if (!equipments.isEmpty()) {
            int safeHeavenDoorsCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < safeHeavenDoorsCount; i++) {
                currentLine += i;
                safeHeavenDoors.add(parseSafeHeavenDoor(reader.readLine(), currentLine));
            }
        }

        gameSession = new GameSession(dimensions[0], dimensions[1], true, creatures, equipments, safeHeavenDoors, team);
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

    public void saveGame(File file) throws IOException {

    }

    public List<Integer> getIdsInSafeHaven() {
        List<Integer> ids = new ArrayList<>();
        return ids;
    }


    public HashMap<String, String> customizeBoard() {
        return null;
    }
}
