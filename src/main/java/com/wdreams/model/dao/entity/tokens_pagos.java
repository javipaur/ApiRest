package com.wdreams.model.dao.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


/**
 * Alberto 07/09/2020
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tokens_pagos")
@DataTransferObject
public class tokens_pagos {

    public tokens_pagos() {
    }

    public tokens_pagos(Integer idCiudadano, Integer idComercio, BigDecimal importe,
                        Date fecha, Boolean usado, String token)
    {
        this.idCiudadano = idCiudadano;
        this.idComercio = idComercio;
        this.importe = importe;
        this.fecha = fecha;
        this.usado = usado;
        this.token = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "idCiudadano")
    private Integer idCiudadano;
    @Column(name = "idComercio")
    private Integer idComercio;
    @Column(name = "importe")
    private BigDecimal importe;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "usado")
    private Boolean usado;
    @Column(name = "token")
    private String token;

    @Column(name="fechaUsado")
    private Date fechaUsado;
    @Column(name="ImporteTicketUsado")
    private BigDecimal ImporteTicketUsado;
    @Column(name="ticketUsadoUrl")
    private String ticketUsadoUrl;

    public Date getFechaUsado() {
        return fechaUsado;
    }

    public void setFechaUsado(Date fechaUsado) {
        this.fechaUsado = fechaUsado;
    }

    public BigDecimal getImporteTicketUsado() {
        return ImporteTicketUsado;
    }

    public void setImporteTicketUsado(BigDecimal importeTicketUsado) {
        ImporteTicketUsado = importeTicketUsado;
    }

    public String getTicketUsadoUrl() {
        return ticketUsadoUrl;
    }

    public void setTicketUsadoUrl(String ticketUsadoUrl) {
        this.ticketUsadoUrl = ticketUsadoUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(Integer idCiudadano) {
        this.idCiudadano = idCiudadano;
    }

    public Integer getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Integer idComercio) {
        this.idComercio = idComercio;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getUsado() {
        return usado;
    }

    public void setUsado(Boolean usado) {
        this.usado = usado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        tokens_pagos that = (tokens_pagos) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getIdCiudadano(), that.getIdCiudadano()) &&
                Objects.equals(getIdComercio(), that.getIdComercio()) &&
                Objects.equals(getImporte(), that.getImporte()) &&
                Objects.equals(getFecha(), that.getFecha()) &&
                Objects.equals(getUsado(), that.getUsado()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getFechaUsado(), that.getFechaUsado()) &&
                Objects.equals(getImporteTicketUsado(), that.getImporteTicketUsado()) &&
                Objects.equals(getTicketUsadoUrl(), that.getTicketUsadoUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdCiudadano(), getIdComercio(), getImporte(), getFecha(), getUsado(), getToken(), getFechaUsado(), getImporteTicketUsado(), getTicketUsadoUrl());
    }
}
