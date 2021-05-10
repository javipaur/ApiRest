package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;

/**
 *
 * @author Juan
 */
@Entity
public class EntidadPrealta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    private String codigo;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private String movil;
    private Integer tipoDocumento;
    private String documento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    private String contrasenya;
    
    public void asignarCodigo() {
        this.setCodigo("CT" + String.format("%07X", 000000000l + this.getId()));
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @Override
    public String toString() {
        return "EntidadPrealta{" + "id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", apellidos=" + apellidos + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", movil=" + movil + ", tipoDocumento=" + tipoDocumento + ", documento=" + documento + ", fechaNacimiento=" + fechaNacimiento + ", contrasenya=" + contrasenya + '}';
    }

}
