package com.wdreams.model.rest.response;




import com.wdreams.utils.DateUtils;

import java.util.Date;

public class RespuestaMensaje{

    private String funcion;
    private String error;
    private String error_mensaje;
    private String fecha;

    public RespuestaMensaje(){}
    
    public RespuestaMensaje(String funcion, String error, String error_mensaje) {
        this.funcion = funcion;
        this.error = error;
        this.error_mensaje = error_mensaje;
        this.fecha = DateUtils.ddMMyyyyHHmmss.format(new Date());
    }
    

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    

    public String getError_mensaje() {
        return error_mensaje;
    }

    public void setError_mensaje(String error_mensaje) {
        this.error_mensaje = error_mensaje;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
         return "S:"+"funcion:"+funcion+"|error:"+error+"|error_mensaje:"+error_mensaje +"|fecha:"+fecha;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    private int idRespuesta;
}
