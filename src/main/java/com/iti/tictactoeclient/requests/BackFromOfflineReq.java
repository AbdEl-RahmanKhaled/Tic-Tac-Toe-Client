package com.iti.tictactoeclient.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Player;

public class BackFromOfflineReq extends Request {
    private Player player;

    public BackFromOfflineReq() {
        this.action = ACTION_BACK_FROM_OFFLINE;
    }

    public BackFromOfflineReq(Player player) {
        this.player = player;
        this.action = ACTION_BACK_FROM_OFFLINE;
    }

    public BackFromOfflineReq(@JsonProperty("action") String action, @JsonProperty("player") Player player) {
        super(action);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
