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
public class QueryAlertasDetallesTotales extends QueryAlertasBuilder{

    @Override
    protected String buildSelect() {
        return "count(a.id),sum(a.saldoInicialLeido),sum(a.saldoFinalLeido),sum(a.importeCompras),sum(a.importeRecargas)";
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
