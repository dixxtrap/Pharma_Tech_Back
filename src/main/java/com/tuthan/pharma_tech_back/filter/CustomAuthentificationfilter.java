package com.tuthan.pharma_tech_back.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.ManyToMany;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthentificationfilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public CustomAuthentificationfilter(AuthenticationManager authenticationManager){
        this.authenticationManager =authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {
      /*  try {
            User user =new ObjectMapper().readValue(request.getInputStream(),User.class);
           log.info("-------- {}",user);
            return authenticationManager.authenticate(
                    new  UsernamePasswordAuthenticationToken(
                    user.getUsername(),user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("---------------------"+e.getMessage());
        }*/
        String username=request.getParameter("username");
        String password =request.getParameter("password");
        log.info("Username is {}",username);   log.info("Password is {}",password);
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user =(User)authentication.getPrincipal(); //L'utilisateur qui est authentifié
         Algorithm algorithm =Algorithm.HMAC256(JWTUtil.SECRET);
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +JWTUtil.EXPIRE_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
               .withExpiresAt(new Date(System.currentTimeMillis() +JWTUtil.EXPIRE_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
       /* response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);*/
        Map<String,String> tokens =new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);



    }
}

