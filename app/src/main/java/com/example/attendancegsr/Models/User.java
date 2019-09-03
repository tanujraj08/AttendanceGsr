package com.example.attendancegsr.Models;

public class User {

    private String status, user_id, token;
    private int expires;

    public User(int expires, String user_id, String token, String status) {
        this.status = status;
        this.user_id = user_id;
        this.token = token;
        this.expires = expires;
    }


    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getToken() {
        return token;
    }

    public int getExpires() {
        return expires;
    }

}
