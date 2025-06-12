package nl.hu.bep;

import nl.hu.bep.battlesnek.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    void testGameConstructorAndGetters() {
        Game game = new Game("game-123", "FunGame", 500);

        assertEquals("game-123", game.getId());
        assertEquals("FunGame", game.getName());
        assertEquals(500, game.getTimeout());
    }

    @Test
    void testEqualsAndHashCode() {
        Game game1 = new Game("game-123", "FunGame", 500);
        Game game2 = new Game("game-123", "FunGame", 500);

        assertEquals(game1, game2);
        assertEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testNullIdNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, "GameName", 500);
        });
    }

    @Test
    void testNullNameAllowed() {
        Game game = new Game("game-789", null, 200);
        assertNull(game.getName());
    }

    @Test
    void testToStringNotNull() {
        Game game = new Game("game-123", "FunGame", 500);
        assertNotNull(game.toString());
        assertTrue(game.toString().contains("FunGame"));
    }
}
