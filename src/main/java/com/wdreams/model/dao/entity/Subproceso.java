/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.io.Serializable;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;

/**
 *
 * @author victor
 */
@DataTransferObject(params = @Param(name = "exclude", value = "parent"))
public interface Subproceso<T extends Proceso> extends Serializable{

    public Integer getProgress();
    
    public Integer getPorcentaje();

    public Integer getTotal();

    public String getTitle();

    public String getSufix();

    public T getParent();

    @DataTransferObject
    public static class SubprocesoRandom implements Subproceso<Proceso> {
        
        protected Proceso parent;
        protected Integer process = 0;
        protected String titulo;

        public SubprocesoRandom(String titulo) {
            this.titulo = titulo;
        }
 
        @Override
        public Integer getProgress() {
            return process;
        }
        
        synchronized public void  setProgress(Integer process){
            this.process=process;
        }

        @Override
        public Integer getTotal() {
         return 100;
        }

        @Override
        public String getTitle() {
            return  titulo;
        }

        @Override
        public String getSufix() {
            return "%";
        }

        public void setParent(Proceso parent) {
            this.parent = parent;
        }           

      /*  @Override
        public void execute() {
            parent.appendTerminal("Iniciada ejecución de subproceso "+this.titulo+"</br>");
            try {
                while(process<100){
                    if(process%10==0){
                        parent.appendTerminal("Línea de control "+process+"%</br>");
                    }
                    process++;
                    Thread.sleep(300);
                }   
            } catch (InterruptedException ex) {
                Logger.getLogger(Subproceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            parent.appendTerminal("Iniciada ejecución de subproceso "+this.titulo+"</br>");
        }
*/
        @Override
        public Integer getPorcentaje() {
            return (int)Math.round((getProgress().doubleValue()/getTotal().doubleValue())*100.0);
        }

        @Override
        public Proceso getParent() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
