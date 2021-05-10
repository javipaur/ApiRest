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
@Table(name = "TiposDocumento")
public class TipoDocumento implements Serializable {

    public static final Integer SIN_DOCUMENTO = 1;
    public static final Integer DNI_ESPANNOL = 2;
    public static final Integer PASAPORTE = 3;
    public static final Integer NIE = 4;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", length = 50)
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final TipoDocumento other = (TipoDocumento) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
     public int equivalenciaPadron(){
       if(this.id == TipoDocumento.SIN_DOCUMENTO){
           return 0;
       }else if(this.id == TipoDocumento.NIE){
           return 3;
       }else if(this.id == TipoDocumento.PASAPORTE){
           return 2;
       }else{
           return 1;
       }
                
    }

}
