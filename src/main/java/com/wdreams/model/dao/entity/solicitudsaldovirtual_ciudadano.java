package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Alberto 07/09/2020
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "solicitudsaldovirtual_ciudadano")
@DataTransferObject
public class solicitudsaldovirtual_ciudadano {
    public solicitudsaldovirtual_ciudadano() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idciudadano")
    private Integer ciudadano;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitudid")
    private solicitudsaldovirtual solicitud;

    @Column(name = "eliminado")
    private Boolean eliminado;

    @Column(name = "fechaEliminado")
    private Date fechaEliminado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioElimina")
    private User usuarioElimina;


    @Column(name = "validado")
    private Boolean validado;

    @Column(name = "fechaValidado")
    private Date fechaValidado;

    @Column(name = "usuarioValida")
    private Integer usuarioValida;

    public Integer getUsuarioValida() {
        return usuarioValida;
    }

    public void setUsuarioValida(Integer usuarioValida) {
        this.usuarioValida = usuarioValida;
    }

    public Date getFechaValidado() {
        return fechaValidado;
    }

    public void setFechaValidado(Date fechaValidado) {
        this.fechaValidado = fechaValidado;
    }

    public Boolean getValidado() {
        return validado;
    }


    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public User getUsuarioElimina() {
        return usuarioElimina;
    }

    public void setUsuarioElimina(User usuarioElimina) {
        this.usuarioElimina = usuarioElimina;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Date getFechaEliminado() {
        return fechaEliminado;
    }

    public void setFechaEliminado(Date fechaEliminado) {
        this.fechaEliminado = fechaEliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Integer ciudadano) {
        this.ciudadano = ciudadano;
    }

    public solicitudsaldovirtual getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(solicitudsaldovirtual solicitud) {
        this.solicitud = solicitud;
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
