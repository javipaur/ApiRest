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
@Table(name = "TransicionesEstadoTarjeta")
//@IdClass(TransicionEstadoTarjetaId.class)
public class TransicionEstadoTarjeta implements Serializable {


    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoOrigen", referencedColumnName = "id")
    private EstadoTarjeta estadoOrigen;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoFin", referencedColumnName = "id")
    private EstadoTarjeta estadoFin;
    
    @Column(name = "permitido")
    private Boolean permitido;

    public Integer getEstadoOrigenId() {
        return estadoOrigen.getId();
    }

    public void setEstadoOrigenId(Integer estadoOrigenId) {
        this.estadoOrigen.setId(estadoOrigenId);
    }

    public Integer getEstadoFinId() {
        return estadoFin.getId();
    }

    public void setEstadoFinId(Integer estadoFinId) {
        this.estadoFin.setId(estadoFinId);
    }

    public EstadoTarjeta getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EstadoTarjeta estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public EstadoTarjeta getEstadoFin() {
        return estadoFin;
    }

    public void setEstadoFin(EstadoTarjeta estadoFin) {
        this.estadoFin = estadoFin;
    }

    public boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }

}
