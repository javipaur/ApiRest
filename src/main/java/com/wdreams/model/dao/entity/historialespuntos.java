package com.wdreams.model.dao.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "historialespuntos")
public class historialespuntos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "cantidadpuntos")
    private BigDecimal cantidadpuntos;
    @ManyToOne
    @JoinColumn(name = "identidad", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Entidad entidad;
//    @ManyToOne
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "identidad", nullable = false, referencedColumnName = "id")
//    private Integer entidad;
    @ManyToOne
    @JoinColumn(name = "idcentro", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Centro centro;
//    @ManyToOne
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "idcentro", nullable = false, referencedColumnName = "id")
//    private Integer centro;
    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private User user;
//    @ManyToOne
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "iduser", nullable = true, referencedColumnName = "id")
//    private Integer user;
    @ManyToOne
    @JoinColumn(name = "tipo", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private historialespuntostipos tipo;
//    @ManyToOne
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "tipo", nullable = true, referencedColumnName = "id")
//    private Integer tipo;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCantidadpuntos() {
        return cantidadpuntos;
    }

    public void setCantidadpuntos(BigDecimal cantidadpuntos) {
        this.cantidadpuntos = cantidadpuntos;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public historialespuntostipos getTipo() {
        return tipo;
    }

    public void setTipo(historialespuntostipos tipo) {
        this.tipo = tipo;
    }
}
