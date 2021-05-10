/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;

/**
 *
 * @author victor
 */
@DataTransferObject
public interface Proceso<T extends Subproceso> extends Serializable {

    public String getIdProceso();

    public List<T> getSubprocesos();

    public String getTitle();

    public String getEstado();

    public String getTerminal();

    public void appendTerminal(String string);

    public Integer getProgreso();

    public Integer getTotal();

    public Integer getPorcentaje();

    public Date getFechaInicial();

    public Date getFechaFinal();

    public Usuario getUsuario();
    
    boolean getRunning();

   /* @DataTransferObject
    public static class ProcesoWrapperBD implements Proceso<SubprocesoRandom> {

        protected ProcesoBD procesoBD;
        protected DAO dao;

        @Override
        public String getIdProceso() {
            return this.procesoBD.getIdProceso();
        }

        @Override
        public List<SubprocesoRandom> getSubprocesos() {
            return this.procesoBD.getSubProcesos();
        }

        @Override
        public String getTitle() {
            return this.procesoBD.getTipoProceso().getTitulo();
        }

        @Override
        public String getEstado() {
            return this.procesoBD.getEstadoProceso().getNombre();
        }

        @Override
        public String getTerminal() {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(dao.getAppConfigId(AppConfig.CARPETA_PROCESOS).getValor() + this.getIdProceso() + "_log.log"));
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        String line;

                        line = br.readLine();

                        while (line != null) {
                            sb.append(line);
                            sb.append("<\\br>");
                            line = br.readLine();
                        }
                        return sb.toString();
                    } finally {
                        br.close();
                    }
                } catch (FileNotFoundException ex) {
                    return "";
                }
            } catch (IOException ex) {
                return "";
            }
        }

        @Override
        public void appendTerminal(String string) {
            FileWriter fw;
            try {
                fw = new FileWriter(dao.getAppConfigId(AppConfig.CARPETA_PROCESOS).getValor() + this.getIdProceso() + "_log.log");

                for (int i = 0; i < 10; i++) {
                    fw.write(string);
                }
                fw.close();
            } catch (IOException ex) {
                //Logger.getLogger(ProcesoBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public Integer getProgreso() {
            Integer progreso = 0;
            for (Subproceso subProceso : getSubprocesos()) {
                progreso += subProceso.getProgress();
            }
            return progreso;
        }

        @Override
        public Integer getTotal() {
            Integer total = 0;
            for (Subproceso subProceso : getSubprocesos()) {
                total += subProceso.getTotal();
            }
            return total;
        }

        @Override
        public Integer getPorcentaje() {
            return (int) Math.round((getProgreso().doubleValue() / getTotal().doubleValue()) * 100.0);
        }

        @Override
        public void cancel() {
            this.procesoBD.setEstadoProceso(dao.getEstadoProcesoId(EstadoProceso.CANCELADO));
            //dao.altaUpdateProceso(this);
        }

        @Override
        public void execute() {
            try {
                this.procesoBD.setEstadoProceso(dao.getEstadoProcesoId(EstadoProceso.EJECUTANDO));
                this.procesoBD.setUsuario((Usuario) dao.getUserUsername(LogUtils.getUserName()));
                this.procesoBD.setFechaInicial(new Date());
                // saveUpdate();
                for (SubProceso subProceso : getSubprocesos()) {
                    subProceso.execute();
                    // this.saveUpdate();
                }
                this.procesoBD.setEstadoProceso(dao.getEstadoProcesoId(EstadoProceso.FINALIZADO));
            } catch (Exception ex) {
                LogUtils.escribeLog(loggerBack, "ProcesoBD.execute()", ex);
                this.estadoProceso = dao.getEstadoProcesoId(EstadoProceso.ERROR);
                this.appendTerminal(ExceptionUtils.getFullStackTrace(ex));
            } finally {
                this.fechaFinal = new Date();
                this.saveUpdate();
            }
        }

        @Override
        public Date getFechaInicial() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Date getFechaFinal() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Usuario getUsuario() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }*/

    /*@DataTransferObject
    public static class ProcesoRandom implements Proceso<SubprocesoRandom> {

        public enum Estado {

            EJECUTANDO(Boolean.TRUE, "Ejecutando..."), FINALIZADO(Boolean.FALSE, "Finalizado"), ERROR(Boolean.FALSE, "ERROR !!!"), CANCELADO(Boolean.FALSE, "Cancelado");
            private Boolean running;
            private String nombre;

            private Estado(Boolean running, String nombre) {
                this.running = running;
                this.nombre = nombre;
            }

            public String getNombre() {
                return nombre;
            }

        }

        protected String id;
        protected List<SubprocesoRandom> subProcesos;
        protected String titulo;
        protected StringBuffer stringBuffer = new StringBuffer();
        protected Usuario usuario;
        protected Date fechaInicial;
        protected Date fechaFinal;
        protected Estado estado;

        public ProcesoRandom(Usuario usuario, List<SubprocesoRandom> subProcesos, String titulo) {
            this.id = String.format("PR%07X", new Random().nextInt());
            this.subProcesos = subProcesos;
            this.titulo = titulo;
            for (SubprocesoRandom subprocesoRandom : this.subProcesos) {
                subprocesoRandom.setParent(this);
            }
            this.usuario = usuario;
            this.fechaInicial = new Date();
        }

        @Override
        public List<SubprocesoRandom> getSubprocesos() {
            return this.subProcesos;
        }

        @Override
        public String getIdProceso() {
            return this.id;
        }

        @Override
        public String getTitle() {
            return this.titulo;
        }*/

       /* @Override
        public void cancel() {
            this.estado = Estado.CANCELADO;
        }

        @Override
        public void execute() {
            this.estado = Estado.EJECUTANDO;
            stringBuffer.append("Iniciada ejecución de proceso " + this.id + "</br>");
            for (SubprocesoRandom subprocesoRandom : subProcesos) {;
                subprocesoRandom.execute();
            }
            stringBuffer.append("Finalziada ejecución de proceso " + this.id + "</br>");
            this.fechaFinal = new Date();
            this.estado = Estado.FINALIZADO;
        }*/

     /*   @Override
        public String getEstado() {
            return estado.nombre;
        }

        @Override
        public String getTerminal() {
            return stringBuffer.toString();
        }*/

      /*  @Override
        public void run() {
            execute();
        }

        @Override
        public Integer getProgreso() {
            Integer progreso = 0;
            for (Subproceso subProceso : subProcesos) {
                progreso += subProceso.getProgress();
            }
            return progreso;
        }

        @Override
        public Integer getTotal() {
            Integer total = 0;
            for (Subproceso subProceso : subProcesos) {
                total += subProceso.getTotal();
            }
            return total;
        }

        @Override
        public Integer getPorcentaje() {
            return (int) Math.round((getProgreso().doubleValue() / getTotal().doubleValue()) * 100.0);
        }

        @Override
        public Date getFechaInicial() {
            return this.fechaInicial;
        }

        @Override
        public Date getFechaFinal() {
            return this.fechaFinal;//To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Usuario getUsuario() {
            return this.usuario;
        }

        @Override
        public void appendTerminal(String string) {
            this.stringBuffer.append(string);
        }

    }*/

}
