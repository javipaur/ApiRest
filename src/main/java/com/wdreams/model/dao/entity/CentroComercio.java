package com.wdreams.model.dao.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Juan
 */
@Entity
@Table(name = "Centros")
@DiscriminatorValue("1")
public class CentroComercio extends Centro {

    @Column(name = "importeCobrar")
    private BigDecimal importeCobrar;

    @Column(name = "plazoTiempo")
    private Integer plazoTiempo;

    @Column(name = "porcentajeDescuento")
    private Integer porcentajeDescuento;



    @Column(name = "comercioRecomendado")
    private Boolean comercioRecomendado;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "horario")
    private String horario;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "tip1")
    private String tip1;

    @Column(name = "tip2")
    private String tip2;

    //@Column(name ="idCategoria")
    //private int idCategoria;

    public CentroComercio() {
    }

    public CentroComercio(String nombre, String descripcion, Ambito ambito, String mail,
                          BigDecimal importeCobrar, Integer plazoTiempo, Integer porcentajeDescuento,
                          Boolean gestionaPuntos) {
        super(nombre, descripcion, ambito, mail);
        this.importeCobrar = importeCobrar;
        this.plazoTiempo = plazoTiempo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.setGestionaPuntos(gestionaPuntos);
    }


    public CentroComercio(String nombre, String descripcion, Ambito ambito, String mail,
                          BigDecimal importeCobrar, Integer plazoTiempo, Integer porcentajeDescuento,
                          Boolean gestionaPuntos,
                          Boolean comercioRecomendado, String direccion, String horario, String telefono,
                          String tip1, String tip2) {
        super(nombre, descripcion, ambito, mail);
        this.importeCobrar = importeCobrar;
        this.plazoTiempo = plazoTiempo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.setGestionaPuntos(gestionaPuntos);
        this.comercioRecomendado = comercioRecomendado;
        this.direccion = direccion;
        this.horario = horario;
        this.telefono = telefono;
        this.tip1 = tip1;
        this.tip2 = tip2;
    }



    public BigDecimal getImporteCobrar() {
        return importeCobrar;
    }

    public void setImporteCobrar(BigDecimal importeCobrar) {
        this.importeCobrar = importeCobrar;
    }

    public Integer getPlazoTiempo() {
        return plazoTiempo;
    }

    public void setPlazoTiempo(Integer plazoTiempo) {
        this.plazoTiempo = plazoTiempo;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public void asignarIdCentro() {
        this.setIdCentro(String.format("C%06X", this.getId()));
    }


    public Boolean getComercioRecomendado() {
        return comercioRecomendado;
    }

    public void setComercioRecomendado(Boolean comercioRecomendado) {
        this.comercioRecomendado = comercioRecomendado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTip1() {
        return tip1;
    }

    public void setTip1(String tip1) {
        this.tip1 = tip1;
    }

    public String getTip2() {
        return tip2;
    }

    public void setTip2(String tip2) {
        this.tip2 = tip2;
    }

    //public Integer getIdCategoria(){ return idCategoria; }

    //public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public class ComercioDatosExport extends CentroDatosExport {

        public Boolean getGestionaPuntos() {
            if (Boolean.TRUE == CentroComercio.this.getGestionaPuntos()) {
                return true;
            } else {
                return false;
            }
        }


        public Integer getPlazoTiempo() {
            return CentroComercio.this.getPlazoTiempo();
        }

        public BigDecimal getImporteCobrar() {
            return CentroComercio.this.getImporteCobrar();
        }

        public Integer getPorcentajeDescuento() {
            return CentroComercio.this.getPorcentajeDescuento();
        }

        //public Integer getIdCategoria() {return CentroComercio.this.getIdCategoria(); }

        public String getCategoria() {return CentroComercio.this.getCmscategoria().getNombre(); }

        public Boolean getComercioRecomendado() {return CentroComercio.this.getComercioRecomendado(); }

        public Boolean getApareceApp() { return CentroComercio.this.getMostrar();}
        //public Boolean getApareceApp() { return CentroComercio.this.getVisible();} //por si acaso
    }
}
