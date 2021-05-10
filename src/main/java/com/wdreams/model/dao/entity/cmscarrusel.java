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
@Table(name = "cmscarrusel")
@DataTransferObject
public class cmscarrusel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "entradilla")
    private String entradilla;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "video")
    private String video;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "mostrar")
    private Boolean mostrar;



    public cmscarrusel() {
    }

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

    public String getEntradilla() {
        return entradilla;
    }

    public void setEntradilla(String entradilla) {
        this.entradilla = entradilla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    /*Constructor usado en el alta de evento (EventosFormulario)*/
    public cmscarrusel(String titulo, String entradilla, String descripcion, String imagen,
                       String video, Integer orden, Boolean mostrar) {
//        this.id = id;
        this.titulo = titulo;
        this.entradilla = entradilla;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.video = video;
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
        if ((this.id == null) ? (other.getId() != null) : !this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }
}
