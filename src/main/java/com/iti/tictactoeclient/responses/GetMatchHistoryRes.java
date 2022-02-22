package com.iti.tictactoeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.MatchTable;

import java.util.List;

public class GetMatchHistoryRes extends Response{
    private List<MatchTable> matches;

    public GetMatchHistoryRes() {
        this.type = RESPONSE_GET_MATCH_HISTORY;
    }

    public GetMatchHistoryRes(String status,  List<MatchTable> matches) {
        this.matches = matches;
        this.type = RESPONSE_GET_MATCH_HISTORY;
        this.status = status;
    }

    public GetMatchHistoryRes(@JsonProperty("status") String status,
                              @JsonProperty("type") String type,
                              @JsonProperty("message") String message,
                              @JsonProperty("matches") List<MatchTable> matches) {
        this.matches = matches;
        this.type = RESPONSE_GET_MATCH_HISTORY;
        this.status = status;
        this.message=message;
    }


    public List<MatchTable> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchTable> matches) {
        this.matches = matches;
    }
}
