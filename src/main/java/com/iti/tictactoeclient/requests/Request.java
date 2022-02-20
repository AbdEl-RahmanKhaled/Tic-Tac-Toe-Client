package com.iti.tictactoeclient.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    protected String action;

    public static final String ACTION_LOGIN = "login"; // login done
    public static final String ACTION_ASK_TO_PAUSE = "askToPause"; // game done
    public static final String ACTION_INVITE_TO_GAME = "inviteToGame"; // home done
    public static final String ACTION_ACCEPT_INVITATION = "acceptInvitation"; // home done
    public static final String ACTION_REJECT_INVITATION = "rejectInvitation"; // home done
    public static final String ACTION_UPDATE_BOARD = "updateBoard"; // game
    public static final String ACTION_UPDATE_IN_GAME_STATUS = "updateInGameStatus"; // game done
    public static final String ACTION_SIGN_UP = "signup"; // register done
    public static final String ACTION_SAVE_MATCH = "saveMatch"; // game done
    public static final String ACTION_REJECT_TO_PAUSE = "rejectToPause"; // game done
    public static final String ACTION_ACCEPT_TO_PAUSE = "acceptToPause"; // game done
    public static final String ACTION_SEND_MESSAGE = "sendMessage"; // game j done
    public static final String ACTION_ASK_TO_RESUME = "askToResume"; // history n done
    public static final String ACTION_REJECT_TO_RESUME = "rejectToResume"; // home n done
    public static final String ACTION_ACCEPT_TO_RESUME = "acceptToResume"; // home n done
    public static final String ACTION_GET_MATCH_HISTORY = "getMatchHistory"; // home done
    public static final String ACTION_BACK_FROM_OFFLINE = "backFromOffline"; // server listener done



    public Request() {

    }

    public Request(@JsonProperty("action") String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
