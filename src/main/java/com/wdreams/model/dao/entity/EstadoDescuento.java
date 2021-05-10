package com.wdreams.model.dao.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EstadosDescuento")
public class EstadoDescuento {
    
    public static final Integer ACTIVO =1;
    public static final Integer BLOQUEADO =2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 20)
    private String nombre;

    @Column(name = "estadoGeneral")
    private Integer estadoGeneral;

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

    public Integer getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(Integer estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final EstadoDescuento other = (EstadoDescuento) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
