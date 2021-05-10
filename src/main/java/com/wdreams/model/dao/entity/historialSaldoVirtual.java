package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Alberto 07/09/2020
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "historialsaldovirtual")
@DataTransferObject
public class historialSaldoVirtual {

    public historialSaldoVirtual() {
    }

    ;
//    public historialSaldoVirtual(String descripcion, BigDecimal saldo, solicitudsaldovirtual solicitud,
//                                Date fecha)
//    {
//        this.descripcion = descripcion;
//        this.saldo = saldo;
//        this.solicitud = solicitud;
//    }

    /**
     * Para eliminar
     * @param descripcion
     * @param saldo
     * @param solicitud
     * @param idCiudadano
     * @param eliminado
     * @param fechaEliminado
     * @param usuarioElimina
     */
    public historialSaldoVirtual(String descripcion, BigDecimal saldo, solicitudsaldovirtual solicitud,
                                 Integer idCiudadano, Boolean eliminado, Date fechaEliminado,
                                 Integer usuarioElimina) {
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.solicitud = solicitud;
        this.idCiudadano = idCiudadano;
        this.eliminado = eliminado;
        this.fechaEliminado = fechaEliminado;
        this.fecha = fechaEliminado;
        this.usuarioElimina = usuarioElimina;
    }

    /**
     * Para validar
     * @param descripcion
     * @param saldo
     * @param solicitud
     * @param idCiudadano
     * @param eliminado
     * @param fechaEliminado
     * @param usuarioElimina
     * @param fecha
     */
    public historialSaldoVirtual(String descripcion, BigDecimal saldo, solicitudsaldovirtual solicitud,
                                 Integer idCiudadano, Boolean eliminado, Date fechaEliminado,
                                 Integer usuarioElimina, Date fecha) {
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.solicitud = solicitud;
        this.idCiudadano = idCiudadano;
        this.eliminado = false;
        this.fechaEliminado = null;
        this.fecha = fecha;
        this.usuarioElimina = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "fecha")
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "idCiudadano")
    private Integer idCiudadano;
    @Column(name = "eliminado")
    private Boolean eliminado;
    @Column(name = "fechaEliminado")
    private Date fechaEliminado;
    @Column(name = "usuarioElimina")
    private Integer usuarioElimina;

    public Integer getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(Integer idCiudadano) {
        this.idCiudadano = idCiudadano;
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

    public Integer getUsuarioElimina() {
        return usuarioElimina;
    }

    public void setUsuarioElimina(Integer usuarioElimina) {
        this.usuarioElimina = usuarioElimina;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitudid")
    private solicitudsaldovirtual solicitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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
        if (validacionesEquals(obj)) return false;
        return true;
    }

    private boolean validacionesEquals(Object obj) {
        if (obj == null) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return true;
        }
        final cmscategorias other = (cmscategorias) obj;
        if ((this.id == null) ? (other.getId() != null) : !this.id.equals(other.getId())) {
            return true;
        }
        return false;
    }
}
