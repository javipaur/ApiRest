/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;


import com.wdreams.model.dao.Dao;

/**
 *
 * @author victor
 */
public abstract class DatosEmail {

    public String host;
    public Integer port;
    public String user;
    public String from;
    public String password;
    public String alias;
    public String smtp;


    protected Dao dao;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSmtp() { return smtp; }

    public void setSmtp(String smtp) { this.smtp = smtp; }


}
