/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wdreams.model.dao.Dao;
import com.wdreams.utils.MD5Encode;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Set;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author victor
 */
@JsonIgnoreProperties({"ambito", "centro", "authorities", "roles"})
@Entity
@Table(name = "Usuarios")
public class Usuario extends User {

    public static String WEBDREAMS_USUARIO = "webdreams";

    @Column(name = "nombre", nullable = false, length = 50)
    protected String nombre;
    @Column(name = "apellidos", nullable = false, length = 100)
    protected String apellidos;
    @ManyToOne
    @JoinColumn(name = "idTipoDocumento", nullable = false, referencedColumnName = "id")
    protected TipoDocumento tipoDocumento;
    @Column(name = "documento", nullable = false, length = 9)
    protected String documento;
    @Column(name = "mail", length = 50)
    protected String mail;

    @ManyToOne
    @JoinColumn(name = "idAmbito", referencedColumnName = "id")
    private Ambito ambito;

    @ManyToOne
    @JoinColumn(name = "idCentro", referencedColumnName = "id")
    private Centro centro;

    @ManyToOne
    @JoinColumn(name = "idComercio", referencedColumnName = "id")
    private CentroComercio comercio;

    @ManyToOne
    @JoinColumn(name = "idPuestoImpresion", referencedColumnName = "id")
    private PuestoImpresion puestoImpresion;

    @Column(name = "asignable")
    private Boolean asignable;

    @ManyToOne
    @JoinColumn(name = "idPerfil", referencedColumnName = "id")
    private Perfil perfil;

    public static int USUARIO_SIN_ASIGNAR = 138;
    public static int USUARIO_APP = 17;

    public Usuario(String username, String password, /*Set<Rol> roles,*/ String nombre,
                   String apellidos, TipoDocumento tipoDocumento, String documento, String mail,
                   Perfil perfil,/*TipoUsuario tipoUsuario,*/ Ambito ambito, Centro centro,
                   PuestoImpresion puestoImpresion, Boolean asignable, CentroComercio comercio) throws NoSuchAlgorithmException {
        this.username = username;
        if (password != null) {
            this.password = MD5Encode.encode(password);
        }
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.mail = mail;
        this.perfil = perfil;
        // this.tipoUsuario = tipoUsuario;
        this.ambito = ambito;
        this.centro = centro;
        this.enabled = Boolean.TRUE;
        // this.roles = roles;
        this.asignable = asignable;
        this.puestoImpresion = puestoImpresion;
        this.comercio = comercio;
    }

    public String getIdUsuario() {
        return super.getUsername();
    }

  /*  public TipoUsuario getTipoUsuario() {
        return perfil.getTipoUsuario();
    }*/

   /* public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }*/

    public CentroComercio getComercio() {
        return comercio;
    }

    public void setComercio(CentroComercio comercio) {
        this.comercio = comercio;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Boolean getRequiereAmbito() {
        return this.perfil.getRequiereAmbito() && ambito != null;
    }

    public Boolean getRequiereCentro() {
        return this.perfil.getRequiereCentro() && centro != null;
    }

    public Boolean getRequiereComercio() {
        return this.perfil.getRequiereComercio() && comercio != null;
    }

    public Boolean getRequierePuestoImpresion() {
        return this.perfil.getRequierePuestoImpresion() && puestoImpresion != null;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getAsignable() {
        return asignable;
    }

    public void setAsignable(Boolean asignable) {
        this.asignable = asignable;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public PuestoImpresion getPuestoImpresion() {
        return puestoImpresion;
    }

    public void setPuestoImpresion(PuestoImpresion puestoImpresion) {
        this.puestoImpresion = puestoImpresion;
    }

    public String getEmailEnviable(Dao dao) {
            return this.mail;
    }

    public class UsuarioDatosExport {

        public String getUsername() {
            return Usuario.this.getUsername();
        }

        public String getPerfil() {
            return Usuario.this.getPerfil().getNombre();
        }

        public String getNombre() {
            return Usuario.this.getNombre();
        }

        public String getApellidos() {
            return Usuario.this.getApellidos();
        }

        public String getTipoDocumento() {
            return Usuario.this.getTipoDocumento().getNombre();
        }

        public String getDocumento() {
            return Usuario.this.getDocumento();
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfil.getRoles();
    }

    @Override
    public Set<Rol> getRoles() {
        return perfil.getRoles();
    }


}
