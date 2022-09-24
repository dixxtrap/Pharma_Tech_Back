package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.ERole;
import com.tuthan.pharma_tech_back.models.Role;
import com.tuthan.pharma_tech_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role>  findByName(ERole name);
}
