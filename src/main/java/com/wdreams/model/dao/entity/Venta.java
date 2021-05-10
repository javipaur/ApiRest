package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "venta")
@DataTransferObject
public class Venta {
    public Venta(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "Fecha")
    private Date Fecha;

    @Column(name = "Localizador")
    private String Localizador;

    @Column(name = "IdCiudadano")
    private Integer IdCiudadano;

    @Column(name = "Finalizada")
    private Boolean Finalizada;

    @Column(name = "FormaPago")
    private Integer FormaPago;

    @Column(name = "TotalPagado")
    private BigDecimal TotalPagado;

    @Column(name = "Estado")
    private Integer Estado;

    @Column(name = "isMobile")
    private Boolean isMobile;

    public Venta(Integer idCiudadano){
        this.Fecha = new Date();
        this.Estado = 2;//INICIADA
        this.Finalizada = false;
        this.FormaPago = 1;//De momento s�lo onlineTpv
        this.IdCiudadano = idCiudadano;
        this.TotalPagado = BigDecimal.ZERO;
    }


    public Venta(tokens_pagos tokens_pagos){
        this.Fecha = new Date();
        this.Estado = 1;//PAGADA
        this.Finalizada = true;
        this.FormaPago = 2;//TOKENS_PAGOS
        this.IdCiudadano = tokens_pagos.getIdCiudadano();
        this.TotalPagado = tokens_pagos.getImporte();
    }

    public Venta(Integer ciudadanoId, BigDecimal importe)
    {
        this.Fecha = new Date();
        this.Estado = 1;//PAGADA
        this.Finalizada = true;
        this.FormaPago = 3;//COMPRA_SIN_TOKEN
        this.IdCiudadano = ciudadanoId;
        this.TotalPagado = importe;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public String getLocalizador() {
        return Localizador;
    }

    public void setLocalizador(String localizador) {
        Localizador = localizador;
    }

    public Integer getIdCiudadano() {
        return IdCiudadano;
    }

    public void setIdCiudadano(Integer idCiudadano) {
        IdCiudadano = idCiudadano;
    }

    public Boolean getFinalizada() {
        return Finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        Finalizada = finalizada;
    }

    public Integer getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(Integer formaPago) {
        FormaPago = formaPago;
    }

    public BigDecimal getTotalPagado() {
        return TotalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        TotalPagado = totalPagado;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        Estado = estado;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return getId().equals(venta.getId()) &&
                Objects.equals(getFecha(), venta.getFecha()) &&
                Objects.equals(getLocalizador(), venta.getLocalizador()) &&
                Objects.equals(getIdCiudadano(), venta.getIdCiudadano()) &&
                Objects.equals(getFinalizada(), venta.getFinalizada()) &&
                Objects.equals(getFormaPago(), venta.getFormaPago()) &&
                Objects.equals(getTotalPagado(), venta.getTotalPagado()) &&
                Objects.equals(getEstado(), venta.getEstado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFecha(), getLocalizador(), getIdCiudadano(), getFinalizada(), getFormaPago(), getTotalPagado(), getEstado());
    }

    @Override
    public String toString() {
        return "Venta{" +
                "Id=" + Id +
                ", Fecha=" + Fecha +
                ", Localizador='" + Localizador + '\'' +
                ", IdCiudadano=" + IdCiudadano +
                ", Finalizada=" + Finalizada +
                ", FormaPago=" + FormaPago +
                ", TotalPagado=" + TotalPagado +
                ", Estado=" + Estado +
                '}';
    }
}
