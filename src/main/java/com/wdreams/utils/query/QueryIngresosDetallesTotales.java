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
public class QueryIngresosDetallesTotales extends QueryIngresosBuilder{

    @Override
    protected String buildSelect() {
        return "count(i.id),sum(i.importeIngreso)";
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
