/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author rosa
 */
public class PropertiesUtils {
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private static Properties infoError = new Properties();
    private static Properties infoMensaje = new Properties();
    private static final String PROPERTIES_MSG="mensajes.properties";
    private static final String PROPERTIES_ERROR="ValidationMessages.properties";
    private static final String PROPERTIES_CMS="CMS_Config.properties";
    
    public static String getPropiedadError(String propiedad){
        try{
            infoError.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_ERROR));
            return infoError.getProperty(propiedad);
        }catch(Exception e){
            return null;
        }
    }
    public static String getPropiedadMensaje(String propiedad){
        try{
            infoMensaje.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_MSG));
            return infoMensaje.getProperty(propiedad);
        }catch(Exception e){
            return null;
        }
    }
    public static String getPropiedadCMS(String propiedad){
        try{
            infoMensaje.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_CMS));
            return infoMensaje.getProperty(propiedad);
        }catch(Exception e){
            return null;
        }
    }


    public static String getPropiedadMensajeSource(String propiedad) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(propiedad, null, locale);
        } catch (Exception e) {
            return null;
        }
    }
}
