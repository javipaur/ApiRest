package com.wdreams.model.dao.entity;


import java.math.BigDecimal;

public class TramosCompraElemento {

    public TramosCompraElemento() {
    }

    public TramosCompraElemento(tramosCompra tramo) {
//        this.id = tramo.getId();
//        this.desde = tramo.getDesde();
        this.hasta = tramo.getHasta();
//        this.idAmbito = tramo.getIdAmbito();
        this.puntos = tramo.getPuntos();
    }


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getIdAmbito() {
//        return idAmbito;
//    }
//
//    public void setIdAmbito(Integer idAmbito) {
//        this.idAmbito = idAmbito;
//    }

//    public BigDecimal getDesde() {
//        return desde;
//    }
//
//    public void setDesde(BigDecimal desde) {
//        this.desde = desde;
//    }

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

    private Integer id;


    private Integer idAmbito;

//    private BigDecimal desde;

    private BigDecimal hasta;

    private BigDecimal puntos;
}
