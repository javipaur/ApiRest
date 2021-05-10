/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"transiciones", "estadoGeneral"})
@Entity
@Table(name = "EstadosTarjeta")
public class EstadoTarjeta implements Serializable {

    public static final Integer PENDIENTE_IMPRIMIR = 1;
    public static final Integer IMPRIMIENDO = 2;
    public static final Integer PENDIENTE_ACTIVAR = 3;
    public static final Integer ACTIVA = 4;
    public static final Integer BLOQUEO_PADRON = 5;
    public static final Integer BLOQUEO_ROBO = 6;
    public static final Integer BLOQUEO_PERDIDA = 7;
    public static final Integer BLOQUEO_DETERIORO = 8;
    public static final Integer BLOQUEO_FRAUDE = 9;
    public static final Integer BLOQUEO_BAJA = 10;
    public static final Integer BLOQUEO_TEMPORAL = 11;
//    public static final Integer BLOQUEO_FINANCIERO = 12;
    public static final Integer BLOQUEO_USUARIO_BLOQUEADO = 12;

    public static class ESTADOS_GENERALES {

        public static final Integer BLOQUEO = 1;
        public static final Integer NO_BLOQUEO = 0;
    }
    
    public static class ESTADOS_BIT {

        public static final Integer LISTA_NEGRA = 1;
        public static final Integer LISTA_BLANCA = 2;
    }

    public EstadoTarjeta() {
    }

    public EstadoTarjeta(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estadoGeneral")
    private Integer estadoGeneral;

    @OneToMany(mappedBy = "estadoOrigen")
    private Set<TransicionEstadoTarjeta> transiciones;

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

    public Set<TransicionEstadoTarjeta> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Set<TransicionEstadoTarjeta> transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final EstadoTarjeta other = (EstadoTarjeta) obj;
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
