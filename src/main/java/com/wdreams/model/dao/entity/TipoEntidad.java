package com.wdreams.model.dao.entity;

import javax.persistence.*;

/**
 *
 * @author rosa
 */
@Entity
@Table(name = "TipoEntidades")
public class TipoEntidad {

    public static final Integer TARJETA = 1;
    public static final Integer CIUDADANO = 2;
    public static final Integer AMBITO = 3;
    public static final Integer CENTRO = 4;
    public static final Integer DISPOSITIVO = 5;
    public static final Integer USUARIO = 6;
    public static final Integer INCIDENCIA = 7;
    public static final Integer TIPO_CIUDADANO = 8;
    public static final Integer PROCESO_LISTAS_BLANCAS = 9;
    public static final Integer PROCESO_LISTAS_NEGRAS = 10;
    public static final Integer PROCESO_LISTADO_ACTIVACIONES = 11;
    public static final Integer PROCESO_LISTADO_BLOQUEOS = 12;
    public static final Integer PROCESO_LISTADO_ENVIOS = 13;
    public static final Integer PROCESO_PADRON = 14;
    public static final Integer PROCESO_COMPRAS = 15;
    public static final Integer PROCESO_RECARGAS = 16;
    public static final Integer LIQUIDACION = 17;
    public static final Integer PROCESO_FRAUDE = 18;
    public static final Integer PERFIL = 19;
    public static final Integer BONO = 20;
    public static final Integer TIPOLOGIA = 21;
    public static final Integer TAREA_PROGRAMADA = 22;
    public static final Integer ASIGNACION_BONO = 23;
    public static final Integer DESCUENTO = 24;
    public static final Integer PRODUCTO = 25;

    public static final Integer CENTRO_CATEGORIAS = 26;
    public static final Integer EVENTOS = 27;

    public static final Integer CARRUSEL = 28;
    public static final Integer SECCIONES = 29;
    public static final Integer CONTENIDO = 30;

    public static final Integer BENEFICIOS = 31;
    public static final Integer BENEFICIOSTIPOS = 32;



    public static final Integer SOLICITUD_SALDO = 33;

    public static final Integer VENTA = 34;

    public static final Integer TOKENS_PAGOS = 35;

    public static final Integer LIQUIDACION_SALDO = 36;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "clase", length = 255)
    private String clase;

    @Column(name = "metodoId", length = 255)
    private String metodoId;

    @Column(name = "url")
    private String url;

    public TipoEntidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoEntidad() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final TipoEntidad other = (TipoEntidad) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Class getClase() throws ClassNotFoundException {
        return Class.forName(clase);
    }

    public String getMetodoId() {
        return metodoId;
    }

    public String getUrl() {
        return url;
    }

}