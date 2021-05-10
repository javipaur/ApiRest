/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;



import com.wdreams.model.dao.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author victor
 */
public interface Enviable {
    public abstract String getEmailEnviable(Dao dao);
    public abstract String getText(Dao dao) throws FileNotFoundException, IOException;
    public abstract String getSubject(Dao dao);
    public abstract List<String> getAdjuntos(Dao dao);
}
