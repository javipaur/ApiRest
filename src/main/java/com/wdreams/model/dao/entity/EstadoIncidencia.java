/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "EstadosIncidencia")
public class EstadoIncidencia implements Serializable {

    public static final Integer ABIERTA = 1; //mientras no se haya resuelto
    public static final Integer LEIDA = 2;
    public static final Integer TRAMITADA = 3;
    public static final Integer CERRADA = 4;
    public static final Integer CANCELADA = 5;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", length = 20)
    private String nombre;
    
    @OneToMany(mappedBy = "estadoOrigen")
    private Set<TransicionEstadoIncidencia> transiciones;

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

    public Set<TransicionEstadoIncidencia> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Set<TransicionEstadoIncidencia> transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public int hashCode() {
        int hash = 45;
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
        final EstadoIncidencia other = (EstadoIncidencia) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
