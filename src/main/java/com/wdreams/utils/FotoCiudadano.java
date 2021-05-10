package com.wdreams.utils;



import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.AppConfig;
import com.wdreams.model.dao.entity.Entidad;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import javax.activation.FileDataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;



public class FotoCiudadano {

    @Autowired
    private Dao dao;


    public String getFoto(String idCiudadano) {
        if (idCiudadano.equalsIgnoreCase("")) {
            return "";
        }
        InputStream in = null;
        String encodedfile = "";
        try {
            Entidad ciudadano = (Entidad) dao.getEntidadIdEntidad(idCiudadano);
            FileDataSource dataSource = new FileDataSource(dao.getAppConfigId(AppConfig.CARPETA_FOTOS_CIUDADANO).getValor() + ciudadano.getIdEntidad() + ".jpg");
            in = dataSource.getInputStream();
            byte[] byteArray = IOUtils.toByteArray(in);
            encodedfile = new String(Base64.encodeBase64(byteArray), "UTF-8");
        } catch (FileNotFoundException ex) {
            return "";
        } catch (IOException ex) {
           // LogUtils.escribeLog(loggerErrorWS, "InformacionTarjetaWS.getFoto", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
               // LogUtils.escribeLog(loggerErrorWS, "InformacionTarjetaWS.getFoto", ex);
            }
        }
        return encodedfile;
    }
}
