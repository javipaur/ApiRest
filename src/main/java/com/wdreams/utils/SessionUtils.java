/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author victor
 */
public class SessionUtils {
    
    
    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }
    
    public static Object getAttribute(String name){
        return session().getAttribute(name);
    }
    
    public static void setAttribute(String name, Object object){
        session().setAttribute(name,object);
    }
    
    public static void removeAttribute(String nombre){
        session().removeAttribute(nombre);
    }
}
