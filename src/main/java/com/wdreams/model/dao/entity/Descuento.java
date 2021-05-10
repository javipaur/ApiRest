package com.wdreams.model.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "Descuentos")
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idDescuento", length = 7)
    private String idDescuento;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "indiceCobro", nullable = false)
    private Integer indiceCobro;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaInicial")
    private Date fechaInicial;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFinal")
    private Date fechaFinal;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idAmbito", nullable = false, referencedColumnName = "id")
    private Ambito ambito;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEstadoDescuento", nullable = true, referencedColumnName = "id")
    private EstadoDescuento estadoDescuento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tiposCiudadanosDescuento",
            joinColumns = {
                @JoinColumn(name = "idDescuento")},
            inverseJoinColumns = {
                @JoinColumn(name = "idTipoCiudadano")}
    )
    @Fetch(FetchMode.SELECT)
    private List<TipoCiudadano> tiposCiudadanoAplicable;
    
    @Column(name = "tratamientoTiposCiudadano", length = 3, nullable = false)
    private String tratamientoTiposCiudadano;

    public Descuento() {}

    public Descuento(String nombre, String descripcion, Integer indiceCobro, Ambito ambito, EstadoDescuento estadoDescuento, List<TipoCiudadano> tiposCiudadanoAplicable, Date fechaInicial, Date fechaFinal, String tratamientoTiposCiudadano) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.indiceCobro = indiceCobro;
        this.ambito = ambito;
        this.estadoDescuento = estadoDescuento;
        this.tiposCiudadanoAplicable = tiposCiudadanoAplicable;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.tratamientoTiposCiudadano = tratamientoTiposCiudadano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(String idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIndiceCobro() {
        return indiceCobro;
    }

    public void setIndiceCobro(Integer indiceCobro) {
        this.indiceCobro = indiceCobro;
    }

    public EstadoDescuento getEstadoDescuento() {
        return estadoDescuento;
    }

    public void setEstadoDescuento(EstadoDescuento estadoDescuento) {
        this.estadoDescuento = estadoDescuento;
    }

    public List<TipoCiudadano> getTiposCiudadanoAplicable() {
        return tiposCiudadanoAplicable;
    }

    public void setTiposCiudadanoAplicable(List<TipoCiudadano> tiposCiudadanoAplicable) {
        this.tiposCiudadanoAplicable = tiposCiudadanoAplicable;
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

    public String getTratamientoTiposCiudadano() {
        return tratamientoTiposCiudadano;
    }

    public void setTratamientoTiposCiudadano(String tratamientoTiposCiudadano) {
        this.tratamientoTiposCiudadano = tratamientoTiposCiudadano;
    }
    
    public void asignarIdDescuento() {
        this.idDescuento = (String.format("DE%05X", id));
    }

    public class DescuentoDatosExport {

        public String getIdDescuento() {
            return Descuento.this.idDescuento;
        }

        public String getNombre() {
            return Descuento.this.nombre;
        }

        public String getDescripcion() {
            return Descuento.this.descripcion;
        }

        public Integer getIndiceCobro() {
            return Descuento.this.indiceCobro;
        }

        public String getAmbito() {
            return Descuento.this.ambito.getIdAmbito();
        }

        public String getEstadoDescuento() {
            return Descuento.this.estadoDescuento.getNombre();
        }
        
        public Date getFechaInicio() {
            return Descuento.this.fechaInicial;
        }

        public Date getFechaFin() {
            return Descuento.this.fechaFinal;
        }
        
        public String getTratamientoTiposCiudadano() {
            return Descuento.this.tratamientoTiposCiudadano;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Descuento other = (Descuento) obj;
        return Objects.equals(this.id, other.id);
    }
}
