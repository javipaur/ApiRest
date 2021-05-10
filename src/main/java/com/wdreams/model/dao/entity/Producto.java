/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "Productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idProducto", length = 6)
    private String idProducto;

    @Column(name = "nombre", length = 20)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "importe")
    private BigDecimal importe;

    @Column(name = "importeFijo")
    private Boolean importeFijo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idCentro", nullable = false, referencedColumnName = "id")
    protected Centro centro;

    public Integer getId() {
        return this.id;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdProducto() {
        return this.idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return this.importe;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Boolean getImporteFijo() {
        return importeFijo;
    }

    public void setImporteFijo(Boolean importeFijo) {
        this.importeFijo = importeFijo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto() {
    }

    public Producto(String nombre, String descripcion, BigDecimal importe, Centro centro, Boolean importeFijo, Integer cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = importe;
        this.centro = centro;
        this.importeFijo = importeFijo;
        this.cantidad = cantidad;
    }

    public void asignarIdProducto() {
        this.setIdProducto(String.format("P%03X", this.getId()));
    }

    public class ProductosDatosExport {

        public String getIdProducto() {
            return Producto.this.getIdProducto();
        }

        public String getNombre() {
            return Producto.this.getNombre();
        }

        public String getDescripcion() {
            return Producto.this.getDescripcion();
        }

        public BigDecimal getImporte() {
            return Producto.this.getImporte();
        }

        public String getCentro() {
            return Producto.this.centro.getNombre();
        }

        public Boolean getImporteFijo() {
            return Producto.this.getImporteFijo();
        }

        public Integer getCantidad() {
            return Producto.this.getCantidad();
        }
    }

}
