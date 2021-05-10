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
@Table(name = "cmseventos")
@DataTransferObject
public class cmseventos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "eventosCategoriaId")
    private Integer eventosCategoriaId;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "texto")
    private String texto;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "enlace")
    private String enlace;
    @Column(name = "mostrar")
    private Boolean mostrar;
    @Column(name = "Entradilla1")
    private String Entradilla1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventosCategoriaId() {
        return eventosCategoriaId;
    }

    public void setEventosCategoriaId(Integer eventosCategoriaId) {
        this.eventosCategoriaId = eventosCategoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {

        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public String getEntradilla1() {
        return Entradilla1;
    }

    public void setEntradilla1(String entradilla1) {
        Entradilla1 = entradilla1;
    }


    public cmseventos() {
    }

    /*Constructor usado en el alta de evento (EventosFormulario)*/
    public cmseventos(Integer eventosCategoriaId, String titulo, Date fecha,
                      String texto, String imagen, String enlace,
                      Boolean mostrar, String Entradilla1) {
        this.eventosCategoriaId = eventosCategoriaId;
        this.titulo = titulo;
        this.fecha = fecha;
        this.texto = texto;
        this.imagen = imagen;
        this.enlace = enlace;
        this.mostrar = mostrar;
        this.Entradilla1 = Entradilla1;
    }

    /*Constructor usado en el alta de evento (EventosFormulario)*/
    public cmseventos(String titulo, Date fecha,
                      String texto, String imagen, String enlace,
                      Boolean mostrar, String Entradilla1) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.texto = texto;
        this.imagen = imagen;
        this.enlace = enlace;
        this.mostrar = mostrar;
        this.Entradilla1 = Entradilla1;
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
