package com.wdreams.jwt.service;


import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.Entidad;
import com.wdreams.model.dao.entity.Rol;
import com.wdreams.model.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private Dao dao;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) {
        User usuario = dao.getUserUsername(username);


        if(usuario == null)
        {
            //Si no lo encuentra, puede que me est� pasando un DNI. Esta opci�n es deseada
            String dni = username;
            Entidad testEntidad = dao.getEntidadByDNI(dni);
            String idEntidad = testEntidad.getIdEntidad();
            usuario = dao.getUserUsername(idEntidad);
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Rol role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
//
//        if(authorities.isEmpty()) {
//            logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
//            throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
//        }

        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);




       // Person person = dao.findByUsername(username)
       //         .orElseThrow(() -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));
       // return new User(person.getUsername(), person.getPassword(), new ArrayList<>());
    }

}
