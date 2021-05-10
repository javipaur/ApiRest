/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;

import com.wdreams.model.dao.Dao;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author victor
 */
public abstract class InformacionTarjetaStrategy {
    
    protected Dao dao;
    protected EntradaTarjetaListado.EntradaTarjetaListadoParser entrada;
    
    public  InformacionTarjetaStrategy(Dao dao,EntradaTarjetaListado.EntradaTarjetaListadoParser entrada){
        this.entrada=entrada;
        this.dao=dao;
    }
    
    public abstract List<Tarjeta> informacionTarjetas();
    
    public abstract DetachedCriteria restrictionsToCriteria(DetachedCriteria dc);
    
}
