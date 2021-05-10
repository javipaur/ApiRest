package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Alberto
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "cmssecciones")
@DataTransferObject
public class cmssecciones implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "icono")
    private String icono;
    @Column(name = "link")
    private String link;
    @Column(name = "orden")
    private String orden;
    @Column(name = "mostrar")
    private Boolean mostrar;


    @Column(name = "esenlaceinterno")
    private Boolean esenlaceinterno;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enlaceinternoid")
    private seccionesappmovil seccionappmovil;

    public Boolean getEsenlaceinterno() {
        return esenlaceinterno;
    }

    public void setEsenlaceinterno(Boolean esenlaceinterno) {
        this.esenlaceinterno = esenlaceinterno;
    }

    public seccionesappmovil getSeccionappmovil() {
        return seccionappmovil;
    }

    public void setSeccionappmovil(seccionesappmovil seccionappmovil) {
        this.seccionappmovil = seccionappmovil;
    }

    public cmssecciones() {
    }

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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    /*Constructor usado en el alta de evento (EventosFormulario)*/
    public cmssecciones(String nombre, String icono,
                        String link, String orden, Boolean mostrar) {
//        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
        this.link = link;
        this.orden = orden;
        this.mostrar = mostrar;
    }


    public cmssecciones(String nombre, String icono,
                        String link, String orden, Boolean mostrar,
                        seccionesappmovil seccion, Boolean esenlaceinterno) {
//        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
        this.link = link;
        this.orden = orden;
        this.mostrar = mostrar;
        this.seccionappmovil = seccion;
        this.esenlaceinterno = esenlaceinterno;
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
