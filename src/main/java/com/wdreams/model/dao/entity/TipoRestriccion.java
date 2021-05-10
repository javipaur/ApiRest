/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "TiposRestriccion")
public class TipoRestriccion {

    private static class COMPARADORES {

        public static String EQUALS = "eq";
        public static String BETWEEN = "between";
        public static String GRATER_OR_EQUAL = "ge";
        public static String LOWER_OR_EQUAL = "le";
    }

    private static class TIPOS_VALOR {

        public static String INTEGER = "int";
        public static String DATE = "date";
        public static String STRING = "string";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 15)
    private String nombre;

    @Column(name = "campo", length = 255)
    private String campo;

    @Column(name = "comparador")
    private String comparador;

    @Column(name = "tipoValor")
    private String tipoValor;
    
    @OneToMany(mappedBy = "tipoRestriccion", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<TipoRestriccionCampo> campos;

    public TipoRestriccion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getComparador() {
        return comparador;
    }

    public void setComparador(String comparador) {
        this.comparador = comparador;
    }   
    
    public List<TipoRestriccionCampo> getCampos() {
        return campos;
    }

    public void setCampos(List<TipoRestriccionCampo> campos) {
        this.campos = campos;
    }
     
    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public DetachedCriteria getCriteria(DetachedCriteria dc, String valorInicial, String valorFinal) {
        if (this.comparador.equals(COMPARADORES.EQUALS)) {

        }
        return null;
    }

    public String getQuery(Boolean or) {

        String query ="(r.tipoRestriccion.id="+id+" AND ";
        if (or) {
            if (this.comparador.equals(COMPARADORES.BETWEEN)) {
                query+= "((r.valorInicial<=" + campo + " OR r.valorInicial='') AND (r.valorFinal>=" + campo + " OR r.valorFinal=''))";
            }

            if (this.comparador.equals(COMPARADORES.EQUALS)) {
                query+=  " (" + campo + "=r.valorInicial)";
            }

            if (this.comparador.equals(COMPARADORES.GRATER_OR_EQUAL)) {
                query+=  " (" + campo + ">=r.valorInicial)";
            }

            if (this.comparador.equals(COMPARADORES.LOWER_OR_EQUAL)) {
                query+=  " (" + campo + "<=r.valorInicial)";
            }
        } else {
            if (this.comparador.equals(COMPARADORES.BETWEEN)) {
                query+=  "((r.valorInicial>" + campo + " AND r.valorInicial!='') OR (r.valorFinal<" + campo + " AND r.valorFinal!=''))";
            }

            if (this.comparador.equals(COMPARADORES.EQUALS)) {
                query+=  " (" + campo + "!=r.valorInicial)";
            }

            if (this.comparador.equals(COMPARADORES.GRATER_OR_EQUAL)) {
                query+=  " (" + campo + "<r.valorInicial)";
            }

            if (this.comparador.equals(COMPARADORES.LOWER_OR_EQUAL)) {
                query+=  " (" + campo + ">r.valorInicial)";
            }
        }

        return query+")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final TipoRestriccion other = (TipoRestriccion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
