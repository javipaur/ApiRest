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
@Table(name = "Restricciones")
public class Restriccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTipoCiudadano", nullable = false, referencedColumnName = "id")
    private TipoCiudadano tipoCiudadano;

    @ManyToOne
    @JoinColumn(name = "idTipoRestriccion", nullable = false, referencedColumnName = "id")
    private TipoRestriccion tipoRestriccion;

    @Column(name = "valorInicial")
    private String valorInicial;

    @Column(name = "valorFinal")
    private String valorFinal;

    public Restriccion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoCiudadano getTipoCiudadano() {
        return tipoCiudadano;
    }

    public void setTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.tipoCiudadano = tipoCiudadano;
    }

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getValorInicialForm() {
        if (this.tipoRestriccion.getCampos() != null && !this.tipoRestriccion.getCampos().isEmpty()) {
            for (TipoRestriccionCampo campo : this.getTipoRestriccion().getCampos()) {
                if (campo.getValor().equals(this.getValorInicial())) {
                    return campo.getNombre();
                }
            }
        }
        return valorInicial;
    }

    public String getValorFinalForm() {
        if (this.tipoRestriccion.getCampos() != null && !this.tipoRestriccion.getCampos().isEmpty()) {
            for (TipoRestriccionCampo campo : this.getTipoRestriccion().getCampos()) {
                if (campo.getValor().equals(this.getValorFinal())) {
                    return campo.getNombre();
                }
            }
        }
        return valorFinal;
    }
    
    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

}
