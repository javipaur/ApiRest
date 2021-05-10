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
public  class QueryOperacionesCentrosTotales extends QueryOperacionesCentro{

    @Override
    protected String buildSelect(Integer tipoOperacion) {
        return "count(*)";
    }

    
    
}
