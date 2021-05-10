package com.wdreams.model.dao.entity;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Roles")
public class Rol implements GrantedAuthority {

    public static final Integer ROLE_CIUDANO = 1;
    public static final Integer ROLE_ACCESO_INTRANET = 2;
    public static final Integer ROLE_CONSULTA_CIUDADANO = 3;
    public static final Integer ROLE_EDITA_CIUDADANO = 4;
    public static final Integer ROLE_CONSULTA_TIPO_CIUDADANO = 5;
    public static final Integer ROLE_EDITA_TIPO_CIUDADANO = 6;
    public static final Integer ROLE_CONSULTA_TIPOLOGIAS = 7;
    public static final Integer ROLE_EDITA_TIPOLOGIAS = 8;
    public static final Integer ROLE_CONSULTA_TARJETA = 9;
    public static final Integer ROLE_EDITA_TARJETA = 10;
    public static final Integer ROLE_GENERA_TARJETAS_PRUEBA = 11;
    public static final Integer ROLE_CONSULTA_TARJETA_VISITANTE = 12;
    public static final Integer ROLE_EDITA_TARJETA_VISITANTE = 13;
    public static final Integer ROLE_CONSULTA_AMBITO = 14;
    public static final Integer ROLE_EDITA_AMBITO = 15;
    public static final Integer ROLE_CONSULTA_CENTRO = 16;
    public static final Integer ROLE_EDITA_CENTRO = 17;
    public static final Integer ROLE_CONSULTA_DISPOSITIVO = 18;
    public static final Integer ROLE_EDITA_DISPOSITIVO = 19;
    public static final Integer ROLE_CONSULTA_USUARIO = 20;
    public static final Integer ROLE_EDITA_USUARIO = 21;
    public static final Integer ROLE_CONSULTA_PERFIL = 22;
    public static final Integer ROLE_EDITA_PERFIL = 23;
    public static final Integer ROLE_CONSULTA_COMPRA = 24;
    public static final Integer ROLE_EDITA_COMPRA = 25;
    public static final Integer ROLE_CONSULTA_RECARGA = 26;
    public static final Integer ROLE_EDITA_RECARGA = 27;
    public static final Integer ROLE_CONSULTA_ACCESO = 28;
    public static final Integer ROLE_EDITA_ACCESO = 29;
    public static final Integer ROLE_CONSULTA_INCIDENCIA = 30;
    public static final Integer ROLE_EDITA_INCIDENCIA = 31;
    public static final Integer ROLE_CONSULTA_BONOS = 32;
    public static final Integer ROLE_EDITA_BONOS = 33;
    public static final Integer ROLE_CONSULTA_USOS_BONOS = 34;
    public static final Integer ROLE_EDITA_USOS_BONOS = 35;
    public static final Integer ROLE_CONSULTA_ASIGNACIONES = 36;
    public static final Integer ROLE_EDITA_ASIGNACIONES = 101;
    public static final Integer ROLE_CONSULTA_CONTROL_FRAUDE = 38;
    public static final Integer ROLE_EDITA_CONTROL_FRAUDE = 39;
    public static final Integer ROLE_CONSULTA_CONTABILIDAD = 104;
    public static final Integer ROLE_EDITA_CONTABILIDAD = 41;
    public static final Integer ROLE_CONSULTA_PROCESOS = 106;
    public static final Integer ROLE_EDITA_PROCESOS = 43;
    public static final Integer ROLE_GENERA_LISTADOS = 44;
    public static final Integer ROLE_CONSULTA_TAREAS_PROGRAMADAS = 45;
    public static final Integer ROLE_EDITA_TAREAS_PROGRAMADAS = 46;
    public static final Integer ROLE_PROCESO_COMPROBAR_PADRON = 47;
    public static final Integer ROLE_CONSULTA_DESCUENTOS = 48;
    public static final Integer ROLE_EDITA_DESCUENTOS = 49;
    public static final Integer ROLE_PRODUCTOS_CONSULTAR = 50;
    public static final Integer ROLE_PRODUCTOS_EDITAR = 51;
    public static final Integer ROLE_GENERA_QR = 126;

    // Roles para los WS.
    public static final Integer ROLE_WS_TIPOLOGIAS = 100;
    public static final Integer ROLE_WS_RECARGAS = 37;
    public static final Integer ROLE_WS_RECARGA_ANULACION = 102;
    public static final Integer ROLE_WS_DISPOSITIVO = 103;
    public static final Integer ROLE_WS_COMPRAS = 40;
    public static final Integer ROLE_WS_COMPRA_ANULACION = 105;
    public static final Integer ROLE_WS_CIUDADANO_TARJETA = 42;
    public static final Integer ROLE_WS_CIUDADANO_CONFIRMA_ACTUALIZACION_ESTADO = 107;
    public static final Integer ROLE_WS_CIUDADANO_ACTUALIZACION_ESTADO = 108;
    public static final Integer ROLE_WS_INFORMACION_TARJETA = 109;
    public static final Integer ROLE_WS_ACCESO = 110;
    public static final Integer ROLE_WS_ACCESO_ANULACION = 111;
    public static final Integer ROLE_WS_CONSULTA_SALDO = 112;
    public static final Integer ROLE_WS_INFORMACION_TARJETAS = 113;
    public static final Integer ROLE_WS_CONFIRMA_DESCARGA_TARJETAS = 114;
    public static final Integer ROLE_WS_CONFIRMA_IMPRESION_TARJETAS = 115;
    public static final Integer ROLE_WS_REIMPRIMIR_TARJETAS = 116;
    public static final Integer ROLE_WS_CONSULTA_CENTROS_RECOGIDA = 117;
    public static final Integer ROLE_WS_VALIDAR_USUARIO = 118;
    public static final Integer ROLE_WS_CONSULTA_ASIGNACIONES_BONOS = 119;
    public static final Integer ROLE_WS_REPORTA_USOS_BONOS_ASIGNACIONES_BONOS = 120;
    public static final Integer ROLE_WS_COMPRAOFFLINE = 121;
    public static final Integer ROLE_WS_DEVOLUCION_SALDO = 122;
    public static final Integer ROLE_WS_INSERTAR_CIUDADANO = 123;
    public static final Integer ROLE_WS_MODIFICAR_PASSWORD = 124;

    public static final Integer ROLE_CMS_APP = 125;
    public static final Integer ROLE_BENEFICIOS = 127;

    public static final Integer ROLE_RECARGAS_MASIVAS = 128;
    public static final Integer ROLE_RECARGAS_MASIVAS_SOLICITUD = 129;

    public static final Integer ROLE_GESTOR_SUBVENCIONES_APP = 130;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "nombreListado", length = 255)
    private String nombreListado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "asignable")
    private Boolean asignable;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreListado() {
        return nombreListado;
    }

    public void setNombreListado(String nombreListado) {
        this.nombreListado = nombreListado;
    }

    @Override
    public String getAuthority() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Rol other = (Rol) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Boolean getAsignable() {
        return asignable;
    }

    public void setAsignable(Boolean asignable) {
        this.asignable = asignable;
    }

}
