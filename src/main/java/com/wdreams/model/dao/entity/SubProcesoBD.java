/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Subprocesos")
@DataTransferObject(params = @Param(name = "exclude", value = "parent"))
public class SubProcesoBD implements Serializable, Subproceso<Proceso> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "progreso")
    protected Integer progreso=0;

    @Column(name = "total")
    protected Integer total = 100;

    @Column(name = "sufijo", length = 10)
    protected String sufijo;

    @Column(name = "titulo", length = 50)
    protected String titulo;

    @ManyToOne(targetEntity = ProcesoBD.class)
    @JoinColumn(name = "idProceso", nullable = false, referencedColumnName = "id")
    protected ProcesoBD proceso;

    public SubProcesoBD() {
    }

    public SubProcesoBD(String sufijo, String titulo) {
        this.sufijo = sufijo;
        this.titulo = titulo;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String getTitle() {
        return titulo;
    }

    @Override
    public String getSufix() {
        return sufijo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public ProcesoBD getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoBD proceso) {
        this.proceso = proceso;
    }

   /* public static class SubProcesoBDRandom extends SubProcesoBD<ProcesoBDRandom> {

        public SubProcesoBDRandom() {
        }

        public SubProcesoBDRandom(String sufijo, String titulo) {
            super(sufijo, titulo);
        }

        @Override
        public void execute() {
            this.total=100;
            getParent().appendTerminal("Iniciada ejecución de subproceso " + this.titulo + "</br>");
            try {
                while (this.progreso < 100) {
                    if (this.progreso+1 % 10 == 0) {
                         getParent().appendTerminal("Línea de control " + this.progreso+1 + "%</br>");
                         
                    }
                    this.progreso++;
                    getParent().saveUpdate();
                    Thread.sleep(300);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Subproceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            getParent().appendTerminal("Finalizada ejecución de subproceso " + this.titulo + "</br>");
        }

    }*/

    @Override
    public Integer getProgress() {
       return progreso;
    }

    @Override
    public Integer getPorcentaje() {
        return (int)Math.round((getProgress().doubleValue()/getTotal().doubleValue())*100.0);
    }

    @Override
    public Proceso getParent() {
        return this.proceso;
    }

}
