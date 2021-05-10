package com.wdreams.model.dao.entity;

import java.math.BigDecimal;

public class ResumenPuntos {
    public BigDecimal getCantidadPuntos() {
        return cantidadPuntos;
    }

    public void setCantidadPuntos(BigDecimal cantidadPuntos) {
        this.cantidadPuntos = cantidadPuntos;
    }

    public Long getCantidadCiudadanos() {
        return cantidadCiudadanos;
    }

    public void setCantidadCiudadanos(Long cantidadCiudadanos) {
        this.cantidadCiudadanos = cantidadCiudadanos;
    }

    private BigDecimal cantidadPuntos;
    private Long cantidadCiudadanos;
}
