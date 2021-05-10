/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.LogUtils;
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
@Table(name = "LiquidacionesAmbito")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoLiquidacionAmbito",discriminatorType = DiscriminatorType.INTEGER)
public abstract class LiquidacionAmbito implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "idLiquidacion", nullable = false, referencedColumnName = "id")
    private Liquidacion liquidacion;
    
    @ManyToOne
    @JoinColumn(name = "idAmbito", nullable = false, referencedColumnName = "id")
    private Ambito ambito;
    
    @ManyToOne
    @JoinColumn(name = "idEstadoLiquidacion", nullable = false, referencedColumnName = "id")
    private EstadoLiquidacion estadoLiquidacion;
    
    @Column(name = "importeTotal", nullable = false)
    private Double importeTotal;
    
    @Column(name = "importeIngresar", nullable = false)
    private Double importeIngresar;
    
    @Column(name = "comisionCompra")
    private Integer comisionCompra;
    
    @Column(name = "comisionRecarga")
    private Integer comisionRecarga;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @OneToMany(mappedBy = "liquidacionAmbito", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Ingreso> listaIngresos;
    
    @OneToMany(mappedBy = "liquidacionAmbito", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy(value = "fecha")
    private List<HistorialLiquidacionAmbito> historialLiquidacionAmbito;
    
    public LiquidacionAmbito() {
    }
    
    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public Integer getComisionCompra() {
        return comisionCompra;
    }

    public void setComisionCompra(Integer comisionCompra) {
        this.comisionCompra = comisionCompra;
    }

    public Integer getComisionRecarga() {
        return comisionRecarga;
    }

    public void setComisionRecarga(Integer comisionRecarga) {
        this.comisionRecarga = comisionRecarga;
    }

    public EstadoLiquidacion getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(EstadoLiquidacion estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Ingreso> getListaIngresos() {
        return listaIngresos;
    }

    public void setListaIngresos(List<Ingreso> listaIngresos) {
        this.listaIngresos = listaIngresos;
    }

    public List<HistorialLiquidacionAmbito> getHistorialLiquidacionAmbito() {
        return historialLiquidacionAmbito;
    }

    public void setHistorialLiquidacionAmbito(List<HistorialLiquidacionAmbito> historialLiquidacionAmbito) {
        this.historialLiquidacionAmbito = historialLiquidacionAmbito;
    }

    public Double getImporteIngresar() {
        return importeIngresar;
    }

    public void setImporteIngresar(Double importeIngresar) {
        this.importeIngresar = importeIngresar;
    }
    
    public void altaHistorialLiquidacionAmbito(Dao dao, String desc){
        HistorialLiquidacionAmbito hla = new HistorialLiquidacionAmbito();
        hla.setFecha(new Date());
        hla.setEstadoLiquidacion(this.getEstadoLiquidacion());
        hla.setLiquidacionAmbito(this);
        hla.setUser(dao.getUserUsername(LogUtils.getUserName()));
        hla.setDescripcionTraza(desc);
        dao.altaHitorialLiquidacionAmbito(hla);
    }
    
    public abstract String getTipoLiquidacionAmbito();
}
