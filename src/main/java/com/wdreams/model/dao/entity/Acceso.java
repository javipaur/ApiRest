/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.wdreams.utils.DateUtils;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Accesos")
public class Acceso extends Uso {

    public Acceso() {
    }

    public Acceso(Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, Integer idOperacionAnulada, Aviso aviso) {
        super(fechaRealizacion, fechaRegistro, centro, dispositivo, usuario, tarjeta, descripcion, numeroOperacion, idOperacionAnulada, aviso);
    }

    @Override
    public void asignarIdUso() {
        this.asignarIdUso("A");
    }

    public class AccesoDatosExport {

        public String getIdUso() {
            return (Acceso.this.getIdUso() != null ? Acceso.this.getIdUso() : "");
        }

        public String getNumeroOperacion() {
            return (Acceso.this.getNumeroOperacion() != null ? Acceso.this.getNumeroOperacion() : "");
        }

        public String getEntidad() {
            return (Acceso.this.getTarjeta().getEntidad().getIdCiudadano() != null ? Acceso.this.getTarjeta().getEntidad().getIdCiudadano() : "");
        }

        public String getTarjeta() {
            return Acceso.this.getTarjeta().getIdTarjeta();
        }

        public String getUid() {
            return (Acceso.this.getTarjeta().getUid() != null ? Acceso.this.getTarjeta().getUid() : "");
        }

        public String getAmbito() {
            return Acceso.this.getCentro().getAmbito().getNombre();
        }

        public String getCentro() {
            return (Acceso.this.getCentro().getNombre() != null ? Acceso.this.getCentro().getNombre() : "");
        }

        public Date getRealizacion() {
            return Acceso.this.getFechaRealizacion();
        }

        public Integer getAviso() {
            return (Acceso.this.getAviso().getNumero() != null ? Acceso.this.getAviso().getNumero() : 0);
        }

    }

    public static class AccesoDatosWS {

        private Acceso acceso;

        public AccesoDatosWS() {
        }

        public AccesoDatosWS(Acceso acceso) {
            this.acceso = acceso;
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNumero() {
            return acceso.getNumeroOperacion();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getApellidos() {
            return acceso.getTarjeta().getEntidad().getApellidos();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdCiudadano() {
            return acceso.getTarjeta().getEntidad().getIdCiudadano();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getUid() {
            return acceso.getTarjeta().getUid();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getFecha() {
            return DateUtils.yyyyMMddHHmmss.format(acceso.getFechaRealizacion());
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdCentro() {
            return acceso.getCentro() == null ? "" : acceso.getCentro().getIdCentro();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNombreCentro() {
            return acceso.getCentro() == null ? "" : acceso.getCentro().getNombre();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdAmbito() {
            return acceso.getCentro() == null ? "" : acceso.getCentro().getAmbito() == null?"":acceso.getCentro().getAmbito().getId().toString();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getNombreAmbito() {
            return acceso.getCentro().getAmbito() == null?"":acceso.getCentro().getAmbito().getNombre().toString();
        }

        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getIdDispositivo() {
            return acceso.getDispositivo() == null ? "" : acceso.getDispositivo().getIdDispositivo();
        }
        
        @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
        public String getDescripcion() {
            return acceso.getDescripcion();
        }

    }
}
