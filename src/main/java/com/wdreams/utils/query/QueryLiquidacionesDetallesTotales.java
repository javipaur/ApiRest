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
public class QueryLiquidacionesDetallesTotales extends QueryLiquidacionesBuilder{

    @Override
    protected String buildSelect() {
        return "count(la.id),sum(la.importeIngresar),sum(la.importeTotal)";
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
