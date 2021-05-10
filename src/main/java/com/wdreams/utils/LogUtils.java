/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author victor
 */
public class LogUtils {
    
    public static void escribeLog(Logger logger, String nombreMetodo, Exception e) {
        String currentUser = getUserName();
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();

        logger.error("METODO: " + nombreMetodo + ".\nUSUARIO: " + currentUser + ".\nCAUSA: " + e.getMessage() + ".\nTRAZA:" + stacktrace);
    }
    
    public static void escribeLogMensaje(Logger logger, String nombreMetodo, String mensaje) {
        String currentUser = getUserName();
        logger.error("METODO: " + nombreMetodo + ".\nUSUARIO: " + currentUser + ".\nCAUSA: " + mensaje);
    }

    public static void escribeLogInfo(Logger logger, String nombreMetodo, Exception e) {
        String currentUser = getUserName();
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();

        logger.info("METODO: " + nombreMetodo + ".\nUSUARIO: " + currentUser + ".\nCAUSA: " + e.getMessage() + ".\nTRAZA:" + stacktrace);
    }

    public static void escribeLogMensajeInfo(Logger logger, String nombreMetodo, String mensaje) {
        String currentUser = getUserName();
        logger.info("METODO: " + nombreMetodo + ".\nUSUARIO: " + currentUser + ".\nCAUSA: " + mensaje);
    }
    
    public static void escribeLogRow(Logger logger, String nombreMetodo, String mensaje) {
        logger.error(nombreMetodo+"\n"+mensaje);
    }
    
    public static String getUserName() {

        String currentUser;
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            currentUser = "anonymous";
        } else {
            currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        return currentUser;
    }
    
    
}
