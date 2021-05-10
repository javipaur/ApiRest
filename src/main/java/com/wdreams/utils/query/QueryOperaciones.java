/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.query;

import java.util.Date;
import java.util.List;

import com.wdreams.model.dao.entity.*;
import com.wdreams.utils.Constantes;
import com.wdreams.utils.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author rosa
 */
public class QueryOperaciones {

    public static Integer OPERACION_COMPRA = 1;
    public static Integer OPERACION_RECARGA = 2;
    public static Integer OPERACION_ACCESO = 3;
    public static Integer OPERACION_USO_BONO = 4;

    private Integer operacion;
    private String idCiudadano;
    private String uid;
    private String idTarjeta;
    private String tipo;
    private String idUso;
    private Ambito ambito;
    private Centro centro;
    private Dispositivo dispositivo;
    private Date fechaRealizacionInicial;
    private Date fechaRealizacionFinal;
    private String numeroOperacion;
    private List<Aviso> aviso;
    private TipoCiudadano tipoCiudadano;
    private Bono bono;
    Descuento descuento;
    private Liquidacion liquidacion;
    private Boolean buscarPorLiquidacion;
    private Boolean buscarPorDispositivo;
    private Boolean buscarSaldoLeido;
    private Boolean buscarSinSaldoLeido;
    private Boolean buscaAnuladas;
    private Integer primerResultado;
    private QueryOperacionesBuilder queryBuilder;
    private Boolean eqNumeroOperacion = Boolean.FALSE;

    public QueryOperaciones(QueryOperacionesBuilder queryBuilder, Integer operacion, String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, TipoCiudadano tipoCiudadano, Bono bono, Descuento descuento, List<Aviso> aviso, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion, Integer primerResultado) {
        this.operacion = operacion;
        this.idCiudadano = idCiudadano;
        this.uid = uid;
        this.tipo = tipo;
        this.idUso = idUso;
        this.ambito = ambito;
        this.centro = centro;
        this.dispositivo = dispositivo;
        this.fechaRealizacionInicial = fechaRealizacionInicial;
        this.fechaRealizacionFinal = fechaRealizacionFinal;
        this.numeroOperacion = numeroOperacion;
        this.tipoCiudadano = tipoCiudadano;
        this.bono = bono;
        this.descuento = descuento;
        this.idTarjeta = idTarjeta;
        this.aviso = aviso;
        this.liquidacion = liquidacion;
        this.buscarPorLiquidacion = buscarPorLiquidacion;
        this.buscarPorDispositivo = buscarPorDispositivo;
        this.buscarSaldoLeido = buscarSaldoLeido;
        this.buscarSinSaldoLeido = buscarSinSaldoLeido;
        this.primerResultado = primerResultado;
        this.buscaAnuladas = buscaAnuladas;
        this.queryBuilder = queryBuilder;
        this.eqNumeroOperacion = eqNumeroOperacion;

    }

    private Query addProperties(Query query) {
        if ((idTarjeta != null && !idTarjeta.equals("")) || (uid != null && !uid.equals("")) || tipo != null || (idCiudadano != null && !idCiudadano.equals(""))) {
            if (idCiudadano != null && !idCiudadano.equals("")) {
                query.setString("idCiudadano", idCiudadano + "%");
            }
            if (idTarjeta != null && !idTarjeta.equals("")) {
                query.setString("idTarjeta", idTarjeta + "%");
            }

            if (uid != null && !uid.equals("")) {
                query.setString("uid", uid + "%");
            }
        }
        if (idUso != null && !idUso.equals("")) {
            query.setString("idUso", idUso + "%");
        }
        if (centro == null && ambito != null && !ambito.getCentros().isEmpty()) {
            query.setParameterList("centros", ambito.getCentros());
        }

        if (centro == null && ambito != null && ambito.getCentros().isEmpty()) {
            query.setParameter("centro", null);
        }

        if (centro != null) {
            query.setParameter("centro", centro);
        }
        if (buscarPorDispositivo) {
            if (dispositivo != null) {
                query.setParameter("dispositivo", dispositivo);
            }
        }
        if (fechaRealizacionInicial != null && fechaRealizacionFinal != null) {
            query.setParameter("fechaRealizacionInicial", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaRealizacionInicial)));
            query.setParameter("fechaRealizacionFinal", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaRealizacionFinal)));
        }

        if (numeroOperacion != null && !numeroOperacion.equals("")) {
            if (eqNumeroOperacion) {
                query.setString("numeroOperacion", numeroOperacion);
            } else {
                query.setString("numeroOperacion", numeroOperacion + "%");
            }
        }

        if (tipoCiudadano != null) {
            query.setParameter("tipoCiudadano", tipoCiudadano);
        }

//        if (aviso != null && !aviso.isEmpty()) {
//            query.setParameterList("avisos", aviso);
//        }
        if (buscarPorLiquidacion) {
            if (liquidacion != null) {
                query.setParameter("liquidacion", liquidacion);
            }
        }

        if (bono != null) {
            query.setParameter("bono", bono);
        }

        if (descuento != null) {
            query.setParameter("descuento", descuento);
        }

        return query;
    }

    public Query creareQuery(Session session) {
        Query query = this.addProperties(
                session.createQuery(
                        this.queryBuilder.buildQuery(operacion, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, tipoCiudadano, bono, descuento, aviso, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas, eqNumeroOperacion)
                ));

        if (primerResultado != null) {
            query.setFirstResult(primerResultado);
            query.setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA);
        }
        return query;
    }


    //-----------------------------------------------------------------------

    /**
     * SACADO DE STRINGESCAPEUTILS VERSI�N POSTERIOR
     * <p>
     * 675         * <p>Escapes the characters in a <code>String</code> to be suitable to pass to
     * 676         * an SQL query.</p>
     * 677         *
     * 678         * <p>For example,
     * 679         * <pre>statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" +
     * 680         *   StringEscapeUtils.escapeSql("McHale's Navy") +
     * 681         *   "'");</pre>
     * 682         * </p>
     * 683         *
     * 684         * <p>At present, this method only turns single-quotes into doubled single-quotes
     * 685         * (<code>"McHale's Navy"</code> => <code>"McHale''s Navy"</code>). It does not
     * 686         * handle the cases of percent (%) or underscore (_) for use in LIKE clauses.</p>
     * 687         *
     * 688         * see http://www.jguru.com/faq/view.jsp?EID=8881
     * 689         * @param str  the string to escape, may be null
     * 690         * @return a new String, escaped for SQL, <code>null</code> if null string input
     * 691
     */
    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    public Query crearQueryForOrder(Session session, String campo, String dir) {

        String sql = this.queryBuilder.buildQueryWithOrden(operacion, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, tipoCiudadano, bono, descuento, aviso, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas, eqNumeroOperacion);
        try {
            campo = escapeSql(campo);
            dir = escapeSql(dir);
            sql = sql.replace(":campo", campo);
            sql = sql.replace(":dir", dir);
        }
        catch (Exception e)
        {
            //Si fallase por lo que sea, campo dir... lo que sea, devuelve resultados
        }
        Query query = this.addProperties(
                session.createQuery(
                        sql
                ));

        if (primerResultado != null) {
            query.setFirstResult(primerResultado);
            query.setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA);
        }


        return query;
    }
}
