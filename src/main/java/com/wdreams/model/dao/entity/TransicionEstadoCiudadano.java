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
@Table(name = "TransicionesEstadoCiudadano")
public class TransicionEstadoCiudadano implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoOrigen", referencedColumnName = "id")
    private EstadoCiudadano estadoOrigen;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoFin", referencedColumnName = "id")
    private EstadoCiudadano estadoFin;
    
    @Column(name = "permitido")
    private Boolean permitido;

    public EstadoCiudadano getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EstadoCiudadano estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public EstadoCiudadano getEstadoFin() {
        return estadoFin;
    }

    public void setEstadoFin(EstadoCiudadano estadoFin) {
        this.estadoFin = estadoFin;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }

}
