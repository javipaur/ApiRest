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
@Table(name = "cmscontenidos")
@DataTransferObject
public class cmscontenidos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "seccionid")
    private Integer seccionid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seccionid", insertable = false, updatable = false)
    public cmssecciones cmssecciones;

    @Column(name = "categoria")
    private String categoria;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "foto")
    private String foto;
    @Column(name = "link")
    private String link;
    @Column(name = "entradilla")
    private String entradilla;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "mostrar")
    private Boolean mostrar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeccionid() {
        return seccionid;
    }

    public void setSeccionid(Integer seccionid) {
        this.seccionid = seccionid;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public cmscontenidos() {
    }


    /*Constructor usado en el alta de evento (EventosFormulario)*/
    public cmscontenidos(Integer seccionid, String categoria, String titulo, String foto,
                         String link, String entradilla, String descripcion, Integer orden, Boolean mostrar) {
//        this.id = id;
        this.seccionid = seccionid;
        this.categoria = categoria;
        this.titulo = titulo;
        this.foto = foto;
        this.link = link;
        this.entradilla = entradilla;
        this.descripcion = descripcion;
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
