/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;


import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.AppConfig;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author victor
 */
@Transactional
public class Plantillas {

    public static String PLANTILLA_ALTA_CIUDADANO = "altaCiudadano.html";
    public static String PLANTILLA_CAMBIO_ESTADO_CIUDADANO = "cambioEstadoCiudadano.html";
    public static String PLANTILLA_CAMBIO_ESTADO_TARJETA = "cambioEstadoTarjeta.html";
    public static String PLANTILLA_CAMBIO_ESTADO_INCIDENCIA = "cambioEstadoIncidencia.html";
    public static String PLANTILLA_RECUPERAR_PASSWORD = "recuperarPassword.html";
    public static String PLANTILLA_RESET_PASSWORD = "resetPassword.html";
    public static String PLANTILLA_MODIFICAR_PASSWORD = "modificarPassword.html";
    public static String PLANTILLA_INCIDENCIA = "incidencia.html";

    public static String PLANTILLA_BONO = "asignacionBono.html";

    public static String PLANTILLA_EMPRESA = "nuevaEmpresa.html";
    public static String PLANTILLA_RECARGA = "recarga.html";

    public static String PLANTILLA_SOLICITUD_LIQUIDACION = "solicitudLiquidacion.html";

    public static String PLANTILLA_ESTADISTICAS = "estadistica.html";

    private Dao dao;

   /* @Autowired
    public void setDao(DAO dao) {
        this.dao = dao;
    }*/

    public Plantillas(Dao dao) {
        this.dao= dao;
    }

   /* public String getHistoricoCiudadanoText(HistorialCiudadano historialCiudadano) throws FileNotFoundException, IOException{
        if(historialCiudadano.getEstado().equals(dao.getEstadoCiudadanoId(EstadoCiudadano.ACTIVO))){
            return this.getText(PLANTILLA_ALTA_CIUDADANO);
        }
        return "";
    }*/

    public  String getText(String nombrePlantilla) throws FileNotFoundException, IOException {
        //DAO dao = (DAO) AppContext.getApplicationContext().getBean("miDAO", DAO.class);
        BufferedReader bf = new BufferedReader(new FileReader(dao.getAppConfigId(AppConfig.CARPETA_PLANTILLAS).getValor() + nombrePlantilla));
        String text = "";
        String linea = bf.readLine();
        while (linea != null) {
            text += linea;
            linea = bf.readLine();
            //System.out.println(text);
        }
        return text;
    }
}
