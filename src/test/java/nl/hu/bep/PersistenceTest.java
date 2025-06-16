package nl.hu.bep;

import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;
import nl.hu.bep.battlesnek.persistence.BattlesnakeData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
    private final Path testStoragePath = Path.of(System.getProperty("user.home"), "battlesnake-test.obj");
    private FilePersistenceManager persistence;

    @BeforeEach
    void setup() {
        persistence = new FilePersistenceManager(testStoragePath);
    }

    @Test
    void testSaveAndLoadData() {
        // Arrange
        SnakeAppearance testAppearance = new SnakeAppearance();
        testAppearance.setColor("#FF0000");
        testAppearance.setHead("beluga");
        testAppearance.setTail("round-bum");

        Map<String, GameRecord> games = new HashMap<>();
        GameRecord record = new GameRecord("test-game");
        record.addMove("up");
        games.put("test-game", record);

        persistence.setAppearance(testAppearance);
        persistence.setPlayedGames(games);
        persistence.saveData();

        // Act
        BattlesnakeData loadedData = persistence.loadData();

        // Assert
        assertNotNull(loadedData);
        assertEquals("test-game", loadedData.getPlayedGames().keySet().iterator().next());
        assertEquals("up", loadedData.getPlayedGames().get("test-game").getMoves().get(0));
        assertEquals("#FF0000", loadedData.getAppearance().getColor());
    }

    @Test
    void testLoadWithoutFileReturnsNull() {
        try {
            Files.deleteIfExists(testStoragePath); // test file
        } catch (IOException e) {
            fail("IOException could not remove file: " + e.getMessage());
        }

        BattlesnakeData result = persistence.loadData();

        assertNull(result);
    }

    @Test
    void testInitSetsStaticFields() {
        SnakeAppearance testAppearance = new SnakeAppearance();
        testAppearance.setColor("#ABCDEF");

        GameRecord record = new GameRecord("init-game");
        record.addMove("left");
        Map<String, GameRecord> games = new HashMap<>();
        games.put("init-game", record);

        persistence.setAppearance(testAppearance);
        persistence.setPlayedGames(games);
        persistence.saveData();
        // Reset static fields
        persistence.setAppearance(new SnakeAppearance());
        persistence.setPlayedGames(new HashMap<>());

        // Act
        persistence.init();

        assertEquals("#ABCDEF", persistence.getAppearance().getColor());
        assertTrue(persistence.getPlayedGames().containsKey("init-game"));
    }

    @AfterEach
    void reset() {
        try {
            Files.deleteIfExists(testStoragePath);
        } catch (IOException e) {
            fail("IOException trying to delite file: " + e.getMessage());
        }
    }
}
