package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.CategorieMedicaments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesMedicRepository extends JpaRepository<CategorieMedicaments,Long> {
}
