package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecargaSolicitudListItem implements Serializable {
    private Boolean seleccionado;
    private String nombre;
    private String apellidos;
    private String tipologia;
    private BigDecimal saldoVirtual;
    private String idEntidad;
    private Integer id;
    private Boolean checkEnabled;

    public RecargaSolicitudListItem(){}

    public RecargaSolicitudListItem(Boolean seleccionado, String nombre, String apellidos,
                                    String tipologia, BigDecimal saldoVirtual, String idEntidad,
                                    Integer id){
        try {
            this.seleccionado = seleccionado;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.tipologia = tipologia;
            this.saldoVirtual = saldoVirtual == null? new BigDecimal(0): saldoVirtual;
            this.idEntidad = idEntidad;
            //El id no lo muestro en las tablas, lo uso para controlar los checks
            this.id = id;
            this.checkEnabled = true;
        }
        catch(Exception e)
        {
            String debug = e.toString();
        }
    }

    public RecargaSolicitudListItem(Boolean seleccionado, String nombre, String apellidos,
                                    String tipologia, BigDecimal saldoVirtual, String idEntidad,
                                    Integer id, Boolean checkEnabled){
        try {
            this.seleccionado = seleccionado;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.tipologia = tipologia;
            this.saldoVirtual = saldoVirtual == null? new BigDecimal(0): saldoVirtual;
            this.idEntidad = idEntidad;
            //El id no lo muestro en las tablas, lo uso para controlar los checks
            this.id = id;
            this.checkEnabled = checkEnabled;
        }
        catch(Exception e)
        {
            String debug = e.toString();
        }
    }

    public Boolean getCheckEnabled() {
        return checkEnabled;
    }

    public void setCheckEnabled(Boolean checkEnabled) {
        this.checkEnabled = checkEnabled;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
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

    public String getTipologia() {
        return (tipologia==null?"":tipologia);
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public BigDecimal getSaldoVirtual() {
        return saldoVirtual;
    }

    public void setSaldoVirtual(BigDecimal saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
