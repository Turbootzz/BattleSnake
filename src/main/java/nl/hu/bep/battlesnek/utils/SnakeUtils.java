package nl.hu.bep.battlesnek.utils;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.model.Snake;

import java.util.ArrayList;
import java.util.List;

public class SnakeUtils {
    // intelligence
    private static Coord getNextCoord(String move, Coord head) {
        return switch (move) {
            case "up" -> new Coord(head.getX(), head.getY() + 1);
            case "down" -> new Coord(head.getX(), head.getY() - 1);
            case "left" -> new Coord(head.getX() - 1, head.getY());
            case "right" -> new Coord(head.getX() + 1, head.getY());
            default -> head;
        };
    }

    private static boolean isSafe(String move, Coord head, GameState gameState) {
        Coord next = getNextCoord(move, head);
        for (Snake snake : gameState.getBoard().getSnakes()) {
            for (Coord c : snake.getBody()) {
                if (c.getX() == next.getX() && c.getY() == next.getY()) {
                    return false; // collision
                }
            }
        }
        return true;
    }

    public static List<String> getSafeMoves(Coord head, GameState gameState, List<String> possibleMoves) {
        List<String> safeMoves = new ArrayList<>();
        for (String move : possibleMoves) {
            if (isSafe(move, head, gameState)) {
                safeMoves.add(move);
            }
        }
        return safeMoves;
    }
}
