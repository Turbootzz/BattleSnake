package nl.hu.bep.battlesnek.model;

public class Game {

    private int id;
    private String name;
    private int timeout;

    public Game(int id, String name, int timeout) {
        this.id = id;
        this.name = name;
        this.timeout = timeout;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getTimeout() { return timeout; }
}
