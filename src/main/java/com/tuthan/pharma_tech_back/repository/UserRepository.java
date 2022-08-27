package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>  {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username,String password);
}
