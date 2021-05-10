/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.AppConfig;


/**
 *
 * @author victor
 */
public class DatosEmailAdministrador extends DatosEmail {

    public DatosEmailAdministrador(Dao dao) {
        this.dao = dao;
        host = dao.getAppConfigId(AppConfig.SMTP_EMAIL).getValor();
        port = Integer.parseInt(dao.getAppConfigId(AppConfig.PORT_EMAIL).getValor());
        user = dao.getAppConfigId(AppConfig.USER_EMAIL).getValor();
        alias = dao.getAppConfigId(AppConfig.ALIAS_EMAIL).getValor();
        from = user;
        password = dao.getAppConfigId(AppConfig.PASS_EMAIL).getValor();
        smtp = dao.getAppConfigId(AppConfig.SMTP_EMAIL_SSL).getValor();
    }
}
