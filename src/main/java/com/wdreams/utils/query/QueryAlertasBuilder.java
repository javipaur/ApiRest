/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.utils.query;

import java.util.Date;

/**
 *
 * @author victor
 */
public abstract class QueryAlertasBuilder {
    
    protected From from =new From();
    protected Where where = new Where();
    
    protected abstract String buildSelect();
    
    protected String buildFromWhere(String idAlerta, String idTarjeta, Date fechaInicial, Date fechaFinal, String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido, String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido, Date fechaActualizacionInicial, Date fechaActualizacionFinal) {
        this.from.addEntity("Alerta a");        
        if (idAlerta!=null && !idAlerta.equals("")) {
            this.where.addAndRestriction("a.idAlerta = :idAlerta)");
        }
        if (idTarjeta != null && !idTarjeta.equals("")) {
            this.from.addEntity("Tarjeta t");
            this.where.addAndRestriction("a.tarjeta=t");
            this.where.addAndRestriction("t.idTarjeta = :idTarjeta");
        }
        if (fechaInicial != null && fechaFinal != null) {
            this.where.addAndRestriction("o.fecha between :fechaInicial AND :fechaFinal");
        }
        if ( (idCompraSaldoInicialLeido != null && !idCompraSaldoInicialLeido.equals("")) || (idCompraSaldoFinalLeido != null && !idCompraSaldoFinalLeido.equals(""))) {
            this.from.addEntity("Compra c");
            if (idCompraSaldoInicialLeido != null && !idCompraSaldoInicialLeido.equals("")) {
                this.where.addAndRestriction("a.compraSaldoInicialLeido = c");
                this.where.addAndRestriction("c.idUso = :idCompraSaldoInicialLeido");
            }
            if (idCompraSaldoFinalLeido != null && !idCompraSaldoFinalLeido.equals("")) {
                this.where.addAndRestriction("a.compraSaldoFinalLeido = c");
                this.where.addAndRestriction("c.idUso = :idCompraSaldoFinalLeido");
            }
        }
        if( (idRecargaSaldoInicialLeido != null && !idRecargaSaldoInicialLeido.equals("")) || (idRecargaSaldoFinalLeido != null && !idRecargaSaldoFinalLeido.equals(""))) {
            this.from.addEntity("Recarga c");
            if(idRecargaSaldoInicialLeido != null && !idRecargaSaldoInicialLeido.equals("")){
                this.where.addAndRestriction("a.recargaSaldoInicialLeido = c");
                this.where.addAndRestriction("c.idUso = :idRecargaSaldoInicialLeido");
            }
            if (idRecargaSaldoFinalLeido != null && !idRecargaSaldoFinalLeido.equals("")) {
                this.where.addAndRestriction("a.recargaSaldoFinalLeido = c");
                this.where.addAndRestriction("c.idUso = :idRecargaSaldoFinalLeido");
            }
        }
        if (fechaActualizacionInicial != null && fechaActualizacionFinal != null) {
            this.where.addAndRestriction("o.fechaActualizacion between :fechaActualizacionInicial AND :fechaActualizacionFinal");
        }
        if(!where.getWhere().equals(""))return " FROM "+from.getFrom()+" WHERE "+where.getWhere();
        else return " FROM "+from.getFrom();
    }
    
    protected abstract String buildGroupBy();
    protected abstract String buildOrderby();
    
    public String buildQuery(String idAlerta, String idTarjeta, Date fechaInicial, Date fechaFinal, String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido, String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido, Date fechaActualizacionInicial, Date fechaActualizacionFinal){
        return "SELECT "+this.buildSelect()
                +this.buildFromWhere(idAlerta, idTarjeta, fechaInicial, fechaFinal, idCompraSaldoInicialLeido, idCompraSaldoFinalLeido, idRecargaSaldoInicialLeido, idRecargaSaldoFinalLeido, fechaActualizacionInicial, fechaActualizacionFinal)
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
