/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.model.dao.Dao;
import com.wdreams.utils.DateUtils;
import com.wdreams.utils.Enviable;
import com.wdreams.utils.Plantillas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"tarjeta", "user", "estado", "ambito", "adjuntos"})
@Entity
@Table(name = "HistorialesTarjeta")
public class HistorialTarjeta implements Serializable, Comparable<HistorialTarjeta>, Enviable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTarjeta", nullable = false, referencedColumnName = "id")
    private Tarjeta tarjeta;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idEstadoTarjeta", nullable = false, referencedColumnName = "id")
    private EstadoTarjeta estado;

    @Column(name = "notificado")
    private Boolean notificado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaNotificado")
    private Date fechaNotificado;

    @Column(name = "descripcionTraza")
    private String descripcionTraza;

    @Column(name = "descripcionMail")
    private String descripcionMail;

    @Column(name = "notificable")
    private Boolean notificable;
    
    @Column(name = "grabable", nullable = false)
    private Boolean grabable;
    
    @Column(name = "grabado", nullable = false)
    private Boolean grabado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaGrabacion")
    private Date fechaGrabacion;
    
    @ManyToOne
    @JoinColumn(name = "idAmbito", referencedColumnName = "id")
    private Ambito ambito;
    
    public HistorialTarjeta() {
    }

    public HistorialTarjeta(Tarjeta tarjeta, User user, EstadoTarjeta estado, String descripcionTraza,Boolean grabable,Boolean grabado) {
        this.tarjeta = tarjeta;
        this.user = user;
        this.fecha = new Date();
        this.estado = estado;
        this.descripcionTraza = descripcionTraza;
        this.grabable = grabable;
        this.grabado = grabado;
    }

    public HistorialTarjeta(Tarjeta tarjeta, User user, EstadoTarjeta estado, String descripcionTraza, String descripcionMail, Boolean notificable,Boolean grabable,Boolean grabado, Date fechaGrabacion,Ambito ambito){
        this.tarjeta = tarjeta;
        this.user = user;
        this.fecha = new Date();
        this.estado = estado;
        this.descripcionTraza = descripcionTraza;
        this.descripcionMail = descripcionMail;
        this.notificable = notificable;
        this.grabable = grabable;
        this.grabado = grabado;
        this.fechaGrabacion = fechaGrabacion;
        this.ambito = ambito;
        
    }

    public HistorialTarjeta(Tarjeta tarjeta, User user, EstadoTarjeta estado, String descripcionTraza,Boolean grabable,Boolean grabado, Date fecha) {
        this.tarjeta = tarjeta;
        this.user = user;
        this.fecha = fecha;
        this.estado = estado;
        this.descripcionTraza = descripcionTraza;
        this.grabable = grabable;
        this.grabado = grabado;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarjeta estado) {
        this.estado = estado;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Date getFechaNotificado() {
        return fechaNotificado;
    }

    public void setFechaNotificado(Date fechaNotificado) {
        this.fechaNotificado = fechaNotificado;
    }

    public String getDescripcionTraza() {
        return descripcionTraza;
    }

    public void setDescripcionTraza(String descripcionTraza) {
        this.descripcionTraza = descripcionTraza;
    }

    public String getDescripcionMail() {
        return descripcionMail;
    }

    public void setDescripcionMail(String descripcionMail) {
        this.descripcionMail = descripcionMail;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public Date getFechaGrabacion() {
        return fechaGrabacion;
    }

    public void setFechaGrabacion(Date fechaGrabacion) {
        this.fechaGrabacion = fechaGrabacion;
    }

    public Boolean getGrabable() {
        return grabable;
    }

    public void setGrabable(Boolean grabable) {
        this.grabable = grabable;
    }

    public Boolean getGrabado() {
        return grabado;
    }

    public void setGrabado(Boolean grabado) {
        this.grabado = grabado;
    }
    
    @Override
    public int compareTo(HistorialTarjeta o) {
        return (int) (this.fecha.getTime() - o.getFecha().getTime());
    }

    public Boolean isNotificable() {
        return notificable;
    }

    public void setNotificable(Boolean notificable) {
        this.notificable = notificable;
    }

    @Override
    public String getEmailEnviable(Dao dao) {
        return (tarjeta).getEntidad().getMail();
    }

    @Override
    public String getText(Dao dao) throws FileNotFoundException, IOException {
        return new Plantillas(dao).getText(Plantillas.PLANTILLA_CAMBIO_ESTADO_TARJETA)
                .replace("[MENSAJE]", this.descripcionMail)
                .replace("[NOMBRE]", (tarjeta).getEntidad().getNombre())
                .replace("[APELLIDOS]", tarjeta.getEntidad().getApellidos())
                .replace("[ESTADO]", this.estado.getNombre());
    }

    @Override
    public String getSubject(Dao dao) {
        return "El estado de su cuenta en el sistema Tarjeta Ciudadana ha cambiado a " + this.getEstado().getNombre();
    }

    @Override
    public List<String> getAdjuntos(Dao dao) {
        return null;
    }
    
    public class HistorialTarjetaJson {
        
        public String getId() {
            return HistorialTarjeta.this.getId().toString();
        }
        
//        public String getIdTarjeta(){
//            return HistorialTarjeta.this.getTarjeta().getIdTarjeta();
//        }
        
        public String getUserName() {
            return HistorialTarjeta.this.getUser().getUsername();
        }
        
        public String getFecha() {
            return HistorialTarjeta.this.getFecha() == null ? null : DateUtils.ddMMyyyyHHmmss.format(HistorialTarjeta.this.getFecha());
        }
        
        public String getEstado() {
            return HistorialTarjeta.this.getEstado().getNombre();
        }
        
        public String getNotificado() {
            return HistorialTarjeta.this.getNotificado() == null ? null : HistorialTarjeta.this.getNotificado().toString();
        }
        
        public String getFechaNotificado() {
            return HistorialTarjeta.this.getFechaNotificado() == null ? null : DateUtils.ddMMyyyyHHmmss.format(HistorialTarjeta.this.getFechaNotificado());
        }
        
        public String getDescripcionTraza() {
            return HistorialTarjeta.this.getDescripcionTraza();
        }
        
        public String getDescripcionMail() {
            return HistorialTarjeta.this.getDescripcionMail();
        }
        
        public String getNotificable() {
            return HistorialTarjeta.this.isNotificable()== null ? null :HistorialTarjeta.this.isNotificable().toString();
        }
        
        public String getGrabable() {
            return HistorialTarjeta.this.getGrabable() == null ? null : HistorialTarjeta.this.getGrabable().toString();
        }
        
        public String getGrabado() {
            return HistorialTarjeta.this.getGrabado() == null ? null : HistorialTarjeta.this.getGrabado().toString();
        }
        
        public String getFechaGrabacion() {
            return HistorialTarjeta.this.getFechaGrabacion() == null ? null : DateUtils.ddMMyyyyHHmmss.format(HistorialTarjeta.this.getFechaGrabacion());
        }
        
        
        
        
    }

}
