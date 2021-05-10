/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;

import com.wdreams.model.dao.Dao;
import com.wdreams.utils.*;
import com.wdreams.utils.padron.ClientePadronGuadalajara;
import com.wdreams.utils.Enviable;
import org.apache.log4j.Logger;
import org.hibernate.annotations.*;

/**
 *
 * @author victor
 */
@JsonIgnoreProperties(value = { "estado", "tarjetas", "tipologias", "centrosAsociados", "historialesCiudadano", "incidencias", "usuarioCreador", "user", "tarjetasOrdenadas", "comu", "tipoTutor", "tipoDocumentoTutor", "authorities", "tipoDocumento" })
@Entity
@Table(name = "Entidades", indexes = {@javax.persistence.Index(name = "idEntidadIndex", columnList = "idEntidad")})
@PrimaryKeyJoinColumn(name = "id")
public class Entidad extends User implements Serializable, Enviable {

    public static class TIPO_SOLICITANTE {

        public static int CIUDADANO = 1;
        public static int EMPRESA = 2;

        public static boolean existeTipoEntidad(int tipoEntidad) {
            return tipoEntidad == CIUDADANO || tipoEntidad == EMPRESA;
        }
    }

    @Column(name="tokenTemporal")
    private String tokenTemporal;

    @Column(name="idFCM")
    private String idFCM;

    @Column(name="empresa")
    private String empresa;

    @Column(name = "idEntidad", length = 9)
    private String idEntidad;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "tipoEntidad")
    private int tipoEntidad;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTipoDocumento", nullable = false, referencedColumnName = "id")
    private TipoDocumento tipoDocumento;

    @Column(name = "documento", nullable = true, length = 9)
    private String documento;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idEstadoCiudadano", nullable = false, referencedColumnName = "id")
    private EstadoCiudadano estado;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
    @Column(name = "fechaNacimiento", nullable = true)
    private Date fechaNacimiento;
    //@Cascade(CascadeType.SAVE_UPDATE)

    @JsonIgnore
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER)
    private Set<Tarjeta> tarjetas;

    @JsonIgnore
    @OneToMany(mappedBy = "ciudadano", fetch = FetchType.LAZY)
    private List<TipologiaCiudadano> tipologias;

    @JsonIgnore
    @OneToMany(mappedBy = "ciudadano", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<AsociacionCentro> centrosAsociados;

    @Column(name = "movil", length = 11)
    private String movil;

    @Column(name = "mail", length = 50)
    private String mail;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;


    public String getDirCp() {
        return dirCp;
    }

    public void setDirCp(String dirCp) {
        this.dirCp = dirCp;
    }

    public String getDirLocalidad() {
        return dirLocalidad;
    }

    public void setDirLocalidad(String dirLocalidad) {
        this.dirLocalidad = dirLocalidad;
    }

    @Column(name = "dirCp")
    private String dirCp;

    @Column(name = "dirLocalidad")
    private String dirLocalidad;


    @JsonIgnore
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy(value = "fecha")
    private Set<HistorialCiudadano> historialesCiudadano;

    @Column(name = "prueba")
    private Boolean prueba = Boolean.FALSE;

    @Column(name = "anonimo")
    private Boolean anonimo = Boolean.FALSE;

    @Column(name = "conFoto")
    private Boolean conFoto;

    @JsonIgnore
    @ManyToMany(cascade = javax.persistence.CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "incidenciasCiudadanos",
            joinColumns = {
                @JoinColumn(name = "idEntidad")},
            inverseJoinColumns = {
                @JoinColumn(name = "idIncidencia")})
    @Fetch(FetchMode.SELECT)
    private List<Incidencia> incidencias;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion", nullable = false)
    private Date fechaCreacion;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idUsuarioCreador", nullable = true, referencedColumnName = "id")
    private User usuarioCreador;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idPuestoImpresion", nullable = true, referencedColumnName = "id")
    private PuestoImpresion puestoImpresion;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idComu", nullable = true, referencedColumnName = "id")
    private Comu comu;

    @Column(name = "centroSalud")
    private Boolean centroSalud = Boolean.FALSE;

    @Column(name = "nia")
    private String nia;

    @Column(name = "sexo")
    private Boolean sexo;

    /**
     * TUTOR *
     */
    @Column(name = "tutorEmail")
    private String tutorEmail;
    @Column(name = "tutorMovil")
    private String tutorMovil;
    @Column(name = "tutorNombre")
    private String tutorNombre;
    @Column(name = "tutorApellido")
    private String tutorApellido;
    @Column(name = "tutorFechaNacimiento")
    private Date tutorFechaNacimiento;
    @Column(name = "tutorDocumento")
    private String tutorDocumento;
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTipoTutor", referencedColumnName = "id")
    private TipoTutor tipoTutor;
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "idTipoDocumentoTutor", referencedColumnName = "id")
    private TipoDocumento tipoDocumentoTutor;
    /**
     * FIN TUTOR *
     */

    /**
     * SALDO TARJETA VIRTUAL INICIO
     */
    public BigDecimal getSaldoVirtual() {
        return (saldoVirtual == null? BigDecimal.ZERO:saldoVirtual);
    }

    public void setSaldoVirtual(BigDecimal saldoVirtual) {
        this.saldoVirtual = (saldoVirtual == null? BigDecimal.ZERO:saldoVirtual);
    }
    @Column(name = "saldoVirtual", nullable = true)
    private BigDecimal saldoVirtual = BigDecimal.ZERO;
    /**
     * FIN SALDO TARJETA VIRTUAL
     */

    @Transient
    private String pinSinCodificar;

    public String getPinSinCodificar() {
        return pinSinCodificar;
    }

    public void setPinSinCodificar(String pinSinCodificar) {
        this.pinSinCodificar = pinSinCodificar;
    }

    private static Logger loggerBack = Logger.getLogger("ciudadanaErrorBack");

    public Entidad() {
    }

    public Boolean getPrueba() {
        return prueba;
    }

    @JsonIgnore
    public List<Tarjeta> getTarjetaEstado(EstadoTarjeta estadoTarjeta) {
        List<Tarjeta> tarjetasActivas = new ArrayList<Tarjeta>();
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getEstado().equals(estadoTarjeta)) {
                tarjetasActivas.add(tarjeta);
            }
        }
        return tarjetasActivas;
    }

    @JsonIgnore
    public Tarjeta getTarjetaEstadoGeneral(int estadoGeneral) {
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getEstado().getEstadoGeneral() == estadoGeneral) {
                return tarjeta;
            }
        }
        return null;
    }

    public String getTokenTemporal() {
        return tokenTemporal;
    }

    public void setTokenTemporal(String tokenTemporal) {
        this.tokenTemporal = tokenTemporal;
    }

    public String getIdFCM() {
        return idFCM;
    }

    public void setIdFCM(String idFCM) {
        this.idFCM = idFCM;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Boolean getConFoto() {
        return conFoto;
    }

    public void setConFoto(Boolean conFoto) {
        this.conFoto = conFoto;
    }

    public void setPrueba(Boolean prueba) {
        this.prueba = prueba;
    }

    public Boolean getAnonimo() {
        return anonimo;
    }

    public void setAnonimo(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public String getIdCiudadano() {
        return idEntidad;
    }

    public void setIdCiudadano(String idCiudadano) {
        this.idEntidad = idCiudadano;
        this.setUsername(idCiudadano);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public EstadoCiudadano getEstado() {
        return estado;
    }

    public void setEstado(EstadoCiudadano estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public String getNia() {
        return nia;
    }

    public void setNia(String nia) {
        this.nia = nia;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getTutorMovil() {
        return tutorMovil;
    }

    public void setTutorMovil(String tutorMovil) {
        this.tutorMovil = tutorMovil;
    }

    public String getTutorNombre() {
        return tutorNombre;
    }

    public void setTutorNombre(String tutorNombre) {
        this.tutorNombre = tutorNombre;
    }

    public String getTutorApellido() {
        return tutorApellido;
    }

    public void setTutorApellido(String tutorApellido) {
        this.tutorApellido = tutorApellido;
    }

    public TipoTutor getTipoTutor() {
        return tipoTutor;
    }

    public void setTipoTutor(TipoTutor tipoTutor) {
        this.tipoTutor = tipoTutor;
    }

    public TipoDocumento getTipoDocumentoTutor() {
        return tipoDocumentoTutor;
    }

    public void setTipoDocumentoTutor(TipoDocumento tipoDocumentoTutor) {
        this.tipoDocumentoTutor = tipoDocumentoTutor;
    }

    public Date getTutorFechaNacimiento() {
        return tutorFechaNacimiento;
    }

    public void setTutorFechaNacimiento(Date tutorFechaNacimiento) {
        this.tutorFechaNacimiento = tutorFechaNacimiento;
    }

    public String getTutorDocumento() {
        return tutorDocumento;
    }

    public void setTutorDocumento(String tutorDocumento) {
        this.tutorDocumento = tutorDocumento;
    }

    @JsonIgnore
    public List<Tarjeta> getTarjetasOrdenadas() {
        Collection<Tarjeta> unsorted = new ArrayList<Tarjeta>();
        for (Tarjeta tarjeta : tarjetas) {
//            if (tarjeta.getUid() != null && !tarjeta.getUid().equals("") && !(tarjeta instanceof TarjetaVirtual)) {
//                unsorted.add(tarjeta);
//            }
            unsorted.add(tarjeta);
        }
        //  List<Tarjeta> sorted = asSortedList(unsorted);
        return asSortedList(unsorted);
    }


    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        Collections.sort(list);
        return list;
    }

    public void setTarjetas(Set<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @JsonIgnore
    public List<TipologiaCiudadano> getTipologias() {
        return tipologias;
    }

    public void setTipologias(List<TipologiaCiudadano> tipologias) {
        this.tipologias = tipologias;
    }

    
    @JsonIgnore
    public List<TipoCiudadano> getTipos() {
        List<TipoCiudadano> tiposCiudadano = new ArrayList<TipoCiudadano>();
        for (TipologiaCiudadano tipologiaCiudadano : this.tipologias) {
            tiposCiudadano.add(tipologiaCiudadano.getTipoCiudadano());
        }

        return tiposCiudadano;
    }

    @JsonIgnore
    public List<TipoCiudadano> getTipos(Dao dao) {
        List<TipoCiudadano> tiposCiudadano = this.getTipos();

        /*for (TipologiaCiudadano tipologiaCiudadano : this.tipologias) {
         tiposCiudadano.add(tipologiaCiudadano.getTipoCiudadano());
         }*/
        return tiposCiudadano;
    }

    @JsonIgnore
    public Set<AsociacionCentro> getCentrosAsociados() {
        return centrosAsociados;
    }

    public void setCentrosAsociados(Set<AsociacionCentro> centrosAsociados) {
        this.centrosAsociados = centrosAsociados;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void asignarIdCiudadano(String prefijoCiudadano,String prefijoComercio) {
        this.setIdCiudadano((this.tipoEntidad == TIPO_SOLICITANTE.CIUDADANO ?  prefijoCiudadano : prefijoComercio) + String.format("%08X", 2377000l + this.getId()));
        if (this.documento == null || this.documento.equals("") || this.documento == null) {
            this.setDocumento(this.getIdCiudadano());
        }
    }

    @Override
    public String getEmailEnviable(Dao dao) {
        return this.mail;
    }

    @JsonIgnore
    @Override
    public String getText(Dao dao) throws FileNotFoundException, IOException {
        return new Plantillas(dao).getText(Plantillas.PLANTILLA_ALTA_CIUDADANO)
                .replace("[IDCIUDADANO]", this.idEntidad)
                .replace("[NOMBRE]", this.nombre)
                .replace("[APELLIDOS]", this.apellidos)
                .replace("[PASSWORD]", this.getPinSinCodificar());
    }

    @JsonIgnore
    @Override
    public String getSubject(Dao dao) {
        return "Se ha dado de alta en el sistema tarjeta ciudadana";
    }

    @JsonIgnore
    @Override
    public List<String> getAdjuntos(Dao dao) {
        return null;
    }

    
    
    public Set<HistorialCiudadano> getHistorialesCiudadano() {
        return historialesCiudadano;
    }

    public void setHistorialesCiudadano(Set<HistorialCiudadano> historialesCiudadano) {
        this.historialesCiudadano = historialesCiudadano;
    }

    @JsonIgnore
    public static Logger getLoggerBack() {
        return loggerBack;
    }

    public static void setLoggerBack(Logger loggerBack) {
        Entidad.loggerBack = loggerBack;
    }

    //COMPROBACION DEL PADRON PARA EL CIUDADANO
    public String comprobarPadron(Dao dao) {
        try {
            Integer resultado;

            //String valorPrueba = "1";// Linea para debug
            //if (valorPrueba == null || valorPrueba.equals("1")) { //Linea para debug
            if (dao.getAppConfigId(AppConfig.PADRON_ACTIVADO) == null || dao.getAppConfigId(AppConfig.PADRON_ACTIVADO).getValor().equals("1")) {
                if (tipoDocumento.getId() != TipoDocumento.SIN_DOCUMENTO) {
                    resultado = new ClientePadronGuadalajara().comprobarPadron(this.tipoDocumento.equivalenciaPadron(), documentoPadron(documento), null, fechaNacimiento);
                } else {
                    resultado = new ClientePadronGuadalajara().comprobarPadron(this.tipoDocumento.equivalenciaPadron(), null, nombre.toUpperCase(), fechaNacimiento);
                }
                if (resultado == ClientePadronGuadalajara.ERROR_VARIOS_CIUDADANOS) {
                    return PropertiesUtils.getPropiedadError("error.padron.varios");
                } else if (resultado == ClientePadronGuadalajara.ERROR) {
                    return PropertiesUtils.getPropiedadError("error.padron.datos");
                } else if (resultado == ClientePadronGuadalajara.ERROR_NO_EXISTE) {
                    return PropertiesUtils.getPropiedadError("error.padron.noExiste");
                }
            }
            return null;
        } catch (Exception e) {
            com.wdreams.utils.LogUtils.escribeLog(loggerBack, "Ciudadano.comprobarPadron", e);
            return PropertiesUtils.getPropiedadError("error.padron.conexion");
        }
    }

    private void guardarFoto(Dao dao) {
        try {
            ImageEditor imageEditor = (ImageEditor) SessionUtils.getAttribute("fotoCiudadano");
            File outputfile = new File(dao.getAppConfigId(AppConfig.CARPETA_FOTOS_CIUDADANO).getValor() + idEntidad + ".jpg");
            ImageIO.write(imageEditor.cropImage(), "jpg", outputfile);
        } catch (Exception ex) {
            LogUtils.escribeLog(loggerBack, "entidad.guardarFoto", ex);
        }
    }

    public String documentoPadron(String documento) {
        if (this.tipoDocumento.getId().equals(TipoDocumento.NIE)) {
            return documento.substring(0, 1) + "0" + documento.substring(1);
        }
        return documento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.idEntidad != null ? this.idEntidad.hashCode() : 0);
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
        final Entidad other = (Entidad) obj;
        if ((this.idEntidad == null) ? (other.idEntidad != null) : !this.idEntidad.equals(other.idEntidad)) {
            return false;
        }
        return true;
    }

    public TipoCiudadanoAmbito getDescuento(Dao dao, Ambito ambito) {
        TipoCiudadanoAmbito tipoDescuento = null;
        List<TipologiaCiudadano> tipologias = dao.getTipologiasHQL(idEntidad, null, null, new Date(), true, null);
        for (TipologiaCiudadano tipologiaCiudadano : tipologias) {
            for (TipoCiudadanoAmbito tipoCiudadanoAmbito : tipologiaCiudadano.getTipoCiudadano().getListaAmbitos()) {
                if (tipoCiudadanoAmbito.getTipoAmbito().equals(ambito) && (tipoDescuento == null || tipoCiudadanoAmbito.getPorcentajePago().compareTo(tipoDescuento.getPorcentajePago()) < 0)) {
                    tipoDescuento = tipoCiudadanoAmbito;
                }
            }
        }
        return tipoDescuento;
    }

    @JsonIgnore
    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonIgnore
    public User getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(User usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    @JsonIgnore
    public PuestoImpresion getPuestoImpresion() {
        return puestoImpresion;
    }

    public void setPuestoImpresion(PuestoImpresion puestoImpresion) {
        this.puestoImpresion = puestoImpresion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(int tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Comu getComu() {
        return comu;
    }

    public void setComu(Comu comu) {
        this.comu = comu;
    }

//    public Boolean getCentroSalud() {
//        return centroSalud;
//    }
//
//    public void setCentroSalud(Boolean centroSalud) {
//        this.centroSalud = centroSalud;
//    }
    //--------------------------------------
    public class EntidadDatosExport {

        public String getIdEntidad() {
            return Entidad.this.getIdCiudadano();
        }

        public String getNombre() {
            return Entidad.this.getNombre();
        }

        public String getApellidos() {
            return Entidad.this.getApellidos();
        }

        public String getTipoDocumento() {
            return Entidad.this.getTipoDocumento().getNombre();
        }

        public String getDocumento() {
            return Entidad.this.getDocumento();
        }

        public Date getFechaNacimiento() {
            return Entidad.this.getFechaNacimiento();
        }

        public String getMail() {
            return Entidad.this.getMail();
        }

        public String getMovil() {
            return Entidad.this.getMovil();
        }

        public String getEstado() {
            return Entidad.this.estado.getNombre();
        }

//        public String getComu() {
//            return Entidad.this.comu.getNombre();
//        }
        public String getTelefono() {
            return Entidad.this.telefono;
        }

        public String getDireccion() {
            return Entidad.this.direccion;
        }

        public String getPuestoImpresion() {
            return (Entidad.this.getPuestoImpresion() == null ? "" : Entidad.this.getPuestoImpresion().getNombre());
        }

        public String getUsuarioCreador() {
            return (Entidad.this.getUsuarioCreador() == null ? "" : Entidad.this.getUsuarioCreador().getUsername());
        }

        public String getFechaCreacion() {
            return DateUtils.ddMMyyyyHHmmss.format(Entidad.this.getFechaCreacion());
        }

        public String getAnonimo() {
            return (Entidad.this.getAnonimo() != null && Entidad.this.getAnonimo() == true ? "An�nimo" : "Ciudadano");
        }
    }

    public enum Sexo {
        FEMENINO("Femenino", 0),
        MASCULINO("Masculino", 1);

        private final String texto;
        private final int id;

        int getId() {
            return id;
        }

        public String getTexto() {
            return texto;
        }
        public int getCodigo() {
            return id;
        }

        Sexo(String texto, int id) {
            this.texto = texto;
            this.id = id;
        }
    }

}
