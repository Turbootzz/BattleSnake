package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private String id;
    private String name;
    private int timeout;

    public Game(String id, String name, int timeout) {
        this.id = id;
        this.name = name;
        this.timeout = timeout;
    }

    public Game() {}

    public String getId() { return id; }

    public String getName() { return name; }

    public int getTimeout() { return timeout; }
}
