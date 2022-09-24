package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;

import com.tuthan.pharma_tech_back.repository.RoleRepository;
import com.tuthan.pharma_tech_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@CrossOrigin(origins = "*")
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User SaveUser(User user) {
        log.info("Saving new user {} to the database",user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       // user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getName());
        return roleRepository.save(role);
    }

//    @Override
//    public void addRoleToUser(String username, String roleName) {
//        log.info("Adding role {} to the user {}",roleName,username);
//       User user= userRepository.findByUsername(username);
//       Role role=roleRepository.findByName(roleName);
//       user.getRoles().add(role);
//    }

//    @Override
//    public User getUser(String username) {
//        log.info("Fetching user {}",username);
//        return userRepository.findByUsername(username);
//    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }


}
