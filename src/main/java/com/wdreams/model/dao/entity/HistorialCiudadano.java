/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.model.dao.Dao;
import com.wdreams.utils.Enviable;
import com.wdreams.utils.Plantillas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties(value = { "entidad", "user", "ciudadano", "adjuntos" })
@Entity
@Table(name = "HistorialesCiudadano")
public class HistorialCiudadano implements Serializable, Enviable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idEntidad", nullable = false, referencedColumnName = "id")
    private Entidad entidad;
    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @ManyToOne
    @JoinColumn(name = "idEstadoCiudadano", nullable = false, referencedColumnName = "id")
    private EstadoCiudadano estado;
    @Column(name = "notificado")
    private Boolean notificado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaNotificado")
    private Date fechaNotificado;
    @Column(name = "descripcionTraza")
    private String descripcionTraza;
    @Column(name = "descripcionMail")
    private String descripcionMail;
    @Column(name = "notificable")
    private Boolean notificable;

    public HistorialCiudadano() {
    }

//    @Column(name = "descripcionEmail")
//     private String descripcionEmail;*/
    public HistorialCiudadano(Entidad ciudadano, User user, EstadoCiudadano estado, String descripcionTraza) {
        this.entidad = ciudadano;
        this.user = user;
        this.fecha = new Date();
        this.estado = estado;
        this.notificado = false;
        this.descripcionTraza = descripcionTraza;
    }

    public HistorialCiudadano(Entidad ciudadano, User user, EstadoCiudadano estado, String descripcionTraza, String descripcionMail, Boolean notificable) {
        this.entidad = ciudadano;
        this.user = user;
        this.estado = estado;
        this.descripcionTraza = descripcionTraza;
        this.descripcionMail = descripcionMail;
        this.notificable = notificable;
        this.fecha = new Date();
        this.notificado = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Entidad getCiudadano() {
        return entidad;
    }

    public void setCiudadano(Entidad ciudadano) {
        this.entidad = ciudadano;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoCiudadano getEstado() {
        return estado;
    }

    public void setEstado(EstadoCiudadano estado) {
        this.estado = estado;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public String getDescripcionTraza() {
        return descripcionTraza;
    }

    public void setDescripcionTraza(String descripcionTraza) {
        this.descripcionTraza = descripcionTraza;
    }

    /* public String getDescripcionEmail() {
     return descripcionEmail;
     }

     public void setDescripcionEmail(String descripcionEmail) {
     this.descripcionEmail = descripcionEmail;
     }*/
    @Override
    public String getEmailEnviable(Dao dao) {
        return entidad.getEmailEnviable(dao);
//return "Su estado en el sistema de Tarjeta Ciudadana ha cambiado a " + this.getEstado().getNombre()+"<br/>Información del operador:<br/>"+this.descripcionMail;
    }

    @Override
    public String getText(Dao dao) throws FileNotFoundException, IOException {
        return new Plantillas(dao).getText(Plantillas.PLANTILLA_CAMBIO_ESTADO_CIUDADANO)
                .replace("[MENSAJE]", this.descripcionMail)
                .replace("[NOMBRE]", this.entidad.getNombre())
                .replace("[APELLIDOS]", this.entidad.getApellidos())
                .replace("[ESTADO]", this.estado.getNombre());
    }

    @Override
    public String getSubject(Dao dao) {
        return "El estado de su cuenta en el sistema Tarjeta Ciudadana ha cambiado a " + this.getEstado().getNombre();
    }

    @Override
    public List<String> getAdjuntos(Dao dao) {
        return null;
    }

    public String getDescripcionMail() {
        return descripcionMail;
    }

    public void setDescripcionMail(String descripcionMail) {
        this.descripcionMail = descripcionMail;
    }

    public Date getFechaNotificado() {
        return fechaNotificado;
    }

    public void setFechaNotificado(Date fechaNotificado) {
        this.fechaNotificado = fechaNotificado;
    }

    public Boolean isNotificable() {
        return notificable;
    }

    public void setNotificable(Boolean notificable) {
        this.notificable = notificable;
    }

}
