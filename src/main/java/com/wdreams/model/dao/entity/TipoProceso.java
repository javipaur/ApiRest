/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "TiposProceso")
public class TipoProceso implements Serializable{
    
    public static final Integer PROCESO_RANDOM = 1;
    public static final Integer ALTA_USUARIOS = 2;
    public static final Integer ALTA_CIUDADANOS = 3;
    public static final Integer LISTA_NEGRA = 4;
    public static final Integer LISTA_BLANCA = 5;
    public static final Integer COMPROBAR_PADRON = 6;
    public static final Integer PROCESO_RANDOM2 = 7;
    
    public static class FileProvider{
        public static int FILE_UPLOAD = 1;
        public static int FTP=2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", length = 25)
    private String titulo;
    
   /*  @Column(name = "requiereArchivo")
    private Boolean requiereArchivo;*/
     
    @Column(name = "requiereArchivo")
    private Integer requiereArchivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

  /*  public Boolean getRequiereArchivo() {
        return requiereArchivo;
    }

    public void setRequiereArchivo(Boolean requiereArchivo) {
        this.requiereArchivo = requiereArchivo;
    }*/

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final TipoProceso other = (TipoProceso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Integer getRequiereArchivo() {
        return requiereArchivo;
    }

    public void setRequiereArchivo(Integer requiereArchivo) {
        this.requiereArchivo = requiereArchivo;
    }

}
