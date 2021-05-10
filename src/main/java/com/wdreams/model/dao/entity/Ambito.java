/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.*;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 * @author victor
 */
@Entity
@Table(name = "Ambitos")
@DataTransferObject
public class Ambito implements Serializable, TraceLogger {
    public static Integer CENTROS_AYUNTAMIENTO = 0;
    public static Integer CENTROS_COMERCIO = 1;

    @Column(name = "tipocentro")
    private Integer tipocentro;

    public Integer getTipocentro() {
        return tipocentro;
    }

    public void setTipocentro(Integer tipocentro) {
        this.tipocentro = tipocentro;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "idAmbito", length = 4)
    private String idAmbito;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "mail")
    private String mail;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "visibleGraficos")
    private Boolean visibleGraficos;

    @OneToMany(mappedBy = "ambito", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Centro> centros;

    /*  @Column(name = "usoSFTP", nullable = false)
    private Boolean usoSFTP;
    
    @Column(name = "rutaSFTP", nullable = true)
    private String rutaSFTP;*/
    @Column(name = "comisionCompra", nullable = false)
    private Integer comisionCompra;

    @Column(name = "comisionRecarga", nullable = false)
    private Integer comisionRecarga;

    @Column(name = "permiteCompra", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean permiteCompra;

    @Column(name = "permiteRecarga", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean permiteRecarga;

    @Column(name = "permiteAcceso", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean permiteAcceso;

    @Column(name = "visible")
    private Boolean visible;

    public Ambito() {
    }

    public Ambito(String nombre, String mail, String descripcion, String comisionCompra, String comisionRecarga, Boolean permiteCompra, Boolean permiteRecarga, Boolean permiteAcceso) {
        this.nombre = nombre;
        this.mail = mail;
        this.descripcion = descripcion;
        if (comisionCompra != null && !comisionCompra.equals("")) {
            this.comisionCompra = Integer.parseInt(comisionCompra);
        } else {
            this.comisionCompra = 0;
        }
        if (comisionRecarga != null && !comisionRecarga.equals("")) {
            this.comisionRecarga = Integer.parseInt(comisionRecarga);
        } else {
            this.comisionRecarga = 0;
        }
        this.permiteCompra = permiteCompra;
        this.permiteRecarga = permiteRecarga;
        this.permiteAcceso = permiteAcceso;
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

    public String getIdAmbito() {
        return idAmbito;
    }

    public void setIdAmbito(String idAmbito) {
        this.idAmbito = idAmbito;
    }

    public Set<Centro> getCentros() {
        return centros;
    }

    public void setCentros(Set<Centro> centros) {
        this.centros = centros;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void asignarIdAmbito() {
        this.setIdAmbito(String.format("A%03X", this.getId()));
    }

    public Integer getOrden() {
        if(orden == null)
        {
            return 10000;
        }
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisibleGraficos() {
        return visibleGraficos;
    }

    public void setVisibleGraficos(Boolean visibleGraficos) {
        this.visibleGraficos = visibleGraficos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Ambito other = (Ambito) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Boolean getPermiteCompra() {
        return permiteCompra;
    }

    public void setPermiteCompra(Boolean permiteCompra) {
        this.permiteCompra = permiteCompra;
    }

    public Boolean getPermiteRecarga() {
        return permiteRecarga;
    }

    public void setPermiteRecarga(Boolean permiteRecarga) {
        this.permiteRecarga = permiteRecarga;
    }

    public Boolean getPermiteAcceso() {
        return permiteAcceso;
    }

    public void setPermiteAcceso(Boolean permiteAcceso) {
        this.permiteAcceso = permiteAcceso;
    }

    @Override
    public Logger getLogger() {
        Logger log = Logger.getLogger(Ambito.class);
        try {
            log.addAppender(new FileAppender(new PatternLayout(), this.getIdAmbito() + ".log"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Ambito.class.getName()).log(Level.SEVERE, null, ex);
        }

        return log;
    }

    public class AmbitosDatosExport {

        public String getIdAmbito() {
            return Ambito.this.getIdAmbito();
        }

        public String getNombre() {
            return Ambito.this.getNombre();
        }

        public Integer getCentros() {
            return Ambito.this.getCentros().size();
        }

    }

}
