/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.*;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"estado", "estadoTarjeta", "historialesTarjeta", "ambitosNoPermitidos", "incidencias", "estadoActualActiva", "estadoActualBloqueada", "entidad", "estadoActual", "estadoAnterior", "historialTarjeta", "imprimir", "hasCiudadano"})
@Entity
@Table(name = "Tarjetas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Tarjeta implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "imagen", length = 8)
    private String imagen;



    @Column(name = "uid", length = 8)
    private String uid;

    @Column(name = "idTarjeta", length = 8)
    private String idTarjeta;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEntidad", nullable = true, referencedColumnName = "id")
    private Entidad entidad;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEstadoTarjeta", nullable = false, referencedColumnName = "id")
    private EstadoTarjeta estado;

    @Column(name = "limite")
    private Double limite;

    @Column(name = "tag")
    private Integer tag;

    @Column(name = "via",length = 50)
    private String via;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "piso", length = 10)
    private String piso;

    @Column(name = "puerta", length = 10)
    private String puerta;

    @Column(name = "cp", length = 10)
    private String cp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaPedido", nullable = false)
    private Date fechaPedido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCaducidad", nullable = true)
    private Date fechaCaducidad;

    @OneToMany(mappedBy = "tarjeta", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy(value = "fecha")
    private Set<HistorialTarjeta> historialesTarjeta;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "TarjetasAmbitosNoPermitidos")
    private List<Ambito> ambitosNoPermitidos;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "incidenciasTarjetas",
            joinColumns = {
                @JoinColumn(name = "idTarjeta")},
            inverseJoinColumns = {
                @JoinColumn(name = "idIncidencia")})
    @Fetch(FetchMode.SELECT)
    private List<Incidencia> incidencias;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idPuestoImpresion", nullable = true, referencedColumnName = "id")
    private PuestoImpresion puestoImpresion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAsignacionPuesto", nullable = true)
    private Date fechaAsignacionPuesto;

    @Column(name = "prioridad")
    private int prioridad;

    @Column(name = "modalidadImpresion")
    private int modalidadImpresion;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "centroRecogida", referencedColumnName = "id")
    private CentroRecogida centroRecogida;


    public BigDecimal getSaldoVirtual() {
        return this.entidad.getSaldoVirtual();
    }

    public void setSaldoVirtual(BigDecimal saldoVirtual) {
        this.entidad.setSaldoVirtual(saldoVirtual);
    }

    private BigDecimal saldoVirtual;


    public static String MODALIDAD_PRE = "PRE";
    public static String MODALIDAD_POS = "POS";
    public static String MODALIDAD_VIR = "VIR";

    public static class PRIORIDADES {

        public static int PRIORIDAD_INMEDIATA = 1;
        public static int PEIORIDAD_INDIFERIDA = 2;
        public static int PRIORIDAD_VIRTUAL = 3;
    }

    public static class MODALIDADES_IMPRESION {

        public static final int MODALIDAD_INMEDIATA = 1;
        public static final int MODALIDAD_INDIFERIDA = 2;
        public static final int MODALIDAD_VIRTUAL = 3;

        public static boolean existeModalidad(int modalidad) {
            return modalidad == MODALIDAD_INMEDIATA || modalidad == MODALIDAD_INDIFERIDA
                    || modalidad == MODALIDAD_VIRTUAL;//Mirar luego si no rompo el WebService devolviendo existeModalidadTrue
                    //implica de en el método del webservice EntradaTarjetaListado devuelvo el código 915
        }
    }
    
    // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaUltimaModificacion", nullable = false,  updatable = false, insertable = false, columnDefinition = "TIMESTAMP NOT NULL")
    private Timestamp fechaUltimaModificacion;



    public Tarjeta() {
    }

    private Tarjeta(Entidad ciudadano, int modalidadImpresion, int prioridad, PuestoImpresion puestoImpresion) {
        this.entidad = ciudadano;
        this.fechaPedido = new Date();
        this.modalidadImpresion = modalidadImpresion;
        this.prioridad = prioridad;
        this.puestoImpresion = puestoImpresion;
    }

    public Tarjeta(Entidad ciudadano, EstadoTarjeta estadoTarjeta,int modalidadImpresion, int prioridad, PuestoImpresion puestoImpresion, String via, String numero, String piso, String puerta, String cp, CentroRecogida centroRecogida) {
        this(ciudadano, modalidadImpresion, prioridad, puestoImpresion);
        this.centroRecogida = centroRecogida;
        this.via = via;
        this.numero = numero;
        this.piso = piso;
        this.puerta = puerta;
        this.cp = cp;
        this.estado=estadoTarjeta;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public EstadoTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarjeta estado) {
        this.estado = estado;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Set<HistorialTarjeta> getHistorialesTarjeta() {
        return historialesTarjeta;
    }

    public void setHistorialesTarjeta(Set<HistorialTarjeta> historialesTarjeta) {
        this.historialesTarjeta = historialesTarjeta;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad ciudadano) {
        this.entidad = ciudadano;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public abstract String getTipoTarjeta();

    public abstract TarjetaDatosImprimir getImprimir(Dao dao);

    public abstract TarjetaDatosEstado getEstadoActualActiva(Dao dao, HistorialTarjeta historialTarjeta);

    public abstract TarjetaDatosEstado getEstadoActualBloqueada(Dao dao, HistorialTarjeta historialTarjeta);

    public abstract TarjetaDatosEstado getEstadoActual(Dao dao, HistorialTarjeta historialTarjeta);

    public abstract boolean getHasCiudadano();
    
    public Date getFechaUltimaModificacion() {
        return new Date (this.fechaUltimaModificacion.getTime()); 
    }
    

    @Override
    public int compareTo(Object athat) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        //this optimization is usually worthwhile, and can
        //always be added
        if (this == athat) {
            return EQUAL;
        }

        final Tarjeta that = (Tarjeta) athat;

        //primitive numbers follow this form
        if (this.getId() < that.getId()) {
            return BEFORE;
        }
        if (this.getId() > that.getId()) {
            return AFTER;
        }

        return AFTER;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public abstract void asignarIdTarjeta();

    public List<Ambito> getAmbitosNoPermitidos() {
        return ambitosNoPermitidos;
    }

    public void setAmbitosNoPermitidos(List<Ambito> ambitosNoPermitidos) {
        this.ambitosNoPermitidos = ambitosNoPermitidos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Tarjeta other = (Tarjeta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public static Class stringToModalidad(String string) {
        if (string.equals(MODALIDAD_PRE)) {
            return TarjetaPrepago.class;
        }
        else if(string.equals(MODALIDAD_VIR)){
            return TarjetaVirtual.class;
        }else {
            return TarjetaPospago.class;
        }

    }

    public static String modalidadToString(Class modalidad) {
        if (modalidad.equals(TarjetaPrepago.class)) {
            return MODALIDAD_PRE;
        }
        else if (modalidad.equals(TarjetaVirtual.class)){
            return MODALIDAD_VIR;
        }
        else{
            return MODALIDAD_POS;
        }
    }

    public PuestoImpresion getPuestoImpresion() {
        return puestoImpresion;
    }

    public void setPuestoImpresion(PuestoImpresion puestoImpresion) {
        this.puestoImpresion = puestoImpresion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getModalidadImpresion() {
        return modalidadImpresion;
    }

    public void setModalidadImpresion(int modalidadImpresion) {
        this.modalidadImpresion = modalidadImpresion;
    }

    public Date getFechaAsignacionPuesto() {
        return fechaAsignacionPuesto;
    }

    public void setFechaAsignacionPuesto(Date fechaAsignacionPuesto) {
        this.fechaAsignacionPuesto = fechaAsignacionPuesto;
    }

    public CentroRecogida getCentroRecogida() {
        return centroRecogida;
    }

    public void setCentroRecogida(CentroRecogida centroRecogida) {
        this.centroRecogida = centroRecogida;
    }
    
    public EstadoTarjeta getEstadoAnterior(EstadoTarjeta estadoDefecto){
        List<HistorialTarjeta> historialesTarjeta = new ArrayList<>(this.historialesTarjeta);
        Collections.sort(historialesTarjeta, new Comparator<HistorialTarjeta>(){

            @Override
            public int compare(HistorialTarjeta t, HistorialTarjeta t1) {
                return t1.getFecha().compareTo(t.getFecha());
            }
            
        });
        return (historialesTarjeta.size() >= 2 ? historialesTarjeta.get(1).getEstado() : estadoDefecto);
    }
    
     public class TarjetaDatosExport {
         
        public String getId() {
            return String.valueOf(Tarjeta.this.getId());
        } 

        public String getIdTarjeta() {
            return Tarjeta.this.getIdTarjeta();
        }

        public String getCiudadano() { return Tarjeta.this.getEntidad().getIdCiudadano(); }

        public String getNombreCiudadano(){
            return Tarjeta.this.getEntidad().getNombre() + " " + Tarjeta.this.getEntidad().getApellidos();
        }

        public String getImagen() {
             return Tarjeta.this.getImagen() == null ? null : Tarjeta.this.getImagen();
         }
        
        public String getUid() { return Tarjeta.this.getUid() == null ? null : Tarjeta.this.getUid(); }

        public String getModalidad() { return (Tarjeta.this.getModalidadImpresion()== MODALIDADES_IMPRESION.MODALIDAD_INMEDIATA ? "INMEDIATA" : "ONLINE"); }

        public String getPuestoImpresion() { return (Tarjeta.this.getPuestoImpresion() == null ? "" : Tarjeta.this.getPuestoImpresion().getNombre()); }

        public String getCentroRecogida() { return (Tarjeta.this.getCentroRecogida() == null ? "" : Tarjeta.this.getCentroRecogida().getNombre()); }

        public String getFechaSolicitud() {
            return DateUtils.ddMMyyyyHHmmss.format(Tarjeta.this.getFechaPedido());
        }

        public String getEstadoTarjeta() {
            return Tarjeta.this.getEstado().getNombre();
        }

        public String getTipoTarjeta() {
            return Tarjeta.this.getTipoTarjeta();
        }
        
        public BigDecimal getSaldo() { return (Tarjeta.this instanceof TarjetaPrepago ? ((TarjetaPrepago)Tarjeta.this).getSaldo():null); }

        public String getFechaUltimaModificacion() { return DateUtils.ddMMyyyyHHmmss.format(Tarjeta.this.getFechaUltimaModificacion()); }
        
        public String getFechaCaducidad() { return Tarjeta.this.getFechaCaducidad() == null ? null : DateUtils.ddMMyyyyHHmmss.format(Tarjeta.this.getFechaCaducidad()); }
        
        public Set<HistorialTarjeta.HistorialTarjetaJson> getHistorialTarjeta() {
            Set<HistorialTarjeta> historial = Tarjeta.this.getHistorialesTarjeta();
            Set<HistorialTarjeta.HistorialTarjetaJson> historialJson = new HashSet<>();
            for (HistorialTarjeta ht : historial) {
                historialJson.add(ht.new HistorialTarjetaJson());
            }
            return historialJson;
        }
      
            
        
        
       

        @Override
        public boolean equals(Object o) {
            return Tarjeta.this.equals(o); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            return Tarjeta.this.hashCode(); //To change body of generated methods, choose Tools | Templates.
        }

        
    }
}
