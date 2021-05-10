/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "TransicionesEstadoLiquidacion")
public class TransicionEstadoLiquidacion implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoLiquidacionOrigen", referencedColumnName = "id")
    private EstadoLiquidacion estadoOrigen;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idEstadoLiquidacionFin", referencedColumnName = "id")
    private EstadoLiquidacion estadoFin;
    
    @Column(name = "permitido")
    private Boolean permitido;

    public EstadoLiquidacion getEstadoFin() {
        return estadoFin;
    }

    public void setEstadoFin(EstadoLiquidacion estadoFin) {
        this.estadoFin = estadoFin;
    }

    public EstadoLiquidacion getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EstadoLiquidacion estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }
}
