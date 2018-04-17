package de.danielglaser.magiclivecounter.counter.model;

public class Player {
    private int live;
    private int poison;

    public Player(int live) {
        this.live = live;
        this.poison = 0;
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
}
