/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "TiposCiudadanoAmbito")
public class TipoCiudadanoAmbito implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idTipoCiudadano", nullable = false, referencedColumnName = "id")
    private TipoCiudadano tipoCiudadano;
    
    @ManyToOne
    @JoinColumn(name = "idTipoAmbito", nullable = false, referencedColumnName = "id")
    private Ambito tipoAmbito;
    
    @Column(name = "porcentajePago")
    private BigDecimal porcentajePago;
    
    public TipoCiudadanoAmbito(){
        
    }

    public TipoCiudadanoAmbito(TipoCiudadano tipoCiudadano, Ambito tipoAmbito, BigDecimal porcentajePago) {
        this.tipoCiudadano = tipoCiudadano;
        this.tipoAmbito = tipoAmbito;
        this.porcentajePago = porcentajePago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPorcentajePago() {
        return porcentajePago;
    }
    
    public BigDecimal getPorcentajePagoDecimal() {
         return (porcentajePago != null ? porcentajePago.divide(new BigDecimal(100)) : new BigDecimal(1));
    }

    public void setPorcentajePago(BigDecimal porcentajePago) {
        this.porcentajePago = porcentajePago;
    }

    public Ambito getTipoAmbito() {
        return tipoAmbito;
    }

    public void setTipoAmbito(Ambito tipoAmbito) {
        this.tipoAmbito = tipoAmbito;
    }

    public TipoCiudadano getTipoCiudadano() {
        return tipoCiudadano;
    }

    public void setTipoCiudadano(TipoCiudadano tipoCiudadano) {
        this.tipoCiudadano = tipoCiudadano;
    }
}
