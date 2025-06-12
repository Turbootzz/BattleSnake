package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManager implements Serializable {

    // storage works local and in docker
    private static final Path STORAGE_PATH = Path.of(
            System.getenv().getOrDefault("BATTLESNAKE_STORAGE", System.getProperty("user.home")),
            "battlesnake.obj"
    );

    // Loads saved data on startup
    public static void init() {
        BattlesnakeData data = loadDataFromFile();
        if (data != null) {
            playedGames = data.playedGames != null ? data.playedGames : new HashMap<>();
            appearance = data.appearance != null ? data.appearance : new SnakeAppearance();
        }
    }

    // Appearance
    private static SnakeAppearance appearance = new SnakeAppearance();

    public static SnakeAppearance getAppearance() {
        return appearance;
    }

    public static void setAppearance(SnakeAppearance newAppearance) {
        appearance = newAppearance;
    }

    private static Map<String, GameRecord> playedGames = new HashMap<>();

    public static Map<String, GameRecord> getPlayedGames() { return playedGames; }
    public static void setPlayedGames(Map<String, GameRecord> games) { playedGames = games; }

    // Saves to file
    public static void saveDataToFile(SnakeAppearance appearance) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(STORAGE_PATH))) {
            oos.writeObject(new BattlesnakeData(playedGames, appearance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Loads from file
    public static BattlesnakeData loadDataFromFile() {
        if (Files.exists(STORAGE_PATH)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(STORAGE_PATH))) {
                return (BattlesnakeData) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}