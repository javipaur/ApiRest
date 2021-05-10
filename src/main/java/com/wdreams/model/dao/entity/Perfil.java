/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Perfiles")
public class Perfil implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idPerfil", length = 5)
    private String idPerfil;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "mostrar")
    private Boolean mostrar = Boolean.TRUE;

    @Column(name = "orden")
    private Integer orden;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "perfilesRoles", joinColumns = {
        @JoinColumn(name = "idPerfil")}, inverseJoinColumns = {
        @JoinColumn(name = "idRole")})
    @Fetch(FetchMode.SELECT)
    protected Set<Rol> roles;

    @Column(name = "requiereAmbito")
    protected Boolean requiereAmbito;

    @Column(name = "requiereCentro")
    protected Boolean requiereCentro;

    @Column(name = "requierePuestoImpresion")
    protected Boolean requierePuestoImpresion;

    @Column(name = "requiereComercio")
    protected Boolean requiereComercio;

    public Perfil() {
    }

    public Perfil(String nombre, Set<Rol> roles, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Boolean requiereComercio) {
        this.nombre = nombre;
        this.roles = roles;
        this.requiereAmbito = requiereAmbito;
        this.requiereCentro = requiereCentro;
        this.requierePuestoImpresion = requierePuestoImpresion;
        this.requiereComercio = requiereComercio;
    }

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

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

   public Boolean getRequiereAmbito() {
        return requiereAmbito != null && requiereAmbito;
    }


    public void setRequiereAmbito(Boolean requiereAmbito) {
        this.requiereAmbito = requiereAmbito;
    }

    public Boolean getRequiereCentro() {
        return requiereCentro != null && requiereCentro;
    }

    public void setRequiereCentro(Boolean requiereCentro) {
        this.requiereCentro = requiereCentro;
    }

    public Boolean getRequierePuestoImpresion() {
        return requierePuestoImpresion != null && requierePuestoImpresion;
    }

    public void setRequierePuestoImpresion(Boolean requierePuestoImpresion) {
        this.requierePuestoImpresion = requierePuestoImpresion;
    }

    public Boolean getRequiereComercio() {
        return requiereComercio;
    }

    public void setRequiereComercio(Boolean requiereComercio) {
        this.requiereComercio = requiereComercio;
    }

    public void asignarIdPerfil(){
        this.setIdPerfil(String.format("PE%03X", this.getId()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Perfil other = (Perfil) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public class PerfilDatosExport {

        public String getIdPerfil() {
            return Perfil.this.getIdPerfil();
        }

        public String getNombre() {
            return Perfil.this.getNombre();
        }

        public Boolean getRequiereAmbito() {
            return Perfil.this.getRequiereAmbito();
        }

        public Boolean getRequiereCentro() {
            return Perfil.this.getRequiereCentro();
        }

        public Boolean getRequierePuestoImpresion() {
            return Perfil.this.getRequierePuestoImpresion();
        }

        public Boolean getRequiereComercio() {
            return Perfil.this.getRequiereComercio();
        }
    }

}
