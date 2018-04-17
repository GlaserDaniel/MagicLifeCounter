package de.danielglaser.magiclivecounter.counter.model;

public class Player {
    private int live;
    private int poison;
    private int energy;
    private int commanderDamage;

    public Player(int live) {
        this.live = live;
        this.poison = 0;
        this.energy = 0;
        this.commanderDamage = 0;
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
}
