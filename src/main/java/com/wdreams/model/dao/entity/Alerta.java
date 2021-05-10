/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "Alertas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    @Index(name="idAlertaIndex")
    @Column(name = "idAlerta",length = 11)
    private String idAlerta;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTarjeta", nullable = false, referencedColumnName = "id")
    private Tarjeta tarjeta;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizacion")
    private Date fechaActualizacion;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idCompraSaldoInicialLeido", referencedColumnName = "id")
    private Compra compraSaldoInicialLeido;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idCompraSaldoFinalLeido", referencedColumnName = "id")
    private Compra compraSaldoFinalLeido;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idRecargaSaldoInicialLeido", referencedColumnName = "id")
    private Recarga recargaSaldoInicialLeido;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idRecargaSaldoFinalLeido", referencedColumnName = "id")
    private Recarga recargaSaldoFinalLeido;
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaSaldoInicialLeido", nullable = false)
    private Date fechaSaldoInicialLeido;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaSaldoFinalLeido", nullable = false)
    private Date fechaSaldoFinalLeido;
    
    @Column(name = "saldoInicialLeido")
    private Double saldoInicialLeido;
    
    @Column(name = "saldoFinalLeido")
    private Double saldoFinalLeido;
    
    @Column(name = "importeCompras")
    private Double importeCompras;
    
    @Column(name = "importeRecargas")
    private Double importeRecargas;
    
    public Alerta(){}
    
    public Alerta(Tarjeta tarjeta, Date fecha, Date fechaActualizacion, Compra compraSaldoInicialLeido, Compra compraSaldoFinalLeido, Recarga recargaSaldoInicialLeido, Recarga recargaSaldoFinalLeido, Date fechaSaldoInicialLeido, Date fechaSaldoFinalLeido, Double saldoInicialLeido, Double saldoFinalLeido, Double importeCompras, Double importeRecargas) {
        this.tarjeta=tarjeta;
        this.fecha=fecha;
        this.fechaActualizacion=fechaActualizacion;
        this.compraSaldoInicialLeido=compraSaldoInicialLeido;
        this.compraSaldoFinalLeido=compraSaldoFinalLeido;
        this.recargaSaldoInicialLeido=recargaSaldoInicialLeido;
        this.recargaSaldoFinalLeido=recargaSaldoFinalLeido;
        this.fechaSaldoInicialLeido=fechaSaldoInicialLeido;
        this.fechaSaldoFinalLeido=fechaSaldoFinalLeido;
        this.saldoInicialLeido=saldoInicialLeido;
        this.saldoFinalLeido=saldoFinalLeido;
        this.importeCompras=importeCompras;
        this.importeRecargas=importeRecargas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaSaldoFinalLeido() {
        return fechaSaldoFinalLeido;
    }

    public void setFechaSaldoFinalLeido(Date fechaSaldoFinalLeido) {
        this.fechaSaldoFinalLeido = fechaSaldoFinalLeido;
    }
    
    public Date getFechaSaldoInicialLeido() {
        return fechaSaldoInicialLeido;
    }

    public void setFechaSaldoInicialLeido(Date fechaSaldoInicialLeido) {
        this.fechaSaldoInicialLeido = fechaSaldoInicialLeido;
    }

    public String getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(String idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Compra getCompraSaldoFinalLeido() {
        return compraSaldoFinalLeido;
    }

    public void setCompraSaldoFinalLeido(Compra compraSaldoFinalLeido) {
        this.compraSaldoFinalLeido = compraSaldoFinalLeido;
    }

    public Compra getCompraSaldoInicialLeido() {
        return compraSaldoInicialLeido;
    }

    public void setCompraSaldoInicialLeido(Compra compraSaldoInicialLeido) {
        this.compraSaldoInicialLeido = compraSaldoInicialLeido;
    }

    public Recarga getRecargaSaldoFinalLeido() {
        return recargaSaldoFinalLeido;
    }

    public void setRecargaSaldoFinalLeido(Recarga recargaSaldoFinalLeido) {
        this.recargaSaldoFinalLeido = recargaSaldoFinalLeido;
    }

    public Recarga getRecargaSaldoInicialLeido() {
        return recargaSaldoInicialLeido;
    }

    public void setRecargaSaldoInicialLeido(Recarga recargaSaldoInicialLeido) {
        this.recargaSaldoInicialLeido = recargaSaldoInicialLeido;
    }

    public Double getSaldoFinalLeido() {
        return saldoFinalLeido;
    }

    public void setSaldoFinalLeido(Double saldoFinalLeido) {
        this.saldoFinalLeido = saldoFinalLeido;
    }

    public Double getSaldoInicialLeido() {
        return saldoInicialLeido;
    }

    public void setSaldoInicialLeido(Double saldoInicialLeido) {
        this.saldoInicialLeido = saldoInicialLeido;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Double getImporteCompras() {
        return importeCompras;
    }

    public void setImporteCompras(Double importeCompras) {
        this.importeCompras = importeCompras;
    }

    public Double getImporteRecargas() {
        return importeRecargas;
    }

    public void setImporteRecargas(Double importeRecargas) {
        this.importeRecargas = importeRecargas;
    }
    
    public void asignarIdAlerta(){
        this.idAlerta = "A" + String.format("%010X", this.id);
    }
}
