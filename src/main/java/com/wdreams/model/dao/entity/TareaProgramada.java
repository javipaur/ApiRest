/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wdreams.model.dao.entity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wdreams.model.dao.Dao;
import org.quartz.CronExpression;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "TareasProgramadas")
public class TareaProgramada {
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "idTareaProgramada", length = 11)
    protected String idTareaProgramada;
    
    @Column(name = "nombre", length = 50)
    protected String nombre;
    
    @Column(name = "descripcion")
    protected String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "id")
    protected Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idTipoProceso", nullable = false, referencedColumnName = "id")
    protected TipoProceso tipoProceso;
    
    @ManyToOne
    @JoinColumn(name = "idEstadoTareaProgramada", nullable = false, referencedColumnName = "id")
    protected EstadoTareaProgramada estado;
    
    @Column(name = "archivoProvider")
    protected Integer archivoProvider;

    @Column(name = "server", length = 75)
    protected String server;

    @Column(name = "username", length = 20)
    protected String username;

    @Column(name = "password", length = 20)
    protected String password;

    @Column(name = "port")
    protected Integer port;

    @Column(name = "path", length = 75)
    protected String path;
    
    @Column(name = "cronExpression", length = 40)
    protected String cronExpression;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaUltimaEjecucuion")
    protected Date fechaUltimaEjecucuion;

    public TareaProgramada() {
    }

    public TareaProgramada(String nombre,String descripcion, Usuario usuario, TipoProceso tipoProceso, EstadoTareaProgramada estado, Integer archivoProvider, String server, String username, String password, Integer port, String path, String cronExpression) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.tipoProceso = tipoProceso;
        this.estado = estado;
        this.archivoProvider = archivoProvider;
        this.server = server;
        this.username = username;
        this.password = password;
        this.port = port;
        this.path = path;
        this.cronExpression = cronExpression;
        this.fechaUltimaEjecucuion= new Date();
        this.descripcion=descripcion;
    }

    
    

    public Integer getArchivoProvider() {
       return this.archivoProvider;
    }


    public String getFileNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getFileFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getServer() {
        return this.server;
    }


    public String getPath() {
        return this.path;
    }

    public String getUser() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   public void asignarIdTareaProgramada() {
        this.idTareaProgramada = String.format("TP%09X", id);
    }

    public String getIdTareaProgramada() {
        return idTareaProgramada;
    }

    public void setIdTareaProgramada(String idTareaProgramada) {
        this.idTareaProgramada = idTareaProgramada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoProceso getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(TipoProceso tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }  

    public Date getFechaUltimaEjecucuion() {
        return fechaUltimaEjecucuion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaUltimaEjecucuion(Date fechaUltimaEjecucuion) {
        this.fechaUltimaEjecucuion = fechaUltimaEjecucuion;
    }
    
     public Date getFechaProximaEjecucuion() throws ParseException {
         return new CronExpression(cronExpression).getNextValidTimeAfter(fechaUltimaEjecucuion);
     }
     
     public boolean isExecutionTime() throws ParseException{
         return this.getFechaProximaEjecucuion().before(new Date()) || this.getFechaProximaEjecucuion().equals(new Date());
     }

    public EstadoTareaProgramada getEstado() {
        return estado;
    }

    public void setEstado(EstadoTareaProgramada estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final TareaProgramada other = (TareaProgramada) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

//     public class TareaProgramadaTipoProcesoFileProvider implements ExecutableProceso.ExecutableProcesoBDFile.TipoProcesoFileProvider{
//        private Dao dao;
//
//        public TareaProgramadaTipoProcesoFileProvider(Dao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        public TipoProceso getTipoProceso() {
//            return TareaProgramada.this.getTipoProceso();
//        }
//
//        @Override
//        public Usuario getUsuario() {
//            return TareaProgramada.this.getUsuario();
//        }
//
//        @Override
//        public Integer getArchivoProvider() {
//            return TareaProgramada.this.getArchivoProvider();
//        }
//
//        @Override
//        public String getFileNombre() {
//            return TareaProgramada.this.getFileNombre();
//        }
//
//        @Override
//        public String getFileFile() {
//            return TareaProgramada.this.getFileFile();
//        }
//
//        @Override
//        public String getServer() {
//            return TareaProgramada.this.getServer();
//        }
//
//        @Override
//        public Integer getPortInt() {
//            return TareaProgramada.this.getPort();
//        }
//
//        @Override
//        public String getPath() {
//           return TareaProgramada.this.getPath();
//        }
//
//        @Override
//        public String getUser() {
//            return TareaProgramada.this.getUser();
//        }
//
//        @Override
//        public String getPassword() {
//            return TareaProgramada.this.getPassword();
//        }
//
//     }

    public void setArchivoProvider(Integer archivoProvider) {
        this.archivoProvider = archivoProvider;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPath(String path) {
        this.path = path;
    }
     
//     public ExecutableProceso.ExecutableTareaProgramadaDecorator getExecutable(Dao dao, ProcesoService procesoService) throws IOException{
//        return  new ExecutableProceso.ExecutableTareaProgramadaDecorator(procesoService, ExecutableProcesoFactory.build(procesoService, this.new TareaProgramadaTipoProcesoFileProvider(dao)), this);
//     }
    
}
