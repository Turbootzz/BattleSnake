package nl.hu.bep.battlesnek.model;

public class Snake {

    private int apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
    private float version;

    public Snake(int apiVersion, String author, String color, String head, String tail, float version) {
        this.apiVersion = apiVersion;
        this.author = author;
        this.color = color;
        this.head = head;
        this.tail = tail;
        this.version = version;
    }

    public Snake() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return author.equals(snake.author);
    }

    public String getAuthor() {
        return author;
    }
}
