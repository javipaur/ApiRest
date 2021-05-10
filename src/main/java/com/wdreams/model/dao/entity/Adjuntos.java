package com.wdreams.model.dao.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "Adjuntos")
public class Adjuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", length = 150)
    private String nombre;
    
    @Column(name = "carpeta", length = 100  )
    private String carpeta;

    @Column(name = "revisado")
    private Boolean revisado;
    
    @Column(name = "fechaInsercion")
    private Date fechaInsercion;
    
    @Column(name = "tipoEntidad")
    private int tipoEntidad;
    
    @Column(name = "idEntidad")
    private String idEntidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public int getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(int tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    @Override
    public String toString() {
        return "Adjuntos{" + "id=" + id + ", nombre=" + nombre + ", carpeta=" + carpeta + ", revisado=" + revisado + ", fechaInsercion=" + fechaInsercion + ", tipoEntidad=" + tipoEntidad + ", idEntidad=" + idEntidad + '}';
    }
   
}
