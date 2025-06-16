package nl.hu.bep.battlesnek.utils;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.model.Snake;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SnakeUtils {
    // snake health
    private static final int HEALTH_THRESHOLD = 50;

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
        // check for collisions against enemies and itself
        for (Snake snake : gameState.getBoard().getSnakes()) {

            // Tail is a hitbox
            for (Coord c : snake.getBody()) {
                if (c.equals(next)) {
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

    private static boolean isHeadToHeadRisk(String move, Coord head, GameState gameState, int myLength) {
        Coord nextPos = getNextCoord(move, head);
        for (Snake snake : gameState.getBoard().getSnakes()) {
            if (snake.getId().equals(gameState.getYou().getId())) continue; // Skip You

            Coord enemyHead = snake.getHead();
            // If the enemy snake is the same length or bigger, then head to head is a risk
            if (snake.getLength() >= myLength) {
                // calculate possible enemy moves
                if (calculateDistance(nextPos, enemyHead) == 1) {
                    return true; // potential head to head collision
                }
            }
        }
        return false;
    }

    private static int calculateDistance(Coord a, Coord b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    // -- Strategy functions --

    // Optional is for if there is no food
    private static Optional<Coord> findNearestFood(Coord head, List<Coord> food) {
        return food.stream()
                .min(Comparator.comparingInt(f -> calculateDistance(head, f)));
    }

    private static Optional<Snake> findNearestSmallerEnemy(Coord head, GameState gameState) {
        int myLength = gameState.getYou().getLength();
        return gameState.getBoard().getSnakes().stream()
                .filter(s -> !s.getId().equals(gameState.getYou().getId())) // Alleen vijanden
                .filter(s -> s.getLength() < myLength) // Alleen vijanden die kleiner zijn
                .min(Comparator.comparingInt(s -> calculateDistance(head, s.getHead()))); // Zoek de dichtstbijzijnde
    }

    // -- core intelligense based on prioritylist --

    public static String getSmartMove(Coord head, GameState gameState, List<String> safeMoves) {
        int myLength = gameState.getYou().getLength();

        // 1. Prevent head to head collision with bigger enemy
        List<String> saferMoves = new ArrayList<>(safeMoves);
        saferMoves.removeIf(move -> isHeadToHeadRisk(move, head, gameState, myLength));

        // if after filtering miltiple moves are available, then use the List,
        // otherwise risk with original safeMoves
        List<String> movesToConsider = !saferMoves.isEmpty() ? saferMoves : safeMoves;

        // -- strategy decisions --

        // strategy 1: search food if health is low (high priority)
        if (gameState.getYou().getHealth() < HEALTH_THRESHOLD) {
            Optional<Coord> nearestFood = findNearestFood(head, gameState.getBoard().getFood());
            if (nearestFood.isPresent()) {
                // sort safest moves baesd on nearest food
                movesToConsider.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), nearestFood.get())));
                return movesToConsider.get(0); // best move to nearest food
            }
        }

        // strategy 2: Hunt small enemies if we are big
        Optional<Snake> prey = findNearestSmallerEnemy(head, gameState);
        if (prey.isPresent()) {
            // sort safest moves baesd on nearest enemy head
            movesToConsider.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), prey.get().getHead())));
            return movesToConsider.get(0); // best move to nearest head
        }

        // strategy 3: FALLBACK
        return movesToConsider.get(0);
    }
}