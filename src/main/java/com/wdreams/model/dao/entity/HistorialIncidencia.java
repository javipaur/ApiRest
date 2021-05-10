
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.wdreams.model.dao.Dao;
import com.wdreams.utils.Enviable;
import com.wdreams.utils.Plantillas;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "HistorialesIncidencia")
public class HistorialIncidencia implements Serializable,Comparable<HistorialIncidencia> , Enviable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idIncidencia", nullable = false, referencedColumnName = "id")
    private Incidencia incidencia;
    
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false, referencedColumnName = "id")
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    
    @ManyToOne
    @JoinColumn(name = "idEstadoIncidencia", nullable = false, referencedColumnName = "id")
    private EstadoIncidencia estado;
    
    @Column(name = "notificado")
    private Boolean notificado;
    
    @Column(name = "descripcionTraza")
    private String descripcionTraza;
    
    @Column(name = "descripcionEmail")
    private String descripcionEmail;
    
     @Column(name = "prioridad")
    private String prioridad;

    public HistorialIncidencia() {
    }
    
    public HistorialIncidencia(Incidencia incidencia, User user, Date fecha, EstadoIncidencia estado, String prioridad, String descripcionTraza, String descripcionEmail,Boolean notificado) {
        this.incidencia = incidencia;
        this.user = user;
        this.fecha = fecha;
        this.estado = estado;
        this.notificado = notificado;
        this.descripcionTraza = descripcionTraza;
        this.descripcionEmail = descripcionEmail;
        this.prioridad=prioridad;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
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

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
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

    public String getDescripcionEmail() {
        return descripcionEmail;
    }

    public void setDescripcionEmail(String descripcionEmail) {
        this.descripcionEmail = descripcionEmail;
    }

    @Override
    public int compareTo(HistorialIncidencia o) {
        return (int)(this.fecha.getTime()-this.getFecha().getTime());
    }

    @Override
    public String getEmailEnviable(Dao dao) {
        return this.incidencia.getUsuarioCreador().getUserEmail();
    }

    @Override
    public String getText(Dao dao) throws FileNotFoundException, IOException {
        return new Plantillas(dao).getText(Plantillas.PLANTILLA_CAMBIO_ESTADO_INCIDENCIA)
                .replace("[MENSAJE]", this.getDescripcionEmail())
                .replace("[NOMBRE]", this.incidencia.getUsuarioCreador().getUserNombre())
                .replace("[APELLIDOS]", this.incidencia.getUsuarioCreador().getUserApellidos())
                .replace("[ESTADO]", this.estado.getNombre());
    }

    @Override
    public String getSubject(Dao dao) {
        return "El estado de la incidencia en el sistema de Tarjeta Ciudadana ha cambiado a " + this.getEstado().getNombre();
    }

    @Override
    public List<String> getAdjuntos(Dao dao) {
        return null;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    
}
