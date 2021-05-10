/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 * @author victor
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "Centros")
@DataTransferObject
public class Centro implements Serializable {

    public static Integer CENTROS_AYUNTAMIENTO = 0;
    public static Integer CENTROS_COMERCIO = 1;


    /**
     * Alberto (27/07/2020)
     * XFer pide geoposicionemiento para centros ayuntamiento y comercios
     */
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "longitud")
    private String longitud;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * Alberto (07/04/2020)
     * Gabriel solicita un campo booleano para los comercios. El objetivo es establecer si el comercio
     * acumulará los puntos que gestiona el ayuntamiento en la compras. Hay que tener en cuenta que
     * los puntos los gestiona el ayuntamiento, no el comercio.
     */
    @Column(name = "gestionaPuntos")
    private Boolean gestionaPuntos;

    public Boolean getGestionaPuntos() {
        return gestionaPuntos;
    }

    public void setGestionaPuntos(Boolean gestionaPuntos) {
        this.gestionaPuntos = gestionaPuntos;
    }


    @Column(name = "orden")
    private Integer orden;

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

    @Column(name = "fecha")
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "imagen")
    private String imagen;

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Column(name = "enlace")
    private String enlace;

    public String getEnlace() {
        return enlace;
    }
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    @Column(name = "entradilla")
    private String entradilla;

    public String getEntradilla() {
        return entradilla;
    }
    public void setEntradilla(String entradilla) {
        this.entradilla = entradilla;
    }

    @Column(name = "idCategoria")
    private Integer idCategoria;

    public Integer getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategoria", insertable = false, updatable = false)
    public cmscategorias cmscategoria;

    public cmscategorias getCmscategoria() {
        return cmscategoria;
    }


    public void setCmscategoria(cmscategorias cmscategoria) {
        this.cmscategoria = cmscategoria;
    }

    @Column(name = "texto")
    private String texto;

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Column(name = "mostrar")
    private Boolean mostrar;

    public Boolean getMostrar() {
        return mostrar;
    }
    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    
    @Column(name = "idCentro",length=7)
    private String idCentro;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idAmbito", nullable = false, referencedColumnName = "id")
    private Ambito ambito;

    @OneToMany(mappedBy = "centro", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Dispositivo> dispositivos;

    @OneToMany(mappedBy = "centro", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<centros_imagenes> imagenes;

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre",length=50)
    private String nombre;
    @Column(name = "mail")
    private String mail;
    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "DTYPE")
    @ColumnDefault("")
    private String DTYPE;

    public Centro() {
    }

    /*Constructor usado en el alta de centro (CentroFormulario)*/
    public Centro(String nombre, String descripcion, Ambito ambito, String mail) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ambito = ambito;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public Set<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void asignarIdCentro() {
        this.setIdCentro(String.format("C%06X", this.id));
    }


    public List<centros_imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<centros_imagenes> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Centro other = (Centro) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
     public class CentroDatosExport {
        
        public String getNombre(){
            return Centro.this.getNombre();
        }
        
        public String getIdCentro(){
            return Centro.this.getIdCentro();
        }

        public String getIdAmbito(){
             return Centro.this.getAmbito().getIdAmbito();
         }
        
        public String getNombreAmbito(){
            return Centro.this.getAmbito().getNombre();
        }

        public Boolean getGestionaPuntos() {
            return Centro.this.getGestionaPuntos();
        }
        
        public Integer getDispositivos(){
            return Centro.this.getDispositivos().size();
        }
        
     }
         
}
