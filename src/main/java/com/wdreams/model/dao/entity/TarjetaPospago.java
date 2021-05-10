/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.wdreams.model.dao.Dao;

import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
//@Table(name = "Tarjetas")
@DiscriminatorValue(value = "0")
public class TarjetaPospago extends Tarjeta {

    public TarjetaPospago() {
    }

    public TarjetaPospago(Entidad ciudadano,EstadoTarjeta estadoTarjeta, int modalidadImpresion, int prioridad, PuestoImpresion puestoImpresion,String via, String numero, String piso, String puerta, String cp, CentroRecogida centroRecogida) {
        super(ciudadano,estadoTarjeta, modalidadImpresion, prioridad, puestoImpresion,via, numero, piso, puerta, cp, centroRecogida);
    }

   /* public TarjetaPospago(Ciudadano ciudadano,  int modalidadImpresion, int prioridad, PuestoImpresion puestoImpresion,String via, String numero, String piso, String puerta, String cp) {
        super(ciudadano, modalidadImpresion, prioridad, puestoImpresion, via, numero, piso, puerta, cp);
    }*/

    @Override
    public String getTipoTarjeta() {
        return "Pospago";
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
        this.setIdTarjeta(String.format("TC%06X", this.getId()));
    }
}
