package com.iti.tictactoeclient.models;

public class User extends Credentials {
    private int u_id;
    private String name;
    private int points;

    public User(){}

    public User(int u_id, String name, int points) {
        this.u_id = u_id;
        this.name = name;
        this.points = points;
    }

    public User(String username, String password, int u_id, String name, int points) {
        super(username, password);
        this.u_id = u_id;
        this.name = name;
        this.points = points;
    }

    public void setId(int Id) {
        u_id = Id;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setPoints(int Points) {
        points = Points;
    }

    public int getId() {
        return u_id;
    }

    public String getName() {
        return name;
    }


    public int getPoints() {
        return points;
    }
}
