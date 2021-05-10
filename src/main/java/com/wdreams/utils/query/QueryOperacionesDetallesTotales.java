/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.utils.query;

/**
 *
 * @author victor
 */
public class QueryOperacionesDetallesTotales extends QueryOperacionesBuilder{



    @Override
    protected String buildSelect(Integer tipoOperacion) {
        if(tipoOperacion == QueryOperaciones.OPERACION_COMPRA)
            return "count(o.id),sum(o.importe), sum(o.importeOriginal)";
        else if(tipoOperacion == QueryOperaciones.OPERACION_RECARGA)
            return "count(o.id),sum(o.importe), (SELECT count(*) from Recarga where esRecargaSaldoVirtual = true), (SELECT sum(importe) from Recarga where esRecargaSaldoVirtual = true)";
        else
            return "count(o.id)";
    }

    @Override
    protected String buildGroupBy() {
        return null;
    }

    @Override
    protected String buildOrderby() {
        return null;
    }

    
}
