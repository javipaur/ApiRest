package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Alberto
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "beneficios")
@DataTransferObject
public class beneficios implements Serializable {

    @Column(name="criterio")
    private Integer criterio;

    @Column(name = "comercioId")
    private Integer comercioId;

    @Column(name="categoriaId")
    private Integer categoriaId;

    public Integer getCriterio() {
        return criterio;
    }

    public void setCriterio(Integer criterio) {
        this.criterio = criterio;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;


    @Column(name = "tipoBeneficioId")
    private Integer tipoBeneficioId;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "mostrar")
    private Boolean mostrar;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "comercioId", insertable = false, updatable = false, nullable = false, referencedColumnName = "id")
    private CentroComercio Comercio;

    public beneficios(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getComercioId() {
        return comercioId;
    }

    public void setComercioId(Integer comercioId) {
        this.comercioId = comercioId;
    }

    public Integer getTipoBeneficioId() {
        return tipoBeneficioId;
    }

    public void setTipoBeneficioId(Integer tipoBeneficioId) {
        this.tipoBeneficioId = tipoBeneficioId;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CentroComercio getComercio() {
        return Comercio;
    }

    public void setComercio(CentroComercio comercio) {
        Comercio = comercio;
    }

    public beneficios(String titulo, String descripcion, Integer comercioId, Integer tipoBeneficioId,
                      Integer orden, Boolean mostrar, Date fechaInicio, Date fechaFin, Integer criterioId,
                      Integer categoriaId)
    {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.comercioId = comercioId;
        this.tipoBeneficioId = tipoBeneficioId;
        this.orden = orden;
        this.mostrar = mostrar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.categoriaId = categoriaId;
        switch (criterioId)
        {
            //Si 0, va por comercio
            //Si 1, va por categoria
            case 0:
                this.categoriaId = null;
                break;
            case 1:
                this.comercioId = null;
                break;
            default:
                break;
        }
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
