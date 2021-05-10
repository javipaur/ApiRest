/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Index;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"liquidacion"})
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operacion extends Uso implements Serializable{

   /* public static String OPERACION_ONLINE_CONFIRMADA = "0";
    public static String OPERACION_ONLINE_SIN_CONFIRMAR = "1";
    public static String OPERACION_CONFIRMAR = "2";
    public static String OPERACION_OFFLINE = "3";*/
    
    public static Integer OPERACION = 0;
    public static Integer OPERACION_ANULAR = 1;
    public static Integer OPERACION_DEVOLUCION_SALDO = 2;
    public static Integer RECARGA_SALDO_VIRTUAL = 3;


    @Index(name = "importeIndex")
    @Column(name = "importe", nullable = false)
    protected BigDecimal importe;
    
    @Column(name = "saldoTarjeta", nullable = false)
    protected BigDecimal saldoTarjeta;

    @ManyToOne
    @JoinColumn(name = "idLiquidacion", referencedColumnName = "id")
    private Liquidacion liquidacion;

    public Operacion(Liquidacion liquidacion, Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, BigDecimal importe, BigDecimal saldoTarjeta, Integer idOperacionAnulada,Aviso aviso) {
        super(fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, idOperacionAnulada,aviso);
        this.importe = importe;
        this.saldoTarjeta = saldoTarjeta;
        this.liquidacion = liquidacion;
        this.idOperacionAnulada = idOperacionAnulada;
    }

    public Operacion() {
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getSaldoTarjeta() {
        return saldoTarjeta;
    }

    public void setSaldoTarjeta(BigDecimal saldoTarjeta) {
        this.saldoTarjeta = saldoTarjeta;
    }

    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public Integer getIdOperacionAnulada() {
        return idOperacionAnulada;
    }

    public void setIdOperacionAnulada(Integer idOperacionAnulada) {
        this.idOperacionAnulada = idOperacionAnulada;
    }
    
    public class OperacionDatosExport{
        
        public Date getFechaRealizacion(){
            return Operacion.this.fechaRealizacion;
        }
        
        public String getIdUso(){
            return Operacion.this.getIdUso();
        }
        
        public String getNumOperacion(){
            return Operacion.this.getNumeroOperacion();
        }
        
        public String getAmbito(){
            return Operacion.this.getCentro().getAmbito().getNombre();
        }
        
        public String getCentro(){
            return Operacion.this.getCentro().getNombre();
        }
        
        public BigDecimal getImporte(){
            return Operacion.this.getImporte();
        }
        
        public BigDecimal getSaldo(){
            return Operacion.this.getSaldoTarjeta();
        }
        
    }

    public class OperacionDatosExportSubvenciones{

        public Date getFechaRealizacion(){
            return Operacion.this.fechaRealizacion;
        }

        public String getNumOperacion(){
            return Operacion.this.getNumeroOperacion();
        }

        public BigDecimal getImporte(){
            return Operacion.this.getImporte();
        }

        public String getCentro(){
                if(Operacion.this.getCentro() == null)
                    return "";
                return Operacion.this.getCentro().getNombre();}
    }
}
