package com.wdreams.model.dao;


import com.wdreams.model.dao.entity.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface Dao {

        // -- ACCESO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Acceso anotarAcceso(Acceso acceso);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Acceso> getAccesosListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, List<Aviso> avisos, Boolean buscaAnulada, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAccesosNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, List<Aviso> avisos, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getAccesosAmbitoListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAccesosAmbitoNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getAccesosCentroListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAccesosCentroNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Boolean eqNumeroOperacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeAccesoAnulado(Integer idOperacion);

        // -- ACCION -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaAccion(Accion accion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Accion> getAccionesListado(TipoAccion idTipoAccion, TipoEntidad tipoEntidad, Date fechaInicial, Date fechaFinal, String username, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAccionesNumero(TipoAccion idTipoAccion, TipoEntidad tipoEntidad, Date fechaInicial, Date fechaFinal, String username);

        // -- ALERTAS -- //
//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Alerta> getAlertasListado(String idAlerta, String idTarjeta, Date fechaInicial, Date fechaFinal, String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido, String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido, Date fechaActualizacionInicial, Date fechaActualizacionFinal, Integer primerResultado);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public Object[] getAlertasNumeroImporte(String idAlerta, String idTarjeta, Date fechaInicial, Date fechaFinal, String idCompraSaldoInicialLeido, String idCompraSaldoFinalLeido, String idRecargaSaldoInicialLeido, String idRecargaSaldoFinalLeido, Date fechaActualizacionInicial, Date fechaActualizacionFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public Alerta getAlertaTarjetaPeriodo(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public Alerta getAlertaIdAlerta(String idAlerta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void deleteAlerta(Alerta alerta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaAlerta(Alerta alerta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaAlerta(Alerta alerta);

        // -- AMBITOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Ambito getAmbitoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Ambito getAmbitoIdAmbito(String idAmbito);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaAmbito(Ambito ambito);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaAmbito(Ambito ambito);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Ambito> getAmbitosUsoSFTP();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Ambito> getAmbitos();


        @Transactional(propagation = Propagation.REQUIRED)
        public List<Ambito> getAmbitosCentros();
        @Transactional(propagation = Propagation.REQUIRED)
        public List<Ambito> getAmbitosComercios();


        @Transactional(propagation = Propagation.REQUIRED)
        public List<Ambito> getAmbitosListado(String idAmbito, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAmbitosNumero(String idAmbito);

        // -- APPCONFIG -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public AppConfig getAppConfigId(Integer id);


        @Transactional(propagation = Propagation.REQUIRED)
        public AppConfig getAppConfig(AppConfig.ConfigRecargas config);


        @Transactional(propagation = Propagation.REQUIRED)
        public void setAppConfigId(AppConfig appConfig);

        // -- AVISO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Aviso getAvisoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Aviso getAvisoNumero(Integer numeroAviso);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Aviso> getAvisos();

        // -- CENTRO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaCentro(Centro centro);

        @Transactional(propagation = Propagation.REQUIRED)
        public void setCentroImagenes(Centro centro, List<String> ficherosImagenes);

        @Transactional(propagation = Propagation.REQUIRED)
        public String[] getCentroImagenes(Integer centroId);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaCentro(Centro centro);

        @Transactional(propagation = Propagation.REQUIRED)
        public Centro getCentroId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Centro> getCentros();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Centro> getCentrosListado(String idCentro, Ambito ambito, Integer primerResultado, Integer tipo);


        @Transactional(propagation = Propagation.REQUIRED)
        public List<Centro> getCentrosListado(
                String idCentro, Ambito ambito,
                String nombre, Integer categoriaId, Boolean mostrar,
                Integer primerResultado, Integer tipo);


        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCentrosNumero(String idCentro, Ambito ambito,
                                     String nombre, Integer categoriaId, Boolean mostrar,
                                     Integer tipo);


        @Transactional(propagation = Propagation.REQUIRED)
        public List<Centro> getCentrosListado(String idCentro, Ambito ambito, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCentrosNumero(String idCentro, Ambito ambito, Integer tipo);

        @Transactional(propagation = Propagation.REQUIRED)
        public Centro getCentroIdCentro(String idCentro);

        @Transactional(propagation = Propagation.REQUIRED)
        public CentroComercio getComercioIdComercio(String idComercio);

        @Transactional(propagation = Propagation.REQUIRED)
        public CentroComercio getComercioById(Integer idComercio);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<CentroComercio> getComercios();

        // -- CENTRO DE RECOGIDA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public CentroRecogida getCentroRecogidaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public CentroRecogida getCentroRecogidaIdCentroRecogida(String idCentroRecogida);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<CentroRecogida> getCentrosRecogida();

        // -- TOKEN RECORDAR PASS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public PasswordResetToken guardarPassword(PasswordResetToken resetToken);

        @Transactional(propagation = Propagation.REQUIRED)
        public PasswordResetToken getResetTokenByToken(String token);

        @Transactional(propagation = Propagation.REQUIRED)
        public void updatePassCiudadanoByUsername(String password, String username);

        @Transactional(propagation = Propagation.REQUIRED)
        public PasswordResetToken deleteToken(PasswordResetToken resetToken);

        // -- CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getEntidadCreacionFechaCantidad(Date fechaIni, Date fechaFin);

        @Transactional(propagation = Propagation.REQUIRED)
        public User getCiudadanoByEmailDniFechaNacimiento(String email, String documento, Date fechaNacimiento);

        @Transactional(propagation = Propagation.REQUIRED)
        public User getCiudadanoByEmailDni(String email, String documento);

        @Transactional(propagation = Propagation.REQUIRED)
        public Usuario getComercioByEmailUsername(String email, String documento);

        // -- CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getEntidadByEmail(String email);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getEntidadByDNI(String dni);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getEntidadIdEntidad(String idEntidad);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getEntidadById(int id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getCiudadanoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getCiudadanoDocumento(TipoDocumento tipoDocumento, String documento);

        public void altaCiudadano(Entidad ciudadano);

        public void modificaCiudadano(Entidad ciudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Entidad> getCiudadanosListadoComprobarPadron(Integer id, Integer numCiudadanos);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getUltimoCiudadano();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Entidad> getEntidadesListado(Integer tipoEntidad, String idCiudadano, String nombre, String apellidos, TipoDocumento tipoDocumento, String documento, EstadoCiudadano estado, String idIncidencia, Boolean anonimo, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getEntidadesNumero(Integer tipoEntidad, String idCiudadano, String nombre, String apellidos, TipoDocumento tipoDocumento, String documento, EstadoCiudadano estado, String idIncidencia, Boolean anonimo);

        // -- PADRON --//
        @Transactional(propagation = Propagation.REQUIRED)
        public List<padrondocumentos> getPadronesListado(TipoDocumento tipoDocumento, String documento, String nombre, String apellidos, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getPadronesNumero(TipoDocumento tipoDocumento, String documento, String nombre, String apellidos);


        @Transactional(propagation = Propagation.REQUIRED)
        public void altaEmpadronados(padrondocumentos padrones);

        // -- OPERACION -- //
        public List<Operacion> getOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);
        public BigDecimal getImportesTotalesOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);

        public BigDecimal getImportesTicketsTotalesOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);


        public Long getNumeroOperacionesTarjeta(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal, Integer tipoListado, String ComercioId);

        public List<Operacion> getOperacionesTarjetas(List<Tarjeta> tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);
        public BigDecimal getImportesTotalesOperacionesTarjetas(List<Tarjeta> tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);

        public BigDecimal getImportesTicketsTotalesOperacionesTarjetas(List<Tarjeta> tarjeta, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer tipoListado, String ComercioId);


        public Long getNumeroOperacionesTarjetas(List<Tarjeta> tarjeta, Date fechaInicial, Date fechaFinal, Integer tipoListado, String ComercioId);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getNumeroOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<Operacion> getOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImportesTotalesOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal);


        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImportesTotalesTicketsOperacionesVentasComercio(Integer comercioId, Date fechaInicial, Date fechaFinal);


//    @Transactional(propagation = Propagation.REQUIRED)
//    public Long getNumeroOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal);
//    @Transactional(propagation = Propagation.REQUIRED)
//    public List<Operacion> getOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getNumeroOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImportesTotalesOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImportesTotalesTicketsOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote);


        @Transactional(propagation = Propagation.REQUIRED)
        public List<Operacion> getOperacionesCobrosComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer estado, String lote);


        @Transactional(propagation = Propagation.REQUIRED)
        public LiquidacionSaldo getLiquidacionByLote(String idLote);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<Compra> getOperacionesComprasComercioPendientes(Integer comercioId);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Compra> listaOperacionesLote(String idLote);

        // -- COMPRA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Compra getComprasIdUso(String isUso);

        //Obtencion del ultimo saldo leido en compras
        @Transactional(propagation = Propagation.REQUIRED)
        public Compra getPrimeraCompra(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/);

        @Transactional(propagation = Propagation.REQUIRED)
        public Compra getUltimoSaldoCompraTarjeta(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/);

        // @Transactional(propagation = Propagation.REQUIRED)
        //  public Double getImporteComprasPosteriores(Tarjeta tarjeta, Operacion operacion/*, List<Aviso> avisos*/, Date fechaHasta);
        @Transactional(propagation = Propagation.REQUIRED)
        public Double getImporteComprasEntreFechas(Tarjeta tarjeta, Date fechaIni, Date fechaFin);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteTotalComprasEntreFechas(Date fechaIni, Date fechaFin);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteCompras();

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteComprasAmbito(Ambito ambito);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteRecargasAmbito(Ambito ambito);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteTotalRecargasEntreFechas(Date fechaIni, Date fechaFin);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Compra> getComprasListadoHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnuladas, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getComprasNumeroImporte(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarSinSaldoLeido, Boolean buscaAnulada, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getComprasAmbitoListadoHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getComprasAmbitoNumeroHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getComprasCentroListadoHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getComprasCentroNumeroHql(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Descuento descuento, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Compra anotarCompra(Compra compra);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaCompra(Compra compra);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public Boolean existeNumeroOperacionCompra(String numeroOperacion);

        /*@Transactional(propagation = Propagation.REQUIRED)
         public Boolean existeNumeroOperacionCompra(Centro centro, Dispositivo dispositivo, String numeroOperacion);*/
        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeCompraAnulada(Integer idOperacion);

        // -- DISPOSITIVO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaDispositivo(Dispositivo dispositivo);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaDispositivo(Dispositivo dispositivo);

        @Transactional(propagation = Propagation.REQUIRED)
        public Dispositivo getDispositivoInformacion(String idDispositivo, String numeroSerie);

        @Transactional(propagation = Propagation.REQUIRED)
        public Dispositivo getDispositivoNumernoSerie(String numeroSerie);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Dispositivo> getDispositivosListado(String idDispositivo, Ambito ambito, Centro centro, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getDispositivosNumero(String idDispositivo, Ambito ambito, Centro centro);

        @Transactional(propagation = Propagation.REQUIRED)
        public Dispositivo getDispositivoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Dispositivo getDispositivoIdDispositivo(String idDispositivo);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Dispositivo> getDispositivos();

        // -- ESTADOS CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoCiudadano getEstadoCiudadanoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoCiudadano> getEstadosCiudadano();

        // -- ESTADOS INCIDENCIA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoIncidencia> getEstadoIncidencia();

        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoIncidencia getEstadoIncidenciaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)// ES
        public List<TransicionEstadoIncidencia> getTransicionesEstadoIncidencia(EstadoIncidencia estadoIncidencia);

        // -- ESTADOS LIQUIDACION -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TransicionEstadoLiquidacion> getTransicionesEstadoLiquidacion(EstadoLiquidacion estadoLiquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoLiquidacion> getEstadosLiquidacion();

        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoLiquidacion getEstadoLiquidacionId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public EstadosLiquidacionSaldo getEstadoLiquidacionSaldoId(Integer id);


        // -- ESTADO TARJETA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoTarjeta getEstadoTarjetaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoTarjeta> getEstadosTarjeta();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoTarjeta> getEstadosTarjetaEstadoGeneral(Integer estadoGeneral);

        // -- FECHA LISTADOS -- //
//        @Transactional(propagation = Propagation.REQUIRED)
//        public void setFechaListado(Date fecha, String nombreListado);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaHistorialCiudadano(HistorialCiudadano historialCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaHistorialCiudadano(HistorialCiudadano historialCiudadano);

        // -- HISTORIAL CIUDADANOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getActivacionesCiudadanosFecha(Date fechaIni, Date fechaFin, int estadoCiudadano);

        // -- HISTORIAL INCIDENCIA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaHistorialIncidencia(HistorialIncidencia historialIncidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaHistorialIncidencia(HistorialIncidencia historialIncidencia);

        // -- HISTORIAL TARJETA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getActivacionesTarjetaFecha(Date fechaIni, Date fechaFin, int estadoTarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public HistorialTarjeta getEstadoTarjetaVisitanteActivada(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public HistorialTarjeta getEstadoTarjetaOperacion(Tarjeta tarjeta, Date fecha);

        public void altaHistorialTarjeta(HistorialTarjeta historialTarjeta);

        // -- HISTORIAL LIQUIDACIONES -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<HistorialLiquidacionAmbito> getHistorialLiquidacionAmbitoIdLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaHitorialLiquidacionAmbito(HistorialLiquidacionAmbito historialLiquidacionAmbito);

        // -- INCIDENCIA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<Incidencia> getIncidenciasListado(EstadoIncidencia estadoIncidencia, User usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Incidencia> getIncidenciasListadoPorFechaDescendente(EstadoIncidencia estadoIncidencia, Usuario usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getIncidenciasNumero(EstadoIncidencia estadoIncidencia, Usuario usuario, Usuario usuarioAsignado, TipoIncidencia tipoIncidencia, String prioridad, String idCiudadano, String idTarjeta, String idAlerta);

        @Transactional(propagation = Propagation.REQUIRED)
        public Incidencia getIncidenciaIdIncidencia(String idIncidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaIncidencia(Incidencia incidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaIncidencia(Incidencia incidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<SeguimientoIncidencia> getSeguimientosIncidencia(Incidencia incidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public void addSeguimientoIncidencia(SeguimientoIncidencia seguimiento);

        // -- INGRESOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeNumeroIngreso(String numeroIngreso, LiquidacionAmbito liquidacionAmbito, String entidad);

        @Transactional(propagation = Propagation.REQUIRED)
        public Ingreso getIngresoId(Integer id);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Ingreso> getListadoIngresos(List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getIngresosNumeroImporte(List<LiquidacionAmbito> liquidacionesAmbito, String numeroIngreso, Ambito ambito, String entidadFinanciera, Date fechaIngresoInicial, Date fechaIngresoFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaIngreso(Ingreso ingreso);

        @Transactional(propagation = Propagation.REQUIRED)
        public void deleteIngreso(Ingreso ingreso);

        // -- LIQUIDACIONES -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Liquidacion getLiquidacionIdLiquidacion(String idLiquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public LiquidacionAmbito getLiquidacionAmbitoId(Integer id);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<LiquidacionAmbito> getLiquidacionesListado(Integer tipoLiquidacion, String idLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal, Ambito ambito, EstadoLiquidacion estadoLiquidacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getLiquidacionesNumeroImporte(Integer tipoLiquidacion, String idLiquidacion, Date fechaLiquidacionInicial, Date fechaLiquidacionFinal, Ambito ambito, EstadoLiquidacion estadoLiquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaLiquidacion(Liquidacion liquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaLiquidacion(Liquidacion liquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaLiquidacionAmbito(LiquidacionAmbito liquidacionAmbito);

        // -- PERIODO VIGENCIA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public PeriodoVigencia getPeriodoVigenciaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<PeriodoVigencia> getPeriodosVigencia();

        // -- RECARGAS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga getRecargaIdUso(String isUso);

        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga getRecargaById(int Id);

        @Transactional(propagation = Propagation.REQUIRED)
        public PreRecargaSaldoVirtual getPreRecargaSaldoVirtualById(int Id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga getPrimeraRecarga(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/);

        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga getUltimoSaldoRecargaTarjeta(Tarjeta tarjeta, Date fecha/*, List<Aviso> avisos*/);

        //  @Transactional(propagation = Propagation.REQUIRED)
        //  public Double getImporteRecargasPosteriores(Tarjeta tarjeta, Operacion operacion/*, List<Aviso> avisos*/, Date fechaHasta);
        @Transactional(propagation = Propagation.REQUIRED)
        public Double getImporteRecargasEntreFechas(Tarjeta tarjeta, Date fechaIni, Date fechaFin);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteRecargas();

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Recarga> getRecargasListadoHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarPorDispositivo, Boolean buscarSaldoLeido, Boolean buscarSinSaldoLeido, Boolean buscaAnulada, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getRecargasNumeroImporte(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean buscarSinSaldoLeido, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getRecargasAmbitoListadoHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getRecargasAmbitoNumeroHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getRecargasCentroListadoHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getRecargasCentroNumeroHql(String idCiudadano, String idTarjeta, String uid, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, List<Aviso> avisos, Liquidacion liquidacion, Boolean buscarPorLiquidacion, Boolean eqNumeroOperacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getTarjetaEstadisticasRecargas(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga anotarRecarga(Recarga recarga);

        @Transactional(propagation = Propagation.REQUIRED)
        public Recarga updateRecarga(Recarga recarga);

        @Transactional(propagation = Propagation.REQUIRED)
        public PreRecargaSaldoVirtual anotarPreRecargaSaldoVirtual(PreRecargaSaldoVirtual recarga);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaRecarga(Recarga recarga);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaPreRecarga(PreRecargaSaldoVirtual preRecarga);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeNumeroOperacionRecarga(String numeroOperacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeRecargaAnulada(Integer idOperacion);

        // -- ROL -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public Rol getRolId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Rol> getRoles(Boolean asignable);

        // -- TARJETAS -- //
        //INI -- MOD001
        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getSaldoPrepago();

        @Transactional(propagation = Propagation.REQUIRED)
        public Tarjeta getTarjetaUid(String uidTarjeta);
        //FIN -- MOD001

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTarjeta(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaTarjeta(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaHistorialTarjeta(HistorialTarjeta historialTarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Tarjeta> getTarjetasListado(String idTarjeta, String uid, String tipoTarjeta, List<EstadoTarjeta> estadosTarjeta, String idCiudadano, String idIncidencia, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public void actualizarFechaUltimaModificacionTarjeta(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public void actualizarFechaUltimaModificacionTarjetas(Entidad ciudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void forzarActualizacionListaBlanca(Boolean forzar);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Tarjeta> getTarjetasSaldoNegativoListado(String idTarjeta, String uid, String tipoTarjeta, List<EstadoTarjeta> estadosTarjeta, String idCiudadano, String idIncidencia, BigDecimal saldo, Integer primerResultado);

        /* @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasNoGrabadasAnteriores(Tarjeta tarjeta, Date fecha);

         @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasListadoBlanco(Tarjeta tarjeta);

         @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasListadoNegro(Tarjeta tarjeta);

         @Transactional(propagation = Propagation.REQUIRED)
         public HistorialTarjeta getTarjetaNotificacionListadoBlanco(Tarjeta tarjeta);

         @Transactional(propagation = Propagation.REQUIRED)
         public HistorialTarjeta getTarjetaNotificacionListadoNegro(Tarjeta tarjeta);

         @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasListadoActivaciones();

         @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasListadoBloqueos();

         @Transactional(propagation = Propagation.REQUIRED)
         public List<HistorialTarjeta> getTarjetasListadoEnvios();*/
        @Transactional(propagation = Propagation.REQUIRED)
        public Date getFechaActivacionTarjeta(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public Date getFechaBloqueoTarjeta(Tarjeta tarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Tarjeta> getTarjetasEstado(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTarjetasNumero(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano, String idIncidencia);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTarjetasSaldoNegativoNumero(String idTarjeta, String uid, String tipoTarjeta, EstadoTarjeta estadoTarjeta, String idCiudadano, String idIncidencia, BigDecimal saldo);

        @Transactional(propagation = Propagation.REQUIRED)
        public Tarjeta getTarjetaIdTarjeta(String idTarjeta);

        @Transactional(propagation = Propagation.REQUIRED)
        public Tarjeta getTarjetaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Tarjeta buscarTarjeta(String uid, String idTarjeta, String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public Object[] getTarjetaEstadisticasCompras(Tarjeta tarjeta, Date fechaInicial, Date fechaFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Tarjeta> getTarjetasListadoControlFraude(Integer id, Integer numTarjetas);

        @Transactional(propagation = Propagation.REQUIRED)
        public TarjetaPrepago getUltimaTarjetaPrepago();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Tarjeta> getTarjetasImprimirWS(InformacionTarjetaStrategy strategy, CentroRecogida centroRecogida, String tipoTarjeta, Integer tipoEntidad, Integer prioridad, String idCiudadano, EstadoTarjeta estado, String idTarjeta, String UID, Integer numeroTarjetas);

        // -- TIPO ACCION -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public TipoAccion getTipoAccionId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoAccion> getTipoAccion();

        // -- TIPO ENTIDAD -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public TipoEntidad getTipoEntidadId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoEntidad> getTipoEntidades();

        // -- TIPOLOGIA CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaTipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTipologiaCiudadano(TipologiaCiudadano tipologiaCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTipologiasNumeroHQL(String idCiudadano, TipoAsignacion tipoAsignacion, TipoCiudadano tCiudadano, Date fechaOperacion, Boolean activa);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipologiaCiudadano> getTipologiasHQL(String idCiudadano, TipoAsignacion tipoAsignacion, TipoCiudadano tCiudadano, Date fechaOperacion, Boolean activa, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipologiaCiudadano> getTipologiasCiudadano(Entidad ciudadano);
        /*  @Transactional(propagation = Propagation.REQUIRED)
            public List<TipologiaCiudadano> getTipologiasCiudadano(String idCiudadano, Date fecha, Boolean activa, Integer desde);

            @Transactional(propagation = Propagation.REQUIRED)
            public Long getTipologiasCiudadanoNumero(String idCiudadano, Date fecha, Boolean activa);

            @Transactional(propagation = Propagation.REQUIRED)
            public List<TipologiaCiudadano> getTipologiasComprasOfflineCiudadano(Entidad ciudadano, Date fecha);*/
//    @Transactional(propagation = Propagation.REQUIRED)
//     public List<TipologiaCiudadano> getTipologiasActivasCiudadano(Entidad ciudadano);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<Integer> getIdTipologiasActivasCiudadano(Entidad ciudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipologiaCiudadano> getTipologiasActivasCiudadanoYDin(Entidad ciudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public TipologiaCiudadano getTipologiaCiudadanoId(Integer id);

        /*  // -- TIPOLOGIA CIUDADANO DOCUMENTO -- //
         @Transactional(propagation = Propagation.REQUIRED)
         public void altaTipologiaCiudadanoDocumento(TipologiaCiudadanoDocumento tipologiaCiudadanoDocumento);*/
        // -- TIPO ASIGNACION -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public TipoAsignacion getTiposAsignacionId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoAsignacion> getTiposAsignacion();

        // -- TIPO CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadano> getTiposCiudadano();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoRestriccion getTiposRestriccionId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoRestriccion> getTiposRestriccion();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadano> getTiposCiudadanoTipoAsignacion(TipoAsignacion tipoAsignacion, int tipoEntidad);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadano> getTiposCiudadanoDinamicos(Entidad entidad);

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoCiudadano getTipoCiudadanoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoCiudadano getTipoCiudadanoIdTipoCiudadano(String idTipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadano> getTiposCiudadanoListado(String idTipoCiudadano, String nombre, TipoAsignacion asignacion, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTiposCiudadanoNumero(String idTipoCiudadano, String nombre, TipoAsignacion asignacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTipoCiudadano(TipoCiudadano tipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaTipoCiudadano(TipoCiudadano tipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoAsignacion getTipoAsignacionId(Integer id);

        // -- TIPO CIUDADANO AMBITO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public TipoCiudadanoAmbito getTipoCiudadanoAmbitoTipoCiudadanoAmbito(TipoCiudadano tipoCiudadano, Ambito ambito);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadanoAmbito> getTiposCiudadanoAmbitoTipoCiudadano(TipoCiudadano tipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTipoCiudadanoAmbito(TipoCiudadanoAmbito tipoCiudadanoAmbito);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaTipoCiudadanoAmbito(TipoCiudadanoAmbito tipoCiudadanoAmbito);

        // -- TIPO CIUDADANO DOCUMENTOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public TipoCiudadanoDocumento getTipoCiudadanoDocumentoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadanoDocumento> getTiposCiudadanoDocumentoTipoCiudadano(TipoCiudadano tipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTipoCiudadanoDocumento(TipoCiudadanoDocumento tipoCiudadanoDocumento);

        @Transactional(propagation = Propagation.REQUIRED)
        public void borraTipoCiudadanoDocumento(TipoCiudadanoDocumento tipoCiudadanoDocumento);

        // -- TIPO CIUDADANO RESTRICCIONES -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoCiudadanoRestriccion> getTiposCiudadanoRestriccionTipoCiudadano(TipoCiudadano tipoCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaTipoCiudadanoRestricciones(TipoCiudadanoRestriccion tipoCiudadanoRestriccion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void borraTipoCiudadanoRestricciones(TipoCiudadanoRestriccion tipoCiudadanoRestriccion);

        // -- TIPO DOCUMENTO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoDocumento> getTiposDocumento();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoDocumento getTiposDocumentoId(Integer id);

        // -- TIPO INCIDENCIA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoIncidencia> getTipoIncidencia();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoIncidencia getTipoIncidenciaId(Integer id);

        // -- TIPO USUARIO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoUsuario> getTiposUsuario();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoUsuario getTiposUsuarioId(Integer id);

        // -- TIPO TUTOR -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoTutor> getTiposTutor();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoTutor getTiposTutorId(Integer id);

        // -- TRANSICION ESTADOS CIUDADANO -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TransicionEstadoCiudadano> getTransicionesEstadoCiudadano(EstadoCiudadano estadoCiudadano);

        // -- TRANSICION ESTADOS TARJETA -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public List<TransicionEstadoTarjeta> getTransicionesEstadoTarjeta(EstadoTarjeta estadoTarjeta);

        // -- USERS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaUser(User user);

        @Transactional(propagation = Propagation.REQUIRED)
        public User getUser(String username, String password);

        @Transactional(propagation = Propagation.REQUIRED)
        public User getUserUsername(String username);

        // -- USUARIOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaUsuario(Usuario usuario);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaUsuario(Usuario usuario);

        @Transactional(propagation = Propagation.REQUIRED)
        public Usuario getUsuarioDocumento(TipoDocumento tipoDocumento, String documento);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Usuario> getUsuariosListado(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Usuario> getUsuariosListadoPorIdDescendente(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getUsuariosNumero(String username, Perfil perfil, String nombre, String apellidos, String documento, TipoDocumento tipoDocumento);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Usuario> getUsuarios();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Usuario> getUsuariosAsignables();

        @Transactional(propagation = Propagation.REQUIRED)
        public Usuario getUsuarioId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Usuario getUsuarioIdUsuario(int idUsuario);

        //CENTRO IMPRESION
        @Transactional(propagation = Propagation.REQUIRED)
        public CentroImpresion getCentroImpresionIdCentroImpresion(String idCentroImpresion);

        //PUESTO IMPRESION
        @Transactional(propagation = Propagation.REQUIRED)
        public PuestoImpresion getPuestoImpresionIdPuestoImpresion(String idPuestoImpresion);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<PuestoImpresion> getPuestosImpresion();

        //COMU
        @Transactional(propagation = Propagation.REQUIRED)
        public Comu getComuId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Comu> getComus();

        // --- PERFILES ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaPerfil(Perfil perfil);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaPerfil(Perfil perfil);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Perfil> getPerfiles();

        @Transactional(propagation = Propagation.REQUIRED)
        public Perfil getPerfilId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Perfil getPerfilIdPerfil(String idPerfil);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Perfil> getPerfilesListado(String idPerfil, String nombre, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Integer primerResultado, Boolean requiereComercio);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getPerfilesNumero(String idPerfil, String nombre, Boolean requiereAmbito, Boolean requiereCentro, Boolean requierePuestoImpresion, Boolean requiereComercio);

        // --- PROCESO ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public ProcesoBD getProcesoIdProceso(String idProceso);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaUpdateProceso(ProcesoBD proceso);

        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoProceso getEstadoProcesoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoProceso> getEstadosProceso();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoProceso getTipoProcesoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoProceso> getTiposProceso();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<ProcesoBD> getProcesosListado(String idProceso, Usuario usuario, EstadoProceso estadoProceso, TipoProceso tipoProceso, TareaProgramada tareaProgramada, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getProcesosNumero(String idProceso, Usuario usuario, EstadoProceso estadoProceso, TipoProceso tipoProceso, TareaProgramada tareaProgramada);

        // --- TAREA PROGRAMADA ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaUpdateTareaProgramada(TareaProgramada tareaProgramada);

        @Transactional(propagation = Propagation.REQUIRED)
        public TareaProgramada getTareaProgramadaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public TareaProgramada getTareaProgramadaIdTareaProgramada(String idTareaProgramada);

        public List<TareaProgramada> getTareasProgramadas(String idTareaProgramada, String nombre, EstadoTareaProgramada estado, Usuario usuario, TipoProceso tipoProceso, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTareasProgramadasNumero(String idTareaProgramada, String nombre, EstadoTareaProgramada estado, Usuario usuario, TipoProceso tipoProceso);

        public EstadoTareaProgramada getEstadoTareaProgramadaId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoTareaProgramada> getEstadosTareaProgramada();

        // --- BONOS ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public List<UsoBono> getUsoBonoListado(String idCiudadano, String idTarjeta,
                                               String uid, String tipo,
                                               String idUso, Ambito ambito,
                                               Centro centro, Dispositivo dispositivo,
                                               Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                               String numeroOperacion, List<Aviso> avisos,
                                               Boolean buscaAnulada, Boolean eqNumeroOperacion,
                                               Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<UsoBono> getUsoBonoListadoForWS(String idCiudadano, String idTarjeta,
                                                    String uid, String tipo,
                                                    String idUso, Ambito ambito,
                                                    Centro centro, Dispositivo dispositivo,
                                                    Date fechaRealizacionInicial, Date fechaRealizacionFinal,
                                                    String numeroOperacion, List<Aviso> avisos,
                                                    Boolean buscaAnulada, Boolean eqNumeroOperacion,
                                                    Integer primerResultado, AsignacionBono asignacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Bono getBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Bono getBonoIdBono(String idBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Bono> getBonosListado(String idBono, String nombre, EstadoBono estadoBono, Ambito ambito, TipoBono tipoBono, ConceptoBono conceptoBono, Integer desde, boolean soloBonosDisponibles);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Bono> getBonosListado(Ambito ambito, Centro centro);


        @Transactional(propagation = Propagation.REQUIRED)
        public Long getBonosNumero(String idBono, String nombre, EstadoBono estadoBono, Ambito ambito, TipoBono tipoBono, ConceptoBono conceptoBono, boolean soloBonosDisponibles);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaBono(Bono bono);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaBono(Bono bono);

        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoBono getEstadoBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoBono> getEstadosBono();

        @Transactional(propagation = Propagation.REQUIRED)
        public TipoBono getTipoBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<TipoBono> getTiposBono();

        @Transactional(propagation = Propagation.REQUIRED)
        public ConceptoBono getConceptoBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<ConceptoBono> getConceptosBono();

        // --- ASIGNACION BONO ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public AsignacionBono getAsignacionBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public AsignacionBono getAsignacionBonoIdAsignacionBono(String idAsignacionBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<AsignacionBono> getAsignacionesBonoListado(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getAsignacionesBonosImporte(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteMensualBonificacionesComercio(String idComercio);
        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteBonificacionesComercio(String idComercio);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteMensualBonificacionesCiudadano(String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteBonificacionesCiudadano(String idCiudadano);


        @Transactional(propagation = Propagation.REQUIRED)
        public Long getAsignacionesBonosNumero(String idCiudadano, Bono bono, EstadoAsignacionBono estado, Usuario usuario, Date fechaAsignacionInicial, Date fechaAsignacionFinal);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaBonoAsignacion(AsignacionBono asignacionBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaBonoAsignacion(AsignacionBono asignacionBono);

        //Generico
        @Transactional(propagation = Propagation.REQUIRED)
        public Object getObjectId(Class clase, Integer id);

        // --- ESTADOS ASIGNACION BONO ---//
        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoAsignacionBono getEstadoAsignacionBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoAsignacionBono> getEstadosAsignacionBono();

        // -- USOS BONO -- //
//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<UsoBono> getUsosBonoListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, Bono bono, List<Aviso> avisos, Boolean buscaAnulada, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<UsoBono> getUsosBonoFromEntidad(String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getUsosBonoNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Dispositivo dispositivo, Date fechaRealizacionInicial, Date fechaRealizacionFinal, String numeroOperacion, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getUsosBonoAmbitoListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getUsosBonoAmbitoNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getUsosBonoCentroListado(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getUsosBonoCentroNumero(String idCiudadano, String idTarjeta, String uid, String tipo, String idUso, Ambito ambito, Centro centro, Date fechaRealizacionInicial, Date fechaRealizacionFinal, Bono bono, List<Aviso> avisos, Boolean eqNumeroOperacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaUsoBonoAsignacion(UsoBono usoBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaUsoBonoAsignacion(UsoBono usoBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeUsoBonoAnuladoId(Integer idUsoBono);

        @Transactional(propagation = Propagation.REQUIRED)
        public UsoBono getUsoBonoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getUsosBonosNumeroSimplificado(AsignacionBono asignacionBono, Date fechaIni, Date fechaFin);

        //Descuentos
        @Transactional(propagation = Propagation.REQUIRED)
        public EstadoDescuento getEstadoDescuentoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<EstadoDescuento> getEstadosDescuento();

        @Transactional(propagation = Propagation.REQUIRED)
        public Descuento getDescuentoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Descuento getDescuentoIdDescuento(String idDescuento);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaDescuento(Descuento descuento);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaDescuento(Descuento descuento);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getDescuentosNumero(String idDescuento, String nombre, EstadoDescuento estadoDescuento, Ambito ambito);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Descuento> getDescuentosListado(String idDescuento, String nombre, EstadoDescuento estadoDescuento, Ambito ambito, Integer desde);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean existeNumeroOperacionCompra(String numeroOperacion);


//        /**
//         *
//         * Devuelve SortedMap Key: fecha casteado como entero Value: {fecha,
//         * cantidad de operaciones, suma de importe con descuento, suma de importe
//         * sin descuento}
//         *
//         * @param fechaDesde
//         * @param fechaHasta
//         * @return TreeMap<int, Object[]>
//         */
//        @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.REPEATABLE_READ)
//        public Map<Integer, Object[]> getDatosGraficaOperacionesImportes(LocalDate fechaDesde, LocalDate fechaHasta, Ambito ambito, Centro centro);

//        @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.REPEATABLE_READ)
//        public Map<String, Integer> getDatosGraficaTarjetasEmitidas(LocalDate fechaDesde, LocalDate fechaHasta, Ambito ambito, Centro centro);

//        @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.REPEATABLE_READ)
//        public Map<String, java.math.BigDecimal> getDatosGraficaSaldosTarjetas(LocalDate fechaDesde, LocalDate fechaHasta, Ambito ambito, Centro centro);

//        @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.REPEATABLE_READ)
//        public Map<Integer, Object[]> getDatosGraficaTotalRecargas(LocalDate fechaDesde, LocalDate fechaHasta, Ambito ambito, Centro centro);

        // -- ADJUNTOS -- //
        @Transactional(propagation = Propagation.REQUIRED)
        public void altaAdjuntos(Adjuntos adjuntos);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaAdjunto(Adjuntos adjuntos);

        @Transactional(propagation = Propagation.REQUIRED)
        public void eliminarAdjunto(Adjuntos adjuntos);

        public List<Adjuntos> getAdjuntos(String idCiudadano, int id);

        public Adjuntos getAdjunto(int id);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List<Map> getComprasPagination(DataTableRequest dataTableInRQ, String numeroOperacion, String descripcion, String idCentro, Date fechaRealizacion);

//        @Transactional(propagation = Propagation.REQUIRED)
//        public List getTipologiasPagination(DataTableRequest dataTableInRQ);

        @Transactional(propagation = Propagation.REQUIRED)
        public Producto getProductoId(Integer id);

        @Transactional(propagation = Propagation.REQUIRED)
        public Producto getProductoIdProducto(String idProducto);

        @Transactional(propagation = Propagation.REQUIRED)
        public void altaProducto(Producto producto);

        @Transactional(propagation = Propagation.REQUIRED)
        public void modificaProducto(Producto producto);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Producto> getProductos(Centro centro);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getProductosNumero(String idProducto);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Producto> getProductosListado(String idProducto, Integer primerResultado);

        //    CIUDADANOS PREALTA APP
        public void altaCiudadanoPrealta(EntidadPrealta ciudadano);

        public EntidadPrealta getCiudadanoPrealtaDocumento(TipoDocumento tipoDocumento, String documento);

        public void modificaEntidadPrealta(EntidadPrealta entidad);

        public void updateEntidad(Entidad entidad);


        @Transactional(propagation = Propagation.REQUIRED)
        public void altaBonificacionTarjeta(BonificacionComercios bonificacionComercios);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Compra> getComprasComercioSinBonificar(Centro centro, Tarjeta tarjeta, Integer plazoTiempo, Date fechaInicio);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<BonificacionComercios> getBonificacionComercioByTarjetaAndComercio(Tarjeta tarjeta, CentroComercio comercio, Boolean finalizada);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<BonificacionComercios> getBonificacionesListado(Entidad entidad, Tarjeta tarjeta, CentroComercio comercio, Date fechaInicio, Boolean finalizada, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getBonificacionesNumero(Entidad entidad, Tarjeta tarjeta, CentroComercio comercio, Date fechaInicio);



        //    TRAMOSCOMPRA
        public List<tramosCompra> getTramosCompraByAmbitoId(int ambitoId);
        public void insertaTramosCompra(tramosCompra tramo);
        public void deleteTramosCompraByAmbitoId(int ambitoId);
        public void eliminarTramo(tramosCompra tramo);
        public void actualizarTramo(tramosCompra tramo);


        //      QR
        public void insertaQrPeticiones(qr_peticiones peticion);
        public void deleteQrPeticiones(qr_peticiones peticion);
        public void actualizarQrPeticiones(qr_peticiones peticion);
        public void insertaQrRespuestas(qr_respuestas res);
        public void deleteQrRespuestas(qr_respuestas res);
        public void actualizarQrRespuestas(qr_respuestas res);



        //      QR_Bono
        public void insertaQrBonoPeticiones(qrbono_peticiones peticion);
        public void deleteQrBonoPeticiones(qrbono_peticiones peticion);
        public void actualizarQrBonoPeticiones(qrbono_peticiones peticion);
        public void insertaQrBonoRespuestas(qrbono_respuestas res);
        public void deleteQrBonoRespuestas(qrbono_respuestas res);
        public void actualizarQrBonoRespuestas(qrbono_respuestas res);

        public String fnGetQrBono(String idAsignacion);

        /**
         * PUNTOS
         */
        public ResumenPuntos getResumenPuntosAmbito(Integer idAmbito);
        public ResumenPuntos getResumenPuntosCentro(Integer idCentro);
        public ResumenPuntos getResumenPuntosCiudadano(Integer idCiudadano);
        public ResumenPuntos getResumenPuntosCiudadano_Comercios(Integer idCiudadano);
        public ResumenPuntos getResumenPuntosCiudadano_Centros(Integer idCiudadano);


        public List getListadoPuntosByAmbito(Integer idAmbito, Integer primerResultado);
        public List getListadoPuntosByCentro(Integer idCentro, Integer primerResultado);
        public List<historialespuntos> getListadoPuntosCiudadanoComercios(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado);
        public List<historialespuntos> getListadoPuntosCiudadanoCentros(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado);

        public Long getTotalPuntosCiudadanoComercios(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado);
        public Long getTotalPuntosCiudadanoCentros(Integer idCiudadano, Date fechaInicio, Date fechaFin, Integer tipo, Integer primerResultado);


        public Boolean documentoExistsOnPadronDocumentos(String documento);

        @Transactional(propagation = Propagation.REQUIRED)
        public padrondocumentos getPadronDocumento(String documento);


        @Transactional
        public Boolean spAsignaBonoConvert(String idAsignacionBono);






        @Transactional
        public Boolean altaCmsCategoria(cmscategorias centro);
        @Transactional
        public Boolean modificaCmsCategoria(cmscategorias centro);
        @Transactional
        public cmscategorias getCmsCategoria(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscategorias> getCmsCategoriaListado(String nombre, Integer primerResultado, Integer tipo);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscategorias> getCmsEventosCategorias();
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscategorias> getCmsComerciosCategorias();
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscategorias> getCmsCentrosCategorias();
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCmsCategoriasNumero(String nombre, Integer tipo);



        @Transactional
        public Boolean altaCmsEvento(cmseventos evento) throws ParseException;
        @Transactional
        public Boolean modificaCmsEvento(cmseventos evento) throws ParseException;
        @Transactional
        public cmseventos getCmsEvento(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmseventos> getCmsEventosListado(String nombre, Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCmsEventosNumero(String nombre);


        /**
         * CARRUSEL
         */
        @Transactional
        public Boolean altaCmsCarrusel(cmscarrusel carrusel) throws ParseException;
        @Transactional
        public Boolean modificaCmsCarrusel(cmscarrusel carrusel) throws ParseException;
        @Transactional
        public cmscarrusel getCmsCarrusel(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscarrusel> getCmsCarruselListado(String nombre, Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscarrusel> getCmsCarruselListadoMostrar();
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCmsCarruselNumero(String nombre);

        /**
         * CONTENIDOS
         */
        @Transactional
        public Boolean altaCmsContenido(cmscontenidos contenido) throws ParseException;
        @Transactional
        public Boolean modificaCmsContenido(cmscontenidos contenido) throws ParseException;
        @Transactional
        public cmscontenidos getCmsContenido(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscontenidos> getCmsContenidoListado(String nombre, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmscontenidos> getCmsContenidoListadoBySeccion(Integer seccionId);

        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCmsContenidoNumero(String nombre);

        /**
         * SECCIONES
         */
        @Transactional
        public Boolean altaCmsSecciones(cmssecciones seccion) throws ParseException;
        @Transactional
        public Boolean modificaCmsSecciones(cmssecciones seccion) throws ParseException;
        @Transactional
        public cmssecciones getCmsSecciones(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmssecciones> getCmsSeccionesListado(String nombre, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmssecciones> getCmsSeccionesLinkablesListado(String nombre, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<cmssecciones> getCmsSeccionesListadoMostrar();
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getCmsSeccionesNumero(String nombre);





        /**
         * WS CMS
         */
        public List<CentroComercio> SelectAllCentrosAsociados();
        public List<cmscategorias> SelectAllCentrosAsociadosCategorias();
        public List<CentroAyuntamiento> SelectAllCentrosMunicipales();
        public List<cmscategorias> SelectAllCentrosMunicipalesCategorias();
        public List<cmseventos> SelectAllEventos();
        public List<cmscategorias> SelectAllEventosCategorias();

        public ImmutablePair<Integer, List<CentroComercio>> PagedCentrosAsociados(modelo modelo);
        public ImmutablePair<Integer, List<cmscategorias>> PagedCentrosAsociadosCategorias(modelo modelo);
        public ImmutablePair<Integer, List<CentroAyuntamiento>> PagedCentrosMunicipales(modelo modelo);
        public ImmutablePair<Integer, List<cmscategorias>> PagedCentrosMunicipalesCategorias(modelo modelo);
        public ImmutablePair<Integer, List<cmseventos>> PagedEventos(modelo modelo);
        public ImmutablePair<Integer, List<cmscategorias>> PagedEventosCategorias(modelo modelo);


        public Map<CentroComercio, List<beneficios>> CentrosAsociadosConBeneficios(modeloBeneficio modelo);

//    public Integer TotalPagedCentrosAsociados(modelo modelo);
//    public Integer TotalPagedCentrosAsociadosCategorias(modelo modelo);
//    public Integer TotalPagedCentrosMunicipales(modelo modelo);
//    public Integer TotalPagedCentrosMunicipalesCategorias(modelo modelo);
//    public Integer TotalPagedEventos(modelo modelo);
//    public Integer TotalPagedEventosCategorias(modelo modelo);




        /**
         * BENEFICIOS
         */
        @Transactional
        public Boolean altaBeneficios(beneficios beneficios) throws ParseException;
        @Transactional
        public Boolean modificaBeneficios(beneficios carrusel) throws ParseException;
        @Transactional
        public beneficios getBeneficios(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<beneficios> getBeneficiosListado(String nombre, Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<beneficios> getBeneficiosListadoMostrar();
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getBeneficiosNumero(String nombre);



        /**
         * BENEFICIOSTIPOS
         */
        @Transactional
        public Boolean altaBeneficiosTipos(beneficiostipos carrusel) throws ParseException;
        @Transactional
        public Boolean modificaBeneficiosTipos(beneficiostipos carrusel) throws ParseException;
        @Transactional
        public beneficiostipos getBeneficiosTipos(Integer cmsId);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<beneficiostipos> getBeneficiosTiposListado(String nombre, Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public List<beneficiostipos> getBeneficiosTiposListadoMostrar();
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getBeneficiosTiposNumero(String nombre);


        /**
         * AUXILIARES BENEFICIOS
         */
        @Transactional(propagation = Propagation.REQUIRED)
        public List<beneficiostipos> getBeneficiosTiposListado();

        @Transactional(propagation = Propagation.REQUIRED)
        public List<CentroComercio> getCentrosComercio();




        @Transactional(propagation = Propagation.REQUIRED)
        public List<seccionesappmovil> getAllSeccionesAppMovil();

        @Transactional(propagation = Propagation.REQUIRED)
        public seccionesappmovil getSeccionAppMovil(Integer id);


        @Transactional(propagation = Propagation.REQUIRED)
        public List<RecargaSolicitudListItem> getEntidadesForBySolicitudId(
                String[] idsExcluidos,
                Integer solicitudId);


        @Transactional(propagation = Propagation.REQUIRED)
        public List<RecargaSolicitudListItem> getEntidadesForRecarga(
                String[] idsExcluidos,
                Integer tipologia,
                String idEntidad,
                BigDecimal saldoMaximo,
                Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTotalEntidadesForRecarga(
                Integer tipologia,
                String idEntidad,
                BigDecimal saldoMaximo);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<solicitudsaldovirtual> getSolicitudesPendientes(
                Integer primerResultado,
                String descripcion,
                Boolean validada,
                Date fechaInicial,
                Date fechaFinal);
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTotalSolicitudesPendientes(
                String descripcion,
                Boolean validada,
                Date fechaInicial,
                Date fechaFinal
        );


        @Transactional(propagation = Propagation.REQUIRED)
        public solicitudsaldovirtual getSolicitudSaldoVirtual(Integer solicitudId);


        @Transactional(propagation = Propagation.REQUIRED)
        public solicitudsaldovirtual saveSolicitudSaldoVirtual(solicitudsaldovirtual solicitud);

        @Transactional(propagation = Propagation.REQUIRED)
        public solicitudsaldovirtual updateSolicitudSaldoVirtual(solicitudsaldovirtual solicitud);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<Integer> saveRangeSolicitudsaldovirtual_ciudadano(List<solicitudsaldovirtual_ciudadano> listado);

        @Transactional(propagation = Propagation.REQUIRED)
        public historialSaldoVirtual saveHistorialSaldoVirtual(historialSaldoVirtual historial);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<HistorialCiudadano> altaHistorialCiudadano(RecargaSolicitudListItem[] recargaSolicitud);


        @Transactional(propagation = Propagation.REQUIRED)
        public boolean deleteRelaciones(List<Integer> relacionesBorrar, Integer solicitudId);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean validateRelaciones(List<Integer> relacionesBorrar, Integer solicitudId);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean altaHistorialCiudadanoRemovedSaldo(List<Integer> CiudadanosIds, Integer idSolicitud);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean altaHistorialCiudadanoAddedSaldo(List<Integer> CiudadanosIds, Integer idSolicitud);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean altaHistorialCiudadanoSolicitudRealizada(List<Integer> CiudadanosIds, Integer idSolicitud);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<RecargaSolicitudListItem> getEntidadesFromSolicitud(
                Integer tipologia,
                String idEntidad,
                BigDecimal saldoMaximo,
                String[] idsExcluidos,
                Integer solicitudId,
                Integer primerResultado);
        @Transactional(propagation = Propagation.REQUIRED)
        public Long getTotalEntidadesFromSolicitud(
                Integer tipologia,
                String idEntidad,
                BigDecimal saldoMaximo,
                Integer solicitudId);




        @Transactional(propagation = Propagation.REQUIRED)
        public Venta saveVenta(Venta venta);

        @Transactional(propagation = Propagation.REQUIRED)
        public Venta updateVenta(Venta venta);

        @Transactional(propagation = Propagation.REQUIRED)
        public Venta getVentaByLocalizador(String localizador);

        @Transactional(propagation = Propagation.REQUIRED)
        public List<VentasLineas> getVentasLineas(Venta venta);

        @Transactional(propagation = Propagation.REQUIRED)
        public VentasLineas saveVentasLineas(VentasLineas ventaLinea);

        @Transactional(propagation = Propagation.REQUIRED)
        public VentasLineas updateVentasLineas(VentasLineas ventaLinea);

        @Transactional(propagation = Propagation.REQUIRED)
        public HistorialVentas saveHistorialHistorialVentas(HistorialVentas historial);

        @Transactional(propagation = Propagation.REQUIRED)
        public tokens_pagos saveTokenCobro(tokens_pagos token);

        @Transactional(propagation = Propagation.REQUIRED)
        public tokens_pagos updateTokenCobro(tokens_pagos token);

        @Transactional(propagation = Propagation.REQUIRED)
        public tokens_pagos getTokenCobroByToken(String token);


        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean updateIdFcm(String idCiudadano, String idFcm);

        @Transactional(propagation = Propagation.REQUIRED)
        public Entidad getEntidadTokenTemporal(String token);


        @Transactional(propagation = Propagation.REQUIRED)
        public RedsysNotification saveRedsysNotification(RedsysNotification not);

        @Transactional(propagation = Propagation.REQUIRED)
        public RedsysError saveRedsysError(RedsysError error);

        @Transactional(propagation = Propagation.REQUIRED)
        public LiquidacionSaldo saveLiquidacionSaldo(LiquidacionSaldo liquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public LiquidacionSaldo updateLiquidacionSaldo(LiquidacionSaldo liquidacion);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean liquidaSaldo(List<Compra> compras, LiquidacionSaldo liquidacion);




        public Long getNumeroLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote);
        public List<LiquidacionSaldo> getLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado, Integer estado, String lote);

        public BigDecimal getImportesTotalesLiquidacionesSaldoComercio(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer estado, String lote);


        public List<LiquidacionAgrupado> getLiquidacionesAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado);

        public List<BonosAgrupadoVM> getBonosAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado);

        public List<CobrosBonosAgrupadoVM> getBonosCobrosAgrupado(Integer comercioId, Date fechaInicial, Date fechaFinal, Integer primerResultado);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteComprasCiudadano40(String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getImporteBonificacionesTotal(String idCiudadano);

        @Transactional(propagation = Propagation.REQUIRED)
        public BigDecimal getTotalRecargas(int fechaInicioCampana, int fechaFinCampana);

        /*
         * Métodos para el informe estadístico por correo electrónico
         */
        @Transactional(propagation = Propagation.REQUIRED)
        public int getAltasTotales(Date fayer, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public float getComprasTotales(Date fayer, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public int getComprasTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public float getImporteComprasTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public int getAltasDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public float getBonificacionesTotales(Date hasta, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public float getImporteBonificacionesTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut);
        @Transactional(propagation = Propagation.REQUIRED)
        public int getBonificacionesTotalesDia(Date desde, Date hasta, Boolean ciudadanosPruebaFilterOut);

        @Transactional(propagation = Propagation.REQUIRED)
        public Boolean toggleUsuarioState(int u, Boolean state);
}


