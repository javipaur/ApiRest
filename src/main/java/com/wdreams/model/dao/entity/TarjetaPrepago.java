/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.model.dao.Dao;
;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"imprimir", "hasCiudadano", "ambitosNoPermitidos", "incidencias"})
@Entity
//@Table(name="Tarjetas")
@DiscriminatorValue(value = "1")
public class TarjetaPrepago extends Tarjeta{
    
    
    
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    public TarjetaPrepago() {
    }
  
    
    public TarjetaPrepago(
            Entidad ciudadano,
            EstadoTarjeta estadoTarjeta,
            int modalidadImpresion, 
            int prioridad, 
            PuestoImpresion 
            puestoImpresion,
            String via, 
            String numero, 
            String piso, 
            String puerta, 
            String cp,
            CentroRecogida centroRecogida) {
        super(ciudadano,estadoTarjeta, modalidadImpresion,prioridad, puestoImpresion, via, numero, piso, puerta, cp,centroRecogida);
        this.saldo = new BigDecimal(0);
    }
    
   

    @Override
    public String getTipoTarjeta() {
        return "Prepago";
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public TarjetaDatosImprimir getImprimir(Dao dao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TarjetaDatosEstado getEstadoActualActiva(Dao dao, HistorialTarjeta historialTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TarjetaDatosEstado getEstadoActualBloqueada(Dao dao, HistorialTarjeta historialTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TarjetaDatosEstado getEstadoActual(Dao dao, HistorialTarjeta historialTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getHasCiudadano() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarIdTarjeta() {
        this.setIdTarjeta(String.format("TD%06X", this.getId()));
    }

   
    
}
