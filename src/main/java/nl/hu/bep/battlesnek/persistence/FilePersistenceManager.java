package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FilePersistenceManager implements PersistenceService {
    private static FilePersistenceManager instance;

    // storage works local and in docker
    private final Path storagePath;

    private Map<String, GameRecord> playedGames = new HashMap<>();
    private SnakeAppearance appearance = new SnakeAppearance();

    public FilePersistenceManager() {
        this.storagePath = Path.of(
                System.getenv().getOrDefault("BATTLESNAKE_STORAGE", System.getProperty("user.home")),
                "battlesnake.obj"
        );
    }

    public FilePersistenceManager(Path customPath) {
        this.storagePath = customPath;
    }

    // gets the instance of the persistence so that they are all the same
    public static synchronized FilePersistenceManager getInstance() {
        if (instance == null) {
            instance = new FilePersistenceManager();
        }
        return instance;
    }

    // Loads saved data on startup
    @Override
    public void init() {
        BattlesnakeData data = loadData();
        if (data != null) {
            this.playedGames = data.getPlayedGames() != null ? data.getPlayedGames() : new HashMap<>();
            this.appearance = data.getAppearance() != null ? data.getAppearance() : new SnakeAppearance();
        }
    }

    @Override
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(storagePath))) {
            oos.writeObject(new BattlesnakeData(playedGames, appearance));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BattlesnakeData loadData() {
        if (Files.exists(storagePath)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(storagePath))) {
                return (BattlesnakeData) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Map<String, GameRecord> getPlayedGames() {
        return playedGames;
    }

    @Override
    public void setPlayedGames(Map<String, GameRecord> games) {
        this.playedGames = games;
    }

    @Override
    public SnakeAppearance getAppearance() {
        return appearance;
    }

    @Override
    public void setAppearance(SnakeAppearance appearance) {
        this.appearance = appearance;
    }
}