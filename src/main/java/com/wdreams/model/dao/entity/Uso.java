/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.utils.DateUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"tarjeta", "usuario", "centro", "dispositivo", "aviso"})
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Uso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "idUso", length = 11)
    protected String idUso;

    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "fechaRealizacionIndex")
    @Column(name = "fechaRealizacion", nullable = false)
    protected Date fechaRealizacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaRegistro", nullable = false)
    protected Date fechaRegistro;

    @Index(name = "fechaRealizacionIntIndex")
    @Column(name = "fechaRealizacionInt", nullable = false)
    protected Integer fechaRealizacionInt;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idCentro", nullable = false, referencedColumnName = "id")
    protected Centro centro;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idDispositivo", referencedColumnName = "id")
    protected Dispositivo dispositivo;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idUsuario", referencedColumnName = "id")
    protected Usuario usuario;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTarjeta", nullable = false, referencedColumnName = "id")
    protected Tarjeta tarjeta;

    @Column(name = "descripcion")
    protected String descripcion;

    @Column(name = "numeroOperacion", length = 20)
    protected String numeroOperacion;

    @Column(name = "operacionAnulada", nullable = true)
    protected Integer idOperacionAnulada;

    @ManyToOne
    @JoinColumn(name = "idAviso", referencedColumnName = "id")
    protected Aviso aviso;

    @Column(name = "totalrecords", updatable = false, insertable = false)
    private Integer totalRecords;

    public Uso(Date fechaRealizacion, Date fechaRegistro, Centro centro, Dispositivo dispositivo, Usuario usuario, Tarjeta tarjeta, String descripcion, String numeroOperacion, Integer idOperacionAnulada, Aviso aviso) {
        this.fechaRealizacion = fechaRealizacion;
        this.fechaRealizacionInt = Integer.parseInt(DateUtils.yyyyMMdd.format(fechaRealizacion));
        this.fechaRegistro = fechaRegistro;
        this.centro = centro;
        this.dispositivo = dispositivo;
        this.usuario = usuario;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.numeroOperacion = numeroOperacion;
        this.idOperacionAnulada = idOperacionAnulada;
        this.aviso = aviso;
    }

    public Uso() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdUso() {
        return idUso;
    }

    public void setIdUso(String idUso) {
        this.idUso = idUso;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
        this.fechaRealizacionInt = Integer.parseInt(DateUtils.yyyyMMdd.format(fechaRealizacion));
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    protected void asignarIdUso(String def) {
        this.idUso = def + String.format("%010X", this.id);
    }

    public abstract void asignarIdUso();

    public Integer getIdOperacionAnulada() {
        return idOperacionAnulada;
    }

    public void setIdOperacionAnulada(Integer idOperacionAnulada) {
        this.idOperacionAnulada = idOperacionAnulada;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public void setAviso(Aviso aviso) {
        this.aviso = aviso;
    }

    public Integer getFechaRealizacionInt() {
        return fechaRealizacionInt;
    }

    public void setFechaRealizacionInt(Integer fechaRealizacionInt) {
        this.fechaRealizacionInt = fechaRealizacionInt;
    }

}
