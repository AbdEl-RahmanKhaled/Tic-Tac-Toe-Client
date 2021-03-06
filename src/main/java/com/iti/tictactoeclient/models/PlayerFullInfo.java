package com.iti.tictactoeclient.models;

public class PlayerFullInfo extends Player{
    private int points;
    private String name, status;
    private boolean inGame;

    public PlayerFullInfo(){
        s_id = -1;
    }

    public PlayerFullInfo(int index, int db_id, String name, int points) {
        this.db_id = db_id;
        this.name = name;
        this.points = points;
        s_id = -1;
        status = OFFLINE;
        inGame = false;
    }

    public PlayerFullInfo(int db_id, String name, int points) {
        this.db_id = db_id;
        this.name = name;
        this.points = points;
        s_id = -1;
        status = OFFLINE;
        inGame = false;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}


