/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.rest.response;



import com.wdreams.model.dao.entity.Entidad;

import java.util.Date;

public class RespuestaInformacionCiudadano {

    private RespuestaMensaje respuesta;

    private String idCiudadano;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String movil;
    private String direccion;
    private String documento;
    private Date fechaNacimiento;
    private String email;
    private String estado;
    private String foto;
    private String uid;
    private String idTarjeta;

    private String sexo;
    private String ciudad;
    private String codPostal;

    public RespuestaInformacionCiudadano() {
    }

    public RespuestaInformacionCiudadano(RespuestaMensaje respuesta, Entidad ciudadano, String foto, String uid, String idTarjeta) {
        this.respuesta = respuesta;


        this.idCiudadano = ciudadano.getIdCiudadano();
        this.nombre = ciudadano.getNombre();
        this.apellidos = ciudadano.getApellidos();
        this.telefono = ciudadano.getTelefono();
        this.movil = ciudadano.getMovil();
        this.direccion = ciudadano.getDireccion();
        this.documento = ciudadano.getDocumento();
        this.fechaNacimiento = ciudadano.getFechaNacimiento();
        this.email = ciudadano.getMail();
        this.estado = ciudadano.getEstado().getNombre();
        this.foto = foto;
        this.uid = uid;
        this.idTarjeta = idTarjeta;

        if(ciudadano.getSexo() == null || ciudadano.getSexo() == false)
        {
            this.sexo = "MUJER";
        }
        else{
            this.sexo = "HOMBRE";
        }
        this.ciudad = ciudadano.getDirLocalidad();
        this.codPostal = ciudadano.getDirCp();
    }

    public RespuestaInformacionCiudadano(String funcion, String error, String error_mensaje, String idCiudadano, String nombre, String apellidos, String telefono, String direccion, String documento, Date fechaNacimiento, String email, String estado, String foto, String uid, String idTarjeta) {
        this.respuesta = new RespuestaMensaje(funcion, error, error_mensaje);
        this.idCiudadano = idCiudadano;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.movil = telefono;
        this.direccion = direccion;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.estado = estado;
        this.foto = foto;
        this.uid = uid;
        this.idTarjeta = idTarjeta;
    }


    public RespuestaMensaje getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaMensaje respuesta) {
        this.respuesta = respuesta;
    }


    public String getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
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


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getMovil() {
        return movil;
    }

    public void setMovil(String telefono) {
        this.movil = telefono;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getCiudad() {
        if (tryParseInt(ciudad)){
            //JaxRS lo parsear� como n�mero, devuelvo la cadena con un espacio
            return ciudad + " ";
        }
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
}
