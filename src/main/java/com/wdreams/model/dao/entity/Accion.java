/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import javax.persistence.*;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.LogUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author joel
 */
@Entity
@Table(name = "Acciones")
public class Accion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idAccion", nullable = false)
    private String idAccion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idTipoAccion", nullable = false, referencedColumnName = "id")
    private TipoAccion tipoAccion;

    @ManyToOne
    @JoinColumn(name = "idTipoEntidad", nullable = false, referencedColumnName = "id")
    private TipoEntidad tipoEntidad;

    @Column(name = "idObjeto", nullable = true)
    private Integer idObjeto;

    @Column(name = "idObjetoString", nullable = true)
    private String idObjetoString;

    @ManyToOne
    @JoinColumn(name = "idUserAccion", nullable = true, referencedColumnName = "id")
    private User userAccion;

//    @ManyToOne
//    @JoinColumn(name = "idUser", referencedColumnName = "id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "idEntidad", referencedColumnName = "id")
//    private Entidad ciudadano;
//
//    @ManyToOne
//    @JoinColumn(name = "idAmbito", referencedColumnName = "id")
//    private Ambito ambito;
//
//    @ManyToOne
//    @JoinColumn(name = "idCentro", referencedColumnName = "id")
//    private Centro centro;
//
//    @ManyToOne
//    @JoinColumn(name = "idDispositivo", referencedColumnName = "id")
//    private Dispositivo dispositivo;
//
//    @ManyToOne
//    @JoinColumn(name = "idTarjeta", referencedColumnName = "id")
//    private Tarjeta tarjeta;
//
//    @ManyToOne
//    @JoinColumn(name = "idIncidencia", referencedColumnName = "id")
//    private Incidencia incidencia;
//
//    @ManyToOne
//    @JoinColumn(name = "idTipoCiudadano", referencedColumnName = "id")
//    private TipoCiudadano tipoCiudadano;
//
//    @ManyToOne
//    @JoinColumn(name = "idPerfil", referencedColumnName = "id")
//    private Perfil perfil;
//    
//    @ManyToOne
//    @JoinColumn(name = "idAcceso", referencedColumnName = "id")
//    private Acceso acceso;
//
//    @ManyToOne
//    @JoinColumn(name = "idCompra", referencedColumnName = "id")
//    private Compra compra;
//
//    @ManyToOne
//    @JoinColumn(name = "idRecarga", referencedColumnName = "id")
//    private Recarga recarga;
    @Transient
    private Dao dao;

    @Transient
    private static Logger loggerBack = Logger.getLogger("ciudadanaErrorBack");

    public Accion() {
    }

    //Constructor con todos los parametros
    public Accion(Date fecha, TipoAccion tipoAccion, TipoEntidad tipoEntidad, User userAccion, Object objeto, String descripcion) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        this.idAccion = "";
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipoAccion = tipoAccion;
        this.tipoEntidad = tipoEntidad;
        this.userAccion = userAccion;

        this.idObjeto = (Integer) objeto.getClass().getMethod("getId").invoke(objeto);
        this.idObjetoString = (String) objeto.getClass().getMethod(tipoEntidad.getMetodoId()).invoke(objeto);
    }

    public static void registrarAccion(Dao dao, Accion accion) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        dao.altaAccion(accion);
    }

    /*
     - Metodo simple:
            
     > Fecha: ahora.
     > UserAccion: el usuario identificado.
     > TipoAccion y TipoEntidad: se pasa su id, no el objeto.
     */
    public static void registrarAccionSimple(Dao dao, Integer tipoAccionID, Integer tipoEntidadID, Object objeto, String descripcion) {
        try {

            Accion accion = new Accion(
                    new Date(),
                    dao.getTipoAccionId(tipoAccionID),
                    dao.getTipoEntidadId(tipoEntidadID),
                    dao.getUserUsername(LogUtils.getUserName()),
                    objeto,
                    descripcion
            );

            dao.altaAccion(accion);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

            LogUtils.escribeLog(loggerBack, "Accion.registrarAccionSimple", ex);
        }
    }

    /*
     - Metodo para los procesos:
    
     > Similar al simple, pero en este caso el usario viene como parametro.
     */
    public static void registrarAccionSimpleUsername(Dao dao, Integer tipoAccionID, Integer tipoEntidadID, String username, Object objeto, String descripcion) {
        try {

            Accion accion = new Accion(
                    new Date(),
                    dao.getTipoAccionId(tipoAccionID),
                    dao.getTipoEntidadId(tipoEntidadID),
                    dao.getUserUsername(username),
                    objeto,
                    descripcion
            );

            dao.altaAccion(accion);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

            LogUtils.escribeLog(loggerBack, "Accion.registrarAccionSimpleUserName", ex);
        }
    }

    public String getUrl() {
        return tipoEntidad.getUrl().replace("{idObjeto}", idObjetoString);
    }

//    private Object getObjeto() throws ClassNotFoundException{
//        return dao.getObjectId(tipoEntidad.getClase(), idObjeto);
//    }
//    public String getIdObjetoString() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//        Object objeto = getObjeto();
//        return (String) (objeto.getClass().getMethod(tipoEntidad.getMetodoId()).invoke(objeto));
//    }
//    public String getUrl() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//        return tipoEntidad.getUrl().replace("{idObjeto}", getIdObjetoString());
//    }
    public void asignarIdAccion() {
        idAccion = String.format("%08X", 1700000000l + id);
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public Integer getId() {
        return id;
    }

    public String getIdAccion() {
        return idAccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public String getIdObjetoString() {
        return idObjetoString;
    }

    public User getUserAccion() {
        return userAccion;
    }

    public class VentasDatosExport {

        public String getIdAccion() {
            return Accion.this.getIdAccion();
        }

        public String getUsuarioCreador() {
            return (Accion.this.getUserAccion() == null ? "" : Accion.this.getUserAccion().getUsername());
        }

        public Date getFecha() {
            return Accion.this.getFecha();
        }

        public String getAccionEntidad() {
            return Accion.this.getTipoAccion().getNombre();
        }

        public String getTraza() {
            return Accion.this.getDescripcion();
        }

        public String getIdObjetoString() {
            return Accion.this.getIdObjetoString();
        }

//        public String getUsuario() {
//            return (Accion.this.getUser() == null ? "" : Accion.this.getUser().getUsername());
//        }
//
//        public String getTarjeta() {
//            return (Accion.this.getTarjeta() == null ? "" : Accion.this.getTarjeta().getIdTarjeta());
//        }
//
//        public String getDispositivo() {
//            return (Accion.this.getDispositivo() == null ? "" : Accion.this.getDispositivo().getIdDispositivo());
//        }
//
//        public String getCentro() {
//            return (Accion.this.getCentro() == null ? "" : Accion.this.getCentro().getIdCentro());
//        }
//        public String getTraza() {
//            return Accion.this.getDescripcion();
//        }
    }

}
