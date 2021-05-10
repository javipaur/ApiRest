/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "CentrosRecogida")
public class CentroRecogida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idCentroRecogida",length = 5)
    private String idCentroRecogida;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "via")
    private String via;

    @Column(name = "numero")
    private String numero;

    @Column(name = "piso")
    private String piso;

    @Column(name = "puerta")
    private String puerta;

    @Column(name = "cp")
    private String cp;
    
     @Column(name = "horario", length = 255)
    private String horario;

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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getIdCentroRecogida() {
        return idCentroRecogida;
    }

    public void setIdCentroRecogida(String idCentroRecogida) {
        this.idCentroRecogida = idCentroRecogida;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final CentroRecogida other = (CentroRecogida) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}