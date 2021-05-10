package com.wdreams.controllers;




import com.wdreams.jwt.exception.AuthenticationException;
import com.wdreams.jwt.service.JwtUserDetailsService;
import com.wdreams.model.rest.response.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@RestController
public class AuthenticationController {


    @Autowired
    private Environment env;

    public String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUserDetailsService tcUserDetailsService;


    private Authentication authResult;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password)
            throws AuthenticationException {

        username = username != null ? username : "";
        username = username.trim();
        password = password != null ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        //Utilizamos nuestro propio objeto  Userdetailservice
        UserDetails userDetails = tcUserDetailsService.loadUserByUsername(username);
        //A partir de aqui  montamsoel token
        Collection<? extends GrantedAuthority> roles =  userDetails.getAuthorities();

        Claims claims = Jwts.claims();
        //final String authorities =  userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        String token="Bearer "+Jwts.builder()
                .setSubject(username)
                .claim("Authorities", roles)
                .signWith(SignatureAlgorithm.HS256, "Alguna Clave secreta ")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+120000))
                .compact();

       // String token = jwtTokenUtil.generateToken(userDetails);
        UserAuth respuesta =new UserAuth();
        respuesta.setToken(token);
        respuesta.setUser(userDetails.getUsername());
        respuesta.setMsj("mensaje",String.format("Hola %s, has iniciado sesion con exito",username));

        return ResponseEntity.ok(respuesta);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private Authentication authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
}
