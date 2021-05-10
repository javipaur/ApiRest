package com.wdreams.controllers;


import com.wdreams.jwt.exception.ErrorMensaje;
import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.Entidad;
import com.wdreams.model.dao.entity.EstadoTarjeta;
import com.wdreams.model.dao.entity.Tarjeta;
import com.wdreams.model.rest.response.RespuestaInformacionCiudadano;
import com.wdreams.model.rest.response.RespuestaMensaje;
import com.wdreams.utils.FotoCiudadano;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/")
@Api( tags = "Ciudadanos")
public class Ciudadanos {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private Dao dao;

    private ErrorMensaje msj;

    private FotoCiudadano fotoCiudadano;


    @GetMapping("/informacionCiudadano")
    @ApiOperation(value = "informacionCiudadano",notes = "Informacion del ciudadano")
    //@Secured("ROLE_CONSULTA_TARJETA")
    public ResponseEntity<RespuestaInformacionCiudadano> informacionCiudadano() {

        RespuestaInformacionCiudadano respuesta=new RespuestaInformacionCiudadano();
        RespuestaMensaje err=new RespuestaMensaje();

        String idCiudadano="C1017DF84";
        long tiempoInicio = System.currentTimeMillis();
        try {
            //VALIDAR QUE VENGA INFORMADO RFID O ID TARJETA
//            if ((uid == null || uid.equals("")) && (idTarjeta == null || idTarjeta.equals(""))) {
//                return getSalidaServicio("910", "", "", "", "", "", "", "", "", tiempoInicio);
//            }

            //BUSCAR TARJETA
            Entidad ciudadano = dao.getEntidadIdEntidad(idCiudadano);
            //if (ciudadano == null){
          //  Entidad ciudadano = dao.getEntidadByDNI(idCiudadano);
           // }
//            Tarjeta tarjeta = ProcesosServicios.buscarTarjeta(dao, uid, idTarjeta, "");
            if (ciudadano == null) {
                // return getSalidaServicio("201", "", "", "", "", "", "", null, "", "", "", "", "", tiempoInicio);
                //Para dar errror montamos el objeto respusta

                err.setFuncion("informacionCiudadano");
                err.setError("201");
                err.setError_mensaje(msj.getConfigValue("errorWS.usuario.tarjetaNoPertenece"));
                respuesta.setRespuesta(err);
                return  ResponseEntity.ok(respuesta);
            }
            //Set<Tarjeta> tarjetas = dao.getEntidadIdEntidad(idCiudadano).getTarjetas();
            Set<Tarjeta> tarjetas = ciudadano.getTarjetas();
            String uid = "";
            String idTarjeta = "";
            if (tarjetas != null) {
                // for (Tarjeta tarjeta : dao.getEntidadIdEntidad(idCiudadano).getTarjetas()) {
                for (Tarjeta tarjeta : tarjetas) {
                    if (tarjeta.getEstado().getId().equals(EstadoTarjeta.ACTIVA) || tarjeta.getEstado().getId().equals(EstadoTarjeta.PENDIENTE_IMPRIMIR) || tarjeta.getEstado().getId().equals(EstadoTarjeta.PENDIENTE_ACTIVAR)) {
                        uid = tarjeta.getUid();
                        idTarjeta = tarjeta.getIdTarjeta();
                        break;
                    }
                }
            }
            //Todo ha ido bien
            err.setFuncion("informacionCiudadano");
            err.setError("0");
            //err.setError_mensaje(msj.getConfigValue("errorWS.operacionCorrecta"));
            err.setError("Todo ha ido bien!!");
            respuesta.setRespuesta(err);
            respuesta.setNombre(ciudadano.getNombre());
            respuesta.setApellidos(ciudadano.getApellidos());
            respuesta.setTelefono(ciudadano.getTelefono());
            respuesta.setMovil(ciudadano.getMovil());
            respuesta.setDireccion(ciudadano.getDireccion());
            respuesta.setDocumento(ciudadano.getDocumento());
            respuesta.setFechaNacimiento(ciudadano.getFechaNacimiento());
            respuesta.setEmail(ciudadano.getUserEmail());
            respuesta.setEstado(ciudadano.getDocumento());
            respuesta.setFoto(fotoCiudadano.getFoto(ciudadano.getIdCiudadano()));
            respuesta.setUid(uid);
            respuesta.setIdTarjeta(idTarjeta);

            // return getSalidaServicio("0", ciudadano, this.getFoto(ciudadano.getIdCiudadano()), uid, idTarjeta, tiempoInicio);
        } catch (Exception e) {
            //LogUtils.escribeLog(loggerErrorWS, "InformacionCiudadanoWS.informacionCiudadano."+ idCiudadano, e);
            //return getSalidaServicio("999", idCiudadano, "", "", "", "", "", null, "", "", "", "", "", tiempoInicio);
        }

        return ResponseEntity.ok(respuesta);
    }








}
