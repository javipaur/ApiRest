/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "username", unique = true)
    protected String username;
    @JsonIgnore
    @Column(name = "password", length = 255)
    protected String password;
    @Column(name = "enabled", nullable = false)
    protected Boolean enabled;
    /* @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
     @JoinColumn(name = "idUser", referencedColumnName = "id")
     @Fetch(FetchMode.SELECT)
     private Set<Authorities> authorities;*/
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {
        @JoinColumn(name = "idUser")}, inverseJoinColumns = {
        @JoinColumn(name = "idRole")})
    @Fetch(FetchMode.SELECT)
    protected Set<Rol> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /* public Set<Authorities> getAuthorities() {
     return authorities;
     }*/
    /* public void setAuthorities(Set<Authorities> authorities) {
     this.authorities = authorities;
     }*/
    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getUserEmail() {
        if (this instanceof Usuario) {
            return ((Usuario) this).mail;
        } else {
            return ((Entidad) this).getMail();
        }
    }

    public String getUserNombre() {
        if (this instanceof Usuario) {
            return ((Usuario) this).nombre;
        } else {
            return ((Entidad) this).getNombre();
        }
    }

    public String getUserApellidos() {
        if (this instanceof Usuario) {
            return ((Usuario) this).apellidos;
        } else {      
            return ((Entidad) this).getApellidos();        
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles;
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.addAll((Collection<? extends GrantedAuthority>) getRoles());
            return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
