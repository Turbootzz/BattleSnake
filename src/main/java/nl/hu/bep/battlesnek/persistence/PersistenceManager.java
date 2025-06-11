package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;

import java.util.HashMap;
import java.util.Map;

public class PersistenceManager {
    private static Map<String, GameRecord> playedGames = new HashMap<>();
}
