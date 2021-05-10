/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import com.wdreams.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"usuarioCreador", "usuarioAsignado", "ciudadanos", "tarjetas", "alertas", "historialesIncidencia"})
@Entity
@Table(name = "Incidencia")
public class Incidencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idIncidencia", length = 9)
    private String idIncidencia;

    @Column(name = "descripcion")
    @Type(type = "text")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idEstado", referencedColumnName = "id")
    private EstadoIncidencia estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCreador", referencedColumnName = "id")
    private User usuarioCreador;

    @ManyToOne
    @JoinColumn(name = "idUsuarioAsignado", referencedColumnName = "id")
    private Usuario usuarioAsignado;

    @ManyToOne
    @JoinColumn(name = "idTipoIncidencia", referencedColumnName = "id")
    private TipoIncidencia tipoIncidencia;

    @Column(name = "prioridad", length = 20)
    private String prioridad;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "incidenciasCiudadanos",
            joinColumns = {
                @JoinColumn(name = "idIncidencia")},
            inverseJoinColumns = {
                @JoinColumn(name = "idEntidad")})
    @Fetch(FetchMode.SELECT)
    private List<Entidad> ciudadanos;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "incidenciasTarjetas",
            joinColumns = {
                @JoinColumn(name = "idIncidencia")},
            inverseJoinColumns = {
                @JoinColumn(name = "idTarjeta")})
    @Fetch(FetchMode.SELECT)
    private List<Tarjeta> tarjetas;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "incidenciasAlertas",
            joinColumns = {
                @JoinColumn(name = "idIncidencia")},
            inverseJoinColumns = {
                @JoinColumn(name = "idAlerta")})
    @Fetch(FetchMode.SELECT)
    private List<Alerta> alertas;

    @OneToMany(mappedBy = "incidencia", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy(value = "fecha")
    private Set<HistorialIncidencia> historialesIncidencia;

    @Column(name = "conAdjuntos")
    private Boolean conAdjuntos;


    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "imagen")
    private String imagen;


    public Incidencia() {
    }

    public Boolean getConAdjuntos() {
        return conAdjuntos;
    }

    public void setConAdjuntos(Boolean conAdjuntos) {
        this.conAdjuntos = conAdjuntos;
    }

    public Incidencia(String descripcion, EstadoIncidencia estadoIncidencia, Date fecha, User usuarioCreador, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, List<Entidad> ciudadanos, List<Tarjeta> tarjetas, List<Alerta> alertas, Boolean conAdjuntos) {
        this.descripcion = descripcion;
        this.estado = estadoIncidencia;
        this.fecha = fecha;
        this.usuarioCreador = usuarioCreador;
        this.usuarioAsignado = usuarioAsignado;
        this.tipoIncidencia = tipoIncidencia;
        this.prioridad = prioridad;
        this.ciudadanos = ciudadanos;
        this.tarjetas = tarjetas;
        this.alertas = alertas;
        this.conAdjuntos = conAdjuntos;
    }

    public Incidencia(String descripcion, EstadoIncidencia estadoIncidencia, Date fecha, User usuarioCreador, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, List<Entidad> ciudadanos, List<Tarjeta> tarjetas, List<Alerta> alertas, Boolean conAdjuntos, Float lat, Float lon, String imagen) {
        this.descripcion = descripcion;
        this.estado = estadoIncidencia;
        this.fecha = fecha;
        this.usuarioCreador = usuarioCreador;
        this.usuarioAsignado = usuarioAsignado;
        this.tipoIncidencia = tipoIncidencia;
        this.prioridad = prioridad;
        this.ciudadanos = ciudadanos;
        this.tarjetas = tarjetas;
        this.alertas = alertas;
        this.conAdjuntos = conAdjuntos;
        this.lat = lat;
        this.lon = lon;
        this.imagen = imagen;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public User getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(User usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Usuario getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public List<Entidad> getCiudadanos() {
        return ciudadanos;
    }

    public void setCiudadanos(List<Entidad> ciudadanos) {
        this.ciudadanos = ciudadanos;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public void asignarIdIncidencia() {
        this.setIdIncidencia(String.format("%08X", 560000000l + this.getId()));
    }

    public void addTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
    }

    public void addCiudadano(Entidad ciudadano) {
        this.ciudadanos.add(ciudadano);
    }

    public void addAlerta(Alerta alerta) {
        this.alertas.add(alerta);
    }

    public Set<HistorialIncidencia> getHistorialesIncidencia() {
        return historialesIncidencia;
    }

    public void setHistorialesIncidencia(Set<HistorialIncidencia> historialesIncidencia) {
        this.historialesIncidencia = historialesIncidencia;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    public class IncidenciaDatosExport {

        public String getIdIncidencia() {
            return Incidencia.this.getIdIncidencia();
        }

        public String getTipoIncidencia() {
            return Incidencia.this.getTipoIncidencia().getNombre();
        }

        public String getEstadoIncidencia() {
            return Incidencia.this.getEstado().getNombre();
        }

        public String getPrioridad() {
            return Incidencia.this.getPrioridad();
        }

        public String getUsuarioCreador() {
            return Incidencia.this.getUsuarioCreador().getUsername();
        }

        public String getUsuarioAsignado() {
            if (Incidencia.this.getUsuarioAsignado() == null) {
                return "";
            } else {
                return Incidencia.this.getUsuarioAsignado().getUsername();
            }
        }

        public String getFecha() {
            return DateUtils.ddMMyyyyHHmmss.format(Incidencia.this.getFecha());
            //return Incidencia.this.getFecha();
        }
    }

    public enum Prioridad {
        BAJA(1, "BAJA"),
        MEDIA(2, "MEDIA"),
        ALTA(3, "ALTA");

        private int codigo;
        private String texto;

        public int getCodigo() {
            return this.codigo;
        }

        public String getTexto() {
            return this.texto;
        }

        Prioridad(int codigo, String texto) {
            this.codigo = codigo;
            this.texto = texto;
        }
    }
}
