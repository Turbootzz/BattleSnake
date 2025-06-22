package nl.hu.bep.battlesnek.utils;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.model.Snake;

import java.util.*;
import java.util.stream.Collectors;

public class SnakeUtils {
    // snake health
    private static final int HEALTH_THRESHOLD = 50;

    // calculates the next coordinate based on a move.
    private static Coord getNextCoord(String move, Coord head) {
        return switch (move) {
            case "up" -> new Coord(head.getX(), head.getY() + 1);
            case "down" -> new Coord(head.getX(), head.getY() - 1);
            case "left" -> new Coord(head.getX() - 1, head.getY());
            case "right" -> new Coord(head.getX() + 1, head.getY());
            default -> head;
        };
    }

    // Checks if a move is safe from collisions with walls or other snakes. MOST important survival.
    private static boolean isSafe(String move, Coord head, GameState gameState) {
        Coord nextPos = getNextCoord(move, head);

        // 1. wall check
        int width = gameState.getBoard().getWidth();
        int height = gameState.getBoard().getHeight();
        if (nextPos.getX() < 0 || nextPos.getX() >= width || nextPos.getY() < 0 || nextPos.getY() >= height) {
            return false;
        }

        // 2. snake body check (for all snakes)
        for (Snake snake : gameState.getBoard().getSnakes()) {
            List<Coord> body = snake.getBody();
            // loop through the snake body
            for (int i = 0; i < body.size(); i++) {
                if (nextPos.equals(body.get(i))) {
                    // found a collision. is it deadly?

                    // collision is non-deadly ONLY if it's with a tail of a snake that has NOT just eaten
                    // enemy bodypart is always dangerous
                    boolean isOurSnake = snake.getId().equals(gameState.getYou().getId());
                    boolean isTail = (i == body.size() - 1);
                    boolean hasJustEaten = snake.getHealth() == 100;

                    if (isOurSnake && isTail && !hasJustEaten) {
                        // this is our tail and its not growing. ONLY safe collision
                        continue;
                    } else {
                        // This is a collision with a neck, a body part, or a non-moving tail. This is deadly
                        return false;
                    }
                }
            }
        }

        // if survived all checks, move is safe
        return true;
    }

    // filters a list of possible moves to only the safe moves
    public static List<String> getSafeMoves(Coord head, GameState gameState, List<String> possibleMoves) {
        List<String> safeMoves = new ArrayList<>();
        for (String move : possibleMoves) {
            if (isSafe(move, head, gameState)) {
                safeMoves.add(move);
            }
        }
        return safeMoves;
    }

    // head to head risk if enemy snake is the same size or bigger
    private static boolean isHeadToHeadRisk(String move, Coord head, GameState gameState) {
        Coord nextPos = getNextCoord(move, head);
        int myLength = gameState.getYou().getLength();

        for (Snake enemy : gameState.getBoard().getSnakes()) {
            if (enemy.getId().equals(gameState.getYou().getId())) continue; // Skip self

            if (enemy.getLength() >= myLength) {
                // This enemy is a threat in a head-to-head collision.
                // Check if our next move is adjacent to their head.
                if (calculateDistance(nextPos, enemy.getHead()) <= 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // calculates distance between coordinates
    private static int calculateDistance(Coord a, Coord b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    // -- Strategy functions --

    private static Optional<Coord> findNearestFood(Coord head, List<Coord> food) {
        return food.stream()
                .min(Comparator.comparingInt(f -> calculateDistance(head, f)));
    }

    // finds the nearest enemy snake that is smaller than us. Its optional because there is not always a smaller one
    private static Optional<Snake> findNearestSmallerEnemy(Coord head, GameState gameState) {
        int myLength = gameState.getYou().getLength();
        return gameState.getBoard().getSnakes().stream()
                .filter(s -> !s.getId().equals(gameState.getYou().getId())) // Must be an enemy
                .filter(s -> s.getLength() < myLength) // Must be smaller
                .min(Comparator.comparingInt(s -> calculateDistance(head, s.getHead()))); // Must be the closest
    }

    private static boolean isHuntingRisk(String move, Coord head, Snake prey) {
        Coord nextPos = getNextCoord(move, head);
        // check if our next position is next to the prey's head.
        if (calculateDistance(nextPos, prey.getHead()) <= 1) {
            return true;
        }
        return false;
    }

    // -- core intelligense based on prioritylist --

    public static String getSmartMove(Coord head, GameState gameState, List<String> safeMoves) {
        // priority 1: if health is low
        if (gameState.getYou().getHealth() < HEALTH_THRESHOLD) {
            Optional<Coord> nearestFood = findNearestFood(head, gameState.getBoard().getFood());
            if (nearestFood.isPresent()) {
                // Sort the safe moves by which one gets us closer to the food.
                safeMoves.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), nearestFood.get())));
                return safeMoves.get(0); // Return the best move towards food.
            }
        }

        // priority 2: HUNT smaller snakes
        Optional<Snake> prey = findNearestSmallerEnemy(head, gameState);
        if (prey.isPresent()) {
            // only hunt if we are at least 2 parts longer.
            if (gameState.getYou().getLength() > prey.get().getLength() + 1) {
                // filter out moves that are too risky
                List<String> saferHuntingMoves = safeMoves.stream()
                        .filter(m -> !isHuntingRisk(m, head, prey.get()))
                        .collect(Collectors.toCollection(ArrayList::new));

                if (!saferHuntingMoves.isEmpty()) {
                    // if we have safer options, use them.
                    saferHuntingMoves.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), prey.get().getHead())));
                    return saferHuntingMoves.get(0);
                }
                // if all hunting moves are risky, we wont hunt
            }
        }

        // priority 3: food seeking (default behavior)
        Optional<Coord> anyFood = findNearestFood(head, gameState.getBoard().getFood());
        if (anyFood.isPresent()) {
            // avoid head-to-head risks while looking for food.
            List<String> lessRiskyMoves = safeMoves.stream()
                    .filter(m -> !isHeadToHeadRisk(m, head, gameState))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (!lessRiskyMoves.isEmpty()) {
                // if we have less risky moves, use it
                lessRiskyMoves.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), anyFood.get())));
                return lessRiskyMoves.get(0);
            } else {
                // if all safe moves are risky, take one and hope for the best :')
                safeMoves.sort(Comparator.comparingInt(m -> calculateDistance(getNextCoord(m, head), anyFood.get())));
                return safeMoves.get(0);
            }
        }

        // priority 4: fallback
        // this looks for a way to be able circle in an open space (camping mode)
        Coord myTail = gameState.getYou().getBody().get(gameState.getYou().getBody().size() - 1);
        // sort moves by which one takes the snake furthest from our tail.
        safeMoves.sort((m1, m2) -> {
            int dist1 = calculateDistance(getNextCoord(m1, head), myTail);
            int dist2 = calculateDistance(getNextCoord(m2, head), myTail);
            return Integer.compare(dist2, dist1);
        });

        return safeMoves.get(0);
    }
}