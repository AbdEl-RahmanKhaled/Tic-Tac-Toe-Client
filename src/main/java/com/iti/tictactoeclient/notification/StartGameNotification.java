package com.iti.tictactoeclient.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Match;

public class StartGameNotification extends Notification {
    private Match match;

    public StartGameNotification() {
        this.type = NOTIFICATION_START_GAME;
    }

    public StartGameNotification(Match match) {
        this.match = match;
        this.type = NOTIFICATION_START_GAME;
    }

    public StartGameNotification(@JsonProperty("type") String type, @JsonProperty("match") Match match) {
        this.match = match;
        this.type = type;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
