package com.tuthan.pharma_tech_back.controller;

import com.tuthan.pharma_tech_back.jwtresponse.MessageResponse;
import com.tuthan.pharma_tech_back.models.Medicaments;
import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.repository.MedicamentsRepository;
import com.tuthan.pharma_tech_back.services.MedicamentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@CrossOrigin("*")
public class MedicamentsController {
    private final MedicamentsService medicamentsService;
    private final MedicamentsRepository medicamentsRepository;

    @PostMapping(value = "/saveMedoc")
    public ResponseEntity<?>  saveMedic(@RequestBody Medicaments medicaments) {
        if (medicamentsRepository.existsByNomMedica(medicaments.getNomMedica())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Erreur : le medicaments existe deja !"));
        }
        medicamentsService.SaveMedoc(medicaments);
        medicamentsRepository.save(medicaments);
        return ResponseEntity.ok(new MessageResponse("Medicaments  enregistré avec succès!"));
    }

    @GetMapping(value = "/allMedoc")
    public List<Medicaments> getAllMedoc() {
        return medicamentsService.getAllMedicaments();
    }

    @PutMapping(value = "/modif")
    public Medicaments update(@RequestBody Medicaments medicaments) {
        Medicaments medicaments1 = new Medicaments();
        medicaments1.setNomMedica(medicaments.getNomMedica());
        medicaments1.setCategorieMedicaments(medicaments.getCategorieMedicaments());
        return medicamentsService.SaveMedoc(medicaments1);
    }

    //Rechercher par Nom Medicaments
    @GetMapping("/rechercheNomMedic/{nomMedoc}")
    public Medicaments rechercheMedic(@PathVariable String nomMedoc) {
        return medicamentsService.getByNomMedoc(nomMedoc);
    }
}
