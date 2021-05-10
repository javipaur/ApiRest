package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import java.util.List;


/**
 * Alberto 07/09/2020
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "solicitudsaldovirtual")
@DataTransferObject
public class solicitudsaldovirtual {

    public solicitudsaldovirtual(){}
    public solicitudsaldovirtual(Date fechasolicitud, BigDecimal saldorecargar,
                                 User usuariosolicitud, String DescripcionBreve)
    {
        this.fechasolicitud = fechasolicitud;
        this.saldorecargar = saldorecargar;
        this.usuariosolicitud = usuariosolicitud;
        this.usuariovalidador = null;
        this.fechavalidacion = null;
        this.eliminada = null;
        this.DescripcionBreve = DescripcionBreve;
    }


//    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @JoinTable(name = "solicitudsaldovirtual_ciudadano",
//            joinColumns = @JoinColumn(name = "solicitudId"),
//            inverseJoinColumns = @JoinColumn(name = "ciudadano"))
    @OneToMany(mappedBy = "solicitud", fetch = FetchType.LAZY)
    protected List<solicitudsaldovirtual_ciudadano> relaciones;


    public List<solicitudsaldovirtual_ciudadano> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<solicitudsaldovirtual_ciudadano> relaciones) {
        this.relaciones = relaciones;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fechasolicitud")
    private Date fechasolicitud;
    @Column(name = "saldorecargar")
    private BigDecimal saldorecargar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuariosolicitud")
    private User usuariosolicitud;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuariovalidador")
    private User usuariovalidador;

    @Column(name = "fechavalidacion")
    private Date fechavalidacion;
    @Column(name = "eliminada")
    private Boolean eliminada;
    @Column(name = "fechaEliminada")
    private Date fechaEliminada;

    @Column(name="usuarioelimina")
    private Integer usuarioelimina;

    public Integer getUsuarioelimina() {
        return usuarioelimina;
    }

    public void setUsuarioelimina(Integer usuarioelimina) {
        this.usuarioelimina = usuarioelimina;
    }

    public Date getFechaEliminada() {
        return fechaEliminada;
    }

    public void setFechaEliminada(Date fechaEliminada) {
        this.fechaEliminada = fechaEliminada;
    }

    @Column(name="DescripcionBreve")
    private String DescripcionBreve;

    public String getDescripcionBreve() {
        return DescripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        DescripcionBreve = descripcionBreve;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechasolicitud() {
        return fechasolicitud;
    }

    public void setFechasolicitud(Date fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    public BigDecimal getSaldorecargar() {
        return saldorecargar;
    }

    public void setSaldorecargar(BigDecimal saldorecargar) {
        this.saldorecargar = saldorecargar;
    }

    public User getUsuariosolicitud() {
        return usuariosolicitud;
    }

    public void setUsuariosolicitud(User usuariosolicitud) {
        this.usuariosolicitud = usuariosolicitud;
    }

    public User getUsuariovalidador() {
        return usuariovalidador;
    }

    public void setUsuariovalidador(User usuariovalidador) {
        this.usuariovalidador = usuariovalidador;
    }

    public Date getFechavalidacion() {
        return fechavalidacion;
    }

    public void setFechavalidacion(Date fechavalidacion) {
        this.fechavalidacion = fechavalidacion;
    }

    public Boolean getEliminada() {
        return eliminada;
    }

    public void setEliminada(Boolean eliminada) {
        this.eliminada = eliminada;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final cmscategorias other = (cmscategorias) obj;
        if ((this.id == null) ? (other.getId() != null) : !this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }
}
