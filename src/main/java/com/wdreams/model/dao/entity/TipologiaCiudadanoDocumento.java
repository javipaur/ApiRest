/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "TipologiasCiudadanoDocumento")
public class TipologiaCiudadanoDocumento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idTipologiaCiudadano", nullable = false, referencedColumnName = "id")
    private TipologiaCiudadano tipologiaCiudadano;
    
    @ManyToOne
    @JoinColumn(name = "idTipoCiudadanoDocumento", nullable = false, referencedColumnName = "id")
    private TipoCiudadanoDocumento tipoCiudadanoDocumento;
    
    @Column(name = "nombre", length = 50)
    private String nombre;
    
    @Column(name = "ruta", length = 150)
    private String ruta;
    
    public TipologiaCiudadanoDocumento() {
    }

    public TipologiaCiudadanoDocumento(TipologiaCiudadano tipologiaCiudadano, TipoCiudadanoDocumento tipoCiudadanoDocumento, String nombre, String ruta){
        this.tipologiaCiudadano = tipologiaCiudadano;
        this.tipoCiudadanoDocumento = tipoCiudadanoDocumento;
        this.nombre = nombre;
        this.ruta = ruta;
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public TipoCiudadanoDocumento getTipoCiudadanoDocumento() {
        return tipoCiudadanoDocumento;
    }

    public void setTipoCiudadanoDocumento(TipoCiudadanoDocumento tipoCiudadanoDocumento) {
        this.tipoCiudadanoDocumento = tipoCiudadanoDocumento;
    }

    public TipologiaCiudadano getTipologiaCiudadano() {
        return tipologiaCiudadano;
    }

    public void setTipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano) {
        this.tipologiaCiudadano = tipologiaCiudadano;
    }

}
