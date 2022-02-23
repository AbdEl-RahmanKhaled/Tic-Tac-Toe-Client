package com.iti.tictactoeclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class MatchTable {
    private String player1_Name, player2_Name, winner,status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp m_date;
    private int m_id;
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_PAUSED = "paused";

    public MatchTable(){}
    public MatchTable(String player1_Name, String player2_Name, String winner, String status, Timestamp m_date, int m_id) {
        this.player1_Name = player1_Name;
        this.player2_Name = player2_Name;
        this.winner = winner;
        this.status = status;
        this.m_date = m_date;
        this.m_id = m_id;
    }

    public String getPlayer1_Name() {
        return player1_Name;
    }

    public void setPlayer1_Name(String player1_Name) {
        this.player1_Name = player1_Name;
    }

    public String getPlayer2_Name() {
        return player2_Name;
    }

    public void setPlayer2_Name(String player2_Name) {
        this.player2_Name = player2_Name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getM_date() {
        return m_date;
    }

    public void setM_date(Timestamp m_date) {
        this.m_date = m_date;
    }

    public String getWinner(){return winner;}

    public void setWinner(String winner){this.winner=winner;}

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }
}
