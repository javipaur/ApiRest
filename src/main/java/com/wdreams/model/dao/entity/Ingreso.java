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
@Table(name = "Ingresos")
public class Ingreso implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idLiquidacionAmbito", nullable = false, referencedColumnName = "id")
    private LiquidacionAmbito liquidacionAmbito;
    
    @Column(name = "numeroIngreso", nullable = false)
    private String numeroIngreso;
    
    @Column(name = "importeIngreso", nullable = false)
    private Double importeIngreso;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaIngreso", nullable = false)
    private Date fechaIngreso;
    
    @Column(name = "entidadFinanciera", nullable = false)
    private String entidadFinanciera;

    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getImporteIngreso() {
        return importeIngreso;
    }

    public void setImporteIngreso(Double importeIngreso) {
        this.importeIngreso = importeIngreso;
    }

    public LiquidacionAmbito getLiquidacionAmbito() {
        return liquidacionAmbito;
    }

    public void setLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        this.liquidacionAmbito = liquidacionAmbito;
    }

    public String getNumeroIngreso() {
        return numeroIngreso;
    }

    public void setNumeroIngreso(String numeroIngreso) {
        this.numeroIngreso = numeroIngreso;
    }
}
