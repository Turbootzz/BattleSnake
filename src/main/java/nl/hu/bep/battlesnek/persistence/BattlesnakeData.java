package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.io.Serializable;
import java.util.Map;

public class BattlesnakeData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, GameRecord> playedGames;
    private final SnakeAppearance appearance;

    public BattlesnakeData(Map<String, GameRecord> playedGames, SnakeAppearance appearance) {
        this.playedGames = playedGames;
        this.appearance = appearance;
    }

    public Map<String, GameRecord> getPlayedGames() {
        return playedGames;
    }

    public SnakeAppearance getAppearance() {
        return appearance;
    }
}