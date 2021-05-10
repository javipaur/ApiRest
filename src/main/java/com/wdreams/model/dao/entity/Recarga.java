/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wdreams.model.dao.Dao;
import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Recargas")
public class Recarga extends Operacion implements Cloneable {


    public Boolean isEsRecargaSaldoVirtual() {
        return esRecargaSaldoVirtual;
    }

    public void setEsRecargaSaldoVirtual(Boolean esRecargaSaldoVirtual) {
        this.esRecargaSaldoVirtual = esRecargaSaldoVirtual;
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

    @Column(name = "esRecargaSaldoVirtual", columnDefinition = "boolean default false", nullable = true)
    @ColumnDefault("false")
    protected Boolean esRecargaSaldoVirtual = false;

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



    public Recarga() {
    }

    public Recarga(Liquidacion liquidacion, Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, BigDecimal importe, BigDecimal saldoTarjeta, Integer idOperacionAnulada, Aviso aviso) {
        super(liquidacion, fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, importe, saldoTarjeta, idOperacionAnulada, aviso);
    }

    public Recarga(Liquidacion liquidacion, Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion,
                   String numeroOperacion, BigDecimal importe, BigDecimal saldoTarjeta, Integer idOperacionAnulada, Aviso aviso, boolean recargaVirtual) {
        super(liquidacion, fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, importe, saldoTarjeta, idOperacionAnulada, aviso);
        this.esRecargaSaldoVirtual = recargaVirtual;
    }

    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            //System.out.println(" no se puede duplicar");
           ex.getMessage();

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
            return Recarga.this.getIdUso();
        }

        public String getNumeroOperacion() {
            return Recarga.this.getNumeroOperacion();
        }

        public String getCiudadano() {
            return Recarga.this.getTarjeta().getEntidad().getIdCiudadano();
        }

        public String getTarjeta() {
            return Recarga.this.getTarjeta().getIdTarjeta();
        }

        public String getUid() {
            return Recarga.this.getTarjeta().getUid();
        }

        public String getAmbito() {
            return Recarga.this.getCentro().getAmbito().getNombre();
        }

        public String getCentro() {
            return Recarga.this.getCentro().getNombre();
        }

        public Date getFechaRealizacion() {
            return Recarga.this.getFechaRealizacion();
        }

        public Integer getAviso() {
            return Recarga.this.getAviso().getNumero();
        }

        public BigDecimal getImporte() {
            return Recarga.this.getImporte();
        }

        public String getDescripcion() { return Recarga.this.getDescripcion(); }

    }

    public static class RecargaDatosWS {

        private Recarga recarga;

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public int getCentroId() {
            return centroId;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getCentroNombre() {
            return centroNombre;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public int getAmbitoId() {
            return ambitoId;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getAmbitoNombre() {
            return ambitoNombre;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public boolean getEsRecargaSaldoVirtual() {
            return esRecargaSaldoVirtual;
        }

        private int centroId;
        private String centroNombre;
        private int ambitoId;
        private String ambitoNombre;
        private boolean esRecargaSaldoVirtual;

        public RecargaDatosWS() {
        }


        public RecargaDatosWS(Recarga compra) {
            this.recarga = compra;
            if(compra.getCentro() != null)
            {
                this.centroId = compra.getCentro().getId();
                this.centroNombre = compra.getCentro().getNombre();
                if(compra.getCentro().getAmbito() != null)
                {
                    this.ambitoId = compra.getCentro().getAmbito().getId();
                    this.ambitoNombre = compra.getCentro().getAmbito().getNombre();
                }
                else{
                    this.ambitoId = 0;
                    this.ambitoNombre = "";
                }
            }
            else{
                this.centroId = 0;
                this.centroNombre = "";
                this.ambitoId = 0;
                this.ambitoNombre = "";
            }
            if(compra.isEsRecargaSaldoVirtual() != null)
            {
                this.esRecargaSaldoVirtual = compra.isEsRecargaSaldoVirtual();
            }
            else{
                this.esRecargaSaldoVirtual = false;
            }
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
        @JsonFormat(shape = JsonFormat.Shape.STRING)
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
