/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils.padron;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.wdreams.utils.LogUtils;
import com.wdreams.utils.SHA1Encode;
import org.apache.log4j.Logger;
import org.directwebremoting.util.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *
 * @author victor
 */
public class ClientePadronGuadalajara {

    public static final Integer OK = 0;
    public static final Integer ERROR = 1;
    public static final Integer ERROR_NO_EXISTE = 2;
    public static final Integer ERROR_VARIOS_CIUDADANOS = 3;

    private static Logger loggerBack = Logger.getLogger("ciudadanaErrorBack");
    private static Logger loggerPadron = Logger.getLogger("ciudadanaComprobarPadronMultiple");

    public Integer comprobarPadron(int codigoTipoDocumento, String documento, String nombre, Date fechaNacimiento) throws RemoteException, Exception {
        String fecha = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Long nonce = new Random().nextLong();
        String e = "";
        if (documento != null) {
            e = "<E><OPE><APL>PAD</APL><TOBJ>HAB</TOBJ><CMD>NUMEROHABITANTES</CMD><VER>2.0</VER></OPE><SEC><CLI>ACCEDE</CLI><ORG>0</ORG><ENT>0</ENT><USU>ZITYCARD01</USU><PWD>mO8iovtkx6lXyvKonx+8t/faUh4=</PWD><FECHA>" + fecha + "</FECHA><NONCE>" + nonce + "</NONCE><TOKENSHA1>" + SHA1Encode.codificarSha1Base64((nonce + fecha + "llave1").getBytes()) + "</TOKENSHA1></SEC><PAR><codigoTipoDocumento>" + codigoTipoDocumento + "</codigoTipoDocumento><documento>" + new String(Base64.encodeBase64((documento).getBytes())) + "</documento><nombre></nombre><particula1></particula1><apellido1></apellido1><particula2></particula2><apellido2></apellido2><fechaNacimiento>" + new SimpleDateFormat("yyyyMMddHHmmss").format(fechaNacimiento) + "</fechaNacimiento><busquedaExacta>-1</busquedaExacta></PAR></E>";
        } else {
            e = "<E><OPE><APL>PAD</APL><TOBJ>HAB</TOBJ><CMD>NUMEROHABITANTES</CMD><VER>2.0</VER></OPE><SEC><CLI>ACCEDE</CLI><ORG>0</ORG><ENT>0</ENT><USU>ZITYCARD01</USU><PWD>mO8iovtkx6lXyvKonx+8t/faUh4=</PWD><FECHA>" + fecha + "</FECHA><NONCE>" + nonce + "</NONCE><TOKENSHA1>" + SHA1Encode.codificarSha1Base64((nonce + fecha + "llave1").getBytes()) + "</TOKENSHA1></SEC><PAR><codigoTipoDocumento>" + codigoTipoDocumento + "</codigoTipoDocumento><documento></documento><nombre>" + new String(Base64.encodeBase64((nombre).getBytes())) + "</nombre><particula1></particula1><apellido1></apellido1><particula2></particula2><apellido2></apellido2><fechaNacimiento>" + new SimpleDateFormat("yyyyMMddHHmmss").format(fechaNacimiento) + "</fechaNacimiento><busquedaExacta>-1</busquedaExacta></PAR></E>";
        }
        LogUtils.escribeLogMensaje(loggerPadron, "ClientePadronGuadalajara.comprobarPadron", "ENTRADA:" + e);
        String respuesta = /* new CiService_Impl().getCi().servicio(e)*/ "<Exito>-1</Exito>";
        /* Exito en la respuesta según el documento de empadronamiento es -1. Falta implementar el webservice que reciba esta respuesta */
        String ejemploRespuesta = "<s><sec><nonce>577801202602632225</nonce></sec><res><exito>-1</exito></res><par><numeroHabitantes>1</numeroHabitantes></par></s>";
        
        Document document = this.parseRespuesta(ejemploRespuesta);

        if (this.getExito(document).equals("-1")) {
            if (this.getNumeroHabitantes(document).equals("1")) {
                return OK;
            } else if (this.getNumeroHabitantes(document).equals("0")) {
                return ERROR_NO_EXISTE;
            } else {
                return ERROR_VARIOS_CIUDADANOS;
            }
        } else {
            LogUtils.escribeLogMensaje(loggerBack, "ClientePadronGuadalajara.comprobarPadron", e);
            LogUtils.escribeLogMensaje(loggerBack, "ClientePadronGuadalajara.comprobarPadron", respuesta);
            return ERROR;
        }
    }

    //COMPRAR PADRON SI NO TIENE DOCUMENTO Y ES MENOR DE EDAD
    public Integer comprobarPadronMenorEdad(int codigoTipoDocumento, String nombre, Date fechaNacimiento) throws RemoteException, Exception {
        String fecha = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Long nonce = new Random().nextLong();
        String e = "<E><OPE><APL>PAD</APL><TOBJ>HAB</TOBJ><CMD>NUMEROHABITANTES</CMD><VER>2.0</VER></OPE><SEC><CLI>ACCEDE</CLI><ORG>0</ORG><ENT>0</ENT><USU>ZITYCARD01</USU><PWD>mO8iovtkx6lXyvKonx+8t/faUh4=</PWD><FECHA>" + fecha + "</FECHA><NONCE>" + nonce + "</NONCE><TOKENSHA1>" + SHA1Encode.codificarSha1Base64((nonce + fecha + "llave1").getBytes()) + "</TOKENSHA1></SEC><PAR><codigoTipoDocumento>" + codigoTipoDocumento + "</codigoTipoDocumento><documento></documento><nombre>" + new String(Base64.encodeBase64((nombre).getBytes())) + "</nombre><particula1></particula1><apellido1></apellido1><particula2></particula2><apellido2></apellido2><fechaNacimiento>" + new SimpleDateFormat("yyyyMMddHHmmss").format(fechaNacimiento) + "</fechaNacimiento><busquedaExacta>-1</busquedaExacta></PAR></E>";
        String respuesta = /*new CiService_Impl().getCi().servicio(e)*/ "<Exito>-1</Exito>";
        Document document = this.parseRespuesta(respuesta);

        if (this.getExito(document).equals("-1")) {
            if (this.getNumeroHabitantes(document).equals("1")) {
                return OK;
            } else if (this.getNumeroHabitantes(document).equals("0")) {
                return ERROR_NO_EXISTE;
            } else {
                return ERROR_VARIOS_CIUDADANOS;
            }
        } else {
            LogUtils.escribeLogMensaje(loggerBack, "ClientePadronGuadalajara.comprobarPadronMenorEdad", e);
            LogUtils.escribeLogMensaje(loggerBack, "ClientePadronGuadalajara.comprobarPadronMenorEdad", respuesta);
            return ERROR;
        }
    }

    public Document parseRespuesta(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public String getExito(Document document) {
        return document.getDocumentElement().getElementsByTagName("res").item(0).getFirstChild().getTextContent();
    }

    public String getNumeroHabitantes(Document document) {
        return ((Element) document.getDocumentElement().getElementsByTagName("par").item(0)).getElementsByTagName("numeroHabitantes").item(0).getTextContent();
    }
}
