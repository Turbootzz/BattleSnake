package nl.hu.bep;

import org.junit.jupiter.api.Test;
import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;
import nl.hu.bep.battlesnek.persistence.BattlesnakeData;
import nl.hu.bep.battlesnek.persistence.PersistenceManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
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

        PersistenceManager.setAppearance(testAppearance);
        PersistenceManager.setPlayedGames(games);
        PersistenceManager.saveDataToFile(testAppearance);

        // Act
        BattlesnakeData loadedData = PersistenceManager.loadDataFromFile();

        // Assert
        assertNotNull(loadedData);
        assertEquals("test-game", loadedData.playedGames.keySet().iterator().next());
        assertEquals("up", loadedData.playedGames.get("test-game").getMoves().get(0));
        assertEquals("#FF0000", loadedData.appearance.getColor());
    }

    @Test
    void testLoadWithoutFileReturnsNull() {
        Path path = Path.of(System.getProperty("user.home"), "battlesnake.obj");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            fail("IOException tijdens verwijderen bestand: " + e.getMessage());
        }

        BattlesnakeData result = PersistenceManager.loadDataFromFile();

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

        PersistenceManager.setAppearance(testAppearance);
        PersistenceManager.setPlayedGames(games);
        PersistenceManager.saveDataToFile(testAppearance);
        // Reset static fields
        PersistenceManager.setAppearance(new SnakeAppearance());
        PersistenceManager.setPlayedGames(new HashMap<>());

        // Act
        PersistenceManager.init();

        assertEquals("#ABCDEF", PersistenceManager.getAppearance().getColor());
        assertTrue(PersistenceManager.getPlayedGames().containsKey("init-game"));
    }
}
