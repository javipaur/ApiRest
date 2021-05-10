/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.wdreams.model.dao.Dao;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;

import com.wdreams.utils.DateUtils;
import com.wdreams.utils.LogUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "prerecargassaldovirtual")
public class PreRecargaSaldoVirtual extends Operacion implements Cloneable {

    private static Logger loggerInfo = Logger.getLogger("PreRecargaSaldoVirtual");

    public PreRecargaSaldoVirtual() {
    }

    public PreRecargaSaldoVirtual(Liquidacion liquidacion, Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, BigDecimal importe, BigDecimal saldoTarjeta, Integer idOperacionAnulada, Aviso aviso) {
        super(liquidacion, fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, importe, saldoTarjeta, idOperacionAnulada, aviso);
    }

    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            //System.out.println(" no se puede duplicar");
            LogUtils.escribeLogRow(loggerInfo, "Compra:clone", "no se puede duplicar.");
        }
        return obj;
    }

    public String getNumeroOperacionSiguienteRecarga(Dao dao) {
        Integer numeroARestar = 1;
        String numeroOpCalculado = "";
        AppConfig appConfig = dao.getAppConfigId(AppConfig.ULTIMO_NUM_OPERACION_RECARGA_NEG);;
        do {
            numeroOpCalculado = String.valueOf(Integer.parseInt(appConfig.getValor()) - numeroARestar);
            numeroARestar = numeroARestar + 1;
        } while (dao.existeNumeroOperacionRecarga(numeroOpCalculado));
        appConfig.setValor(numeroOpCalculado);
        dao.setAppConfigId(appConfig);
        return numeroOpCalculado;
    }

    @Override
    public void asignarIdUso() {
        this.asignarIdUso("R");
    }

    public class RecargaDatosExport {

        public String getIdUso() {
            return PreRecargaSaldoVirtual.this.getIdUso();
        }

        public String getNumeroOperacion() {
            return PreRecargaSaldoVirtual.this.getNumeroOperacion();
        }

        public String getCiudadano() {
            return PreRecargaSaldoVirtual.this.getTarjeta().getEntidad().getIdCiudadano();
        }

        public String getTarjeta() {
            return PreRecargaSaldoVirtual.this.getTarjeta().getIdTarjeta();
        }

        public String getUid() {
            return PreRecargaSaldoVirtual.this.getTarjeta().getUid();
        }

        public String getAmbito() {
            return PreRecargaSaldoVirtual.this.getCentro().getAmbito().getNombre();
        }

        public String getCentro() {
            return PreRecargaSaldoVirtual.this.getCentro().getNombre();
        }

        public Date getFechaRealizacion() {
            return PreRecargaSaldoVirtual.this.getFechaRealizacion();
        }

        public Integer getAviso() {
            return PreRecargaSaldoVirtual.this.getAviso().getNumero();
        }

        public BigDecimal getImporte() {
            return PreRecargaSaldoVirtual.this.getImporte();
        }
    }

    public static class RecargaDatosWS {

        private PreRecargaSaldoVirtual recarga;

        public RecargaDatosWS() {
        }

        public RecargaDatosWS(PreRecargaSaldoVirtual compra) {
            this.recarga = compra;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNumero() {
            return recarga.getNumeroOperacion();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdCiudadano() {
            return recarga.getTarjeta().getEntidad().getIdCiudadano();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getUid() {
            return recarga.getTarjeta().getUid();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public BigDecimal getImporte() {
//            return recarga.getImporte();
            return new BigDecimal(recarga.getImporte().toString() + "0");
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getFecha() {
            return DateUtils.yyyyMMddHHmmss.format(recarga.getFechaRealizacion());
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getApellidos() {
            return recarga.getTarjeta().getEntidad().getApellidos();
        }
        
         @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNombreCentro() {
            return recarga.getCentro() == null ? "" : recarga.getCentro().getNombre();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdDispositivo() {
            return recarga.getDispositivo() == null ? "" : recarga.getDispositivo().getIdDispositivo();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getDescripcion() {
            return recarga.getDescripcion();
        }
    }
}
