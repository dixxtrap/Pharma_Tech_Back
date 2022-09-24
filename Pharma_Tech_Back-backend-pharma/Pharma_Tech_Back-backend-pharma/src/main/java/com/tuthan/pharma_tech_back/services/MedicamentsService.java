package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Medicaments;
import com.tuthan.pharma_tech_back.models.Pharmacie;

import java.util.List;

public interface MedicamentsService {
    Medicaments SaveMedoc(Medicaments medicaments);
    Medicaments getMedicaments(Long id);
    List<Medicaments> getAllMedicaments();
    Medicaments getByNomMedoc(String nomMedoc);
}
