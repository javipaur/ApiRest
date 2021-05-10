package com.wdreams.model.dao.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Juan
 */
@Entity
@Table(name = "Centros")
@DiscriminatorValue("0")
public class CentroAyuntamiento extends Centro {

    public CentroAyuntamiento() {
    }
    @Column(name = "gestionaPuntos")
    private Boolean gestionaPuntos;
    public Boolean getGestionaPuntos() {
        return gestionaPuntos;
    }

    public void setGestionaPuntos(Boolean gestionaPuntos) {
        this.gestionaPuntos = gestionaPuntos;
    }

    public CentroAyuntamiento(String nombre, String descripcion, Ambito ambito, String mail, Boolean gestionaPuntos) {
        super(nombre, descripcion, ambito, mail);

        this.gestionaPuntos = gestionaPuntos;
    }
    

}
    