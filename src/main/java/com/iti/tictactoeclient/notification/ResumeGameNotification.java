package com.iti.tictactoeclient.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Position;

import java.util.List;

public class ResumeGameNotification extends Notification {
    private Match match;
    private List<Position> positions;

    public ResumeGameNotification() {
    }

    public ResumeGameNotification(Match match, List<Position> positions) {
        this.match = match;
        this.positions = positions;
    }

    public ResumeGameNotification(@JsonProperty("type") String type,
                                  @JsonProperty("match") Match match,
                                  @JsonProperty("positions") List<Position> positions) {
        super(type);
        this.match = match;
        this.positions = positions;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
