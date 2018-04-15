package de.danielglaser.magiclivecounter.counter;

public class Player {
    private int live;

    public Player(int live) {
        this.live = live;
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
}
