/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "AsociacionesCentro")
public class AsociacionCentro implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idCentro", nullable = false, referencedColumnName = "id")
    private Centro centro;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idCiudadano", nullable = false, referencedColumnName = "id")
    private Entidad ciudadano;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name="tag", length = 5)
    private String tag;

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Entidad getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Entidad ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
   
}
