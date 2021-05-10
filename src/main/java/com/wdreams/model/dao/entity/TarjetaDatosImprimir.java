/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.*;

/**
 *
 * @author rosa
 */
@XmlType(propOrder={"RFID", "nombre", "apellidos","idCiudadano","tipos","modalidad","idAux","tipoTarjeta","nombreImagen","code","fechaPedido","fechaCaducidad","fechaEnvio","saldo","direccion"})
public class TarjetaDatosImprimir implements Serializable{
    
    private String RFID;
    private String nombre;
    private String apellidos;
    private String idCiudadano;
    private ArrayList<Integer> tipos= new ArrayList<Integer>();
    private Integer modalidad;
    private String idAux;
    private Integer tipoTarjeta;
    private String nombreImagen;
    private String code;
    private String fechaPedido;
    private String fechaEnvio;
    private String fechaCaducidad;
    private String saldo;
    private String direccion;
    
    public TarjetaDatosImprimir(){}
    
    public TarjetaDatosImprimir(String RFID, String nombre, String apellidos, String idCiudadano, ArrayList<Integer> tipos, Integer modalidad, String idAux, Integer tipoTarjeta,String nombreImagen, String code, Date fechaPedido, Date fechaCaducidad, String saldo, String direccion) {
        this.RFID = RFID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idCiudadano = idCiudadano;
        for(Integer tipo:tipos){this.tipos.add(tipo);}
        if(tipos.isEmpty()){
            this.tipos.add(1);
        }
        this.modalidad = modalidad;
        this.idAux = idAux;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreImagen=nombreImagen;
        //if(imagen!=null) this.imagen = "data:image/jpeg;base64,"+imagen;
        this.code = idCiudadano;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd hh mm ss");
        this.fechaPedido = sdf.format(fechaPedido);
        if(fechaEnvio!=null) this.fechaCaducidad = sdf.format(fechaEnvio);
        else this.fechaEnvio = "";
        if(fechaCaducidad!=null) this.fechaCaducidad = sdf.format(fechaCaducidad);
        else this.fechaCaducidad = "";
        this.saldo = saldo;
        this.direccion = direccion;
    }
    
    //RFID
    public String getRFID() {
        return RFID;
    }
    
    @XmlElement(name="RFID")
    public void setRFID(String RFID) {
        this.RFID = RFID;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @XmlElement(name="nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Apellidos
    public String getApellidos() {
        return apellidos;
    }
    
    @XmlElement(name="apellidos")
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    //IdCiudadano
    public String getIdCiudadano() {
        return idCiudadano;
    }
    
    @XmlElement(name="idCiudadano")
    public void setIdCiudadano(String idCiudadano) {
         this.idCiudadano = idCiudadano;
    }
    
    //Tipos
    public ArrayList<Integer> getTipos() {
        return tipos;
    }
    
    @XmlElementWrapper(name="tipologia")
    @XmlElement(name="tipo")
    public void setTipos(ArrayList<Integer> tipos) {
        this.tipos = tipos;
    }
    
    //Modalidad
    public Integer getModalidad() {
        return modalidad;
    }
    
    @XmlElement(name="modalidad")
    public void setModalidad(Integer modalidad) {
        this.modalidad = modalidad;
    }
    
    //IdAux
    public String getIdAux() {
        return idAux;
    }
    
    @XmlElement(name="idAux")
    public void setIdAux(String idAux) {
        this.idAux = idAux;
    }
    
    //TipoTarjeta
    public Integer getTipoTarjeta() {
        return tipoTarjeta;
    }
    
    @XmlElement(name="tipoTarjeta")
    public void setTipoTarjeta(Integer tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    //Imagen
    public String getNombreImagen() {
        return nombreImagen;
    }

    @XmlElement(name="nombreImagen")
    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
 
    //FechaPedido
    public String getFechaPedido() {
        return fechaPedido;
    }
    
    @XmlElement(name="fechaPedido")
    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
    
    //FechaEnvio
    public String getFechaEnvio() {
        return fechaEnvio;
    }
    
    @XmlElement(name="fechaEnvio")
    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    //Direccion
    public String getDireccion() {
        return direccion;
    }

    @XmlElement(name="direccion")
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //FechaCaducidad
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    @XmlElement(name="fechaCaducidad")
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    //Saldo
    public String getSaldo() {
        return saldo;
    }

    @XmlElement(name="saldo")
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
