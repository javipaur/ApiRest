package com.wdreams.model.dao.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ventaslineas")
@DataTransferObject
public class VentasLineas {
    public VentasLineas(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "Fecha")
    private Date Fecha;

    @Column(name = "TipoVenta")
    private Integer TipoVenta;

    @Column(name = "Importe")
    private BigDecimal Importe;

    @Column(name = "VentaId")
    private Integer VentaId;

    public VentasLineas(Date Fecha, Integer TipoVenta, BigDecimal Importe, Integer VentaId){
       this.Fecha = Fecha;
       this.TipoVenta = TipoVenta;
       this.Importe = Importe;
       this.VentaId = VentaId;
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

    public Integer getTipoVenta() {
        return TipoVenta;
    }

    public void setTipoVenta(Integer tipoVenta) {
        TipoVenta = tipoVenta;
    }

    public BigDecimal getImporte() {
        return Importe;
    }

    public void setImporte(BigDecimal importe) {
        Importe = importe;
    }

    public Integer getVentaId() {
        return VentaId;
    }

    public void setVentaId(Integer ventaId) {
        VentaId = ventaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentasLineas that = (VentasLineas) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getFecha(), that.getFecha()) &&
                Objects.equals(getTipoVenta(), that.getTipoVenta()) &&
                Objects.equals(getImporte(), that.getImporte()) &&
                Objects.equals(getVentaId(), that.getVentaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFecha(), getTipoVenta(), getImporte(), getVentaId());
    }

    @Override
    public String toString() {
        return "VentasLineas{" +
                "Id=" + Id +
                ", Fecha=" + Fecha +
                ", TipoVenta=" + TipoVenta +
                ", Importe=" + Importe +
                ", VentaId=" + VentaId +
                '}';
    }
}
