/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.query;

import java.util.Date;

import com.wdreams.model.dao.entity.Ambito;
import com.wdreams.model.dao.entity.EstadoLiquidacion;
import com.wdreams.utils.Constantes;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author rosa
 */
public class QueryLiquidaciones {
    
    private Integer tipoLiquidacion;
    private String idLiquidacion;
    private Ambito ambito;
    private EstadoLiquidacion estadoLiquidacion;
    private Date fechaLiquidacionInicial;
    private Date fechaLiquidacionFinal;
    private Integer primerResultado;
    private QueryLiquidacionesBuilder queryBuilder;

    public QueryLiquidaciones(QueryLiquidacionesBuilder queryBuilder, Integer tipoLiquidacion, String idLiquidacion, Ambito ambito, EstadoLiquidacion estadoLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal,Integer primerResultado) {
        this.tipoLiquidacion = tipoLiquidacion;
        this.idLiquidacion = idLiquidacion;
        this.ambito = ambito;
        this.estadoLiquidacion = estadoLiquidacion;
        this.fechaLiquidacionInicial = fechaLiquidacionInicial;
        this.fechaLiquidacionFinal = fechaLiquidacionFinal;
        this.primerResultado = primerResultado;
        this.queryBuilder=queryBuilder;
    }
    
    private Query addProperties(Query query) {
        if (idLiquidacion!=null && !idLiquidacion.equals("")) {
            query.setString("idLiquidacion", idLiquidacion);
        }
        if (ambito != null) {
            query.setParameter("ambito", ambito);
        }
        if (estadoLiquidacion != null) {
            query.setParameter("estadoLiquidacion", estadoLiquidacion);
        }
        if (fechaLiquidacionInicial != null && fechaLiquidacionFinal != null) {
            query.setParameter("fechaLiquidacionInicial", fechaLiquidacionInicial);
            query.setParameter("fechaLiquidacionFinal", fechaLiquidacionFinal);
        }
        return query;
    }
    
    public Query creareQuery(Session session) {
        Query query = this.addProperties(session.createQuery(this.queryBuilder.buildQuery(tipoLiquidacion,idLiquidacion, ambito, estadoLiquidacion, fechaLiquidacionInicial, fechaLiquidacionFinal)));
        if (primerResultado != null) {
            query.setFirstResult(primerResultado);
            query.setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA);
        }
        return query;
    }
}
