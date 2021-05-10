/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"ciudadano", "listaDocumentos", "usuarioCreador"})
@Entity
@Table(name = "TipologiasCiudadano")
public class TipologiaCiudadano implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTipoCiudadano", nullable = false, referencedColumnName = "id")
    private TipoCiudadano tipoCiudadano;

    @ManyToOne
    @JoinColumn(name = "idCiudadano", nullable = false, referencedColumnName = "id")
    private Entidad ciudadano;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAsignado", nullable = false)
    private Date fechaAsignado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaDesactivada", nullable = true)
    private Date fechaDesactivada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaInicio", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCaducidad", nullable = false)
    private Date fechaCaducidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizada", nullable = true)
    private Date fechaActualizada;

    @Column(name = "actualizada")
    private Boolean actualizada;

    @Column(name = "activa")
    private Boolean activa;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "id")
    private Usuario usuarioCreador;
    
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @OneToMany(mappedBy = "tipologiaCiudadano", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<TipologiaCiudadanoDocumento> listaDocumentos;

    public TipologiaCiudadano() {
    }

    public TipologiaCiudadano(TipoCiudadano tipoCiudadano, Entidad ciudadano, Date fechaAsignado, Date fechaInicio, Date fechaCaducidad, Date fechaActualizada, Boolean actualizada, Boolean activa, Usuario usuarioCreador,List<TipologiaCiudadanoDocumento> listaDocumentos) {
        this.tipoCiudadano = tipoCiudadano;
        this.ciudadano = ciudadano;
        this.fechaAsignado = fechaAsignado;
        this.fechaInicio = fechaInicio;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaActualizada = fechaActualizada;
        this.actualizada = actualizada;
        this.activa = activa;
        this.usuarioCreador = usuarioCreador;
        this.listaDocumentos=listaDocumentos;
        for(TipologiaCiudadanoDocumento tipologiaCiudadanoDocumento : this.listaDocumentos){
            tipologiaCiudadanoDocumento.setTipologiaCiudadano(this);
        }
    }

    public TipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano, TipoCiudadano tipoCiudadano, Entidad ciudadano) {
        if (tipologiaCiudadano != null) {
            this.id = tipologiaCiudadano.getId();
            this.tipoCiudadano = tipologiaCiudadano.getTipoCiudadano();
            this.ciudadano = tipologiaCiudadano.getCiudadano();
            this.fechaAsignado = tipologiaCiudadano.getFechaAsignado();
            this.fechaDesactivada = tipologiaCiudadano.getFechaDesactivada();
            this.fechaInicio = tipologiaCiudadano.getFechaInicio();
            this.fechaCaducidad = tipologiaCiudadano.getFechaCaducidad();
            this.fechaActualizada = tipologiaCiudadano.getFechaActualizada();
            this.actualizada = tipologiaCiudadano.getActualizada();
            this.activa = tipologiaCiudadano.getActiva();
            this.usuarioCreador = tipologiaCiudadano.getUsuarioCreador();

        } else {
            this.tipoCiudadano = tipoCiudadano;
            this.ciudadano = ciudadano;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getIdTipologiaCiudadano() {
        return ciudadano.getIdCiudadano();
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Boolean getActualizada() {
        return actualizada;
    }

    public void setActualizada(Boolean actualizada) {
        this.actualizada = actualizada;
    }

    public Entidad getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Entidad ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Date getFechaActualizada() {
        return fechaActualizada;
    }

    public void setFechaActualizada(Date fechaActualizada) {
        this.fechaActualizada = fechaActualizada;
    }

    public Date getFechaAsignado() {
        return fechaAsignado;
    }

    public void setFechaAsignado(Date fechaAsignado) {
        this.fechaAsignado = fechaAsignado;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public TipoCiudadano getTipoCiudadano() {
        return tipoCiudadano;
    }

    public void setTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.tipoCiudadano = tipoCiudadano;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Date getFechaDesactivada() {
        return fechaDesactivada;
    }

    public void setFechaDesactivada(Date fechaDesactivada) {
        this.fechaDesactivada = fechaDesactivada;
    }

    public List<TipologiaCiudadanoDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<TipologiaCiudadanoDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public class TipologiaCiudadanoExport {

        public String getIdCiudadano() {
            return TipologiaCiudadano.this.getCiudadano().getIdCiudadano();
        }

        public String getNombre() {
            return TipologiaCiudadano.this.getCiudadano().getNombre();
        }

        public String getApellidos() {
            return TipologiaCiudadano.this.getCiudadano().getApellidos();
        }

        public String getTipoCiudadano() {
            return TipologiaCiudadano.this.getTipoCiudadano().getIdTipoCiudadano();
        }

        public String getNombreTipoCiudadano() {
            return TipologiaCiudadano.this.getTipoCiudadano().getNombre();
        }

        public String getTipoAsignacion() {
            return TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getNombre();
        }

        public Date getFechaAsignada() {
            return (TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getId() == TipoAsignacion.MANUAL ? TipologiaCiudadano.this.getFechaAsignado() : null);
        }

        public Date getFechaInicio() {
            return (TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getId() == TipoAsignacion.MANUAL ? TipologiaCiudadano.this.getFechaInicio() : null);
        }

        public Date getFechaCaducidad() {
            return (TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getId() == TipoAsignacion.MANUAL ? TipologiaCiudadano.this.getFechaCaducidad(): null);
        }

        public String getUsuarioCreador() {
            return (TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getId() == TipoAsignacion.MANUAL ? TipologiaCiudadano.this.getUsuarioCreador().getUsername(): "");
        }
        
        public String getActiva() {
            return (TipologiaCiudadano.this.getTipoCiudadano().getTipoAsignacion().getId() == TipoAsignacion.MANUAL ? (TipologiaCiudadano.this.getActiva() ? "SI" : "NO"): "");
        }
   
     }

}
