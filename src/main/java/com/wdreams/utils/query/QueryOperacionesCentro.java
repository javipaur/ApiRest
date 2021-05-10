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
public abstract class QueryOperacionesCentro extends QueryOperacionesBuilder{

    @Override
    protected String buildGroupBy() {
        return "a,ce";
    }

    @Override
    protected String buildFromWhere(Integer operacion, String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, TipoCiudadano tipoCiudadano, Bono bono, Descuento descuento, List<Aviso> aviso, Liquidacion liquidacion, Boolean busquedaPorLiquidacion, Boolean busquedaPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnulada, Boolean eqNumeroOperacion) {
        from.addEntity("Ambito a");
        from.addEntity("Centro ce");
        where.addAndRestriction("o.centro=ce");
        where.addAndRestriction("ce.ambito=a");
        return super.buildFromWhere(operacion, idCiudadano, idTarjeta,uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion,tipoCiudadano,bono, descuento, aviso, liquidacion, busquedaPorLiquidacion, busquedaPorDispositivo,buscarSaldoLeido,buscarSinSaldoLeido,buscaAnulada,eqNumeroOperacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String buildOrderby() {
        return null;
    }
    
}
