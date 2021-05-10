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
@Table(name = "EstadosLiquidacion")
public class EstadoLiquidacion implements Serializable{
    
    public static final Integer ABIERTA=1;
    public static final Integer LIQUIDADA=2;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "permiteModificarUsos", nullable = false)
    private Boolean permiteModificarUsos;
    
    @Column(name = "permiteModificarIngresos", nullable = false)
    private Boolean permiteModificarIngresos;

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

    public Boolean getPermiteModificarIngresos() {
        return permiteModificarIngresos;
    }

    public void setPermiteModificarIngresos(Boolean permiteModificarIngresos) {
        this.permiteModificarIngresos = permiteModificarIngresos;
    }

    public Boolean getPermiteModificarUsos() {
        return permiteModificarUsos;
    }

    public void setPermiteModificarUsos(Boolean permiteModificarUsos) {
        this.permiteModificarUsos = permiteModificarUsos;
    }
}
