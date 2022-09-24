package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie,Long> {
     Pharmacie findByNomPharm(String nomPharm);
//     @Query("select p from Pharmacie p where p.nomPharm like :nomPharm")
//      Page<Pharmacie> pharmacieByNomPharm(@Param("nomPharm") String mc);
}
