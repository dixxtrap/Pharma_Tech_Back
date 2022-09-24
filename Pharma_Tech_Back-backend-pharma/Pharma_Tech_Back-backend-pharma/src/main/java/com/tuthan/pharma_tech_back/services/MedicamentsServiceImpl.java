package com.tuthan.pharma_tech_back.services;

import com.tuthan.pharma_tech_back.models.Medicaments;
import com.tuthan.pharma_tech_back.repository.MedicamentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MedicamentsServiceImpl implements MedicamentsService{
    private  final MedicamentsRepository medicamentsRepository;
    @Override
    public Medicaments SaveMedoc(Medicaments medicaments) {
        return medicamentsRepository.save(medicaments);
    }

    @Override
    public Medicaments getMedicaments(Long id) {
        return medicamentsRepository.findById(id).get();
    }

    @Override
    public List<Medicaments> getAllMedicaments() {
        return medicamentsRepository.findAll();
    }

    @Override
    public Medicaments getByNomMedoc(String nomMedoc) {
        return medicamentsRepository.findByNomMedica(nomMedoc);
    }
}
