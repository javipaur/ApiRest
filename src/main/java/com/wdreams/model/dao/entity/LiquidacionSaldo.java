package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "liquidacionsaldo")
public class LiquidacionSaldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "fecha", nullable = false)
    protected Date fecha;

    @Column(name = "referencia", nullable = false)
    protected String referencia;

    @Column(name = "importe", nullable = false)
    protected BigDecimal importe;

    @ManyToOne()
    @JoinColumn(name = "estado")
    private EstadosLiquidacionSaldo estado;

    @ManyToOne()
    @JoinColumn(name = "comercioId")
    private CentroComercio comercio;

    @Column(name = "cantidadOperaciones")
    protected Integer cantidadOperaciones;

    @Column(name = "fechaLiquidacion")
    protected Date fechaLiquidacion;


    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idUsuarioSolicitud", nullable = true, referencedColumnName = "id")
    private User UsuarioSolicitud;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idUsuarioLiquidador", nullable = true, referencedColumnName = "id")
    private User UsuarioLiquidador;

    public User getUsuarioSolicitud() {
        return UsuarioSolicitud;
    }

    public void setUsuarioSolicitud(User usuarioSolicitud) {
        UsuarioSolicitud = usuarioSolicitud;
    }

    public User getUsuarioLiquidador() {
        return UsuarioLiquidador;
    }

    public void setUsuarioLiquidador(User usuarioLiquidador) {
        UsuarioLiquidador = usuarioLiquidador;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Integer getCantidadOperaciones() {
        return cantidadOperaciones;
    }

    public void setCantidadOperaciones(Integer cantidadOperaciones) {
        this.cantidadOperaciones = cantidadOperaciones;
    }

    public CentroComercio getComercio() {
        return comercio;
    }

    public void setComercio(CentroComercio comercio) {
        this.comercio = comercio;
    }

    public LiquidacionSaldo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public EstadosLiquidacionSaldo getEstado() {
        return estado;
    }

    public void setEstado(EstadosLiquidacionSaldo estado) {
        this.estado = estado;
    }


    public class LiquidacionesDatosExportSubvenciones {

        public Date getFecha() { return LiquidacionSaldo.this.fecha; }

        public String getReferencia() {
            return LiquidacionSaldo.this.referencia;
        }

        public String getNombre() {
            return LiquidacionSaldo.this.comercio.getNombre();
        }

        public BigDecimal getTotal() {
            return LiquidacionSaldo.this.importe;
        }

        public Integer getOperaciones() {
            return LiquidacionSaldo.this.cantidadOperaciones;
        }

        public String getEstado() { return LiquidacionSaldo.this.estado.nombre; }

        public Date getLiquidacion() {
            return LiquidacionSaldo.this.fechaLiquidacion;
        }

    }
}
