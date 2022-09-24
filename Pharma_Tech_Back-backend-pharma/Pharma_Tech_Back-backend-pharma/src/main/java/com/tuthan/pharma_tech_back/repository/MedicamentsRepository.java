package com.tuthan.pharma_tech_back.repository;

import com.tuthan.pharma_tech_back.models.Medicaments;
import com.tuthan.pharma_tech_back.models.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentsRepository extends JpaRepository<Medicaments,Long> {
    Medicaments findByNomMedica(String nomMedoc);
    Boolean existsByNomMedica(String nomMedoc);
}
