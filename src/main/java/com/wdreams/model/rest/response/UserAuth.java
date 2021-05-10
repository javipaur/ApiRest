package com.wdreams.model.rest.response;

public class UserAuth {
    private String user;
    private String msj;
    private String token;

    public UserAuth(String token) {
    }

    public UserAuth() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String mensaje, String msj) {
        this.msj = msj;
    }
}
