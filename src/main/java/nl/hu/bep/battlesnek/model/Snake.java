package nl.hu.bep.battlesnek.model;

import java.util.List;

public class Snake {

    private String id;
    private String name;
    private int health;
    private List<Coord> body;
    private Coord head;
    private int length;

    public Snake(String id, String name, int health, List<Coord> body, Coord head, int length) {
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public List<Coord> getBody() { return body; }
    public void setBody(List<Coord> body) { this.body = body; }

    public Coord getHead() { return head; }
    public void setHead(Coord head) { this.head = head; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
}
