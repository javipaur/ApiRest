/**
 * @author Alberto Piedrafita
 * Clase para listar los resultados agrupados
 */

package com.wdreams.model.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class CobrosBonosAgrupadoVM {
    private Integer comercioId;
    private String idComercio;
    private String nombreComercio;
    private Long totalOperaciones;
    private BigDecimal totalImporteTicket;
    private BigDecimal totalCobroBonoConsumo;


    public CobrosBonosAgrupadoVM() {
    }

    public CobrosBonosAgrupadoVM(String idComercio, Integer comercioId,
                                 String nombreComercio,
                                 Long totalOperaciones,
                                 BigDecimal totalImporteTicket, BigDecimal totalCobroBonoConsumo) {
        this.idComercio = idComercio;
        this.comercioId = comercioId;
        this.nombreComercio = nombreComercio;
        this.totalOperaciones = totalOperaciones;
        this.totalImporteTicket = totalImporteTicket;
        this.totalCobroBonoConsumo = totalCobroBonoConsumo;
    }



    public String getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(String idComercio) {
        this.idComercio = idComercio;
    }

    public Integer getComercioId() {
        return comercioId;
    }

    public void setComercioId(Integer comercioId) {
        this.comercioId = comercioId;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }


    public Long getTotalOperaciones() {
        return totalOperaciones;
    }

    public void setTotalOperaciones(Long totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }

    public BigDecimal getTotalImporteTicket() {
        return totalImporteTicket;
    }

    public void setTotalImporteTicket(BigDecimal totalImporteTicket) {
        this.totalImporteTicket = totalImporteTicket;
    }

    public BigDecimal getTotalCobroBonoConsumo() {
        return totalCobroBonoConsumo;
    }

    public void setTotalCobroBonoConsumo(BigDecimal totalCobroBonoConsumo) {
        this.totalCobroBonoConsumo = totalCobroBonoConsumo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CobrosBonosAgrupadoVM that = (CobrosBonosAgrupadoVM) o;
        return Objects.equals(getComercioId(), that.getComercioId()) &&
                Objects.equals(getIdComercio(), that.getIdComercio()) &&
                Objects.equals(getNombreComercio(), that.getNombreComercio()) &&
                Objects.equals(getTotalOperaciones(), that.getTotalOperaciones()) &&
                Objects.equals(getTotalImporteTicket(), that.getTotalImporteTicket()) &&
                Objects.equals(getTotalCobroBonoConsumo(), that.getTotalCobroBonoConsumo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComercioId(), getIdComercio(), getNombreComercio(), getTotalOperaciones(), getTotalImporteTicket(), getTotalCobroBonoConsumo());
    }

    public class CobrosBonosAgrupadoDatosExport {

        public CobrosBonosAgrupadoDatosExport(String nombreComercio,
                                              Long totalOperaciones,
                                              BigDecimal totalImporteTicket, BigDecimal totalCobroBonoConsumo) {
            this.nombreComercio = nombreComercio;
            this.totalOperaciones = totalOperaciones;
            this.totalImporteTicket = totalImporteTicket;
            this.totalCobroBonoConsumo = totalCobroBonoConsumo;
        }

        String nombreComercio;
        Long totalOperaciones;
        BigDecimal totalImporteTicket;
        BigDecimal totalCobroBonoConsumo;

        public String getNombreComercio() {
            return nombreComercio;
        }

        public void setNombreComercio(String nombreComercio) {
            this.nombreComercio = nombreComercio;
        }

        public Long getTotalOperaciones() {
            return totalOperaciones;
        }

        public void setTotalOperaciones(Long totalOperaciones) {
            this.totalOperaciones = totalOperaciones;
        }

        public BigDecimal getTotalImporteTicket() {
            return totalImporteTicket;
        }

        public void setTotalImporteTicket(BigDecimal totalImporteTicket) {
            this.totalImporteTicket = totalImporteTicket;
        }

        public BigDecimal getTotalCobroBonoConsumo() {
            return totalCobroBonoConsumo;
        }

        public void setTotalCobroBonoConsumo(BigDecimal totalCobroBonoConsumo) {
            this.totalCobroBonoConsumo = totalCobroBonoConsumo;
        }
    }
}
