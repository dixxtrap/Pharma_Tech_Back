package com.tuthan.pharma_tech_back.controller;

import com.tuthan.pharma_tech_back.models.Pharmacie;
import com.tuthan.pharma_tech_back.services.PharmacieService;
import com.tuthan.pharma_tech_back.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class PharmacienController {
    @Autowired
    private PharmacieService pharmacieService;

    @GetMapping("/pharmacies")
    public List<Pharmacie> getAllPharmciess() {
        System.out.println("Recevez tous les pharmacies...");
        return pharmacieService.getAllPharmcies();
    }


    //Ajout de Pharmcie
    @PostMapping("/addpharmcie")
    public void createPharmcie(@RequestBody Pharmacie pharmacie)  {
        pharmacieService.SavePharmacie(pharmacie);
    }
    @GetMapping("/getPharmacie")
    public Pharmacie getPharmcie(@PathVariable Long id){
        return  pharmacieService.getPharmacie(id);
    }

    //Rechercher par Nom
    @GetMapping("/rechercheNomPhar/{nomPharma}")
    public Pharmacie recherchePharmacie(@PathVariable String nomPharma) {
        return pharmacieService.getByNomPharmacie(nomPharma);
    }
}
