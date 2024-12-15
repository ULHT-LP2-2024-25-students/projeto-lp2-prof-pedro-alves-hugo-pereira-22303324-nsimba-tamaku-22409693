package pt.ulusofona.lp2.thenightofthelivingdeisi;

public abstract class Creature extends BoardPiece {
    protected String name;
    protected int id;
    protected Team team;
    protected CreatureStatus status;
    protected Coord coord;
    protected Equipment equipment;
    protected String image;
    protected int equipmentsDestroyed;
    protected int equipmentsCaptured;
    protected boolean transformed = false;
    protected boolean safe = false;

    public Creature(String name, int id, Team team, int row, int col, String image) {
        this.name = name;
        this.id = id;
        this.team = team;
        this.coord = new Coord(row, col);
        this.image = image;
        this.equipmentsDestroyed = 0;
        this.equipmentsCaptured = 0;
        this.status = CreatureStatus.ALIVE;
    }

    public boolean isAlive() {
        return status == CreatureStatus.ALIVE;
    }

    public void die() {
        this.status = CreatureStatus.DEAD;
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
                String team = "Zombie";
                return transformed ? team + " (Transformado)" : team;
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

    public void changePosition(Coord coord) {
        this.coord.update(coord);
        if (hasEquipment()) {
            equipment.changePosition(coord);
        }
    }

    public void save() {
        safe = true;
    }

    public boolean isSafe() {
        return safe;
    }

    public void transform() {
        if (isHuman()) {
            team = Team.ZOMBIES;
            transformed = true;
            if (hasEquipment()) {
                destroy(equipment);
                unquip();
            }
        }
    }

    public String[] getInfo() {
        String[] info = new String[7];
        String teamAsString = getCreatureTeamAsString();
        info[0] = String.valueOf(getId());
        info[1] = getCreatureTypeAsString();
        info[2] = teamAsString;
        info[3] = getName();
        info[4] = safe ? null : String.valueOf(coord.getY());
        info[5] = safe ? null : String.valueOf(coord.getX());
        info[6] = String.valueOf(getImage());
        return info;
    }


    public String getInfoAsString() {
        if (isSafe()) {
            return String.format("%s | %s | %s | %s | +%d @ Safe Haven",
                    getId(),
                    getCreatureTypeAsString(),
                    getCreatureTeamAsString(),
                    getName(),
                    isHuman() ? equipmentsCaptured : equipmentsDestroyed);
        }
        return String.format("%s | %s | %s | %s | %s%d @ (%d, %d)%s",
                getId(),
                getCreatureTypeAsString(),
                getCreatureTeamAsString(),
                getName(),
                getCreatureSign(),
                isHuman() ? equipmentsCaptured : equipmentsDestroyed,
                coord.getY(), coord.getX(),
                hasEquipment() ? " | " + equipment.getInfoAsString() : "");
    }


    public void unquip() {
        this.equipment = null;
    }

    public void equip(Equipment equipment) {
        if (team == Team.ALIVES) {
            equipment.setAsCaptured();
            equipment.changePosition(coord);
            equipmentsCaptured++;
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

    public void useEquipment() {
        if (!hasEquipment()) {
            throw  new UnsupportedOperationException("Nao tem equipamento");
        }
        equipment.use();
    }

    public abstract boolean movesRectilinear(int distance);

    public abstract boolean movesObliqual(int distance);


}
