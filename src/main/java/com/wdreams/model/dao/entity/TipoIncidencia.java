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
@Table(name = "TiposIncidencia")
public class TipoIncidencia implements Serializable {
    
    public static final Integer TARJETA = 1;
    public static final Integer COMPRA = 2;
    public static final Integer RECARGA = 3;
    public static final Integer ACCESO = 4; 
    public static final Integer USO_BONO = 5;
    public static final Integer ERROR_CIUDADANO = 6;
    public static final Integer ERROR_TARJETA = 7;  
    public static final Integer OTRAS = 8;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", length = 20)
    private String nombre;

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
    
     @Override
    public int hashCode() {
        int hash = 65;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final TipoIncidencia other = (TipoIncidencia) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
