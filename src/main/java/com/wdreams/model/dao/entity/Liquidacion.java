/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "Liquidaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoLiquidacion",discriminatorType = DiscriminatorType.INTEGER)
public abstract class Liquidacion implements Serializable{
    
    public static Integer LIQUICACION_COMPRA=0;
    public static Integer LIQUICACION_RECARGA=1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "idLiquidacion")
    private String idLiquidacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    
    @OneToMany(mappedBy = "liquidacion", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<LiquidacionAmbito> listaLiquidacionesAmbito;
    
    public Liquidacion() {
        fecha=new Date();
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(String idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public List<LiquidacionAmbito> getListaLiquidacionesAmbito() {
        return listaLiquidacionesAmbito;
    }

    public void setListaLiquidacionesAmbito(List<LiquidacionAmbito> listaLiquidacionesAmbito) {
        this.listaLiquidacionesAmbito = listaLiquidacionesAmbito;
    }
    
    public void asignarIdLiquidacion(){
        this.setIdLiquidacion(String.format("L%05X", this.getId()));
    }
    
    public abstract String getTipoLiquidacion();
}
