package com.wdreams.utils;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 30/07/2020
 * xFer quiere filtros en los listados.
 *
 * Planteamiento de la soluci�n:
 *      1.- En el DAO consulto la sesi�n y si existe el identificadorListado en la sesi�n ordeno por ese criterio
 *      2.- Convierto la cabecera de los listados con par�metros querystring
 *      3.- Mediante customTaglibFiltro checkeo si muestro el icono para arriba o para abajo en la cabecera
 *      4.- Por querystring del get, capturo las variables: identificadorListado, campo, direcci�n.
 *          Si no viene querystring limpio ese filtro de la sesi�n
 *      5.- A correr!!!
 */

@Component//Para que los beans encuentren esta clase
public class OrdenBySession {
    public OrdenBySession(){

    }


    /**
     * Recibe un tipo de listado y devuelve lo que haya en la sesi�n
     * @param tipo el tipo a buscar en la sesi�n
     * @return
     */
    public SessionFilters getSessionFilter(LISTADO_TIPOS tipo)
    {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        if(session.getAttribute(tipo.name()) != null)
        {
            return (SessionFilters)session.getAttribute(tipo.name());
        }
        return null;
    }

    /**
     * Recibe un objeto a almacenar en la sesi�n
     * @param filtro el objeto filtro, dentro contiene el tipo de listado
     */
    public void setSessionFilter(SessionFilters filtro)
    {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute(filtro.tipoListado.name(), filtro);
    }


    public void setSessionFilter(Integer tipoListado, String campo)
    {
        SessionFilters pre = getSessionFilter(getListadoById(tipoListado));
        if(pre != null && pre.getCampo().equals(campo))
        {
            /**
             * Sesi�n pre-existente y mismo campo, me limito a cambiar el orden
             */
            switch (pre.getOrden())
            {
                case ASC:
                    pre.setOrden(ORDEN.DESC);
                    break;
                case DESC:
                    pre.setOrden(ORDEN.ASC);
                    break;
            }
            setSessionFilter(pre);
        }
        else{
            pre = new SessionFilters();
            pre.setOrden(ORDEN.ASC);
            pre.setCampo(campo);
            pre.setTipoListado(getListadoById(tipoListado));
            setSessionFilter(pre);
        }
    }


    /**
     * Elimina un objeto de la sesi�n estableci�ndolo en nulo.
     * Est� pensado para cuando no venga ning�n par�metro en el get
     * @param tipo el objeto a eliminar
     */
    public void removeSessionFilter(LISTADO_TIPOS tipo)
    {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute(tipo.name(), null);
    }

    /**
     * Esta clase es la encargada de almacenar las variables necesarias para conocer qu� filtro
     * se desea aplicar.
     */
    @Scope("session")
    public class SessionFilters{
        private LISTADO_TIPOS tipoListado;
        private String campo;
        private ORDEN orden;

        public SessionFilters(){}

        public LISTADO_TIPOS getTipoListado() {
            return tipoListado;
        }

        public void setTipoListado(LISTADO_TIPOS tipoListado) {
            this.tipoListado = tipoListado;
        }

        public String getCampo() {
            return campo;
        }

        public void setCampo(String campo) {
            this.campo = campo;
        }

        public ORDEN getOrden() {
            return orden;
        }

        public void setOrden(ORDEN orden) {
            this.orden = orden;
        }
    }

    /**
     * Pongo en un enum el tipo de orden para "ayudar a la memoria"
     */
    public static enum ORDEN{
        ASC(1),
        DESC(2);

        private int id;

        ORDEN(int id) {
            this.id = id;
        }

        public int getNum() {
            return id;
        }
    }

    /**
     * Pongo en un enum los tipos de listado para "ayudar a la memoria"
     */
    public enum LISTADO_TIPOS {
        GESTION_USUARIOS(1),
        GESTION_PERFILES(2),
        GESTION_ACCIONES(3),

        CIUDADANOS(4),

        TARJETAS(5),

        RECURSOS_AMBITOS(6),
        RECURSOS_CENTROS(7),
        RECURSOS_COMERCIOS(8),
        RECURSOS_APP_EVENTOS(9),
        RECURSOS_APP_CATEGORIAS_EVENTOS(10),
        RECURSOS_APP_CATEGORIAS_CENTROS(31),
        RECURSOS_APP_CATEGORIAS_COMERCIOS(32),
        RECURSOS_APP_CARRUSEL(11),
        RECURSOS_APP_CONTENIDOS(12),
        RECURSOS_APP_SECCIONES(13),
        RECURSOS_DISPOSITIVOS(14),
        RECURSOS_BONOS(15),
        RECURSOS_BONOS_ASIGNACIONES(16),
        RECUROS_PRODUCTOS(17),

        USOS_DESCUENTOS(18),
        USOS_DESCUENTOS_AMBITOS(19),
        USOS_DESCUENTOS_CENTROS(20),
        USOS_RECARGAS(21),
        USOS_RECARGAS_AMBITOS(22),
        USOS_RECARGAS_CENTROS(23),

        USOS_ACCESOS(33),
        USOS_ACCESOS_AMBITOS(34),
        USOS_ACCESOS_CENTROS(35),

        USOS_BONOS(24),
        USOS_BONOS_AMBITOS(25),
        USOS_BONOS_CENTROS(26),

        INCIDENCIAS(27),

        TARIFICACION_TIPOS_CIUDADANO(28),
        TARIFICACION_DESCUENTOS(29),
        TARIFICACION_FRAUDE(30),

        BENEFICIOS(36),
        BENEFICIOS_TIPOS(37),

        RECARGAS_SOLICITUD(38),
        LISTADO_RECARGAS_SOLICITUD(39),
        RECARGAS_DETALLE(40),

        OPERACIONES_LISTADO(41),

        EMPADRONADOS(42)
        ;

        private int id;

        LISTADO_TIPOS(int id) {
            this.id = id;
        }

        public int getNum() {
            return id;
        }

    }

    /**
     * Dame el enum que quiero by Id
     * @param id un elefante
     * @return
     */
    public LISTADO_TIPOS getListadoById(int id)
    {
        LISTADO_TIPOS r = null;
        for(LISTADO_TIPOS l : LISTADO_TIPOS.values())
        {
            if(l.getNum() == id)
            {
                r = l;
                break;
            }
        }
        return r;
    }
}
