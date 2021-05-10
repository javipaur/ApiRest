package com.wdreams.model.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "historialespuntostipos")
public class historialespuntostipos implements Serializable {

    /**
     * 1 -> RECARGA
     * 2 -> USO
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "nombre")
    private String nombre;
}
