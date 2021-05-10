package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class modeloBeneficio {
//    private Integer pagina;
//    private Integer cantidadResultados;
    private Integer categoriaId;

//    public Integer getPagina() {
//        return pagina;
//    }
//
//    public void setPagina(Integer pagina) {
//        this.pagina = pagina;
//    }
//
//    public Integer getCantidadResultados() {
//        return cantidadResultados;
//    }
//
//    public void setCantidadResultados(Integer cantidadResultados) {
//        this.cantidadResultados = cantidadResultados;
//    }

    private Date FechaInicio;

    private Integer tipoBeneficio;

    private Boolean comercioRecomendado;

    private Boolean conBeneficio;

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public Integer getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(Integer tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public Boolean getComercioRecomendado() {
        return comercioRecomendado;
    }

    public void setComercioRecomendado(Boolean comercioRecomendado) {
        this.comercioRecomendado = comercioRecomendado;
    }

    public Boolean getConBeneficio() {
        return conBeneficio;
    }

    public void setConBeneficio(Boolean conBeneficio) {
        this.conBeneficio = conBeneficio;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
