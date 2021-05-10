package com.wdreams.model.dao.entity;


import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "BonificacionComercios")
public class BonificacionComercios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEntidad", nullable = true, referencedColumnName = "id")
    private Entidad entidad;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTarjeta", nullable = true, referencedColumnName = "id")
    private Tarjeta tarjeta;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idComercio", nullable = true, referencedColumnName = "id")
    private CentroComercio comercio;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    private BigDecimal importeAcumulado;
    private BigDecimal bonificacionMonedero;
    private Boolean finalizada;
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    private BigDecimal bonificacionGenerada;

    public BonificacionComercios() {
    }

    public BonificacionComercios(Entidad entidad, Tarjeta tarjeta, CentroComercio comercio, BigDecimal bonificacionMonedero, Date fechaInicio,
                                 BigDecimal importeAcumulado, Boolean finalizada, Date fechaFinal, BigDecimal bonificacionGenerada) {

        this.entidad = entidad;
        this.tarjeta = tarjeta;
        this.comercio = comercio;
        this.fechaInicio = fechaInicio;
        this.importeAcumulado = importeAcumulado;
        this.bonificacionMonedero = bonificacionMonedero;
        this.finalizada = finalizada;
        this.fechaFinal = fechaFinal;
        this.bonificacionGenerada = bonificacionGenerada;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public CentroComercio getComercio() {
        return comercio;
    }

    public void setComercio(CentroComercio comercio) {
        this.comercio = comercio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getImporteAcumulado() {
        return importeAcumulado;
    }

    public void setImporteAcumulado(BigDecimal importeAcumulado) {
        this.importeAcumulado = importeAcumulado;
    }

    public BigDecimal getBonificacionMonedero() {
        return bonificacionMonedero;
    }

    public void setBonificacionMonedero(BigDecimal bonificacionMonedero) {
        this.bonificacionMonedero = bonificacionMonedero;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getBonificacionGenerada() {
        return bonificacionGenerada;
    }

    public void setBonificacionGenerada(BigDecimal bonificacionGenerada) {
        this.bonificacionGenerada = bonificacionGenerada;
    }

    public class BonificacionComercioDatosExport {

        public String getNombreComercio() {
            return BonificacionComercios.this.getComercio().getNombre();
        }

        public String getFechaInicio() {
            return DateUtils.yyyyMMddHHmmss.format(BonificacionComercios.this.getFechaInicio());
        }

        public String getFechaFinal() {
            return DateUtils.yyyyMMddHHmmss.format(BonificacionComercios.this.getFechaFinal());
        }

        public BigDecimal getImporteAcumulado() {
            return BonificacionComercios.this.getImporteAcumulado();
        }

        public BigDecimal getBonificacionGenerada() {
            return BonificacionComercios.this.getBonificacionGenerada();
        }

        public String getNombreCiudadano() {
            return BonificacionComercios.this.getEntidad().getNombre() + " "
                    + BonificacionComercios.this.getEntidad().getApellidos();
        }

        public String getIdentificadorTarjeta() {
            return BonificacionComercios.this.getTarjeta().getIdTarjeta();
        }

        public String getFinalizada() {
            return BonificacionComercios.this.getFinalizada() == true ? "Si" : "No";
        }

    }
}
