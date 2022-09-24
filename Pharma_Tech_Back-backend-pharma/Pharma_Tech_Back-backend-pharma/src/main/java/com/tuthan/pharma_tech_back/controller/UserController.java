package com.tuthan.pharma_tech_back.controller;

import com.tuthan.pharma_tech_back.exception.TokenRefreshException;
import com.tuthan.pharma_tech_back.jwtrequest.LoginRequest;
import com.tuthan.pharma_tech_back.jwtrequest.RefreshTokenRequest;
import com.tuthan.pharma_tech_back.jwtrequest.SignupRequest;
import com.tuthan.pharma_tech_back.jwtresponse.MessageResponse;
import com.tuthan.pharma_tech_back.jwtresponse.SignupResponse;
import com.tuthan.pharma_tech_back.jwtresponse.TokenRefreshResponse;
import com.tuthan.pharma_tech_back.models.ERole;
import com.tuthan.pharma_tech_back.models.RefreshToken;
import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;
import com.tuthan.pharma_tech_back.repository.RoleRepository;
import com.tuthan.pharma_tech_back.repository.UserRepository;
import com.tuthan.pharma_tech_back.services.RefreshTokenService;
import com.tuthan.pharma_tech_back.services.UserDetailsImpl;
import com.tuthan.pharma_tech_back.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new SignupResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        //Verifie si l'utilisateur existe deja dans la base
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Erreur : le nom d'utilisateur est déjà pris !"));
        }
        //Verifie si l'email existe deja dans la base
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Erreur : l'e-mail est déjà utilisé !"));

        }
        //Créer un nouveau utilisateur
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getNom(),signUpRequest.getPrenom());
            user.setActive(true);
            user.setDateCreation(user.getDateCreation());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur : le rôle est introuvable."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() ->
                                        new RuntimeException("Erreur : le rôle est introuvable."));

                        roles.add(adminRole);

                        break;
//                    case "pharmacien":
//                        Role pharmaRole = roleRepository.findByRoleUser(ERole.ROLE_PHARMACIEN)
//                                .orElseThrow(() -> new RuntimeException("Erreur : le rôle est introuvable."));
//                        roles.add(pharmaRole);
//
//                          break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() ->
                                        new RuntimeException("Erreur : le rôle est introuvable"));

                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,

                        "Le jeton d'actualisation n'est pas dans la base de données!"));
    }

}
