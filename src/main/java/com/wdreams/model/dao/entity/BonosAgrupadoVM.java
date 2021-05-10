/**
 * @author Alberto Piedrafita
 * Clase para listar los resultados agrupados
 */

package com.wdreams.model.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class BonosAgrupadoVM {
    private Integer comercioId;
    private String nombreComercio;
    private Long totalOperaciones;
    private BigDecimal totalTickets;
    private BigDecimal totalBonosConsumo;

    private String idComercio;

    public BonosAgrupadoVM() {
    }

    public BonosAgrupadoVM(String idComercio, Integer comercioId,
                           String nombreComercio,
                           Long totalOperaciones, BigDecimal totalTickets,
                           BigDecimal totalBonosConsumo) {
        this.idComercio = idComercio;
        this.comercioId = comercioId;
        this.nombreComercio = nombreComercio;
        this.totalOperaciones = totalOperaciones;
        this.totalTickets = totalTickets;
        this.totalBonosConsumo = totalBonosConsumo;
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

    public BigDecimal getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(BigDecimal totalTickets) {
        this.totalTickets = totalTickets;
    }

    public BigDecimal getTotalBonosConsumo() {
        return totalBonosConsumo;
    }

    public void setTotalBonosConsumo(BigDecimal totalBonosConsumo) {
        this.totalBonosConsumo = totalBonosConsumo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonosAgrupadoVM that = (BonosAgrupadoVM) o;
        return Objects.equals(getComercioId(), that.getComercioId()) &&
                Objects.equals(getNombreComercio(), that.getNombreComercio()) &&
                Objects.equals(getTotalOperaciones(), that.getTotalOperaciones()) &&
                Objects.equals(getTotalTickets(), that.getTotalTickets()) &&
                Objects.equals(getTotalBonosConsumo(), that.getTotalBonosConsumo()) &&
                Objects.equals(getIdComercio(), that.getIdComercio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComercioId(), getNombreComercio(), getTotalOperaciones(), getTotalTickets(), getTotalBonosConsumo(), getIdComercio());
    }

    public class BonosAgrupadoDatosExport {

        public BonosAgrupadoDatosExport(String nombreComercio, Long totalOperaciones,
                                        BigDecimal totalTickets, BigDecimal totalBonosConsumo) {
            this.nombreComercio = nombreComercio;
            this.totalOperaciones = totalOperaciones;
            this.totalTickets = totalTickets;
            this.totalBonosConsumo = totalBonosConsumo;
        }

        String nombreComercio;
        Long totalOperaciones;
        BigDecimal totalTickets;
        BigDecimal totalBonosConsumo;

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

        public BigDecimal getTotalTickets() {
            return totalTickets;
        }

        public void setTotalTickets(BigDecimal totalTickets) {
            this.totalTickets = totalTickets;
        }

        public BigDecimal getTotalBonosConsumo() {
            return totalBonosConsumo;
        }

        public void setTotalBonosConsumo(BigDecimal totalBonosConsumo) {
            this.totalBonosConsumo = totalBonosConsumo;
        }
    }
}
