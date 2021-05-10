/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.query;


import java.util.Date;


import com.wdreams.utils.Constantes;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author rosa
 */
public class QueryAlertas {
    
    private String idAlerta;
    private String idTarjeta;
    private Date fechaInicial;
    private Date fechaFinal;
    private Date fechaActualizacionInicial;
    private Date fechaActualizacionFinal;
    private String idCompraSaldoInicialLeido;
    private String idCompraSaldoFinalLeido;
    private String idRecargaSaldoInicialLeido;
    private String idRecargaSaldoFinalLeido;
    private Integer primerResultado;
    private QueryAlertasBuilder queryBuilder;

    public QueryAlertas(QueryAlertasBuilder queryBuilder, String idAlerta, String idTarjeta, Date fechaInicial, Date fechaFinal, String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido, String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido, Date fechaActualizacionInicial, Date fechaActualizacionFinal, Integer primerResultado) {
        this.idAlerta = idAlerta;
        this.idTarjeta = idTarjeta;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.fechaActualizacionInicial = fechaActualizacionInicial;
        this.fechaActualizacionFinal = fechaActualizacionFinal;
        this.idCompraSaldoInicialLeido = idCompraSaldoInicialLeido;
        this.idCompraSaldoFinalLeido = idCompraSaldoFinalLeido;
        this.idRecargaSaldoInicialLeido = idRecargaSaldoInicialLeido;
        this.idRecargaSaldoFinalLeido = idRecargaSaldoFinalLeido;
        this.primerResultado = primerResultado;
        this.queryBuilder=queryBuilder;
    }
    
    private Query addProperties(Query query) {
        if (idAlerta!=null && !idAlerta.equals("")) {
            query.setString("idAlerta", idAlerta);
        }
        if (idTarjeta != null  && !idTarjeta.equals("")) {
            query.setParameter("idTarjeta", idTarjeta);
        }
        if (idCompraSaldoInicialLeido != null && !idCompraSaldoInicialLeido.equals("")) {
            query.setParameter("idCompraSaldoInicialLeido", idCompraSaldoInicialLeido);
        }
        if (idCompraSaldoFinalLeido != null && !idCompraSaldoFinalLeido.equals("")) {
            query.setParameter("idCompraSaldoFinalLeido", idCompraSaldoFinalLeido);
        }
        if (idRecargaSaldoInicialLeido != null && !idRecargaSaldoInicialLeido.equals("")) {
            query.setParameter("idRecargaSaldoInicialLeido", idRecargaSaldoInicialLeido);
        }
        if (idRecargaSaldoFinalLeido != null && !idRecargaSaldoFinalLeido.equals("")) {
            query.setParameter("idRecargaSaldoFinalLeido", idRecargaSaldoFinalLeido);
        }
        if (fechaInicial != null && fechaFinal != null) {
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fechaFinal);
        }
        if (fechaActualizacionInicial != null && fechaActualizacionFinal != null) {
            query.setParameter("fechaActualizacionInicial", fechaActualizacionInicial);
            query.setParameter("fechaActualizacionFinal", fechaActualizacionFinal);
        }
        return query;
    }
    
    public Query creareQuery(Session session) {
        Query query = this.addProperties(session.createQuery(this.queryBuilder.buildQuery(idAlerta, idTarjeta, fechaInicial, fechaFinal, idCompraSaldoInicialLeido, idCompraSaldoFinalLeido, idRecargaSaldoInicialLeido, idRecargaSaldoFinalLeido, fechaActualizacionInicial, fechaActualizacionFinal)));
        if (primerResultado != null) {
            query.setFirstResult(primerResultado);
            query.setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA);
        }
        return query;
    }
}
