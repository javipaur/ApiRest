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
public class QueryIngresosDetalles extends QueryIngresosBuilder{

    @Override
    protected String buildSelect() {
        return "i";
    }

    @Override
    protected String buildGroupBy() {
        return null;
    }

    @Override
    protected String buildOrderby() {
        return "fechaIngreso";
    }
    
}
