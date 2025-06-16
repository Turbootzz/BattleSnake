package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.util.Map;

public interface PersistenceService {
    void init();
    void saveData();
    BattlesnakeData loadData();
    Map<String, GameRecord> getPlayedGames();
    void setPlayedGames(Map<String, GameRecord> games);
    SnakeAppearance getAppearance();
    void setAppearance(SnakeAppearance appearance);
}
