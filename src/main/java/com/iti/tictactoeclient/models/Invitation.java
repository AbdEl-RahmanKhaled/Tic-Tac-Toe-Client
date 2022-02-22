package com.iti.tictactoeclient.models;

public class Invitation {
    public static final String GAME_INVITATION = "New Game Invitation";
    public static final String RESUME_INVITATION = "Resume Game Invitation";
    private Player player;
    private String type;
    private Match match;
    private String name;

    public Invitation(Player player, String type) {
        this.player = player;
        this.type = type;
    }

    public Invitation(String type, Player player, Match match) {
        this.player = player;
        this.type = type;
        this.match = match;
    }

    public Invitation() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
