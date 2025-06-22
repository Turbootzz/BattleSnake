package nl.hu.bep.battlesnek.persistence;

import nl.hu.bep.battlesnek.model.Feedback;
import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BattlesnakeData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, GameRecord> playedGames;
    private final SnakeAppearance appearance;
    private List<Feedback> feedbackList;

    public BattlesnakeData(Map<String, GameRecord> playedGames, SnakeAppearance appearance, List<Feedback> feedbackList) {
        this.playedGames = playedGames;
        this.appearance = appearance;
        this.feedbackList = feedbackList;
    }

    public Map<String, GameRecord> getPlayedGames() {
        return playedGames;
    }
    public SnakeAppearance getAppearance() {
        return appearance;
    }
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }
}