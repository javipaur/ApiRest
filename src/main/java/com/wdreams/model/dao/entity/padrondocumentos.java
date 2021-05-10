package com.wdreams.model.dao.entity;


import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Alberto, en Demo nos pasan un excel con los documentos
 */
@Entity
@Table(name = "padrondocumentos")
@DataTransferObject
public class padrondocumentos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "documento")
    private String documento;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "tipodocumento", nullable = true, referencedColumnName = "id")
    protected TipoDocumento tipoDocumento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    public padrondocumentos(String documento, TipoDocumento tipoDocumento, String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
    }

    public padrondocumentos() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumento getTipodocumento() {
        return tipoDocumento;
    }

    public void setTipodocumento(TipoDocumento tipodocumento) {
        this.tipoDocumento = tipodocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 100 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final padrondocumentos other = (padrondocumentos) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
