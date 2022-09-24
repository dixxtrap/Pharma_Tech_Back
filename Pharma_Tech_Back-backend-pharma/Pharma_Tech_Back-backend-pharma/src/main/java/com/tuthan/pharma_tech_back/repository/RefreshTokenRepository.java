package com.tuthan.pharma_tech_back.repository;

import java.util.Optional;

import com.tuthan.pharma_tech_back.models.RefreshToken;
import com.tuthan.pharma_tech_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByUser(User user);
}
