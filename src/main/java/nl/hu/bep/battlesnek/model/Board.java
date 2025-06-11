package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
    private int width;
    private int height;
    private List<Coord> food;
    private List<Coord> hazards;
    private List<Snake> snakes;

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public List<Coord> getFood() { return food; }
    public void setFood(List<Coord> food) { this.food = food; }

    public List<Coord> getHazards() { return hazards; }
    public void setHazards(List<Coord> hazards) { this.hazards = hazards; }

    public List<Snake> getSnakes() { return snakes; }
    public void setSnakes(List<Snake> snakes) { this.snakes = snakes; }
}
