/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Alberto
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "tipoCentro",
//        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "cmscategorias")
@DataTransferObject
public class cmscategorias implements Serializable {

    public static Integer CENTROS_AYUNTAMIENTO = 0;
    public static Integer CENTROS_COMERCIO = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "color")
    private String color;
    @Column(name = "icono")
    private String icono;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "mostrar")
    private Boolean mostrar;
    @Column(name = "tipoCentro")
    private Integer tipoCentro;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
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

    public Integer getTipoCentro() {
        return tipoCentro;
    }

    public void setTipoCentro(Integer tipoCentro) {
        this.tipoCentro = tipoCentro;
    }

    public cmscategorias() {
    }




    /*Constructor usado en el alta de centro (CentroFormulario)*/
    public cmscategorias(String nombre, String color, String icono, Integer orden, Boolean mostrar, Integer tipoCentro) {
        this.nombre = nombre;
        this.color = color;
        this.icono = icono;
        this.orden = orden;
        this.mostrar = mostrar;
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
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


}
