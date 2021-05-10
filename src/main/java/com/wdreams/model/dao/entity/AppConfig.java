/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.wdreams.model.dao.Dao;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "AppConfig")
public class AppConfig {

    public static enum ConfigRecargas{
        RECARGAS_MODULO,
        RECARGAS_MINIMO,
        RECARGAS_MAXIMO,
        RECARGAS_SALDO_MAXIMO
    }


    public static int CARPETA_FOTOS_CIUDADANO = 1;
    public static int CARPETA_ADJUNTOS_NOTIFICACION = 2;
    public static int CARPETA_PLANTILLAS = 3;
    public static int SMTP_EMAIL = 4;
    public static int PORT_EMAIL = 5;
    public static int USER_EMAIL = 6;
    public static int PASS_EMAIL = 7;
    public static int CARPETA_XML_CIUDADANO = 8;
    public static int RETRASO_MAXIMO_DIAS = 9;
    public static int CARPETA_ADJUNTOS_INCIDENCIA = 10;
    public static int CARPETA_LISTADOS = 11;
    public static int CARPETA_ADJUNTOS_TIPOS = 12;
    public static int SERVER_CONEXION_SFTP_TOTALES = 13;
    public static int SERVER_CONEXION_SFTP_POSPAGO = 14;
    public static int SERVER_CONEXION_SFTP_PREPAGO = 15;
    public static int PORT_CONEXION_SFTP = 16;
    public static int USER_CONEXION_SFTP = 17;
    public static int PASS_CONEXION_SFTP = 18;
    public static int NUM_CIUDADANOS_PADRON = 19;
    public static int ULTIMO_CIUDADANO_PADRON = 20;
    public static int BLOQUEAR_CIUDADANO_PADRON = 21;
    public static int MARGEN_HORAS_OPERACION = 22;
    public static int CARPETA_AMBITOS = 23;
    public static int SEPARACION_DIRECTORIOS = 24;
    public static int USER_EMAIL_DESTINO = 25;
    public static int AMBITO_AJUSTE = 26;
    public static int ULTIMO_NUM_OPERACION_COMPRA_NEG = 27;
    public static int ULTIMO_NUM_OPERACION_RECARGA_NEG = 28;
    public static int NUM_TARJETAS_CONTROL_FRAUDE = 29;
    public static int DIAS_MARGEN_CONTROL_FRAUDE = 30;
    public static int ULTIMA_TARJETA_CONTROL_FRAUDE = 31;
    public static int PADRON_ACTIVADO = 32;
    public static int SALDO_MAXIMO = 33;
    public static int TIEMPO_ASIGNACION_IMPRESION = 34;
    public static int CARPETA_CONFIGURACION = 35;
    public static int CARPETA_DOCUMENTOS = 36;
    public static int CARPETA_PROCESOS = 37;
    public static int COMPROBAR_VERSION_APP_TCA = 38;
    public static int APK_APP_TCA = 39;
    public static int ALIAS_EMAIL = 40;
    public static int VALIDAR_PADRON_CON_TABLAS = 51;
    public static int USERS_EMAIL_COPIAS_OCULTAS = 52;


    public static int CARPETA_FOTOS_CMS = 53;

    public static int CARPETA_TICKETS_COMPRA = 55;

    public static int SALDO_MAXIMO_CIUDADANO_BONIFICACIONES = 56;

    public static int USERS_EMAIL_COPIAS_OCULTAS_EMPRESA = 54;

    public static int SALDO_MAXIMO_COMERCIOS_MENSUAL = 57;
    public static int SALDO_MAXIMO_COMERCIOS = 58;

    public static int DELAY_MINUTOS_TOKENS_TEMPORALES = 59;


    public static int PORCENTAJE_SUBVENCION_COMERCIOS = 60;
    public static int CARPETA_FOTO_TICKETS = 61;


    public static int CARPETA_LIQUIDACIONES = 62;
    public static int EMAIL_AYUNTAMIENTO = 63;

    public static int SALDO_MAXIMO_CIUDADANO_MENSUAL = 64;

    public static int MINIMO_COMPRA_BONIFICAR = 65;
    public static int MINIMO_COMPRA_COBRAR = 66;
    public static int FCM_TOKEN = 67;
    public static int VALIDAR_PREFIJO_ID_CIUDADANO=68;
    public static int VALIDAR_PREFIJO_ID_COMERCIO=69;
    public static int URL_APP_ANDROID=70;
    public static int URL_APP_IOS=71;
    public static int ENTORNO_PRE_PRO=72;
    public static int SMTP_EMAIL_SSL=73;
    public static int URL_PRIVACIDAD=74;
    public static int URL_CONDICIONES=75;
    public static int LIMITE_TAMANYO_FOTO_TICKET_MB=76;
    public static int TOTAL_RECARGAS=77;
    public static int FECHAINICIO_CAMPANA_COMERCIO=78;
    public static int FECHAFIN_CAMPANA_COMERCIO=79;
    public static int CIUDADANOS_PRUEBA_FILTER_OUT=80;
    public static int EMAIL_CCO_ESTADISTICAS=81;
    public static int EMAIL_TO_ESTADISTICAS=82;
    public static int FECHA_BLOQUEO_GASTO_SALDOVIRTUAL=83;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "valor", nullable = false)
    private String valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public static String getSeparacionDirectorios(Dao dao) {
        return dao.getAppConfigId(AppConfig.SEPARACION_DIRECTORIOS).getValor();
    }
}
