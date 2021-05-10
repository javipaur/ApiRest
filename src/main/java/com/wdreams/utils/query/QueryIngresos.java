/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.query;

import java.util.Date;
import java.util.List;

import com.wdreams.model.dao.entity.Ambito;
import com.wdreams.model.dao.entity.LiquidacionAmbito;
import com.wdreams.utils.Constantes;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author rosa
 */
public class QueryIngresos {
    
    private List<LiquidacionAmbito> liquidacionesAmbito;
    private String numeroIngreso;
    private Ambito ambito;
    private String entidadFinanciera;
    private Date fechaIngresoInicial;
    private Date fechaIngresoFinal;
    private Integer primerResultado;
    private QueryIngresosBuilder queryBuilder;

    public QueryIngresos(QueryIngresosBuilder queryBuilder, List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal, Integer primerResultado) {
        this.liquidacionesAmbito = liquidacionesAmbito;
        this.numeroIngreso = numeroIngreso;
        this.ambito = ambito;
        this.entidadFinanciera = entidadFinanciera;
        this.fechaIngresoInicial = fechaIngresoInicial;
        this.fechaIngresoFinal = fechaIngresoFinal;
        this.primerResultado = primerResultado;
        this.queryBuilder=queryBuilder;
    }
    
    private Query addProperties(Query query) {
        if (liquidacionesAmbito != null && liquidacionesAmbito.size()>0) {
            query.setParameterList("liquidacionesAmbito", liquidacionesAmbito);
        }
        if (numeroIngreso!=null && !numeroIngreso.equals("")) {
            query.setString("numeroIngreso", numeroIngreso);
        }
        if (ambito != null) {
            query.setParameter("ambito", ambito);
        }
        if (entidadFinanciera != null && !entidadFinanciera.equals("")) {
            query.setString("entidadFinanciera", entidadFinanciera);
        }
        if (fechaIngresoInicial != null && fechaIngresoFinal != null) {
            query.setParameter("fechaIngresoInicial", fechaIngresoInicial);
            query.setParameter("fechaIngresoFinal", fechaIngresoFinal);
        }
        return query;
    }
    
    public Query creareQuery(Session session) {
        Query query = this.addProperties(session.createQuery(this.queryBuilder.buildQuery(liquidacionesAmbito, numeroIngreso, ambito, entidadFinanciera, fechaIngresoInicial, fechaIngresoFinal)));
        if (primerResultado != null) {
            query.setFirstResult(primerResultado);
            query.setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA);
        }
        return query;
    }
}
