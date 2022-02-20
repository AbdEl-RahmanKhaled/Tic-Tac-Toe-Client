package com.iti.tictactoeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AskToPauseRes extends Response {

    public AskToPauseRes() {
        this.type = RESPONSE_ASK_TO_PAUSE;
    }

    public AskToPauseRes(@JsonProperty("status") String status, @JsonProperty("type") String type, @JsonProperty("message") String message) {
        this.message = message;
        this.type = type;
        this.status = status;
    }
}
