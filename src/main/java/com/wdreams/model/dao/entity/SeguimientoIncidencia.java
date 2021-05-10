/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author joel
 */
@Entity
@Table(name = "SeguimientoIncidencia")
public class SeguimientoIncidencia implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idIncidencia", referencedColumnName = "id")
    private Incidencia incidencia;
    
    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User user;
    
    @Column(name="anotacion")
    private String anotacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    public SeguimientoIncidencia() {
    }

    
    public SeguimientoIncidencia(Incidencia incidencia, User user, String anotacion, Date fecha) {
        this.incidencia = incidencia;
        this.user = user;
        this.anotacion = anotacion;
        this.fecha = fecha;
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

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }
    
    
}
