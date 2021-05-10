/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.utils.query;




import com.wdreams.model.dao.entity.Ambito;
import com.wdreams.model.dao.entity.EstadoLiquidacion;

import java.util.Date;

/**
 *
 * @author victor
 */
public abstract class QueryLiquidacionesBuilder {
    
    protected From from =new From();
    protected Where where = new Where();
    
    protected abstract String buildSelect();
    
    protected String buildFromWhere(Integer tipoLiquidacion, String idLiquidacion, Ambito ambito, EstadoLiquidacion estadoLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal) {
        
        this.from.addEntity(tipoLiquidacion == null ? "Liquidacion l" : tipoLiquidacion == 0 ? "LiquidacionCompra l" : "LiquidacionRecarga l");
        this.from.addEntity("LiquidacionAmbito la");
        this.where.addAndRestriction("la.liquidacion=l");
        
        if (idLiquidacion!=null && !idLiquidacion.equals("")) {
            this.where.addAndRestriction("l.idLiquidacion = :idLiquidacion");
        }
        if (ambito != null) {
            this.where.addAndRestriction("la.ambito = :ambito");
        }
        if (estadoLiquidacion != null) {
            this.where.addAndRestriction("la.estadoLiquidacion = :estadoLiquidacion");
        }
        if (fechaLiquidacionInicial != null && fechaLiquidacionFinal != null) {
            this.where.addAndRestriction("l.fecha between :fechaLiquidacionInicial AND :fechaLiquidacionFinal");
        }
        if(!where.getWhere().equals(""))return " FROM "+from.getFrom()+" WHERE "+where.getWhere();
        else return " FROM "+from.getFrom();
    }
    
    protected abstract String buildGroupBy();
    protected abstract String buildOrderby();
    
    public String buildQuery(Integer tipoLiquidacion, String idLiquidacion, Ambito ambito, EstadoLiquidacion estadoLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal){
        return "SELECT "+this.buildSelect()
                +this.buildFromWhere(tipoLiquidacion, idLiquidacion, ambito, estadoLiquidacion, fechaLiquidacionInicial, fechaLiquidacionFinal)
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
