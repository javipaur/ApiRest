/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import javax.persistence.*;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "Avisos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Aviso {
    
    public static int OPERACION_CORRECTA = 1;
    public static int TARJETA_PRUEBA = 2;

    public static int OPERACION_INICIADA = 7;//Añadir campo también en la bbdd

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    @Column(name = "numero")
    protected Integer numero;
    
    @Column(name = "descripcion")
    protected String descripcion;
    
    @Column(name = "incluidoCalculo")
    protected Boolean incluidoCalculo;
    
    public Aviso() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIncluidoCalculo() {
        return incluidoCalculo;
    }

    public void setIncluidoCalculo(Boolean incluidoCalculo) {
        this.incluidoCalculo = incluidoCalculo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public String getFullName(){
        return numero +" - "+descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Aviso other = (Aviso) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
