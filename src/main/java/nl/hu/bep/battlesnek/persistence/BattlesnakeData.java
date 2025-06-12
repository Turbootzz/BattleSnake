package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.io.Serializable;
import java.util.Map;

public class BattlesnakeData implements Serializable {
    public Map<String, GameRecord> playedGames;
    public SnakeAppearance appearance;

    public BattlesnakeData(Map<String, GameRecord> playedGames, SnakeAppearance appearance) {
        this.playedGames = playedGames;
        this.appearance = appearance;
    }
}