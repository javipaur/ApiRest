/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Type;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "TiposCiudadanoDocumento")
@DataTransferObject
public class TipoCiudadanoDocumento implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", length=50)
    private String nombre;
    
    @ManyToOne()
    @JoinColumn(name = "idTipoCiudadano", nullable = true, referencedColumnName = "id")
    private TipoCiudadano tipoCiudadano;
    
    @Column(name = "descripcion")
    @Type(type="text")
    private String descripcion;
    
    @Column(name = "adjunto")
    private Boolean adjunto;
    
    public Boolean getAdjunto() {
        return adjunto!=null && adjunto ;
    }

    public void setAdjunto(Boolean adjunto) {
        this.adjunto = adjunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCiudadano getTipoCiudadano() {
        return tipoCiudadano;
    }

    public void setTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.tipoCiudadano = tipoCiudadano;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoCiudadanoDocumento other = (TipoCiudadanoDocumento) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
