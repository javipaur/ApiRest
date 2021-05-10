/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;
import java.io.Serializable;
import javax.persistence.*;
/**
 *
 * @author rosa
 */
@Entity
@Table(name = "TiposCiudadanoRestriccion")
public class TipoCiudadanoRestriccion implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    
  /*  @ManyToOne
    @JoinColumn(name = "idTipoCiudadano", nullable = false, referencedColumnName = "id")
    private TipoCiudadano tipoCiudadano;*/
    
    @Column(name = "restriccion", length = 50)
    private String restriccion;
    
    @Column(name = "edad")
    private Integer edad;

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

 /*   public TipoCiudadano getTipoCiudadano() {
        return tipoCiudadano;
    }

    public void setTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.tipoCiudadano = tipoCiudadano;
    }*/
    
}
