/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "EstadosCiudadano")
public class EstadoCiudadano implements Serializable {

    public static final Integer ACTIVO = 1;
    public static final Integer BLOQUEO_TEMPORAL = 2;
    public static final Integer BLOQUEO_DOCUMENTOS = 3;
    public static final Integer BLOQUEO_FRAUDE = 4;
    public static final Integer BLOQUEO_PADRON = 5;
    public static final Integer BLOQUEO_BAJA = 6;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre", length = 20)
    private String nombre;
    @JsonIgnore
    @OneToMany(mappedBy = "estadoOrigen",fetch = FetchType.LAZY)
    private Set<TransicionEstadoCiudadano> transiciones;
    
    @Column(name = "estadoGeneral")
    private Integer estadoGeneral;
    
     public static class ESTADOS_GENERALES {
        public static final Integer BLOQUEO = 1;
        public static final Integer NO_BLOQUEO = 0;
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

    public Set<TransicionEstadoCiudadano> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Set<TransicionEstadoCiudadano> transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final EstadoCiudadano other = (EstadoCiudadano) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Integer getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(Integer estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

}
