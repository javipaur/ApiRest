/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;



/**
 *
 * @author rosa
 */
public class LiquidacionAmbitoDetalle {
    
    private LiquidacionAmbito liquidacionAmbito;
    
    private Long numeroIngresos;
    private Double importeComision;
    private Double importeIngresos;
    private Double estadoContable;

    public LiquidacionAmbitoDetalle(){}
    
    public Double getImporteIngresos() {
        return importeIngresos;
    }

    public void setImporteIngresos(Double importeIngresos) {
        this.importeIngresos = importeIngresos;
    }

    public LiquidacionAmbito getLiquidacionAmbito() {
        return liquidacionAmbito;
    }

    public void setLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito) {
        this.liquidacionAmbito = liquidacionAmbito;
    }

    public Long getNumeroIngresos() {
        return numeroIngresos;
    }

    public void setNumeroIngresos(Long numeroIngresos) {
        this.numeroIngresos = numeroIngresos;
    }

    public Double getEstadoContable() {
        return estadoContable;
    }

    public void setEstadoContable(Double estadoContable) {
        this.estadoContable = estadoContable;
    }

    public Double getImporteComision() {
        return importeComision;
    }

    public void setImporteComision(Double importeComision) {
        this.importeComision = importeComision;
    }
}
