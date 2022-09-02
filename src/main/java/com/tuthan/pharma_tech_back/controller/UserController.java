package com.tuthan.pharma_tech_back.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuthan.pharma_tech_back.filter.CustomAuthentificationfilter;
import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;
import com.tuthan.pharma_tech_back.repository.UserRepository;
import com.tuthan.pharma_tech_back.security.JWTUtil;
import com.tuthan.pharma_tech_back.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
     private UserDetailsService userDetailsService;
     @Autowired
     private UserRepository userRepository;
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
   @PostMapping("/authenticate")
   public User getUserByLogin(@RequestBody User user)
   {
       try {
         //  userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
       userDetailsService.loadUserByUsername(user.getUsername());

       }catch (Exception e)
       {
           System.out.println("Erreur login ou mot de passe!!!:"+e.getLocalizedMessage());
       }
       return null;
   }

        @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.SaveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        String authorizationHeader =request.getHeader(AUTHORIZATION);
        if(authorizationHeader !=null && authorizationHeader.startsWith(JWTUtil.PREFIX)){
            try {
                String jwt =authorizationHeader.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm =Algorithm.HMAC256(JWTUtil.SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(jwt);
                String username =decodedJWT.getSubject();
                User user =userService.getUser(username);
                String jwtAccesToken= JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() +JWTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getRoleUser).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens =new HashMap<>();
                tokens.put("access_token",jwt);
                tokens.put("refresh_token",jwtAccesToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){
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
            throw new  RuntimeException("Refresh Token is missing");
        }
    }
    @GetMapping(path = "/profile")
    public User profile(Principal principal){
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }
    @Data
    class RoleToUserForm {
        private String username;
        private String roleName;
    }


}
