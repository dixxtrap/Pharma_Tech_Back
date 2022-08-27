package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie,Long> {
    public Pharmacie findByNomPharm(String nomPharm);
}
