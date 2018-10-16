package de.danielglaser.magiclivecounter.counter.model;

public class Player {
    private String name;
    private int live;
    private int poison;
    private int energy;
    private int commanderDamage;

    public Player() {
        this.name = "";
        this.live = Settings.getInstance().getStartLive();
        this.poison = 0;
        this.energy = 0;
        this.commanderDamage = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLive() {
        return live;
    }

    public void addLive(int amount) {
        live += amount;
    }

    public void subLive(int amount) {
        live -= amount;
    }

    public int getPoison() {
        return poison;
    }

    public void addPoison(int amount) {
        poison += amount;
    }

    public void subPoison(int amount) {
        poison -= amount;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int amount) {
        energy += amount;
    }

    public void subEnergy(int amount) {
        energy -= amount;
    }

    public int getCommanderDamage() {
        return commanderDamage;
    }

    public void addCommanderDamage(int amount) {
        commanderDamage += amount;
    }

    public void subCommanderDamage(int amount) {
        commanderDamage -= amount;
    }

    public void resetPoints() {
        this.live = Settings.getInstance().getStartLive();
        this.poison = 0;
        this.energy = 0;
        this.commanderDamage = 0;
    }
}
