package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;

import java.util.List;

public interface UserService {
   // User createToken(User user);

    User SaveUser(User user);
    Role saveRole(Role role);
    //void addRoleToUser(String username,String roleName);
   // User getUser(String username);
    List<User>getUsers();
 // User Authent(String username ,String password);

}
