/**
 * @author Alberto Piedrafita
 * Clase para listar los resultados agrupados
 */

package com.wdreams.model.dao.entity;


import java.math.BigDecimal;
import java.util.Objects;

public class LiquidacionAgrupado {
    private Integer comercioId;
    private String nombreComercio;
    private BigDecimal importeSinSolicitar;
    private BigDecimal importePendiente;
    private BigDecimal importeLiquidado;
    private BigDecimal total;

    private String idComercio;

    public LiquidacionAgrupado() {
    }

    public LiquidacionAgrupado(String idComercio, Integer comercioId, String nombreComercio, BigDecimal importeSinSolicitar, BigDecimal importePendiente, BigDecimal importeLiquidado, BigDecimal total) {
        this.idComercio = idComercio;
        this.comercioId = comercioId;
        this.nombreComercio = nombreComercio;
        this.importeSinSolicitar = importeSinSolicitar;
        this.importePendiente = importePendiente;
        this.importeLiquidado = importeLiquidado;
        this.total = total;
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

    public BigDecimal getImporteSinSolicitar() {
        return importeSinSolicitar;
    }

    public void setImporteSinSolicitar(BigDecimal importeSinSolicitar) {
        this.importeSinSolicitar = importeSinSolicitar;
    }

    public BigDecimal getImportePendiente() {
        return importePendiente;
    }

    public void setImportePendiente(BigDecimal importePendiente) {
        this.importePendiente = importePendiente;
    }

    public BigDecimal getImporteLiquidado() {
        return importeLiquidado;
    }

    public void setImporteLiquidado(BigDecimal importeLiquidado) {
        this.importeLiquidado = importeLiquidado;
    }

    public BigDecimal getTotal() {
        if (total == null)
            return BigDecimal.ZERO;
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiquidacionAgrupado that = (LiquidacionAgrupado) o;
        return Objects.equals(getComercioId(), that.getComercioId()) &&
                Objects.equals(getNombreComercio(), that.getNombreComercio()) &&
                Objects.equals(getImporteSinSolicitar(), that.getImporteSinSolicitar()) &&
                Objects.equals(getImportePendiente(), that.getImportePendiente()) &&
                Objects.equals(getImporteLiquidado(), that.getImporteLiquidado()) &&
                Objects.equals(getTotal(), that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComercioId(), getNombreComercio(), getImporteSinSolicitar(), getImportePendiente(), getImporteLiquidado(), getTotal());
    }


    public class LiquidacionAgrupadoDatosExport {

        public LiquidacionAgrupadoDatosExport(String nombreComercio, BigDecimal importeSinSolicitar, BigDecimal importePendiente, BigDecimal importeLiquidado, BigDecimal importeTotal) {
            this.nombreComercio = nombreComercio;
            this.importeSinSolicitar = importeSinSolicitar;
            this.importePendiente = importePendiente;
            this.importeLiquidado = importeLiquidado;
            this.importeTotal = importeTotal;
        }

        String nombreComercio;
        BigDecimal importeSinSolicitar;
        BigDecimal importePendiente;
        BigDecimal importeLiquidado;
        BigDecimal importeTotal;

        public String getNombreComercio() {
            return nombreComercio;
        }

        public void setNombreComercio(String nombreComercio) {
            this.nombreComercio = nombreComercio;
        }

        public BigDecimal getImporteSinSolicitar() {
            return importeSinSolicitar;
        }

        public void setImporteSinSolicitar(BigDecimal importeSinSolicitar) {
            this.importeSinSolicitar = importeSinSolicitar;
        }

        public BigDecimal getImportePendiente() {
            return importePendiente;
        }

        public void setImportePendiente(BigDecimal importePendiente) {
            this.importePendiente = importePendiente;
        }

        public BigDecimal getImporteLiquidado() {
            return importeLiquidado;
        }

        public void setImporteLiquidado(BigDecimal importeLiquidado) {
            this.importeLiquidado = importeLiquidado;
        }

        public BigDecimal getImporteTotal() {
            return importeTotal;
        }

        public void setImporteTotal(BigDecimal importeTotal) {
            this.importeTotal = importeTotal;
        }
    }
}
