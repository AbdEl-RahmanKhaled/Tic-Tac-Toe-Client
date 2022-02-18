package com.iti.tictactoeclient.models;

public class Credentials {
    protected String username;
    protected String password;

    public Credentials(){}

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUserName(String userName) {
        username = userName;
    }

    public void setPassword(String Password) {
        password = Password;
    }
    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
