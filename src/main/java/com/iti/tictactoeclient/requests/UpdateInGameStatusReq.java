package com.iti.tictactoeclient.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UpdateInGameStatusReq extends Request {
    private boolean inGame;

    public UpdateInGameStatusReq() {

        super(ACTION_UPDATE_IN_GAME_STATUS);
    }

    public UpdateInGameStatusReq(boolean inGame) {
        this.inGame = inGame;
        this.action = ACTION_UPDATE_IN_GAME_STATUS;
    }

    public UpdateInGameStatusReq(@JsonProperty("action") String action,
                                 @JsonProperty("inGame") boolean inGame) {
        super(action);
        this.inGame = inGame;
    }

    public boolean getInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
