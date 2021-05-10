/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"usuario", "centro"})
@Entity
@Table(name = "Dispositivos")
@DataTransferObject
public class Dispositivo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "idDispositivo", length = 7)
    private String idDispositivo;
    @Column(name = "numeroSerie", length = 20)
    private String numeroSerie;
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idCentro", nullable = false, referencedColumnName = "id")
    private Centro centro;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo = Boolean.TRUE;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAlta")
    private Date fechaAlta;

    @Column(name = "forzarActualizacionListaBlanca")
    private Boolean forzarActualizacionListaBlanca;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idUsuarioUltimoLogin", nullable = true, referencedColumnName = "id")
    private Usuario usuario;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimaListaBlanca")
    private Date ultimaListaBlanca;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimaOperacionRecibida")
    private Date ultimaOperacionRecibida;
    @Column(name = "versionApp")
    private Integer versionApp;
    @Column(name = "precioCobro")
    private BigDecimal precioCobro;
    @Column(name = "forzarPrecio")
    private Boolean forzarPrecio;
    @Column(name = "modoBus")
    private Boolean modoBus;
    @Column(name = "ticketCompra")
    private Boolean ticketCompra;
    @Column(name = "ticketRecarga")
    private Boolean ticketRecarga;
    @Column(name = "ticketBono")
    private Boolean ticketBono;
    @Column(name = "ticketUso")
    private Boolean ticketUso;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getUltimaListaBlanca() {
        return ultimaListaBlanca;
    }

    public void setUltimaListaBlanca(Date ultimaListaBlanca) {
        this.ultimaListaBlanca = ultimaListaBlanca;
    }

    public Date getUltimaOperacionRecibida() {
        return ultimaOperacionRecibida;
    }

    public void setUltimaOperacionRecibida(Date ultimaOperacionRecibida) {
        this.ultimaOperacionRecibida = ultimaOperacionRecibida;
    }

    public Integer getVersionApp() {
        return versionApp;
    }

    public void setVersionApp(Integer versionApp) {
        this.versionApp = versionApp;
    }

    public BigDecimal getPrecioCobro() {
        return precioCobro;
    }

    public void setPrecioCobro(BigDecimal precioCobro) {
        this.precioCobro = precioCobro;
    }

    public Boolean getForzarPrecio() {
        return forzarPrecio;
    }

    public void setForzarPrecio(Boolean forzarPrecio) {
        this.forzarPrecio = forzarPrecio;
    }

    public Boolean getModoBus() {
        return modoBus;
    }

    public void setModoBus(Boolean modoBus) {
        this.modoBus = modoBus;
    }

    public Boolean getTicketCompra() {
        return ticketCompra;
    }

    public void setTicketCompra(Boolean ticketCompra) {
        this.ticketCompra = ticketCompra;
    }

    public Boolean getTicketRecarga() {
        return ticketRecarga;
    }

    public void setTicketRecarga(Boolean ticketRecarga) {
        this.ticketRecarga = ticketRecarga;
    }

    public Boolean getTicketBono() {
        return ticketBono;
    }

    public void setTicketBono(Boolean ticketBono) {
        this.ticketBono = ticketBono;
    }

    public Boolean getTicketUso() {
        return ticketUso;
    }

    public void setTicketUso(Boolean ticketUso) {
        this.ticketUso = ticketUso;
    }

    public Dispositivo() {
    }

    public Dispositivo(String numeroSerie, Centro centro, String descripcion) {
        this.numeroSerie = numeroSerie;
        this.centro = centro;
        this.descripcion = descripcion;
        this.fechaAlta = new Date();
        this.forzarActualizacionListaBlanca = false;
        this.versionApp = -1;
        this.precioCobro = new BigDecimal(0);
        this.forzarPrecio = false;
        this.modoBus = false;
        this.ticketCompra = true;
        this.ticketRecarga = true;
        this.ticketBono = true;
        this.ticketUso = true;
    }

    public Dispositivo(
            String numeroSerie,
            Centro centro,
            String descripcion,
            Usuario usuario,
            Integer versionApp,
            BigDecimal precioCobro,
            Boolean forzarPrecio,
            Boolean modoBus,
            Boolean ticketCompra,
            Boolean ticketRecarga,
            Boolean ticketBono,
            Boolean ticketUso) {
        this.numeroSerie = numeroSerie;
        this.centro = centro;
        this.descripcion = descripcion;
        this.activo = true;
        this.fechaAlta = new Date();
        this.forzarActualizacionListaBlanca = false;
        this.usuario = usuario;
        this.versionApp = versionApp;
        this.precioCobro = precioCobro;
        this.forzarPrecio = forzarPrecio;
        this.modoBus = modoBus;
        this.ticketCompra = ticketCompra;
        this.ticketRecarga = ticketRecarga;
        this.ticketBono = ticketBono;
        this.ticketUso = ticketUso;
        
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean getForzarActualizacionListaBlanca() {
        return forzarActualizacionListaBlanca;
    }

    public void setForzarActualizacionListaBlanca(Boolean forzarActualizacionListaBlanca) {
        this.forzarActualizacionListaBlanca = forzarActualizacionListaBlanca;
    }

    public void assignarIdDispositivo() {
        this.idDispositivo = String.format("D%06X", id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dispositivo other = (Dispositivo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public class DispositivoDatosExport {

        public String getIdDispositivo() {
            return Dispositivo.this.getIdDispositivo();
        }

        public Date getFechaAlta() {
            return Dispositivo.this.getFechaAlta();
        }

        public String getDescripcion() {
            return Dispositivo.this.getDescripcion();
        }

        public String getIdAmbito() {
            return Dispositivo.this.getCentro().getAmbito().getIdAmbito();
        }

        public String getNombreAmbito() {
            return Dispositivo.this.getCentro().getAmbito().getNombre();
        }

        public String getIdCentro() {
            return Dispositivo.this.getCentro().getIdCentro();
        }

        public String getNombreCentro() {
            return Dispositivo.this.getCentro().getNombre();

        }

        public Boolean getActivo() {
            return Dispositivo.this.getActivo();
        }

    }

}
