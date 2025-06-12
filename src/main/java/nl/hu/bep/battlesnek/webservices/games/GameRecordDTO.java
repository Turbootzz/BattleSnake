package nl.hu.bep.battlesnek.webservices.games;

import java.util.List;

public class GameRecordDTO {
    public String gameId;
    public List<String> moves;
    public int totalTurns;

    public GameRecordDTO(String gameId, List<String> moves, int totalTurns) {
        this.gameId = gameId;
        this.moves = moves;
        this.totalTurns = totalTurns;
    }
}
