/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author victor
 */

@Entity
@Table(name = "TipoRestriccionCampos")
public class TipoRestriccionCampo {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idTipoRestriccion", nullable = false, referencedColumnName = "id")
    private TipoRestriccion tipoRestriccion;
    
    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "valor", length = 10)
    private String valor;

    public TipoRestriccionCampo() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
    
}
