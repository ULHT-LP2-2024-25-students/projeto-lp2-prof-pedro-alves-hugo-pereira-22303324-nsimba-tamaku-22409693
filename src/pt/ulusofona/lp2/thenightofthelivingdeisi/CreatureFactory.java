package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class CreatureFactory {
    private static CreatureFactory instance;

    public static CreatureFactory getInstance() {
        if (instance == null) {
            instance = new CreatureFactory();
        }
        return instance;
    }

    private CreatureFactory() {

    }

    public Creature createCreature(int creatureType, String name, int id, Team team, int row, int col, int currentLine) throws InvalidFileException {
        if ((creatureType == 3 && team == Team.ZOMBIES) || (creatureType == 4 && team == Team.ALIVES)) {
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
}
