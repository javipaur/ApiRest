/**
 * Clase que implementa la clase abstracta Tarjeta y que mapea aquellos registros de la bbdd que se corresponden con
 * lo que nosotros llamamos Tarjeta Virtual.
 *
 * Es un copia y pega de las prepago, adaptada a esta entidad, con el discriminador de tipo = 3
 *
 * @author Alberto Piedrafita (16/04/2020)
 * @version 1.0
 */

package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.model.dao.Dao;


import java.math.BigDecimal;
import javax.persistence.*;


@JsonIgnoreProperties({"imprimir", "hasCiudadano", "ambitosNoPermitidos", "incidencias"})
@Entity
@DiscriminatorValue(value = "3")
//@SecondaryTable(name="entidades", pkJoinColumns = {
//        @PrimaryKeyJoinColumn(
//                name="idEntidad", referencedColumnName="id")
//})
public class TarjetaVirtual  extends Tarjeta{

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;
//    @Column(name = "saldo", nullable = false)
//    private BigDecimal saldo;

    /**
     * Con el saldo intento hacer una magia a trav�s de hibernate, que promete mucho y no s� hasta d�nde me puede ayudar
     * (si no lo intento no lo s�). Si este comentario sigue en pie cuando recibas este proyecto tan chulo, significa
     * que funciona.
     */
//    @Column(name="saldoVirtual", table="Entidades")
//    private BigDecimal saldo;

    public TarjetaVirtual() {
    }


    public TarjetaVirtual(
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
//        this.saldo = new BigDecimal(0);
//        this.setSaldo(new BigDecimal(0));//Lo meto con el setter para que apunte a la tabla de usuario y no a la de tarjeta
        //Pruebo con 100 para ver si actualizo el dato
        this.setSaldo(BigDecimal.ZERO);//Lo meto con el setter para que apunte a la tabla de usuario y no a la de tarjeta
    }



    @Override
    public String getTipoTarjeta() {
        return "Virtual";
    }

    public BigDecimal getSaldo() {
        return this.getEntidad().getSaldoVirtual();
    }

    public void setSaldo(BigDecimal saldo) {
        super.getEntidad().setSaldoVirtual(saldo);
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
        this.setIdTarjeta(String.format("TV%06X", this.getId()));
    }



}
