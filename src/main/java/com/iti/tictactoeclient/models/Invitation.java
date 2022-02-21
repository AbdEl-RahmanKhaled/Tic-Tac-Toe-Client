package com.iti.tictactoeclient.models;

public class Invitation {
    private PlayerFullInfo playerFullInfo;

    public Invitation(PlayerFullInfo playerFullInfo) {
        this.playerFullInfo = playerFullInfo;
    }

    public Invitation() {
    }

    public PlayerFullInfo getPlayerFullInfo() {
        return playerFullInfo;
    }

    public void setPlayerFullInfo(PlayerFullInfo playerFullInfo) {
        this.playerFullInfo = playerFullInfo;
    }
}
