/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.*;

import com.wdreams.model.dao.Dao;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties({"listaRestricciones", "listaAmbitos", "listaDocumentos"})
@Entity
@Table(name = "TiposCiudadano")
@DataTransferObject
public class TipoCiudadano implements Serializable {

    public static String IDTIPO_EMPADRONADO = "TI00001";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idTipoCiudadano", length = 7)
    private String idTipoCiudadano;

    @Column(name = "tipoEntidad")
    private int tipoEntidad;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion")
    @Type(type = "text")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idTipoAsignacion", referencedColumnName = "id")
    private TipoAsignacion tipoAsignacion;

    @Column(name = "diasCaducidad")
    private Integer diasCaducidad;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE})
    @OneToMany(mappedBy = "tipoCiudadano", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<TipoCiudadanoDocumento> listaDocumentos;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE})
    @OneToMany(mappedBy = "tipoCiudadano", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Restriccion> listaRestricciones;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE})
    @OneToMany(mappedBy = "tipoCiudadano", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<TipoCiudadanoAmbito> listaAmbitos;

    @Column(name = "isOr")
    private Boolean isOr;

    public TipoCiudadano() {
    }

    public TipoCiudadano(int tipoEntidad, String nombre, String descripcion, TipoAsignacion tipoAsignacion, Integer diasCaducidad, List<TipoCiudadanoDocumento> listaDocumentos, List<Restriccion> listaRestricciones, List<TipoCiudadanoAmbito> listaAmbitos) {
        this.tipoEntidad = tipoEntidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoAsignacion = tipoAsignacion;
        this.diasCaducidad = diasCaducidad;
        this.listaDocumentos = listaDocumentos;
        this.listaRestricciones = listaRestricciones;
        this.listaAmbitos = listaAmbitos;

        this.actualizaTipoCiudadano();

    }

    private void actualizaTipoCiudadano() {
        for (TipoCiudadanoAmbito tipoCiudadanoAmbito : listaAmbitos) {
            tipoCiudadanoAmbito.setTipoCiudadano(this);
        }
        if (listaDocumentos != null) {
            for (TipoCiudadanoDocumento tipoCiudadanoDocumento : listaDocumentos) {
                tipoCiudadanoDocumento.setTipoCiudadano(this);
            }
        }

        if (listaRestricciones != null) {
            for (Restriccion tipoCiudadanoRestriccion : listaRestricciones) {
                tipoCiudadanoRestriccion.setTipoCiudadano(this);
            }
        }
    }

    public void modificaDatos(TipoCiudadano tipoCiudadano) {

        this.nombre = tipoCiudadano.getNombre();
        this.descripcion = tipoCiudadano.getDescripcion();
        this.tipoAsignacion = tipoCiudadano.getTipoAsignacion();
        this.diasCaducidad = tipoCiudadano.getDiasCaducidad();

        /* for(TipoCiudadanoDocumento tipoCiudadanoDocumento:this.getListaDocumentos()){
         if(!tipoCiudadano.getListaDocumentos().contains(tipoCiudadanoDocumento)){
         tipoCiudadanoDocumento.setTipoCiudadano(null);
         tipoCiudadano.getListaDocumentos().add(tipoCiudadanoDocumento);
         }
         }
        
         for(TipoCiudadanoDocumento tipoCiudadanoDocumento:tipoCiudadano.getListaDocumentos()){
         tipoCiudadanoDocumento.setTipoCiudadano(this);
         }*/
        for (TipoCiudadanoDocumento tipoCiudadanoDocumento : tipoCiudadano.getListaDocumentos()) {
            if (!this.listaDocumentos.contains(tipoCiudadanoDocumento)) {
                tipoCiudadanoDocumento.setTipoCiudadano(this);
                this.listaDocumentos.add(tipoCiudadanoDocumento);
            }
        }

        for (TipoCiudadanoDocumento tipoCiudadanoDocumento : this.listaDocumentos) {
            if (!this.getListaDocumentos().contains(tipoCiudadanoDocumento)) {
                tipoCiudadanoDocumento.setTipoCiudadano(null);
            }
        }

        //  this.listaDocumentos.clear();
        //  this.listaDocumentos.addAll(tipoCiudadano.getListaDocumentos());
        this.listaRestricciones = tipoCiudadano.getListaRestricciones();
        this.listaAmbitos = tipoCiudadano.getListaAmbitos();

        //this.actualizaTipoCiudadano();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdTipoCiudadano() {
        return idTipoCiudadano;
    }

    public void asignarIdTipoCiudadano() {
        this.idTipoCiudadano = (String.format("TI%05X", this.getId()));
    }

    public void setIdTipoCiudadano(String idTipoCiudadano) {
        this.idTipoCiudadano = idTipoCiudadano;
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

    public TipoAsignacion getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void setTipoAsignacion(TipoAsignacion tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

    public Integer getDiasCaducidad() {
        return diasCaducidad;
    }

    public void setDiasCaducidad(Integer diasCaducidad) {
        this.diasCaducidad = diasCaducidad;
    }

    public List<TipoCiudadanoDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(ArrayList<TipoCiudadanoDocumento> listaDocumentos) {
        this.listaDocumentos.clear();
        this.listaDocumentos.addAll(listaDocumentos);
    }

    public List<TipoCiudadanoAmbito> getListaAmbitos() {
        return listaAmbitos;
    }

    public void setListaAmbitos(ArrayList<TipoCiudadanoAmbito> listaAmbitos) {
        this.listaAmbitos.clear();
        this.listaAmbitos.addAll(listaAmbitos);
    }

    public List<Restriccion> getListaRestricciones() {
        return listaRestricciones;
    }

    public void setListaRestricciones(ArrayList<Restriccion> listaRestricciones) {
        this.listaRestricciones.clear();
        this.listaRestricciones.addAll(listaRestricciones);
        for (Restriccion restriccion : listaRestricciones){
            restriccion.setTipoCiudadano(this);
        }
    }

    public int getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(int tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public void inicializaTiposCiudadanoAmbitos(Dao dao) {
        List<Ambito> ambitos = dao.getAmbitos();
        ambitoLoop:
        for (Ambito ambito : ambitos) {
            for (TipoCiudadanoAmbito tipoCiudadanoAmbito : this.listaAmbitos) {
                if (tipoCiudadanoAmbito.getTipoAmbito().equals(ambito)) {
                    break ambitoLoop;
                }

            }
            listaAmbitos.add(new TipoCiudadanoAmbito(this, ambito, new BigDecimal(100)));
        }
        Collections.sort(listaAmbitos, new Comparator<TipoCiudadanoAmbito>() {

            @Override
            public int compare(TipoCiudadanoAmbito t, TipoCiudadanoAmbito t1) {
                return t.getTipoAmbito().getNombre().compareTo(t1.getTipoAmbito().getNombre());
            }

        });
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final TipoCiudadano other = (TipoCiudadano) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Boolean getIsOr() {
        return isOr;
    }

    public void setIsOr(Boolean isOr) {
        this.isOr = isOr;
    }

    public class TipoCiudadanoDatosExport {

        public String getIdTipoCiudadano() {
            return TipoCiudadano.this.idTipoCiudadano;
        }

        public String getNombre() {
            return TipoCiudadano.this.nombre;
        }

        public String getAsignacion() {
            return TipoCiudadano.this.tipoAsignacion.getNombre();

        }
    }
    
    public String getRestriccionString(){
        String query="tc.id="+this.id+" AND (";
        for(Restriccion restriccion : this.listaRestricciones){
            if(this.listaRestricciones.indexOf(restriccion)!=0){
                query+=" AND ";
            }
            query+=restriccion.getTipoRestriccion().getCampo()+" "
                    +restriccion.getTipoRestriccion().getComparador()+" "+
                    restriccion.getValorInicial()+
                    (restriccion.getTipoRestriccion().getComparador().equals("between") ? " AND "+restriccion.getValorFinal():"");
        }
        query+=")";
        return query;
    }

}
