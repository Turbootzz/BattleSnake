package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private String id;
    private String name;
    private int timeout;

    public Game(String id, String name, int timeout) {
        this.id = id;
        this.name = name;
        this.timeout = timeout;

        if (id == null) {
            throw new IllegalArgumentException("Game id should not be null");
        }
    }

    public Game() {}

    // to prevent java to put same gamedetails into the same memory
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return timeout == game.timeout &&
                Objects.equals(id, game.id) &&
                Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timeout);
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public int getTimeout() { return timeout; }

    @Override
    public String toString() {
        return "Game{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", timeout=" + timeout + '}';
    }
}
