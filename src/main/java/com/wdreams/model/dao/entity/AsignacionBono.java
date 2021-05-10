/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;



import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "AsignacionesBono")
public class AsignacionBono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idAsignacionBono", length = 13)
    private String idAsignacionBono;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idBono", nullable = false, referencedColumnName = "id")
    private Bono bono;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idCiudadano", nullable = false, referencedColumnName = "id")
    private Entidad ciudadano;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idEstadoAsignacionBono", nullable = false, referencedColumnName = "id")
    private EstadoAsignacionBono estado;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "idUsuarioAsignador", nullable = false, referencedColumnName = "id")
    private Usuario usuarioAsignador;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAsignacion")
    private Date fechaAsignacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaUltimoUso")
    private Date fechaUltimoUso;

    @Column(name = "numeroUsos")
    private Integer numeroUsos = 0;

    @Transient
    private Integer numeroUsosDiario = 0;

    @Transient
    private Integer numeroUsosMensual = 0;


    public BigDecimal getPrecioAsignacion() {

        if(precioAsignacion == null || precioAsignacion == BigDecimal.ZERO)
        {
            if(this.bono == null)
            {
                return new BigDecimal("0.00");
            }
            return this.bono.getPrecio();
        }
        return precioAsignacion;
    }

    public void setPrecioAsignacion(BigDecimal precioAsignacion) {
        this.precioAsignacion = precioAsignacion;
    }

    @Column(name = "precioAsignacion", nullable = true)
    protected BigDecimal precioAsignacion;



    public String getIdBonoAsignacionEntidad() {
        return ciudadano.getIdCiudadano();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdAsignacionBono() {
        return idAsignacionBono;
    }

    public void setIdAsignacionBono(String idAsignacionBono) {
        this.idAsignacionBono = idAsignacionBono;
    }


    public Bono getBono() {
        bono.setCentros(this.bono.getCentros());
        return bono;
    }

    public void setBono(Bono bono) {
        this.bono = bono;
    }

    public Entidad getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Entidad ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Integer getNumeroUsos() {
        return numeroUsos;
    }

    public void setNumeroUsos(Integer numeroUsos) {
        this.numeroUsos = numeroUsos;
    }

    public Date getFechaUltimoUso() {
        return fechaUltimoUso;
    }

    public void setFechaUltimoUso(Date fechaUltimoUso) {
        this.fechaUltimoUso = fechaUltimoUso;
    }

    public EstadoAsignacionBono getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignacionBono estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioAsignador() {
        return usuarioAsignador;
    }

    public void setUsuarioAsignador(Usuario usuarioAsignador) {
        this.usuarioAsignador = usuarioAsignador;
    }

    public Integer getNumeroUsosDiario() {
        return numeroUsosDiario;
    }

    public void setNumeroUsosDiario(Integer num)
    {
        this.numeroUsosDiario = num;
    }

    public Integer getNumeroUsosMensual() {
        return numeroUsosMensual;
    }

    public void setNumeroUsosMensual(Integer num)
    {
        this.numeroUsosMensual = num;
    }

    public void asignarIdAsignacionBono() {
        this.setIdAsignacionBono(String.format("AS%010X", this.getId()));
    }

    /* Dado una fecha se determina cuantos usos habia realizado esta asignacion (this) */
    public void inicializarUltimosUsos(Dao dao, Date fecha) {

        Date fechaInicioMes = DateUtils.getFechaInicioMes(fecha);
        Date fechaInicioDia = DateUtils.getFechaInicioDia(fecha);
        
        Date fechaFinMes = DateUtils.getFechaAlFinMes(fecha);
        Date fechaFinDia = DateUtils.getFechaAlFinDia(fecha);
        
        numeroUsosMensual = dao.getUsosBonosNumeroSimplificado(this, fechaInicioMes, fechaFinMes).intValue();
        numeroUsosDiario = dao.getUsosBonosNumeroSimplificado(this, fechaInicioDia, fechaFinDia).intValue();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final AsignacionBono other = (AsignacionBono) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public class AsignacionBonoExport {

        public String getIdCiudadano() {
            return AsignacionBono.this.getCiudadano().getIdCiudadano();
        }

        public String getBono() {
            return AsignacionBono.this.getBono().getIdBono();
        }

        public String getIdAsignacionBono() { return (AsignacionBono.this.getIdAsignacionBono() != null ? AsignacionBono.this.getIdAsignacionBono() : "") ; }

        public String getEstado() {
            return AsignacionBono.this.getEstado().getNombre();
        }

        public String getUsuario() {
            return AsignacionBono.this.getUsuarioAsignador().getUsername();
        }

        public Date getFechaAsignacion() {
            return AsignacionBono.this.getFechaAsignacion();
        } // puede ser NULL

        public Integer getNumeroUsos() {
            return (AsignacionBono.this.getNumeroUsos() != null ? AsignacionBono.this.getNumeroUsos() : 0);
        }

        public Integer getNumeroUsosDiario() {
            return AsignacionBono.this.getNumeroUsosDiario();
        }

        public Integer getNumeroUsosMensual() {
            return AsignacionBono.this.getNumeroUsosMensual();
        }
    }
}

/*
 public Integer getNumeroUsosDiarioReal() {

 int usosUltimoDia = this.numeroUsosDiario;

 if (this.fechaUltimoUso != null) {

 Calendar ultimoUsoCalendar = Calendar.getInstance();
 Calendar hoyCalendar = Calendar.getInstance();

 ultimoUsoCalendar.setTime(this.fechaUltimoUso);
 hoyCalendar.setTime(new Date());

 if (ultimoUsoCalendar.get(Calendar.YEAR) != hoyCalendar.get(Calendar.YEAR) || ultimoUsoCalendar.get(Calendar.MONTH) != hoyCalendar.get(Calendar.MONTH) || ultimoUsoCalendar.get(Calendar.DAY_OF_MONTH) != hoyCalendar.get(Calendar.DAY_OF_MONTH)) {
 usosUltimoDia = 0;
 }
 }

 return usosUltimoDia;
 }

 public Integer getNumeroUsosMensualReal() {

 int usosUltimoMes = this.numeroUsosMensual;

 if (this.fechaUltimoUso != null) {

 Calendar ultimoUsoCalendar = Calendar.getInstance();
 Calendar hoyCalendar = Calendar.getInstance();

 ultimoUsoCalendar.setTime(this.fechaUltimoUso);
 hoyCalendar.setTime(new Date());

 if (ultimoUsoCalendar.get(Calendar.YEAR) != hoyCalendar.get(Calendar.YEAR) || ultimoUsoCalendar.get(Calendar.MONTH) != hoyCalendar.get(Calendar.MONTH)) {
 usosUltimoMes = 0;
 }
 }

 return usosUltimoMes;
 }
 */
//    @Column(name = "numeroUsosDiario")
//    private Integer numeroUsosDiario=0;
//
//    @Column(name = "numeroUsosMensual")
//    private Integer numeroUsosMensual=0;
//SELECT count(*) FROM db_tc_ca.usosbonos WHERE idAsignacionBono = 26 and YEAR(fechaRealizacion) = YEAR(NOW()) and MONTH(fechaRealizacion) = MONTH(NOW())
//SELECT count(*) FROM db_tc_ca.usosbonos WHERE idAsignacionBono = 26 and YEAR(fechaRealizacion) = YEAR(NOW()) and MONTH(fechaRealizacion) = MONTH(NOW())  and DAY(fechaRealizacion) = DAY(NOW())
//@Formula("(select min(o.creation_date) from Orders o where o.customer_id = id)")
//---
//    @Formula("(SELECT count(*) FROM UsoBono u WHERE u.asignacionBono = id and YEAR(u.fechaRealizacion) = YEAR(current_date()) and MONTH(u.fechaRealizacion) = MONTH(current_date()))")
//    @Formula("(SELECT count(*) FROM UsoBono u WHERE u.asignacionBono = id and YEAR(u.fechaRealizacion) = YEAR(current_date()) and MONTH(u.fechaRealizacion) = MONTH(current_date()) and DAY(u.fechaRealizacion) = DAY(current_date()))")

