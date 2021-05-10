/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "UsosBonos")
public class UsoBono extends Uso {

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idAsignacionBono", nullable = false, referencedColumnName = "id")
    protected AsignacionBono asignacionBono;

    @Column(name = "cantidad", nullable = true)
    protected Integer cantidad;

    public Integer getPax() {
        return pax;
    }

    public void setPax(Integer pax) {
        this.pax = pax;
    }

    @Column(name = "pax", nullable = true)
    protected Integer pax;

    @Override
    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    @Override
    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    @Column(name = "fechaRealizacion", nullable = true)
    protected Date fechaRealizacion;

    public UsoBono() {
    }

    public UsoBono(Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, Integer idOperacionAnulada, AsignacionBono asignacionBono, Integer cantidad, Aviso aviso) {
        super(fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion
                , numeroOperacion, idOperacionAnulada, aviso);
        this.asignacionBono = asignacionBono;
        this.cantidad = cantidad;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaRegistro = fechaRegistro;
        this.centro = centro;
        this.dispositivo = dispositivo;
        this.usuario = usuario;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.numeroOperacion = numeroOperacion;
        this.idOperacionAnulada = idOperacionAnulada;
        this.aviso = aviso;
    }

    public UsoBono(Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, Integer idOperacionAnulada, AsignacionBono asignacionBono, Integer cantidad, Aviso aviso, Integer pax) {
        super(fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion
                , numeroOperacion, idOperacionAnulada, aviso);
        this.asignacionBono = asignacionBono;
        this.cantidad = cantidad;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaRegistro = fechaRegistro;
        this.centro = centro;
        this.dispositivo = dispositivo;
        this.usuario = usuario;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.numeroOperacion = numeroOperacion;
        this.idOperacionAnulada = idOperacionAnulada;
        this.aviso = aviso;
        this.pax = pax;
    }

    @Override
    public void asignarIdUso() {
        this.asignarIdUso("U");
    }

    public AsignacionBono getAsignacionBono() {
        return asignacionBono;
    }

    public void setAsignacionBono(AsignacionBono asignacionBono) {
        this.asignacionBono = asignacionBono;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public class UsoBonoDatosExport {

        public String getIdUso() {
            return UsoBono.this.getIdUso();
        }

        public Integer getPax() {
            return (UsoBono.this.getPax() != null ? UsoBono.this.getPax() : 0);
        }

        public Integer getCantidad() {
            return (UsoBono.this.getCantidad() != null ? UsoBono.this.getCantidad() : 0);
        }

        public String getAsignacionBono() {
            return UsoBono.this.getAsignacionBono().getIdAsignacionBono();
        }

        public String getNumeroOperacion() {
            return (UsoBono.this.getNumeroOperacion() != null ? UsoBono.this.getNumeroOperacion() : "");
        }

        public String getCiudadano() {
            return UsoBono.this.getTarjeta().getEntidad().getIdCiudadano();
        }

        public String getTarjeta() {
            return UsoBono.this.getTarjeta().getIdTarjeta();
        }

        public String getUid() {
            return UsoBono.this.getTarjeta().getUid();
        }

        public String getAmbito() {
            return UsoBono.this.getCentro().getAmbito().getNombre();
        }

        public String getCentro() {
            return UsoBono.this.getCentro().getNombre();
        }

        public Date getRealizacion() {
            return UsoBono.this.getFechaRealizacion();
        }

        public Integer getAviso() {
            return (UsoBono.this.getAviso().getNumero() != null ? UsoBono.this.getAviso().getNumero() : 0);
        }

        public String getBono() {
            return UsoBono.this.getAsignacionBono().getBono().getNombre();
        }
    }
    
    public static class UsosBonoDatosWS {

        /**
         * Ahora piden incluir el �mbito y el centro
         */
        private String ambitoNombre;
        private int ambitoId;
        private String centroNombre;
        private int centroId;

        private Integer pax;

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






        private UsoBono usoBono;

        public UsosBonoDatosWS() {
        }

        public UsosBonoDatosWS(UsoBono usoBono) {
            this.usoBono = usoBono;
            this.ambitoId = usoBono.getCentro().getAmbito().getId();
            this.ambitoNombre = usoBono.getCentro().getAmbito().getNombre();
            this.centroId = usoBono.getCentro().getId();
            this.centroNombre = usoBono.getCentro().getNombre();
            this.pax = usoBono.getPax();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdAsignacionBono() {
            return usoBono.getAsignacionBono().getIdAsignacionBono();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNumero() {
            return usoBono.getNumeroOperacion();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdCiudadano() {
            return usoBono.getTarjeta().getEntidad().getIdCiudadano();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getUid() {
            return usoBono.getTarjeta().getUid();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getFecha() {
            return DateUtils.yyyyMMddHHmmss.format(usoBono.getFechaRealizacion());
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getApellidos() {
            return usoBono.getTarjeta().getEntidad().getApellidos();
        }
        
         @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNombreCentro() {
            return usoBono.getCentro() == null ? "" : usoBono.getCentro().getNombre();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdDispositivo() {
            return usoBono.getDispositivo() == null ? "" : usoBono.getDispositivo().getIdDispositivo();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getDescripcion() {
            return usoBono.getDescripcion();
        }


        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public Integer getPax() {
            return usoBono.getPax();
        }

    }

}
