/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author joel
 */
@Entity
@Table(name = "TipoAcciones")
public class TipoAccion implements Serializable{
    
    public static final Integer ALTA=1;
    public static final Integer BAJA=2;
    public static final Integer MODIFICACION=3;
    public static final Integer EJECUCION=4;
    public static final Integer BLOQUEO=5;
    public static final Integer DESBLOQUEO=6;
    public static final Integer ENVIO=7;
    public static final Integer IMPRIMIR=8;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", length = 20)
    private String nombre;

    public TipoAccion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoAccion() {
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

    @Override
    public int hashCode() {
        int hash = 3;
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
        final TipoAccion other = (TipoAccion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
    
     
}
