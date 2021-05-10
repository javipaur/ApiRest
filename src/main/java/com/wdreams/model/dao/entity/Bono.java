/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Bonos")
public class Bono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idBono", length = 6)
    private String idBono;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;

    public Integer getPax() {
        return pax;
    }

    public void setPax(Integer pax) {
        this.pax = pax;
    }

    @Column(name = "pax", nullable = true)
    protected Integer pax;

    @Column(name = "orden", nullable = true)
    protected Integer orden;

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getValidezQR() {
        return validezQR;
    }

    public void setValidezQR(Integer validezQR) {
        this.validezQR = validezQR;
    }

    @Column(name = "validezQR", nullable = true)
    protected Integer validezQR;




    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idAmbito", nullable = false, referencedColumnName = "id")
    private Ambito ambito;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "BonosCentros")
    private List<Centro> centros;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTipoBono", nullable = true, referencedColumnName = "id")
    private TipoBono tipoBono;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idConceptoBono", nullable = true, referencedColumnName = "id")
    private ConceptoBono conceptoBono;

    @Column(name = "numeroMaximoUsos")
    private Integer numeroMaximoUsos;

    @Column(name = "numeroMaximoUsosDiario")
    private Integer numeroMaximoUsosDiario;

    @Column(name = "numeroMaximoUsosMensual")
    private Integer numeroMaximoUsosMensual;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEstadoBono", nullable = true, referencedColumnName = "id")
    private EstadoBono estadoBono;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaInicial")
    private Date fechaInicial;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFinal")
    private Date fechaFinal;


    /**
     * Añadidos campos para ticketing
     */
    @Column(name = "maxdiasvalidez")
    private Integer maxDiasValidez;

    @Column(name = "datado")
    private Boolean datado;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "externalref")
    private String externalref;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "activo")
    private Boolean activo;







    public Bono() {
    }

    public Bono(String nombre,String descripcion, Ambito ambito, List<Centro> centros,
                TipoBono tipoBono, ConceptoBono conceptoBono, Integer numeroMaximoUsos,
                Integer numeroMaximoUsosDiario, Integer numeroMaximoUsosMensual,
                Date fechaInicial, Date fechaFinal, EstadoBono estadoBono) {
        this.nombre = nombre;
        this.ambito = ambito;
        this.centros = centros;
        this.tipoBono = tipoBono;
        this.conceptoBono = conceptoBono;
        this.numeroMaximoUsos = numeroMaximoUsos;
        this.numeroMaximoUsosDiario = numeroMaximoUsosDiario;
        this.numeroMaximoUsosMensual = numeroMaximoUsosMensual;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estadoBono = estadoBono;
        this.descripcion=descripcion;
    }

    public Bono(String nombre,String descripcion, Ambito ambito, List<Centro> centros,
                TipoBono tipoBono, ConceptoBono conceptoBono, Integer numeroMaximoUsos,
                Integer numeroMaximoUsosDiario, Integer numeroMaximoUsosMensual,
                Date fechaInicial, Date fechaFinal, EstadoBono estadoBono, Integer pax) {
        this.nombre = nombre;
        this.ambito = ambito;
        this.centros = centros;
        this.tipoBono = tipoBono;
        this.conceptoBono = conceptoBono;
        this.numeroMaximoUsos = numeroMaximoUsos;
        this.numeroMaximoUsosDiario = numeroMaximoUsosDiario;
        this.numeroMaximoUsosMensual = numeroMaximoUsosMensual;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estadoBono = estadoBono;
        this.descripcion=descripcion;
        this.pax = pax;
    }


    public Bono(String nombre,String descripcion, Ambito ambito, List<Centro> centros, TipoBono tipoBono,
                ConceptoBono conceptoBono, Integer numeroMaximoUsos, Integer numeroMaximoUsosDiario,
                Integer numeroMaximoUsosMensual, Date fechaInicial, Date fechaFinal,
                EstadoBono estadoBono, Integer pax, Integer maxdiasvalidez, Boolean datado,
                BigDecimal precio, String externalref, String grupo,
                String categoria, Boolean activo, Integer validezQR, Integer orden) {
        this.maxDiasValidez = maxdiasvalidez;
        this.datado = datado;
        this.precio = precio;
        this.externalref = externalref;
        this.grupo = grupo;
        this.categoria = categoria;
        this.activo = activo;

        this.nombre = nombre;
        this.ambito = ambito;
        this.centros = centros;
        this.tipoBono = tipoBono;
        this.conceptoBono = conceptoBono;
        this.numeroMaximoUsos = numeroMaximoUsos;
        this.numeroMaximoUsosDiario = numeroMaximoUsosDiario;
        this.numeroMaximoUsosMensual = numeroMaximoUsosMensual;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estadoBono = estadoBono;
        this.descripcion=descripcion;
        this.pax = pax;
        this.validezQR = validezQR;

        this.orden = orden;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdBono() {
        return idBono;
    }

    public void setIdBono(String idBono) {
        this.idBono = idBono;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public List<Centro> getCentros() {
        return centros;
    }

    public void setCentros(List<Centro> centros) {
        this.centros = centros;
    }

    public TipoBono getTipoBono() {
        return tipoBono;
    }

    public void setTipoBono(TipoBono tipoBono) {
        this.tipoBono = tipoBono;
    }

    public ConceptoBono getConceptoBono() {
        return conceptoBono;
    }

    public void setConceptoBono(ConceptoBono conceptoBono) {
        this.conceptoBono = conceptoBono;
    }

    public Integer getNumeroMaximoUsos() {
        return numeroMaximoUsos;
    }

    public void setNumeroMaximoUsos(Integer numeroMaximoUsos) {
        this.numeroMaximoUsos = numeroMaximoUsos;
    }

    public Integer getNumeroMaximoUsosDiario() {
        return numeroMaximoUsosDiario;
    }

    public void setNumeroMaximoUsosDiario(Integer numeroMaximoUsosDiario) {
        this.numeroMaximoUsosDiario = numeroMaximoUsosDiario;
    }

    public Integer getNumeroMaximoUsosMensual() {
        return numeroMaximoUsosMensual;
    }

    public void setNumeroMaximoUsosMensual(Integer numeroMaximoUsosMensual) {
        this.numeroMaximoUsosMensual = numeroMaximoUsosMensual;
    }

    public EstadoBono getEstadoBono() {
        return estadoBono;
    }

    public void setEstadoBono(EstadoBono estadoBono) {
        this.estadoBono = estadoBono;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void asignarIdBono() {
        this.setIdBono(String.format("BO%04X", this.getId()));
    }



    public Integer getMaxDiasValidez() {
        return maxDiasValidez;
    }

    public void setMaxDiasValidez(Integer maxDiasValidez) {
        this.maxDiasValidez = maxDiasValidez;
    }

    public Boolean getDatado() {
        return datado;
    }

    public void setDatado(Boolean datado) {
        this.datado = datado;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getExternalref() {
        return externalref;
    }

    public void setExternalref(String externalref) {
        this.externalref = externalref;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bono other = (Bono) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public class BonoDatosExport {

        public String getIdBono() {
            return Bono.this.idBono;
        }

        public Integer getOrden() {
            return Bono.this.orden;
        }

        public String getNombre() {
            return Bono.this.nombre;
        }

        public BigDecimal getPrecio() {
            return Bono.this.precio;
        }

        public String getAmbito() {
            return Bono.this.ambito.getNombre();
        }

//        public String getTipoBono() {
//            return Bono.this.tipoBono.getNombre();
//        }

//        public String getConcepto() {
//            return Bono.this.conceptoBono.getNombre();
//        }

        public String getEstado() {
            return Bono.this.estadoBono.getNombre();
        }

        public Date getFechaInicio() {
            return Bono.this.fechaInicial;
        }

        public Date getFechaFin() {
            return Bono.this.fechaFinal;
        }

        public Integer getNumeroMaximoUsos() {
            return Bono.this.numeroMaximoUsos;
        }

        public Integer getNumeroMaximoUsosDiario() {
            return Bono.this.numeroMaximoUsosDiario;
        }

        public Integer getNumeroMaximoUsosMensual() {
            return Bono.this.numeroMaximoUsosMensual;
        }
    }

}
