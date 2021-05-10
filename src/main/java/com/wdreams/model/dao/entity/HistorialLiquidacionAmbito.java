/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "HitorialesLiquidacionesAmbitos")
public class HistorialLiquidacionAmbito implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idLiquidacionAmbito", nullable = false, referencedColumnName = "id")
    private LiquidacionAmbito liquidacionAmbito;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    
    @Column(name = "descripcionTraza", nullable = false)
    private String descripcionTraza;
    
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false, referencedColumnName = "id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "idEstadoLiquidacion", nullable = false, referencedColumnName = "id")
    private EstadoLiquidacion estadoLiquidacion;

    public String getDescripcionTraza() {
        return descripcionTraza;
    }

    public void setDescripcionTraza(String descripcionTraza) {
        this.descripcionTraza = descripcionTraza;
    }

    public EstadoLiquidacion getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(EstadoLiquidacion estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LiquidacionAmbito getLiquidacionAmbito() {
        return liquidacionAmbito;
    }

    public void setLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        this.liquidacionAmbito = liquidacionAmbito;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
