/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.wdreams.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author rosa
 */
@XmlType(propOrder={"idCiudadano","UID","UIDDecimal","idTarjeta","fechaActivacion","fechaCaducidad","fechaBloqueo","calificacion","tiposCiudadano","tipoBloqueo","avisos","observaciones"})
public class TarjetaDatosEstado implements Serializable{
    
    private String idCiudadano;
    private String UID;
    private String UIDDecimal;
    private String idTarjeta;
    private String fechaActivacion;
    private String fechaCaducidad;
    private String fechaBloqueo;
    private String calificacion;
    private ArrayList<Integer> tiposCiudadano = new ArrayList<Integer>();
    private String tipoBloqueo;
    private String avisos;
    private String observaciones;
    
    public TarjetaDatosEstado(){}
    
    public TarjetaDatosEstado(String idCiudadano,String UID,String UIDDecimal,String idTarjeta,Date fechaActivacion,Date fechaCaducidad,Date fechaBloqueo,String calificacion,ArrayList<Integer> tiposCiudadano,String tipoBloqueo,String avisos,String observaciones) {
        this.idCiudadano = idCiudadano;
        this.UID = UID;
        this.UIDDecimal = UIDDecimal;
        this.idTarjeta = idTarjeta;
        if(fechaActivacion!=null){
            this.fechaActivacion = DateUtils.yyyyMMddHHmmss.format(fechaActivacion);
        }else{
            this.fechaActivacion = null;
        }
        if(fechaCaducidad!=null){
            this.fechaCaducidad = DateUtils.yyyyMMddHHmmss.format(fechaCaducidad);
        }else{
            this.fechaCaducidad = null;
        }
        if(fechaBloqueo!=null){
            this.fechaBloqueo = DateUtils.yyyyMMddHHmmss.format(fechaBloqueo);
        }else{
            this.fechaBloqueo = null;
        }
        this.calificacion = calificacion;
        for(Integer tipo:tiposCiudadano){this.tiposCiudadano.add(tipo);}
        this.tipoBloqueo = tipoBloqueo;
        this.avisos = avisos;
        this.observaciones = observaciones;
    }
    
    //idCiudadano
    public String getIdCiudadano() {
        return idCiudadano;
    }
    @XmlElement(name="identificador_ciudadano")
    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
    }
    
    //UID
    public String getUID() {
        return UID;
    }
    @XmlElement(name="rfid")
    public void setUID(String UID) {
        this.UID = UID;
    }
    
    //UID decimal
    public String getUIDDecimal() {
        return UIDDecimal;
    }
    @XmlElement(name="rfid_decimal")
    public void setUIDDecimal(String UIDDecimal) {
        this.UIDDecimal = UIDDecimal;
    }
    
    //idTarjeta
    public String getIdTarjeta() {
        return idTarjeta;
    }
    @XmlElement(name="identificador_tarjeta")
    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
    
    //fechaActivacion
    public String getFechaActivacion() {
        return fechaActivacion;
    }
    @XmlElement(name="fecha_activacion")
    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }
    
    //fechaCaducidad
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }
    @XmlElement(name="fecha_caducidad")
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
    //fechaBloqueo
    public String getFechaBloqueo() {
        return fechaBloqueo;
    }
    @XmlElement(name="fecha_bloqueo")
    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }
    
    //calificacion
    public String getCalificacion() {
        return calificacion;
    }
    @XmlElement(name="calificacion")
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
    
    //tipologias
    public ArrayList<Integer> getTiposCiudadano() {
        return tiposCiudadano;
    }
    @XmlElementWrapper(name="tipos_ciudadano")
    @XmlElement(name="id_tipo_ciudadano")
    public void setTiposCiudadano(ArrayList<Integer> tiposCiudadano) {
        this.tiposCiudadano = tiposCiudadano;
    }
    
    //tipoBloqueo
    public String getTipoBloqueo() {
        return tipoBloqueo;
    }
    @XmlElement(name="tipo_bloqueo")
    public void setTipoBloqueo(String tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }
    
    //avisos
    public String getAvisos() {
        return avisos;
    }
    @XmlElement(name="avisos")
    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }
    
    //observaciones
    public String getObservaciones() {
        return observaciones;
    }
    @XmlElement(name="observaciones")
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
