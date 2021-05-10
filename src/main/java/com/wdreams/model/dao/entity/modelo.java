package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class modelo {
    private Integer pagina;
    private Integer cantidadResultados;
    private Integer categoriaId;

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(Integer cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
