/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.DateUtils;
import com.wdreams.utils.LogUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"usuario", "aviso", "numeroOperacionSiguienteCompra"})
@Entity
@Table(name = "Compras")
public class Compra extends Operacion implements Cloneable {

    private static Logger loggerInfo = Logger.getLogger("Compra");
    @Column(name = "indiceDeCobro", nullable = true)
    protected BigDecimal indiceDeCobro;
    protected BigDecimal importeOriginal;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idDescuento", nullable = true, referencedColumnName = "id")
    protected Descuento descuento;


    @Column(name = "diaHoraPosted", nullable = false)
    protected Date diaHoraPosted;
    @Column(name = "comercioId", nullable = false)
    protected Integer comercioId;
    @Column(name = "comercioNombre", nullable = false)
    protected String comercioNombre;
    @Column(name = "idCiudadano", nullable = false)
    protected String idCiudadano;
    @Column(name = "importeTotalTicket", nullable = false)
    protected BigDecimal importeTotalTicket;
    @Column(name = "ticketUrl", nullable = false)
    protected String ticketUrl;

    public Compra(Liquidacion liquidacion, Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, BigDecimal importe, BigDecimal importeOriginal, BigDecimal saldoTarjeta, BigDecimal indiceDeCobro, Descuento descuento, Integer idOperacionAnulada, Aviso aviso) {
        super(liquidacion, fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, importe, saldoTarjeta, idOperacionAnulada, aviso);
        this.indiceDeCobro = indiceDeCobro;
        this.descuento = descuento;
        this.importeOriginal = importeOriginal;
    }


    @ManyToOne()
    @JoinColumn(name = "idLiquidacionSaldo")
    private LiquidacionSaldo liquidacionSaldo;

    public LiquidacionSaldo getLiquidacionSaldo() {
        return liquidacionSaldo;
    }

    public void setLiquidacionSaldo(LiquidacionSaldo liquidacionSaldo) {
        this.liquidacionSaldo = liquidacionSaldo;
    }

    public Compra() {}

    public BigDecimal getIndiceDeCobro() {
        return indiceDeCobro;
    }

    public BigDecimal getIndiceDeCobroDecimal() {
        return (indiceDeCobro != null ? indiceDeCobro.divide(new BigDecimal(100)) : new BigDecimal(1));
    }

    public Date getDiaHoraPosted() {
        return diaHoraPosted;
    }

    public void setDiaHoraPosted(Date diaHoraPosted) {
        this.diaHoraPosted = diaHoraPosted;
    }

    public Integer getComercioId() {
        return comercioId;
    }

    public void setComercioId(Integer comercioId) {
        this.comercioId = comercioId;
    }

    public String getComercioNombre() {
        return comercioNombre;
    }

    public void setComercioNombre(String comercioNombre) {
        this.comercioNombre = comercioNombre;
    }

    public String getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
    }

    public BigDecimal getImporteTotalTicket() {
        return importeTotalTicket;
    }

    public void setImporteTotalTicket(BigDecimal importeTotalTicket) {
        this.importeTotalTicket = importeTotalTicket;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public void setIndiceDeCobro(BigDecimal indiceDeCobro) {
        this.indiceDeCobro = indiceDeCobro;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
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

    public String getNumeroOperacionSiguienteCompra(Dao dao) {
        Integer numeroARestar = 1;
        String numeroOpCalculado = "";
        AppConfig appConfig = dao.getAppConfigId(AppConfig.ULTIMO_NUM_OPERACION_COMPRA_NEG);;
        do {
            numeroOpCalculado = String.valueOf(Integer.parseInt(appConfig.getValor()) - numeroARestar);
            numeroARestar = numeroARestar + 1;
        } while (dao.existeNumeroOperacionCompra(numeroOpCalculado));
        appConfig.setValor(numeroOpCalculado);
        dao.setAppConfigId(appConfig);
        return numeroOpCalculado;
    }



    public BigDecimal getImporteOriginal() {
        return importeOriginal;
    }

    public void setImporteOriginal(BigDecimal importeOriginal) {
        this.importeOriginal = importeOriginal;
    }

    @Override
    public void asignarIdUso() {
        this.asignarIdUso("C");
    }

    public BigDecimal getImporteDescontado() {
        return this.importeOriginal.subtract(this.importe);
    }

    public class CompraDatosExport {

        public String getIdUso() {
            return Compra.this.getIdUso();
        }

        public String getNumeroOperacion() {
            return Compra.this.getNumeroOperacion();
        }

        public String getEntidad() {
            return Compra.this.getTarjeta().getEntidad().getIdCiudadano();
        }

        public String getTarjeta() {
            return Compra.this.getTarjeta().getIdTarjeta();
        }

        public String getUid() {
            return Compra.this.getTarjeta().getUid();
        }

        public String getAmbito() {
            return Compra.this.getCentro().getAmbito().getNombre();
        }

        public String getCentro() {
            return Compra.this.getCentro().getNombre();
        }

        public Date getRealizacion() {
            return Compra.this.getFechaRealizacion();
        }

        public BigDecimal getImporteOriginal() {
            return Compra.this.getImporteOriginal();
        }

        public Double getPorcentajeCobro() {
            return Compra.this.getIndiceDeCobroDecimal().doubleValue();
        }

        public BigDecimal getImporteDescontado() {
            return Compra.this.getImporteDescontado();
        }

        public String getDescuento() {
            return (Compra.this.getDescuento() != null ? Compra.this.getDescuento().getIdDescuento() : "");
        }

        public Integer getAviso() {
            return Compra.this.getAviso().getNumero();
        }

        public BigDecimal getImporte() {
            return Compra.this.getImporte();
        }
    }

    public static class CompraDatosWS {

        /**
         * Ahora piden incluir el �mbito y el centro
         */
        private String ambitoNombre;
        private int ambitoId;
        private String centroNombre;
        private int centroId;

        public String getAmbitoNombre() {
            return ambitoNombre;
        }

        public void setAmbitoNombre(String ambitoNombre) {
            this.ambitoNombre = ambitoNombre;
        }

        public int getAmbitoId() {
            return ambitoId;
        }

        public void setAmbitoId(int ambitoId) {
            this.ambitoId = ambitoId;
        }

        public String getCentroNombre() {
            return centroNombre;
        }

        public void setCentroNombre(String centroNombre) {
            this.centroNombre = centroNombre;
        }

        public int getCentroId() {
            return centroId;
        }

        public void setCentroId(int centroId) {
            this.centroId = centroId;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public Boolean getBySaldoVirtual() {
//            return bySaldoVirtual;
            return false;//Por el momento hardcodeado
        }

        public void setBySaldoVirtual(Boolean bySaldoVirtual) {
            this.bySaldoVirtual = bySaldoVirtual;
        }

        private Boolean bySaldoVirtual;









        private Compra compra;

        public CompraDatosWS() {
        }

        public CompraDatosWS(Compra compra) {
            this.compra = compra;
            if(compra.getCentro() != null) {
                this.centroId = compra.getCentro().getId();
                this.centroNombre = compra.getCentro().getNombre();
                this.ambitoId = compra.getCentro().getAmbito().getId();
                this.ambitoNombre = compra.getCentro().getAmbito().getNombre();
            }
        }


        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNumero() {
            return compra.getNumeroOperacion();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdCiudadano() {
            return compra.getTarjeta().getEntidad().getIdCiudadano();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getUid() {
            return compra.getTarjeta().getUid();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public BigDecimal getImporte() {
//            return compra.getImporte();
            return new BigDecimal(compra.getImporte().toString() + "0");
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getFecha() {
            return DateUtils.yyyyMMddHHmmss.format(compra.getFechaRealizacion());
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getApellidos(){
            return compra.getTarjeta().getEntidad().getApellidos();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public BigDecimal getIndiceCobro() {
//            return compra.getIndiceDeCobroDecimal();
            return new BigDecimal(compra.getIndiceDeCobroDecimal().toString() + "0");
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNombreCentro() {
            return compra.getCentro() == null ? "" : compra.getCentro().getNombre();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdDispositivo() {
            return compra.getDispositivo() == null ? "" : compra.getDispositivo().getIdDispositivo();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getDescripcion() {
            return compra.getDescripcion();
        }


//        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
//        public String getDescripcion() {
//            return compra.getDescripcion();
//        }
//
//        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
//        public String getDescripcion() {
//            return compra.getDescripcion();
//        }


    }

}
