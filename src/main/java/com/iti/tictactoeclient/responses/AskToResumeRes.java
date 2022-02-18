package com.iti.tictactoeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Player;

public class AskToResumeRes extends Response {
    private Player player;

    public AskToResumeRes(String status, Player player) {
        //super(status,RESPONSE_INVITE_TO_GAME);
        this.player = player;
        this.status = status;
        this.type = RESPONSE_ASK_TO_RESUME;

    }

    public AskToResumeRes(String status, String message, Player player) {
        //super(status,RESPONSE_INVITE_TO_GAME);
        this.player = player;
        this.status = status;
        this.message = message;
        this.type = RESPONSE_ASK_TO_RESUME;
    }

    public AskToResumeRes(@JsonProperty("status") String status,
                           @JsonProperty("type") String type,
                           @JsonProperty("message") String message,
                           @JsonProperty("player") Player player) {
        //super(message,status,RESPONSE_INVITE_TO_GAME);
        this.status = status;
        this.type = type;
        this.message = message;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
