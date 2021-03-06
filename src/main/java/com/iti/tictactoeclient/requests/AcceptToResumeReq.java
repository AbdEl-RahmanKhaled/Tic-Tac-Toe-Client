package com.iti.tictactoeclient.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;

public class AcceptToResumeReq extends Request {

    private Player player;
    private Match match;

    public AcceptToResumeReq() {
        super(ACTION_ACCEPT_TO_RESUME);
    }

    public AcceptToResumeReq(Player player, Match match) {
        super(ACTION_ACCEPT_TO_RESUME);
        this.player = player;
        this.match = match;
    }

    public AcceptToResumeReq(@JsonProperty("action") String action,
                             @JsonProperty("player") Player player,
                             @JsonProperty("match") Match match) {
        super(action);
        this.player = player;
        this.match = match;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
