package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "qr_respuestas")
public class qr_respuestas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @JoinTable(name = "qr_peticiones", joinColumns = {
            @JoinColumn(name = "peticionid")}, inverseJoinColumns = {
            @JoinColumn(name = "id")})
    @Column(name = "peticionid")
    private Integer peticionid;
    @Column(name = "caducidad")
    private Date caducidad;
    @Column(name = "pax")
    private Integer pax;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "modo")
    private String modo;
    @Column(name = "ruido")
    private String ruido;
    @Column(name = "json_encriptado")
    private String json_encriptado;
    @Column(name = "QR")
    private String QR;


    @Column(name="tarifa")
    private String tarifa;

    @Column(name="importe")
    private BigDecimal importe;

    public qr_respuestas(Integer peticionid, Date caducidad, Integer pax, Date fecha, String servicio,
                         String modo, String ruido, String json_encriptado, String tarifa, BigDecimal importe){
        this.peticionid = peticionid;
        this.caducidad = caducidad;
        this.pax = pax;
        this.fecha = fecha;
        this.servicio = servicio;
        this.modo = modo;
        this.ruido = ruido;
        this.json_encriptado = json_encriptado;
        this.tarifa = tarifa;
        this.importe = importe;
    }
    public void setQR(String QR)
    {
        this.QR = QR;
    }
    public int getId(){
        return this.id;
    }
    public String getJson_encriptado(){
        return this.json_encriptado;
    }
}
