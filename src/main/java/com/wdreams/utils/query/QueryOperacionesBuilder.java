/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.query;


import com.wdreams.model.dao.entity.*;

import java.util.Date;
import java.util.List;


/**
 *
 * @author victor
 */
public abstract class QueryOperacionesBuilder {

    protected From from = new From();
    protected Where where = new Where();

    protected abstract String buildSelect(Integer tipoOperacion);

    protected String buildFromWhere(Integer operacion, String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, TipoCiudadano tipoCiudadano, Bono bono, Descuento descuento, List<Aviso> aviso, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion) {

        if (operacion == QueryOperaciones.OPERACION_COMPRA) {
            this.from.addEntity("Compra o");
        }
        if (operacion == QueryOperaciones.OPERACION_RECARGA) {
            this.from.addEntity("Recarga o");
        }
        if (operacion == QueryOperaciones.OPERACION_ACCESO) {
            this.from.addEntity("Acceso o");
        }
        if (operacion == QueryOperaciones.OPERACION_USO_BONO) {
            this.from.addEntity("UsoBono o");
        }
        

        if (idUso != null && !idUso.equals("")) {
            this.where.addAndRestriction("lower(o.idUso) like lower(:idUso)");
        }
        if (centro == null && ambito != null && !ambito.getCentros().isEmpty()) {
            this.where.addAndRestriction("o.centro in :centros");
        }

        if (centro != null || (centro == null && ambito != null && ambito.getCentros().isEmpty())) {
            this.where.addAndRestriction("o.centro = :centro");
        }
        if (buscarPorDispositivo) {
            if (dispositivo == null) {
                this.where.addAndRestriction("o.dispositivo is null");
            } else {
                this.where.addAndRestriction("o.dispositivo = :dispositivo");
            }
        }
        if (fechaRealizacionInicial != null && fechaRealizacionFinal != null) {
            this.where.addAndRestriction("o.fechaRealizacionInt between :fechaRealizacionInicial AND :fechaRealizacionFinal");
        }

        if (numeroOperacion != null && !numeroOperacion.equals("")) {
            if (eqNumeroOperacion) {
                this.where.addAndRestriction("lower(o.numeroOperacion) = lower(:numeroOperacion)");
            } else {
                this.where.addAndRestriction("lower(o.numeroOperacion) like lower(:numeroOperacion)");
            }
        }

        if (tipoCiudadano != null) {
            this.where.addAndRestriction("o.tipoCiudadano = :tipoCiudadano");
        }
        
//        if (aviso != null && !aviso.isEmpty()) {
//            this.where.addAndRestriction("o.aviso in :avisos");
//        }
        if ((uid != null && !uid.equals("")) || (idTarjeta != null && !idTarjeta.equals("")) || (tipo != null && !tipo.isEmpty()) || (idCiudadano != null && !idCiudadano.equals(""))) {
            if (tipo == null || tipo.equals("")) {
                this.from.addEntity("Tarjeta t");
            } else if (tipo.equals("PRE")) {
                this.from.addEntity("TarjetaPrepago t");
            } else if (tipo.equals("POS")) {
                this.from.addEntity("TarjetaPospago t");
            }

            this.where.addAndRestriction("o.tarjeta=t");
            if (idCiudadano != null && !idCiudadano.equals("")) {
                this.from.addEntity("Entidad ci");
                this.where.addAndRestriction("t.entidad=ci").addAndRestriction("lower(ci.idEntidad) like lower(:idCiudadano)");
            }
            if (idTarjeta != null && !idTarjeta.equals("")) {
                this.where.addAndRestriction("lower(t.idTarjeta) like lower(:idTarjeta)");
            }

            if (uid != null && !uid.equals("")) {
                this.where.addAndRestriction("lower(t.uid) like lower(:uid)");
            }
        }
        
        if(bono!=null){
            this.from.addEntity("AsignacionBono ab");
            this.where.addAndRestriction("o.asignacionBono=ab").addAndRestriction("ab.bono = :bono");
        }
        
        if (descuento != null) {
            this.where.addAndRestriction("o.descuento = :descuento");
        }
        
        if (buscarPorLiquidacion) {
            if (liquidacion == null) {
                this.where.addAndRestriction("o.liquidacion is null");
            } else {
                this.where.addAndRestriction("o.liquidacion = :liquidacion");
            }
        }
        if (buscarSaldoLeido) {
            this.where.addAndRestriction("o.saldoFinal is not null");
        }
        if (buscarSinSaldoLeido) {
            this.where.addAndRestriction("o.saldoFinal is null");
        }

        if (!buscaAnuladas) {
            this.where.addAndRestriction("o.idOperacionAnulada is null");
            this.where.addAndRestriction("o.id not in (SELECT oa.idOperacionAnulada FROM " + (operacion == QueryOperaciones.OPERACION_COMPRA ? "Compra oa" : (operacion == QueryOperaciones.OPERACION_RECARGA ? "Recarga oa" : "Acceso oa")) + " WHERE oa.idOperacionAnulada is not null)");
        }

        if (!where.getWhere().equals("")) {
            return " FROM " + from.getFrom() + " WHERE " + where.getWhere();
        } else {
            return " FROM " + from.getFrom();
        }
    }

    protected abstract String buildGroupBy();

    protected abstract String buildOrderby();

    public String buildQuery(Integer operacion, String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, TipoCiudadano tipoCiudadano,Bono bono, Descuento descuento, List<Aviso> aviso, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion) {
//        return "SELECT " + this.buildSelect(operacion)
//                + this.buildFromWhere(operacion, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, tipoCiudadano, bono, descuento, aviso, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas,eqNumeroOperacion)
//                + (this.buildGroupBy() == null ? "" : " GROUP BY " + this.buildGroupBy())
//                + (this.buildOrderby() == null ? "" : " ORDER BY " + this.buildOrderby());
        String consulta =  "SELECT " + this.buildSelect(operacion)
                + this.buildFromWhere(operacion, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, tipoCiudadano, bono, descuento, aviso, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas,eqNumeroOperacion)
                + (this.buildGroupBy() == null ? "" : " GROUP BY " + this.buildGroupBy())
                + (this.buildOrderby() == null ? "" : " ORDER BY " + this.buildOrderby());


        return consulta;
    }



    public String buildQueryWithOrden(Integer operacion, String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, TipoCiudadano tipoCiudadano,Bono bono, Descuento descuento, List<Aviso> aviso, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion) {
        return "SELECT " + this.buildSelect(operacion)
                + this.buildFromWhere(operacion, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, tipoCiudadano, bono, descuento, aviso, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas,eqNumeroOperacion)
                + (this.buildGroupBy() == null ? " ORDER BY o.:campo :dir" : " GROUP BY " + this.buildGroupBy())
                ;
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
