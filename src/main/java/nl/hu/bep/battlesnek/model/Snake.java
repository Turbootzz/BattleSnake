package nl.hu.bep.battlesnek.model;

import java.util.List;

public class Snake {

    private int id;
    private String name;
    private int health;
    private List<Coord> body;
    private Coord head;
    private int length;

    public Snake(int id, String name, int health, List<Coord> body, Coord head, int length) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.body = body;
        this.head = head;
        this.length = length;
    }

    public Snake() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return name.equals(snake.name);
    }

    public String getName() { return name; }
}
