package pt.ulusofona.lp2.thenightofthelivingdeisi;

public abstract class Creature extends BoardPiece {
    protected String name;
    protected int id;
    protected Team team;
    protected Coord coord;
    protected Equipment equipment;
    protected String image;
    protected int equipmentsDestroyed;

    public Creature(String name, int id, Team team, int row, int col, String image) {
        this.name = name;
        this.id = id;
        this.team = team;
        this.coord = new Coord(row, col);
        this.image = image;
        this.equipmentsDestroyed = 0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }


    public Coord getCoord() {
        return this.coord;
    }

    public abstract String getCreatureTypeAsString();


    public String getCreatureTeamAsString() {
        switch (team) {
            case ALIVES -> {
                return "Humano";
            }
            case ZOMBIES -> {
                return "Zombie";
            }
            default -> {
                return "";
            }
        }
    }

    public String getCreatureSign() {
        return team == Team.ALIVES ? "+" : "-";
    }

    public boolean isHuman() {
        return team == Team.ALIVES;
    }

    public String getImage() {
        return image;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void changePosition(int row, int col) {
        this.coord = new Coord(row, col);
        if (hasEquipment()) {
            equipment.changePosition(row, col);
        }
    }

    public void transformar() {
        if (isHuman()) {
            team = Team.ZOMBIES;
        }
    }

    public String[] getInfo() {
        String[] info = new String[7];
        String teamAsString = getCreatureTeamAsString();
        info[0] = String.valueOf(getId());
        info[1] = getCreatureTypeAsString();
        info[2] = teamAsString;
        info[3] = getName();
        info[4] = String.valueOf(coord.getY());
        info[5] = String.valueOf(coord.getX());
        info[6] = String.valueOf(getImage());
        return info;
    }


    public String getInfoAsString() {
        int equipmentCounter = equipment == null ? 0 : 1;
        return String.format("%s | %s | %s | %s | %s%d @ (%d, %d)%s",
                getId(),
                getCreatureTypeAsString(),
                getCreatureTeamAsString(),
                getName(),
                getCreatureSign(),
                isHuman() ? equipmentCounter : equipmentsDestroyed,
                coord.getY(), coord.getX(),
                hasEquipment() ? " | " + equipment.getInfoAsString() : "");
    }


    public void unquip() {
        this.equipment = null;
    }

    public void equip(Equipment equipment) {
        if (team == Team.ALIVES) {
            equipment.setAsCaptured();
            equipment.changePosition(coord.getX(), coord.getY());
            this.equipment = equipment;
            return;
        }
        throw new UnsupportedOperationException("Zombies não se equipam");
    }

    public void destroy(Equipment equipment) {
        equipment.setAsDestroyed();
        equipmentsDestroyed++;
    }


    public boolean hasEquipment() {

        return equipment != null;
    }

    public String getResumedInfo() {
        String team = this.getTeam() == Team.ALIVES ? "H" : "Z";
        return String.format("%s:%d", team, this.getId());
    }

    @Override
    public boolean moves() {
        return true;
    }

    public boolean onlyMovesInTheMorning() {
        return false;
    }

    public boolean onlyMovesAtNight() {
        return false;
    }

    public boolean carriesEquipment() {
        return true;
    }

    public abstract boolean movesRectilinear(int distance);

    public abstract boolean movesObliqual(int distance);


}
