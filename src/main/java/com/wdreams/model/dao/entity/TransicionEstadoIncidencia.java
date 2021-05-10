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
@Table(name = "TransicionesEstadoIncidencia")
public class TransicionEstadoIncidencia implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoOrigen", referencedColumnName = "id")
    private EstadoIncidencia estadoOrigen;
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoFin", referencedColumnName = "id")
    private EstadoIncidencia estadoFin;
    @Column(name = "permitido")
    private Boolean permitido;

    public EstadoIncidencia getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EstadoIncidencia estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public EstadoIncidencia getEstadoFin() {
        return estadoFin;
    }

    public void setEstadoFin(EstadoIncidencia estadoFin) {
        this.estadoFin = estadoFin;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }
       
}
