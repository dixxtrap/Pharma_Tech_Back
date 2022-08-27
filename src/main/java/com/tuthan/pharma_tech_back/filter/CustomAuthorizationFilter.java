package com.tuthan.pharma_tech_back.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Authenticator;
import com.tuthan.pharma_tech_back.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        response.addHeader("Access-Control-Allow-Origin", "*"); //toutes les URI sont autorisées
//        response.addHeader("Access-Control-Allow-Methods","Origin, X-Requested-With, Content-Type, Accept, Authorization,GET, POST, PUT, DELETE, OPTIONS");
//        response.addHeader("Access-Control-Allow-Headers", "Origin,Access-Control-Allow-Origin,Content-Type,Accept,Jwt-Token,Authorization,X-Requested-With,Access-Control-Allow-Credentials");
//        if(request.getMethod().equals("OPTIONS")){
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
  if(request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/api/token/refresh")){
       filterChain.doFilter(request,response);
   }else {
       String authorizationHeader =request.getHeader(JWTUtil.AUTH_HEADER);
       if(authorizationHeader !=null && authorizationHeader.startsWith(JWTUtil.PREFIX)){
           try {
               String token =authorizationHeader.substring(JWTUtil.PREFIX.length());
               Algorithm algorithm =Algorithm.HMAC256(JWTUtil.SECRET);
               JWTVerifier verifier = JWT.require(algorithm).build();
               DecodedJWT decodedJWT = verifier.verify(token);
               String username =decodedJWT.getSubject();
               String[] roles =decodedJWT.getClaim("roles").asArray(String.class);
               Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
               stream(roles).forEach(role ->{
                   authorities.add( new SimpleGrantedAuthority(role));
               });
               UsernamePasswordAuthenticationToken authenticationToken =new
                       UsernamePasswordAuthenticationToken(username,null,authorities);
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              filterChain.doFilter(request,response);
               log.info("Reussi ");
               response.setStatus(Status.STATUS_ACTIVE);
           }catch (Exception exception){
               log.error("Error logging in :{}",exception.getMessage());
               response.setHeader("error",exception.getMessage());
               response.setStatus(FORBIDDEN.value());
               // response.sendError(FORBIDDEN.value());
               Map<String,String> error =new HashMap<>();
               error.put("error_message",exception.getMessage());
               response.setContentType(APPLICATION_JSON_VALUE);
               new ObjectMapper().writeValue(response.getOutputStream(),error);

           }
       }
       else{
           filterChain.doFilter(request,response);
       }
   }
    }

}
