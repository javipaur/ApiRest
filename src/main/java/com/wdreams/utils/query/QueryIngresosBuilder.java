/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.utils.query;



import com.wdreams.model.dao.entity.Ambito;
import com.wdreams.model.dao.entity.LiquidacionAmbito;

import java.util.Date;
import java.util.List;

/**
 *
 * @author rosa
 */
public abstract class QueryIngresosBuilder {
    
    protected From from =new From();
    protected Where where = new Where();
    
    protected abstract String buildSelect();
    
    protected String buildFromWhere(List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal) {
        this.from.addEntity("Ingreso i");
        if (liquidacionesAmbito != null && liquidacionesAmbito.size()>0) {
            this.where.addAndRestriction("i.liquidacionAmbito in :liquidacionesAmbito");
        }
        if (numeroIngreso!=null && !numeroIngreso.equals("")) {
            this.where.addAndRestriction("i.numeroIngreso = :numeroIngreso");
        }
        if (ambito != null) {
            this.where.addAndRestriction("i.ambito = :ambito");
        }
        if (entidadFinanciera != null && !entidadFinanciera.equals("")) {
            this.where.addAndRestriction("i.entidadFinanciera = :entidadFinanciera");
        }
        if (fechaIngresoInicial != null && fechaIngresoFinal != null) {
            this.where.addAndRestriction("l.fecha between :fechaIngresoInicial AND :fechaIngresoFinal");
        }
        if(!where.getWhere().equals(""))return " FROM "+from.getFrom()+" WHERE "+where.getWhere();
        else return " FROM "+from.getFrom();
    }
    
    protected abstract String buildGroupBy();
    protected abstract String buildOrderby();
    
    public String buildQuery(List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal){
        return "SELECT "+this.buildSelect()
                +this.buildFromWhere(liquidacionesAmbito, numeroIngreso, ambito, entidadFinanciera, fechaIngresoInicial, fechaIngresoFinal)
                +(this.buildGroupBy() == null ?"":" GROUP BY "+this.buildGroupBy())
                +(this.buildOrderby() == null ? "" : " ORDER BY "+this.buildOrderby());
    }
    
    class From {
        private String from = "";
        public void addEntity(String entity) {
            from += (from.equals("") ? entity : ", " + entity);
        }
        public String getFrom() {
            return from;
        }
    }

    class Where {
        private String where = "";
        public Where addAndRestriction(String restriction) {
            where += (where.equals("") ? restriction : " AND " + restriction);
            return this;
        }
        public Where addOrRestriction(String restriction) {
            where += (where.equals("") ? restriction : " OR " + restriction);
            return this;
        }
        public String getWhere() {
            return where;
        }
    }
}
