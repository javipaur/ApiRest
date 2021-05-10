/**
 * Entidad para gestionar los puntos
 */
package com.wdreams.model.dao.entity;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;

@Entity
@Table(name = "tramosCompra")
@DataTransferObject
public class tramosCompra implements Serializable, TraceLogger {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAmbito() {
        return idAmbito;
    }

    public void setIdAmbito(Integer idAmbito) {
        this.idAmbito = idAmbito;
    }

    public BigDecimal getDesde() {
        return desde;
    }

    public void setDesde(BigDecimal desde) {
        this.desde = desde;
    }

    public BigDecimal getHasta() {
        return hasta;
    }

    public void setHasta(BigDecimal hasta) {
        this.hasta = hasta;
    }

    public BigDecimal getPuntos() {
        return puntos;
    }

    public void setPuntos(BigDecimal puntos) {
        this.puntos = puntos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name = "idAmbito", referencedColumnName = "id")
    @Column(name = "idAmbito", nullable = false)
    private Integer idAmbito;

    @Column(name = "desde")
    private BigDecimal desde;

    @Column(name = "hasta")
    private BigDecimal hasta;

    @Column(name = "puntos", nullable = false)
    private BigDecimal puntos;

    public tramosCompra(){}

    public tramosCompra(Integer idAmbito, BigDecimal desde, BigDecimal hasta, BigDecimal puntos)
    {
        this.idAmbito = idAmbito;
        this.desde = desde;
        this.hasta = hasta;
        this.puntos = puntos;
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
        final tramosCompra other = (tramosCompra) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public Logger getLogger() {
        Logger log = Logger.getLogger(tramosCompra.class);
        try {
            log.addAppender(new FileAppender(new PatternLayout(), this.getIdAmbito() + ".log"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(tramosCompra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return log;
    }

    public class tramosCompraDatosExport {

//        public String getIdAmbito() {
//            return this.getIdAmbito();
//        }
//
//        public String getNombre() {
//            return this.getNombre();
//        }
//
//        public Integer getCentros() {
//            return this.getCentros().size();
//        }

    }
}
