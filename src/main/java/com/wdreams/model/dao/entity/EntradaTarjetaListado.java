/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wdreams.model.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rosa
 */
@XmlRootElement(namespace = "http://webservices.tc.webdreams.com/")
public class EntradaTarjetaListado {

    private String idCiudadano="";
    private String idTarjeta="";
    private String uid="";
    private String modalidadTarjeta="";
    private Integer estadoTarjeta=1;
    private Integer numeroTarjetas;
    private Integer prioridadTarjetas;
    private String idCentroImpresion;
    private String idPuestoImpresion;
    private Integer modalidadImpresion;
    private Integer tipoEntidad;
    private String idCentroRecogida;

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/", defaultValue = "0")
    public Integer getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(Integer tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/", defaultValue = "")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/" ,defaultValue = "")
    public String getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
    }

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/")
    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/", defaultValue = "")
    public String getModalidadTarjeta() {
        return modalidadTarjeta;
    }

    public void setModalidadTarjeta(String modalidadTarjeta) {
        this.modalidadTarjeta = modalidadTarjeta;
    }

    @XmlElement(namespace = "http://webservices.tc.webdreams.com/", defaultValue = "1")
    public int getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(int estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public int getNumeroTarjetas() {
        return numeroTarjetas;
    }

    public void setNumeroTarjetas(int numeroTarjetas) {
        this.numeroTarjetas = numeroTarjetas;
    }

    public int getPrioridadTarjetas() {
        return prioridadTarjetas;
    }

    public void setPrioridadTarjetas(int prioridadTarjetas) {
        this.prioridadTarjetas = prioridadTarjetas;
    }

    public String getIdCentroImpresion() {
        return idCentroImpresion;
    }

    public void setIdCentroImpresion(String idCentroImpresion) {
        this.idCentroImpresion = idCentroImpresion;
    }

    public String getIdPuestoImpresion() {
        return idPuestoImpresion;
    }

    public void setIdPuestoImpresion(String idPuestoImpresion) {
        this.idPuestoImpresion = idPuestoImpresion;
    }

    public int getModalidadImpresion() {
        return modalidadImpresion;
    }

    public void setModalidadImpresion(int modalidadImpresion) {
        this.modalidadImpresion = modalidadImpresion;
    }

    public String getIdCentroRecogida() {
        return idCentroRecogida;
    }

    public void setIdCentroRecogida(String idCentroRecogida) {
        this.idCentroRecogida = idCentroRecogida;
    }

    @Override
    public String toString() {
        return "E:"
                + "idCiudadano:" + idCiudadano
                + "|idTarjeta:" + idTarjeta
                + "|uid:" + uid
                + "|modalidadTarjeta:" + modalidadTarjeta
                + "|estadoTarjeta:" + estadoTarjeta
                + "|numeroTarjetas:" + numeroTarjetas
                + "|prioridadTarjetas:" + prioridadTarjetas
                + "|idCentroImpresion:" + idCentroImpresion
                + "|idPuestoImpresion:" + idPuestoImpresion
                + "|modalidadImpresion:" + modalidadImpresion
                + "|idCentroRecogida:" + idCentroRecogida;
    }

    /* public static class EstadoTarjetaAdapter extends XmlAdapter<Integer, EstadoTarjeta> {

     @Override
     public EstadoTarjeta unmarshal(Integer vt) throws Exception {
     return new EstadoTarjeta(vt);
     }

     @Override
     public Integer marshal(EstadoTarjeta bt) throws Exception {
     return bt.getId();
     }

     }*/
    public class EntradaTarjetaListadoParser {

        private CentroImpresion centroImpresion;
        private PuestoImpresion puestoImpresion;
        private CentroRecogida centroRecogida;
        private EstadoTarjeta estadoTarjeta;

        private Dao dao;

        public EntradaTarjetaListadoParser(Dao dao) {
            this.dao = dao;
        }

        public EntradaTarjetaListado getEntradaTarjetaListado() {
            return EntradaTarjetaListado.this;
        }

        public String parseAndValidate() {

            //EXISTE MODALIDAD
            if (modalidadImpresion==null || !Tarjeta.MODALIDADES_IMPRESION.existeModalidad(modalidadImpresion)) {
                return "915";
            }
            
            if (tipoEntidad==null || !Entidad.TIPO_SOLICITANTE.existeTipoEntidad(tipoEntidad)) {
                return "917";
            }

            //EXISTE CENTRO IMPRESION
            if ((this.centroImpresion = dao.getCentroImpresionIdCentroImpresion(idCentroImpresion)) == null) {
                return "640";
            }

            //EXISTE PUESTO DE IMPRESION
            if ((this.puestoImpresion = dao.getPuestoImpresionIdPuestoImpresion(idPuestoImpresion)) == null) {
                return "650";
            }

            //PUESTO DE IMPRESION PERTENECE A CENTRO DE IMPRESION    
            if (!puestoImpresion.getCentroImpresion().equals(centroImpresion)) {
                return "651";
            }

            //EXISTE CENTRO DE RECOGIDA       
            if (!idCentroRecogida.equals("") && (centroRecogida = dao.getCentroRecogidaIdCentroRecogida(idCentroRecogida)) == null) {
                return "660";
            }

            //EXISTE ESTADO TARJETA
            if ((this.estadoTarjeta = dao.getEstadoTarjetaId(EntradaTarjetaListado.this.estadoTarjeta)) == null) {
                return "211";
            }

            return "0";
        }

        public CentroImpresion getCentroImpresion() {
            return centroImpresion;
        }

        public void setCentroImpresion(CentroImpresion centroImpresion) {
            this.centroImpresion = centroImpresion;
        }

        public PuestoImpresion getPuestoImpresion() {
            return puestoImpresion;
        }

        public void setPuestoImpresion(PuestoImpresion puestoImpresion) {
            this.puestoImpresion = puestoImpresion;
        }

        public CentroRecogida getCentroRecogida() {
            return centroRecogida;
        }

        public void setCentroRecogida(CentroRecogida centroRecogida) {
            this.centroRecogida = centroRecogida;
        }

        public int getPrioridadTarjetas() {
            return prioridadTarjetas;
        }

        public String getIdCiudadano() {
            return idCiudadano;
        }

        public EstadoTarjeta getEstadoTarjeta() {
            return estadoTarjeta;
        }

        public void setEstadoTarjeta(EstadoTarjeta estadoTarjeta) {
            this.estadoTarjeta = estadoTarjeta;
        }

        public String getIdTarjeta() {
            return idTarjeta;
        }

        public String getUid() {
            return uid;
        }

        public int getNumeroTarjetas() {
            return numeroTarjetas;
        }

        public int getModalidadImpresion() {
            return modalidadImpresion;
        }
        
        public int getTipoEntidad(){
            return tipoEntidad;
        }
    }

}
