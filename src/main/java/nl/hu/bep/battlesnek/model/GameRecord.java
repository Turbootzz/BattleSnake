package nl.hu.bep.battlesnek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameRecord {
    private String gameId;
    private List<String> moves;
    private int totalTurns;

    public GameRecord() {
        this.moves = new ArrayList<>();
    }

    public GameRecord(String gameId) {
        this.gameId = gameId;
        this.moves = new ArrayList<>();
        this.totalTurns = 0;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<String> getMoves() {
        return moves;
    }
    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public int getTotalTurns() {
        return totalTurns;
    }
    public void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }

    public void addMove(String move) {
        this.moves.add(move);
        this.totalTurns++;
    }
}