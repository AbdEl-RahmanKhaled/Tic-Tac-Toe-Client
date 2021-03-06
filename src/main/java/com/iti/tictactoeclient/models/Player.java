package com.iti.tictactoeclient.models;

public class Player {
    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";
    protected int db_id;
    protected long s_id;


    public Player() {
    }

    public Player(PlayerFullInfo playerFullInfo) {
        this.s_id = playerFullInfo.s_id;
        this.db_id = playerFullInfo.db_id;
    }

    public Player(int db_id, long s_id) {
        this.db_id = db_id;
        this.s_id = s_id;
    }

    public long getS_id() {
        return s_id;
    }

    public void setS_id(long s_id) {
        this.s_id = s_id;
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

}




