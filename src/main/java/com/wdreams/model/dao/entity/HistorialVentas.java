package com.wdreams.model.dao.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "historialventas")
@DataTransferObject
public class HistorialVentas {
    public HistorialVentas(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "Descripcion")
    private String Descripcion;

    @Column(name = "Fecha")
    private Date Fecha;

    @Column(name = "IdCiudadano")
    private Integer IdCiudadano;

    @Column(name = "SaldoVirtualActual")
    private BigDecimal SaldoVirtualActual;

    @Column(name = "IdVenta")
    private Integer IdVenta;

    public HistorialVentas(String descripcion, Date fecha, Integer idCiudadano, BigDecimal SaldoVirtual, Integer idVenta){
        this.Descripcion = descripcion;
        this.Fecha = fecha;
        this.IdCiudadano = idCiudadano;
        this.SaldoVirtualActual = SaldoVirtual;
        this.IdVenta = idVenta;
    }

    public Integer getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(Integer idVenta) {
        IdVenta = idVenta;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public Integer getIdCiudadano() {
        return IdCiudadano;
    }

    public void setIdCiudadano(Integer idCiudadano) {
        IdCiudadano = idCiudadano;
    }

    public BigDecimal getSaldoVirtualActual() {
        return SaldoVirtualActual;
    }

    public void setSaldoVirtualActual(BigDecimal saldoVirtualActual) {
        SaldoVirtualActual = saldoVirtualActual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialVentas that = (HistorialVentas) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getDescripcion(), that.getDescripcion()) &&
                Objects.equals(getFecha(), that.getFecha()) &&
                Objects.equals(getIdCiudadano(), that.getIdCiudadano()) &&
                Objects.equals(getSaldoVirtualActual(), that.getSaldoVirtualActual());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescripcion(), getFecha(), getIdCiudadano(), getSaldoVirtualActual());
    }

    @Override
    public String toString() {
        return "HistorialVentas{" +
                "Id=" + Id +
                ", Descripcion='" + Descripcion + '\'' +
                ", Fecha=" + Fecha +
                ", IdCiudadano=" + IdCiudadano +
                ", SaldoVirtualActual=" + SaldoVirtualActual +
                '}';
    }
}
