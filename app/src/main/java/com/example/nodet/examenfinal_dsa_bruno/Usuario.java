package com.example.nodet.examenfinal_dsa_bruno;

/**
 * Created by nodet on 1/6/17.
 */

public class Usuario {
    private String login;
    private String avatar_url;

    public Usuario(){}


    public String getAvatar() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
