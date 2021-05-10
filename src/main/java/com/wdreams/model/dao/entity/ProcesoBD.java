/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Procesos")
@DataTransferObject
public class ProcesoBD implements Proceso<SubProcesoBD> {

    private static Logger loggerBack = Logger.getLogger("ciudadanaErrorBack");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "idProceso", length = 11)
    protected String idProceso;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "id")
    protected Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaInicial")
    protected Date fechaInicial;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaFinal")
    protected Date fechaFinal;

    @ManyToOne
    @JoinColumn(name = "idEstadoProceso", nullable = false, referencedColumnName = "id")
    protected EstadoProceso estadoProceso;

    @ManyToOne
    @JoinColumn(name = "idTipoProceso", nullable = false, referencedColumnName = "id")
    protected TipoProceso tipoProceso;

    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "proceso", fetch = FetchType.EAGER, targetEntity = SubProcesoBD.class)
    @Fetch(FetchMode.SELECT)
    protected List<SubProcesoBD> subProcesos = new ArrayList<SubProcesoBD>();

    @Column(name = "rutaLogs", length = 200)
    protected String rutaLogs;

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
    
    @ManyToOne
    @JoinColumn(name = "idTareaProgramada", nullable = true, referencedColumnName = "id")
    protected TareaProgramada tareaProgramada;

    public ProcesoBD() {

    }

    public ProcesoBD(Usuario usuario, TipoProceso tipoProceso) {
        this.usuario = usuario;
        this.tipoProceso = tipoProceso;
    }

    @Override
    public String getIdProceso() {
        return this.idProceso;
    }

    @Override
    public Date getFechaInicial() {
        return this.fechaInicial;
    }

    @Override
    public Date getFechaFinal() {
        return this.fechaFinal;
    }

    @Override
    public Usuario getUsuario() {
        return this.usuario;
    }

    public static void setLoggerBack(Logger loggerBack) {
        ProcesoBD.loggerBack = loggerBack;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(EstadoProceso estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public TipoProceso getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(TipoProceso tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public void asignarIdPerfil() {
        this.idProceso = String.format("PR%09X", id);
    }

    public List<SubProcesoBD> getSubProcesos() {
        return subProcesos;
    }

    @Override
    public List<SubProcesoBD> getSubprocesos() {
        return subProcesos;
    }

    public void setSubProcesos(List<SubProcesoBD> subProcesos) {
        this.subProcesos = subProcesos;
    }

    @Override
    public String getTitle() {
        return this.tipoProceso.getTitulo();
    }

    @Override
    public String getEstado() {
        return this.getEstadoProceso().getNombre();
    }

    @Override
    public String getTerminal() {
        // BufferedReader br;
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(rutaLogs));
            return new String(encoded);
            /*br = new BufferedReader(new FileReader(rutaLogs));
             try {
             try {
             StringBuilder sb = new StringBuilder();
             String line;

             line = br.readLine();

             while (line != null) {
             sb.append(line);
             line = br.readLine();
             }
             return sb.toString().replace("\\n", " --linebreak-- ");
             } finally {
             br.close();
             }
             } catch (FileNotFoundException ex) {
             return "";
             }*/
        } catch (IOException ex) {
            return "";
        }
    }

    public String getRutaLogs() {
        return rutaLogs;
    }

    public void setRutaLogs(String rutaLogs) {
        this.rutaLogs = rutaLogs;
    }

    @Override
    public void appendTerminal(String string) {
        FileWriter fw;
        try {
            fw = new FileWriter(rutaLogs, true);

            fw.append(string);
            fw.append("\n");

            fw.close();
        } catch (IOException ex) {
            //Logger.getLogger(ProcesoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Integer getProgreso() {
        Integer progreso = 0;
        for (Subproceso subProceso : subProcesos) {
            progreso += subProceso.getPorcentaje();
        }
        return progreso;
    }

    @Override
    public Integer getTotal() {
        return subProcesos.size()*100;
    }

    @Override
    public Integer getPorcentaje() {
        return (int) Math.round((getProgreso().doubleValue() / getTotal().doubleValue()) * 100.0);
    }

    @Override
    public boolean getRunning() {
        return this.estadoProceso.getRunning();
    }

    public class ProcesoBDDatosExport {

        public String getIdProceso() {
            return ProcesoBD.this.getIdProceso();
        }

        public String getTipoProceso() {
            return ProcesoBD.this.getTipoProceso().getTitulo();
        }

        public String getUsuario() {
            return ProcesoBD.this.getUsuario().getUsername();
        }

        public String getEstado() {
            return ProcesoBD.this.getEstadoProceso().getNombre();
        }

        public Date getFechaInicio() {
            return ProcesoBD.this.getFechaInicial();
        }

        public Date getFechaFin() {
            return ProcesoBD.this.getFechaFinal();
        }
    }

    public Integer getArchivoProvider() {
        return archivoProvider;
    }

    public void setArchivoProvider(Integer archivoProvider) {
        this.archivoProvider = archivoProvider;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TareaProgramada getTareaProgramada() {
        return tareaProgramada;
    }

    public void setTareaProgramada(TareaProgramada tareaProgramada) {
        this.tareaProgramada = tareaProgramada;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final ProcesoBD other = (ProcesoBD) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
