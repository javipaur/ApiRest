package com.wdreams.model.dao;

import com.wdreams.model.dao.entity.*;
import com.wdreams.utils.Constantes;
import com.wdreams.utils.DateUtils;
import com.wdreams.utils.LogUtils;
import com.wdreams.utils.OrdenBySession;
import com.wdreams.utils.query.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DaoImpl implements Dao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager em;


    public Session currentSession() {
        return em.unwrap(Session.class).getSession();

    }



    public DetachedCriteria setOrdenListado(DetachedCriteria dc, OrdenBySession.LISTADO_TIPOS tipoListado) {
        try {
            OrdenBySession gestor = new OrdenBySession();
            OrdenBySession.SessionFilters filtro = gestor.getSessionFilter(tipoListado);

            if (filtro != null) {

//            if(filtro.getCampo().indexOf('.') != -1)
//            {
//                //Estamos buscando un camo relacionado, meto ñapilla
//                String tablaRelacionada = filtro.getCampo().split("\\.")[0];
////                String campoRelacionado = filtro.getCampo().split(".")[n];
//                dc.createAlias(tablaRelacionada, tablaRelacionada);//En el código todos los l
//            }

                switch (filtro.getOrden()) {
                    case ASC:
                        dc.addOrder(Order.asc(filtro.getCampo()).nulls(NullPrecedence.LAST));
                        break;
                    case DESC:
                        dc.addOrder(Order.desc(filtro.getCampo()).nulls(NullPrecedence.LAST));
                        break;
                }
            }
        } catch (Exception e) {
            //Por si acaso
        }
        return dc;
    }


    // -- RESET PASSWORD CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public PasswordResetToken guardarPassword(PasswordResetToken resetToken) {
        this.currentSession().save(resetToken);
        return resetToken;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PasswordResetToken deleteToken(PasswordResetToken resetToken) {
        this.currentSession().delete(resetToken);
        return resetToken;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PasswordResetToken getResetTokenByToken(String token) {
        PasswordResetToken prt = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(PasswordResetToken.class);
            dc.add(Restrictions.eq("token", token));
            prt = (PasswordResetToken) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            LogUtils.escribeLog((org.apache.log4j.Logger) logger, "DAOiml.getEntidadIdEntidad", ex);
            logger.error("DAOiml.getEntidadIdEntidad", ex);
        }
        return prt;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassCiudadanoByUsername(String password, String username) {
        String hqlUpdate = "update User c set c.password = :password where c.username = :username";
        Query query = currentSession().createQuery(hqlUpdate).setParameter("password", password).setParameter("username", username);
        query.executeUpdate();

    }

    // -- ACCESO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Acceso anotarAcceso(Acceso acceso) {
        acceso.setIdUso("");
        this.currentSession().save(acceso);
        acceso.asignarIdUso();
        this.currentSession().update(acceso);
        return acceso;
    }

    @Transactional(propagation = Propagation.REQUIRED)
     public Boolean existeNumeroOperacionCompra(Centro centro, Dispositivo dispositivo, String numeroOperacion) {
     try {
     DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
     dc.add(Restrictions.eq("centro", centro));
     if(dispositivo!=null){
     dc.add(Restrictions.eq("dispositivo", dispositivo));
     }
     dc.add(Restrictions.eq("numeroOperacion", numeroOperacion));
     if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
     return Boolean.TRUE;
     }
     } catch (DataAccessException ex) {
         logger.error( "DAOiml.existeNumeroOperacionCompra", ex);
     return null;
     }
     return Boolean.FALSE;
     }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public List<Acceso> getAccesosListado(String idCiudadano, String idTarjeta,
//                                          String uid, String tipo,
//                                          String idUso, Ambito ambito,
//                                          Centro centro, Dispositivo dispositivo,
//                                          Date fechaRealizacionInicial, Date fechaRealizacionFinal,
//                                          String numeroOperacion, List<Aviso> avisos,
//                                          Boolean buscaAnulada, Boolean eqNumeroOperacion,
//                                          Integer primerResultado
//    ) {
//        List<Acceso> accesos = new ArrayList<Acceso>();
//        try {
//            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetalles(), QueryOperaciones.OPERACION_ACCESO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, buscaAnulada, eqNumeroOperacion, primerResultado);
//
//            OrdenBySession gestor = new OrdenBySession();
//            OrdenBySession.SessionFilters filtro = gestor.getSessionFilter(OrdenBySession.LISTADO_TIPOS.USOS_ACCESOS);
//
//            if (filtro != null) {
//                switch (filtro.getOrden()) {
//                    case ASC:
//                        accesos = qC.crearQueryForOrder(currentSession(), filtro.getCampo(), "ASC").list();
//                        break;
//                    case DESC:
//                        accesos = qC.crearQueryForOrder(currentSession(), filtro.getCampo(), "DESC").list();
//                        break;
//                }
//            } else {
//                accesos = qC.creareQuery(currentSession()).list();
//            }
//
////            accesos = qC.creareQuery(currentSession()).list();
//        } catch (Exception ex) {
//            logger.error( "DAOiml.getAccesosListado", ex);
//        }
//        return accesos;
//    }
//
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAccesosNumero(String idCiudadano, String idTarjeta,
                                 String uid, String tipo,
                                 String idUso, Ambito ambito,
                                 Centro centro, Dispositivo dispositivo,
                                 Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                 String numeroOperacion, List<Aviso> avisos,
                                 Boolean eqNumeroOperacion
    ) {
        Long numeroAccesos = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetallesTotales(), QueryOperaciones.OPERACION_ACCESO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroAccesos = (Long) qC.creareQuery(currentSession()).list().get(0);
        } catch (Exception ex) {
            logger.error( "DAOiml.getAccesosNumero", ex);
        }
        return numeroAccesos;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAccesosAmbitoNumero(String idCiudadano, String idTarjeta,
                                       String uid, String tipo,
                                       String idUso, Ambito ambito,
                                       Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                       List<Aviso> avisos, Boolean eqNumeroOperacion
    ) {
        Long numeroAccesos = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesAmbitoTotales(), QueryOperaciones.OPERACION_ACCESO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, null, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroAccesos = Long.valueOf(qC.creareQuery(currentSession()).list().size());
        } catch (Exception ex) {
            logger.error( "DAOiml.getAccesosAmbitoNumero", ex);
        }
        return numeroAccesos;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAccesosCentroNumero(String idCiudadano, String idTarjeta,
                                       String uid, String tipo,
                                       String idUso, Ambito ambito,
                                       Centro centro, Date fechaRealizacionInicial,
                                       Date fechaRealizacionFinal, List<Aviso> avisos,
                                       Boolean eqNumeroOperacion
    ) {
        Long numeroAccesos = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesCentrosTotales(), QueryOperaciones.OPERACION_ACCESO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroAccesos = Long.valueOf(qC.creareQuery(currentSession()).list().size());
        } catch (Exception ex) {
            logger.error( "DAOiml.getAccesosCentroNumero", ex);
        }
        return numeroAccesos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeAccesoAnulado(Integer idOperacion
    ) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Acceso.class);
            dc.add(Restrictions.eq("idOperacionAnulada", idOperacion));
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeAccesoAnulado", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    // -- ACCION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaAccion(Accion accion
    ) {
        //accion.setIdAccion("");
        this.currentSession().save(accion);
        accion.asignarIdAccion();
        this.currentSession().update(accion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAccionesNumero(TipoAccion tipoAccion, TipoEntidad tipoEntidad,
                                  Date fechaInicial, Date fechaFinal,
                                  String username
    ) {
        Long numAcciones = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Accion.class);
            if (tipoAccion != null) {
                dc.add(Restrictions.eq("tipoAccion", tipoAccion));
            }
            if (tipoEntidad != null) {
                dc.add(Restrictions.eq("tipoEntidad", tipoEntidad));
            }
            if (!username.equals("")) {
                dc.createAlias("user", "u");
                dc.add(Restrictions.ilike("u.username", username, MatchMode.START));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            dc.setProjection(Projections.rowCount());
            numAcciones = (Long) DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getAccionesNumero", ex);
        }

        return numAcciones;
    }

    // -- ALERTAS -- //
//    @Transactional(propagation = Propagation.REQUIRED)
//    public List<Alerta> getAlertasListado(String idAlerta, String idTarjeta,
//                                          Date fechaInicial, Date fechaFinal,
//                                          String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido,
//                                          String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido,
//                                          Date fechaActualizacionInicial, Date fechaActualizacionFinal,
//                                          Integer primerResultado
//    ) {
//        List<Alerta> alertas = new ArrayList<Alerta>();
//        try {
//            QueryAlertas qC = new QueryAlertas(new QueryAlertasDetalles(), idAlerta, idTarjeta, fechaInicial, fechaFinal, idCompraSaldoInicialLeido, idCompraSaldoFinalLeido, idRecargaSaldoInicialLeido, idRecargaSaldoFinalLeido, fechaActualizacionInicial, fechaActualizacionFinal, primerResultado);
//            alertas = qC.creareQuery(currentSession()).list();
//        } catch (Exception ex) {
//            logger.error( "DAOiml.getAlertasListado", ex);
//        }
//        return alertas;
//    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Alerta getAlertaTarjetaPeriodo(Tarjeta tarjeta, Date fechaInicial,
                                          Date fechaFinal
    ) {
        Alerta alerta = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Alerta.class);
            dc.add(Restrictions.eq("tarjeta", tarjeta));
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.eq("fechaSaldoInicialLeido", fechaInicial));
                dc.add(Restrictions.eq("fechaSaldoFinalLeido", fechaFinal));
            } else if (fechaInicial != null) {
                dc.add(Restrictions.eq("fechaSaldoInicialLeido", fechaInicial));
            } else {
                dc.add(Restrictions.eq("fechaSaldoFinalLeido", fechaFinal));
            }
            alerta = (Alerta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            //logger.error( "DAOiml.getAlertaTarjetaPeriodo", ex);
            return null;
        }
        return alerta;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Alerta getAlertaIdAlerta(String idAlerta
    ) {
        Alerta alerta = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Alerta.class);
            dc.add(Restrictions.eq("idAlerta", idAlerta));
            alerta = (Alerta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAlertaIdAlerta", ex);
            return null;
        }
        return alerta;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAlerta(Alerta alerta
    ) {
        this.currentSession().delete(alerta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaAlerta(Alerta alerta
    ) {
        this.currentSession().save(alerta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaAlerta(Alerta alerta
    ) {
        this.currentSession().update(alerta);
    }

    // -- AMBITOS -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Ambito getAmbitoId(Integer id
    ) {
        try {
            return (Ambito) this.currentSession().get(Ambito.class, id);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitoId", ex);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Ambito getAmbitoIdAmbito(String idAmbito
    ) {
        Ambito ambito = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            dc.add(Restrictions.eq("idAmbito", idAmbito));
            ambito = (Ambito) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitoIdAmbito", ex);
            return null;
        }
        return ambito;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaAmbito(Ambito ambito
    ) {
        this.currentSession().save(ambito);

        /*Se guarda en bbdd y genera un id -> con el id se crea un idAmito (Axxx)
         -> Se guarda de nuevo el ambito, esta vez con el id con el id. */
        ambito.asignarIdAmbito();
        modificaAmbito(ambito);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaAmbito(Ambito ambito
    ) {
        this.currentSession().merge(ambito);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ambito> getAmbitosUsoSFTP() {
        List<Ambito> ambitos = new ArrayList<Ambito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            dc.add(Restrictions.eq("usoSFTP", Boolean.TRUE));
            ambitos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitosUsoSFTP", ex);
        }
        return ambitos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ambito> getAmbitos() {
        List<Ambito> ambitos = new ArrayList<Ambito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.addOrder(Order.asc("nombre"));
            ambitos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitos", ex);
        }
        return ambitos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ambito> getAmbitosCentros() {
        List<Ambito> ambitos = new ArrayList<Ambito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.add(Restrictions.eq("tipocentro", 0));
            dc.addOrder(Order.asc("nombre"));
            ambitos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitosCentros", ex);
        }
        return ambitos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ambito> getAmbitosComercios() {
        List<Ambito> ambitos = new ArrayList<Ambito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.add(Restrictions.eq("tipocentro", 1));
            dc.addOrder(Order.asc("nombre"));
            ambitos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitosComercios", ex);
        }
        return ambitos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ambito> getAmbitosListado(String idAmbito, Integer primerResultado
    ) {
        List<Ambito> ambitos = new ArrayList<Ambito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            if (!idAmbito.equals("")) {
                dc.add(Restrictions.ilike("idAmbito", idAmbito, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_AMBITOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                ambitos = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                ambitos = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitosListado", ex);
        }
        return ambitos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAmbitosNumero(String idAmbito
    ) {
        Long numeroAmbitos = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            if (!idAmbito.equals("")) {
                dc.add(Restrictions.ilike("idAmbito", idAmbito, MatchMode.START));
            }
            //dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.setProjection(Projections.rowCount());
            numeroAmbitos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAmbitosNumero", ex);
        }
        return numeroAmbitos;
    }

    // -- APP CONFIG -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public AppConfig getAppConfigId(Integer id
    ) {
        return (AppConfig) this.currentSession().get(AppConfig.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AppConfig getAppConfig(AppConfig.ConfigRecargas config) {
        Criteria criteria = this.currentSession().createCriteria(AppConfig.class);
        AppConfig resultado = (AppConfig) criteria.add(Restrictions.eq("nombre", config.name()))
                .uniqueResult();
        return resultado;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setAppConfigId(AppConfig appConfig
    ) {
        this.currentSession().update(appConfig);
    }

    // -- AVISO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Aviso getAvisoId(Integer id
    ) {
        try {
            return (Aviso) this.currentSession().get(Aviso.class, id);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAvisoId", ex);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Aviso getAvisoNumero(Integer numero
    ) {
        Aviso aviso = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Aviso.class);
            dc.add(Restrictions.eq("numero", numero));
            aviso = (Aviso) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getAvisoNumero", ex);
        }
        return aviso;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Aviso> getAvisos() {
        List<Aviso> avisos = new ArrayList<Aviso>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Aviso.class);
            dc.addOrder(Order.asc("numero"));
            avisos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAvisos", ex);
        }
        return avisos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String[] getCentroImagenes(Integer centroId) {
        List<centros_imagenes> centros = new ArrayList<centros_imagenes>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(centros_imagenes.class);
            dc.add(Restrictions.eq("centro.id", centroId));
            centros = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.setCentroImagenes", ex);
        }
        return centros.stream().map(x -> x.getImagen()).toArray(String[]::new);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void setCentroImagenes(Centro centro, List<String> ficherosImagenes) {
        Integer idCentro = centro.getId();
        List<centros_imagenes> centros = new ArrayList<centros_imagenes>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(centros_imagenes.class);
            dc.add(Restrictions.eq("centro.id", idCentro));
            centros = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.setCentroImagenes", ex);
        }
        for (centros_imagenes l : centros) {
            try {
                //Borro las imágenes del sistema
                File f = new File(getAppConfigId(AppConfig.CARPETA_FOTOS_CMS).getValor() + l.getImagen());
                f.delete();
            } catch (Exception e) {
                String debug = e.getMessage();
            }
            this.currentSession().delete(l);
        }
        for (String imagen : ficherosImagenes) {
            centros_imagenes add = new centros_imagenes(getCentroId(idCentro), imagen);
            this.currentSession().save(add);
        }
        //Se finí
    }

    // -- CENTRO --//
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaCentro(Centro centro
    ) {
        this.currentSession().save(centro);
        centro.asignarIdCentro();
        modificaCentro(centro);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaCentro(Centro centro
    ) {
        this.currentSession().update(centro);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Centro getCentroId(Integer id
    ) {
        return (Centro) this.currentSession().get(Centro.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Centro> getCentros() {
        List<Centro> centros = new ArrayList<Centro>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            centros = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentros", ex);
        }
        return centros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Centro> getCentrosListado(String idCentro, Ambito ambito,
                                          Integer primerResultado
    ) {
        List<Centro> centros = new ArrayList<Centro>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }

            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosListado", ex);
        }
        return centros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Centro> getCentrosListado(String idCentro, Ambito ambito, Integer primerResultado, Integer tipo) {
        List<Centro> centros = new ArrayList<Centro>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("class", tipo));
            }
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }
            switch (tipo) {
                case 0:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_CENTROS);
                    break;
                case 1:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_COMERCIOS);
                    break;
            }
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosListado", ex);
        }
        return centros;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<Centro> getCentrosListado(
            String idCentro, Ambito ambito,
            String nombre, Integer categoriaId, Boolean mostrar,
            Integer primerResultado, Integer tipo
    ) {
        List<Centro> centros = new ArrayList<Centro>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("class", tipo));
            }
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }

            /**
             * ADICIONAL
             */
            if (nombre != null && !nombre.isEmpty()) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
            }
            if (categoriaId != null) {
                dc.add(Restrictions.eq("idCategoria", categoriaId));
            }
            if (mostrar != null) {
                dc.add(Restrictions.or(Restrictions.eq("mostrar", mostrar), Restrictions.isNull("mostrar")));
            }


            switch (tipo) {
                case 0:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_CENTROS);
                    break;
                case 1:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_COMERCIOS);
                    break;
            }
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                centros = (List<Centro>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosListado", ex);
        }
        return centros;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCentrosNumero(String idCentro, Ambito ambito,
                                 String nombre, Integer categoriaId, Boolean mostrar,
                                 Integer tipo) {
        Long numeroCentros = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("class", tipo));
            }
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }

            /**
             * ADICIONAL
             */
            if (nombre != null && !nombre.isEmpty()) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
            }
            if (categoriaId != null) {
                dc.add(Restrictions.eq("idCategoria", categoriaId));
            }
            if (mostrar != null) {
                dc.add(Restrictions.or(Restrictions.eq("mostrar", mostrar), Restrictions.isNull("mostrar")));
            }


            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroCentros = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosNumero", ex);
        }
        return numeroCentros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCentrosNumero(String idCentro, Ambito ambito, Integer tipo) {
        Long numeroCentros = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("class", tipo));
            }
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroCentros = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosNumero", ex);
        }
        return numeroCentros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCentrosNumero(String idCentro, Ambito ambito
    ) {
        Long numeroCentros = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            if (!idCentro.equals("")) {
                dc.add(Restrictions.ilike("idCentro", idCentro, MatchMode.START));
            }
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroCentros = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosNumero", ex);
        }
        return numeroCentros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CentroComercio getComercioById(Integer idComercio) {
        CentroComercio centro = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroComercio.class);
            dc.add(Restrictions.eq("id", idComercio));
            centro = (CentroComercio) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getCentroIdCentro", ex);
        }
        return centro;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<CentroComercio> getComercios() {
        List<CentroComercio> centros = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroComercio.class);
            centros = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getComercios", ex);
        }
        return centros;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CentroComercio getComercioIdComercio(String idComercio
    ) {
        CentroComercio centro = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroComercio.class);
            dc.add(Restrictions.eq("idCentro", idComercio));
            centro = (CentroComercio) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());


        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getCentroIdCentro", ex);
        }
        return centro;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Centro getCentroIdCentro(String idCentro
    ) {
        Centro centro = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Centro.class);
            dc.add(Restrictions.eq("idCentro", idCentro));
            centro = (Centro) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getCentroIdCentro", ex);
        }
        return centro;
    }

    // -- CENTRO DE RECOGIDA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public CentroRecogida getCentroRecogidaId(Integer id
    ) {
        return (CentroRecogida) this.currentSession().get(CentroRecogida.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CentroRecogida getCentroRecogidaIdCentroRecogida(String idCentroRecogida
    ) {
        CentroRecogida centroRecogida = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroRecogida.class);
            dc.add(Restrictions.eq("idCentroRecogida", idCentroRecogida));
            centroRecogida = (CentroRecogida) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentroRecogidaIdCentroRecogida", ex);
        }
        return centroRecogida;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CentroRecogida> getCentrosRecogida() {
        List<CentroRecogida> centrosRecogida = new ArrayList<CentroRecogida>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroRecogida.class);
            centrosRecogida = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosRecogida", ex);
        }
        return centrosRecogida;
    }

    // -- CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getEntidadCreacionFechaCantidad(Date fechaIni, Date fechaFin) {
        Long cantidadCiudadanos = 0l;
        try {

            Query query = currentSession().createQuery(
                    "SELECT count(*) "
                            + "FROM Entidad e "
                            + "WHERE "
                            + "e.fechaCreacion between :fechaIni and :fechaFin"
            );

            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);

            cantidadCiudadanos = (Long) query.list().get(0);

            if (cantidadCiudadanos == null) {
                cantidadCiudadanos = Long.valueOf(00);
            }

        } catch (Exception ex) {
            logger.error( "DAOiml.getEntidadCreacionFechaCantidad", ex);
        }
        return cantidadCiudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User getCiudadanoByEmailDniFechaNacimiento(String email, String documento, Date fechaNacimiento) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("mail", email));
            dc.add(Restrictions.eq("documento", documento));
            dc.add(Restrictions.eq("fechaNacimiento", fechaNacimiento));

            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanoByEmailDniFechaNacimiento", ex);
        }
        return entidad;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User getCiudadanoByEmailDni(String email, String documento) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("mail", email));
            dc.add(Restrictions.eq("documento", documento));

            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanoByEmailDni", ex);
        }
        return entidad;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Usuario getComercioByEmailUsername(String email, String documento) {
        Usuario user = null;
        try {
            //Primero  validamos le usuario
            DetachedCriteria dc = DetachedCriteria.forClass(Usuario.class)
                    .createAlias("comercio", "comercio");
            dc.add(Restrictions.eq("mail", email));
            dc.add(Restrictions.eq("documento", documento));
            //Nos retorna el objeto usuario con el comercio asociado segun el mail proporcionado por parametro.
            user = (Usuario) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
            //Comparamos el id comercio que nos pasan por parametro  co nel objeto recuperado si son iguales  ok
//            if(user.getComercio().getIdCentro().equals(documento)){
//                return user;
//
//            }
            //ToDo Si el mail es multiple para un coemrcio validacion comentada.
           /* List<Usuario> entidades = query.list();
            if (!user.isEmpty()) {
                user = entidades.get(0);
            }*/
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComercioByEmailUsername", ex);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getEntidadByEmail(String email) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("mail", email));
            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEntidadByEmail", ex);
        }
        return entidad;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getEntidadByDNI(String documento) {
        Entidad entidad = null;
        try {
//            Query query = null;
//            query = currentSession().createQuery("SELECT e FROM Entidad e WHERE e.documento = :documento");
//            query.setParameter("documento", documento);
//            List<Entidad> entidades = query.list();
//            if (!entidades.isEmpty()) {
//                entidad = entidades.get(0);
//            }
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("documento", documento));
            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEntidadByDNI", ex);
        }
        return entidad;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getEntidadIdEntidad(String idEntidad
    ) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("idEntidad", idEntidad));
            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
           // entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEntidadIdEntidad", ex);
        }
        return entidad;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getEntidadById(int id) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("id", id));
            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEntidadById", ex);
        }
        return entidad;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getCiudadanoId(Integer id
    ) {
        Entidad ciudadano = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("id", id));
            ciudadano = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanoId", ex);
        }
        return ciudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getCiudadanoDocumento(TipoDocumento tipoDocumento, String documento
    ) {
        Entidad ciudadano = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            //dc.add(Restrictions.eq("tipoDocumento", tipoDocumento));
            dc.add(Restrictions.eq("documento", documento));
            ciudadano = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getCiudadanoDocumento", ex);
        }
        return ciudadano;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void altaCiudadano(Entidad ciudadano) {

        ciudadano.setComu(getComuId(1));
        this.currentSession().save(ciudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void modificaCiudadano(Entidad ciudadano) {
        this.currentSession().update(ciudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<Entidad> getCiudadanosListadoComprobarPadron(Integer id, Integer numCiudadanos) {
        List<Entidad> ciudadanos = new ArrayList<Entidad>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.gt("id", id));
            dc.addOrder(Order.asc("id"));
            ciudadanos = dc.getExecutableCriteria(this.currentSession()).setMaxResults(numCiudadanos).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanosListadoComprobarPadron", ex);
        }
        return ciudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getUltimoCiudadano() {
        Entidad ciudadano = new Entidad();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.addOrder(Order.desc("id"));
            ciudadano = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).setMaxResults(1).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUltimoCiudadano", ex);
        }
        return ciudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getEntidadesCriteria(Integer tipoEntidad, String idCiudadano, String nombre, String apellidos, TipoDocumento tipoDocumento, String documento, EstadoCiudadano estado, String idIncidencia, Boolean anonimo) {
        DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);

        if (tipoEntidad != null) {
            dc.add(Restrictions.eq("tipoEntidad", tipoEntidad));
        }

        if (!idCiudadano.equals("")) {
            dc.add(Restrictions.ilike("idEntidad", idCiudadano, MatchMode.START));
        }
        if (!nombre.equals("")) {
            dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
        }
        if (!apellidos.equals("")) {
            dc.add(Restrictions.ilike("apellidos", apellidos, MatchMode.ANYWHERE));
        }
        if (tipoDocumento != null) {
            dc.add(Restrictions.eq("tipoDocumento", tipoDocumento));
        }
        if (!documento.equals("")) {
            dc.add(Restrictions.ilike("documento", documento, MatchMode.START));
        }
        if (estado != null) {
            dc.add(Restrictions.eq("estado", estado));
        }

        if (!idIncidencia.equals("")) {
            dc.createAlias("incidencias", "i");
            dc.add(Restrictions.ilike("i.idIncidencia", idIncidencia, MatchMode.START));
        }
        if (anonimo != null && anonimo == true) {
            dc.add(Restrictions.eq("anonimo", true));
        } else if (anonimo != null && !anonimo) {
            dc.add(Restrictions.eq("anonimo", false));
        }
        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Entidad> getEntidadesListado(Integer tipoEntidad, String idCiudadano, String nombre, String apellidos, TipoDocumento tipoDocumento, String documento, EstadoCiudadano estado, String idIncidencia, Boolean anonimo, Integer primerResultado) {
        List<Entidad> ciudadanos = new ArrayList<Entidad>();
        try {
            DetachedCriteria dc = this.getEntidadesCriteria(tipoEntidad, idCiudadano, nombre, apellidos, tipoDocumento, documento, estado, idIncidencia, anonimo);

            dc.createCriteria("tipoDocumento", JoinType.LEFT_OUTER_JOIN);
            //  dc.createCriteria("tipoDocumentoTutor", JoinType.LEFT_OUTER_JOIN);
            dc.createCriteria("estado", JoinType.LEFT_OUTER_JOIN);
            // dc.createCriteria("tipoTutor", JoinType.LEFT_OUTER_JOIN);
            /* if (!idCiudadano.equals("")) {
             dc.add(Restrictions.ilike("idCiudadano", idCiudadano, MatchMode.START));
             }
             if (!nombre.equals("")) {
             dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
             }
             if (!apellidos.equals("")) {
             dc.add(Restrictions.ilike("apellidos", apellidos, MatchMode.ANYWHERE));
             }
             if (tipoDocumento != null) {
             dc.add(Restrictions.eq("tipoDocumento", tipoDocumento));
             }
             if (!documento.equals("")) {
             dc.add(Restrictions.ilike("documento", documento, MatchMode.START));
             }
             if (estado != null) {
             dc.add(Restrictions.eq("estado", estado));
             }

             if (!idIncidencia.equals("")) {
             dc.createAlias("incidencias", "i");
             dc.add(Restrictions.ilike("i.idIncidencia", idIncidencia, MatchMode.START));
             }*/
//            dc.addOrder(Order.asc("idEntidad"));

            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.CIUDADANOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado != null) {
                ciudadanos = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            } else {
                ciudadanos = dc.getExecutableCriteria(this.currentSession()).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanosListado", ex);
        }
        return ciudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getEntidadesNumero(Integer tipoEntidad, String idCiudadano, String nombre, String apellidos, TipoDocumento tipoDocumento, String documento, EstadoCiudadano estado, String idIncidencia, Boolean anonimo) {
        Long numeroCiudadanos = 0l;
        try {
            DetachedCriteria dc = this.getEntidadesCriteria(tipoEntidad, idCiudadano, nombre, apellidos, tipoDocumento, documento, estado, idIncidencia, anonimo);
            dc.setProjection(Projections.rowCount());
            numeroCiudadanos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCiudadanosNumero", ex);
        }
        return numeroCiudadanos;
    }

    // -- PADRONES -- //

    @Transactional(propagation = Propagation.REQUIRED)
    public List<padrondocumentos> getPadronesListado(TipoDocumento tipoDocumento, String documento, String nombre, String apellidos, Integer primerResultado) {
        List<padrondocumentos> padrones = new ArrayList<padrondocumentos>();
//        try {
//            String cadenaWhereDocumento = " ";
//            Query query = null;
//            if (documento != null && !documento.isEmpty()) {
//                cadenaWhereDocumento = " where p.documento like :documento";
//                query = currentSession().createQuery("SELECT p FROM padrondocumentos p" + cadenaWhereDocumento);
//                query.setParameter("documento", documento);
//            }
//            else {
//                query = currentSession().createQuery("SELECT p FROM padrondocumentos p");
//            }
//
//            if (primerResultado == null) {
//                padrones = query.list();
//            } else {
//                padrones = (List<padrondocumentos>) query.list().stream().skip(primerResultado).limit(Constantes.NUMERO_ELEMENTOS_PAGINA).collect(Collectors.toList());
//            }
//
//        } catch (DataAccessException ex) {
//            logger.error( "DAOiml.getPadronesListado", ex);
//        }

        try {
            DetachedCriteria dc = this.getPadronesCriteria(documento, tipoDocumento, nombre, apellidos);
            if (primerResultado == null) {
                padrones = (List<padrondocumentos>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                padrones = (List<padrondocumentos>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();

                dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.EMPADRONADOS);
                /**
                 * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
                 * en su defecto creo el orden a continuación
                 */
                dc.addOrder(Order.asc("documento"));
                if (primerResultado != null) {
                    padrones = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
                } else {
                    padrones = dc.getExecutableCriteria(this.currentSession()).list();
                }

            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEmpadronadosListado", ex);
        }
        return padrones;

    }

    private DetachedCriteria getPadronesCriteria(String documento, TipoDocumento tipoDocumento, String nombre, String apellidos) {
        DetachedCriteria dc = DetachedCriteria.forClass(padrondocumentos.class);

        if (!documento.equals(
                "")) {
            dc.add(Restrictions.ilike("documento", documento, MatchMode.START));
        }
        if (tipoDocumento
                != null) {
            dc.add(Restrictions.eq("tipoDocumento", tipoDocumento));
        }
        if (!nombre.equals(
                "")) {
            dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
        }

        if (!apellidos.equals(
                "")) {
            dc.add(Restrictions.ilike("apellidos", apellidos, MatchMode.ANYWHERE));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getPadronesNumero(TipoDocumento tipoDocumento, String documento, String nombre, String apellidos) {
        Long numeroEmpadronados = 0l;
//        try {
//            Query query = null;
//            query = currentSession().createQuery("SELECT count(p) FROM padrondocumentos p");
//            numeroEmpadronados = (Long) query.list().get(0);
//        } catch (DataAccessException ex) {
//            logger.error( "DAOiml.getCiudadanosNumero", ex);
//        }
        try {
            DetachedCriteria dc = this.getPadronesCriteria(documento, tipoDocumento, nombre, apellidos);
            dc.setProjection(Projections.rowCount());
            numeroEmpadronados = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEmpadronadosListado", ex);
        }
        return numeroEmpadronados;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaEmpadronados(padrondocumentos padrones) {
        this.currentSession().save(padrones);
    }

    // -- OPERACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Operacion> getOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<Operacion>();
        try {
            DetachedCriteria dc = getOperacionesTarjetaDetachedCriteria(tarjeta, fechaInicial, fechaFinal, tipoListado, ComercioId);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);
            //dc.add(Restrictions.and(Property.forName("class").ne(PreRecargaSaldoVirtual.class)));

//            dc.addOrder(Order.desc("fechaRealizacion"));

            /**
             * Elimino las pre-recargas
             */
            operaciones = (List<Operacion>) dc.getExecutableCriteria(this.currentSession()).list();
            operaciones = operaciones
                    .stream().filter(x -> !(x instanceof PreRecargaSaldoVirtual))
                    .collect(Collectors.toList());
            OrdenBySession gestor = new OrdenBySession();
            if (gestor.getSessionFilter(OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO) == null) {
                //No hay orden
                operaciones = operaciones.
                        stream().sorted((a1, a2) -> Integer.compare(a2.getFechaRealizacionInt(), a1.getFechaRealizacionInt())).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return operaciones;
    }


    // -- OPERACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<Operacion>();
        try {
            DetachedCriteria dc = getOperacionesTarjetaDetachedCriteria(tarjeta, fechaInicial, fechaFinal, tipoListado, ComercioId);

            //dc.add(Restrictions.and(Property.forName("class").ne(PreRecargaSaldoVirtual.class)));
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);

            dc.addOrder(Order.desc("fechaRealizacion"));

            /**
             * Elimino las pre-recargas
             */
            operaciones = (List<Operacion>) dc.getExecutableCriteria(this.currentSession()).list();
            operaciones = operaciones
                    .stream().filter(x -> !(x instanceof PreRecargaSaldoVirtual))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }

        BigDecimal resultado = BigDecimal.valueOf(0);
        for (Operacion op : operaciones) {
            if (op instanceof Compra) {
                resultado = resultado.subtract(op.getImporte());
            } else if (op instanceof Recarga) {
                resultado = resultado.add(op.getImporte());
            }
        }
        return resultado;
    }


    // -- OPERACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTicketsTotalesOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<Operacion>();
        try {
            DetachedCriteria dc = getOperacionesTarjetaDetachedCriteria(tarjeta, fechaInicial, fechaFinal, tipoListado, ComercioId);

            //dc.add(Restrictions.and(Property.forName("class").ne(PreRecargaSaldoVirtual.class)));
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);

            dc.addOrder(Order.desc("fechaRealizacion"));

            /**
             * Elimino las pre-recargas
             */
            operaciones = (List<Operacion>) dc.getExecutableCriteria(this.currentSession()).list();
            operaciones = operaciones
                    .stream().filter(x -> !(x instanceof PreRecargaSaldoVirtual))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }

        BigDecimal resultado = BigDecimal.valueOf(0);
        for (Operacion op : operaciones) {
            if (op instanceof Compra) {
                if (((Compra) op).getImporteTotalTicket() != null)
                    resultado = resultado.add(((Compra) op).getImporteTotalTicket());
            } else if (op instanceof Recarga) {
                if (((Recarga) op).getImporteTotalTicket() != null)
                    resultado = resultado.add(((Recarga) op).getImporteTotalTicket());
            }
        }
        return resultado;
    }

    // -- OPERACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getNumeroOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer tipoListado, String ComercioId) {
        Long numeroOperaciones = 0L;
        try {
            DetachedCriteria dc = getOperacionesTarjetaDetachedCriteria(tarjeta, fechaInicial, fechaFinal, tipoListado, ComercioId);
            List<Operacion> operaciones = (List<Operacion>) dc.getExecutableCriteria(this.currentSession()).list();
            operaciones = operaciones
                    .stream().filter(x -> !(x instanceof PreRecargaSaldoVirtual))
                    .collect(Collectors.toList());

            numeroOperaciones = new Long(operaciones.size());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return numeroOperaciones;
    }

    private DetachedCriteria getOperacionesTarjetaDetachedCriteria(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal) {
        DetachedCriteria dc = DetachedCriteria.forClass(Operacion.class);
        if (tarjeta != null) {
            dc.add(Restrictions.eq("tarjeta", tarjeta));
        }
        if (fechaInicial != null && fechaFinal != null) {
            dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
        }
//        DetachedCriteria subquery = DetachedCriteria.forClass(PreRecargaSaldoVirtual.class)
//                .setProjection(Projections.id())
//                .add(Restrictions.isNotNull("id"));
//        dc.add ( Property.forName("id").notIn(subquery) );
        return dc;
    }


    private DetachedCriteria getOperacionesTarjetaDetachedCriteria(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer tipoListado, String comercioId) {
        DetachedCriteria dc = null;
        if (tipoListado != null && tipoListado > 1) {
            switch (tipoListado) {
                case 2:
                    dc = DetachedCriteria.forClass(Compra.class);
                    break;
                case 3:
                    dc = DetachedCriteria.forClass(Recarga.class);
                    break;
            }
        } else {
            dc = DetachedCriteria.forClass(Operacion.class);
        }
        if (comercioId != null && !comercioId.isEmpty() && !comercioId.equals("TODOS")) {
            dc.add(Restrictions.eq("centro.id", getCentroIdCentro(comercioId).getId()));
        }
        if (tarjeta != null) {
            dc.add(Restrictions.eq("tarjeta", tarjeta));
        }
        if (fechaInicial != null && fechaFinal != null) {
            dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
        }
//        DetachedCriteria subquery = DetachedCriteria.forClass(PreRecargaSaldoVirtual.class)
//                .setProjection(Projections.id())
//                .add(Restrictions.isNotNull("id"));
//        dc.add ( Property.forName("id").notIn(subquery) );
        return dc;
    }


    @Override
    public List<Operacion> getOperacionesTarjetas(List<Tarjeta> tarjetas, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = getOperacionesTarjetasDetachedCriteria(tarjetas, fechaInicial, fechaFinal, tipoListado, ComercioId);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);
            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return operaciones;
    }


    @Override
    public BigDecimal getImportesTotalesOperacionesTarjetas(List<Tarjeta> tarjetas, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = getOperacionesTarjetasDetachedCriteria(tarjetas, fechaInicial, fechaFinal, tipoListado, ComercioId);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);
            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        BigDecimal resultado = BigDecimal.valueOf(0);
        for (Operacion op : operaciones) {
            if (op instanceof Compra) {
                resultado = resultado.subtract(op.getImporte());
            } else if (op instanceof Recarga) {
                resultado = resultado.add(op.getImporte());
            }
        }
        return resultado;
    }

    @Override
    public BigDecimal getImportesTicketsTotalesOperacionesTarjetas(List<Tarjeta> tarjetas, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId) {
        List<Operacion> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = getOperacionesTarjetasDetachedCriteria(tarjetas, fechaInicial, fechaFinal, tipoListado, ComercioId);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.OPERACIONES_LISTADO);
            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        BigDecimal resultado = BigDecimal.valueOf(0);
        for (Operacion op : operaciones) {
            if (op instanceof Recarga && ((Recarga) op).getImporteTotalTicket() != null) {
                resultado = resultado.add(((Recarga) op).getImporteTotalTicket());
            }
        }
        return resultado;
    }

    @Override
    public Long getNumeroOperacionesTarjetas(List<Tarjeta> tarjetas, Date fechaInicial, Date fechaFinal, Integer tipoListado, String ComercioId) {
        Long numeroOperaciones = 0L;
        try {
            DetachedCriteria dc = getOperacionesTarjetasDetachedCriteria(tarjetas, fechaInicial, fechaFinal, tipoListado, ComercioId);
            dc.setProjection(Projections.rowCount());
            ArrayList<Long> object = (ArrayList<Long>) dc.getExecutableCriteria(this.currentSession()).list();
            numeroOperaciones = object.get(0) + object.get(1);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return numeroOperaciones;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Operacion> getOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado) {
        List<Operacion> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return operaciones;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Compra> getOperacionesComprasComercioPendientes(Integer comercioId) {
        List<Compra> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            dc.add(Restrictions.isNull("liquidacionSaldo"));

            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return operaciones;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LiquidacionSaldo getLiquidacionByLote(String idLote) {
        LiquidacionSaldo liquidacion = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionSaldo.class);
            dc.add(Restrictions.eq("referencia", idLote));
            liquidacion = (LiquidacionSaldo) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComprasIdUso", ex);
        }
        return liquidacion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Compra> listaOperacionesLote(String idLote) {
        List<Compra> operaciones = new ArrayList<>();
        try {
            LiquidacionSaldo liquidacion = getLiquidacionByLote(idLote);

            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.in("liquidacionSaldo", liquidacion));

            dc.addOrder(Order.desc("fechaRealizacion"));
            operaciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesTarjeta", ex);
        }
        return operaciones;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getNumeroOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal) {
        Long numeroOperaciones = 0L;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            dc.setProjection(Projections.rowCount());
            ArrayList<Long> object = (ArrayList<Long>) dc.getExecutableCriteria(this.currentSession()).list();
            numeroOperaciones = object.get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroOperacionesVentasComercio", ex);
        }
        return numeroOperaciones;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));

            List<Recarga> recargas = dc.getExecutableCriteria(this.currentSession()).list();

            return recargas.stream()
                    .map(x -> x.getImporte())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroOperacionesVentasComercio", ex);
        }
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesTicketsOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));

            List<Recarga> recargas = dc.getExecutableCriteria(this.currentSession()).list();

            return recargas.stream()
                    .filter(y -> y.getImporteTotalTicket() != null)
                    .map(x -> x.getImporteTotalTicket())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroOperacionesVentasComercio", ex);
        }
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getNumeroOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        Long numeroOperaciones = 0L;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            if (estado != null && estado > 0) {
                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.eq("l.estado.id", estado));
            }
            if (estado != null && estado == 0) {
//                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.isNull("liquidacionSaldo"));
            }
            if (lote != null && !lote.isEmpty()) {
                if (estado == null || estado == 0) {
                    dc.createAlias("liquidacionSaldo", "l");
                }
                dc.add(Restrictions.eq("l.referencia", lote));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            dc.setProjection(Projections.rowCount());
            try {
                ArrayList<Long> object = (ArrayList<Long>) dc.getExecutableCriteria(this.currentSession()).list();
                numeroOperaciones = object.get(0);
            } catch (Exception e) {
                //no hay elementos
                return 0L;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroOperacionesCobrosComercio", ex);
        }
        return numeroOperaciones;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            if (estado != null && estado > 0) {
                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.eq("l.estado.id", estado));
            }
            if (estado != null && estado == 0) {
//                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.isNull("liquidacionSaldo"));
            }
            if (lote != null && !lote.isEmpty()) {
                if (estado == null || estado == 0) {
                    dc.createAlias("liquidacionSaldo", "l");
                }
                dc.add(Restrictions.eq("l.referencia", lote));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            List<Compra> compras = dc.getExecutableCriteria(this.currentSession()).list();
            return compras.stream()
                    .map(x -> x.getImporte())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception ex) {
            logger.error( "DAOiml.getNumeroOperacionesCobrosComercio", ex);
        }
        return BigDecimal.ZERO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesTicketsOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            if (estado != null && estado > 0) {
                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.eq("l.estado.id", estado));
            }
            if (estado != null && estado == 0) {
//                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.isNull("liquidacionSaldo"));
            }
            if (lote != null && !lote.isEmpty()) {
                if (estado == null || estado == 0) {
                    dc.createAlias("liquidacionSaldo", "l");
                }
                dc.add(Restrictions.eq("l.referencia", lote));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            List<Compra> compras = dc.getExecutableCriteria(this.currentSession()).list();
            return compras.stream()
                    .filter(y -> y.getImporteTotalTicket() != null)
                    .map(x -> x.getImporteTotalTicket())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception ex) {
            logger.error( "DAOiml.getNumeroOperacionesCobrosComercio", ex);
        }
        return BigDecimal.ZERO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Operacion> getOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer estado, String lote) {
        List<Operacion> operaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            if (comercioId != null) {
                dc.add(Restrictions.in("comercioId", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
            }
            if (estado != null && estado > 0) {
                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.eq("l.estado.id", estado));
            }
            if (estado != null && estado == 0) {
//                dc.createAlias("liquidacionSaldo", "l");
                dc.add(Restrictions.isNull("liquidacionSaldo"));
            }
            if (lote != null && !lote.isEmpty()) {
                if (estado == null || estado == 0) {
                    dc.createAlias("liquidacionSaldo", "l");
                }
                dc.add(Restrictions.eq("l.referencia", lote));
            }
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            dc.addOrder(Order.desc("fechaRealizacion"));
            try {
                operaciones = dc.getExecutableCriteria(this.currentSession()).list();
            } catch (Exception e) {
                //no hay resultados
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getOperacionesCobrosComercio", ex);
        }
        return operaciones;
    }


    private DetachedCriteria getOperacionesTarjetasDetachedCriteria(List<Tarjeta> tarjetas, Date fechaInicial, Date fechaFinal, Integer tipoListado, String ComercioId) {
        DetachedCriteria dc = null;
        if (tipoListado != null && tipoListado > 1) {
            switch (tipoListado) {
                case 2:
                    dc = DetachedCriteria.forClass(Compra.class);
                    break;
                case 3:
                    dc = DetachedCriteria.forClass(Recarga.class);
                    break;
            }
        } else {
            dc = DetachedCriteria.forClass(Operacion.class);
        }
        if (ComercioId != null && !ComercioId.isEmpty() && !ComercioId.equals("TODOS")) {
            dc.add(Restrictions.eq("centro.id", getCentroIdCentro(ComercioId).getId()));
        }
        dc.add(Restrictions.in("tarjeta", tarjetas));
        if (fechaInicial != null && fechaFinal != null) {
            dc.add(Restrictions.between("fechaRealizacionInt", Integer.parseInt(DateUtils.yyyyMMdd.format(fechaInicial)), Integer.parseInt(DateUtils.yyyyMMdd.format(fechaFinal))));
        }
        return dc;
    }

    // -- COMPRAS -- //
    //Obtencion de las compras por el identificador de uso
    @Transactional(propagation = Propagation.REQUIRED)
    public Compra getComprasIdUso(String idUso) {
        Compra compra = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.eq("idUso", idUso));
            compra = (Compra) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComprasIdUso", ex);
        }
        return compra;
    }

    //Obtencion del ultimo saldo leido en compras
    @Transactional(propagation = Propagation.REQUIRED)
    public Compra getPrimeraCompra(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/) {
        Compra compra = null;
        List<Compra> compras = new ArrayList<Compra>();
        try {
            Query query = null;
            /* if(avisos==null){
             query=currentSession().createQuery("SELECT c FROM Compra c WHERE c.tarjeta = :tarjeta AND DATE(c.fechaRealizacion)<= :fecha ORDER BY c.fechaRealizacion ASC");
             }else{*/
            query = currentSession().createQuery("SELECT c FROM Compra c WHERE c.tarjeta = :tarjeta AND c.aviso in :avisos AND DATE(c.fechaRealizacion)<= :fecha ORDER BY c.fechaRealizacion ASC");
            //query.setParameterList("avisos", avisos);
            // }
            String fechaAux = DateUtils.yyyyMMddHHmmss_conFormato.format(fecha);
            query.setString("fecha", fechaAux.substring(0, 10));
            query.setParameter("tarjeta", tarjeta);
            compras = query.list();
            if (!compras.isEmpty()) {
                compra = compras.get(0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getPrimeraCompra", ex);
        }
        return compra;
    }

    //Obtencion del ultimo saldo leido en compras
    @Transactional(propagation = Propagation.REQUIRED)
    public Compra getUltimoSaldoCompraTarjeta(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/) {
        Compra compra = null;
        List<Compra> compras = new ArrayList<Compra>();
        try {
            Query query = null;
            /*  if(avisos==null){
             query=currentSession().createQuery("SELECT c FROM Compra c WHERE c.saldoFinal is not null AND c.tarjeta = :tarjeta AND DATE(c.fechaRealizacion)<= :fecha ORDER BY c.fechaRealizacion DESC");
             }else{*/
            query = currentSession().createQuery("SELECT c FROM Compra c WHERE c.saldoFinal is not null AND c.tarjeta = :tarjeta AND c.aviso in :avisos AND DATE(c.fechaRealizacion)<= :fecha ORDER BY c.fechaRealizacion DESC");
            //  query.setParameterList("avisos", avisos);
            // }
            String fechaAux = DateUtils.yyyyMMddHHmmss_conFormato.format(fecha);
            query.setString("fecha", fechaAux.substring(0, 10));
            query.setParameter("tarjeta", tarjeta);
            compras = query.list();
            if (!compras.isEmpty()) {
                compra = compras.get(0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getUltimoSaldoCompraTarjeta", ex);
        }
        return compra;
    }

    //Obtencion del importe de las compras realizadas posteriores a la fecha
    /*  @Transactional(propagation = Propagation.REQUIRED)
     public Double getImporteComprasPosteriores(Tarjeta tarjeta, Operacion operacion, List<Aviso> avisos, Date fechaHasta){
     Double importe = 0.0;
     Calendar cFechaRealizacion = Calendar.getInstance();
     cFechaRealizacion.setTime(operacion.getFechaRealizacion());
     try {
     Query query = null;
     if(avisos==null){
     query=currentSession().createQuery("SELECT sum(c.importe) FROM Compra c WHERE c.fechaRealizacion between :fechaRealizacionInicial AND :fechaRealizacionFinal AND c.tarjeta = :tarjeta ORDER BY c.fechaRealizacion DESC");
     if(operacion.getSaldoFinal()!=null)cFechaRealizacion.add(Calendar.SECOND, 1);
     query.setParameter("fechaRealizacionFinal", fechaHasta);
     query.setParameter("fechaRealizacionInicial", cFechaRealizacion.getTime());
     }else{
     query=currentSession().createQuery("SELECT sum(c.importe) FROM Compra c WHERE c.fechaRealizacion between :fechaRealizacionInicial AND :fechaRealizacionFinal AND c.tarjeta = :tarjeta AND c.aviso in :avisos ORDER BY c.fechaRealizacion DESC");
     if(operacion.getSaldoFinal()!=null)cFechaRealizacion.add(Calendar.SECOND, 1);
     query.setParameter("fechaRealizacionFinal", fechaHasta);
     query.setParameterList("avisos", avisos);
     query.setParameter("fechaRealizacionInicial", cFechaRealizacion.getTime());
     }
     query.setParameter("tarjeta", tarjeta);
     importe=(Double)query.list().get(0);
     if(importe==null) importe=0.0;
     } catch (Exception ex) {
     logger.error( "DAOiml.getImporteComprasPosteriores", ex);
     }
     return importe;
     }*/
    //Obtencion del importe de las compras realizadas entre dos fechas
    @Transactional(propagation = Propagation.REQUIRED)
    public Double getImporteComprasEntreFechas(Tarjeta tarjeta, Date fechaIni, Date fechaFin) {
        Double importe = 0.0;
        try {
            Query query = currentSession().createQuery("SELECT sum(c.importe) FROM Compra c WHERE c.fechaRealizacion between :fechaIni and :fechaFin AND c.tarjeta = :tarjeta AND c.saldoFinal is null");
            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);
            query.setParameter("tarjeta", tarjeta);
            importe = (Double) query.list().get(0);
            if (importe == null) {
                importe = 0.0;
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteComprasEntreFechas", ex);
        }
        return importe;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BigDecimal getImporteTotalComprasEntreFechas(Date fechaIni, Date fechaFin) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        BigDecimal importe = new BigDecimal(0.0);
        try {
            Query query = currentSession().createQuery("SELECT sum(c.importe) FROM Compra c WHERE c.fechaRealizacion between :fechaIni and :fechaFin");
            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);
            importe = (BigDecimal) query.list().get(0);
            if (importe == null) {
                importe = new BigDecimal(0.0);
            } else {
                //          System.out.println(importe);
               logger.info( "getImporteTotalComprasEntreFechas Importe:" + importe);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteTotalComprasEntreFechas", ex);
        }
        return importe;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteCompras() {
        BigDecimal importe = new BigDecimal(0.0);
        try {
            Query query = currentSession().createQuery("SELECT sum(c.importe) FROM Compra c");
            importe = (BigDecimal) query.list().get(0);
            if (importe == null) {
                importe = new BigDecimal(0.0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteCompras", ex);
        }
        return importe;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteComprasAmbito(Ambito ambito) {
        BigDecimal importeTotalAmbito = new BigDecimal(0.0);
        Set<Centro> _centros = null;
        try {
            if (ambito != null) {
                _centros = ambito.getCentros();
                Criteria suma = sessionFactory.getCurrentSession().createCriteria(Compra.class)
                        .setProjection(Projections.sum("importe"))
                        .add(Restrictions.isNull("idOperacionAnulada"))
                        .add(Restrictions.in("centro", _centros.toArray()));
                importeTotalAmbito = (BigDecimal) suma.uniqueResult();
            } else {
                Criteria suma = sessionFactory.getCurrentSession().createCriteria(Compra.class)
                        .setProjection(Projections.sum("importe"))
                        .add(Restrictions.isNull("idOperacionAnulada"));

                importeTotalAmbito = (BigDecimal) suma.uniqueResult();
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteComprasAmbito", ex);
        }
        return importeTotalAmbito;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteRecargasAmbito(Ambito ambito) {
        BigDecimal importeTotalAmbito = new BigDecimal(0.0);
        Set<Centro> _centros = null;
        try {
            if (ambito != null) {
                _centros = ambito.getCentros();
                Criteria suma = sessionFactory.getCurrentSession().createCriteria(Recarga.class)
                        .setProjection(Projections.sum("importe"))
                        .add(Restrictions.isNull("idOperacionAnulada"))
                        .add(Restrictions.in("centro", _centros.toArray()));
                importeTotalAmbito = (BigDecimal) suma.uniqueResult();
            } else {
                Criteria suma = sessionFactory.getCurrentSession().createCriteria(Recarga.class)
                        .setProjection(Projections.sum("importe"))
                        .add(Restrictions.isNull("idOperacionAnulada"));

                importeTotalAmbito = (BigDecimal) suma.uniqueResult();
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteRecargasAmbito", ex);
        }
        return importeTotalAmbito;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BigDecimal getImporteTotalRecargasEntreFechas(Date fechaIni, Date fechaFin) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        BigDecimal importe = new BigDecimal(0.0);
        try {
            Query query = currentSession().createQuery("SELECT sum(c.importe) FROM Recarga c WHERE c.fechaRealizacion between :fechaIni and :fechaFin");
            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);
            importe = (BigDecimal) query.list().get(0);
            if (importe == null) {
                importe = new BigDecimal(0.0);
            } else {
               logger.info( "DAOimpl:getImporteTotalRecargasEntreFechas Importe: " + importe);

                //            System.out.println(importe);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteTotalRecargasEntreFechas", ex);
        }
        return importe;
    }

    //Obtencion del listado de compras segun criterios de busqueda
//    @Transactional(propagation = Propagation.REQUIRED)
//    public List<Compra> getComprasListadoHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion, Integer primerResultado) {
//        List<Compra> compras = new ArrayList<Compra>();
//        try {
//            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetalles(), QueryOperaciones.OPERACION_COMPRA, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, null, descuento, avisos, liquidacion, buscarPorLiquidacion, buscarPorDispositivo, buscarSaldoLeido, buscarSinSaldoLeido, buscaAnuladas, eqNumeroOperacion, primerResultado);
//
//
//            OrdenBySession gestor = new OrdenBySession();
//            OrdenBySession.SessionFilters filtro = null;
//            try {
//                filtro = gestor.getSessionFilter(OrdenBySession.LISTADO_TIPOS.USOS_DESCUENTOS);
//            } catch (Exception e) {
//            }
//            if (filtro != null) {
//                switch (filtro.getOrden()) {
//                    case ASC:
//                        compras = qC.crearQueryForOrder(currentSession(), filtro.getCampo(), "ASC").list();
//                        break;
//                    case DESC:
//                        compras = qC.crearQueryForOrder(currentSession(), filtro.getCampo(), "DESC").list();
//                        break;
//                }
//            } else {
//                compras = qC.creareQuery(currentSession()).list();
//            }
//
//        } catch (Exception ex) {
//            logger.error( "DAOiml.getComprasListadoHql", ex);
//        }
//        return compras;
//    }

    //Obtencion del numero e importe totales de las compras segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getComprasNumeroImporte(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarSinSaldoLeido, Boolean buscaAnulada, Boolean eqNumeroOperacion) {
        List totales = new ArrayList();
        totales.add(new Object[]{0l, 0.0});
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetallesTotales(), QueryOperaciones.OPERACION_COMPRA, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, descuento, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, buscarSinSaldoLeido, buscaAnulada, eqNumeroOperacion, null);
            Query query = qC.creareQuery(currentSession());
            totales = query.list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getComprasNumeroImporte", ex);
        }
        return (Object[]) totales.get(0);
    }



    //Obtencion del numero total de compras por ambito segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getComprasAmbitoNumeroHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesAmbitoTotales(), QueryOperaciones.OPERACION_COMPRA, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, descuento, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            totales = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getComprasAmbitoNumeroHql", ex);
        }
        return Long.valueOf(totales.size());
    }



    //Obtencion del numero total de compras por centro segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getComprasCentroNumeroHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesCentrosTotales(), QueryOperaciones.OPERACION_COMPRA, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, descuento, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            totales = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getComprasCentroNumeroHql", ex);
        }
        return Long.valueOf(totales.size());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Compra anotarCompra(Compra compra) {
        compra.setIdUso("");
        this.currentSession().save(compra);
        compra.asignarIdUso();
        this.currentSession().update(compra);
        return compra;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaCompra(Compra compra) {
        this.currentSession().update(compra);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeNumeroOperacionCompra(String numeroOperacion) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.eq("numeroOperacion", numeroOperacion));
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeNumeroOperacionCompra", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    /*  @Transactional(propagation = Propagation.REQUIRED)
     public Boolean existeNumeroOperacionCompra(Centro centro, Dispositivo dispositivo, String numeroOperacion) {
     try {
     DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
     dc.add(Restrictions.eq("centro", centro));
     if(dispositivo!=null){
     dc.add(Restrictions.eq("dispositivo", dispositivo));
     }
     dc.add(Restrictions.eq("numeroOperacion", numeroOperacion));
     if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
     return Boolean.TRUE;
     }
     } catch (DataAccessException ex) {
     logger.error( "DAOiml.existeNumeroOperacionCompra", ex);
     return null;
     }
     return Boolean.FALSE;
     }*/
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeCompraAnulada(Integer idOperacion) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.eq("idOperacionAnulada", idOperacion));
            /* dc.add(Restrictions.not(Restrictions.eq("aviso", this.getAvisoNumero(303))));*/
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeCompraAnulada", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    // -- DISPOSITIVO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaDispositivo(Dispositivo dispositivo) {
        this.currentSession().save(dispositivo);
        dispositivo.assignarIdDispositivo();
        modificaDispositivo(dispositivo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void forzarActualizacionListaBlanca(Boolean forzar) {
        String queryString = "UPDATE Dispositivo SET forzarActualizacionListaBlanca=?";
        int query = this.currentSession().createQuery(queryString).setBoolean(0, forzar).executeUpdate();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaDispositivo(Dispositivo dispositivo) {
        this.currentSession().update(dispositivo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dispositivo getDispositivoInformacion(String idDispositivo, String numeroSerie) {
        Dispositivo dispositivo = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            if (idDispositivo != null && !idDispositivo.equals("")) {
                dc.add(Restrictions.eq("idDispositivo", idDispositivo));
            }
            if (numeroSerie != null && !numeroSerie.equals("")) {
                dc.add(Restrictions.eq("numeroSerie", numeroSerie));
            }
            dispositivo = (Dispositivo) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivoInformacion", ex);
        }
        return dispositivo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dispositivo getDispositivoNumernoSerie(String numeroSerie) {
        Dispositivo dispositivo = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            dc.add(Restrictions.eq("numeroSerie", numeroSerie));
            dispositivo = (Dispositivo) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivoNumernoSerie", ex);
        }
        return dispositivo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Dispositivo> getDispositivosListado(String idDispositivo, Ambito ambito, Centro centro, Integer primerResultado) {
        List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            if (!idDispositivo.equals("")) {
                dc.add(Restrictions.ilike("idDispositivo", idDispositivo, MatchMode.START));
            }
            if (ambito != null) {
                dc.createAlias("centro", "c");
                dc.add(Restrictions.eq("c.ambito", ambito));
            }
            if (centro != null) {
                dc.add(Restrictions.eq("centro", centro));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_DISPOSITIVOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                dispositivos = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                dispositivos = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivosListado", ex);
        }
        return dispositivos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getDispositivosNumero(String idDispositivo, Ambito ambito, Centro centro) {
        Long numeroDispositivos = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            if (!idDispositivo.equals("")) {
                dc.add(Restrictions.ilike("idDispositivo", idDispositivo, MatchMode.START));
            }
            if (ambito != null) {
                dc.createAlias("centro", "c");
                dc.add(Restrictions.eq("c.ambito", ambito));
            }
            if (centro != null) {
                dc.add(Restrictions.eq("centro", centro));
            }
            dc.setProjection(Projections.rowCount());
            numeroDispositivos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivosNumero", ex);
        }
        return numeroDispositivos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dispositivo getDispositivoId(Integer id) {
        Dispositivo dispositivo = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            dc.add(Restrictions.eq("id", id));
            dispositivo = (Dispositivo) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivoId", ex);
        }
        return dispositivo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dispositivo getDispositivoIdDispositivo(String idDispositivo) {
        Dispositivo dispositivo = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            dc.add(Restrictions.eq("idDispositivo", idDispositivo));
            dispositivo = (Dispositivo) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivoIdDispositivo", ex);
        }
        return dispositivo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Dispositivo> getDispositivos() {
        List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Dispositivo.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dispositivos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDispositivos", ex);
        }
        return dispositivos;
    }

    // -- ESTADOS CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoCiudadano getEstadoCiudadanoId(Integer id) {
        return (EstadoCiudadano) this.currentSession().get(EstadoCiudadano.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoCiudadano> getEstadosCiudadano() {
        List<EstadoCiudadano> estadosCiudadano = new ArrayList<EstadoCiudadano>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoCiudadano.class);
            estadosCiudadano = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosCiudadano", ex);
        }
        return estadosCiudadano;
    }

    // -- ESTADOS INCIDENCIA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoIncidencia> getEstadoIncidencia() {
        List<EstadoIncidencia> estadosIncidencia = new ArrayList<EstadoIncidencia>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoIncidencia.class);
            estadosIncidencia = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadoIncidencia", ex);
        }
        return estadosIncidencia;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoIncidencia getEstadoIncidenciaId(Integer id) {
        return (EstadoIncidencia) this.currentSession().get(EstadoIncidencia.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TransicionEstadoIncidencia> getTransicionesEstadoIncidencia(EstadoIncidencia estadoIncidencia) {
        List<TransicionEstadoIncidencia> transiciones = new ArrayList<TransicionEstadoIncidencia>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TransicionEstadoIncidencia.class);
            dc.add(Restrictions.eq("estadoOrigen", estadoIncidencia));
            transiciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTransicionesEstadoIncidencia", ex);
        }
        return transiciones;
    }

    // -- ESTADOS LIQUIDACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TransicionEstadoLiquidacion> getTransicionesEstadoLiquidacion(EstadoLiquidacion estadoLiquidacion) {
        List<TransicionEstadoLiquidacion> transiciones = new ArrayList<TransicionEstadoLiquidacion>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TransicionEstadoLiquidacion.class);
            dc.add(Restrictions.eq("estadoOrigen", estadoLiquidacion));
            transiciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTransicionesEstadoLiquidacion", ex);
        }
        return transiciones;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoLiquidacion> getEstadosLiquidacion() {
        List<EstadoLiquidacion> estadoLiquidacion = new ArrayList<EstadoLiquidacion>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoLiquidacion.class);
            estadoLiquidacion = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosLiquidacion", ex);
        }
        return estadoLiquidacion;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoLiquidacion getEstadoLiquidacionId(Integer id) {
        return (EstadoLiquidacion) this.currentSession().get(EstadoLiquidacion.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EstadosLiquidacionSaldo getEstadoLiquidacionSaldoId(Integer id) {
        return (EstadosLiquidacionSaldo) this.currentSession().get(EstadosLiquidacionSaldo.class, id);
    }

    // -- ESTADO TARJETA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoTarjeta getEstadoTarjetaId(Integer id) {
        return (EstadoTarjeta) this.currentSession().get(EstadoTarjeta.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoTarjeta> getEstadosTarjeta() {
        List<EstadoTarjeta> estadosTarjeta = new ArrayList<EstadoTarjeta>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoTarjeta.class);
            estadosTarjeta = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosTarjeta", ex);
        }
        return estadosTarjeta;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoTarjeta> getEstadosTarjetaEstadoGeneral(Integer estadoGeneral) {
        List<EstadoTarjeta> estadosTarjeta = new ArrayList<EstadoTarjeta>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoTarjeta.class);
            dc.add(Restrictions.eq("estadoGeneral", estadoGeneral));
            estadosTarjeta = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosTarjetaEstadoGeneral", ex);
        }
        return estadosTarjeta;
    }

    // -- HISTORICO CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getActivacionesCiudadanosFecha(Date fechaIni, Date fechaFin, int estadoCiudadano) {
        Long cantidadActivacionesCiudadano = Long.valueOf(01);

        Query query = currentSession().createQuery(
                "SELECT count(*) "
                        + "FROM HistorialCiudadano e "
                        + "WHERE "
                        + "idEstadoCiudadano = :idEstadoCiudadano and e.fecha "
                        + "between :fechaIni and :fechaFin");

        query.setParameter("fechaIni", fechaIni);
        query.setParameter("fechaFin", fechaFin);
        query.setParameter("idEstadoCiudadano", estadoCiudadano);

        cantidadActivacionesCiudadano = (Long) query.list().get(0);

        if (cantidadActivacionesCiudadano == null) {
            cantidadActivacionesCiudadano = Long.valueOf(00);
        }

        return cantidadActivacionesCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaHistorialCiudadano(HistorialCiudadano historialCiudadano) {
        this.currentSession().save(historialCiudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaHistorialCiudadano(HistorialCiudadano historialCiudadano) {
        this.currentSession().update(historialCiudadano);
    }

    // -- HISTORIAL INCIDENCIA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaHistorialIncidencia(HistorialIncidencia historialIncidencia) {
        this.currentSession().save(historialIncidencia);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaHistorialIncidencia(HistorialIncidencia historialIncidencia) {
        this.currentSession().update(historialIncidencia);
    }

    // -- HISTORIAL TARJETA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public HistorialTarjeta getEstadoTarjetaVisitanteActivada(Tarjeta tarjeta) {
        DetachedCriteria dc = DetachedCriteria.forClass(HistorialTarjeta.class);
        dc.add(Restrictions.eq("tarjeta", tarjeta));
        dc.add(Restrictions.eq("estado", this.getEstadoTarjetaId(EstadoTarjeta.ACTIVA)));
        List<HistorialTarjeta> historialTarjetas = dc.getExecutableCriteria(this.currentSession()).list();
        if (historialTarjetas.size() != 0) {
            return historialTarjetas.get(0);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public HistorialTarjeta getEstadoTarjetaOperacion(Tarjeta tarjeta, Date fecha) {
        DetachedCriteria dc = DetachedCriteria.forClass(HistorialTarjeta.class);
        dc.add(Restrictions.eq("tarjeta", tarjeta));
        dc.add(Restrictions.lt("fecha", fecha));
        dc.createAlias("tarjeta", "t");
        dc.add(Restrictions.ne("t.tag", 3));
        dc.addOrder(Order.desc("fecha"));
        List<HistorialTarjeta> historialTarjetas = dc.getExecutableCriteria(this.currentSession()).list();
        if (historialTarjetas.size() != 0) {
            return historialTarjetas.get(0);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaHistorialTarjeta(HistorialTarjeta historialTarjeta) {
        this.currentSession().save(historialTarjeta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaHistorialTarjeta(HistorialTarjeta historialTarjeta) {
        this.currentSession().update(historialTarjeta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getActivacionesTarjetaFecha(Date fechaIni, Date fechaFin, int estadoTarjeta) {
        Long cantidadActivacionesTarjeta = Long.valueOf(01);

        Query query = currentSession().createQuery(
                "SELECT count(*) "
                        + "FROM HistorialTarjeta e "
                        + "WHERE "
                        + "idEstadoTarjeta = :idEstadoTarjeta and e.fecha "
                        + "between :fechaIni and :fechaFin");

        query.setParameter("fechaIni", fechaIni);
        query.setParameter("fechaFin", fechaFin);
        query.setParameter("idEstadoTarjeta", estadoTarjeta);

        cantidadActivacionesTarjeta = (Long) query.list().get(0);

        if (cantidadActivacionesTarjeta == null) {
            cantidadActivacionesTarjeta = Long.valueOf(00);
        }

        return cantidadActivacionesTarjeta;
    }

    // -- HISTORIAL LIQUIDACIONES -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<HistorialLiquidacionAmbito> getHistorialLiquidacionAmbitoIdLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        List<HistorialLiquidacionAmbito> historialLiquidacionAmbito = new ArrayList<HistorialLiquidacionAmbito>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(HistorialLiquidacionAmbito.class);
            dc.add(Restrictions.eq("liquidacionAmbito", liquidacionAmbito));
            historialLiquidacionAmbito = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getHistorialLiquidacionAmbitoIdLiquidacionAmbito", ex);
        }
        return historialLiquidacionAmbito;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaHitorialLiquidacionAmbito(HistorialLiquidacionAmbito historialLiquidacionAmbito) {
        this.currentSession().save(historialLiquidacionAmbito);
    }

    // -- INCIDENCIA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Incidencia> getIncidenciasListado(EstadoIncidencia estadoIncidencia, User usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta, Integer desde) {
        List incidencias = new ArrayList();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Incidencia.class);
            if (tipoIncidencia != null) {
                dc.add(Restrictions.eq("tipoIncidencia", tipoIncidencia));
            }
            if (estadoIncidencia != null) {
                dc.add(Restrictions.eq("estado", estadoIncidencia));
            }
            if (usuario != null) {
                dc.add(Restrictions.eq("usuarioCreador", usuario));
            }
            if (usuarioAsignado != null) {
                dc.add(Restrictions.eq("usuarioAsignado", usuarioAsignado));
            }
            if (!prioridad.equals("")) {
                dc.add(Restrictions.eq("prioridad", prioridad));
            }
            if (!idCiudadano.equals("")) {
                dc.createAlias("ciudadanos", "ciudadano");
                dc.add(Restrictions.ilike("ciudadano.idEntidad", idCiudadano, MatchMode.START));
            }
            if (!idTarjeta.equals("")) {
                dc.createAlias("tarjetas", "tarjeta");
                dc.add(Restrictions.ilike("tarjeta.idTarjeta", idTarjeta, MatchMode.START));
            }
            if (!idAlerta.equals("")) {
                dc.createAlias("alertas", "alerta");
                dc.add(Restrictions.ilike("alerta.idAlerta", idAlerta, MatchMode.START));
            }
            dc.addOrder(Order.desc("id"));
            if (desde == null) {
                incidencias = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                incidencias = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getIncidenciasListado", ex);
        }
        return incidencias;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Incidencia> getIncidenciasListadoPorFechaDescendente(EstadoIncidencia estadoIncidencia, Usuario usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta, Integer desde) {
        List incidencias = new ArrayList();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Incidencia.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.INCIDENCIAS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("fecha"));
//            dc.addOrder(Order.desc("fecha"));
            if (tipoIncidencia != null) {
                dc.add(Restrictions.eq("tipoIncidencia", tipoIncidencia));
            }
            if (estadoIncidencia != null) {
                dc.add(Restrictions.eq("estado", estadoIncidencia));
            }
            if (usuario != null) {
                dc.add(Restrictions.eq("usuarioCreador", usuario));
            }
            if (usuarioAsignado != null) {
                dc.add(Restrictions.eq("usuarioAsignado", usuarioAsignado));
            }
            if (!prioridad.equals("")) {
                dc.add(Restrictions.eq("prioridad", prioridad));
            }
            if (!idCiudadano.equals("")) {
                dc.createAlias("ciudadanos", "ciudadano");
                dc.add(Restrictions.ilike("ciudadano.idEntidad", idCiudadano, MatchMode.START));
            }
            if (!idTarjeta.equals("")) {
                dc.createAlias("tarjetas", "tarjeta");
                dc.add(Restrictions.ilike("tarjeta.idTarjeta", idTarjeta, MatchMode.START));
            }
            if (!idAlerta.equals("")) {
                dc.createAlias("alertas", "alerta");
                dc.add(Restrictions.ilike("alerta.idAlerta", idAlerta, MatchMode.START));
            }
            if (desde == null) {
                incidencias = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                incidencias = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getIncidenciasListadoPorFechaDescendente", ex);
        }
        return incidencias;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getIncidenciasNumero(EstadoIncidencia estadoIncidencia, Usuario usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta) {
        List incidencias = new ArrayList();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Incidencia.class);
            if (tipoIncidencia != null) {
                dc.add(Restrictions.eq("tipoIncidencia", tipoIncidencia));
            }
            if (estadoIncidencia != null) {
                dc.add(Restrictions.eq("estadoIncidencia", estadoIncidencia));
            }
            if (usuario != null) {
                dc.add(Restrictions.eq("usuarioCreador", usuario));
            }
            if (usuarioAsignado != null) {
                dc.add(Restrictions.eq("usuarioAsignado", usuarioAsignado));
            }
            if (!prioridad.equals("")) {
                dc.add(Restrictions.eq("prioridad", prioridad));
            }
            if (!idCiudadano.equals("")) {
                dc.createAlias("ciudadanos", "ciudadano");
                dc.add(Restrictions.ilike("ciudadano.idEntidad", idCiudadano, MatchMode.START));
            }
            if (!idTarjeta.equals("")) {
                dc.createAlias("tarjetas", "tarjeta");
                dc.add(Restrictions.ilike("tarjeta.idTarjeta", idTarjeta, MatchMode.START));
            }
            if (!idAlerta.equals("")) {
                dc.createAlias("alertas", "alerta");
                dc.add(Restrictions.ilike("alerta.idAlerta", idAlerta, MatchMode.START));
            }
            incidencias = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getIncidenciasNumero", ex);
        }

        return new Long(incidencias.size());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Incidencia getIncidenciaIdIncidencia(String idIncidencia) {
        Incidencia incidencia = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Incidencia.class);
            dc.add(Restrictions.eq("idIncidencia", idIncidencia));
            incidencia = (Incidencia) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getIncidenciaIdIncidencia", ex);
        }
        return incidencia;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaIncidencia(Incidencia incidencia) {
        incidencia.setIdIncidencia("");
        this.currentSession().save(incidencia);
        incidencia.asignarIdIncidencia();
        this.currentSession().update(incidencia);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaIncidencia(Incidencia incidencia) {
        this.currentSession().update(incidencia);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<SeguimientoIncidencia> getSeguimientosIncidencia(Incidencia incidencia) {

        List<SeguimientoIncidencia> seguimientos = new ArrayList<SeguimientoIncidencia>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(SeguimientoIncidencia.class);
            dc.add(Restrictions.eq("incidencia", incidencia));
            seguimientos = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getSeguimientosIncidencia", ex);
        }
        return seguimientos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addSeguimientoIncidencia(SeguimientoIncidencia seguimiento) {
        this.currentSession().save(seguimiento);
    }

    // -- INGRESOS -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeNumeroIngreso(String numeroIngreso, LiquidacionAmbito liquidacionAmbito, String entidad) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ingreso.class);
            dc.add(Restrictions.eq("numeroIngreso", numeroIngreso));
            dc.add(Restrictions.eq("liquidacionAmbito", liquidacionAmbito));
            dc.add(Restrictions.eq("entidadFinanciera", entidad));
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeNumeroIngreso", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Ingreso getIngresoId(Integer id) {
        Ingreso ingreso = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ingreso.class);
            dc.add(Restrictions.eq("id", id));
            ingreso = (Ingreso) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getIngresoId", ex);
        }
        return ingreso;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getIngresosNumeroImporte(List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryIngresos qC = new QueryIngresos(new QueryIngresosDetallesTotales(), liquidacionesAmbito, numeroIngreso, ambito, entidadFinanciera, fechaIngresoInicial, fechaIngresoFinal, null);
            Query query = qC.creareQuery(currentSession());
            totales = query.list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getIngresosNumeroImporte", ex);
        }
        return (Object[]) totales.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaIngreso(Ingreso ingreso) {
        this.currentSession().save(ingreso);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteIngreso(Ingreso ingreso) {
        this.currentSession().delete(ingreso);
    }

    // -- LIQUIDACIONES -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Liquidacion getLiquidacionIdLiquidacion(String idLiquidacion) {
        Liquidacion liquidacion = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Liquidacion.class);
            dc.add(Restrictions.eq("idLiquidacion", idLiquidacion));
            liquidacion = (Liquidacion) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getLiquidacionIdLiquidacion", ex);
        }
        return liquidacion;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LiquidacionAmbito getLiquidacionAmbitoId(Integer id) {
        LiquidacionAmbito liquidacionAmbito = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionAmbito.class);
            dc.add(Restrictions.eq("id", id));
            liquidacionAmbito = (LiquidacionAmbito) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getLiquidacionAmbitoId", ex);
        }
        return liquidacionAmbito;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getLiquidacionesNumeroImporte(Integer tipoLiquidacion, String idLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal, Ambito ambito, EstadoLiquidacion estadoLiquidacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryLiquidaciones qC = new QueryLiquidaciones(new QueryLiquidacionesDetallesTotales(), tipoLiquidacion, idLiquidacion, ambito, estadoLiquidacion, fechaLiquidacionFinal, fechaLiquidacionFinal, null);
            Query query = qC.creareQuery(currentSession());
            totales = query.list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getLiquidacionesNumeroImporte", ex);
        }
        return (Object[]) totales.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaLiquidacion(Liquidacion liquidacion) {
        this.currentSession().save(liquidacion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaLiquidacion(Liquidacion liquidacion) {
        this.currentSession().update(liquidacion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        this.currentSession().save(liquidacionAmbito);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        this.currentSession().update(liquidacionAmbito);
    }

    // -- PERIODO VIGENCIA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public PeriodoVigencia getPeriodoVigenciaId(Integer id) {
        try {
            return (PeriodoVigencia) this.currentSession().get(PeriodoVigencia.class, id);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPeriodoVigenciaId", ex);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<PeriodoVigencia> getPeriodosVigencia() {
        List<PeriodoVigencia> periodoVigencia = new ArrayList<PeriodoVigencia>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(PeriodoVigencia.class);
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.addOrder(Order.asc("nombre"));
            periodoVigencia = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPeriodosVigencia", ex);
        }
        return periodoVigencia;
    }

    // -- RECARGAS -- //
    //Obtencion de las recargas por el identificador de uso
    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga getRecargaIdUso(String idUso) {
        Recarga recarga = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("idUso", idUso));
            recarga = (Recarga) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getRecargaIdUso", ex);
        }
        return recarga;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga getRecargaById(int Id) {
        Recarga recarga = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("id", Id));
            recarga = (Recarga) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getRecargaById", ex);
        }
        return recarga;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PreRecargaSaldoVirtual getPreRecargaSaldoVirtualById(int Id) {
        PreRecargaSaldoVirtual recarga = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(PreRecargaSaldoVirtual.class);
            dc.add(Restrictions.eq("id", Id));
            recarga = (PreRecargaSaldoVirtual) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPreRecargaById", ex);
        }
        return recarga;
    }


    //Obtencion del ultimo saldo leido en recargas
    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga getPrimeraRecarga(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/) {
        Recarga recarga = null;
        List<Recarga> recargas = new ArrayList<Recarga>();
        try {
            Query query = null;
            /*  if(avisos==null){
             query=currentSession().createQuery("SELECT r FROM Recarga r WHERE r.tarjeta = :tarjeta AND DATE(r.fechaRealizacion)<= :fecha ORDER BY r.fechaRealizacion ASC");
             }else{*/
            query = currentSession().createQuery("SELECT r FROM Recarga r WHERE r.tarjeta = :tarjeta AND r.aviso in :avisos AND DATE(r.fechaRealizacion)<= :fecha ORDER BY r.fechaRealizacion ASC");
            // query.setParameterList("avisos", avisos);
            // }
            String fechaAux = DateUtils.yyyyMMddHHmmss_conFormato.format(fecha);
            query.setString("fecha", fechaAux.substring(0, 10));
            query.setParameter("tarjeta", tarjeta);
            recargas = query.list();
            if (!recargas.isEmpty()) {
                recarga = recargas.get(0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getPrimeraRecarga", ex);
        }
        return recarga;
    }

    //Obtencion del ultimo saldo leido en recargas
    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga getUltimoSaldoRecargaTarjeta(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/) {
        Recarga recarga = null;
        List<Recarga> recargas = new ArrayList<Recarga>();
        try {
            Query query = null;
            /*if(avisos==null){
             query=currentSession().createQuery("SELECT r FROM Recarga r WHERE r.saldoFinal is not null AND r.tarjeta = :tarjeta AND DATE(r.fechaRealizacion)<= :fecha ORDER BY r.fechaRealizacion DESC");
             }else{*/
            query = currentSession().createQuery("SELECT r FROM Recarga r WHERE r.saldoFinal is not null AND r.tarjeta = :tarjeta AND r.aviso in :avisos AND DATE(r.fechaRealizacion)<= :fecha ORDER BY r.fechaRealizacion DESC");
            //query.setParameterList("avisos", avisos);
            //}
            String fechaAux = DateUtils.yyyyMMddHHmmss_conFormato.format(fecha);
            query.setString("fecha", fechaAux.substring(0, 10));
            query.setParameter("tarjeta", tarjeta);
            recargas = query.list();
            if (!recargas.isEmpty()) {
                recarga = recargas.get(0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getUltimoSaldoRecargaTarjeta", ex);
        }
        return recarga;
    }


    //Obtencion del importe de las compras realizadas entre dos fechas
    @Transactional(propagation = Propagation.REQUIRED)
    public Double getImporteRecargasEntreFechas(Tarjeta tarjeta, Date fechaIni, Date fechaFin) {
        Double importe = 0.0;
        try {
            Query query = currentSession().createQuery("SELECT sum(r.importe) FROM Recarga r WHERE r.fechaRealizacion between :fechaIni and :fechaFin AND r.tarjeta = :tarjeta AND r.saldoFinal is null");
            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);
            query.setParameter("tarjeta", tarjeta);
            importe = (Double) query.list().get(0);
            if (importe == null) {
                importe = 0.0;
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteRecargasEntreFechas", ex);
        }
        return importe;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteRecargas() {
        BigDecimal importe = new BigDecimal(0.0);
        try {
            Query query = currentSession().createQuery("SELECT sum(r.importe) FROM Recarga r");
            importe = (BigDecimal) query.list().get(0);
            if (importe == null) {
                importe = new BigDecimal(0.0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getImporteRecargas", ex);
        }
        return importe;
    }


    //Obtencion del numero e importe totales de las recargas segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getRecargasNumeroImporte(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarSinSaldoLeido, Boolean eqNumeroOperacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetallesTotales(), QueryOperaciones.OPERACION_RECARGA, idCiudadano, idTarjeta, uid, null, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, null, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, buscarSinSaldoLeido, Boolean.TRUE, eqNumeroOperacion, null);
            totales = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getRecargasNumeroImporte", ex);
        }
        return (Object[]) totales.get(0);
    }



    //Obtencion del numero total de recargas por ambito segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getRecargasAmbitoNumeroHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesAmbitoTotales(), QueryOperaciones.OPERACION_RECARGA, idCiudadano, idTarjeta, uid, null, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, null, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            totales = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getRecargasAmbitoNumeroHql", ex);
        }
        return Long.valueOf(totales.size());
    }



    //Obtencion del numero total de recargas por centro segun criterios de busqueda
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getRecargasCentroNumeroHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion) {
        List totales = Arrays.asList(0l, 0.0);
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesCentrosTotales(), QueryOperaciones.OPERACION_RECARGA, idCiudadano, idTarjeta, uid, null, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, null, null, avisos, liquidacion, buscarPorLiquidacion, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            totales = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getRecargasCentroNumeroHql", ex);
        }
        return Long.valueOf(totales.size());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getTarjetaEstadisticasRecargas(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal) {
        Object[] estadisticas = new Object[3];
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("tarjeta", tarjeta));
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacion", fechaInicial, fechaFinal));
            }
            dc.setProjection(Projections.projectionList().add(Projections.rowCount()).add(Projections.sum("importe")).add(Projections.avg("importe")));
            estadisticas = (Object[]) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getTarjetaEstadisticasRecargas", ex);
        }

        if ((BigDecimal) (estadisticas[1]) == null) {
            estadisticas[1] = new BigDecimal(0);
        }

        if ((Double) (estadisticas[2]) == null) {
            estadisticas[2] = new Double(0);
        }

        return estadisticas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga anotarRecarga(Recarga recarga) {
        try {
            recarga.setIdUso("");
            this.currentSession().save(recarga);
            recarga.asignarIdUso();
            this.currentSession().update(recarga);
            return recarga;
        } catch (Exception e) {
            String debugger = e.toString();
            return recarga;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Recarga updateRecarga(Recarga recarga) {
        try {
            this.currentSession().update(recarga);
            return recarga;
        } catch (Exception e) {
            String debugger = e.toString();
            return recarga;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PreRecargaSaldoVirtual anotarPreRecargaSaldoVirtual(PreRecargaSaldoVirtual recarga) {
        recarga.setIdUso("");
        this.currentSession().save(recarga);
        recarga.asignarIdUso();
        this.currentSession().update(recarga);
        return recarga;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaRecarga(Recarga recarga) {
        this.currentSession().update(recarga);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaPreRecarga(PreRecargaSaldoVirtual preRecarga) {
        this.currentSession().update(preRecarga);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeNumeroOperacionRecarga(String numeroOperacion) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("numeroOperacion", numeroOperacion));
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeNumeroOperacionRecarga", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeRecargaAnulada(Integer idOperacion) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("idOperacionAnulada", idOperacion));
            //dc.add(Restrictions.not(Restrictions.eq("aviso", this.getAvisoNumero(403))));
            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeRecargaAnulada", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    // -- ROL -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public Rol getRolId(Integer id) {
        return (Rol) this.currentSession().get(Rol.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Rol> getRoles(Boolean asignable) {
        List<Rol> roles = new ArrayList<Rol>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Rol.class);
            if (asignable != null) {
                dc.add(Restrictions.eq("asignable", asignable));
            }
            roles = (List<Rol>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getRoles", ex);
        }
        return roles;
    }

    // -- TARJETAS -- //
    //INI -- MOD001
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getSaldoPrepago() {
        BigDecimal importe = new BigDecimal(0.0);
        try {
            Query query = currentSession().createQuery("SELECT sum(t.saldo) FROM Tarjeta t WHERE t.prioridad = 1");
            importe = (BigDecimal) query.list().get(0);
            if (importe == null) {
                importe = new BigDecimal(0.0);
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getSaldoPrepago", ex);
        }
        return importe;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tarjeta getTarjetaUid(String uidTarjeta) {
        Tarjeta tarjeta = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Tarjeta.class);
            dc.add(Restrictions.eq("uid", uidTarjeta));
            tarjeta = (Tarjeta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
            tarjeta.setAmbitosNoPermitidos(tarjeta.getAmbitosNoPermitidos());
        } catch (Exception ex) {
            logger.error( "DAOiml.getTarjetaUid", ex);
        }
        return tarjeta;
    }

    //FIN -- MOD001
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTarjeta(Tarjeta tarjeta) {
        this.currentSession().save(tarjeta);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaTarjeta(Tarjeta tarjeta) {
        try {
            this.currentSession().update(tarjeta);
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.modificaTarjeta", ex);
            throw ex;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getTarjetasCriteria(String idTarjeta, String uid, String tipoTarjeta, List<EstadoTarjeta> estadosTarjeta, String idCiudadano, String idIncidencia) {
        DetachedCriteria dc = null;

        if (tipoTarjeta.equals("")) {
            dc = DetachedCriteria.forClass(Tarjeta.class);
        } else if (tipoTarjeta.equals("PRE")) {
            dc = DetachedCriteria.forClass(TarjetaPrepago.class);
        } else if (tipoTarjeta.equals("POS")) {
            dc = DetachedCriteria.forClass(TarjetaPospago.class);
            /* }else if(tipoTarjeta.equals("VIS")){
             dc = DetachedCriteria.forClass(TarjetaVisitante.class);
             */
        } else if (tipoTarjeta.equals("PRE/POS")) {
            dc = DetachedCriteria.forClass(Tarjeta.class);
            dc.add(Restrictions.isNotNull("entidad"));
        }

        if (!idTarjeta.equals("")) {
            dc.add(Restrictions.ilike("idTarjeta", idTarjeta, MatchMode.START));
        }
        if (!uid.equals("")) {
            dc.add(Restrictions.ilike("uid", uid, MatchMode.START));
        }
        if (estadosTarjeta != null && !estadosTarjeta.isEmpty()) {
            dc.add(Restrictions.in("estado", estadosTarjeta));
        }
        if (!idCiudadano.equals("")) {
            dc.createAlias("entidad", "c");
            dc.add(Restrictions.ilike("c.idEntidad", idCiudadano, MatchMode.START));
        }

        if (!idIncidencia.equals("")) {
            dc.createAlias("incidencias", "i");
            dc.add(Restrictions.ilike("i.idIncidencia", idIncidencia, MatchMode.START));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTarjetasNumero(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano, String idIncidencia) {
        Long numeroTarjetas = 0l;
        try {
            DetachedCriteria dc = this.getTarjetasCriteria(idTarjeta, uid, tipoTarjeta, (estadoTarjeta == null ? null : Arrays.asList(estadoTarjeta)), idCiudadano, idIncidencia);

            dc.setProjection(Projections.rowCount());
            numeroTarjetas = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetasNumero", ex);
        }
        return numeroTarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTarjetasSaldoNegativoNumero(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano, String idIncidencia, BigDecimal saldo) {
        Long numeroTarjetas = 0l;
        try {
            DetachedCriteria dc = this.getTarjetasCriteria(idTarjeta, uid, tipoTarjeta, (estadoTarjeta == null ? null : Arrays.asList(estadoTarjeta)), idCiudadano, idIncidencia);
            dc.add(Restrictions.lt("saldo", new BigDecimal(0)));
            dc.setProjection(Projections.rowCount());
            numeroTarjetas = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetasNumero", ex);
        }
        return numeroTarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasListado(String idTarjeta, String uid, String tipoTarjeta, List<EstadoTarjeta> estadosTarjeta, String idCiudadano, String idIncidencia, Integer primerResultado) {
        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        try {
            DetachedCriteria dc = this.getTarjetasCriteria(idTarjeta, uid, tipoTarjeta, estadosTarjeta, idCiudadano, idIncidencia);
            //dc.addOrder(Order.desc("fechaPedido"));
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.TARJETAS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            if (primerResultado == null) {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetasListado", ex);
        }
        return tarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasSaldoNegativoListado(String idTarjeta, String uid, String tipoTarjeta, List<EstadoTarjeta> estadosTarjeta, String idCiudadano, String idIncidencia, BigDecimal saldo, Integer primerResultado) {
        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        try {
            DetachedCriteria dc = this.getTarjetasCriteria(idTarjeta, uid, tipoTarjeta, estadosTarjeta, idCiudadano, idIncidencia);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.TARIFICACION_FRAUDE);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("fechaPedido"));
            dc.add(Restrictions.lt("saldo", new BigDecimal(0)));

            if (primerResultado == null) {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetasSaldoNegativoListado", ex);
        }
        return tarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasListado(Integer estadoBitWise) {
        List<Tarjeta> tarjetas;
        DetachedCriteria dc = DetachedCriteria.forClass(Tarjeta.class);
        dc.createAlias("estado", "e");
        tarjetas = dc.getExecutableCriteria(this.currentSession()).list();
        return tarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarFechaUltimaModificacionTarjetas(Entidad ciudadano) {
        int id = ciudadano.getId();
        String queryString = "UPDATE Tarjeta SET fechaUltimaModificacion=NOW() WHERE idEntidad=?";
        int query = this.currentSession().createQuery(queryString).setInteger(0, id).executeUpdate();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarFechaUltimaModificacionTarjeta(Tarjeta tarjeta) {
        int id = tarjeta.getId();
        String queryString = "UPDATE Tarjeta SET fechaUltimaModificacion=NOW() WHERE id=?";
        int query = this.currentSession().createQuery(queryString).setInteger(0, id).executeUpdate();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Date getFechaActivacionTarjeta(Tarjeta tarjeta) {
        DetachedCriteria dc = DetachedCriteria.forClass(HistorialTarjeta.class);
        dc.add(Restrictions.eq("tarjeta", tarjeta));
        dc.add(Restrictions.eq("estado", getEstadoTarjetaId(EstadoTarjeta.ACTIVA)));
        dc.addOrder(Order.desc("fecha"));
        try {
            return ((HistorialTarjeta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list())).getFecha();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Date getFechaBloqueoTarjeta(Tarjeta tarjeta) {
        DetachedCriteria dc = DetachedCriteria.forClass(HistorialTarjeta.class);
        dc.add(Restrictions.eq("tarjeta", tarjeta));
        dc.add(Restrictions.ne("estado", getEstadoTarjetaId(EstadoTarjeta.ACTIVA)));
        dc.add(Restrictions.ne("estado", getEstadoTarjetaId(EstadoTarjeta.PENDIENTE_IMPRIMIR)));
        dc.add(Restrictions.ne("estado", getEstadoTarjetaId(EstadoTarjeta.PENDIENTE_ACTIVAR)));
        dc.add(Restrictions.ne("estado", getEstadoTarjetaId(EstadoTarjeta.IMPRIMIENDO)));
        dc.addOrder(Order.desc("fecha"));
        try {
            return ((HistorialTarjeta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list())).getFecha();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasEstado(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano) {
        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        try {
            DetachedCriteria dc = null;

            if (tipoTarjeta.equals("")) {
                dc = DetachedCriteria.forClass(Tarjeta.class);
            } else if (tipoTarjeta.equals("PRE")) {
                dc = DetachedCriteria.forClass(TarjetaPrepago.class);
            } else if (tipoTarjeta.equals("POS")) {
                dc = DetachedCriteria.forClass(TarjetaPospago.class);
                /*}else if(tipoTarjeta.equals("VIS")){
                 dc = DetachedCriteria.forClass(TarjetaVisitante.class);
                 */
            } else if (tipoTarjeta.equals("PRE/POS")) {
                dc = DetachedCriteria.forClass(Tarjeta.class);
                dc.add(Restrictions.isNotNull("ciudadano"));
            }

            if (!idTarjeta.equals("")) {
                dc.add(Restrictions.ilike("idTarjeta", idTarjeta, MatchMode.START));
            }
            if (!uid.equals("")) {
                dc.add(Restrictions.ilike("uid", uid, MatchMode.START));
            }
            if (estadoTarjeta != null) {
                dc.add(Restrictions.eq("estado", estadoTarjeta));
            }
            if (!idCiudadano.equals("")) {
                dc.createAlias("ciudadano", "c");
                dc.add(Restrictions.ilike("c.idCiudadano", idCiudadano, MatchMode.START));
            }
            tarjetas = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetasEstado", ex);
        }
        return tarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tarjeta getTarjetaIdTarjeta(String idTarjeta) {
        Tarjeta tarjeta = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Tarjeta.class);
            dc.add(Restrictions.eq("idTarjeta", idTarjeta));
            tarjeta = (Tarjeta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
            tarjeta.setAmbitosNoPermitidos(tarjeta.getAmbitosNoPermitidos());
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getTarjetaIdTarjeta", ex);
        }
        return tarjeta;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tarjeta getTarjetaId(Integer id) {
        return (Tarjeta) this.currentSession().get(Tarjeta.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Tarjeta buscarTarjeta(String uid, String idTarjeta, String idCiudadano) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Tarjeta.class);
            if (idTarjeta != null && !idTarjeta.isEmpty()) {
                dc.add(Restrictions.eq("idTarjeta", idTarjeta));
            }
            if (idCiudadano != null && !idCiudadano.isEmpty()) {
                dc.createAlias("ciudadano", "c");
                dc.add(Restrictions.eq("c.idCiudadano", idCiudadano));
            }
            if (uid != null && !uid.isEmpty()) {
                dc.add(Restrictions.eq("uid", uid));
            }
            return (Tarjeta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.buscarTarjeta", ex);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object[] getTarjetaEstadisticasCompras(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal) {
        Object[] estadisticas = new Object[3];
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.eq("tarjeta", tarjeta));
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fechaRealizacion", fechaInicial, fechaFinal));
            }
            dc.setProjection(Projections.projectionList().add(Projections.rowCount()).add(Projections.sum("importe")).add(Projections.avg("importe")));
            estadisticas = (Object[]) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getTarjetaEstadisticasCompras", ex);
        }
        if (estadisticas[1] == null) {
            estadisticas[1] = 0d;
        }
        if (estadisticas[2] == null) {
            estadisticas[2] = 0d;
        }
        return estadisticas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasListadoControlFraude(Integer id, Integer numTarjetas) {
        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TarjetaPrepago.class);
            dc.add(Restrictions.gt("id", id));
            dc.addOrder(Order.asc("id"));
            tarjetas = dc.getExecutableCriteria(this.currentSession()).setMaxResults(numTarjetas).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTarjetasListadoControlFraude", ex);
        }
        return tarjetas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TarjetaPrepago getUltimaTarjetaPrepago() {
        TarjetaPrepago tarjeta = new TarjetaPrepago();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TarjetaPrepago.class);
            dc.addOrder(Order.desc("id"));
            tarjeta = (TarjetaPrepago) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).setMaxResults(1).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUltimaTarjetaPrepago", ex);
        }
        return tarjeta;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tarjeta> getTarjetasImprimirWS(InformacionTarjetaStrategy strategy, CentroRecogida centroRecogida, String tipoTarjeta, Integer tipoEntidad, Integer prioridad, String idCiudadano, EstadoTarjeta estado, String idTarjeta, String UID, Integer numeroTarjetas) {

        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        try {
            DetachedCriteria dc = (tipoTarjeta.equals("") ? DetachedCriteria.forClass(Tarjeta.class) : (tipoTarjeta.equals("PRE") ? DetachedCriteria.forClass(TarjetaPrepago.class) : DetachedCriteria.forClass(TarjetaPospago.class)));

            dc = strategy.restrictionsToCriteria(dc);

            if (centroRecogida != null) {
                dc.add(Restrictions.eq("centroRecogida", centroRecogida));
            } else {
                dc.add(Restrictions.isNull("centroRecogida"));
            }

            if (prioridad != null) {
                dc.add(Restrictions.eq("prioridad", prioridad));
            }

            if (!idCiudadano.equals("") || tipoEntidad != null) {
                dc.createAlias("entidad", "e");

                if (!idCiudadano.equals("")) {
                    dc.add(Restrictions.ilike("e.idEntidad", idCiudadano));
                }

                if (tipoEntidad != null) {
                    dc.add(Restrictions.eq("e.tipoEntidad", tipoEntidad));
                }

            }

            if (estado != null) {
                dc.add(Restrictions.eq("estado", estado));
            }

            if (!idTarjeta.equals("")) {
                dc.add(Restrictions.ilike("idTarjeta", idTarjeta));
            }

            if (!UID.equals("")) {
                dc.add(Restrictions.ilike("uid", idTarjeta));
            }

            if (numeroTarjetas == null) {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tarjetas = dc.getExecutableCriteria(this.currentSession()).setMaxResults(numeroTarjetas).list();
            }

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTarjetasImprimirWS", ex);
        }
        return tarjetas;
    }

    // -- TIPO ACCION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoAccion getTipoAccionId(Integer id) {
        return (TipoAccion) this.currentSession().get(TipoAccion.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoAccion> getTipoAccion() {
        List<TipoAccion> tiposAccion = new ArrayList<TipoAccion>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoAccion.class);
            tiposAccion = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoAccion", ex);
        }
        return tiposAccion;

    }

    // -- TIPO ENTIDAD -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoEntidad getTipoEntidadId(Integer id) {
        return (TipoEntidad) this.currentSession().get(TipoEntidad.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoEntidad> getTipoEntidades() {
        List<TipoEntidad> tiposEntidades = new ArrayList<TipoEntidad>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoEntidad.class);
            tiposEntidades = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoEntidades", ex);
        }
        return tiposEntidades;
    }

    // -- TIPOLOGIA CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaTipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano) {
        this.currentSession().update(tipologiaCiudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano) {
        this.currentSession().save(tipologiaCiudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasCiudadano(Entidad ciudadano) {
        List<TipologiaCiudadano> tipologiaCiudadanos = new ArrayList<TipologiaCiudadano>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class);
            dc.add(Restrictions.eq("ciudadano", ciudadano));
            tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasCiudadano", ex);
        }
        return tipologiaCiudadanos;
    }

    private DetachedCriteria getTipologiasCiudadanoCriteria(String idCiudadano, Date fecha, Boolean activa) {
        DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class);
        if (idCiudadano != null && !idCiudadano.equals("")) {
            dc.createAlias("ciudadano", "c");
            dc.add(Restrictions.like("c.idEntidad", idCiudadano, MatchMode.START));
        }
        if (fecha != null) {
            dc.add(Restrictions.le("fechaInicio", fecha));
            dc.add(Restrictions.ge("fechaCaducidad", fecha));
        }
        if (activa != null) {
            dc.add(Restrictions.eq("activa", activa));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getTipologiasHQLFROMWHERE(String idCiudadano, TipoAsignacion tipoAsignacion, TipoCiudadano tCiudadano, Date fechaOperacion, Boolean activa) {
        List<TipoCiudadano> tiposCiudadanoDinamicos = this.getTiposCiudadano();
        String queryRaw = "FROM Entidad e";
        queryRaw += " left outer join TipoCiudadano tc ON tc.tipoAsignacion.id=1 ";

        boolean seHaPuestoOrAsignacionDinamica = false;
        boolean firstAnd = false;
        for (TipoCiudadano tipoCiudadano : tiposCiudadanoDinamicos) {
            if (tipoCiudadano.getTipoAsignacion().getId() == TipoAsignacion.EDAD) {

                if (!seHaPuestoOrAsignacionDinamica) {
                    queryRaw += "OR ";
                    seHaPuestoOrAsignacionDinamica = true;
                }

                queryRaw += (firstAnd ? " OR " : " ");
                queryRaw += "(" + tipoCiudadano.getRestriccionString() + ")";
                firstAnd = true;
            }
        }

        queryRaw += " left outer join TipologiaCiudadano ti ON ti.ciudadano.id=e.id AND ti.tipoCiudadano.id=tc.id";
        queryRaw += " WHERE ";

        boolean requiredAnd = false;

        if (tipoAsignacion != null) {
            queryRaw += (requiredAnd ? " AND " : " ");
            queryRaw += "tc.tipoAsignacion.id = " + tipoAsignacion.getId() + " ";
            requiredAnd = true;
        }

        if (tCiudadano != null) {
            queryRaw += (requiredAnd ? " AND " : " ");
            queryRaw += "tc.id = " + tCiudadano.getId() + " ";
            requiredAnd = true;
        }

        if (!idCiudadano.equals("")) {
            queryRaw += (requiredAnd ? " AND " : " ");
            queryRaw += "e.idEntidad like '" + idCiudadano + "%' ";
            requiredAnd = true;
        }

        if (activa != null) {
            queryRaw += (requiredAnd ? " AND " : " ");
            queryRaw += "(tc.tipoAsignacion.id=2 OR ti.activa = " + activa + ")";
            requiredAnd = true;
        }

        if (fechaOperacion != null) {
            queryRaw += (requiredAnd ? " AND " : " ");
            queryRaw += "(tc.tipoAsignacion.id=2 OR '" + DateUtils.hibernateFormat.format(fechaOperacion) + "' between ti.fechaInicio and ti.fechaCaducidad)";
            requiredAnd = true;
        }

        queryRaw += (requiredAnd ? " AND " : " ");
        queryRaw += "(tc.tipoAsignacion.id=2 OR (tc.tipoAsignacion.id=1 AND ti.id is not null))";

        return queryRaw;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTipologiasNumeroHQL(String idCiudadano, TipoAsignacion tipoAsignacion, TipoCiudadano tCiudadano, Date fechaOperacion, Boolean activa) {
        Long numero = 0l;
        try {
            String queryRaw = "SELECT COUNT(tc.id) " + this.getTipologiasHQLFROMWHERE(idCiudadano, tipoAsignacion, tCiudadano, fechaOperacion, activa);
            numero = (Long) this.currentSession().createQuery(queryRaw).uniqueResult();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasNumeroHQL", ex);
        }

        return numero;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasHQL(String idCiudadano, TipoAsignacion tipoAsignacion, TipoCiudadano tCiudadano, Date fechaOperacion, Boolean activa, Integer desde) {
        List<TipologiaCiudadano> tipologiaCiudadano = new ArrayList<TipologiaCiudadano>();

        try {

            String queryRaw = "SELECT new TipologiaCiudadano(ti,tc,e) " + this.getTipologiasHQLFROMWHERE(idCiudadano, tipoAsignacion, tCiudadano, fechaOperacion, activa);
            // List<TipoCiudadano> tiposCiudadanoDinamicos = this.getTiposCiudadano();
            /*String queryRaw = "SELECT new TipologiaCiudadano(ti,tc,e) FROM Entidad e";
             queryRaw += " left outer join TipoCiudadano tc ON tc.tipoAsignacion.id=1 OR";

             boolean firstAnd = false;
             for (TipoCiudadano tipoCiudadano : tiposCiudadanoDinamicos) {
             if (tipoCiudadano.getTipoAsignacion().getId() == TipoAsignacion.EDAD) {
             queryRaw += (firstAnd ? " OR " : " ");
             queryRaw += "(" + tipoCiudadano.getRestriccionString() + ")";
             firstAnd = true;
             }
             }

             queryRaw += " left outer join TipologiaCiudadano ti ON ti.ciudadano.id=e.id AND ti.tipoCiudadano.id=tc.id";
             queryRaw += " WHERE  e.idEntidad='" + idCiudadano + "' AND (tc.tipoAsignacion.id=2 OR (tc.tipoAsignacion.id=1 AND ti.id is not null))";*/
            if (desde != null) {
                tipologiaCiudadano = this.currentSession().createQuery(queryRaw).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            } else {
                tipologiaCiudadano = this.currentSession().createQuery(queryRaw).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasHQL", ex);
        }

        return tipologiaCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasActivasCiudadanoYDin(Entidad ciudadano) {
        List<TipologiaCiudadano> tipologiasCiudadanoAll = new ArrayList<TipologiaCiudadano>();
        List<TipologiaCiudadano> tipologiaCiudadanos = getTipologiasHQL(ciudadano.
                getIdCiudadano(), null, null, null, Boolean.TRUE, null);

        for (int i = 0; i < tipologiaCiudadanos.size(); i++) {
            tipologiasCiudadanoAll.add(i, tipologiaCiudadanos.get(i));

        }

        return tipologiasCiudadanoAll;
    }

    public List<TipologiaCiudadano> getTipologiasCiudadano(String idCiudadano, Date fecha, Boolean activa, Integer desde) {
        List<TipologiaCiudadano> tipologiaCiudadanos = new ArrayList<TipologiaCiudadano>();
        try {

            DetachedCriteria dc = this.getTipologiasCiudadanoCriteria(idCiudadano, fecha, activa);

            if (desde == null) {
                tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasCiudadano", ex);
        }
        return tipologiaCiudadanos;
    }

    public Long getTipologiasCiudadanoNumero(String idCiudadano, Date fecha, Boolean activa) {
        Long numeroTipologiaCiudadanos = 0l;
        try {

            DetachedCriteria dc = this.getTipologiasCiudadanoCriteria(idCiudadano, fecha, activa);
            dc.setProjection(Projections.rowCount());
            numeroTipologiaCiudadanos = (Long) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasCiudadanoNumero", ex);
        }
        return numeroTipologiaCiudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasCiudadanoFecha(Entidad ciudadano, Date fecha) {
        List<TipologiaCiudadano> tipologiaCiudadanos = new ArrayList<TipologiaCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class
            );
            dc.add(Restrictions.eq("ciudadano", ciudadano));
            dc.add(Restrictions.eq("activa", true));
            dc.add(Restrictions.le("fechaInicio", fecha));
            dc.add(Restrictions.ge("fechaCaducidad", fecha));
            tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasCiudadanoFecha", ex);
        }
        return tipologiaCiudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasActivasCiudadano(Entidad ciudadano) {
        List<TipologiaCiudadano> tipologiaCiudadanos = new ArrayList<TipologiaCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class
            );
            dc.add(Restrictions.eq("ciudadano", ciudadano));
            dc.add(Restrictions.eq("activa", true));
            tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasActivasCiudadano", ex);
        }
        return tipologiaCiudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Integer> getIdTipologiasActivasCiudadano(Entidad ciudadano) {
        List<Integer> idsTipologiaCiudadano = new ArrayList<Integer>();
        List<TipologiaCiudadano> tipologiaCiudadanos = getTipologiasHQL(ciudadano.
                getIdCiudadano(), null, null, null, Boolean.TRUE, null);

        for (int i = 0; i < tipologiaCiudadanos.size(); i++) {
            idsTipologiaCiudadano.add(i, tipologiaCiudadanos.get(i).getTipoCiudadano().getId());

        }

        return idsTipologiaCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipologiaCiudadano> getTipologiasComprasOfflineCiudadano(Entidad ciudadano, Date fecha) {
        List<TipologiaCiudadano> tipologiaCiudadanos = new ArrayList<TipologiaCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class
            );
            dc.add(Restrictions.eq("ciudadano", ciudadano));
            dc.add(Restrictions.disjunction()
                    .add(Restrictions.eq("activa", true))
                    .add(Restrictions.conjunction()
                            .add(Restrictions.ne("fechaDesactivada", null))
                            .add(Restrictions.gt("fechaDesactivada", fecha))));
            tipologiaCiudadanos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiasComprasOfflineCiudadano", ex);
        }
        return tipologiaCiudadanos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipologiaCiudadano getTipologiaCiudadanoId(Integer id) {
        TipologiaCiudadano tipologiaCiudadano = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipologiaCiudadano.class
            );
            dc.add(Restrictions.eq("id", id));
            tipologiaCiudadano = (TipologiaCiudadano) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTipologiaCiudadanoId", ex);
        }
        return tipologiaCiudadano;
    }

    // -- TIPOLOGIA CIUDADANO DOCUMENTO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipologiaCiudadanoDocumento(TipologiaCiudadanoDocumento tipologiaCiudadanoDocumento) {
        this.currentSession().save(tipologiaCiudadanoDocumento);
    }

    // -- TIPO ASIGNACION -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoAsignacion
    getTiposAsignacionId(Integer id) {
        return (TipoAsignacion) this.currentSession().get(TipoAsignacion.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoAsignacion> getTiposAsignacion() {
        List<TipoAsignacion> tiposAsignacion = new ArrayList<TipoAsignacion>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoAsignacion.class
            );
            tiposAsignacion = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposAsignacion", ex);
        }
        return tiposAsignacion;
    }

    // -- TIPO CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadano> getTiposCiudadano() {
        List<TipoCiudadano> tiposCiudadano = new ArrayList<TipoCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadano.class
            );
            tiposCiudadano = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadano", ex);
        }
        return tiposCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadano> getTiposCiudadanoDinamicos(Entidad entidad) {
        List<TipoCiudadano> tiposCiudadano = new ArrayList<TipoCiudadano>();
        try {
            List<TipoRestriccion> tiposRestriccion = this.getTiposRestriccion();
            String isOr = "";
            String isAnd = "";
            for (TipoRestriccion tipoRestriccion : tiposRestriccion) {
                tipoRestriccion.getQuery(Boolean.TRUE);
                if (!isOr.equals("") && !isAnd.equals("")) {
                    isOr += " OR ";
                    isAnd += " OR ";
                }

                isOr += tipoRestriccion.getQuery(Boolean.TRUE);
                isAnd += tipoRestriccion.getQuery(Boolean.FALSE);

            }

            String query = "SELECT distinct(tc) FROM Entidad e, TipoCiudadano tc, Restriccion r"
                    + " WHERE e.id=" + entidad.getId() + " and tc.tipoAsignacion.id=" + TipoAsignacion.EDAD + " and (("
                    + "tc.isOr is true and tc.id in (SELECT r.tipoCiudadano.id from Restriccion r where " + isOr + ")) or ("
                    + "tc.isOr is false and tc.id not in (SELECT r.tipoCiudadano.id from Restriccion r where " + isAnd + ")))";

            tiposCiudadano = currentSession().createQuery(query).list();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoDinamicos", ex);
        }
        return tiposCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoRestriccion
    getTiposRestriccionId(Integer id) {
        return (TipoRestriccion) this.currentSession().get(TipoRestriccion.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoRestriccion> getTiposRestriccion() {
        List<TipoRestriccion> tiposRestriccion = new ArrayList<TipoRestriccion>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoRestriccion.class
            );
            dc.setFetchMode(
                    "campos", FetchMode.JOIN);
            dc.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            tiposRestriccion = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposRestriccion", ex);
        }
        return tiposRestriccion;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadano> getTiposCiudadanoTipoAsignacion(TipoAsignacion tipoAsignacion, int tipoEntidad) {
        List<TipoCiudadano> tiposCiudadano = new ArrayList<TipoCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadano.class);
            dc.add(Restrictions.eq("tipoAsignacion", tipoAsignacion));
            dc.add(Restrictions.eq("tipoEntidad", tipoEntidad));
            tiposCiudadano = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoTipoAsignacion", ex);
        }
        return tiposCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoCiudadano
    getTipoCiudadanoId(Integer id) {
        return (TipoCiudadano) this.currentSession().get(TipoCiudadano.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoCiudadano getTipoCiudadanoIdTipoCiudadano(String idTipoCiudadano) {
        TipoCiudadano tipoCiudadano = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadano.class
            );
            dc.add(Restrictions.eq("idTipoCiudadano", idTipoCiudadano));
            tipoCiudadano = (TipoCiudadano) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoCiudadanoIdTipoCiudadano", ex);
        }
        return tipoCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getTiposCiudadanoCriteria(String idTipoCiudadano, String nombre, TipoAsignacion asignacion) {
        DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadano.class
        );
        if (!idTipoCiudadano.equals(
                "")) {
            dc.add(Restrictions.eq("idTipoCiudadano", idTipoCiudadano));
        }

        if (!nombre.equals(
                "")) {
            dc.add(Restrictions.eq("nombre", nombre));
        }
        if (asignacion
                != null) {
            dc.add(Restrictions.eq("tipoAsignacion", asignacion));
        }
        return dc;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadano> getTiposCiudadanoListado(String idTipoCiudadano, String nombre, TipoAsignacion asignacion, Integer desde) {
        List<TipoCiudadano> tiposCiudadano = new ArrayList<TipoCiudadano>();
        try {
            DetachedCriteria dc = this.getTiposCiudadanoCriteria(idTipoCiudadano, nombre, asignacion);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.TARIFICACION_TIPOS_CIUDADANO);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (desde == null) {
                tiposCiudadano = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tiposCiudadano = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoListado", ex);
        }
        return tiposCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTiposCiudadanoNumero(String idTipoCiudadano, String nombre, TipoAsignacion asignacion) {
        Long numeroTiposCiudadano = 0l;
        try {
            DetachedCriteria dc = this.getTiposCiudadanoCriteria(idTipoCiudadano, nombre, asignacion);
            dc.setProjection(Projections.rowCount());
            numeroTiposCiudadano = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoNumero", ex);
        }
        return numeroTiposCiudadano;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.currentSession().save(tipoCiudadano);
        tipoCiudadano.asignarIdTipoCiudadano();
        this.currentSession().update(tipoCiudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.currentSession().merge(tipoCiudadano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoAsignacion
    getTipoAsignacionId(Integer id) {
        return (TipoAsignacion) this.currentSession().get(TipoAsignacion.class, id);
    }

    // -- TIPO CIUDADANO AMBITO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoCiudadanoAmbito getTipoCiudadanoAmbitoTipoCiudadanoAmbito(TipoCiudadano tipoCiudadano, Ambito ambito) {
        TipoCiudadanoAmbito tiposCiudadanoAmbito = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoAmbito.class
            );
            dc.add(Restrictions.eq("tipoCiudadano", tipoCiudadano));
            dc.add(Restrictions.eq("tipoAmbito", ambito));
            tiposCiudadanoAmbito = (TipoCiudadanoAmbito) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoCiudadanoAmbitoTipoCiudadanoAmbito", ex);
        }
        return tiposCiudadanoAmbito;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadanoAmbito> getTiposCiudadanoAmbitoTipoCiudadano(TipoCiudadano tipoCiudadano) {
        List<TipoCiudadanoAmbito> tiposCiudadanoAmbito = new ArrayList<TipoCiudadanoAmbito>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoAmbito.class
            );
            dc.add(Restrictions.eq("tipoCiudadano", tipoCiudadano));
            tiposCiudadanoAmbito = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoAmbitoTipoCiudadano", ex);
        }
        return tiposCiudadanoAmbito;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipoCiudadanoAmbito(TipoCiudadanoAmbito tipoCiudadanoAmbito) {
        this.currentSession().save(tipoCiudadanoAmbito);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaTipoCiudadanoAmbito(TipoCiudadanoAmbito tipoCiudadanoAmbito) {
        this.currentSession().update(tipoCiudadanoAmbito);
    }

    // -- TIPO CIUDADANO DOCUMENTOS -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public TipoCiudadanoDocumento getTipoCiudadanoDocumentoId(Integer id) {
        TipoCiudadanoDocumento tipoCiudadanoDocumento = new TipoCiudadanoDocumento();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoDocumento.class
            );
            dc.add(Restrictions.eq("id", id));
            tipoCiudadanoDocumento = (TipoCiudadanoDocumento) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoCiudadanoDocumentoId", ex);
        }
        return tipoCiudadanoDocumento;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadanoDocumento> getTiposCiudadanoDocumentoTipoCiudadanoAdjunto(TipoCiudadano tipoCiudadano, Boolean adjunto) {
        List<TipoCiudadanoDocumento> tiposCiudadanoDocumento = new ArrayList<TipoCiudadanoDocumento>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoDocumento.class
            );
            dc.add(Restrictions.eq("tipoCiudadano", tipoCiudadano));
            dc.add(Restrictions.eq("adjunto", adjunto));
            tiposCiudadanoDocumento = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoDocumentoTipoCiudadanoAdjunto", ex);
        }
        return tiposCiudadanoDocumento;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadanoDocumento> getTiposCiudadanoDocumentoTipoCiudadano(TipoCiudadano tipoCiudadano) {
        List<TipoCiudadanoDocumento> tiposCiudadanoDocumento = new ArrayList<TipoCiudadanoDocumento>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoDocumento.class
            );
            dc.add(Restrictions.eq("tipoCiudadano", tipoCiudadano));
            tiposCiudadanoDocumento = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoDocumentoTipoCiudadano", ex);
        }
        return tiposCiudadanoDocumento;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipoCiudadanoDocumento(TipoCiudadanoDocumento tipoCiudadanoDocumento) {
        this.currentSession().save(tipoCiudadanoDocumento);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void borraTipoCiudadanoDocumento(TipoCiudadanoDocumento tipoCiudadanoDocumento) {
        this.currentSession().delete(tipoCiudadanoDocumento);
    }

    // -- TIPO CIUDADANO RESTRICCIONES -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoCiudadanoRestriccion> getTiposCiudadanoRestriccionTipoCiudadano(TipoCiudadano tipoCiudadano) {
        List<TipoCiudadanoRestriccion> tiposCiudadanoRestriccion = new ArrayList<TipoCiudadanoRestriccion>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoCiudadanoRestriccion.class
            );
            dc.add(Restrictions.eq("tipoCiudadano", tipoCiudadano));
            tiposCiudadanoRestriccion = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposCiudadanoRestriccionTipoCiudadano", ex);
        }
        return tiposCiudadanoRestriccion;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaTipoCiudadanoRestricciones(TipoCiudadanoRestriccion tipoCiudadanoRestriccion) {
        this.currentSession().save(tipoCiudadanoRestriccion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaTipoCiudadanoRestricciones(TipoCiudadanoRestriccion tipoCiudadanoRestriccion) {
        this.currentSession().update(tipoCiudadanoRestriccion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void borraTipoCiudadanoRestricciones(TipoCiudadanoRestriccion tipoCiudadanoRestriccion) {
        this.currentSession().delete(tipoCiudadanoRestriccion);
    }

    // -- TIPO DOCUMENTO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoDocumento> getTiposDocumento() {
        List<TipoDocumento> tiposDocumento = new ArrayList<TipoDocumento>();

        try {
            List<TipoDocumento> td = new ArrayList<TipoDocumento>();
            DetachedCriteria dc = DetachedCriteria.forClass(TipoDocumento.class
            );
            td = dc.getExecutableCriteria(this.currentSession()).list();
            //Noelia quiere que salga en primer lugar el DNI
            for (TipoDocumento tipo : td) {
                if (tipo.getId() == 2) {
                    tiposDocumento.add(0, tipo);
                } else {
                    tiposDocumento.add(tipo);
                }
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposDocumento", ex);
        }
        return tiposDocumento;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoDocumento
    getTiposDocumentoId(Integer id) {
        return (TipoDocumento) this.currentSession().get(TipoDocumento.class, id);
    }

    // -- TIPO INCIDENCIA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoIncidencia> getTipoIncidencia() {
        List<TipoIncidencia> tiposIncidencia = new ArrayList<TipoIncidencia>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoIncidencia.class
            );
            tiposIncidencia = dc.getExecutableCriteria(currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTipoIncidencia", ex);
        }
        return tiposIncidencia;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoIncidencia
    getTipoIncidenciaId(Integer id) {
        return (TipoIncidencia) this.currentSession().get(TipoIncidencia.class, id);
    }

    // -- TIPO USUARIO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoUsuario> getTiposUsuario() {
        List<TipoUsuario> tiposUsuario = new ArrayList<TipoUsuario>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoUsuario.class
            );
            tiposUsuario = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposUsuario", ex);
        }
        return tiposUsuario;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoUsuario
    getTiposUsuarioId(Integer id) {
        return (TipoUsuario) this.currentSession().get(TipoUsuario.class, id);
    }

    // -- TIPO TUTOR -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoTutor> getTiposTutor() {
        List<TipoTutor> tiposTutor = new ArrayList<TipoTutor>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoTutor.class
            );
            tiposTutor = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposTutor", ex);
        }
        return tiposTutor;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoTutor
    getTiposTutorId(Integer id) {
        return (TipoTutor) this.currentSession().get(TipoTutor.class, id);
    }

    // -- TRANSICION ESTADOS CIUDADANO -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TransicionEstadoCiudadano> getTransicionesEstadoCiudadano(EstadoCiudadano estadoCiudadano) {
        List<TransicionEstadoCiudadano> transiciones = new ArrayList<TransicionEstadoCiudadano>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TransicionEstadoCiudadano.class
            );
            dc.add(Restrictions.eq("estadoOrigen", estadoCiudadano));
            transiciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTransicionesEstadoCiudadano", ex);
        }
        return transiciones;
    }

    // -- TRANSICION ESTADOS TARJETA -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public List<TransicionEstadoTarjeta> getTransicionesEstadoTarjeta(EstadoTarjeta estadoTarjeta) {
        List<TransicionEstadoTarjeta> transiciones = new ArrayList<TransicionEstadoTarjeta>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TransicionEstadoTarjeta.class
            );
            dc.add(Restrictions.eq("estadoOrigen", estadoTarjeta));
            transiciones = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTransicionesEstadoTarjeta", ex);
        }
        return transiciones;
    }

    // -- USERS -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaUser(User user) {
        this.currentSession().update(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User getUser(String username, String password) {
        User user = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(User.class
            );
            dc.add(Restrictions.eq("username", username));
            dc.add(Restrictions.eq("password", password));
            user = (User) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUser", ex);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User getUserUsername(String username) {
        User user = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(User.class);
            dc.add(Restrictions.eq("username", username));
            user = (User) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(em.unwrap(Session.class).getSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getUserUsername", ex);
        }
        return user;
    }

    // -- USUARIOS -- //
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaUsuario(Usuario usuario) {
        this.currentSession().save(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaUsuario(Usuario usuario) {
        this.currentSession().update(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario getUsuarioDocumento(TipoDocumento tipoDocumento, String documento) {
        Usuario usuario = null;

        try {
            DetachedCriteria rcrit = DetachedCriteria.forClass(Usuario.class
            );
            rcrit.add(Restrictions.eq("tipoDocumento", tipoDocumento));
            rcrit.add(Restrictions.eq("documento", documento));
            usuario = (Usuario) DataAccessUtils.uniqueResult(rcrit.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuarioDocumento", ex);
        }
        return usuario;

    }

    private DetachedCriteria getUsuariosCriteria(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento) {
        DetachedCriteria dc = DetachedCriteria.forClass(Usuario.class
        );
        if (!username.equals(
                "")) {
            dc.add(Restrictions.ilike("username", username, MatchMode.START));
        }

        if (!nombre.equals(
                "")) {
            dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
        }

        if (!apellidos.equals(
                "")) {
            dc.add(Restrictions.ilike("apellidos", apellidos, MatchMode.ANYWHERE));
        }

        if (!documento.equals(
                "")) {
            dc.add(Restrictions.ilike("documento", documento, MatchMode.START));
        }
        if (tipoDocumento
                != null) {
            dc.add(Restrictions.eq("tipoDocumento", tipoDocumento));
        }
        if (perfil
                != null) {
            dc.add(Restrictions.eq("perfil", perfil));
        }
        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> getUsuariosListado(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento, Integer primerResultado) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            DetachedCriteria dc = this.getUsuariosCriteria(username, perfil, nombre, apellidos, documento, tipoDocumento);
            if (primerResultado == null) {
                usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuariosListado", ex);
        }
        return usuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> getUsuariosListadoPorIdDescendente(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento, Integer primerResultado) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            DetachedCriteria dc = this.getUsuariosCriteria(username, perfil, nombre, apellidos, documento, tipoDocumento);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.GESTION_USUARIOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuariosListado", ex);
        }
        return usuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getUsuariosNumero(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento) {
        Long numeroUsuarios = 0l;
        try {
            DetachedCriteria dc = this.getUsuariosCriteria(username, perfil, nombre, apellidos, documento, tipoDocumento);
            dc.setProjection(Projections.rowCount());
            numeroUsuarios = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuariosListado", ex);
        }
        return numeroUsuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Usuario.class
            );
            usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuarios", ex);
        }
        return usuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> getUsuariosAsignables() {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Usuario.class
            );
            dc.add(Restrictions.eq("asignable", Boolean.TRUE));
            usuarios = (List<Usuario>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuariosAsignables", ex);
        }
        return usuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario
    getUsuarioId(Integer id) {
        return (Usuario) this.currentSession().get(Usuario.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CentroImpresion getCentroImpresionIdCentroImpresion(String idCentroImpresion) {
        CentroImpresion centroImpresion = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroImpresion.class
            );
            dc.add(Restrictions.eq("idCentroImpresion", idCentroImpresion));
            centroImpresion = (CentroImpresion) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentroImpresionIdCentroImpresion", ex);
        }
        return centroImpresion;
    }

    //Juan 27-10-17 Método para coger un usuario desde su identificador
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario getUsuarioIdUsuario(int idUsuario) {
        Usuario usuario = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Usuario.class);
            dc.add(Restrictions.eq("id", idUsuario));
            usuario = (Usuario) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getUsuarioIdUsuario", ex);
        }
        return usuario;
    }

    //PUESTO IMPRESION
    @Transactional(propagation = Propagation.REQUIRED)
    public PuestoImpresion getPuestoImpresionIdPuestoImpresion(String idPuestoImpresion) {
        PuestoImpresion puestoImpresion = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(PuestoImpresion.class
            );
            dc.add(Restrictions.eq("idPuestoImpresion", idPuestoImpresion));
            puestoImpresion = (PuestoImpresion) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPuestoImpresionIdPuestoImpresion", ex);
        }
        return puestoImpresion;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<PuestoImpresion> getPuestosImpresion() {
        List<PuestoImpresion> puestosImpresion = new ArrayList<PuestoImpresion>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(PuestoImpresion.class
            );
            puestosImpresion = (List<PuestoImpresion>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPuestosImpresion", ex);
        }
        return puestosImpresion;
    }

    //COMU
    @Transactional(propagation = Propagation.REQUIRED)
    public Comu
    getComuId(Integer id) {
        return (Comu) this.currentSession().get(Comu.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Comu> getComus() {
        List<Comu> comus = new ArrayList<Comu>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Comu.class
            );
            comus = (List<Comu>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComus", ex);
        }
        return comus;
    }

    // --- PERFILES ---//
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaPerfil(Perfil perfil) {
        perfil.setIdPerfil("");
        this.currentSession().save(perfil);
        perfil.asignarIdPerfil();
        perfil.setOrden(perfil.getId());
        this.currentSession().update(perfil);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaPerfil(Perfil perfil) {
        this.currentSession().update(perfil);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Perfil> getPerfiles() {
        List<Perfil> perfiles = new ArrayList<Perfil>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Perfil.class
            );
            dc.add(Restrictions.eq("mostrar", Boolean.TRUE));
            dc.addOrder(Order.asc("orden"));
            perfiles = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPerfiles", ex);
        }
        return perfiles;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Perfil
    getPerfilId(Integer id) {
        return (Perfil) this.currentSession().get(Perfil.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Perfil getPerfilIdPerfil(String idPerfil) {
        Perfil perfil = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Perfil.class
            );
            dc.add(Restrictions.eq("idPerfil", idPerfil));
            perfil = (Perfil) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPerfilIdPerfil", ex);
        }
        return perfil;

    }

    private DetachedCriteria getPerfilesCriteria(String idPerfil, String nombre, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Boolean requiereComercio) {
        DetachedCriteria dc = DetachedCriteria.forClass(Perfil.class
        );
        if (!idPerfil.equals(
                "")) {
            dc.add(Restrictions.ilike("idPerfil", idPerfil, MatchMode.START));
        }

        if (!nombre.equals(
                "")) {
            dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
        }

        if (requiereAmbito
                != null) {
            dc.add(Restrictions.eq("requiereAmbito", requiereAmbito));
        }

        if (requiereComercio
                != null) {
            dc.add(Restrictions.eq("requiereComercio", requiereAmbito));
        }

        if (requiereCentro
                != null) {
            dc.add(Restrictions.eq("requiereCentro", requiereCentro));
        }

        if (requierePuestoImpresion
                != null) {
            dc.add(Restrictions.eq("requierePuestoImpresion", requierePuestoImpresion));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Perfil> getPerfilesListado(String idPerfil, String nombre, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Integer primerResultado, Boolean requiereComercio) {
        List<Perfil> perfiles = new ArrayList<Perfil>();
        try {
            DetachedCriteria dc = this.getPerfilesCriteria(idPerfil, nombre, requiereAmbito, requiereCentro, requierePuestoImpresion, requiereComercio);

            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.GESTION_PERFILES);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            if (primerResultado == null) {
                perfiles = (List<Perfil>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                perfiles = (List<Perfil>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPerfilesListado", ex);
        }
        return perfiles;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getPerfilesNumero(String idPerfil, String nombre, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Boolean requiereComercio) {
        Long numeroUsuarios = 0l;
        try {
            DetachedCriteria dc = this.getPerfilesCriteria(idPerfil, nombre, requiereAmbito, requiereCentro, requierePuestoImpresion, requiereComercio);
            dc.setProjection(Projections.rowCount());
            numeroUsuarios = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPerfilesNumero", ex);
        }
        return numeroUsuarios;
    }

    // --- PROCESO ---//
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaUpdateProceso(ProcesoBD proceso) {
        try {
            if (proceso.getId() == null) {
                this.currentSession().save(proceso);
                proceso.asignarIdPerfil();
                File folder = new File(this.getAppConfigId(AppConfig.CARPETA_PROCESOS).getValor() + proceso.getIdProceso() + "/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                proceso.setRutaLogs(folder.getPath() + "/" + proceso.getIdProceso() + "_log.log");
            }

            this.currentSession().update(proceso);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.altaUpdateProceso", ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProcesoBD getProcesoIdProceso(String idProceso) {
        ProcesoBD procesoBD = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(ProcesoBD.class
            );
            dc.add(Restrictions.eq("idProceso", idProceso));
            procesoBD = (ProcesoBD) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.getProcesoIdProceso", ex);
        }
        return procesoBD;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoProceso
    getEstadoProcesoId(Integer id) {
        return (EstadoProceso) this.currentSession().get(EstadoProceso.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoProceso> getEstadosProceso() {
        List<EstadoProceso> estadosProceso = new ArrayList<EstadoProceso>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoProceso.class
            );
            estadosProceso = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosProceso", ex);
        }
        return estadosProceso;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoProceso
    getTipoProcesoId(Integer id) {
        return (TipoProceso) this.currentSession().get(TipoProceso.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoProceso> getTiposProceso() {
        List<TipoProceso> tiposProceso = new ArrayList<TipoProceso>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoProceso.class
            );
            tiposProceso = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposProceso", ex);
        }
        return tiposProceso;

    }

    private DetachedCriteria getProcesosCriteria(String idProceso, Usuario usuario, EstadoProceso estadoProceso, TipoProceso tipoProceso, TareaProgramada tareaProgramada) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProcesoBD.class
        );

        if (idProceso
                != null && !idProceso.equals(
                "")) {
            dc.add(Restrictions.like("idProceso", idProceso, MatchMode.START));
        }

        if (usuario
                != null) {
            dc.add(Restrictions.eq("usuario", usuario));
        }

        if (estadoProceso
                != null) {
            dc.add(Restrictions.eq("estadoProceso", estadoProceso));
        }

        if (tipoProceso
                != null) {
            dc.add(Restrictions.eq("tipoProceso", tipoProceso));
        }

        if (tareaProgramada != null) {
            dc.add(Restrictions.eq("tareaProgramada", tareaProgramada));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoBD> getProcesosListado(String idProceso, Usuario usuario, EstadoProceso estadoProceso, TipoProceso tipoProceso, TareaProgramada tareaProgramada, Integer primerResultado) {
        List<ProcesoBD> procesos = new ArrayList<ProcesoBD>();
        try {
            DetachedCriteria dc = this.getProcesosCriteria(idProceso, usuario, estadoProceso, tipoProceso, tareaProgramada);
            dc.addOrder(Order.desc("fechaInicial"));
            if (primerResultado == null) {
                procesos = (List<ProcesoBD>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                procesos = (List<ProcesoBD>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getProcesosListado", ex);
        }
        return procesos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getProcesosNumero(String idProceso, Usuario usuario, EstadoProceso estadoProceso, TipoProceso tipoProceso, TareaProgramada tareaProgramada) {
        Long numeroProcesos = 0l;
        try {
            DetachedCriteria dc = this.getProcesosCriteria(idProceso, usuario, estadoProceso, tipoProceso, tareaProgramada);
            dc.setProjection(Projections.rowCount());
            numeroProcesos = (Long) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getProcesosNumero", ex);
        }
        return numeroProcesos;
    }

    // --- TAREA PROGRAMADA ---//
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaUpdateTareaProgramada(TareaProgramada tareaProgramada) {
        try {
            if (tareaProgramada.getId() == null) {
                this.currentSession().save(tareaProgramada);
                tareaProgramada.asignarIdTareaProgramada();
            }

            this.currentSession().update(tareaProgramada);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.altaUpdateTareaProgramada", ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TareaProgramada getTareaProgramadaId(Integer id) {
        return (TareaProgramada) this.currentSession().get(TareaProgramada.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TareaProgramada getTareaProgramadaIdTareaProgramada(String idTareaProgramada) {
        TareaProgramada tareaProgramada = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TareaProgramada.class);
            dc.add(Restrictions.eq("idTareaProgramada", idTareaProgramada));
            tareaProgramada = (TareaProgramada) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTareaProgramadaIdTareaProgramada", ex);
        }
        return tareaProgramada;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getTareasProgramadasDetachedCriteria(String idTareaProgramada, String nombre, EstadoTareaProgramada estado, Usuario usuario, TipoProceso tipoProceso) {
        DetachedCriteria dc = DetachedCriteria.forClass(TareaProgramada.class);

        if (idTareaProgramada != null && !idTareaProgramada.equals("")) {
            dc.add(Restrictions.like("idTareaProgramada", idTareaProgramada, MatchMode.START));
        }

        if (nombre != null && !nombre.equals("")) {
            dc.add(Restrictions.like("nombre", nombre, MatchMode.START));
        }

        if (estado != null) {
            dc.add(Restrictions.eq("estado", estado));
        }

        if (usuario != null) {
            dc.add(Restrictions.eq("usuario", usuario));
        }

        if (tipoProceso != null) {
            dc.add(Restrictions.eq("tipoProceso", tipoProceso));
        }
        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TareaProgramada> getTareasProgramadas(String idTareaProgramada, String nombre, EstadoTareaProgramada estado, Usuario usuario, TipoProceso tipoProceso, Integer desde) {
        List<TareaProgramada> tareasProgramadas = new ArrayList<TareaProgramada>();

        try {
            DetachedCriteria dc = this.getTareasProgramadasDetachedCriteria(idTareaProgramada, nombre, estado, usuario, tipoProceso);
            if (desde == null) {
                tareasProgramadas = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                tareasProgramadas = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTareasProgramadas", ex);
        }
        return tareasProgramadas;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTareasProgramadasNumero(String idTareaProgramada, String nombre, EstadoTareaProgramada estado, Usuario usuario, TipoProceso tipoProceso) {
        Long numero = 0l;

        try {
            DetachedCriteria dc = this.getTareasProgramadasDetachedCriteria(idTareaProgramada, nombre, estado, usuario, tipoProceso);
            dc.setProjection(Projections.rowCount());
            numero = (Long) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTareasProgramadasNumero", ex);
        }
        return numero;

    }

    public EstadoTareaProgramada
    getEstadoTareaProgramadaId(Integer id) {
        return (EstadoTareaProgramada) this.currentSession().get(EstadoTareaProgramada.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoTareaProgramada> getEstadosTareaProgramada() {
        List<EstadoTareaProgramada> estadosTareaProgramada = new ArrayList<EstadoTareaProgramada>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoTareaProgramada.class
            );
            estadosTareaProgramada = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposProceso", ex);
        }
        return estadosTareaProgramada;

    }

    // --- BONOS ---//
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UsoBono> getUsoBonoListado(String idCiudadano, String idTarjeta,
                                           String uid, String tipo,
                                           String idUso, Ambito ambito,
                                           Centro centro, Dispositivo dispositivo,
                                           Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                           String numeroOperacion, List<Aviso> avisos,
                                           Boolean buscaAnulada, Boolean eqNumeroOperacion,
                                           Integer primerResultado) {
        List<UsoBono> usoBono = new ArrayList<>();
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetalles(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, buscaAnulada, eqNumeroOperacion, primerResultado);
            usoBono = qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getUsoBonoListado", ex);
        }
        return usoBono;
    }

    // --- BONOS ---//
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UsoBono> getUsoBonoListadoForWS(String idCiudadano, String idTarjeta,
                                                String uid, String tipo,
                                                String idUso, Ambito ambito,
                                                Centro centro, Dispositivo dispositivo,
                                                Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                                String numeroOperacion, List<Aviso> avisos,
                                                Boolean buscaAnulada, Boolean eqNumeroOperacion,
                                                Integer primerResultado, AsignacionBono asignacion) {
        List<UsoBono> usoBono = new ArrayList<>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(UsoBono.class);
            if (asignacion != null) {
                dc.add(Restrictions.eq("asignacionBono", asignacion));
            }
            if (fechaRealizacionFinal != null) {
                dc.add(Restrictions.le("fechaRealizacion", fechaRealizacionFinal));
            }
            if (fechaRealizacionInicial != null) {
                dc.add(Restrictions.ge("fechaRealizacion", fechaRealizacionInicial));
            }
            if (centro != null) {
                dc.add(Restrictions.eq("centro", centro));
            }
            if (dispositivo != null) {
                dc.add(Restrictions.eq("dispositivo", dispositivo));
            }
            if (idTarjeta != null && !idTarjeta.equals("")) {
                Tarjeta tarjeta = this.getTarjetaIdTarjeta(idTarjeta);
                dc.add(Restrictions.eq("tarjeta", tarjeta));
            }
            //dc.setProjection(Projections.distinct(Projections.property("asignacionBono.id")));

            usoBono = (ArrayList<UsoBono>) dc.getExecutableCriteria(this.currentSession()).list();
//            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetalles(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, null, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, buscaAnulada, eqNumeroOperacion, primerResultado);
//            usoBono = qC.creareQuery(currentSession()).list();

        } catch (Exception ex) {
            logger.error( "DAOiml.getUsoBonoListado", ex);
        }

        return usoBono;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Bono getBonoId(Integer id) {
        return (Bono) this.currentSession().get(Bono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Bono getBonoIdBono(String idBono) {
        Bono bono = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Bono.class);
            dc.add(Restrictions.eq("idBono", idBono));
            dc.setFetchMode("centros", FetchMode.JOIN);
            bono = (Bono) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonoIdBono", ex);
        }
        return bono;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Bono> getBonosListado(Ambito ambito, Centro centro) {
        List<Bono> bonos = new ArrayList<Bono>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Bono.class);
            if (ambito != null) {
                dc.add(Restrictions.eq("ambito", ambito));
            }

            dc.addOrder(Order.asc("orden"));
            bonos = dc
                    .getExecutableCriteria(this.currentSession())
                    .list();
            if (centro != null) {
                /**
                 * Hay pocos bonos, puedo salir del paso con lambda. Si hubiese muchísimos se puede crear una
                 * consulta pseudoSQL.
                 */
                bonos = bonos.stream()
                        .filter(x -> x.getCentros().stream()
                                .filter(y -> y.getId() == centro.getId()).collect(Collectors.toList()).size() > 0)
                        .collect(Collectors.toList());
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonosListado", ex);
        }
        bonos = bonos.stream()
                .filter(x -> x.getCentros().stream()
                        .filter(y -> y.getIdCentro().isEmpty() == false).collect(Collectors.toList()).size() > 0)
                .collect(Collectors.toList());
        return bonos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Bono> getBonosListado(String idBono, String nombre, EstadoBono estadoBono, Ambito ambito, TipoBono tipoBono, ConceptoBono conceptoBono, Integer desde, boolean soloBonosDisponibles) {
        List<Bono> bonos = new ArrayList<>();
        try {
            DetachedCriteria dc = this.getBonosCriteria(idBono, nombre, estadoBono, ambito, tipoBono, conceptoBono, soloBonosDisponibles);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_BONOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            if (desde == null) {
                bonos = dc
                        .getExecutableCriteria(this.currentSession())
                        .list();
            } else {
                bonos = dc.getExecutableCriteria(this.currentSession())
                        .setFirstResult(desde)
                        .setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonosListado", ex);
        }
        return bonos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getBonosNumero(String idBono, String nombre, EstadoBono estadoBono, Ambito ambito, TipoBono tipoBono, ConceptoBono conceptoBono, boolean soloBonosDisponibles) {
        Long numero = 0l;

        try {
            DetachedCriteria dc = this.getBonosCriteria(idBono, nombre, estadoBono, ambito, tipoBono, conceptoBono, soloBonosDisponibles);
            dc.setProjection(Projections.rowCount());
            numero = (Long) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonosNumero", ex);
        }
        return numero;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getBonosCriteria(String idBono, String nombre, EstadoBono estadoBono, Ambito ambito, TipoBono tipoBono, ConceptoBono conceptoBono, boolean soloBonosDisponibles) {
        DetachedCriteria dc = DetachedCriteria.forClass(Bono.class);

        if (!idBono.equals("")) {
            dc.add(Restrictions.like("idBono", idBono, MatchMode.START));
        }

        if (!nombre.equals("")) {
            dc.add(Restrictions.like("nombre", nombre, MatchMode.START));
        }

        if (estadoBono != null) {
            dc.add(Restrictions.eq("estadoBono", estadoBono));
        }

        if (ambito != null) {
            dc.add(Restrictions.eq("ambito", ambito));
        }

        if (tipoBono != null) {
            dc.add(Restrictions.eq("tipoBono", tipoBono));
        }

        if (conceptoBono != null) {
            dc.add(Restrictions.eq("conceptoBono", conceptoBono));
        }

        if (soloBonosDisponibles) {
            dc.add(Restrictions.or(Restrictions.ge("fechaFinal", new Date()), Restrictions.isNull("fechaFinal")));
        }
        //dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaBono(Bono bono) {
        try {
            this.currentSession().save(bono);
            bono.asignarIdBono();
            this.currentSession().update(bono);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.altaBono", ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaBono(Bono bono) {
        this.currentSession().merge(bono);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoBono getEstadoBonoId(Integer id) {
        return (EstadoBono) this.currentSession().get(EstadoBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoBono> getEstadosBono() {
        List<EstadoBono> estadosBono = new ArrayList<EstadoBono>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoBono.class);
            estadosBono = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadoBono", ex);
        }
        return estadosBono;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TipoBono getTipoBonoId(Integer id) {
        return (TipoBono) this.currentSession().get(TipoBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoBono> getTiposBono() {
        List<TipoBono> tiposBono = new ArrayList<TipoBono>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(TipoBono.class);
            tiposBono = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTiposBono", ex);
        }
        return tiposBono;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ConceptoBono getConceptoBonoId(Integer id) {
        return (ConceptoBono) this.currentSession().get(ConceptoBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConceptoBono> getConceptosBono() {
        List<ConceptoBono> conceptosBono = new ArrayList<ConceptoBono>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(ConceptoBono.class);
            conceptosBono = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getConceptoBono", ex);
        }
        return conceptosBono;
    }

    // --- ASIGNACION BONO ---//
    @Transactional(propagation = Propagation.REQUIRED)
    public AsignacionBono getAsignacionBonoId(Integer id) {
        return this.currentSession().get(AsignacionBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AsignacionBono getAsignacionBonoIdAsignacionBono(String idAsignacionBono) {

        AsignacionBono asignacionBono = null;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(AsignacionBono.class);
            dc.add(Restrictions.eq("idAsignacionBono", idAsignacionBono));
            asignacionBono = (AsignacionBono) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAsignacionBonoIdAsignacionBono", ex);
        }

        return asignacionBono;
    }

    private DetachedCriteria getAsignacionesCriteria(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal) {
        DetachedCriteria dc = DetachedCriteria.forClass(AsignacionBono.class);

        if (!idCiudadano.equals("")) {
            dc.createAlias("ciudadano", "c");
            dc.add(Restrictions.like("c.idEntidad", idCiudadano, MatchMode.START));
        }

        if (bono != null) {
            dc.add(Restrictions.eq("bono", bono));
        }

        if (estado != null) {
            dc.add(Restrictions.eq("estado", estado));
        }

        if (usuario != null) {
            dc.add(Restrictions.eq("usuarioAsignador", usuario));
        }

        if (fechaAsignacionInicial != null && fechaAsignacionFinal != null) {
            dc.add(Restrictions.between("fechaAsignacion", fechaAsignacionInicial, fechaAsignacionFinal));
        }
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return dc;
    }


    private DetachedCriteria getCriteriaUsosBono(String idCiudadano, Bono bono,
                                                 EstadoAsignacionBono estado, Usuario usuario,
                                                 Date fechaAsignacionInicial,
                                                 Date fechaAsignacionFinal,
                                                 String idBono) {
        DetachedCriteria dc = DetachedCriteria.forClass(UsoBono.class);

        if (bono != null) {
            dc.add(Restrictions.eq("asignacionBono.idAsignacionBono", idBono));
            dc.add(Restrictions.eq("id", idBono));
        }

        if (!idCiudadano.equals("")) {
            dc.createAlias("asignacionBono.ciudadano", "c");
            dc.add(Restrictions.like("c.idEntidad", idCiudadano, MatchMode.START));
        }

//        if (estado != null) {
//            dc.add(Restrictions.eq("asignacionBono.estado.id", estado.getId()));
//        }

        if (usuario != null) {
            dc.add(Restrictions.eq("asignacionBono.usuarioAsignador", usuario));
        }

        if (fechaAsignacionInicial != null && fechaAsignacionFinal != null) {
            dc.add(Restrictions.between("asignacionBono.fechaAsignacion", fechaAsignacionInicial, fechaAsignacionFinal));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AsignacionBono> getAsignacionesBonoListado(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal, Integer desde) {
        List<AsignacionBono> asignaciones = new ArrayList<>();
        try {
            DetachedCriteria dc = this.getAsignacionesCriteria(idCiudadano, bono, estado, usuario, fechaAsignacionInicial, fechaAsignacionFinal);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_BONOS_ASIGNACIONES);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (desde == null) {
                asignaciones = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                asignaciones = dc.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAsignacionesBonoListado", ex);
        }
        Date fechaInicioMes = DateUtils.getFechaInicioMes(new Date());
        Date fechaInicioDia = DateUtils.getFechaInicioDia(new Date());

        Date fechaFinMes = DateUtils.getFechaAlFinMes(new Date());
        Date fechaFinDia = DateUtils.getFechaAlFinDia(new Date());
        for (int i = 0; i < asignaciones.size(); i++) {
            if (asignaciones.get(i).getBono().getCentros() == null ||
                    asignaciones.get(i).getBono().getCentros().size() == 0) {
                asignaciones.get(i).setBono(this.getBonoIdBono(asignaciones.get(i).getBono().getIdBono()));
            }
//            /**
//             * Relleno los campos de :
//             *  1.- fechaUltimoUso
//             *  2.- numeroUsos
//             *  3.- numeroUsosDiario
//             *  4.- numeroUsosMensual
//             */
//            AsignacionBono asigna = asignaciones.get(i);
//            List<UsoBono> usosBonoPorBono = new ArrayList<UsoBono>();
//            DetachedCriteria dcBono = this.getCriteriaUsosBono(idCiudadano, bono, estado,
//                    usuario, fechaAsignacionInicial, fechaAsignacionFinal, asigna.getIdAsignacionBono());
//            try {
//                if (desde == null) {
//                    usosBonoPorBono = dcBono.getExecutableCriteria(this.currentSession()).list();
//                } else {
//                    usosBonoPorBono = dcBono.getExecutableCriteria(this.currentSession()).setFirstResult(desde).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
//                }
//                if(usosBonoPorBono.size() > 0) {
//                    asigna.setFechaUltimoUso(
//                            usosBonoPorBono.stream()
//                                    .map(u -> u.getFechaRealizacion()).max(Date::compareTo).get());
//                    asigna.setNumeroUsos(
//                            ((Long)
//                                    usosBonoPorBono.stream().count()).intValue());
//
//                    asigna.setNumeroUsosDiario(getUsosBonosNumeroSimplificado(asigna, fechaInicioMes, fechaFinMes).intValue());
//                    asigna.setNumeroUsosMensual(getUsosBonosNumeroSimplificado(asigna, fechaInicioDia, fechaFinDia).intValue());
//                }
//            }
//            catch(Exception e)
//            {
//                int debugger = 1;
//            }


        }
        return asignaciones;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteBonificacionesCiudadano(String idCiudadano) {
        BigDecimal numero = BigDecimal.ZERO;

//        Entidad entidad = getEntidadIdEntidad(idCiudadano);

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("idCiudadano", idCiudadano));
            //Me aseguro que es bonificación por compra
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
//            Calendar c = Calendar.getInstance();   // this takes current date
//            c.set(Calendar.DAY_OF_MONTH, 1);
//            dc.add(Restrictions.gt("fechaRealizacion", c.getTime()));
            List<Recarga> listado = dc.getExecutableCriteria(this.currentSession()).list();
            if (listado.size() == 0) {
                return numero;
            }
            numero = listado.stream().map(x -> x.getImporte()).reduce((x, y) -> x.add(y)).get();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteMensualBonificacionesComercio", ex);
        }
        return numero;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteMensualBonificacionesCiudadano(String idCiudadano) {
        BigDecimal numero = BigDecimal.ZERO;

//        Entidad entidad = getEntidadIdEntidad(idCiudadano);

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("idCiudadano", idCiudadano));
            //Me aseguro que es bonificación por compra
            dc.add(Restrictions.isNotNull("importeTotalTicket"));
            Calendar c = Calendar.getInstance();   // this takes current date
            c.set(Calendar.DAY_OF_MONTH, 1);
            dc.add(Restrictions.gt("fechaRealizacion", c.getTime()));
            List<Recarga> listado = dc.getExecutableCriteria(this.currentSession()).list();
            if (listado.size() == 0) {
                return numero;
            }
            numero = listado.stream().map(x -> x.getImporte()).reduce((x, y) -> x.add(y)).get();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteMensualBonificacionesComercio", ex);
        }
        return numero;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteMensualBonificacionesComercio(String idComercio) {
        BigDecimal numero = BigDecimal.ZERO;

        CentroComercio comercio = getComercioIdComercio(idComercio);

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(tokens_pagos.class);
            dc.add(Restrictions.eq("idComercio", comercio.getId()));
            dc.add(Restrictions.eq("usado", true));
            Calendar c = Calendar.getInstance();   // this takes current date
            c.set(Calendar.DAY_OF_MONTH, 1);
            dc.add(Restrictions.gt("fecha", c.getTime()));
            List<tokens_pagos> listado = dc.getExecutableCriteria(this.currentSession()).list();
            if (listado.size() == 0) {
                return numero;
            }
            numero = listado.stream().map(x -> x.getImporte()).reduce((x, y) -> x.add(y)).get();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteMensualBonificacionesComercio", ex);
        }
        return numero;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteBonificacionesComercio(String idComercio) {
        BigDecimal numero = BigDecimal.ZERO;

        CentroComercio comercio = getComercioIdComercio(idComercio);

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(tokens_pagos.class);
            dc.add(Restrictions.eq("idComercio", comercio.getId()));
            dc.add(Restrictions.eq("usado", true));
            List<tokens_pagos> listado = dc.getExecutableCriteria(this.currentSession()).list();
            if (listado.size() == 0) {
                return numero;
            }
            numero = listado.stream().map(x -> x.getImporte()).reduce((x, y) -> x.add(y)).get();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteBonificacionesComercio", ex);
        }
        return numero;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getAsignacionesBonosImporte(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal) {
        BigDecimal numero = BigDecimal.ZERO;

        try {
            DetachedCriteria dc = this.getAsignacionesCriteria(idCiudadano, bono, estado, usuario, fechaAsignacionInicial, fechaAsignacionFinal);

            List<AsignacionBono> asignaciones = dc.getExecutableCriteria(this.currentSession()).list();

            if (asignaciones != null) {
                return asignaciones.stream()
                        .peek(x -> {
                            if (x.getPrecioAsignacion() == null) {
                                x.setPrecioAsignacion(new BigDecimal(0));
                            }
                        }).map(x -> x.getPrecioAsignacion()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
            }


        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAsignacionesBonosNumero", ex);
        }
        return numero;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Long getAsignacionesBonosNumero(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal) {
        Long numero = 0l;

        try {
            DetachedCriteria dc = this.getAsignacionesCriteria(idCiudadano, bono, estado, usuario, fechaAsignacionInicial, fechaAsignacionFinal);
            dc.setProjection(Projections.rowCount());
            numero = (Long) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAsignacionesBonosNumero", ex);
        }
        return numero;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaBonoAsignacion(AsignacionBono asignacionBono) {
        this.currentSession().save(asignacionBono);
        asignacionBono.asignarIdAsignacionBono();
        modificaBonoAsignacion(asignacionBono);


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaBonoAsignacion(AsignacionBono asignacionBono) {
        this.currentSession().update(asignacionBono);
    }

    // --- ESTADOS ASIGNACION BONO ---//
    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoAsignacionBono getEstadoAsignacionBonoId(Integer id) {
        return (EstadoAsignacionBono) this.currentSession().get(EstadoAsignacionBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoAsignacionBono> getEstadosAsignacionBono() {
        List<EstadoAsignacionBono> estadosAsignacionBono = new ArrayList<EstadoAsignacionBono>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoAsignacionBono.class);
            dc.addOrder(Order.asc("nombre"));
            estadosAsignacionBono = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosAsignacionBono", ex);
        }
        return estadosAsignacionBono;
    }

    //Generico
    @Transactional(propagation = Propagation.REQUIRED)
    public Object getObjectId(Class clase, Integer id) {
        return this.currentSession().get(clase, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getUsosBonoNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion) {
        Long numeroUsosBonos = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetallesTotales(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, dispositivo, fechaRealizacionInicial, fechaRealizacionFinal, numeroOperacion, null, bono, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroUsosBonos = (Long) qC.creareQuery(currentSession()).list().get(0);
        } catch (Exception ex) {
            logger.error( "DAOiml.getUsosBonoNumero", ex);
        }
        return numeroUsosBonos;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<UsoBono> getUsosBonoFromEntidad(String idCiudadano) {
        List<UsoBono> res = new ArrayList<UsoBono>();
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesDetalles(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, null, null);
            res = (List<UsoBono>) qC.creareQuery(currentSession()).list();
        } catch (Exception ex) {
            logger.error( "DAOiml.getUsosBonoNumero", ex);
        }
        return res;
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public Long getUsosBonoAmbitoNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion) {
        Long numeroUsosBonos = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesAmbitoTotales(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, null, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, bono, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroUsosBonos = Long.valueOf(qC.creareQuery(currentSession()).list().size());
        } catch (Exception ex) {
            logger.error( "DAOiml.getUsosBonoAmbitoNumero", ex);
        }
        return numeroUsosBonos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getUsosBonoCentroNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion) {
        Long numeroUsosBono = 0l;
        try {
            QueryOperaciones qC = new QueryOperaciones(new QueryOperacionesCentrosTotales(), QueryOperaciones.OPERACION_USO_BONO, idCiudadano, idTarjeta, uid, tipo, idUso, ambito, centro, null, fechaRealizacionInicial, fechaRealizacionFinal, null, null, bono, null, avisos, null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, eqNumeroOperacion, null);
            numeroUsosBono = Long.valueOf(qC.creareQuery(currentSession()).list().size());
        } catch (Exception ex) {
            logger.error( "DAOiml.getUsosBonoCentroNumero", ex);
        }
        return numeroUsosBono;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaUsoBonoAsignacion(UsoBono usoBono) {
        try {
            this.currentSession().save(usoBono);
            usoBono.asignarIdUso();
            modificaUsoBonoAsignacion(usoBono);
        } catch (Exception ex) {
            logger.error( "DAOimpl.altaUsoBonoAsignacion", ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaUsoBonoAsignacion(UsoBono usoBono) {
        this.currentSession().update(usoBono);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean existeUsoBonoAnuladoId(Integer idUsoBono) {
        try {

            DetachedCriteria dc = DetachedCriteria.forClass(UsoBono.class);
            dc.add(Restrictions.eq("idOperacionAnulada", idUsoBono));

            if (dc.getExecutableCriteria(this.currentSession()).list().size() > 0) {
                return Boolean.TRUE;
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.existeUsoBonoAnulado", ex);
            return null;
        }
        return Boolean.FALSE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UsoBono getUsoBonoId(Integer id) {
        return (UsoBono) this.currentSession().get(UsoBono.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getUsosBonosNumeroSimplificado(AsignacionBono asignacionBono, Date fechaIni, Date fechaFin) {

        Long numeroUsosBonos = 0l;

        try {

            Query query = currentSession().createQuery(
                    "SELECT sum(u.cantidad) "
                            + "FROM UsoBono u "
                            + "WHERE "
                            + "u.asignacionBono = :asignacionBono "
                            + "AND u.fechaRealizacion between :fechaIni and :fechaFin"
            );

            query.setParameter("asignacionBono", asignacionBono);
            query.setParameter("fechaIni", fechaIni);
            query.setParameter("fechaFin", fechaFin);

            numeroUsosBonos = (Long) query.list().get(0);

            if (numeroUsosBonos == null) {
                numeroUsosBonos = 0l;
            }

        } catch (Exception ex) {
            logger.error( "DAOiml.getUsosBonosNumeroSimplificado", ex);
        }
        return numeroUsosBonos;
    }

    // ****************
    //*** DESCUENTOS ***
    // ****************
    @Transactional(propagation = Propagation.REQUIRED)
    public EstadoDescuento getEstadoDescuentoId(Integer id) {
        return (EstadoDescuento) this.currentSession().get(EstadoDescuento.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<EstadoDescuento> getEstadosDescuento() {
        List<EstadoDescuento> estadoDescuentos = new ArrayList<EstadoDescuento>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EstadoDescuento.class);
            estadoDescuentos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEstadosDescuento", ex);
        }
        return estadoDescuentos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Descuento getDescuentoId(Integer id) {
        try {
            return (Descuento) this.currentSession().get(Descuento.class, id);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDescuentoId", ex);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Descuento getDescuentoIdDescuento(String idDescuento) {
        Descuento descuento = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Descuento.class);
            dc.add(Restrictions.eq("idDescuento", idDescuento));
            descuento = (Descuento) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDescuentoIdDescuento", ex);
            return null;
        }
        return descuento;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void altaDescuento(Descuento descuento) {
        this.currentSession().save(descuento);
        descuento.asignarIdDescuento();
        modificaDescuento(descuento);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaDescuento(Descuento descuento) {
        this.currentSession().update(descuento);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DetachedCriteria getDescuentosCriteria(String idDescuento, String nombre, EstadoDescuento estadoDescuento, Ambito ambito) {

        DetachedCriteria dc = DetachedCriteria.forClass(Descuento.class);

        if (idDescuento != null && !idDescuento.equals("")) {
            dc.add(Restrictions.like("idDescuento", idDescuento, MatchMode.ANYWHERE));
        }

        if (nombre != null && !nombre.equals("")) {
            dc.add(Restrictions.like("nombre", nombre, MatchMode.ANYWHERE));
        }

        if (estadoDescuento != null) {
            dc.add(Restrictions.eq("estadoDescuento", estadoDescuento));
        }

        if (ambito != null) {
            dc.add(Restrictions.eq("ambito", ambito));
        }

        return dc;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getDescuentosNumero(String idDescuento, String nombre, EstadoDescuento estadoDescuento, Ambito ambito) {
        Long numero = 0l;

        try {
            DetachedCriteria dc = this.getDescuentosCriteria(idDescuento, nombre, estadoDescuento, ambito);
            dc.setProjection(Projections.rowCount());
            numero = (Long) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getDescuentosNumero", ex);
        }
        return numero;
    }

    // -- ADJUNTOS -- //
    @Transactional
    public void altaAdjuntos(Adjuntos adjuntos) {
        this.currentSession().save(adjuntos);
    }

    @Transactional
    public void modificaAdjunto(Adjuntos adjuntos) {
        this.currentSession().update(adjuntos);
    }

    @Transactional
    public void eliminarAdjunto(Adjuntos adjuntos) {
        this.currentSession().delete(adjuntos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Adjuntos getAdjunto(int id) {
        Adjuntos adjuntos;
        Criteria criteria = this.currentSession().createCriteria(Adjuntos.class);
        criteria.add(Restrictions.eq("id", id));
        adjuntos = (Adjuntos) criteria.uniqueResult();
        return adjuntos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Adjuntos> getAdjuntos(String idCiudadano, int id) {
        List<Adjuntos> adjuntos = new ArrayList<Adjuntos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Adjuntos.class);
            if (idCiudadano != null && !idCiudadano.equalsIgnoreCase("")) {
                dc.add(Restrictions.eq("idEntidad", idCiudadano));
            }
            if (id != 0) {
                dc.add(Restrictions.eq("id", id));
            }
            adjuntos = dc.getExecutableCriteria(this.currentSession()).addOrder(Order.desc("id")).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAdjuntosByIdEntidad", ex);
        }
        return adjuntos;
    }


    /**
     * FIN Nuevos listados --EN DESARROLLO-
     */
    // -- PRODUCTOS -- //
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Producto getProductoId(Integer id) {
        try {
            return (Producto) this.currentSession().get(Producto.class, id);
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getProductoId", ex);
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Producto getProductoIdProducto(String idProducto) {
        Producto producto = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Producto.class);
            dc.add(Restrictions.eq("idProducto", idProducto));
            producto = (Producto) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getProductoIdProducto", ex);
            return null;
        }
        return producto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaProducto(Producto producto) {
        this.currentSession().save(producto);

        /*Se guarda en bbdd y genera un id -> con el id se crea un idAmito (Axxx)
         -> Se guarda de nuevo el ambito, esta vez con el id con el id. */
        producto.asignarIdProducto();
        modificaProducto(producto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaProducto(Producto producto) {
        this.currentSession().merge(producto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Producto> getProductos(Centro centro) {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Producto.class);
            if (centro != null) {
                dc.add(Restrictions.eq("centro", centro));
            }
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.addOrder(Order.asc("nombre"));
            productos = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getProductos", ex);
        }
        return productos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getProductosNumero(String idProducto) {
        Long numeroProductos = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Producto.class);
            if (!idProducto.equals("")) {
                dc.add(Restrictions.ilike("idProducto", idProducto, MatchMode.START));
            }
            //dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            dc.setProjection(Projections.rowCount());
            numeroProductos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getProductosNumero", ex);
        }
        return numeroProductos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Producto> getProductosListado(String idProducto, Integer primerResultado) {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Producto.class);
            if (!idProducto.equals("")) {
                dc.add(Restrictions.ilike("idProducto", idProducto, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECUROS_PRODUCTOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                productos = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                productos = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getProductosListado", ex);
        }
        return productos;
    }

    //    CIUDADANOS PREALTA APP
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaCiudadanoPrealta(EntidadPrealta ciudadano) {
        this.currentSession().save(ciudadano);
        ciudadano.asignarCodigo();
        this.currentSession().update(ciudadano);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EntidadPrealta getCiudadanoPrealtaDocumento(TipoDocumento tipoDocumento, String documento) {
        EntidadPrealta ciudadano = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(EntidadPrealta.class);
            dc.add(Restrictions.eq("tipoDocumento", tipoDocumento.getId()));
            dc.add(Restrictions.eq("documento", documento));
            ciudadano = (EntidadPrealta) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (Exception ex) {
            logger.error( "DAOiml.getCiudadanoPrealtaDocumento", ex);
        }
        return ciudadano;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modificaEntidadPrealta(EntidadPrealta entidad) {
        try {
            this.currentSession().update(entidad);
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.modificaEntidadPrealta", ex);
            throw ex;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateEntidad(Entidad entidad) {
        try {
            this.currentSession().update(entidad);
        } catch (DataAccessException ex) {
            logger.error( "DAOompl.updateEntidad", ex);
            throw ex;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void altaBonificacionTarjeta(BonificacionComercios bonificacionComercios) {
        this.currentSession().saveOrUpdate(bonificacionComercios);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BonificacionComercios> getBonificacionComercioByTarjetaAndComercio(Tarjeta tarjeta, CentroComercio comercio, Boolean finalizada) {
        DetachedCriteria dc = null;
        try {
            dc = DetachedCriteria.forClass(BonificacionComercios.class);
            if (tarjeta != null) {
                dc.add(Restrictions.eq("tarjeta", tarjeta));
            }
            if (comercio != null) {
                dc.add(Restrictions.eq("comercio", comercio));
            }
            if (finalizada != null) {
                dc.add(Restrictions.eq("finalizada", finalizada));
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonificacionComercioByTarjetaAndComercio", ex);
        }
        return dc.getExecutableCriteria(this.currentSession()).list();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Compra> getComprasComercioSinBonificar(Centro centro, Tarjeta tarjeta, Integer plazoTiempo, Date fechaInicio) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Compra.class);
        c.add(Restrictions.or(Restrictions.isNull("comercioBonificada"), Restrictions.eq("comercioBonificada", false)));
        c.add(Restrictions.ge("fechaRealizacion", fechaInicio));
        c.add(Restrictions.ge("fechaRealizacion", DateUtils.addDays(new Date(), -plazoTiempo)));
        c.add(Restrictions.eq("centro", centro));
        c.add(Restrictions.eq("tarjeta", tarjeta));
        return c.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BonificacionComercios> getBonificacionesListado(Entidad entidad, Tarjeta tarjeta, CentroComercio comercio, Date fechaInicio, Boolean finalizada, Integer primerResultado) {
        List<BonificacionComercios> bonificaciones = new ArrayList<>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(BonificacionComercios.class);
            if (entidad != null) {
                dc.add(Restrictions.eq("entidad", entidad));
            }
            if (tarjeta != null) {
                dc.add(Restrictions.eq("tarjeta", tarjeta));
            }
            if (comercio != null) {
                dc.add(Restrictions.eq("comercio", comercio));
            }
            if (fechaInicio != null) {
                dc.add(Restrictions.eq("fechaInicio", fechaInicio));
            }
            if (finalizada != null) {
                dc.add(Restrictions.eq("finalizada", finalizada));
            }
            dc.addOrder(Order.desc("id"));
            if (primerResultado != null) {
                bonificaciones = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            } else {
                bonificaciones = dc.getExecutableCriteria(this.currentSession()).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonificacionesListado", ex);
        }
        return bonificaciones;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getBonificacionesNumero(Entidad entidad, Tarjeta tarjeta, CentroComercio comercio, Date fechaInicio) {
        Long numeroBonificaciones = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(BonificacionComercios.class);
            if (entidad != null) {
                dc.add(Restrictions.eq("entidad", entidad));
            }
            if (tarjeta != null) {
                dc.add(Restrictions.eq("tarjeta", tarjeta));
            }
            if (comercio != null) {
                dc.add(Restrictions.eq("comercio", comercio));
            }
            if (fechaInicio != null) {
                dc.add(Restrictions.eq("fechaInicio", fechaInicio));
            }
            dc.setProjection(Projections.rowCount());
            numeroBonificaciones = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getBonificacionesNumero", ex);
        }
        return numeroBonificaciones;
    }


    /**
     * TRAMOS COMPRA
     */


    /**
     * getTramosCompraByAmbitoId devuelve tramos de puntos en base al ámbito
     *
     * @param ambitoId
     * @return ArrayList de tramosCOmpra
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<tramosCompra> getTramosCompraByAmbitoId(int ambitoId) {
        List<tramosCompra> resultado = new ArrayList<tramosCompra>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(tramosCompra.class);
            dc.add(Restrictions.eq("idAmbito", ambitoId));
//            dc.setProjection(Projections.rowCount());
            resultado = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOimpl.getTramosCompraByAmbitoId", ex);
        }
        return resultado;
    }

    /**
     * ELIMINO TODOS LOS TRAMOSCOMPRAS DE UN ÁMBITO
     *
     * @param ambitoId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTramosCompraByAmbitoId(int ambitoId) {
        Transaction tx = this.currentSession().beginTransaction();
        try {
            Query q = this.currentSession().createQuery("delete from tramosCompra where idAmbito=:idAmbito");
            q.setParameter("idAmbito", ambitoId);
            q.executeUpdate();
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteTramosCompraByAmbitoId. AmbitoId: " + ambitoId, e);
            e.printStackTrace();
        } finally {
            /**
             * Alberto: No está cerrando la sesión en ningún sitio, no voy a inventar
             */
            //this.currentSession().close();
        }
    }


    /**
     * Inserta un tramoCompra
     *
     * @param tramo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertaTramosCompra(tramosCompra tramo) {
        try {
            this.currentSession().saveOrUpdate(tramo);
        } catch (Exception e) {
            logger.error( "DAOimpl.insertaTramosCompra", e);
            e.printStackTrace();
        }
    }

    /**
     * Elimina un traamoCompra
     *
     * @param tramo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminarTramo(tramosCompra tramo) {
        try {
            this.currentSession().delete(tramo);
        } catch (Exception e) {
            logger.error( "DAOimpl.eliminarTramo", e);
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un tramoCompra
     *
     * @param tramo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarTramo(tramosCompra tramo) {
        try {
            this.currentSession().saveOrUpdate(tramo);
        } catch (Exception e) {
            logger.error( "DAOimpl.actualizarTramo", e);
            e.printStackTrace();
        }
    }


/**
 * QR
 */

    /**
     * Inserta un qr_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertaQrPeticiones(qr_peticiones peticion) {
        try {
            this.currentSession().saveOrUpdate(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.insertaQrPeticiones", e);
            e.printStackTrace();
        }
    }

    /**
     * Elimina un qr_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQrPeticiones(qr_peticiones peticion) {
        try {
            this.currentSession().delete(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteQrPeticiones", e);
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un qr_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarQrPeticiones(qr_peticiones peticion) {
        try {
            this.currentSession().saveOrUpdate(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.actualizarQrPeticiones", e);
            e.printStackTrace();
        }
    }


    /**
     * Inserta un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertaQrRespuestas(qr_respuestas res) {
        try {
            this.currentSession().saveOrUpdate(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.insertaQrRespuestas", e);
            e.printStackTrace();
        }
    }

    /**
     * Elimina un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQrRespuestas(qr_respuestas res) {
        try {
            this.currentSession().delete(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteQrRespuestas", e);
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarQrRespuestas(qr_respuestas res) {
        try {
            this.currentSession().saveOrUpdate(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.actualizarQrRespuestas", e);
            e.printStackTrace();
        }
    }


/**
 * QR BONO
 */

    /**
     * Inserta un qrbono_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertaQrBonoPeticiones(qrbono_peticiones peticion) {
        try {
            this.currentSession().saveOrUpdate(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.insertaQrPeticiones", e);
            e.printStackTrace();
        }
    }

    /**
     * Elimina un qr_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQrBonoPeticiones(qrbono_peticiones peticion) {
        try {
            this.currentSession().delete(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteQrPeticiones", e);
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un qr_peticiones
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarQrBonoPeticiones(qrbono_peticiones peticion) {
        try {
            this.currentSession().saveOrUpdate(peticion);
        } catch (Exception e) {
            logger.error( "DAOimpl.actualizarQrPeticiones", e);
            e.printStackTrace();
        }
    }

    /**
     * Inserta un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertaQrBonoRespuestas(qrbono_respuestas res) {
        try {
            this.currentSession().saveOrUpdate(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.insertaQrRespuestas", e);
            e.printStackTrace();
        }
    }

    /**
     * Elimina un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQrBonoRespuestas(qrbono_respuestas res) {
        try {
            this.currentSession().delete(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteQrRespuestas", e);
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un qr_respuestas
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizarQrBonoRespuestas(qrbono_respuestas res) {
        try {
            this.currentSession().saveOrUpdate(res);
        } catch (Exception e) {
            logger.error( "DAOimpl.actualizarQrRespuestas", e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public String fnGetQrBono(String idAsignacion) {
        String qr = null;
        try {
            qr = (String) this.currentSession()
                    .createSQLQuery(
                            "SELECT fnGetQrBono(:idAsignacion)"
                    )
                    .setParameter("idAsignacion", idAsignacion)
                    .uniqueResult();
        } catch (Exception e) {
            logger.error( "DAOimpl.fnGetQrBono", e);
            e.printStackTrace();
        }
        return qr;
    }


    /**
     * PUNTOS
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResumenPuntos getResumenPuntosAmbito(Integer idAmbito) {
        ResumenPuntos respuesta = new ResumenPuntos();
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(distinct hp.entidad.id) as x FROM historialespuntos as hp where hp.centro.ambito.id=:idAmbito")
                    .setParameter("idAmbito", idAmbito).uniqueResult();
            BigDecimal cantidadPuntoss = (BigDecimal) currentSession().createQuery("SELECT sum(cantidadpuntos) as x FROM historialespuntos as hp where hp.centro.ambito.id=:idAmbito")
                    .setParameter("idAmbito", idAmbito)
                    .uniqueResult();
            respuesta.setCantidadCiudadanos(cantidadCiudadanos);
            respuesta.setCantidadPuntos(cantidadPuntoss);
        } catch (Exception e) {
            logger.error( "DAOimpl.getResumenPuntosAmbito", e);
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResumenPuntos getResumenPuntosCentro(Integer idCentro) {
        ResumenPuntos respuesta = new ResumenPuntos();
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(distinct hp.entidad.id) as x FROM historialespuntos as hp where hp.centro.id=:idcentro")
                    .setParameter("idcentro", idCentro).uniqueResult();
            BigDecimal cantidadPuntoss = (BigDecimal) currentSession().createQuery("SELECT sum(cantidadpuntos) as x FROM historialespuntos as hp where hp.centro.id=:idcentro")
                    .setParameter("idcentro", idCentro)
                    .uniqueResult();
            respuesta.setCantidadCiudadanos(cantidadCiudadanos);
            respuesta.setCantidadPuntos(cantidadPuntoss);
        } catch (Exception e) {
            logger.error( "DAOimpl.getResumenPuntosCentro", e);
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResumenPuntos getResumenPuntosCiudadano(Integer idCiudadano) {
        ResumenPuntos respuesta = new ResumenPuntos();
        try {
            int cantidadCiudadanos = currentSession().createQuery(
                    "SELECT count(distinct hp.entidad.id) as x FROM historialespuntos as hp where hp.entidad.id=:identidad")
                    .setParameter("identidad", idCiudadano).getFirstResult();
            BigDecimal cantidadPuntoss = (BigDecimal) currentSession().createQuery("SELECT sum(cantidadpuntos) as x FROM historialespuntos as hp where hp.entidad.id=:identidad")
                    .setParameter("identidad", idCiudadano)
                    .uniqueResult();
            respuesta.setCantidadCiudadanos((long) cantidadCiudadanos);
            respuesta.setCantidadPuntos(cantidadPuntoss);
        } catch (Exception e) {
            logger.error( "DAOimpl.getResumenPuntosCiudadano", e);
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResumenPuntos getResumenPuntosCiudadano_Comercios(Integer idCiudadano) {
        ResumenPuntos respuesta = new ResumenPuntos();
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(distinct hp.entidad.id) as x FROM historialespuntos as hp where hp.entidad.id=:identidad and hp.centro.class = :idcentro")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_COMERCIO)
                    .uniqueResult();
            BigDecimal cantidadPuntoss = (BigDecimal) currentSession().createQuery("SELECT sum(cantidadpuntos) as x FROM historialespuntos as hp where hp.entidad.id=:identidad and hp.centro.class = :idcentro")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_COMERCIO)
                    .uniqueResult();

            respuesta.setCantidadCiudadanos(cantidadCiudadanos);
            respuesta.setCantidadPuntos(cantidadPuntoss);
        } catch (Exception e) {
            logger.error( "DAOimpl.getResumenPuntosCiudadano_Comercios", e);
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResumenPuntos getResumenPuntosCiudadano_Centros(Integer idCiudadano) {
        ResumenPuntos respuesta = new ResumenPuntos();
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(distinct hp.entidad.id) as x FROM historialespuntos as hp where hp.entidad.id=:identidad and hp.centro.class = :idcentro")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_AYUNTAMIENTO)
                    .uniqueResult();
            BigDecimal cantidadPuntoss = (BigDecimal) currentSession().createQuery("SELECT sum(cantidadpuntos) as x FROM historialespuntos as hp where hp.entidad.id=:identidad and hp.centro.class = :idcentro")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_AYUNTAMIENTO)
                    .uniqueResult();


            respuesta.setCantidadCiudadanos(cantidadCiudadanos);
            respuesta.setCantidadPuntos(cantidadPuntoss);
        } catch (Exception e) {
            logger.error( "DAOimpl.getResumenPuntosCiudadano_Centros", e);
            e.printStackTrace();
        }
        return respuesta;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List getListadoPuntosByAmbito(Integer idAmbito, Integer primerResultado) {
        ArrayList<historialespuntos> respuesta = new ArrayList<historialespuntos>();
        try {
            respuesta =
                    (ArrayList<historialespuntos>)
                            currentSession().createQuery(
                                    "SELECT * FROM historialespuntos as hp inner join centros as c on hp.idcentro=c.id and c.idAmbito=:idAmbito LIMIT :primer, :limite")
                                    .setParameter("primer", primerResultado)
                                    .setParameter("limite", Constantes.NUMERO_ELEMENTOS_PAGINA)
                                    .setParameter("idAmbito", idAmbito).list();
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosByAmbito", e);
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List getListadoPuntosByCentro(Integer idCentro, Integer primerResultado) {
        List<historialespuntos> res = new ArrayList<historialespuntos>();

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Ambito.class);
            if (idCentro != null) {
                dc.add(Restrictions.ilike("idCentro", idCentro.toString(), MatchMode.START));
            }
            if (primerResultado == null) {
                res = dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                res = dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosByCentro", e);
            e.printStackTrace();
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<historialespuntos> getListadoPuntosCiudadanoComercios(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado) {
        List<historialespuntos> res = new ArrayList<historialespuntos>();
        try {
            res = (ArrayList<historialespuntos>) currentSession().createQuery(
                    "SELECT hp as x FROM historialespuntos as hp where hp.entidad.id=:identidad " +
                            "and hp.centro.class = :idcentro " +
                            "and hp.fecha > :fechaIni " +
                            "and hp.fecha < :fechaFin ")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_COMERCIO)

                    .setParameter("fechaIni", fechaInicio)
                    .setParameter("fechaFin", fechaFin)

                    .setFirstResult(Constantes.NUMERO_ELEMENTOS_PAGINA * (primerResultado - 1))
                    .setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA)

                    .list();
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosCiudadanoComercios", e);
            e.printStackTrace();
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<historialespuntos> getListadoPuntosCiudadanoCentros(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado) {
        List<historialespuntos> res = new ArrayList<historialespuntos>();
        try {
            res = (ArrayList<historialespuntos>) currentSession().createQuery(
                    "SELECT hp as x FROM historialespuntos as hp where hp.entidad.id=:identidad " +
                            "and hp.centro.class = :idcentro " +
                            "and hp.fecha > :fechaIni " +
                            "and hp.fecha < :fechaFin ")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_AYUNTAMIENTO)

                    .setParameter("fechaIni", fechaInicio)
                    .setParameter("fechaFin", fechaFin)

                    .setFirstResult(Constantes.NUMERO_ELEMENTOS_PAGINA * (primerResultado - 1))
                    .setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA)

                    .list();
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosCiudadanoCentros", e);
            e.printStackTrace();
        }
        return res;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTotalPuntosCiudadanoComercios(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado) {
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(*) as x FROM historialespuntos as hp where hp.entidad.id=:identidad " +
                            "and hp.centro.class = :idcentro " +
                            "and hp.fecha > :fechaIni " +
                            "and hp.fecha < :fechaFin ")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_COMERCIO)

                    .setParameter("fechaIni", fechaInicio)
                    .setParameter("fechaFin", fechaFin)

                    .uniqueResult();
            return cantidadCiudadanos;
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosCiudadanoCentros", e);
            e.printStackTrace();
            return new Long(0);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTotalPuntosCiudadanoCentros(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado) {
        try {
            Long cantidadCiudadanos = (Long) currentSession().createQuery(
                    "SELECT count(*) as x FROM historialespuntos as hp where hp.entidad.id=:identidad " +
                            "and hp.centro.class = :idcentro " +
                            "and hp.fecha > :fechaIni " +
                            "and hp.fecha < :fechaFin ")
                    .setParameter("identidad", idCiudadano)
                    .setParameter("idcentro", Centro.CENTROS_AYUNTAMIENTO)

                    .setParameter("fechaIni", fechaInicio)
                    .setParameter("fechaFin", fechaFin)

                    .uniqueResult();
            return cantidadCiudadanos;
        } catch (Exception e) {
            logger.error( "DAOimpl.getListadoPuntosCiudadanoCentros", e);
            e.printStackTrace();
            return new Long(0);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public padrondocumentos getPadronDocumento(String documento) {
        padrondocumentos res = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(padrondocumentos.class);
            if (documento != null && !documento.isEmpty()) {
                dc.add(Restrictions.eq("documento", documento));
            } else {
                throw new DataRetrievalFailureException("Dato vacío comprobando el padrón. Se devuelve objeto null.");
                //Tras esto debería ir a la excepcion (es hija) y devolver null;
            }
            res = (padrondocumentos) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getPadronDocumento", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean documentoExistsOnPadronDocumentos(String documento) {
        try {
            Boolean lanzarConsulta =
                    new Boolean(this.getAppConfigId(AppConfig.VALIDAR_PADRON_CON_TABLAS).getValor());
            // true; //Linea para debug
            if (lanzarConsulta == true) {
                Long cantidadCiudadanos = (Long) currentSession().createQuery(
                        "SELECT count(*) as x FROM padrondocumentos as pd where pd.documento = :documento")
                        .setParameter("documento", documento)
                        .uniqueResult();
                return cantidadCiudadanos > 0;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error( "DAOimpl.documentoExistsOnPadronDocumentos", e);
            e.printStackTrace();
            return false;
        }
    }


    @Transactional
    public Boolean spAsignaBonoConvert(String idAsignacionBono) {
        try {
            Query query = currentSession().createSQLQuery("CALL spAsignaBonoConvert(:idAsignacionBono)")
                    .setParameter("idAsignacionBono", idAsignacionBono);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public Boolean altaCmsCategoria(cmscategorias centroCategoria) {
        try {
            this.currentSession().save(centroCategoria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaCmsCategoria(cmscategorias centroCategoria) {
        try {
            this.currentSession().update(centroCategoria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public cmscategorias getCmsCategoria(Integer cmsId) {
        cmscategorias centroCategoria = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            if (cmsId != null) {
                dc.add(Restrictions.eq("id", cmsId));
            }
            centroCategoria = (cmscategorias) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.altaCmsCategoria", ex);
        }
        return centroCategoria;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> getCmsEventosCategorias() {
        List<cmscategorias> centrosCategorias = new ArrayList<cmscategorias>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 2));//2 son eventos
            dc.addOrder(Order.asc("nombre"));
            centrosCategorias = (List<cmscategorias>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosCategoriaListado", ex);
        }
        return centrosCategorias;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> getCmsComerciosCategorias() {
        List<cmscategorias> centrosCategorias = new ArrayList<cmscategorias>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 1));//1 son comercios
            dc.addOrder(Order.asc("nombre"));
            centrosCategorias = (List<cmscategorias>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsComercios", ex);
        }
        return centrosCategorias;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> getCmsCentrosCategorias() {
        List<cmscategorias> centrosCategorias = new ArrayList<cmscategorias>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 0));//0 son centros
            dc.addOrder(Order.asc("nombre"));
            centrosCategorias = (List<cmscategorias>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsCentros", ex);
        }
        return centrosCategorias;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> getCmsCategoriaListado(String nombre, Integer primerResultado, Integer tipo) {
        List<cmscategorias> centrosCategorias = new ArrayList<cmscategorias>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("tipoCentro", tipo));
            }
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            switch (tipo) {
                case 0:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CATEGORIAS_CENTROS);
                    break;
                case 1:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CATEGORIAS_COMERCIOS);
                    break;
                case 2:
                    dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CATEGORIAS_EVENTOS);
                    break;
            }

            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                centrosCategorias = (List<cmscategorias>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                centrosCategorias = (List<cmscategorias>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosCategoriaListado", ex);
        }
        return centrosCategorias;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCmsCategoriasNumero(String nombre, Integer tipo) {
        Long numeroCentros = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            if (tipo != null) {
                dc.add(Restrictions.eq("tipoCentro", tipo));
            }
            if (!nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroCentros = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCategoriasCentrosNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numeroCentros;
    }


    /**
     * EVENTOS
     */
    @Transactional
    public Boolean altaCmsEvento(cmseventos evento) throws ParseException {
        try {
            this.currentSession().save(evento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaCmsEvento(cmseventos evento) throws ParseException {
        try {
            this.currentSession().update(evento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public cmseventos getCmsEvento(Integer cmsId) {
        cmseventos evento = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmseventos.class);
            if (cmsId != null) {
                dc.add(Restrictions.eq("id", cmsId));
            }
            evento = (cmseventos) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsEvento", ex);
        }
        return evento;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmseventos> getCmsEventosListado(String nombre, Integer primerResultado) {
        List<cmseventos> eventos = new ArrayList<cmseventos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmseventos.class);
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_EVENTOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                eventos = (List<cmseventos>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                eventos = (List<cmseventos>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsEventosListado", ex);
        }
        return eventos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCmsEventosNumero(String nombre) {
        Long numeroEventos = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmseventos.class);
            if (!nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroEventos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsEventosNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numeroEventos;
    }


    /**
     * CARRUSEL
     */
    @Transactional
    public Boolean altaCmsCarrusel(cmscarrusel carrusel) throws ParseException {
        try {
            this.currentSession().save(carrusel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaCmsCarrusel(cmscarrusel carrusel) throws ParseException {
        try {
            this.currentSession().update(carrusel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public cmscarrusel getCmsCarrusel(Integer cmsId) {
        cmscarrusel carr = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscarrusel.class);
            if (cmsId != null) {
                dc.add(Restrictions.eq("id", cmsId));
            }
            carr = (cmscarrusel) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsCarrusel", ex);
        }
        return carr;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscarrusel> getCmsCarruselListado(String titulo, Integer primerResultado) {
        List<cmscarrusel> carr = new ArrayList<cmscarrusel>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscarrusel.class);
            if (titulo != null && !titulo.equals("")) {
                dc.add(Restrictions.ilike("titulo", titulo, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CARRUSEL);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                carr = (List<cmscarrusel>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                carr = (List<cmscarrusel>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsCarruselListado", ex);
        }
        return carr;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscarrusel> getCmsCarruselListadoMostrar() {
        List<cmscarrusel> carr = new ArrayList<cmscarrusel>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscarrusel.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            carr = (List<cmscarrusel>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsCarruselListadoMostrar", ex);
        }
        return carr;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCmsCarruselNumero(String titulo) {
        Long numcarr = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscarrusel.class);
            if (!titulo.equals("")) {
                dc.add(Restrictions.ilike("titulo", titulo, MatchMode.START));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numcarr = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsCarruselNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numcarr;
    }

    /**
     * CONTENIDOS
     */
    @Transactional
    public Boolean altaCmsContenido(cmscontenidos contenido) throws ParseException {
        try {
            this.currentSession().save(contenido);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaCmsContenido(cmscontenidos contenido) throws ParseException {
        try {
            this.currentSession().update(contenido);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public cmscontenidos getCmsContenido(Integer cmsId) {
        cmscontenidos cont = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscontenidos.class);
            if (cmsId != null) {
                dc.add(Restrictions.eq("id", cmsId));
            }
            cont = (cmscontenidos) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsContenido", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscontenidos> getCmsContenidoListadoBySeccion(Integer seccionId) {
        List<cmscontenidos> cont = new ArrayList<cmscontenidos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscontenidos.class);
            dc.add(Restrictions.eq("seccionid", seccionId));
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            cont = (List<cmscontenidos>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsContenidoListado", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscontenidos> getCmsContenidoListado(String titulo, Integer primerResultado) {
        List<cmscontenidos> cont = new ArrayList<cmscontenidos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscontenidos.class);
            if (titulo != null && !titulo.equals("")) {
                dc.add(Restrictions.ilike("titulo", titulo, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CONTENIDOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            /**
             * Sólo muestro contenidos que apuntan a sección url null, idseccion null
             */
            dc.createAlias("cmssecciones", "sec");
            dc.add(Restrictions.isNull("sec.link"));
            dc.add(Restrictions.isNull("sec.seccionappmovil"));

            if (primerResultado == null) {
                cont = (List<cmscontenidos>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                cont = (List<cmscontenidos>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsContenidoListado", ex);
        }
        return cont;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCmsContenidoNumero(String titulo) {
        Long numConts = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscontenidos.class);
            if (!titulo.equals("")) {
                dc.add(Restrictions.ilike("titulo", titulo, MatchMode.START));
            }
            /**
             * Sólo muestro contenidos que apuntan a sección url null, idseccion null
             */
            dc.createAlias("cmssecciones", "sec");
            dc.add(Restrictions.isNull("sec.link"));
            dc.add(Restrictions.isNull("sec.seccionappmovil"));


            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numConts = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsContenidoNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numConts;
    }

    /**
     * SECCIONES
     */
    @Transactional
    public Boolean altaCmsSecciones(cmssecciones seccion) throws ParseException {
        try {
            this.currentSession().save(seccion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaCmsSecciones(cmssecciones seccion) throws ParseException {
        try {
            this.currentSession().update(seccion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public cmssecciones getCmsSecciones(Integer cmsId) {
        cmssecciones sec = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmssecciones.class);
            if (cmsId != null) {
                dc.add(Restrictions.eq("id", cmsId));
            }
            sec = (cmssecciones) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsSecciones", ex);
        }
        return sec;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmssecciones> getCmsSeccionesLinkablesListado(String nombre, Integer primerResultado) {
        List<cmssecciones> sec = new ArrayList<cmssecciones>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmssecciones.class);
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_SECCIONES);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            /**
             * Sólo muestro contenidos que apuntan a sección url null, idseccion null
             */
            dc.add(Restrictions.isNull("link"));
            dc.add(Restrictions.isNull("seccionappmovil"));

            if (primerResultado == null) {
                sec = (List<cmssecciones>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                sec = (List<cmssecciones>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsSeccionesListado", ex);
        }
        return sec;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmssecciones> getCmsSeccionesListado(String nombre, Integer primerResultado) {
        List<cmssecciones> sec = new ArrayList<cmssecciones>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmssecciones.class);
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_SECCIONES);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                sec = (List<cmssecciones>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                sec = (List<cmssecciones>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsSeccionesListado", ex);
        }
        return sec;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmssecciones> getCmsSeccionesListadoMostrar() {
        List<cmssecciones> sec = new ArrayList<cmssecciones>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmssecciones.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            sec = (List<cmssecciones>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsSeccionesListadoMostrar", ex);
        }
        return sec;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getCmsSeccionesNumero(String nombre) {
        Long numeroEventos = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmssecciones.class);
            if (!nombre.equals("")) {
                dc.add(Restrictions.ilike("nombre", nombre, MatchMode.START));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numeroEventos = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCmsSeccionesNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numeroEventos;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CentroComercio> SelectAllCentrosAsociados() {
        DetachedCriteria dc = null;
        List<CentroComercio> res = new ArrayList<>();
        try {
            dc = DetachedCriteria.forClass(CentroComercio.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllCentrosAsociados", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> SelectAllCentrosAsociadosCategorias() {
        List<cmscategorias> res = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 1));
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllCentrosAsociadosCategorias", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CentroAyuntamiento> SelectAllCentrosMunicipales() {
        DetachedCriteria dc = null;
        List<CentroAyuntamiento> res = new ArrayList<>();
        try {
            dc = DetachedCriteria.forClass(CentroAyuntamiento.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllCentrosMunicipales", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> SelectAllCentrosMunicipalesCategorias() {
        List<cmscategorias> res = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 0));
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllCentrosMunicipalesCategorias", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmseventos> SelectAllEventos() {
        DetachedCriteria dc = null;
        List<cmseventos> res = new ArrayList<>();
        try {
            dc = DetachedCriteria.forClass(cmseventos.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("fecha").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllEventos", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<cmscategorias> SelectAllEventosCategorias() {
        List<cmscategorias> res = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 2));
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.SelectAllEventosCategorias", ex);
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<CentroComercio>> PagedCentrosAsociados(modelo modelo) {
        List<CentroComercio> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroComercio.class);
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            if (modelo.getCategoriaId() != null && modelo.getCategoriaId() > 0) {
                dc.add(Restrictions.eq("idCategoria", modelo.getCategoriaId()));
            }
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession())
                    .setFirstResult(modelo.getPagina() * modelo.getCantidadResultados())
                    .setMaxResults(modelo.getCantidadResultados())
                    .list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedCentrosAsociados", ex);
        }
        return new ImmutablePair<Integer, List<CentroComercio>>(total, res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<cmscategorias>> PagedCentrosAsociadosCategorias(modelo modelo) {
        List<cmscategorias> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 1));
            dc.add(Restrictions.eq("mostrar", true));
            if (modelo.getCategoriaId() != null) {
                dc.add(Restrictions.eq("id", modelo.getCategoriaId()));
            }
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession()).setFirstResult(modelo.getPagina() * modelo.getCantidadResultados()).setMaxResults(modelo.getCantidadResultados()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedCentrosAsociadosCategorias", ex);
        }
        return new ImmutablePair<Integer, List<cmscategorias>>(total, res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<CentroAyuntamiento>> PagedCentrosMunicipales(modelo modelo) {
        List<CentroAyuntamiento> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(CentroAyuntamiento.class);
            if (modelo.getCategoriaId() != null && modelo.getCategoriaId() > 0) {
                dc.add(Restrictions.eq("idCategoria", modelo.getCategoriaId()));
            }
//            dc.add(Restrictions.eq("idCategoria", modelo.getCategoriaId()));
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession())
                    .setFirstResult(modelo.getPagina() * modelo.getCantidadResultados())
                    .setMaxResults(modelo.getCantidadResultados())
                    .list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedCentrosMunicipales", ex);
        }
        return new ImmutablePair<Integer, List<CentroAyuntamiento>>(total, res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<cmscategorias>> PagedCentrosMunicipalesCategorias(modelo modelo) {
        List<cmscategorias> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 0));
            dc.add(Restrictions.eq("mostrar", true));
            if (modelo.getCategoriaId() != null) {
                dc.add(Restrictions.eq("id", modelo.getCategoriaId()));
            }
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession()).setFirstResult(modelo.getPagina() * modelo.getCantidadResultados()).setMaxResults(modelo.getCantidadResultados()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedCentrosMunicipalesCategorias", ex);
        }
        return new ImmutablePair<Integer, List<cmscategorias>>(total, res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<cmseventos>> PagedEventos(modelo modelo) {
        List<cmseventos> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmseventos.class);
            if (modelo.getCategoriaId() != null && modelo.getCategoriaId() > 0) {
                dc.add(Restrictions.eq("eventosCategoriaId", modelo.getCategoriaId()));
            }
            dc.add(Restrictions.eq("mostrar", true));
            dc.addOrder(Order.asc("fecha").nulls(NullPrecedence.LAST));
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession())
                    .setFirstResult(modelo.getPagina() * modelo.getCantidadResultados())
                    .setMaxResults(modelo.getCantidadResultados())
                    .list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedEventos", ex);
        }
        return new ImmutablePair<Integer, List<cmseventos>>(total, res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ImmutablePair<Integer, List<cmscategorias>> PagedEventosCategorias(modelo modelo) {
        List<cmscategorias> res = null;
        Integer total = 0;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(cmscategorias.class);
            dc.add(Restrictions.eq("tipoCentro", 2));
            dc.add(Restrictions.eq("mostrar", true));
            if (modelo.getCategoriaId() != null) {
                dc.add(Restrictions.eq("id", modelo.getCategoriaId()));
            }
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            total = dc.getExecutableCriteria(this.currentSession())
                    .list().size();
            res = dc.getExecutableCriteria(this.currentSession()).setFirstResult(modelo.getPagina() * modelo.getCantidadResultados()).setMaxResults(modelo.getCantidadResultados()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.PagedEventosCategorias", ex);
        }
        return new ImmutablePair<Integer,List<cmscategorias>>(total, res);
    }


    /**
     * BENEFICIOS
     */
    @Transactional
    public Boolean altaBeneficios(beneficios beneficios) throws ParseException {
        try {
            this.currentSession().save(beneficios);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaBeneficios(beneficios beneficios) throws ParseException {
        try {
            this.currentSession().update(beneficios);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public beneficios getBeneficios(Integer beneficios) {
        beneficios cont = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficios.class);
            if (beneficios != null) {
                dc.add(Restrictions.eq("id", beneficios));
            }
            cont = (beneficios) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficios", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<beneficios> getBeneficiosListado(String nombre, Integer primerResultado) {
        List<beneficios> cont = new ArrayList<beneficios>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficios.class);
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("titulo", nombre, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.BENEFICIOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (primerResultado == null) {
                cont = (List<beneficios>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                cont = (List<beneficios>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
//            cont = (List<beneficios>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosListado", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<beneficios> getBeneficiosListadoMostrar() {
        List<beneficios> cont = new ArrayList<beneficios>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficios.class);
            dc.add(Restrictions.eq("mostrar", true));
//            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CONTENIDOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("orden"));
            cont = (List<beneficios>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosListadoMostrar", ex);
        }
        return cont;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getBeneficiosNumero(String nombre) {
        Long numConts = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficios.class);
            if (!nombre.equals("")) {
                dc.add(Restrictions.ilike("titulo", nombre, MatchMode.START));
            }
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numConts = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numConts;
    }

    /**
     * BENEFICIOSTIPOS
     */
    @Transactional
    public Boolean altaBeneficiosTipos(beneficiostipos beneficiostipos) throws ParseException {
        try {
            this.currentSession().save(beneficiostipos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean modificaBeneficiosTipos(beneficiostipos beneficiostipos) throws ParseException {
        try {
            this.currentSession().update(beneficiostipos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public beneficiostipos getBeneficiosTipos(Integer beneficiostiposid) {
        beneficiostipos cont = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficiostipos.class);
            if (beneficiostiposid != null) {
                dc.add(Restrictions.eq("id", beneficiostiposid));
            }
            cont = (beneficiostipos) dc.getExecutableCriteria(this.currentSession()).list().get(0);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosTipos", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<beneficiostipos> getBeneficiosTiposListado(String nombre, Integer primerResultado) {
        List<beneficiostipos> cont = new ArrayList<beneficiostipos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficiostipos.class);
            if (nombre != null && !nombre.equals("")) {
                dc.add(Restrictions.ilike("titulo", nombre, MatchMode.START));
            }
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.BENEFICIOS_TIPOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
//            dc.addOrder(Order.desc("orden"));
//            cont = (List<beneficiostipos>) dc.getExecutableCriteria(this.currentSession()).list();
            if (primerResultado == null) {
                cont = (List<beneficiostipos>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                cont = (List<beneficiostipos>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosTiposListado", ex);
        }
        return cont;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<beneficiostipos> getBeneficiosTiposListadoMostrar() {
        List<beneficiostipos> cont = new ArrayList<beneficiostipos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficiostipos.class);
            dc.add(Restrictions.eq("mostrar", true));
//            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECURSOS_APP_CONTENIDOS);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("orden"));
            cont = (List<beneficiostipos>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosTiposListadoMostrar", ex);
        }
        return cont;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getBeneficiosTiposNumero(String nombre) {
        Long numConts = 0l;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficiostipos.class);
            dc.setProjection(Projections.rowCount());
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            numConts = DataAccessUtils.longResult(dc.getExecutableCriteria(this.currentSession()).list());

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosTiposNumero", ex);
        } catch (Exception exc) {
            String excepcion = exc.toString();
        }
        return numConts;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<beneficiostipos> getBeneficiosTiposListado() {
        List<beneficiostipos> cont = new ArrayList<beneficiostipos>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(beneficiostipos.class);
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            cont = (List<beneficiostipos>) dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBeneficiosTiposListado", ex);
        }
        return cont;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CentroComercio> getCentrosComercio() {
        DetachedCriteria dc = null;
        List<CentroComercio> res = new ArrayList<>();
        try {
            dc = DetachedCriteria.forClass(CentroComercio.class);
            dc.addOrder(Order.asc("orden").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getCentrosComercio", ex);
        }
        return res;
    }


    @Transactional
    public Map<CentroComercio, List<beneficios>> CentrosAsociadosConBeneficios(modeloBeneficio modelo) {
        try {

            Query queryCentro = currentSession().createQuery(
                    "SELECT centro from CentroComercio as centro " +
                            "where (centro.idCategoria = :idCategoria or :idCategoria is null) " +
                            "and (centro.comercioRecomendado = :comercioRecomendado " +
                            "or :comercioRecomendado is null " +
                            "or :comercioRecomendado = false and comercioRecomendado is null)" +
                            " and centro.mostrar=true" +
                            " order by centro.orden asc"
            );

            queryCentro.setParameter("idCategoria", modelo.getCategoriaId());
            queryCentro.setParameter("comercioRecomendado", modelo.getComercioRecomendado());
            List<CentroComercio> res = (List<CentroComercio>) queryCentro.list();
            Map<CentroComercio, List<beneficios>> map = new HashMap<>();
            for (CentroComercio c : res) {
                List<beneficios> beneficios = new ArrayList<>();
                Integer comercioId = c.getId();
                Integer categoriaId = c.getIdCategoria();
                Query queryBeneficios = currentSession().createQuery(
                        "SELECT beneficios from beneficios as beneficios " +
                                " where (beneficios.comercioId = :comercioId" +
                                " or beneficios.categoriaId = :categoriaId) " +
                                " and (beneficios.tipoBeneficioId = :tipoBeneficioId " +
                                " or :tipoBeneficioId is null)" +
                                " and (:fecha is null or " +
                                " :fecha >= beneficios.fechaInicio and :fecha <= beneficios.fechaFin)"
                );
                queryBeneficios.setParameter("tipoBeneficioId", modelo.getTipoBeneficio());
                queryBeneficios.setParameter("fecha", modelo.getFechaInicio());
                queryBeneficios.setParameter("comercioId", comercioId);
                queryBeneficios.setParameter("categoriaId", categoriaId);

                beneficios = (List<beneficios>) queryBeneficios.list();

                if (modelo.getConBeneficio() != null && modelo.getConBeneficio() == true
                        && (beneficios == null || beneficios.size() == 0)) {
                    //Sin beneficios y beneficios requerido, saltamos
                } else {
                    map.put(c, beneficios);
                }
            }
            return map;
        } catch (Exception e) {
            String exc = e.toString();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<seccionesappmovil> getAllSeccionesAppMovil() {
        DetachedCriteria dc = null;
        List<seccionesappmovil> res = new ArrayList<>();
        try {
            dc = DetachedCriteria.forClass(seccionesappmovil.class);
            dc.addOrder(Order.asc("nombreSeccion").nulls(NullPrecedence.LAST));
            res = dc.getExecutableCriteria(this.currentSession()).list();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAllSeccionesAppMovil", ex);
        }
        return res;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public seccionesappmovil getSeccionAppMovil(Integer id) {
        DetachedCriteria dc = null;
        seccionesappmovil res = null;
        try {
            dc = DetachedCriteria.forClass(seccionesappmovil.class);
            dc.add(Restrictions.eq("id", id));
            dc.addOrder(Order.asc("nombreSeccion").nulls(NullPrecedence.LAST));
            res = (seccionesappmovil) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAllSeccionesAppMovil", ex);
        }
        return res;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<RecargaSolicitudListItem> getEntidadesForBySolicitudId(
            String[] idsExcluidos,
            Integer solicitudId) {
        try {
            List<Entidad> res = new ArrayList<>();

            solicitudsaldovirtual solicitud = getSolicitudSaldoVirtual(solicitudId);

            DetachedCriteria dcRel = DetachedCriteria.forClass(solicitudsaldovirtual_ciudadano.class);
            dcRel.add(Restrictions.eq("solicitud.id", solicitudId));
            dcRel.add(Restrictions.isNull("eliminado"));
            List<solicitudsaldovirtual_ciudadano> relacionesEnLiza =
                    (List<solicitudsaldovirtual_ciudadano>)
                            dcRel.getExecutableCriteria(this.currentSession()).list();

            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECARGAS_DETALLE);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            dc.add(Restrictions.in("id", relacionesEnLiza
                    .stream().map(x -> x.getCiudadano()).collect(Collectors.toList())));

//            ProjectionList projList = Projections.projectionList();
//            projList.add(Projections.property("tipologias"));
//            dc.setProjection(projList);
            /**
             * Fetch lazy -> Eager
             */
//            dc.add(Restrictions.or(Restrictions.isNotNull("tipologias"), Restrictions.isNull("tipologias")));
            if (idsExcluidos == null || (idsExcluidos.length == 1
                    && idsExcluidos[0].isEmpty())) {
                idsExcluidos = null;
            }

            List<Integer> excluidos = new ArrayList<>();
            if (idsExcluidos != null) {
                List<String> limpiame = new ArrayList<>();
                for (String id : idsExcluidos) {
                    if (!id.isEmpty()) {
                        limpiame.add(id);
                    }
                }
                excluidos = limpiame.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
            }

            List<Integer> finalExcluidos = excluidos;

            res = (List<Entidad>) dc.getExecutableCriteria(this.currentSession()).list();
            return res.
                    stream().map(x ->
                    new RecargaSolicitudListItem(finalExcluidos == null || !finalExcluidos.contains(x.getId()), x.getNombre(), x.getApellidos(),
                            x.getTipologias().stream()
                                    .map(y -> y.getTipoCiudadano().getNombre()).collect(Collectors.joining(", ")),
                            x.getSaldoVirtual(), x.getIdEntidad(),
                            x.getId(),
                            solicitud.getFechavalidacion() == null)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error( "DAOimpl.getEntidadesForRecarga", e);
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<RecargaSolicitudListItem> getEntidadesForRecarga(
            String[] idsExcluidos,
            Integer tipologia,
            String idEntidad,
            BigDecimal saldoMaximo,
            Integer primerResultado) {
        try {
            List<Entidad> res = new ArrayList<>();


            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECARGAS_SOLICITUD);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (tipologia != null && tipologia == -1
            ) {
                tipologia = null;

            }
            if (tipologia != null) {
                dc.createAlias("tipologias", "tp");
                dc.add(Restrictions.eq("tp.tipoCiudadano.id", tipologia));
            }
            if (idEntidad != null && !idEntidad.isEmpty()) {
                dc.add(Restrictions.eq("idEntidad", idEntidad));
            }
            if (saldoMaximo != null) {
                dc.add(Restrictions.or(Restrictions.le("saldoVirtual", saldoMaximo), Restrictions.isNull("saldoVirtual")));
            }

//            ProjectionList projList = Projections.projectionList();
//            projList.add(Projections.property("tipologias"));
//            dc.setProjection(projList);
            /**
             * Fetch lazy -> Eager
             */
//            dc.add(Restrictions.or(Restrictions.isNotNull("tipologias"), Restrictions.isNull("tipologias")));

            if (primerResultado == null) {
                res = (List<Entidad>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                res = (List<Entidad>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }

            if (idsExcluidos == null || (idsExcluidos.length == 1
                    && idsExcluidos[0].isEmpty())) {
                idsExcluidos = null;
            }

            List<Integer> excluidos = new ArrayList<>();
            if (idsExcluidos != null) {
                List<String> limpiame = new ArrayList<>();
                for (String id : idsExcluidos) {
                    if (!id.isEmpty()) {
                        limpiame.add(id);
                    }
                }
                excluidos = limpiame.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
            }

            List<Integer> finalExcluidos = excluidos;

            List<RecargaSolicitudListItem> nuevorest = new ArrayList<RecargaSolicitudListItem>();

            for (int x = 0; x < res.size(); x++) {

                //Entidad entidad= res.remove(0);
                RecargaSolicitudListItem rcl = new RecargaSolicitudListItem();
                rcl.setNombre(res.get(x).getNombre());
                rcl.setApellidos(res.get(x).getApellidos());
                rcl.setTipologia(res.get(x).getTipologias().stream().map(y -> y.getTipoCiudadano().getNombre()).collect(Collectors.joining(", ")));
                rcl.setSaldoVirtual(res.get(x).getSaldoVirtual());
                rcl.setIdEntidad(res.get(x).getIdEntidad());
                rcl.setId(res.get(x).getId());
                rcl.setSeleccionado(finalExcluidos == null || !finalExcluidos.contains(res.get(x).getId()));

                nuevorest.add(rcl);
            }
            return nuevorest;

//            return res.
//                    stream().map(x ->
//                    new RecargaSolicitudListItem(finalExcluidos == null || !finalExcluidos.contains(x.getId()),
//                            x.getNombre(),
//                            x.getApellidos(),
//                            x.getTipologias().stream().map(y -> y.getTipoCiudadano().getNombre()).collect(Collectors.joining(", ")),
//                            x.getSaldoVirtual(),
//                            x.getIdEntidad(),
//                            x.getId())
//            ).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error( "DAOimpl.getEntidadesForRecarga", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTotalEntidadesForRecarga(
            Integer tipologia,
            String idEntidad,
            BigDecimal saldoMaximo) {
        try {
            List<Entidad> res = new ArrayList<>();

            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECARGAS_SOLICITUD);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));

            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (tipologia != null) {
//                TipologiaCiudadano t = getTipologiaCiudadanoId(tipologia);
                dc.createAlias("tipologias", "tp");
                dc.add(Restrictions.eq("tp.tipoCiudadano.id", tipologia));
            }
            if (idEntidad != null) {
                dc.add(Restrictions.eq("idEntidad", idEntidad));
            }
            if (saldoMaximo != null) {
                dc.add(Restrictions.or(Restrictions.le("saldoVirtual", saldoMaximo), Restrictions.isNull("saldoVirtual")));
            }

            return Long.valueOf((dc.getExecutableCriteria(this.currentSession()).list()).size());
        } catch (Exception e) {
            logger.error( "DAOimpl.getTotalEntidadesForRecarga", e);
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<solicitudsaldovirtual> getSolicitudesPendientes(
            Integer primerResultado,
            String descripcion,
            Boolean validada,
            Date fechaInicial,
            Date fechaFinal) {
        try {
            List<solicitudsaldovirtual> res = new ArrayList<>();

            DetachedCriteria dc = DetachedCriteria.forClass(solicitudsaldovirtual.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.LISTADO_RECARGAS_SOLICITUD);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            dc.add(Restrictions.or(Restrictions.eq("eliminada", false), Restrictions.isNull("eliminada")));

            if (descripcion != null) {
                dc.add(Restrictions.ilike("DescripcionBreve", descripcion, MatchMode.START));
            }
            if (validada != null && validada == true) {
                dc.add(Restrictions.isNotNull("fechavalidacion"));
            }
            if (validada != null && validada == false) {
                dc.add(Restrictions.isNull("fechavalidacion"));
            }
            if (fechaInicial != null) {
                dc.add(Restrictions.gt("fechasolicitud", fechaInicial));
            }
            if (fechaFinal != null) {
                dc.add(Restrictions.lt("fechasolicitud", fechaFinal));
            }

            if (primerResultado == null) {
                res = (List<solicitudsaldovirtual>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                res = (List<solicitudsaldovirtual>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }

            for (solicitudsaldovirtual sol : res) {
                sol.setRelaciones(
                        sol.getRelaciones()
                                .stream().filter(x -> x.getFechaEliminado() == null)
                                .distinct()
                                .collect(Collectors.toList()));
            }
            return res;
        } catch (Exception e) {
            logger.error( "DAOimpl.getSolicitudesPendientes", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTotalSolicitudesPendientes(
            String descripcion,
            Boolean validada,
            Date fechaInicial,
            Date fechaFinal
    ) {
        try {
            List<solicitudsaldovirtual> res = new ArrayList<>();

            DetachedCriteria dc = DetachedCriteria.forClass(solicitudsaldovirtual.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.LISTADO_RECARGAS_SOLICITUD);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (descripcion != null) {
                dc.add(Restrictions.ilike("DescripcionBreve", descripcion, MatchMode.START));
            }
            if (validada != null && validada == true) {
                dc.add(Restrictions.isNotNull("fechavalidacion"));
            }
            if (validada != null && validada == false) {
                dc.add(Restrictions.isNull("fechavalidacion"));
            }
            if (fechaInicial != null) {
                dc.add(Restrictions.gt("fechasolicitud", fechaInicial));
            }
            if (fechaFinal != null) {
                dc.add(Restrictions.lt("fechasolicitud", fechaFinal));
            }


            dc.add(Restrictions.or(Restrictions.eq("eliminada", false), Restrictions.isNull("eliminada")));
            Long total = Long.valueOf((dc.getExecutableCriteria(this.currentSession()).list()).size());
            return total;
        } catch (Exception e) {
            logger.error( "DAOimpl.getTotalSolicitudesPendientes", e);
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public solicitudsaldovirtual saveSolicitudSaldoVirtual(solicitudsaldovirtual solicitud) {
        try {
            this.currentSession().save(solicitud);
            return solicitud;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveSolicitudSaldoVirtual", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public solicitudsaldovirtual updateSolicitudSaldoVirtual(solicitudsaldovirtual solicitud) {
        try {
            this.currentSession().update(solicitud);
            return solicitud;
        } catch (Exception e) {
            logger.error( "DAOimpl.updateSolicitudSaldoVirtual", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public historialSaldoVirtual saveHistorialSaldoVirtual(historialSaldoVirtual historial) {
        try {
            this.currentSession().save(historial);
            return historial;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveHistorialSaldoVirtual", e);
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<Integer> saveRangeSolicitudsaldovirtual_ciudadano(List<solicitudsaldovirtual_ciudadano> listado) {
        try {
            List<Integer> result = new ArrayList<Integer>();

            if (listado == null) {
                return null;
            }

            for (solicitudsaldovirtual_ciudadano entity : listado) {
                result.add((Integer) this.currentSession().save(entity));
            }

            return result;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveRangeSolicitudsaldovirtual_ciudadano", e);
            return new ArrayList<Integer>();
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<HistorialCiudadano> altaHistorialCiudadano(RecargaSolicitudListItem[] recargaSolicitud) {
        try {
            List<HistorialCiudadano> result = new ArrayList<HistorialCiudadano>();

            if (recargaSolicitud == null) {
                return result;
            }

            User u = getUserUsername(LogUtils.getUserName());
            for (RecargaSolicitudListItem entity : recargaSolicitud) {
                Entidad e = getEntidadIdEntidad(entity.getIdEntidad());
                result.add((HistorialCiudadano) this.currentSession().save(
                        new HistorialCiudadano(e,
                                u,
                                e.getEstado(),
                                "Solicitud de recarga de saldo virtual iniciada. ID de la solicitud: " + entity.getId()))
                );
            }
            return result;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveRangeSolicitudsaldovirtual_ciudadano", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean validateRelaciones(List<Integer> relacionesBorrar, Integer solicitudId) {

        try {
            List<solicitudsaldovirtual_ciudadano> res = new ArrayList<>();
            DetachedCriteria dc = DetachedCriteria.forClass(solicitudsaldovirtual_ciudadano.class);

            dc.add(Restrictions.isNull("eliminado"));
            dc.add(Restrictions.in("solicitud.id", solicitudId));

            res = (List<solicitudsaldovirtual_ciudadano>) dc.getExecutableCriteria(this.currentSession()).list();

            User u = getUserUsername(LogUtils.getUserName());
            Integer idUsuario = u.getId();
//            for (solicitudsaldovirtual_ciudadano entity : res)
            for (Integer i = 0; i < res.size(); i++) {
                solicitudsaldovirtual_ciudadano entity = res.get(i);
                entity.setValidado(true);
                entity.setFechaValidado(new Date());
                entity.setUsuarioValida(idUsuario);
                this.currentSession().update(entity);
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.validateRelaciones", e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteRelaciones(List<Integer> relacionesBorrar, Integer solicitudId) {

        try {
            List<solicitudsaldovirtual_ciudadano> res = new ArrayList<>();
            DetachedCriteria dc = DetachedCriteria.forClass(solicitudsaldovirtual_ciudadano.class);

            dc.add(Restrictions.in("ciudadano", relacionesBorrar));
            dc.add(Restrictions.in("solicitud.id", solicitudId));

            res = (List<solicitudsaldovirtual_ciudadano>) dc.getExecutableCriteria(this.currentSession()).list();

            User u = getUserUsername(LogUtils.getUserName());
            for (solicitudsaldovirtual_ciudadano entity : res) {
                entity.setEliminado(true);
                entity.setFechaEliminado(new Date());
                entity.setUsuarioElimina(u);
                this.currentSession().update(entity);
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.deleteAlerta", e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean altaHistorialCiudadanoAddedSaldo(List<Integer> CiudadanosIds, Integer idSolicitud) {
        try {
            List<Integer> result = new ArrayList<Integer>();

            if (CiudadanosIds == null) {
                return false;
            }
            solicitudsaldovirtual sol = getSolicitudSaldoVirtual(idSolicitud);
            User u = getUserUsername(LogUtils.getUserName());
            for (Integer i = 0; i < CiudadanosIds.size(); i++) {
//            for (Integer id : CiudadanosIds) {
                Entidad e = getCiudadanoId(CiudadanosIds.get(i));
                /**
                 * Sumo saldo
                 */
                e.setSaldoVirtual(e.getSaldoVirtual().add(sol.getSaldorecargar()));
                this.currentSession().save(
                        new HistorialCiudadano(e,
                                u,
                                e.getEstado(),
                                "Solicitud de recarga de saldo virtual validada. " +
                                        " SALDO DEL CIUDADANO: " + e.getSaldoVirtual() +
                                        ". ID de la solicitud: " + idSolicitud + "" +
                                        ". IMPORTE DE LA RECARGA: " + sol.getSaldorecargar() +
                                        ". Ciudadanos afectados: " + CiudadanosIds.size()));
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.altaHistorialCiudadanoRemovedSaldo", e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean altaHistorialCiudadanoSolicitudRealizada(List<Integer> CiudadanosIds, Integer idSolicitud) {
        try {
            List<Integer> result = new ArrayList<Integer>();

            if (CiudadanosIds == null) {
                return false;
            }
            solicitudsaldovirtual sol = getSolicitudSaldoVirtual(idSolicitud);
            User u = getUserUsername(LogUtils.getUserName());
            for (Integer i = 0; i < CiudadanosIds.size(); i++) {
//            for (Integer id : CiudadanosIds) {
                Entidad e = getCiudadanoId(CiudadanosIds.get(i));
                this.currentSession().save(
                        new HistorialCiudadano(e,
                                u,
                                e.getEstado(),
                                "Solicitud de recarga de saldo virtual iniciada. " +
                                        " SALDO DEL CIUDADANO: " + e.getSaldoVirtual() +
                                        ". ID de la solicitud: " + idSolicitud + "" +
                                        ". IMPORTE DE LA RECARGA: " + sol.getSaldorecargar() +
                                        ". Ciudadanos afectados: " + CiudadanosIds.size()));
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.altaHistorialCiudadanoRemovedSaldo", e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean altaHistorialCiudadanoRemovedSaldo(List<Integer> CiudadanosIds, Integer idSolicitud) {
        try {
            List<Integer> result = new ArrayList<Integer>();

            if (CiudadanosIds == null) {
                return false;
            }

            User u = getUserUsername(LogUtils.getUserName());
            for (Integer i = 0; i < CiudadanosIds.size(); i++) {
//            for (Integer id : CiudadanosIds) {
                Entidad e = getCiudadanoId(CiudadanosIds.get(i));
                HistorialCiudadano h = new HistorialCiudadano(e,
                        u,
                        e.getEstado(),
                        "Solicitud de recarga de saldo virtual cancelada. " +
                                "ID de la solicitud: " + idSolicitud + "" +
                                ". Ciudadanos afectados: " + CiudadanosIds.size());
                this.currentSession().save(h);
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.altaHistorialCiudadanoRemovedSaldo", e);
            return false;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<RecargaSolicitudListItem> getEntidadesFromSolicitud(
            Integer tipologia,
            String idEntidad,
            BigDecimal saldoMaximo,
            String[] idsExcluidos,
            Integer solicitudId,
            Integer primerResultado) {
        try {
            List<Entidad> res = new ArrayList<>();

            solicitudsaldovirtual solicitud = getSolicitudSaldoVirtual(solicitudId);

            DetachedCriteria dcRel = DetachedCriteria.forClass(solicitudsaldovirtual_ciudadano.class);
            dcRel.add(Restrictions.eq("solicitud.id", solicitudId));
            dcRel.add(Restrictions.isNull("eliminado"));
            List<solicitudsaldovirtual_ciudadano> relacionesEnLiza =
                    (List<solicitudsaldovirtual_ciudadano>)
                            dcRel.getExecutableCriteria(this.currentSession()).list();

            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.LISTADO_RECARGAS_SOLICITUD);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (tipologia != null) {
                dc.createAlias("tipologias", "tp");
                dc.add(Restrictions.eq("tp.tipoCiudadano.id", tipologia));
            }
            if (idEntidad != null) {
                dc.add(Restrictions.eq("idEntidad", idEntidad));
            }
            if (saldoMaximo != null) {
                dc.add(Restrictions.or(Restrictions.le("saldoVirtual", saldoMaximo), Restrictions.isNull("saldoVirtual")));
            }
            dc.add(Restrictions.in("id", relacionesEnLiza
                    .stream().map(x -> x.getCiudadano()).collect(Collectors.toList())));

//            ProjectionList projList = Projections.projectionList();
//            projList.add(Projections.property("tipologias"));
//            dc.setProjection(projList);
            /**
             * Fetch lazy -> Eager
             */
//            dc.add(Restrictions.or(Restrictions.isNotNull("tipologias"), Restrictions.isNull("tipologias")));
            if (idsExcluidos == null || (idsExcluidos.length == 1
                    && idsExcluidos[0].isEmpty())) {
                idsExcluidos = null;
            }

            List<Integer> excluidos = new ArrayList<>();
            if (idsExcluidos != null) {
                List<String> limpiame = new ArrayList<>();
                for (String id : idsExcluidos) {
                    if (!id.isEmpty()) {
                        limpiame.add(id);
                    }
                }
                excluidos = limpiame.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
            }

            List<Integer> finalExcluidos = excluidos;

            if (primerResultado == null) {
                res = (List<Entidad>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                res = (List<Entidad>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }
            return res.
                    stream().map(x ->
                    new RecargaSolicitudListItem(
                            finalExcluidos == null || !finalExcluidos.contains(x.getId()),
                            x.getNombre(),
                            x.getApellidos(),
                            x.getTipologias().stream()
                                    .map(y -> y.getTipoCiudadano().getNombre()).collect(Collectors.joining(", ")),
                            x.getSaldoVirtual(), x.getIdEntidad(), x.getId(),
                            solicitud.getFechavalidacion() == null)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error( "DAOimpl.getEntidadesForRecarga", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long getTotalEntidadesFromSolicitud(
            Integer tipologia,
            String idEntidad,
            BigDecimal saldoMaximo,
            Integer solicitudId) {
        try {
            List<Entidad> res = new ArrayList<>();

            DetachedCriteria dcRel = DetachedCriteria.forClass(solicitudsaldovirtual_ciudadano.class);
            dcRel.add(Restrictions.eq("solicitud.id", solicitudId));
            dcRel.add(Restrictions.isNull("eliminado"));
            List<solicitudsaldovirtual_ciudadano> relacionesEnLiza =
                    (List<solicitudsaldovirtual_ciudadano>)
                            dcRel.getExecutableCriteria(this.currentSession()).list();


            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc = setOrdenListado(dc, OrdenBySession.LISTADO_TIPOS.RECARGAS_DETALLE);
            /**
             * Sólo atiende al primer orden establecido, primero trato de establecerlo con el método anterior,
             * en su defecto creo el orden a continuación
             */
            dc.addOrder(Order.desc("id"));
            if (tipologia != null) {
//                TipologiaCiudadano t = getTipologiaCiudadanoId(tipologia);
                dc.createAlias("tipologias", "tp");
                dc.add(Restrictions.eq("tp.tipoCiudadano.id", tipologia));
            }
            if (idEntidad != null) {
                dc.add(Restrictions.eq("idEntidad", idEntidad));
            }
            if (saldoMaximo != null) {
                dc.add(Restrictions.or(Restrictions.le("saldoVirtual", saldoMaximo), Restrictions.isNull("saldoVirtual")));
            }
            dc.add(Restrictions.in("id", relacionesEnLiza
                    .stream().map(x -> x.getCiudadano()).collect(Collectors.toList())));

            return Long.valueOf((dc.getExecutableCriteria(this.currentSession()).list()).size());
        } catch (Exception e) {
            logger.error( "DAOimpl.getTotalEntidadesForRecarga", e);
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public solicitudsaldovirtual getSolicitudSaldoVirtual(Integer solicitudId) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(solicitudsaldovirtual.class);
            dc.add(Restrictions.eq("id", solicitudId));

            solicitudsaldovirtual res = null;
            res = (solicitudsaldovirtual) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
            return res;
        } catch (Exception e) {
            logger.error( "DAOimpl.getSolicitudSaldoVirtual", e);
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Venta saveVenta(Venta venta) {
        try {
            this.currentSession().save(venta);
            return venta;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveVenta", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Venta updateVenta(Venta venta) {
        try {
            this.currentSession().update(venta);
            return venta;
        } catch (Exception e) {
            logger.error( "DAOimpl.updateVenta", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Venta getVentaByLocalizador(String localizador) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Venta.class);
            dc.add(Restrictions.eq("Localizador", localizador));

            Venta res = null;
            res = (Venta) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
            return res;
        } catch (Exception e) {
            logger.error( "DAOimpl.getVentaByLocalizador", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<VentasLineas> getVentasLineas(Venta venta) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(VentasLineas.class);
            dc.add(Restrictions.eq("VentaId", venta.getId()));

            List<VentasLineas> res = null;
            res = (List<VentasLineas>) dc.getExecutableCriteria(this.currentSession()).list();
            return res;
        } catch (Exception e) {
            logger.error( "DAOimpl.getVentasLineas", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VentasLineas saveVentasLineas(VentasLineas ventaLinea) {
        try {
            this.currentSession().save(ventaLinea);
            return ventaLinea;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveVentasLineas", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VentasLineas updateVentasLineas(VentasLineas ventaLinea) {
        try {
            this.currentSession().update(ventaLinea);
            return ventaLinea;
        } catch (Exception e) {
            logger.error( "DAOimpl.updateVentasLineas", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public HistorialVentas saveHistorialHistorialVentas(HistorialVentas historial) {
        try {
            this.currentSession().save(historial);
            return historial;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveHistorialHistorialVentas", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public tokens_pagos saveTokenCobro(tokens_pagos token) {
        try {
            this.currentSession().save(token);
            return token;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveTokensPagos", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public tokens_pagos updateTokenCobro(tokens_pagos token) {
        try {
            this.currentSession().update(token);
            return token;
        } catch (Exception e) {
            logger.error( "DAOimpl.updateTokensPagos", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public tokens_pagos getTokenCobroByToken(String token) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(tokens_pagos.class);
            dc.add(Restrictions.eq("token", token));

            tokens_pagos res = null;
            res = (tokens_pagos) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
            return res;
        } catch (Exception e) {
            logger.error( "DAOimpl.getTokenCobroByToken", e);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean updateIdFcm(String idCiudadano, String idFcm) {
        Entidad ciudadano = getEntidadIdEntidad(idCiudadano);
        if (ciudadano == null) {
            return false;
        } else {
            try {
                ciudadano.setIdFCM(idFcm);
                updateEntidad(ciudadano);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 22/10/2020 -> xFer pide que no sólo podamos sacar el token por
     * tokenTemporal, también lo tenemos que poder sacar si el token
     * es idCiudadano o idTarjeta
     *
     * @param token
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Entidad getEntidadTokenTemporal(String token) {
        Entidad entidad = null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.eq("tokenTemporal", token));
            entidad = (Entidad) DataAccessUtils.uniqueResult(dc.getExecutableCriteria(this.currentSession()).list());
            if (entidad == null) {
                //Si no he encontrado el ciudadano, pruebo por idCiudadano
                entidad = getEntidadIdEntidad(token);
                if (entidad == null) {
                    Tarjeta tarj = getTarjetaIdTarjeta(token);
                    if (tarj != null) {
                        entidad = tarj.getEntidad();
                    }
                }
            }
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getEntidadTokenTemporal", ex);
        }
        return entidad;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public RedsysNotification saveRedsysNotification(RedsysNotification not) {
        try {
            this.currentSession().save(not);
            return not;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveRedsysNotification", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RedsysError saveRedsysError(RedsysError error) {
        try {
            this.currentSession().save(error);
            return error;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveRedsysError", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LiquidacionSaldo saveLiquidacionSaldo(LiquidacionSaldo liquidacion) {
        try {
            this.currentSession().save(liquidacion);
            return liquidacion;
        } catch (Exception e) {
            logger.error( "DAOimpl.saveLiquidacionSaldo", e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LiquidacionSaldo updateLiquidacionSaldo(LiquidacionSaldo liquidacion) {
        try {
            this.currentSession().update(liquidacion);
            return liquidacion;
        } catch (Exception e) {
            logger.error( "DAOimpl.updateLiquidacionSaldo", e);
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean liquidaSaldo(List<Compra> compras, LiquidacionSaldo liquidacion) {
        try {
            for (Compra compra : compras) {
                compra.setLiquidacionSaldo(liquidacion);
                this.currentSession().update(compra);
            }
            return true;
        } catch (Exception e) {
            logger.error( "DAOimpl.liquidacionSaldo", e);
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionSaldo.class);
            if (comercioId != null) {
                dc.add(Restrictions.eq("comercio.id", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            if (estado != null && estado > 0) {
                dc.add(Restrictions.eq("estado.id", estado));
            }
            if (estado != null && estado == 0) {
                dc.add(Restrictions.isNull("estado"));
            }
            if (lote != null && !lote.isEmpty()) {
                dc.add(Restrictions.eq("referencia", lote));
            }
            List<LiquidacionSaldo> liquidaciones = (List<LiquidacionSaldo>) dc.getExecutableCriteria(this.currentSession()).list();
            liquidaciones = liquidaciones
                    .stream()
                    .collect(Collectors.toList());
            return liquidaciones
                    .stream()
                    .map(x -> x.getImporte())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroLiquidacionesComercio", ex);
        }
        return null;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImportesTotalesTicketsLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionSaldo.class);
            if (comercioId != null) {
                dc.add(Restrictions.eq("comercio.id", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            if (estado != null && estado > 0) {
                dc.add(Restrictions.eq("estado.id", estado));
            }
            if (estado != null && estado == 0) {
                dc.add(Restrictions.isNull("estado"));
            }
            if (lote != null && !lote.isEmpty()) {
                dc.add(Restrictions.eq("referencia", lote));
            }
            List<LiquidacionSaldo> liquidaciones = (List<LiquidacionSaldo>) dc.getExecutableCriteria(this.currentSession()).list();
            liquidaciones = liquidaciones
                    .stream()
                    .collect(Collectors.toList());
            return liquidaciones
                    .stream()
                    .map(x -> x.getImporte())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroLiquidacionesComercio", ex);
        }
        return null;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Long getNumeroLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote) {
        Long numeroOperaciones = 0L;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionSaldo.class);
            if (comercioId != null) {
                dc.add(Restrictions.eq("comercio.id", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            if (estado != null && estado > 0) {
                dc.add(Restrictions.eq("estado.id", estado));
            }
            if (estado != null && estado == 0) {
                dc.add(Restrictions.isNull("estado"));
            }
            if (lote != null && !lote.isEmpty()) {
                dc.add(Restrictions.eq("referencia", lote));
            }
            List<LiquidacionSaldo> liquidaciones = (List<LiquidacionSaldo>) dc.getExecutableCriteria(this.currentSession()).list();
            liquidaciones = liquidaciones
                    .stream()
                    .collect(Collectors.toList());
            numeroOperaciones = new Long(liquidaciones.size());
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getNumeroLiquidacionesComercio", ex);
        }
        return numeroOperaciones;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<LiquidacionSaldo> getLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer estado, String lote) {
        List liquidaciones = new ArrayList();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(LiquidacionSaldo.class);
            if (comercioId != null) {
                dc.add(Restrictions.eq("comercio.id", comercioId));
            }
            if (fechaInicial != null && fechaFinal != null) {
                dc.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            if (estado != null && estado > 0) {
                dc.add(Restrictions.eq("estado.id", estado));
            }
            if (estado != null && estado == 0) {
                dc.add(Restrictions.isNull("estado"));
            }
            if (lote != null && !lote.isEmpty()) {
                dc.add(Restrictions.eq("referencia", lote));
            }
            dc.addOrder(Order.desc("fecha").nulls(NullPrecedence.LAST));

            if (primerResultado == null) {
                liquidaciones = (List<LiquidacionSaldo>) dc.getExecutableCriteria(this.currentSession()).list();
            } else {
                liquidaciones = (List<LiquidacionSaldo>) dc.getExecutableCriteria(this.currentSession()).setFirstResult(primerResultado).setMaxResults(Constantes.NUMERO_ELEMENTOS_PAGINA).list();
            }

        } catch (Exception ex) {
            logger.error( "DAOiml.getLiquidacionesComercio", ex);
        }
        return liquidaciones;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<LiquidacionAgrupado> getLiquidacionesAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado) {
        List<LiquidacionAgrupado> resultado = new ArrayList<>();
        List<LiquidacionAgrupado> liquidaciones = new ArrayList<LiquidacionAgrupado>();
        try {

            String cadenaWhereFechas = " and c.importeTotalTicket is not null";
            if (fechaInicial != null && fechaFinal != null) {
                cadenaWhereFechas = " and fechaRealizacion between :fechaIni and :fechaFin";
            }
            if (comercioId != null) {
                cadenaWhereFechas += " and c.comercioId =" + comercioId;
            }
            Query query = currentSession().createQuery(
                    "SELECT " +
                            "(select idCentro from CentroComercio as comer where id = c.comercioId) as idComercio, " +
                            "c.comercioId as comercioId, " +
                            "(select nombre from CentroComercio x where x.id=c.comercioId) as nombreComercio," +
                            "(select sum(importe) from Compra as comp where comercioId = c.comercioId and idLiquidacionSaldo is null) as importeSinSolicitar, " +
                            "(select sum(importe) from Compra as comp where comercioId = c.comercioId and idLiquidacionSaldo is not null and liquidacionSaldo.estado.id = 1) as importePendiente, " +
                            "(select sum(importe) from Compra as comp where comercioId = c.comercioId and idLiquidacionSaldo is not null and liquidacionSaldo.estado.id = 2) as importeLiquidado, " +
                            "(select sum(importe) from Compra as comp where comercioId = c.comercioId) as total " +
                            " FROM Compra as c where c.comercioId is not null " +
                            cadenaWhereFechas +
                            " group by c.comercioId" +
                            " order by fechaRealizacion desc"
            );
            if (fechaInicial != null && fechaFinal != null) {
                query.setParameter("fechaIni", fechaInicial);
                query.setParameter("fechaFin", fechaFinal);
            }
            query.setResultTransformer(new AliasToBeanResultTransformer(LiquidacionAgrupado.class));

            if (primerResultado == null) {
                liquidaciones = (List<LiquidacionAgrupado>) query.list();
            } else {
                liquidaciones = (List<LiquidacionAgrupado>) query.list().stream().skip(primerResultado).limit(Constantes.NUMERO_ELEMENTOS_PAGINA).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getLiquidacionesAgrupado", ex);
        }
        return liquidaciones;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<BonosAgrupadoVM> getBonosAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado) {
        List<BonosAgrupadoVM> resultado = new ArrayList<>();
        List<BonosAgrupadoVM> res = new ArrayList<BonosAgrupadoVM>();
        try {

            String cadenaWhereFechas = " ";
            if (fechaInicial != null && fechaFinal != null) {
                cadenaWhereFechas = " and fechaRealizacion between :fechaIni and :fechaFin";
            }
            if (comercioId != null) {
                cadenaWhereFechas += " and r.comercioId =" + comercioId;
            }
            Query query = currentSession().createQuery(
                    "SELECT " +
                            " r.comercioId as comercioId, " +
                            " (Select c.idCentro from CentroComercio as c where c.id = r.comercioId) as idComercio, " +
                            "    (select c.nombre from CentroComercio as c where c.id = r.comercioId) as nombreComercio," +
                            "    (select count(c.id) from Recarga as c where c.comercioId = r.comercioId) as totalOperaciones," +
                            "    (Select SUM(importeTotalTicket) from Recarga as c where c.comercioId = r.comercioId) as totalTickets," +
                            "    (Select SUM(importe) from Recarga as c where c.comercioId = r.comercioId) as totalBonosConsumo" +
                            " from Recarga as r " +
                            " where r.ticketUrl is not null " +
                            cadenaWhereFechas +
                            " group by r.comercioId" +
                            " order by fechaRealizacion desc"
            );
            if (fechaInicial != null && fechaFinal != null) {
                query.setParameter("fechaIni", fechaInicial);
                query.setParameter("fechaFin", fechaFinal);
            }
            query.setResultTransformer(new AliasToBeanResultTransformer(BonosAgrupadoVM.class));

            if (primerResultado == null) {
                res = (List<BonosAgrupadoVM>) query.list();
            } else {
                res = (List<BonosAgrupadoVM>) query.list().stream().skip(primerResultado).limit(Constantes.NUMERO_ELEMENTOS_PAGINA).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getBonosAgrupado", ex);
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CobrosBonosAgrupadoVM> getBonosCobrosAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado) {
        List<CobrosBonosAgrupadoVM> resultado = new ArrayList<>();
        List<CobrosBonosAgrupadoVM> res = new ArrayList<CobrosBonosAgrupadoVM>();
        try {

            String cadenaWhereFechas = "";
            if (fechaInicial != null && fechaFinal != null) {
                cadenaWhereFechas = " and fechaRealizacion between :fechaIni and :fechaFin";
            }
            if (comercioId != null) {
                cadenaWhereFechas += " and c.comercioId =" + comercioId;
            }
            Query query = currentSession().createQuery(
                    "SELECT " +
                            " r.comercioId as comercioId, " +
                            " (Select c.idCentro from CentroComercio as c where c.id = r.comercioId) as idComercio, " +
                            "    (select c.nombre from CentroComercio as c where c.id = r.comercioId) as nombreComercio, " +
                            "    (select count(c) from Compra as c where c.comercioId = r.comercioId) as totalOperaciones, " +
                            "    (Select SUM(importeTotalTicket) from Compra as c where c.comercioId = r.comercioId) as totalImporteTicket, " +
                            "    (Select SUM(importe) from Compra as c where c.comercioId = r.comercioId) as totalCobroBonoConsumo " +
                            " from Compra as r " +
                            " where r.ticketUrl is not null " +
                            cadenaWhereFechas +
                            " group by r.comercioId" +
                            " order by fechaRealizacion desc"
            );
            if (fechaInicial != null && fechaFinal != null) {
                query.setParameter("fechaIni", fechaInicial);
                query.setParameter("fechaFin", fechaFinal);
            }
            query.setResultTransformer(new AliasToBeanResultTransformer(CobrosBonosAgrupadoVM.class));

            if (primerResultado == null) {
                res = (List<CobrosBonosAgrupadoVM>) query.list();
            } else {
                res = (List<CobrosBonosAgrupadoVM>) query.list().stream().skip(primerResultado).limit(Constantes.NUMERO_ELEMENTOS_PAGINA).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            logger.error( "DAOiml.getBonosCobrosAgrupado", ex);
        }
        return res;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteComprasCiudadano40(String idCiudadano) {

        BigDecimal suma = BigDecimal.ZERO;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.eq("idCiudadano", idCiudadano));
            dc.setProjection(Projections.sum("importe"));
            suma = (BigDecimal) dc.getExecutableCriteria(this.currentSession()).uniqueResult();


        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteComprasCiudadano40", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }

        return suma;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getImporteBonificacionesTotal(String idCiudadano) {
        // LA DE PIEDRAFITA ME PARECE LIOSA Y NO LA ENTIENDO

        BigDecimal suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.eq("idCiudadano", idCiudadano));
            dc.add(Restrictions.isNotNull("importeTotalTicket"));//ESTO LO HACE PIEDRAFITA, NO MOLESTA.
            dc.setProjection(Projections.sum("importe"));
            suma = (BigDecimal) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteBonificacionesTotal", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma;
    }

    /**
     * Método que obtiene el total de importes de las recargas comprendidas entre dos fechas.
     * Este método puede dejar fuera de la consulta las recargas realizadas por los usuarios de prueba según una configuración AppConfig.
     *
     * @param fechaInicioCampana
     * @param fechaFinCampana
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public BigDecimal getTotalRecargas(int fechaInicioCampana, int fechaFinCampana) {
        BigDecimal suma = BigDecimal.ZERO;
        try {
            //Usuarios de prueba
            List<String> usuariosPrueba = getUsuariosPrueba();

            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.between("fechaRealizacionInt", fechaInicioCampana, fechaFinCampana));

            if (usuariosPrueba.size() > 0 && Boolean.valueOf(getAppConfigId(AppConfig.CIUDADANOS_PRUEBA_FILTER_OUT).getValor())) {
                dc.add(Restrictions.not(Restrictions.in("idCiudadano", usuariosPrueba)));
            }

            dc.setProjection(Projections.sum("importe"));
            suma = (BigDecimal) dc.getExecutableCriteria(this.currentSession()).uniqueResult();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getTotalRecargas", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma;
    }

    /**
     * Método para listar los usuarios CIUDADANO registrados para pruebas.
     *
     * @return
     */
    private List<String> getUsuariosPrueba() {
        List<String> usuariosPrueba = new ArrayList();
        try {
            String sql = "select idEntidad from Entidad where mail like '%@wdreams.com'";
            Query query = this.currentSession().createQuery(sql);
            query.setReadOnly(true);
            usuariosPrueba = (List<String>) query.list();

        } catch (Exception e) {
            logger.error( "DAOiml.getUsuariosPrueba", e);

        } finally {
            return usuariosPrueba;
        }

    }

    /*
     * Métodos para el informe estadístico por correo electrónico
     *
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int getAltasTotales(Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.le("fechaCreacion", hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> usuariosPrueba = getUsuariosPrueba();
                if (usuariosPrueba.size() > 0) {   //Usuarios de prueba
                    dc.add(Restrictions.not(Restrictions.in("idEntidad", usuariosPrueba)));
                }
            }
            dc.setProjection(Projections.count("documento"));
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAltasTotales", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int getComprasTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.between("fechaRealizacion", desde, hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }
            dc.setProjection(Projections.rowCount());
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComprasTotalesDia", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public float getImporteComprasTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.between("fechaRealizacion", desde, hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.sum("importe"));
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComprasTotalesDia", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.floatValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public float getComprasTotales(Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Compra.class);
            dc.add(Restrictions.le("fechaRealizacion", hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.sum("importe"));
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getComprasTotales", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.floatValue();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public int getAltasDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;

        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Entidad.class);
            dc.add(Restrictions.between("fechaCreacion", desde, hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idEntidad", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.rowCount());
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

//            Query query = currentSession().createQuery(
//                    "select count(descripcion)" +
//                    "FROM Entidad" +
//                    "where DATE(fechaCreacion)between :desde and :hasta");
//            query.setParameter("desde", desde);
//            query.setParameter("hasta", hasta);
////            query.setResultTransformer(new AliasToBeanResultTransformer(Recarga.class));
//            suma = (Number) query.uniqueResult();

            return suma.intValue();
        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getAltasDia", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public float getBonificacionesTotales(Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.le("fechaRealizacion", hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.sum("importe"));
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonificacionesTotales", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.floatValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public float getImporteBonificacionesTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
//            dc.add(Restrictions.eq("fechaCreacion",dayer));
//            dc.setProjection(Projections.count("documento"));
            dc.add(Restrictions.between("fechaRealizacion", desde, hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.sum("importe"));
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getImporteBonificacionesTotalesDia", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.floatValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int getBonificacionesTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut) {
        Number suma = BigDecimal.ZERO;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(Recarga.class);
            dc.add(Restrictions.between("fechaRealizacion", desde, hasta));
            if (ciudadanosPruebaFilterOut) {
                List<String> ciudadanosPrueba = getUsuariosPrueba();
                if (ciudadanosPrueba.size() > 0) {
                    dc.add(Restrictions.not(Restrictions.in("idCiudadano", ciudadanosPrueba)));
                }
            }

            dc.setProjection(Projections.rowCount());
            suma = (Number) dc.getExecutableCriteria(this.currentSession()).uniqueResult();

        } catch (DataAccessException ex) {
            logger.error( "DAOiml.getBonificacionesTotalesDia", ex);
        }
        if (suma == null) {
            suma = BigDecimal.ZERO;
        }
        return suma.intValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean toggleUsuarioState(int u, Boolean state) {

        try {
            Usuario us = this.getUsuarioId(u);

            us.setEnabled(state);
            this.currentSession().update(us);

        } catch (Exception e) {

            logger.error( "DAOiml.toggleUsuarioState", e);
            return false;
        }
        return true;
    }


}







