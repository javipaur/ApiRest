/**
 * Author Alberto
 */

package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "qr_peticiones")
public class qr_peticiones implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @JoinTable(name = "entidades", joinColumns = {
            @JoinColumn(name = "idCiudadano")}, inverseJoinColumns = {
            @JoinColumn(name = "id")})
    private String idCiudadano;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "modo")
    private String modo;
    @Column(name = "extra")
    private String extra;
    @Column(name = "unidades")
    private Integer unidades;
    @Column(name = "firma")
    private String firma;

    public qr_peticiones(){}
    public qr_peticiones(String idCiudadano, Date fecha, String servicio, String modo, String extra,
                         Integer unidades, String firma){
        this.idCiudadano = idCiudadano;
        this.fecha = fecha;
        this.servicio = servicio;
        this.modo = modo;
        this.extra = extra;
        this.unidades = unidades;
        this.firma = firma;
    }


    public int getId(){
        return this.id;
    }
}
