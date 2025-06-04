package nl.hu.bep.battlesnek.model;

import java.util.List;

public class Board {
    private int width;
    private int height;
    private List<Coord> food;
    private List<Coord> hazards;
    private List<Snake> snakes;
}
