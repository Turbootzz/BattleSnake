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
}
